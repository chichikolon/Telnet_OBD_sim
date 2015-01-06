package telnet.connection;


import java.io.InputStream;
import java.io.PrintStream;

import telnet.commandlist.CommandList;

public class TelnetStatus extends CommandList {
    private TelnetConnection telnetConnection;
	private InputStream inputStream;
	private PrintStream printStream;
	
    public synchronized TelnetConnection getTelentInstance() {
        return this.telnetConnection;
    }
 
    public synchronized void setTelnetInstace(TelnetConnection instance) {
        this.telnetConnection = instance;
    }
    
    public boolean isConnected(){
    	return getTelentInstance().isConnected();
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
		System.out.println("IsConnected: " + this.isConnected() );
	}
	
	
	
	
 
}
