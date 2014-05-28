import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class Item extends ZorkObject{
	private String writing;
	private List<String> turn_on;
	private boolean turned_on;
	public Item(){
		super();
		type = "item";
		turn_on = new ArrayList<String>();
		turned_on = false;
		writing = "Nothing written.";
	}
	public boolean checkTurnOn(){
		return turned_on;
	}
	public void print(){
		System.out.println("Item: " + name);
		System.out.println("Description:" + description);
		System.out.println("Writing:" + writing);
		Iterator<String> sitr = turn_on.iterator();
		while(sitr.hasNext()){ System.out.println("turn on:" + sitr.next()); }
	}
	public String getWriting(){
		return writing;
	}
	public String turnOn(){
		turned_on = true;
		if(turn_on.size() == 2){
		System.out.println(">> " + turn_on.get(0));
		return turn_on.get(1);
		}else{
			return null;
		}
	}
	public void configure(List<String> config){
		
		if(config.size() == 1){
			name = config.get(0);
		}
		else{
		Iterator<String> itr = config.iterator();
		String lastElement = "";
		String currElement = "";
		List<String> Config = new ArrayList<String>();
		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			if(lastElement.equals( "STARTwriting")){
				writing = currElement;
			}else if(lastElement.equals( "STARTname")){
				name = currElement;
			}else if(lastElement.equals( "STARTstatus")){
				status = currElement;
			}else if(lastElement.equals( "STARTdescription")){
				description = currElement;
			}
			else if(lastElement.equals( "STARTturnon")){
				while(!currElement.equals("ENDturnon")){
					if(!(currElement.startsWith("START")||currElement.startsWith("END"))){
						turn_on.add(currElement);
					}
					currElement = itr.next();
				}
			}else if(lastElement.equals( "STARTtrigger")){
				Trigger trigger = new Trigger();
				while(!currElement.equals("ENDtrigger")){
					Config.add(currElement);
					itr.remove();
					currElement = itr.next();		
				}
				if(trigger != null && currElement.equals( "ENDtrigger")){
					trigger.configure(Config);
					triggers.add(trigger);
					Config.clear();
				}			
			}
			
		}
	    }
		
	}

}
