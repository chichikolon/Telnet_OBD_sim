package telnet.connection;

import java.io.InputStream;

import lib.log4j.MyLogger;

class TelnetRead {
	private static TelnetRead INSTANCE;
	private InputStream inputStream;
	
	private TelnetRead(TelnetConnection telnetConnection){
		// Get input and output stream references
		if (TelnetConnection.isConnected()){
			inputStream = telnetConnection.getInputStream();
		}
		
	}
	
	static InputStream getInputStream(TelnetConnection telnetConnection)
	{
    	if(INSTANCE == null) INSTANCE = new TelnetRead(telnetConnection);
        return INSTANCE.inputStream;
    }
	
	
	private static InputStream getInputStream() throws Exception
	{
    	if(INSTANCE != null) return INSTANCE.inputStream;
    	else throw new Exception("TelnetRead instance is empty. Try to run getInputStream(TelnetConnection telnetConnection)");
    }
	
	static String readUntil(String pattern) {
		
		InputStream in = null;
		try {
			in = getInputStream();
		} catch (Exception f) {
			MyLogger.log.error("Before run  readUntil execute TelnetRead with getInputStream(TelnetConnection telnetConnection)");
			f.printStackTrace();
			return "";
			
		}
		
		
		try {
			
			int leng = pattern.length();
			leng -=1;
			char lastChar = pattern.charAt(leng);
			StringBuffer sb = new StringBuffer();
			char ch = (char) in.read();
			while (true) {
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
