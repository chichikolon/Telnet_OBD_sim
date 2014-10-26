package examples.telnet;


import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.PrintStream;

public class TelnetV2{
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String prompt = ">";
	private String pattern;

	public TelnetV2(String server) {
		try {
			// Connect to the specified server
			telnet.connect(server, 2001);
			
			// Get input and output stream references
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			
			// Advance to a prompt
			readUntil("ser2net port 2001 device /home/pi/dev/pts_out [9600 N81] (Debian GNU/Linux)");
			sendCommand("?");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String readUntil(String pattern) {
		try {
			
			int leng = pattern.length();
			leng -=1;
			char lastChar = pattern.charAt(leng);
			StringBuffer sb = new StringBuffer();
			boolean found = false;
			char ch = (char) in.read();
			while (true) {
				System.out.print(ch);
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void write(String value) {
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public String sendCommand(String command) {
		try {
			write(command);
			return readUntil(prompt + "");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	public void disconnect() {
		try {
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			TelnetV2 telnet = new TelnetV2("192.168.1.20");			
			telnet.sendCommand("AT Z");
			telnet.sendCommand("01 00");
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}