package examples.telnet;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;


public class TelnetWrite {
	private volatile static TelnetWrite INSTANCE;
	private PrintStream outputStream;
	
	private TelnetWrite(TelnetConnection telnetConnection){
		// Get input and output stream references
		if (TelnetConnection.isConnected()){
			outputStream = new PrintStream(telnetConnection.getOutputStream());
		}
		
	}
	
	public static PrintStream getOutputStream(TelnetConnection telnetConnection)
	{
    	if(INSTANCE == null){
            synchronized(TelnetRead.class){
                //double checking Singleton instance
                if(INSTANCE == null){
                    INSTANCE = new TelnetWrite(telnetConnection);
                }
            }
         }
         return INSTANCE.outputStream;
    }
	public static void write(String value) {
		
		
		PrintStream out = INSTANCE.outputStream;
		try {
			out.println(value);
			out.flush();
//			System.out.println(value);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
