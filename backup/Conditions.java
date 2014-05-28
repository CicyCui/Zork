import java.util.Iterator;
import java.util.List;


class Conditions {
	private String owner;
	private String status;
	private String has;
	private String object;
	public Conditions(){}
	
	public void print(){
		owner = null;
		status = null;
		has = null;
		object = null;
		System.out.println("Condition: " + owner + " " + status + " " + has + " " + object);
	}
	public void configure(List<String> config){
		Iterator<String> itr = config.iterator();
		String lastElement = "";
		String currElement = "";

		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			if(lastElement.equals( "STARTowner")){
				owner = currElement;
			}else if(lastElement.equals( "STARTstatus")){
				status = currElement;
			}else if(lastElement.equals( "STARThas")){
				has = currElement;
			}else if(lastElement.equals( "STARTobject")){
				object = currElement;
			}
			itr.remove();
		}
	}
	public void setOwner(String s){
		owner = s;
	}
	public void setStatus(String s){
		status = s;
	}
	public void setHas(String s){
		has = s;
	}
	public void setObject(String s){
		object = s;
	}
	public String getOwner(){
		return owner;
	}
	public String getStatus(){
		return status;
	}
	public String getHas(){
		return has;
	}
	public String getObject(){
		return object;
	}
	
}
