
//import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ZorkXMLHandler extends DefaultHandler{
	
	private List<String> map;
	private String element;
	public ZorkXMLHandler() { map = new ArrayList<String>();}

	public List<String> getMap(){
		return map;
	}	
	//public void startDocument(){
		//System.out.println("Start parsing");
//	}	
//	public void endDocument(){
		//System.out.println("End parsing");
		//final Iterator<String> itr = map.iterator();
		//while(itr.hasNext()){
		//	System.out.println("element: "+itr.next());
		//}
//	}	
	public void startElement(String uri, String localName, 
            String qName, Attributes attributes) throws SAXException{
		//System.out.println("Tag:"+qName);
		map.add("START"+qName);
	}
	
	public void endElement(java.lang.String uri, 
			java.lang.String localName, java.lang.String qName) throws SAXException{
		map.add("END"+qName);
	}
	
	public void characters(char[] ch, int start, int length){
		element="";
		if(ch[start] !=' ' && ch[start] != '\n' && ch[start] != 0)
		{
			for(int i=start;i<start+length;i++)
			{
				if( ch[i] != '\n' && ch[i] != 0 )
					element+=ch[i];
			}
		}
		if(element!="") {
			map.add(element);
		}
	}

}
