package examples.telnet;


public class Command  {

	private String command;
	private String output;
	
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
	
	@Override
	public boolean equals(Object command) {
		// TODO Auto-generated method stub
		return this.getCommand().equals(command);
		
	}
	
	
}
