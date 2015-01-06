package telnet.commandlist;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class CommandList extends PriorityBlockingQueue<Command>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BlockingQueue<Command> commandList = new PriorityBlockingQueue<Command>(10, new PriorityQueueBlockingComparator());
    
	
	public BlockingQueue<Command> getCommandList(){
		return this.commandList;
	}
	 
	private void _setCommandToList(Command command){
		try {
			this.getCommandList().put(command);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Command setCommandToList(String command){
		Command commandObj = new Command(command);
		this._setCommandToList(commandObj);
		return commandObj;
	}
	
	
	public Command setCommandToList(String command, CommandPriority priority){
		Command commandObj = new Command(command, priority);
		this._setCommandToList(commandObj);
		return commandObj;
	}
	
	public void removeCommand(Command command){
		
		this.getCommandList().remove(command);
	}

	class PriorityQueueBlockingComparator implements Comparator<Command>{
		@Override
		public int compare(Command c1, Command c2) {
			// TODO Auto-generated method stub
			return c2.getPriority().getPriorityValue() - c1.getPriority().getPriorityValue();
		}
	}
	
//	public static void main(String[] args) {
//		CommandList commandList = new CommandList();
//		commandList.setCommandToList("00", CommandPriority.CRITICAL);
//		commandList.setCommandToList("01", CommandPriority.HIGHT);
//		commandList.setCommandToList("021", CommandPriority.MEDIUM);
//		commandList.setCommandToList("022", CommandPriority.MEDIUM);
//		commandList.setCommandToList("023", CommandPriority.MEDIUM);
//		commandList.setCommandToList("024", CommandPriority.MEDIUM);
//		commandList.setCommandToList("00", CommandPriority.CRITICAL);
//		commandList.setCommandToList("03", CommandPriority.LOW);
//		commandList.setCommandToList("03", CommandPriority.LOW);
//		commandList.setCommandToList("025");
//		commandList.setCommandToList("026");
//		commandList.setCommandToList("027", CommandPriority.MEDIUM);
////		System.out.println(commandList.getCommandList());
//		
//		while (commandList.getCommandList().size()>0){
//			System.out.println(commandList.getCommandList().poll() + " ");
//		}
//		
//	}
	
	
}
