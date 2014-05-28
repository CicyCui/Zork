import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Container extends ZorkObject {
	private List<String> accept;
	public List<String> items;
	public Container(){
		super();
		type = "container";
		accept = new ArrayList<String>();
		items = new ArrayList<String>();
	}
	public void print(){
		System.out.println("Container: " + name);
		System.out.println("Description:" + description);
		Iterator<String> bitr = items.iterator();
		while(bitr.hasNext()){ System.out.println(bitr.next()); }
		Iterator<String> sitr = accept.iterator();
		while(sitr.hasNext()){ System.out.println(sitr.next()); }
	}
	
	public void configure(List<String> rawList){
		Iterator<String> itr = rawList.iterator();
		String lastElement = "";
		String currElement = "";
		List<String> itemConfig = new ArrayList<String>();
		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			if(lastElement.equals( "STARTitem")){	
					items.add(currElement);			
			}else if(lastElement.equals( "STARTtrigger")){
				Trigger trigger = new Trigger();
				while(!currElement.equals("ENDtrigger")){
					itemConfig.add(currElement);
					itr.remove();
					lastElement = currElement;
					currElement = itr.next();		
				}
				if(trigger != null && currElement.equals( "ENDtrigger")){
					//System.out.println(itemConfig);
					trigger.configure(itemConfig);
					triggers.add(trigger);
					itemConfig.clear();
				}			
			}else if(lastElement.equals( "STARTname")){
				name = currElement;
			}else if(lastElement.equals( "STARTstatus")){
				status = currElement;
			}else if(lastElement.equals("STARTdescription")){
				description = currElement;
			}else if(lastElement.equals("STARTaccept")){
				accept.add(currElement);
			}
			
			itr.remove();
		}
	}
	public void setAccept(String s){
		accept.add(s);
	}
	public List<String> getAccept(){
		return accept;
	}
	public void addItem(String i){
		items.add(i);
	}
	public boolean checkAccept(String s){
		if(accept.contains(s)){
			return true;
		}else{
			return false;
		}
	}
	public String contains(){
		return items.toString();
	}

}
