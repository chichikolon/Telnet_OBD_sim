package telnet.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
//    	getTelnetClient().setConnectTimeout(TIMEOUT);
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
            synchronized(TelnetConnection.class){
                //double checking Singleton instance
                if(INSTANCE == null){
                    INSTANCE = new TelnetConnection(address, port);
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

    public static boolean isConnected(){
    	return INSTANCE.telnetClient.isConnected();
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
		return INSTANCE.telnetClient.getInputStream();
	}
	
	
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return INSTANCE.telnetClient.getOutputStream();
	}

	public static TelnetConnection getInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}
    
    
}
