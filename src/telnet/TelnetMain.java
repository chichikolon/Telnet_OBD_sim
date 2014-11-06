package telnet;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import telnet.commandlist.Command;
import telnet.connection.SetupTelnetConnection;
import telnet.connection.TelnetConnection;
import telnet.connection.TelnetStatus;
import lib.log4j.MyLogger;


public class TelnetMain{
	private static TelnetStatus telnetStatus = new TelnetStatus();
	public static final String PROMPT = ">";

	
	private Thread connectThread;
	
	
	public void close(){
		connectThread.interrupt();
	}
	
	public TelnetStatus getTelnetStatus() {
		return telnetStatus;
	}

	public void setTelnetStatus(TelnetStatus telnetStatus) {
		TelnetMain.telnetStatus = telnetStatus;
	}
	
	public TelnetMain(String address, int port) {
		
		connectThread = new Thread( 
				new SetupTelnetConnection(
					getTelnetStatus(),
					address,
					port), 
				"Main-Send-CommandToList-Thread");
		connectThread.start();
		
	}
	
	public void sendCommand(final String command) {
		// This add only given to the command list. But the real execution is done in SetupTelnetConnection
		//
		
		final Command commandObj = getTelnetStatus().setCommandToList(command);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(new Callable<String>(){
		    public String call() throws Exception {
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
					
				} while (output == null & getTelnetStatus().isConnected());
				return output;
		    }
		});
		
		//TODO: After read commandObj, You can remove it from list
		try {
			MyLogger.log.debug("My output: "+ future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();
		getTelnetStatus().removeCommand(commandObj);
	}
		
		
	public void disconnect() {
		this.close();
		try {
			TelnetConnection.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		MyLogger.log.info("Goodbay!");
	}
	
	
	
	public static void main(String[] args) {
		
		
		try {
			
			final TelnetMain telnet = new TelnetMain("192.168.1.20", 2001);			

			
			for (int i = 0; i<10*1; i++){
				final String j = Integer.toString(i);
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						telnet.sendCommand("01 "+j );
					}
				}).start();
			}

			telnet.sendCommand("04 00");
			telnet.sendCommand("01 00");
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}