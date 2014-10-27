package examples.telnet;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.management.InstanceAlreadyExistsException;

import org.apache.commons.net.telnet.TelnetClient;
import org.omg.CORBA.TIMEOUT;




public class MyTelnet
{
	
	private volatile static MyTelnet INSTANCE;
    
    private final TelnetClient telnetClient = new TelnetClient();

    private String mAddress;
    private int mPort;
    
    
    private MyTelnet(){};
    
    private MyTelnet(String address, int port)
    {
        // Initialize the connection
        int TIMEOUT = 3;
    	telnetClient.setConnectTimeout(TIMEOUT);
        
    	this.mAddress = address;
        this.mPort = port;
    	
    	if(!telnetClient.isConnected()){
	    	do{
	    		try {
					telnetClient.connect(this.mAddress, this.mPort);
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						Thread.sleep(1000);
					} catch (InterruptedException b) {
						// TODO Auto-generated catch block
						b.printStackTrace();
						break;
					}
		    		TIMEOUT--;
	    		}
	    	} while (!telnetClient.isConnected() && TIMEOUT>=0);
    	}
    }

    // Static getter
    public static TelnetClient getConnection(String address, int port)
    {
    	if(INSTANCE == null){
            synchronized(MyTelnet.class){
                //double checking Singleton instance
                if(INSTANCE == null){
                    INSTANCE = new MyTelnet(address, port);
                }
            }
         }
         return INSTANCE.telnetClient;
    }
    
    public static TelnetClient getConnection()
    {
    	if(INSTANCE == null){
    		throw new RuntimeException("Unable to connect. Start with getConnection(String address, int port)");
    		
    	}
    	
    	if(!INSTANCE.telnetClient.isConnected()){
    		throw new RuntimeException("Unable to connect. Start with getConnection(String address, int port)");
    	}
        return INSTANCE.telnetClient;
    }
    
    
}
