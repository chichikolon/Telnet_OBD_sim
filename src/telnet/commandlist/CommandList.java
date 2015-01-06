package telnet.commandlist;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class CommandList extends PriorityBlockingQueue<Command> implements Comparator<Command>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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


	@Override
	public int compare(Command c1, Command c2) {
		// TODO Auto-generated method stub
		return c1.getPriority().getPriorityValue() - c2.getPriority().getPriorityValue();
	}
	
	
	public static void main(String[] args) {
		CommandList commandList = new CommandList();
		commandList.add(null);
	}
	
	
}
