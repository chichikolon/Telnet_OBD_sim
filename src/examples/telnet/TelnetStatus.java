package examples.telnet;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TelnetStatus {
    private TelnetConnection telnetConnection;
	private InputStream inputStream;
	private PrintStream printStream;
	
	private List<Command> commandList = new ArrayList<Command >();
    
	public List<Command> getCommandList(){
		return this.commandList;
	}
	 
	
	public Command setCommandToList(String command){
		Command commandObj = new Command(command);
		this.commandList.add(commandObj);
		return commandObj;
	}
	
	public Command getCommandByName(String command){
		for(Command commandFromList: this.commandList){
			if (commandFromList.equals(command)){
				return commandFromList; 
			}
		}
		return null;
	}
	
	
    public TelnetConnection getTelentInstance() {
        return this.telnetConnection;
    }
 
    public void setTelnetInstace(TelnetConnection instance) {
        this.telnetConnection = instance;
    }
    
    public boolean isConnected(){
    	return this.telnetConnection.isConnected();
    }

	public void setInputStream(InputStream in) {
		// TODO Auto-generated method stub
		this.inputStream = in;
	}

	public void setPrinterStream(PrintStream out) {
		// TODO Auto-generated method stub
		this.printStream = out;
	}
	
	public InputStream getInputStream(){
		return this.inputStream;
	}
	
	public PrintStream getPrinterStream(){
		return this.printStream;
	}
	
	
	public void printConnection(){
		System.out.println("IsConnected: " + isConnected() );
	}
	
	
	
	
 
}
