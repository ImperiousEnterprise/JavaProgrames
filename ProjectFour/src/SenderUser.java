import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;


public class SenderUser {

	public static void main(String[] args) throws IOException {

		// Instantiate an RDTSender object to send the packets
		SelectiveRepeatSender sender 
			= new SelectiveRepeatSender ("127.0.01", SelectiveRepeatReceiver.DEFAULT_PORT );

		// Open fiel for reading
		FileInputStream in = new FileInputStream("BattingChampions.txt");
		
		// Create an array to read into
		byte data[] = new byte[SelectiveRepeatSender.MAX_DATA_IN_PACKET];

		// Keep filling the array and sending until there is not enough
		// data left to fill the array
		while( in.available() >= data.length ) {
			
			// Read in the data
			in.read(data);
			// Loop until send accepts the data
			
			while ( false == sender.rdt_send(data));

		}
		
		// Make a smaller array for the remaining data
		data = new byte[in.available()];
		
		// Read in the remaining data
		in.read(data);
		
		// Send it
		while ( false == sender.rdt_send(data));	

		in.close(); // close the data file
		sender.close(); // close the sender

	} // end main

} // end SenderUser class
