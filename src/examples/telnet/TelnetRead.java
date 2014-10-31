package examples.telnet;

import java.io.InputStream;

import org.apache.commons.net.telnet.TelnetClient;


public class TelnetRead {
	private volatile static TelnetRead INSTANCE;
	private InputStream inputStream;
	
	private TelnetRead(TelnetConnection telnetConnection){
		// Get input and output stream references
		if (TelnetConnection.isConnected()){
			inputStream = telnetConnection.getInputStream();
		}
		
	}
	
	public static InputStream getInputStream(TelnetConnection telnetConnection)
	{
    	if(INSTANCE == null){
            synchronized(TelnetRead.class){
                //double checking Singleton instance
                if(INSTANCE == null){
                    INSTANCE = new TelnetRead(telnetConnection);
                }
            }
         }
         return INSTANCE.inputStream;
    }
	public static String readUntil(String pattern) {
		
		InputStream in = INSTANCE.inputStream;
		
		
		try {
			
			int leng = pattern.length();
			leng -=1;
			char lastChar = pattern.charAt(leng);
			StringBuffer sb = new StringBuffer();
			char ch = (char) in.read();
			while (true) {
//				System.out.print(ch);
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
