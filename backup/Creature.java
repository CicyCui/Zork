import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class Creature extends ZorkObject{
	private String vulnerability;
	private Attack attack;
	
	public Creature(){
		super();
		type = "creature";
		attack = new Attack();
	}
	public void print(){
		System.out.println("Creature: " + name);
		System.out.println("Description:" + description);
		System.out.println("Vulnerability:" + vulnerability);
		//attack.print();
	}
	
	public void configure(List<String> config){
		Iterator<String> itr = config.iterator();
		String lastElement = "";
		String currElement = "";
		List<String> Config = new ArrayList<String>();
		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			if(lastElement.equals( "STARTattack")){
				attack = new Attack();
				while(!currElement.equals("ENDattack")){
					Config.add(currElement);
					lastElement = currElement;
					currElement = itr.next();
				}
			}else if(lastElement.equals( "STARTtrigger")){
				Trigger trigger = new Trigger();
				while(!currElement.equals("ENDtrigger")){
					Config.add(currElement);
					itr.remove();
					currElement = itr.next();		
				}
				if(trigger != null && currElement.equals("ENDtrigger")){
					trigger.configure(Config);
					triggers.add(trigger);
					Config.clear();
				}			
			}else if(lastElement.equals("ENDattack")){
				attack.configure(Config);
				Config.clear();
			}
			else if(lastElement.equals( "STARTvulnerability")){
				vulnerability = currElement;
			}
			else if(lastElement.equals( "STARTname")){
				super.setName(currElement);
			}
			else if(lastElement.equals( "STARTdescription")){
				super.setDescription(currElement);
			}
			else if(lastElement.equals( "STARTstatus")){
				status = currElement;
			}else if(lastElement.equals("STARTcreature")){
				name = currElement;
			}
		}
	}
	public void setVulnerability(String s){
		vulnerability = s;
	}
	public String getVulnerability(){
		return vulnerability;
	}
	public Attack getAttack(){
		return attack;
	}
	
}
