package telnet;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import lib.log4j.MyLogger;
import telnet.commandlist.Command;
import telnet.connection.CommandWorker;
import telnet.connection.SetupTelnetConnection;
import telnet.connection.TelnetConnection;
import telnet.connection.TelnetStatus;

public class TelnetMain {
	private TelnetStatus telnetStatus = new TelnetStatus();
	public static final String PROMPT = ">";

	private ExecutorService executorServiceStartup = Executors.newSingleThreadExecutor();
	private ExecutorService executorServiceCommandsWorker = Executors.newSingleThreadExecutor();
	private ExecutorService executorServiceSendCommand = Executors.newSingleThreadExecutor();
	

	public void close() {
		executorServiceStartup.shutdownNow();
		executorServiceCommandsWorker.shutdownNow();
		executorServiceSendCommand.shutdownNow();
	}

	public TelnetStatus getTelnetStatus() {
		return telnetStatus;
	}

	public TelnetMain(String address, int port) {

		
		// Part responsible for setup telnet connection
		executorServiceStartup.execute(new SetupTelnetConnection(
				getTelnetStatus(),
				address, 
				port)
				);
		
		MyLogger.log.info("Waiting to connect");
		executorServiceStartup.shutdown();
		MyLogger.log.debug("Wait to terminate all telnet setup tasks");
		while (!executorServiceStartup.isTerminated()) {
			MyLogger.log.debug(".");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				this.close();
			}
		}
		
		
		// Part responsible for start command queue worker 
		executorServiceCommandsWorker.execute(new CommandWorker(getTelnetStatus()));
		MyLogger.log.info("Starting command queue worker");
		executorServiceCommandsWorker.shutdown();
				


	}

	public void sendCommand(final String command) {
		// This add only given to the command list. But the real execution is
		// done in SetupTelnetConnection
		//

		final Command commandObj = getTelnetStatus().setCommandToList(command);

		Future<String> future = executorServiceSendCommand.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// Command is added to ArrayList which is read in other Thread.
				String output;
				do {
					synchronized (commandObj) {
						try {
							// Wait read command output until in main thread
							// command will be send
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

		// TODO: After read commandObj, You can remove it from list
		try {
			MyLogger.log.debug("My output: " + future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorServiceSendCommand.shutdown();
		getTelnetStatus().removeCommand(commandObj);
	}

	public void disconnect() {
		this.close();
		try {
			TelnetConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MyLogger.log.info("Goodbay!Tschuss");
	}

	public static void main(String[] args) {

		try {

			final TelnetMain telnet = new TelnetMain("192.168.1.20", 2001);

			for (int i = 0; i < 1 * 1; i++) {
				final String j = Integer.toString(i);

				new Thread(new Runnable() {
					@Override
					public void run() {
						telnet.sendCommand("01 " + j);
					}
				}).start();
			}

			telnet.sendCommand("04 00");
			telnet.sendCommand("01 00");
			telnet.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}