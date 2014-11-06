package telnet.connection;

import java.io.PrintStream;

import lib.log4j.MyLogger;



class TelnetWrite {
	private static TelnetWrite INSTANCE;
	private PrintStream outputStream;
	
	private TelnetWrite(TelnetConnection telnetConnection){
		// Get input and output stream references
		if (TelnetConnection.isConnected()){
			outputStream = new PrintStream(telnetConnection.getOutputStream());
		}
		
	}
	
	static PrintStream getOutputStream(TelnetConnection telnetConnection)
	{
    	if(INSTANCE == null) INSTANCE = new TelnetWrite(telnetConnection);
        return INSTANCE.outputStream;
    }
	
	private static PrintStream getOutputStream() throws Exception
	{
    	if(INSTANCE != null) return INSTANCE.outputStream;
    	else throw new Exception("TelnetWrite instance is empty. Try to run getOutputStream(TelnetConnection telnetConnection)");
    }

	
	static void write(String value) {
		
		PrintStream out = null;
		try {
			out = getOutputStream();
		} catch (Exception f){
			MyLogger.log.error("Before run  write(String) execute TelnetWrite with getOutputStream(TelnetConnection telnetConnection)");
			f.printStackTrace();
			return;
		}
		
		
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
