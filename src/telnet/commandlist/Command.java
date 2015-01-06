package telnet.commandlist;




public class Command  {

	private String command;
	private String output = null;
	private CommandPriority priority = CommandPriority.MEDIUM; 
	
			
	public Command(String command, CommandPriority priority){
		this(command);
		if (!priority.equals(null)){
			this.priority = priority;
		}
	}
	
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
		return "\nCommand: "+ command + "\n\tOutput: " + output;
	}

	public CommandPriority getPriority() {
		return priority;
	}

	public void setPriority(CommandPriority priority) {
		this.priority = priority;
	}
	
}
