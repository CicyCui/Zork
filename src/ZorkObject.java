import java.util.ArrayList;
import java.util.List;


class ZorkObject {
	protected String name;
	protected String status;
	protected String description;
	protected List<Trigger> triggers;
	protected List<String> items;
	protected String type;
	
	public void configure(List<String> configMap){};
	
	public ZorkObject(){
		triggers = new ArrayList<Trigger>();
		items = new ArrayList<String>();
	}
	public void setName(String s_name){
		name = s_name;
	}
	public String getName(){
		return name;
	}
	public void setStatus(String s_status){
		status = s_status;
	}
	public String getStatus(){
		return status;
	}
	public void setDescription(String s_description){
		description = s_description;
	}
	public String getDescription(){
		return description;
	}
	/*public void setTriggers(List<String> Triggers){
		for(Trigger trigger: triggers){
			trigger.configure(Triggers);
		}
	}*/
	public void print(){System.out.println(name);}
	public String getType(){
		return type;
	}

}
