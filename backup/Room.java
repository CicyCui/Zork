import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.Stack;

class Room extends ZorkObject{
	private String Roomtype;
	private List<Border> border;
	public List<String> containers;
	public List<String> items;
	public List<String> creatures;

	
	public Room(){
		super();
		type = "room";
		Roomtype = "normal";
	    border = new ArrayList<Border>();
	    containers = new ArrayList<String>();
	    items = new ArrayList<String>();
	    creatures = new ArrayList<String>();
	}
	public void print(){
		System.out.println("Room name:" + name + " Type:" + Roomtype);
		System.out.println("Description:" + description);
		
		Iterator<Border> bitr = border.iterator();
		while(bitr.hasNext()){ bitr.next().print(); }	
		Iterator<String> citr = containers.iterator();
		while(citr.hasNext()){System.out.println("container: "+citr.next());}
		citr = items.iterator();
		while(citr.hasNext()){System.out.println("item: "+citr.next());}
		citr = creatures.iterator();
		while(citr.hasNext()){System.out.println("creature: "+citr.next());}
		
	}
	public void configureRoom(List<String> roomMap){
		
		Iterator<String> itr = roomMap.iterator();
		String last = "";
		String curr = "";
		//Stack<String> tmpPile = new Stack<String>(); 
		List<String> tmpList = new ArrayList<String>();	
		Border btmp = null;

		while(itr.hasNext()){
			last = curr;
			curr = itr.next();
			//System.out.println(curr);
			if(last.equals("STARTitem")){
				items.add(curr);
			}else if(last.equals("STARTcontainer")){
				containers.add(curr);
			}else if(last.equals("STARTcreature")){
				creatures.add(curr);
			}else if(last.equals("STARTname")){
				name = curr;
			}else if(last.equals("STARTstatus")){
				status = curr;
			}else if(last.equals("STARTdescription")){
				description = curr;
			}else if(last.equals("STARTtype")){
				Roomtype = curr;
			}
			else if(last.equals("STARTtrigger")){
				Trigger trigger = new Trigger();
				while(!curr.equals("ENDtrigger")){
					tmpList.add(curr);
					itr.remove();
					curr = itr.next();		
				}
				if(trigger != null && curr.equals("ENDtrigger")){
					trigger.configure(tmpList);
					triggers.add(trigger);
					tmpList.clear();
				}			
			}else if(last.equals("STARTborder")){
				btmp = new Border();
				while(!curr.equals("ENDborder") && itr.hasNext()){
					//System.out.println(curr);
					tmpList.add(curr);
					last = curr;
					curr = itr.next();		
				}
				if(btmp != null && curr.equals("ENDborder")){
					//System.out.println("border: "+tmpList);
					btmp.configure(tmpList);
					border.add(btmp);
					tmpList.clear();
				}	
			}
		}
	}
	public void setName(String s_name){
		super.setName(s_name);
	}
	
	public List<Border> getBorder(){
		return border;
	}
	public void addContainer(String c){
		containers.add(c);
	}
	public List<String> getContainer(){
		return containers;
	}
	public void addItem(String i){
		items.add(i);
	}
	public List<String> getItems(){
		return items;
	}
	public void addCreature(String c){
		creatures.add(c);
	}
	public List<String> getCreatures(){
		return creatures;
	}
	public void setType(String s){
		type = s;
	}
	public String getType(){
		return type;
	}
	public String getRoomType(){
		return Roomtype;
	}
	public void DeleteBorder(String borderName){
		for(Border b: border){
			if(b.getName().equals(borderName)){
				border.remove(b);
			}
		}
	}
}
