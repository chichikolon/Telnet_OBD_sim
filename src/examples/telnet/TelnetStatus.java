package examples.telnet;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TelnetStatus extends CommandList {
    private TelnetConnection telnetConnection;
	private InputStream inputStream;
	private PrintStream printStream;
	
    public TelnetConnection getTelentInstance() {
        return this.telnetConnection;
    }
 
    public void setTelnetInstace(TelnetConnection instance) {
        this.telnetConnection = instance;
    }
    
    public boolean isConnected(){
    	return TelnetConnection.isConnected();
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
