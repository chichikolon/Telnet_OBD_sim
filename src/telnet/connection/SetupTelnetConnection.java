package telnet.connection;

import java.io.InputStream;
import java.io.PrintStream;

import lib.log4j.MyLogger;

public class SetupTelnetConnection implements Runnable {
	
	String address = "192.168.1.20";
	int port = 2001;
	
	private TelnetStatus telnetStatus;
	
	public SetupTelnetConnection(TelnetStatus telnetStatus, String address, int port) {
		this(telnetStatus);
		if (address != null ){
			this.address = address;
		}
		if (port != 0){
			this.port = port;
		}
		
	}
	
	public SetupTelnetConnection(TelnetStatus telnetStatus) {
		this.telnetStatus = telnetStatus;
	}
	
	@Override
	public void run() {
//		synchronized(telnetStatus){
			try {
				TelnetConnection.getConnection(this.address, this.port);
				telnetStatus.setTelnetInstace(TelnetConnection.getInstance());
				
				if (!telnetStatus.isConnected()){
					MyLogger.log.error("Not connected. Check IP and port adress.");
					System.exit(1);
				}
//					// Get input and output stream references
				
				
				InputStream in = TelnetRead.getInputStream(TelnetConnection.getInstance()); 
				telnetStatus.setInputStream(in);
				PrintStream out = TelnetWrite.getOutputStream(TelnetConnection.getInstance());
				telnetStatus.setPrinterStream(out);
				

				// Advance to a prompt
				TelnetRead.readUntil("ser2net port 2001 device /home/pi/dev/pts_out [9600 N81] (Debian GNU/Linux)");
				
				TelnetSendCommand.sendCommand("?");
				TelnetSendCommand.sendCommand("AT SP 0");
				
//				telnetStatus.notifyAll();
				
			}
			catch (Exception e) {
				e.printStackTrace();
				
//			}	
		}
		

	}
}
		

