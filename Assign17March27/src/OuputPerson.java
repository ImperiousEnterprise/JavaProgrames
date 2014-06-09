import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.InputMismatchException;
import java.util.Scanner;


public class OuputPerson {

	/**
	 * This class expects one param which is a filename. Use Open Run 
	 * Dialog to specify the paramater.
	 * @param args
	 */
	public static void main(String [] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please enter a file:");
		String fileName = keyboard.nextLine();
		//NOTE: Read a person from fileName using Object IO: 
		//and output this to the console
		//This is what you must do
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(
					new FileInputStream(fileName));			
			 System.out.println(ois.readObject());
			 
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
}
