import java.util.List;
import java.util.Map;


class Command {
	public Command(){}
	
	public boolean checkTriggers(String cmd,Trigger tr,Map<String, ZorkObject> ZorkMap,List<String> inventory){
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
					System.out.println(">> "+tr.getPrint());
					//System.out.println(">> triggered: "+ tr.getType() +tr.checkTrigger());
					for(String action: actions){
						 executeCmd(action,ZorkMap);
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
						 executeCmd(action,ZorkMap);	 
						}
						return true;
					}else tr.setTrigger(false);	
				}else if(tr.getCommand().equals("")){			
					if(CheckCondition(condition, inventory, ZorkMap)){
						tr.setTrigger(true);
						System.out.println(">> "+tr.getPrint());
						//System.out.println(">> triggered:"+tr.checkTrigger());
						for(String action: actions){
						 executeCmd(action,ZorkMap);
						}
						return true;
						}else tr.setTrigger(false);			
				}
				
			}
		}
			
		return false;
	}

	public void executeCmd(String cmd, Map<String, ZorkObject> ZorkMap){
		String object;
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
			object = cmd.substring(8);
			Delete(object, ZorkMap);
		}
		//Update obj to status
		else if(cmd.startsWith("Update")){
			object = cmd.split(" ")[1];
			String status = cmd.split(" ")[3];
			Update(object, status,ZorkMap);
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
					currRoom.containers.remove(obj);
				}else if(currRoom.items.contains(obj)){
					currRoom.items.remove(obj);
				}else if(currRoom.creatures.contains(obj)){
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
				container.items.remove(obj);
			}
		}
	}
    public void Update(String obj, String status, Map<String,ZorkObject> ZorkMap){
    	for(String key: ZorkMap.keySet()){
			ZorkObject curr = ZorkMap.get(key);
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
