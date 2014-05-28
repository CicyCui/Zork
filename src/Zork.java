import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Zork {

	public static void main(String[] args) {
		try {
			if(args.length == 0){
				System.out.println("XML file needed to configure game!");
				return;
			}
			//List<Trigger> mytriggers = new ArrayList<Trigger>();
			XMLparser myParser = new XMLparser(args[0]);//System.in.toString());
			Map<String,ZorkObject> ZorkMap = myParser.getMap();
			Map<Trigger, String> triggers = new HashMap<Trigger, String>();
			//List<Trigger> cmdTriggers = new ArrayList<Trigger>();
			
			//System.out.println(ZorkMap.toString());
			Room dummy = new Room();
			dummy.name = "dummy";
			List<Room> rooms = new ArrayList<Room>();
			rooms.add(dummy);
			//List<Trigger> triggers = new ArrayList<Trigger>();
			Trigger currTri;
			for(String key: ZorkMap.keySet()){
				if(key.startsWith("room")){
					//System.out.println(key+ZorkMap.get(key));
					rooms.add((Room)ZorkMap.get(key));
					//ZorkMap.get(key).print();
				}
				else{
					if(ZorkMap.get(key).triggers.size() > 0){
					for(Trigger tri: ZorkMap.get(key).triggers){
					currTri = tri;
					if(currTri != null){
						if(currTri.getCommand() == null && currTri.getCondition() != null && currTri.getCondition().getObject() != null)
							triggers.put(currTri,currTri.getCondition().getObject());
						if(currTri.getCommand() != null)
							triggers.put(currTri,currTri.getCommand());
						//System.out.println("Trigger: "+currTri+" "+currTri.getType()+" "+currTri.getCondition().getObject());
							//cmdTriggers.add(currTri);
					}
					}
					}
				}
				
			}
			//for(ZorkObject ob: ZorkMap.values()){
			//	ob.print();
			//}
			Command command = new Command();
			Iterator<Room> itr = rooms.iterator();
			Iterator<Border> bitr;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			Room curr = null;
			Room tmp = null;
			Border border = null;
			List<String> inventory = new ArrayList<String>();
			//List<String> myList = new ArrayList<String>();
			String obj = "";
			Iterator<Item> iitr;
			Iterator<String> litr;
			Iterator<Container> citr;
			Item curritem;
			Container currcontainer;
			boolean triggerDetected = false;
			boolean roomTrigger = false;
			boolean contextChanged = true;
			
			while(itr.hasNext()){
				curr = itr.next();
				if(curr.getName() != null && curr.getName().equals("Entrance")){
					break;
				}
			}		
			while(true){
				if(contextChanged){
					System.out.println(">> You are at " + curr.getName() + ".");
					System.out.println(">> " + curr.getDescription());
					contextChanged = false;
				}
			
				System.out.print(">> ");
				input = br.readLine();
				
				triggerDetected = false;
				for(Trigger myTrigger: triggers.keySet()){	
					 if(command.checkTriggers(input,myTrigger,ZorkMap,inventory,curr,rooms,contextChanged)){
						 triggerDetected = true;
					 }
				}
				
				if(!triggerDetected){
				roomTrigger = false;
				for(Trigger tri: curr.triggers){
					if(command.checkTriggers(input,tri,ZorkMap,inventory,curr,rooms,contextChanged)){
						roomTrigger = true;
					}
				}
				if(!roomTrigger){
				
				if(input.equals("n")){
					//System.out.println(">> Heading north...");
					bitr = curr.getBorder().iterator();
					//System.out.println(curr.getBorder());
					while(bitr.hasNext()){
						border = bitr.next();
						//System.out.println(">> " + border.getDirection()+ ":"+border.getName());
						if(border.getDirection().equals("north")){
							itr = rooms.iterator();
							while(itr.hasNext()){
								tmp = itr.next();
								if(border.getName().equals(tmp.getName())){
									curr = tmp;
									contextChanged = true;
									break;
								}
							}
							if(!contextChanged){
								System.out.println(">> " + "Cannot go that way.");
							}
						}
					}
				}else if(input.equals("e") ){
					//System.out.println(">> Heading east...");
					bitr = curr.getBorder().iterator();
					//System.out.println(">> " + border.getDirection()+ ":"+border.getName());
					while(bitr.hasNext()){
						border = bitr.next();
						if(border.getDirection().equals("east")){
							itr = rooms.iterator();
							while(itr.hasNext()){
								tmp = itr.next();
								if(border.getName().equals(tmp.getName())){
									curr = tmp;
									contextChanged = true;
									break;
								}
							}
							if(!contextChanged){
								System.out.println(">> " + "Cannot go that way.");
							}
							break;
						}
					}
					if(!bitr.hasNext()){
						System.out.println(">> " + "Cannot go that way.");
					}
				}else if(input.equals("w") ){
					//System.out.println(">> Heading west...");
					bitr = curr.getBorder().iterator();
					//System.out.println(">> " + border.getDirection()+ ":"+border.getName());
					while(bitr.hasNext()){
						border = bitr.next();
						if(border.getDirection().equals("west")){
							itr = rooms.iterator();
							while(itr.hasNext()){
								tmp = itr.next();
								if(border.getName().equals(tmp.getName())){
									curr = tmp;
									contextChanged = true;
									break;
								}
							}
							if(!contextChanged){
								System.out.println(">> " + "Cannot go that way.");
							}
							break;
						}
					}
					if(!bitr.hasNext()){
						System.out.println(">> " + "Cannot go that way.");
					}
				}else if(input.equals("s")){
					//System.out.println(">> Heading south...");
					bitr = curr.getBorder().iterator();
					//System.out.println(">> " + border.getDirection()+ ":"+border.getName());
					while(bitr.hasNext()){
						border = bitr.next();
						if(border.getDirection().equals("south")){
							itr = rooms.iterator();
							while(itr.hasNext()){
								tmp = itr.next();
								if(border.getName().equals(tmp.getName())){
									curr = tmp;
									contextChanged = true;
									break;
								}
							}
							if(!contextChanged){
								System.out.println(">> " + "Cannot go that way.");
							}
							break;
						}
					}
					if(!bitr.hasNext()){
						System.out.println(">> " + "Cannot go that way.");
					}
				}
				else if(input.equals("i") ){
					if(inventory.size() == 0) System.out.println(">> Inventory: empty");
					else System.out.println(">> Inventory: " + inventory.toString());
					
				}
				else if(input.startsWith("take") ){
					if(input.length() > 4){
					obj = input.substring(5);
					List<Item> iList = new ArrayList<Item>();
					for(String key: ZorkMap.keySet()){
						if(key.startsWith("item")){
							//System.out.println(key+ZorkMap.get(key));
							litr = curr.items.iterator();
							while(litr.hasNext()){
								if(key.equals("item"+litr.next())) iList.add((Item)ZorkMap.get(key));
							}
						}
					} 
					curritem = new Item();
					curritem.setName("");
					iList.add(curritem);
					iitr = iList.iterator();
					while(iitr.hasNext()){
						curritem = iitr.next();
						if(curritem.getName().equals(obj)){
							System.out.println(">> Item " + curritem.getName()+ " added to inventory.");
							inventory.add(obj);
							curr.items.remove(obj);
							iList.clear();
							
							break;
						}
					}
					if(!iitr.hasNext()){
						System.out.println(">> " + "Cannot take " + obj);
						iList.clear();
					}
					}
				
						
					
				}else if(input.startsWith("open")){
					if(input.length() > 4){
					obj = input.substring(5);
					if(obj.equals("exit") && curr.getRoomType().equals("exit")){
						command.GameOver();
						break;
					}
					List<Container> cList = new ArrayList<Container>();
					for(String key: ZorkMap.keySet()){
						if(key.startsWith("container")){
							//System.out.println(key+ZorkMap.get(key));
							litr = curr.containers.iterator();
							while(litr.hasNext()){
								if(key.equals("container"+litr.next())) cList.add((Container)ZorkMap.get(key));
							}
						}
					} 
					currcontainer = new Container();
					currcontainer.setName("");
					cList.add(currcontainer);
					citr = cList.iterator();
					while(citr.hasNext()){
						currcontainer = citr.next();
						//System.out.println(">> " + currcontainer.getName());
						//if( currcontainer.trigger == null || currcontainer.trigger != null && !command.checkTriggers(input,currcontainer.trigger,ZorkMap,inventory)){
						if(currcontainer.getName().equals(obj)){
							System.out.println(">> " + currcontainer.getName() + " opens");
							if(currcontainer.items.size() == 0) System.out.println(">> " + currcontainer.getName() + " is empty");
							else 
								System.out.println(">> You found " + currcontainer.items.toString() + " in the " + obj);
								litr = currcontainer.items.iterator();
								while(litr.hasNext()) curr.addItem(litr.next());
								currcontainer.items.clear();
								cList.clear();
							
							break;
						}
					//}
					}
					if(!citr.hasNext()){
						System.out.println(">> " + "Cannot open " + obj);
						cList.clear();
					}
					}
						
					
				}else if(input.startsWith("read")){
					if(input.length() > 4){
					obj = input.substring(5);
					if(inventory.contains(obj))
					{
						curritem = (Item)ZorkMap.get("item"+obj);
						System.out.println(">> " + curritem.getWriting());
					}
					else{
						System.out.println(">> Cannot read " + obj);
					}
					}
				}else if(input.startsWith("drop")){
					if(input.length() > 4){
					obj = input.substring(5);
					if(inventory.contains(obj))
					{
						curr.addItem(obj);
						inventory.remove(obj);
						System.out.println(">> Item " + obj + " dropped at " + curr.getName());
						curritem = (Item)ZorkMap.get("item"+obj);
						
					}
					else{
						System.out.println(">> You do not have " + obj + " to drop.");
					}
					}
				}else if(input.startsWith("put")){
					if(input.split(" ").length >= 4){
					obj = input.split(" ")[1];
					String container = input.split(" ")[3];
					if(ZorkMap.containsKey("container" + container)){
					   currcontainer = (Container)ZorkMap.get("container" + input.split(" ")[3]);
					
					if(inventory.contains(obj))
					{
						if(currcontainer.checkAccept(obj)){
						currcontainer.addItem(obj);
						inventory.remove(obj);
						System.out.println(">> " + obj + " put into " + currcontainer.getName());
						}else{
							System.out.println(">> " + currcontainer.getName() + " does not accept " + obj); 
						}
						
					}
					else{
						System.out.println(">> You do not have " + obj + " to put into " + currcontainer.getName());
					}
					}else{
						System.out.println(">> No such container: "+ container);
					}
					}
				}else if(input.startsWith("turn on")){
					if(input.length() > 7){
						obj = input.substring(8);		
						if(inventory.contains(obj))
						{
							curritem = (Item) ZorkMap.get("item" + obj);
							String cmd = curritem.turnOn();
							if(cmd == null){
								System.out.println(">> Cannot turn on " + obj);
							}else{
								System.out.println(">> You activate the " + obj);
								command.executeCmd(cmd, ZorkMap,curr,rooms,contextChanged);
								//System.out.println("Trigger: "+ curritem.trigger);//print trigger
								//if(triggers.containsKey(curritem.trigger)){
								for(Trigger mytri: triggers.keySet()){
									if(obj.equals(triggers.get(mytri)))
									 	//System.out.println("Trigger: "+ mytri);
									command.checkTriggers(curritem.getName(),mytri,ZorkMap,inventory,curr,rooms,contextChanged);
								}
								//}
								
							}
						}
						else{
							System.out.println(">> You do not have " + obj + " to turn on.");
						}
						
					
				  }
				}else if(input.equals("look")){
					System.out.println(">> " + curr.getDescription());
				}
				else if(input.startsWith("attack")){
					if(input.split(" ").length >= 4){
						obj = input.split(" ")[1];
						String item = input.split(" ")[3];
						if(inventory.contains(item)){
						if(ZorkMap.keySet().contains("creature" + obj) && curr.creatures.contains(obj)){
							Creature creature = (Creature) ZorkMap.get("creature" + obj);
							if(creature.getVulnerability().contains(item)){
								Attack attk = creature.getAttack();
								Conditions cond = attk.getConditions();
								if(command.CheckCondition(cond, inventory, ZorkMap)){
									System.out.println(">> You assulted " + obj + " with " + item + "!");
									System.out.println(">> " + attk.getPrint());
									for(String cmd: attk.getAction()){
										command.executeCmd(cmd, ZorkMap,curr,rooms,contextChanged);
									}
									//curr.creatures.remove(obj);
									//inventory.remove(item);
								}else{
									System.out.println(">> Conditions not met to complete the mission.");
								}
							}else{
								System.out.println(">> " + obj + " is not vunerable to " + item);
							}
						}else{
							System.out.println(">> You cannot attack " + obj + ".");
						}
						}else{
							if(item.equals("hand") || item.equals("face") ) System.out.println(">> Seriously?");
							else System.out.println(">> You do not have "+ item + ".");
						}
					
					}else{
						System.out.println(">> Error.");
					}
				}else{
					System.out.println(">> Error.");
				}
				
				for(Trigger myTrigger: triggers.keySet()){
					command.checkTriggers(input,myTrigger,ZorkMap,inventory,curr,rooms,contextChanged);
				}
				}
				}
			 }
			
			
		} catch (Exception e) {
			System.out.println("Exception occurred!");
			e.printStackTrace();
		}
		
		
	}

}
