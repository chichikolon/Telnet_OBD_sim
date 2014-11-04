package examples.telnet;


import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetV2{
//	private TelnetClient telnet = new TelnetClient();
	private static TelnetStatus telnetStatus = new TelnetStatus();
	public static final String PROMPT = ">";

	
	
	
	
	public TelnetV2(String address, int port) {
		
		SetupTelnetConnection telnetConnection =  
				new SetupTelnetConnection(telnetStatus,
				address,
				port);
		
		
		Thread connectThread = new Thread(telnetConnection);
		connectThread.start();
	}
	
	public void sendCommand(final String command) {
		
		Command commandObj = telnetStatus.setCommandToList(command);
		
		String output;
		do {
			synchronized(commandObj){
				try {
					commandObj.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output = commandObj.getOutput();
			}
		} while (output == null);
		//TODO: After read this commandObj You can remove it from list
		System.out.println("Moj output: "+ output);
		telnetStatus.removeCommand(commandObj);
		
		
		
//		SetupReadWrite setupReadWrite = 
//				new SetupReadWrite(telnetStatus, command);
//		Thread sendThread = new Thread (setupReadWrite);
//		sendThread.start();
		
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