package telnet.connection;

import lib.log4j.MyLogger;
import telnet.TelnetMain;

class TelnetSendCommand {
	
	static String sendCommand(String command){
		String output = "";
		
		try {
			MyLogger.log.info("command: "+ command);
			TelnetWrite.write(command);
			do {
				output =  TelnetRead.readUntil(TelnetMain.PROMPT);
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

