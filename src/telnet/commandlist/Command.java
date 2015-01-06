package telnet.commandlist;

import java.util.Comparator;





public class Command  {

	enum CommandPriority implements Comparable<CommandPriority> {
		CRITICAL(0), HIGHT(1), MEDIUM(2), LOW(3);

		private int priority;

		private CommandPriority(int priority) {
			this.priority = priority;
		}

		public int getPriorityValue() {
			return this.priority;
		}

	}
	
	
	
	
	
	private String command;
	private String output = null;
	private CommandPriority priority = CommandPriority.MEDIUM; 

			
	
			
	public Command(String command){
		this.command = command;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public String getOutput(){
		return this.output;
	}
	
	public void setOutput(String output){
		this.output = output;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Command: "+ command + "\nOutput: " + output;
	}

	public CommandPriority getPriority() {
		return priority;
	}

	public void setPriority(CommandPriority priority) {
		this.priority = priority;
	}
	
//	@Override
//	public boolean equals(Object command) {
//		// TODO Auto-generated method stub
//		return this.getCommand().equals(command);
//		
//	}
	
	
}
