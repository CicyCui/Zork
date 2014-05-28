import java.util.Iterator;
import java.util.List;


class Border extends ZorkObject{
	private String direction;
	private String name;
	
	public Border(){}	
	
	public void print(){
		System.out.println("Border: " + direction + " " + name);
	}
	
	public void configure(List<String> config){
		Iterator<String> itr = config.iterator();
		String lastElement = "";
		String currElement = "";
		while(itr.hasNext()){
			lastElement = currElement;
			currElement = itr.next();
			if(lastElement.equals("STARTname")){
				name = currElement;
			}else if(lastElement.equals("STARTdirection")){
				direction = currElement;
			}
			itr.remove();
		}
	}
	public void setName(String sname){
		name = sname;
	}
	public String getName(){
		return name;
	}
	public void setDirection(String sdirection){
		direction = sdirection;
	}
	public String getDirection(){
		return direction;
	}
}
