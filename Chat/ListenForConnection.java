//Written By Femi Adeyemi
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class ListenForConnection {
	//Keeps track of all the screens names with the associated IP address
	ConcurrentHashMap<String, InetSocketAddress> clientMap = new  ConcurrentHashMap<String, InetSocketAddress>();
	static final int SERVERPORT = 31200;
	static final boolean DEBUG = false;
	String name;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	int po;
	
	public ListenForConnection() throws IOException{
		
		ServerSocket open = new ServerSocket(SERVERPORT );

		do{
			//Accepting Connections from Clients
			Socket op = open.accept();
			DataInputStream gis = new DataInputStream(op.getInputStream());
			DataOutputStream gus = new DataOutputStream(op.getOutputStream());
			
	
			//Receives code to determine join, remove, or addition
			int code = gis.readInt();
			
		
			name = gis.readUTF().trim();
			
			
			
			// Join	
			if(code == 0){
				
				System.out.println( "Adding " + name );
				//returns IP address of sending Client
				InetAddress IP = op.getInetAddress();

				int port = gis.readInt();
				
				//adds Clients to the ClientMap
				add(name, new InetSocketAddress(IP, port));
				
				System.out.println(clientMap);
				
			}else if(code == 1){
				
				//remove
				remove(name);
				
			}else if(code == 2){
				//Get Info
				InetSocketAddress isa = null;
				
				if (clientMap.containsKey(name)){
					isa = getInfo(name);
					//Sends code for Client to know that name is on the ClientMap
					gus.writeInt(1);
					gus.write(isa.getAddress().getAddress());
					gus.writeInt( isa.getPort());
					System.out.println("Everything is Sent");
				}
				else {
					System.out.println( "Could not find " + name );
					dos.writeInt(2);
				}
				
				
			}
		
			op.close();
			
		
		}while(true);
	}
	
	//Add method puts information in ClientMap
	public void add(String yo, InetSocketAddress po){
		clientMap.put(yo, po);
	}

	//Remove method  removes information from the ClientMap
	public void remove(String y ){
		clientMap.remove(y);
	}

	//Gets the information from the ClientMap
	public InetSocketAddress getInfo(String po){
		return clientMap.get(po);				
	}
	
	public static void main(String[] args) throws Exception
	{		
		//ServerSocket serveSocket = new ServerSocket(31200);
		
		//new ChatThread( serveSocket.accept(), "ListenForConnection" );
		new ListenForConnection();
	}

}
