package log4j;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//import org.apache.log4j.PropertyConfigurator;




public class MyLogger{
		
	 /* Get actual class name to be printed on */
	public static Logger log = LogManager.getLogger(Object.class.getName());
	
	
	  public static void main(String[] args){
	     log.info("Starting logger");
	  }	

}
