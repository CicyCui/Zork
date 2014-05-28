
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Stack;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLparser {

	private List<String> map;
	//private List<Room> ZorkMap;
	private Map<String,ZorkObject> ZorkMap;
	//private Map<String,String> Owner;
	//private Stack<String> context;
	public XMLparser(String filename) throws IOException {
		try {
			map = new ArrayList<String>();
			ZorkMap = new HashMap<String,ZorkObject>();
			//Owner = new HashMap<String,String>();
			//context = new Stack<String>();
			
			XMLReader p = XMLReaderFactory.createXMLReader();
			ZorkXMLHandler h = new ZorkXMLHandler();
			
		    p.setContentHandler(h);
		    p.parse(filename);
		    map = h.getMap();
		    Iterator<String> itr = map.iterator();
		    String curr = "";
		    Room currRoom = null;
		    Container ctmp = null;
			Item itmp = null;
			Creature crtmp = null;
		    List<String> subMap = new ArrayList<String>();
			while(itr.hasNext()){
				curr = itr.next();
				
				if(curr.equals("STARTroom")){
					//System.out.println("Start Room!");
					currRoom = new Room();
					while(!curr.equals("ENDroom")  && itr.hasNext()){
						subMap.add(curr);
						curr = itr.next();
						//System.out.println(curr);
					}
					if(currRoom != null && curr.equals("ENDroom")){
						//System.out.println(subMap);
						currRoom.configureRoom(subMap);
						subMap.clear();
						ZorkMap.put("room"+currRoom.getName(),currRoom);
					}	
				}
				if(curr.equals("STARTitem")){
					//System.out.println("Start Room!");
					itmp = new Item();
					while(!curr.equals("ENDitem")  && itr.hasNext()){
						subMap.add(curr);
						curr = itr.next();
						//System.out.println(curr);
					}
					if(itmp != null && curr.equals("ENDitem")){
						itmp.configure(subMap);
						subMap.clear();
						ZorkMap.put("item"+itmp.getName(),itmp);
					}	
				}
				if(curr.equals("STARTcontainer")){
					//System.out.println("Start Room!");
					ctmp = new Container();
					while(!curr.equals("ENDcontainer")  && itr.hasNext()){
						subMap.add(curr);
						curr = itr.next();
						//System.out.println(curr);
					}
					if(ctmp != null && curr.equals("ENDcontainer")){
						ctmp.configure(subMap);
						subMap.clear();
						ZorkMap.put("container"+ctmp.getName(),ctmp);
					}	
				}
				if(curr.equals("STARTcreature")){
					//System.out.println("Start Room!");
					crtmp = new Creature();
					while(!curr.equals("ENDcreature")  && itr.hasNext()){
						subMap.add(curr);
						curr = itr.next();
						//System.out.println(curr);
					}
					if(ctmp != null && curr.equals("ENDcreature")){
						crtmp.configure(subMap);
						subMap.clear();
						ZorkMap.put("creature"+crtmp.getName(),crtmp);
					}	
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	public Map<String,ZorkObject> getMap(){
		return ZorkMap;
	}
	
}

