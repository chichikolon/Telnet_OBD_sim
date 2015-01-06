package telnet.connection;

import telnet.commandlist.Command;

public class CommandWorker implements Runnable {

	
	private TelnetStatus telnetStatus;

	public CommandWorker(TelnetStatus telnetStatus) {
		this.telnetStatus = telnetStatus;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		do {
//			System.out.println("telnetStatus.getCommandList(): " + telnetStatus.getCommandList());
			for (Command commandObj: telnetStatus.getCommandList()){
				if (commandObj.getOutput() == null){
					String command = commandObj.getCommand();
					String output = TelnetSendCommand.sendCommand(command);
					synchronized(commandObj){
						commandObj.setOutput(output);
						commandObj.notify();
					}
				} continue;
				
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				break;
			}
		} while (!Thread.currentThread().isInterrupted());

	}

}
