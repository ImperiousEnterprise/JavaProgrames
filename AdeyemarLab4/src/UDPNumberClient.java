import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class UDPNumberClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner readFromConsole = new Scanner( System.in );
		
		System.out.print("Enter first number: ");
		float firstNumber = readFromConsole.nextFloat();
		System.out.print("Enter second number: ");		
		float secondNumber = readFromConsole.nextFloat();
		System.out.print("Enter IP address: ");
		String IP = readFromConsole.next();
		
		
		DatagramSocket clientSock = new DatagramSocket();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		DataOutputStream dos = new DataOutputStream( baos );
				
		dos.writeFloat( firstNumber );
		dos.writeFloat( secondNumber );
				
		DatagramPacket packet = new DatagramPacket( baos.toByteArray(), baos.size() );

		//DatagramPacket packet = new DatagramPacket( new byte[8], 8);
		packet.setAddress(InetAddress.getByName(IP));
		packet.setPort( UDPNumberServer.SERVER_PORT );
		clientSock.send(packet);
		DatagramPacket receivePacket = new DatagramPacket( new byte[4], 4 );
		clientSock.receive( receivePacket );
		clientSock.close();




	}

}
