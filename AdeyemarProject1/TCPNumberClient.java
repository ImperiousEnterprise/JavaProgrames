//By : Femi Adeyemi
// UniqueID: Adeyemar
// Project 1
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPNumberClient {

	/**
	 * @param args
	 */
	public static int i;
	protected static String go;
	protected static String serverIP;
	protected static int guess;
	protected static int gue;
	protected static DataOutputStream gog;
	protected static DataInputStream oops;
	protected static Socket socket;
	
	 static void Connect() throws UnknownHostException, IOException, EOFException{
		Scanner readFromConsole = new Scanner(System.in);
		
		System.out.print("Enter server IP: ");
		serverIP = readFromConsole.nextLine();
		
		if( serverIP.equals("") )
		{
			serverIP = "127.0.0.1";
			
		}
		System.out.println("Contacting Server at: " + serverIP);
					
		// First do while loop is responsible for running multiple games after yes or no is answered.
		do{
			// Second do while loop is responsible for checking 3 times with the Server to give 3 guesses
			do{
				socket = new Socket( InetAddress.getByName(serverIP), 32100);
								
				//Checks whether a connection has been made. If not, leaves the method
				// and demands for reconnection with the Server
				if(socket.isConnected()== false){
					System.out.println("Reconnect with server by restarting client and/or Server");
					return;
				}
				gog = new DataOutputStream( socket.getOutputStream() );
        
				oops = new DataInputStream( socket.getInputStream() );
				
				//Basically where the max and min values of the range are stored.
				int max = oops.readInt();
				int min = oops.readInt();
		
		
				System.out.print("Guess a number between " + min + " and " + max + " and enter it: " );
				
				guess = readFromConsole.nextInt();	
							
				gog.writeInt(guess);
					
				gue = oops.readInt();
				int va = oops.readInt();
				
				//gue is the variable sent back from the server to determine rather the
				// guess was correct. va is basically the right guess that was made.
				if(gue == 0){
					System.out.print("Try Again. ");
				}else if(gue == 1){
					System.out.println("The number is " + va + ".You Win!");
					break;		
				}else if(gue == 2){
					System.out.println("Game Over. The correct guess was " + va + ". Client loses.");
					break;
				}	
				i++;		
			}while(gue == 0 && socket.isConnected()== true);
				Work();
		}while(go.equalsIgnoreCase("yes")&& socket.isConnected()== true);
		if(socket.isConnected() == true){
		System.out.println("Connection Closed");
		// be neat and tiddy after no more games are desired.
		socket.close();
		}else{
			System.out.println("Reconnect with server by restarting client and/or Server");
		}	
	}
	 // Does the Checking for a New Game
	 static void Work() throws IOException{
		 if(socket.isConnected()== true){				
			 Scanner readFromConsole = new Scanner(System.in);
			 System.out.print("Do you want to play another Game? Enter 'yes' or 'no': ");
			go = readFromConsole.next();
			gog.writeUTF(go);
			}else{
				System.out.println("Reconnect with server by restarting client and/or Server");
				return;
			}
	 }
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Connect();
		
	}

}
