package examples.telnet;

public class SetupReadWrite implements Runnable {

	private TelnetStatus telnetStatus;
	private String command;
	
	
	
	
	public SetupReadWrite(TelnetStatus telnetStatus, String command){
		this.telnetStatus = telnetStatus;
		this.command = command;
	}
	
	
	static String sendCommand(String command){
		String output = "";
		
		try {
			System.out.println("command: "+ command);
			TelnetWrite.write(command);
			do {
				output =  TelnetRead.readUntil(TelnetV2.PROMPT);
			} while (output == "");
			output = output.substring(command.length(), output.length()-2);
			output = output.replaceAll("\\s+","");
			System.out.println("output: "+ output);
			
		
		}
			catch (Exception e) {
				e.printStackTrace();
			}
		return output;
	}
	
	@Override
	public void run() {
		synchronized(telnetStatus){
			// TODO Auto-generated method stub
			
			if (!telnetStatus.isConnected()){	
				try {
					System.out.println("waiting to get notified at time:"+System.currentTimeMillis());
	                telnetStatus.wait();
	                
	                
	                while(true){
	                	if (!telnetStatus.isConnected()){
	                	
	                	
		                	Command c = telnetStatus.setCommandToList(command);
		                	String output = this.sendCommand(command);
		                	c.setOutput(output);
	                	}
	                }
	                
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
			
	}
}

