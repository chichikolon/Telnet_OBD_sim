package telnet.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lib.log4j.MyLogger;
import lib.org.apache.commons.net.telnet.TelnetClient;




public class TelnetConnection
{
	
	private static TelnetConnection INSTANCE;
    
    private final TelnetClient telnetClient = new TelnetClient();

    private String mAddress;
    private int mPort;
    
    private TelnetConnection(String address, int port)
    {
        // Initialize the connection
        int TIMEOUT = 5;
    	telnetClient.setConnectTimeout(TIMEOUT);
        
    	this.mAddress = address;
        this.mPort = port;
    	
    	if(!telnetClient.isConnected()){
	    	do{
	    		try {
	    			MyLogger.log.info(".");
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
		    		TIMEOUT-=1;
	    		}
	    	} while (!telnetClient.isConnected() && TIMEOUT>=0);
    	}
    }

    // Static getter
    public static TelnetClient getConnection(String address, int port)
    //Get current telnet connection OR make new one
    {
    	if(getInstance() == null){
            synchronized(TelnetConnection.class){
                //double checking Singleton instance
                if(getInstance() == null){
                    setInstance(new TelnetConnection(address, port));
                }
            }
         }
         return getInstance().telnetClient;
    }
    
    public static TelnetClient getConnection()
    {
    	if(getInstance() == null){
    		throw new RuntimeException("Unable to connect. Start with getConnection(String address, int port)");
    		
    	}
    	
    	if(!getInstance().telnetClient.isConnected()){
    		throw new RuntimeException("Unable to connect. Start with getConnection(String address, int port)");
    	}
        return getInstance().telnetClient;
    }

    public static boolean isConnected(){
    	return getInstance().telnetClient.isConnected();
    }
    
    public static void disconnect(){
    	try {
			getConnection().disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return getInstance().telnetClient.getInputStream();
	}
	
	
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return getInstance().telnetClient.getOutputStream();
	}

	public synchronized static void setInstance(TelnetConnection instance) {
		// TODO Auto-generated method stub
		INSTANCE = instance;
	}
	
	public synchronized static TelnetConnection getInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}
    
    
}
