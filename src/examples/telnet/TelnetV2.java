package examples.telnet;


import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetV2{
//	private TelnetClient telnet = new TelnetClient();
	private static TelnetStatus telnetStatus = new TelnetStatus();
	public static final String PROMPT = ">";

	
	
	
	
	public TelnetV2(String address, int port) {
		
		SetupTelnetConnection setupTelnetConnection =  
				new SetupTelnetConnection(telnetStatus,
				address,
				port);
		
		
		Thread connectThread = new Thread(setupTelnetConnection);
		connectThread.start();
	}
	
	public void sendCommand(final String command) {
		SetupReadWrite setupReadWrite = 
				new SetupReadWrite(telnetStatus, command);
		Thread sendThread = new Thread (setupReadWrite);
		sendThread.start();
		
	}
		
		
//	public void disconnect() {
//		if (telnet.isConnected()){
//			try {
//				telnet.disconnect();
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public static void main(String[] args) {
		try {
			
			TelnetV2 telnet = new TelnetV2("192.168.1.20", 2001);			
//			telnet.sendCommand("?");

			telnet.sendCommand("04 00");
			telnet.sendCommand("01 00");
//			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}