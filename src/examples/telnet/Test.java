package examples.telnet;

public class Test {

	
	public static void main(String[] args) {
		
		int i = 5;
		
		if (true || i>5){
			System.out.println("Jestem");
		}
		
		
		do{
			i--;
		} while (true || i>=0);
		System.out.println("Koniec i=" + i);
		
		
	}
}
