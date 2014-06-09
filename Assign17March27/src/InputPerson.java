import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;


public class InputPerson {

	/**
	 * This class expects one param which is a filename. Use Open Run 
	 * Dialog to specify the paramater.
	 * @param args
	 */
	public static void main(String [] args){
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please enter a name:");
		String name = keyboard.nextLine();
		System.out.print("Please enter an age:");
		int age = 0;
		try{
			age = keyboard.nextInt();
			keyboard.nextLine();
	}
	catch (InputMismatchException e) {
		System.out.println("Sorry that is not an int, aborting");
		System.exit(0);
	}
		System.out.print("Please enter a file:");
		String fileName = keyboard.nextLine();
		Person p = new Person(name,age);
		//NOTE: Write p to fileName using Object IO: This is what you must do
		try {
			ObjectOutputStream  no = new ObjectOutputStream(new FileOutputStream(fileName));
			no.writeObject(p);
			no.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
