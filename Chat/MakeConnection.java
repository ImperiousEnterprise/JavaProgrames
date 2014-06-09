import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class MakeConnection {
	ArrayList<ChatThread> clientConnections = new ArrayList<ChatThread>();
	boolean listening = true;
	final int LISTENPORT = 31200;
	protected String serverIP;
	//Codes to send to the server
	 int join = 0;
	 int remove = 1;
	 int info = 2;
	 int port;
	 Scanner scanner = new Scanner (System.in);
	 ServerSocket ss;
	 String getty;
	 String sp;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public MakeConnection() throws IOException{
		//Creates random port
		ss = new ServerSocket(0);
		
		port = ss.getLocalPort();
		
		 

		System.out.print("Enter ScreenName: ");
		sp = scanner.nextLine();
		
		System.out.print("Enter server IP. If server is local just press Enter: ");
		serverIP = scanner.nextLine();
		
		if( serverIP.equals("") )
		{
			serverIP = "127.0.0.1";
			
			
		}
		 
		
		// Establishes Connection to the server
		Socket socket = new Socket( InetAddress.getByName(serverIP), ListenForConnection.SERVERPORT);
		DataOutputStream gog = new DataOutputStream( socket.getOutputStream() );
		DataInputStream oops = new DataInputStream( socket.getInputStream() );
		
		//Checks to make sure socket is connected. If not immediately exits program
		if(socket.isConnected()== false){
			System.out.println("Reconnect with server by restarting client and/or Server");
			System.exit(0);
		}
		//writes the information to server here
		gog.writeInt(join);
		gog.writeUTF(sp.trim());
		gog.writeInt(port);		
		System.out.println("Wrote");
		
		socket.close();
			
		new Join().start();
		
		new Keyboard().start();
		
			
			
	
	}
	
//Class responsible fore listening for Connections
class Join extends Thread {
		
		public Join(){
			try {
				// Timeouts the ss.accept() so the join server does not hang
				ss.setSoTimeout(500);
				
				
			} catch (IOException e) {
				System.err.println("Error creating server socket used to listing for joining clients.");
				System.exit(0);
			}
			
		} // end JoinServer
		
		/**
		 * Listens for join and exiting clients using TCP.
		 */
		public void run(){

			while(listening) {
				yield();
				try {
										
					// Receive connections from other clients
					Socket s;
					
					s = ss.accept();					

					// Make a thread to take to the client
					clientConnections.add( new ChatThread( s, sp ) );
					
					}
	
				catch (SocketTimeoutException e) {}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			try {
				//Loops through clientConnections to end each ChatThread
				for(ChatThread x : clientConnections){
					x.finish();
				}
				ss.close();
				System.out.println("Join Server has stopped running");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		} // end run
		
	} // end JoinServer class
	
	
	// Inner class to listen for screen names or quit
	class Keyboard extends Thread {
		
		
		public Keyboard() throws IOException{}
		
		// Listens for screen name or quit
		public void run() {
			do{
				
					System.out.print("Enter screen name or quit: ");
					getty = scanner.nextLine();
				
				Socket sock;
				try {
					
					sock = new Socket( InetAddress.getByName(serverIP), LISTENPORT);
					DataOutputStream dos = new DataOutputStream( sock.getOutputStream() );
					DataInputStream dis = new DataInputStream( sock.getInputStream() );
				
					// If quit is received, sends remove code to server and sets listening to false
						if(getty.equalsIgnoreCase("quit")){
							dos.writeInt(remove);
							dos.writeUTF(sp);
							listening = false;
							
						}else if(!getty.equalsIgnoreCase("")){
							//Sends code for information to server
							dos.writeInt(info);
							
							dos.writeUTF(getty);
							
							//Receives feed back from the server
							int cody = dis.readInt();							
							
							if(cody == 1){
								byte ip[]= new byte[4];
								dis.read(ip);
								int pot = dis.readInt();
								clientConnections.add( new ChatThread( new Socket( InetAddress.getByAddress(ip), pot ), sp));
							}else{
								System.out.println( "Could not find " + getty );
							}				
					}
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}while(listening);
			
			System.out.println("Keyboard thread has stopped running");
		} // end run
		
	} // end of Join class
	
	
	public static void main(String[] args) throws Exception {
		
		//new ChatThread( new Socket("127.0.0.1", 31200 ), "MakeConnection");
		new MakeConnection();
	}
}
