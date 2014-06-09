import java.io.FileOutputStream;
import java.io.IOException;


public class ReceiverUser {

	public static void main(String[] args) throws IOException {
	
		// Instantiate using the default port
		SelectiveRepeatReceiver receiver = new  SelectiveRepeatReceiver( );
		
		// Open a file to write to
		FileOutputStream fos = 
			new FileOutputStream("copy of BattingChampions.txt");
		
		// Receive the initial data
		byte data[] = receiver.rdt_receive();
		
		// Loop until all data has been received
		while(data != null) { 
			
			fos.write(data); // Write data to a file 
			
			data = receiver.rdt_receive(); // Receive data
		}
		
		// Close the receiver
		receiver.close();
		
		// Close the data file
		fos.close();
	
	} // end main

} // end ReceiverUser class
