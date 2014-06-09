//By : Femi Adeyemi
// UniqueID: Adeyemar
// Project 1
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class TCPNumberServer {

	/**
	 * @param args
	 */
	public static int num1;
	public static int sub;
	public static int randomVal;
	public static int winner;
	protected static int loop = 0;
	protected static int SERVER_PORT = 32100;
	protected static boolean gut = true;
	protected static int x;
	public static String g;
	
	//code borrowed from Labs
	static void displayContactInfo(ServerSocket serverSocket){
		try{
			//Display contact information.
			System.out.println("Number Server standing by to accept Clients: " + "\nIP : " + InetAddress.getLocalHost() + "\nPort: " + serverSocket.getLocalPort() + "\n\n");
		}catch (UnknownHostException e){
			//NS lookup for host IP failed?
			//This should only happen if the host machine does
			//not have an IP address.
			e.printStackTrace();
		}
	}
	
	//Main method for enacting the Server
	 static void Server() throws IOException{
		DataInputStream dis; 
		DataOutputStream gus;
		ServerSocket open;
		
		
		open = new ServerSocket(SERVER_PORT);
		// First do while loop is responsible for running multiple games after yes or no is recieved.
		do{
			displayContactInfo(open);
			// Responsible for doing the random numbers and picking ranges.
			RandomGenerator();
			do{
				Socket op = open.accept();
				dis = new DataInputStream(op.getInputStream());
				gus = new DataOutputStream(op.getOutputStream());
				//Max number
				gus.writeInt(num1);
				//Min number
				gus.writeInt(sub);
	
				int wi;
				// Reads the Guesses
				x = dis.readInt();
		
				// Determines if another guess is need or answer is correct. Based off of 0, 1, 2
				if(x == randomVal){
					wi = 1;
				}else if(x != randomVal && loop == 2){
					wi= 2 ;
				}else{
					wi = 0;
				}
				gus.writeInt(wi);
				gus.writeInt(randomVal);
				
				//Counter for guesses
				loop++;
				System.out.println("written");
			}while(loop <= 2);
		
			// Reads in yes or no for the project
			g = dis.readUTF();
			loop = 0;
		}while(g.equalsIgnoreCase("yes"));
		
		System.out.println("Connection Closed");
		// For being nice and tidy
		dis.close();
		gus.close();
		open.close();
		
	}
	 // Handles Generating numbers
	protected static void RandomGenerator(){
		Random gen = new Random();
		num1 = gen.nextInt(991);
		sub = num1 - gen.nextInt(11);
		randomVal = gen.nextInt(num1 - sub) + sub; 		
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server();
	}

}
