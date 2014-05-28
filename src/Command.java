import java.util.Iterator;
import java.util.List;
import java.util.Map;


class Command {
	public Command(){}
	
	public boolean checkTriggers(String cmd,Trigger tr,Map<String, ZorkObject> ZorkMap,List<String> inventory,Room curr,List<Room> rooms,boolean contextChanged){
		if(tr != null){
			//System.out.println("Trigger: "+tr+" "+tr.getType()+" "+tr.getCondition().getObject());
		Conditions condition;
		List<String> actions;
			condition = tr.getCondition();
			actions = tr.getActions();
			if((tr.getType().equals("single")) ){
				if(!(tr.checkTrigger())){
				if((!tr.getCommand().equals("")) && cmd.equals(tr.getCommand()) || tr.getCommand().equals("")){
				if(CheckCondition(condition, inventory,ZorkMap)){
					tr.setTrigger(true);
					for( String prt: tr.getPrint()){
					System.out.println(">> "+prt);
					}
					//System.out.println(">> triggered: "+ tr.getType() +tr.checkTrigger());
					for(String action: actions){
						 executeCmd(action,ZorkMap,curr,rooms,contextChanged);
					}
					return true;
				}
				}
				}
			}else {
				if((!tr.getCommand().equals("")) && cmd.equals(tr.getCommand())){
					if(CheckCondition(condition, inventory, ZorkMap)){
						tr.setTrigger(true);
						System.out.println(">> "+tr.getPrint());
						//System.out.println(">> triggered: "+ tr.getType() + tr.checkTrigger()+tr.getCommand());
						for(String action: actions){
							executeCmd(action,ZorkMap,curr,rooms,contextChanged);	 
						}
						return true;
					}else tr.setTrigger(false);	
				}else if(tr.getCommand().equals("")){			
					if(CheckCondition(condition, inventory, ZorkMap)){
						tr.setTrigger(true);
						System.out.println(">> "+tr.getPrint());
						//System.out.println(">> triggered:"+tr.checkTrigger());
						for(String action: actions){
							executeCmd(action,ZorkMap,curr,rooms,contextChanged);
						}
						return true;
						}else tr.setTrigger(false);			
				}
				
			}
		}
			
		return false;
	}

	public void executeCmd(String cmd, Map<String, ZorkObject> ZorkMap,Room curr,List<Room> rooms,boolean contextChanged){
		String object;
		String input = cmd;
		Iterator<Room> itr =rooms.iterator();
		Iterator<Border> bitr = curr.getBorder().iterator();
		Border border = null;
		Room tmp = null;
		
		//Add obj to container
		if(cmd.startsWith("Add")){
			object = cmd.split(" ")[1];
			String container = cmd.split(" ")[3];
			if(ZorkMap.keySet().contains("room"+container)){
				//System.out.println(ZorkMap.get("item"+object).getName());
				//Add(object,ZorkMap.get("room" + container));
				Room room = (Room) ZorkMap.get("room" + container);
				room.addItem(object);
				System.out.println(">> "+object+" added to "+container);
			}else if(ZorkMap.keySet().contains("container"+container)){
				//Add(object, ZorkMap.get("container" + container));
				Container cont = (Container) ZorkMap.get("room" + container);
				cont.addItem(object);
				System.out.println(">> "+object+" added to "+container);
			}else{
				System.out.println("Error occurred while adding " + object + " to " + container);
			}
			
		}
		//Delete obj
		else if(cmd.startsWith("Delete")){
			if(cmd.split(" ").length >= 2){
				object = cmd.split(" ")[1];
				Delete(object, ZorkMap);
			}
			
		}
		//Update obj to status
		else if(cmd.startsWith("Update")){
			object = cmd.split(" ")[1];
			String status = cmd.split(" ")[3];
			Update(object, status,ZorkMap);
		}else if(input.equals("n")){
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
		}if(input.equals("e") ){
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
		
		
	}
	
	public void Add(String obj,ZorkObject dest){	
		dest.items.add(obj);
	}
	public void Delete(String obj,Map<String,ZorkObject> ZorkMap){
		for(String key: ZorkMap.keySet()){
			ZorkObject curr = ZorkMap.get(key);
			if(curr.getType().equals("room")){
				Room currRoom = (Room) curr;
				if(currRoom.containers.contains(obj)){
					System.out.println(obj + "deleted");
					currRoom.containers.remove(obj);
				}
				if(currRoom.items.contains(obj)){
					System.out.println(obj + "deleted");
					currRoom.items.remove(obj);
				}
				if(currRoom.creatures.contains(obj)){
					System.out.println(obj + "deleted");
					currRoom.creatures.remove(obj);
				}
				else {
				  for(Border b: currRoom.getBorder()){
					if(b.getName().equals(obj)){
						currRoom.DeleteBorder(obj);
					}
				  }
				}
			}else if(curr.getType().equals("container")){
				Container container = (Container)curr;
				if(container.items.contains(obj)){
					System.out.println(obj + "deleted");
					container.items.remove(obj);
				}
			}
		}
	}
    public void Update(String obj, String status, Map<String,ZorkObject> ZorkMap){
    	ZorkObject curr;
    	for(String key: ZorkMap.keySet()){
			curr = ZorkMap.get(key);
			if(curr.getName().equals(obj)){
				curr.setStatus(status);
			}
    	}
    }
    public void GameOver(){
    	System.out.println(">> Victory !!!!");
    }
    
    public boolean CheckCondition(Conditions condition, List<String> inventory, Map<String,ZorkObject> ZorkMap){
    	if(condition != null && condition.getOwner() != null && condition.getHas() != null){
    		String obj = condition.getOwner();
    		//System.out.println(" owner: " + obj);
			String item = condition.getObject();
			//System.out.println(" object: " + item);
			
			if(obj.equals("inventory") ){
				if(condition.getHas().equals("no") && inventory.contains(item)){
    			return false;
				}else{
					return true;
				}
    		}
			
    		for(ZorkObject curr: ZorkMap.values()){
    			if(curr.getName().equals(obj)){
    				if(curr.getType().equals("room")){
    					Room currRoom = (Room) curr;
    					if((!currRoom.containers.contains(item)) && condition.getHas().equals("yes")){
    						return false;
    					}else if((!currRoom.items.contains(item)) && condition.getHas().equals("yes")){
    						return false;
    					}else{
    						return true;
    					}
    				}else if(curr.getType().equals("container")){
    					Container container = (Container)curr;
    					if(!container.items.contains(item) && condition.getHas().equals("yes")){
    						return false;
    					}else{
    						return true;
    					}
    				}
    			}
    		}
    	}else if(condition != null  && condition.getObject() != null && condition.getStatus() != null){
    		String obj = condition.getObject();
    		String status = condition.getStatus();
    		for(ZorkObject curr: ZorkMap.values()){
    			if(curr.getName().equals(obj)){
    				if(!curr.getStatus().equals(status)){
    					return false;
    				}
    			}
    		}
    	}
    	return true;
    }
}
