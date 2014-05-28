import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class Trigger {
	private Conditions condition;
    private String command;
    private String type;
    private String print;
    private ArrayList<String> actions;
	private boolean triggered;

	public Trigger(){
		actions = new ArrayList<String>();	
		condition = new Conditions();
		command = "";
		type = "single";
		print = "";
		triggered = false;
	}
	public List<String> getActions(){
		return actions;
	}
	public String getPrint(){
		return print;
	}
	public void setTrigger(boolean status){
		triggered = status;
	}
	public boolean checkTrigger(){
		return triggered;
	}
	public Conditions getCondition(){
		return condition;
	}
	public String getCommand(){
		return command;
	}
	public String getType(){
		return type;
	}
	public String pprint(){
		return print;
	}
	

	public void configure(List<String> config){
		Iterator<String> itr = config.iterator();
		String lastElement = "";
		String currElement = "";
		List<String> cond = new ArrayList<String>();
		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			//System.out.println(currElement);
			if(lastElement.equals("STARTprint")){
				print = currElement;
			}else if(lastElement.equals("STARTcommand")){
				command = currElement;
			}else if(lastElement.equals("STARTtype")){
				type = currElement;
				if(type.equals("permanent")) triggered = true;
			}else if(lastElement.equals("STARTaction")){
				actions.add(currElement);
			}else if(lastElement.equals( "STARTcondition")){
				while(!currElement.equals("ENDcondition")){
					cond.add(currElement);	
					lastElement = currElement;
					currElement = itr.next();
					//System.out.println(currElement);
				}
				condition.configure(cond);
			}
			
			
		}
	}

}
