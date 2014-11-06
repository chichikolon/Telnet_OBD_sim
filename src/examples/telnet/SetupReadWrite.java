package examples.telnet;

import log4j.MyLogger;

class SetupReadWrite {

//	private TelnetStatus telnetStatus;
//	private String command;
//
//	public TelnetStatus getTelnetStatus() {
//		return telnetStatus;
//	}
//
//
//	public void setTelnetStatus(TelnetStatus telnetStatus) {
//		this.telnetStatus = telnetStatus;
//	}
//	
//	
//	
//	public SetupReadWrite(TelnetStatus telnetStatus, String command){
//		this.setTelnetStatus(telnetStatus);
//		this.command = command;
//	}
	
	
	static String sendCommand(String command){
		String output = "";
		
		try {
			MyLogger.log.info("command: "+ command);
			TelnetWrite.write(command);
			do {
				output =  TelnetRead.readUntil(TelnetV2.PROMPT);
			} while (output == "" & !Thread.currentThread().isInterrupted());
			output = output.substring(command.length(), output.length()-2);
			output = output.replaceAll("\\s+","");
			MyLogger.log.debug("output: "+ output);
		}
			catch (Exception e) {
				e.printStackTrace();
			}
		return output;
	}

}

