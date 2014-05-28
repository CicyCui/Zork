

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Attack 
{
    private List<String> actions;
    private Conditions condition;
    private String print;
    
    public Attack(){
    	actions = new ArrayList<String>();
    	condition = null;
    	print = "";
    }
    
    public List<String> getAction(){
    	return actions;
    }
    public Conditions getConditions(){
    	return condition;
    }
    public String getPrint(){
    	return print;
    }
    
    public void configure(List<String> config){
    	Iterator<String> itr = config.iterator();
		String lastElement = "";
		String currElement = "";
		List<String> Config = new ArrayList<String>();
		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			if(lastElement.equals( "STARTcondition")){
				condition = new Conditions();
				while(!currElement.equals("ENDcondition")){
					Config.add(currElement);
					lastElement = currElement;
					currElement = itr.next();
				}			
			}else if(lastElement.equals("STARTaction")){
				actions.add(currElement);
			}
			else if(lastElement.equals( "STARTprint")){
				print = currElement;
			}
			
		}
    }

    
}
