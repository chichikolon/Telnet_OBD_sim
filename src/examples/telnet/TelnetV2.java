package examples.telnet;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
		
		//
		
		final Command commandObj = telnetStatus.setCommandToList(command);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future future = executorService.submit(new Callable(){
		    public Object call() throws Exception {
		    	//Command is added to ArrayList which is read in other Thread. 
				
				String output;
				do {
					synchronized(commandObj){
						try {
							//Wait read command output until in main thread command will be send 
							commandObj.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						output = commandObj.getOutput();
					}
				} while (output == null);
				return output;
		    }
		});
		
		//TODO: After read this commandObj You can remove it from list
		try {
			System.out.println("Moj output: "+ future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		telnetStatus.removeCommand(commandObj);

		
//		do {
//			synchronized(commandObj){
//				try {
//					//Wait read command output until in main thread command will be send 
//					commandObj.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				output = commandObj.getOutput();
//			}
//		} while (output == null);
//		//TODO: After read this commandObj You can remove it from list
//		System.out.println("Moj output: "+ output);
//		telnetStatus.removeCommand(commandObj);
//		
		
		
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
			
			final TelnetV2 telnet = new TelnetV2("192.168.1.20", 2001);			
//			telnet.sendCommand("?");

			
			
//			Thread[] listThread = new Thread[20];
			for (int i = 0; i<20; i++){
				final String j = ""+i;
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						telnet.sendCommand("01 "+j );
					}
				}, j).start();
			}

			telnet.sendCommand("04 00");
			telnet.sendCommand("01 00");
//			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}