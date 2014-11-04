package examples.telnet;

import java.util.ArrayList;
import java.util.List;

public class CommandList{

	private List<Command> commandList = new ArrayList<Command >();
    
	public synchronized List<Command> getCommandList(){
		return this.commandList;
	}
	 
	
	public Command setCommandToList(String command){
		Command commandObj = new Command(command);
		this.getCommandList().add(commandObj);
		return commandObj;
	}
	
//	public Command getCommandByName(String command){
//		for(Command commandFromList: this.getCommandList()){
//			if (commandFromList.equals(command)){
//				return commandFromList; 
//			}
//		}
//		return null;
//	}
	
	public void removeCommand(Command command){
		this.commandList.remove(command);
		
		
//		this.getCommandList().remove(command);
	}
	
	
}
