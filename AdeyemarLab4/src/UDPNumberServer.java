import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPNumberServer {

	/**
	 * @param args
	 */
	static int SERVER_PORT = 32150;
	static void displayContactInfo( DatagramSocket dgSocket )
	{
		try {
			// Display contact information.
			System.out.println( 
				"Number Server standing by to accept Clients:"		
				+ "\nIP : " + InetAddress.getLocalHost() 
				+ "\nPort: " + dgSocket.getLocalPort() 
				+ "\n\n" );				
			
		} catch (UnknownHostException e) {
			// NS lookup for host IP failed? 
			// This should only happen if the host machine does 
			// not have an IP address.
			e.printStackTrace();
		}

	} // end displayContactInfo

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DatagramSocket serverSock = new DatagramSocket(SERVER_PORT);
		DatagramPacket receivePacket = new DatagramPacket( new byte[8], 8);
		float axle;
		
		do{
		
		serverSock.receive(receivePacket);
	
		ByteArrayInputStream bais = new ByteArrayInputStream( receivePacket.getData() );

		DataInputStream dis = new DataInputStream( bais );
				
		float numberOne = dis.readFloat();
		float numberTwo = dis.readFloat();
		axle = numberOne*numberTwo;
		
		
		serverSock.receive( receivePacket);
		System.out.print( "Packet received from host: " 
				+ receivePacket.getAddress() 
				+ " using a socket bound to UDP port " 
				+ receivePacket.getPort());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		DataOutputStream dos = new DataOutputStream( baos );
		dos.writeFloat(axle);				
		DatagramPacket packet = new DatagramPacket( baos.toByteArray(), baos.size() );


		DatagramPacket sendPacket 
		= new DatagramPacket( new byte[4], 4);

		packet.setSocketAddress(receivePacket.getSocketAddress() );				
		serverSock.send(packet);
		}while(axle >= 0);
		serverSock.close();
		


	}

}
