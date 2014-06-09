import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedList;





/**
 * Implementation of the  selective repeat protocol from "Computer 
 * Networking" by James F. Kurose and Keith Ross.
 * 
 */
public class SelectiveRepeatReceiver {

	/**
	 * Default port number for receiving packets from a Rdt3pt0Sender Object 
	 */
	public static final int DEFAULT_PORT = 32100;
	
	public static final int PACKET_SIZE = SelectiveRepeatSender.MAX_DATA_IN_PACKET + SelectiveRepeatSender.NUMBER_OF_OVERHEAD_BYTES;
	
	// Only IP address and port number from which data will be accepted
	InetSocketAddress destination;
	
	// Private or protected data members
	DatagramSocket dgSock;
	
	// Indicates the sequence number expected in the next packet
	int expectedSequence = 0;
	
	// Private or protected data members
	// TODO
	byte[] buffer = new byte[1000];
	int x = 0;
	int counter =0;
	int previous= 0;
	HashMap<Integer, byte[]> oh = new HashMap<Integer, byte[]>();
	
	int lastPacketSequence = -1;
	
	/**
	 * Creates a "reliable connection" using the default port number 
	 * port number. Objects of this class are used to 
	 * receive data using the selective repeat protocol.
	 *  
	 * @param serverPort port number for the server to listen on
	 * @throws SocketException 
	 */
	public SelectiveRepeatReceiver ( ) throws SocketException
	{
		this( DEFAULT_PORT );
	
	} // end default constructor
	
	
	/**
	 * Creates a "reliable conection" using a specified 
	 * port number. Objects of this class are used to 
	 * receive data using the  selective repeat protocol.
	 *  
	 * @param serverPort port number for the server to listen on
	 * @throws SocketException 
	 * 
	 */
	public SelectiveRepeatReceiver ( int serverPort ) throws SocketException
	{ 
		dgSock = new UDatagramSocket( serverPort );
		
	} // end Rdt3pt0Receiver	
	
	
	/**
	 * Returns received data via a byte array. When the method returns 
	 * the array will contain the received data. The byte 
	 * array will be null when the connection is closed by the 
	 * the sender.
	 * 
	 * @return data or null if the connection is closed.
	 * @throws IOException 
	 */
	public byte[] rdt_receive() throws IOException
	{ 
		if( oh.containsKey(Integer.valueOf(expectedSequence))){
			
			byte[] data = oh.get(Integer.valueOf(expectedSequence));
			oh.remove((Integer.valueOf(expectedSequence)));
			System.out.println("Already there");
			expectedSequence += data.length;
			
			return data;
			
			
		}
		
		
		
		while(true){
			// TODO
			DatagramPacket receivePacket = new DatagramPacket( new byte[PACKET_SIZE], PACKET_SIZE);
			dgSock.receive(receivePacket);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
			DataInputStream dis = new DataInputStream( bais );
			 
			
			int bytesReceived = receivePacket.getLength() - SelectiveRepeatSender.NUMBER_OF_OVERHEAD_BYTES;
			System.out.println("Byte Received: " + bytesReceived + " " + counter + " #" + receivePacket.getSocketAddress() + " " + receivePacket.getLength());
			counter++; 
			byte[] actualData = new byte[bytesReceived];
			
			dis.read(actualData);
			
			
			int receivedSequence = dis.readInt();
			short negCkSm = dis.readShort(); 
			
			bais.reset();
			
			byte[] dataAndSequence = new byte[receivePacket.getLength() - 2 ]; 
			
			dis.read(dataAndSequence);
			
			short ckSum = CheckSum.calculateChecksum( dataAndSequence );
		
			System.out.println("Byte Received: " + bytesReceived + " " + receivedSequence);
			
					
			if((negCkSm + ckSum) == 0 ){
				
				if (bytesReceived == 0 ){
					
					lastPacketSequence = receivedSequence;
				}	
	
				if( receivedSequence == expectedSequence){
					//int py = Integer.parseInt(oh.get(expectedSequence).toString());
					
					previous = expectedSequence;

					
					expectedSequence = receivedSequence + actualData.length;
					sendAck(expectedSequence, receivePacket.getSocketAddress());
					
					return actualData;									
					
				}else  if (receivedSequence > expectedSequence ){
					
					oh.put(Integer.valueOf(receivedSequence), actualData);
				
					sendAck(expectedSequence, receivePacket.getSocketAddress());
					}
					
				}

			
			
	

		}
		
		//return null; 
		
	} // end rdt_receive
	/**
	 * Creates and acknowledgment message for the supplied sequence 
	 * number and sends it.
	 *
	 * @param seq sequence number being acknowledged.
	 * @param sender IP address and port number to which the acknowledgment is being sent
	 * @throws IOException 
	 */
	public void sendAck( int seq, SocketAddress sender ) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream( baos );
		
		try {
			dos.writeInt( seq );
		
			dos.writeShort( CheckSum.calculateNegativeChecksum( baos.toByteArray()));
			
			DatagramPacket packet = new DatagramPacket( baos.toByteArray(), baos.size(), sender );
			
			dgSock.send( packet );
			System.out.println("Sending " + previous);
			previous++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} // end sendAck
	
	
	
	/**
	 * Closes the selective repeat connection.
	 */
	public void close()
	{ 
		// TODO
		dgSock.close();
		
	} // end close
	
	
	// Private or protected methods
	// TODO
	

} // end SelectiveRepeatReceiver class
;