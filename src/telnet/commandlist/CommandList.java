package telnet.commandlist;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandList{

	private List<Command> commandList = new CopyOnWriteArrayList<Command>();
    
	public synchronized List<Command> getCommandList(){
		return this.commandList;
	}
	 
	
	public Command setCommandToList(String command){
		Command commandObj = new Command(command);
		this.getCommandList().add(commandObj);
		return commandObj;
	}
	
	public void removeCommand(Command command){
		this.getCommandList().remove(command);
	}
	
	
}
