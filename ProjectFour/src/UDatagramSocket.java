import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Michael John (johnma@muohio.edu)
 * @date Feb. 8, 2008
 *
 * Based on UnreliableDatagramSocket by:
 * @author Lukasz Opyrchal (opyrchal@muohio.edu)
 * @author Eric Bachmann (bachmaer@muohio.edu)
 * 
 * <P>UDatagramSocket provides an unreliable network 
 * layer; specifically, it provides a UDP socket that 
 * behaves exactly like a standard UDP sockets except that it
 * subject the messages to </P>
 * <ul>
 * <li>loss 
 * <li>reordering
 * <li>duplication
 * <li>corruption
 * </ul>
 * with each specified probability.
 * 
 * 
 * <P>UDatagramSocket extends the DatagramSocket class, therefore; you
 * should be able to use it just like a regular DatagramSocket. The methods
 * that are overridden are send and close. The send in UDatagramSocket
 * does not actually send the datagram but adds it to a queue that is
 * being checked by a different thread called the NetworkSender. 
 * The NetworkSender thread checks the message queue for any new entries.
 * If there are any then it goes through the steps of seeing if the datagram
 * will undergo any type of error, and then sends it, calling the 
 * send method in DatagramSocket. </P>
 */
public class UDatagramSocket extends DatagramSocket {

	// Out buffer, array of Message objects that are waiting to be sent
	private Vector<Message> outBuf;

	// Probability that a packet will be lost
	private double lossProb = 0.0; 

	// Probability that a packet will be duplicate
	private double duplicateProb = 0.0; 

	// Probability that a packets will be reordered, only for pipelined simulation
	private double reorderProb = 0.0; 

	// Probability that a packet will be corrupted
	private double corruptProb = 0.0; 

	// Max number of error to introduce to a packet
	private final int MAX_ERRORS = 1; 

	// More verbose output
	private boolean DEBUG = false; 

	// Lose first packet that is sent
	private boolean loseFirstMsg = false; 

	// Stop sending thread
	private boolean stopSender = false;

	// Modify next message
	private boolean modMsg = true;

	// Used to find the probability that something will happen to a packet
	private Random generator;

	// configuration file for Datagram socket
	private static final String SOCKET_CONFIG_FILE_NAME = "socket.cfg"; 

	// Version number for this class
	public static final String version = "1.0";


	/**
	 * Default Constructor for the class. It creates a UDP socket,
	 * binds it to a remote address
	 * 
	 * @throws SocketException : in case a socket exception occurred
	 */
	public UDatagramSocket() throws SocketException
	{
		super();

		completeSetUp(); 

	} //end Default Constructor


	/**
	 * Constructor for the class. It creates a UDP socket,
	 * binds it to a remote address
	 * 
	 * @param localPort : a local port socket is bound to
	 * @throws SocketException : in case a socket exception occurred
	 */
	public UDatagramSocket(int port) throws SocketException
	{
		super(port);
		
		completeSetUp();

	} //end Constructor
	
	
	/**
	 * Constructor for the class. It creates a UDP socket,
	 * binds it to a remote address
	 * 
	 * @param localPort : a local port socket is bound to
	 * @param remoteAddress : remote address that the socket is bound to
	 * @throws SocketException : in case a socket exception occurred
	 */
	public UDatagramSocket(int port, InetAddress laddr) throws SocketException
	{
		super( port, laddr );
		
		completeSetUp();

	} //end Constructor
	
	
	/**
	 * Constructor for the class. It creates a UDP socket,
	 * binds it to a remote address
	 * 
	 * @param bindaddr : local socket address to bind, or null for an unbound socket
	 * @throws SocketException : in case a socket exception occurred
	 */
	public UDatagramSocket( SocketAddress bindaddr) throws SocketException
	{
		super( bindaddr );
		
		completeSetUp();

	} //end Constructor
	
	
	/**
	 * Instantiates message queue. Reads the configuration file. Starts the
	 * sending thread. The sending thread introduces errors as the files are
	 * sent.
	 */
	private void completeSetUp() throws SocketException
	{	
		if (DEBUG) System.out.println("UnreliableDatagramSocket:  Version " + version);

		// Buffer for messages
		outBuf = new Vector<Message>(32);

		// Read content from configuration file
		readConfigFile();

		// Start Thread that will handle the sending of packets
		new NetworkSender().start();
		
	} // end setUp
	

	/**
	 * Reads the configuration file.
	 */
	private void readConfigFile() 
	{
		// Open Config file for reading
		Scanner file = null;
		try {
			file = new Scanner(new BufferedReader(new FileReader(SOCKET_CONFIG_FILE_NAME)));
		} catch (IOException ioe) {

			System.err.println("Config file could not be found! Using Default values.");
			return;
		}

		Pattern comment = Pattern.compile("^#"); // Pattern used to find comment lines
		Pattern pat;
		Matcher mat;
		String currLine;
		int randomSeed = 101; //giving it a default value
		
		while (file.hasNext()) {
			currLine = file.nextLine();
			
			// Checking if current line is not a comment
			if ( !(mat = comment.matcher(currLine)).find() ) {

				pat = Pattern.compile("(\\S+)\\s*=\\s*(\\d*\\.\\d+)");
				mat = pat.matcher(currLine);
				
				if (mat.find()) {
					String option = mat.group(1);
					double value = Double.parseDouble(mat.group(2));
	
					if (option.equalsIgnoreCase("lossProbability")) {
						lossProb = value;
					}
					if (option.equalsIgnoreCase("duplicateProbability")) {
						duplicateProb = value;
					}
					if (option.equalsIgnoreCase("ReorderProbability")) {
						reorderProb = value;
					}
					if (option.equalsIgnoreCase("CorruptProbability")) {
						corruptProb = value;
					}
					if (option.equalsIgnoreCase("RandomSeed")) {
						randomSeed = (int)value;
					}
				}

				// Checking for Debug and Lose First message inputs
				pat = Pattern.compile("(\\S+)\\s*=\\s*(true|false|TRUE|FALSE)");
				mat = pat.matcher(currLine);
			
				if(mat.find()) {
					String option = mat.group(1);
					
					boolean value = Boolean.parseBoolean(mat.group(2));
					if (option.equalsIgnoreCase("LoseFirstMessage")) {
						loseFirstMsg = value;
					}
					if (option.equalsIgnoreCase("Debug")) {
						DEBUG = value;
					}
				}

			}
		}//end while-loop

		generator = new Random(randomSeed);

		// seeing if content from config file was read properly
		if ( DEBUG ) {

			System.out.println("Contents read from config file:");
			System.out.println(": : LossProb: "  + lossProb);
			System.out.println(": : DuplicateProb: "  + duplicateProb);
			System.out.println(": : reorderProb: "  + reorderProb);
			System.out.println(": : CorruptProb: "  + corruptProb);
			System.out.println(": : Lose First Msg: "  + loseFirstMsg);
			System.out.println(": : Debug: " + DEBUG);
		}

	} //end readConfigFile

	
	/**
	 * Overrides send method in DatagramSocket.
	 * Instead of sending the packet, it is added to a queue.
	 * 
	 * @param packet : Datagram packet to be sent
	 */
	public void send(DatagramPacket packet) {

		if ( DEBUG ) System.out.println("\"Sending!\" Actually queuing.");

		outBuf.add(new Message(packet));

	} //end send

	
	/**
	 * Closes the UDP socket and destroys the NetworkSender thread.
	 */
	public void close() {

		// Stop the sending thread
		stopSender = true;
		
		if (DEBUG) System.out.println("Close method called.");
		
	} //end close

	
	/**
	 * Calls super class close method. Should only be 
	 * called when all threads using the super class
	 * object have stopped.
	 */
	protected void  closeSocket()
	{
		super.close();
		
	} // end closeSocket
	

	
	/**
	 * Dequeue an object from queue of datagrams to be sent.
	 * Then introduces the message to error(s) if conditions
	 * are met.
	 * 
	 * @return object : dequeued object
	 * @throws IllegalMonitorStateException
	 * @throws InterruptedException
	 */
	protected Message getNextMsg()

	throws IllegalMonitorStateException, InterruptedException {
	
		Message msg = null;		

		if (outBuf.isEmpty()) {
			return null;
		}
		try {
			msg = ((Message) outBuf.elementAt(0));
			if (msg == null) 
				return null;

			outBuf.removeElementAt(0);
			
			if ( modMsg ) {			
				
				if ( loseFirstMsg ) {
					
					loseFirstMsg = false;
				
					return null;
				}
				else {
					if (msg.errorsCount >= MAX_ERRORS) { // no errors if msg already
					
						return msg; // subjected to maxNoOfErrs.
					}
					
					
					if (generator.nextDouble() < corruptProb) { // corrupt msg
						
						corruptPacket(msg);
						if (DEBUG)
							System.out.println("Message Corrupted");
						return msg;
					}

					if (generator.nextDouble() < lossProb) { // lose msg
						
						if (DEBUG)
							System.out.println("Message Lost");
						return null;
					}

					if ( ( !outBuf.isEmpty() ) && (generator.nextDouble() < reorderProb) ) {
						
						// reorder msg
						if (DEBUG)
							System.out.println("Messages Reordered");
						
						msg.errorsCount++;
						
						outBuf.insertElementAt(msg,
						
								((int) (outBuf.size() * generator.nextDouble())));
						
						return null;
					}

					if ( generator.nextDouble() < duplicateProb ) {

						// duplicate msg
						if (DEBUG)
							System.out.println("Message Duplicated");
						
						Message duplicateMsg =
							new Message(
									new DatagramPacket(
											msg.packet.getData(),
											msg.packet.getLength(),
											msg.packet.getAddress(),
											msg.packet.getPort()));

						duplicateMsg.errorsCount = msg.errorsCount + 1;
						
						outBuf.insertElementAt(duplicateMsg,
								((int) (outBuf.size() * generator.nextDouble())));
					}
				}
			}
			else {
				System.out.println("First msg not modified");
				
				modMsg = true;
				
				return msg;
			}
		} catch (ArrayIndexOutOfBoundsException aiobe) {
			return null;
		}
		return msg;
	
	} //end getNextMsg


	/**
	 * Sends a packet using the send method from DatagramSocket
	 * 
	 * @param packet : packet to be sent
	 */
	private void callSuperSend(DatagramPacket packet) {

		try {
			super.send( packet );
			
		}catch (SocketException se) {
			
			System.err.println("Socket Exception. Most likely socket is being closed!");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	} //end callSuperSend


	/**
	 * Corrupts the given message.
	 * Done by switching a byte in the message.
	 * @param msg
	 */
	private void corruptPacket(Message msg)
	{
		// get number of bytes in Message and create
		// new array to store corrupted data
		int numberOfBytes = msg.packet.getLength();
		byte data[] = msg.packet.getData();

		// corrupt a byte
		int corruptByte = (int)(Math.random() * numberOfBytes );
		data[corruptByte] = (byte)(~data[corruptByte]);

		// save corrupt data to original Message
		msg.packet.setData(data);
	} // end corruptPacket

	

	/**
	 * Class NetworkSender : Inner Class to UnreliableDatagramSocket
	 *
	 * This thread sends the messages of outBuf, subjecting them to probabilistic
	 * errors (loss, reordering, duplication), maximum message lifetime, and
	 * transmission delay. Specifically, it does the following:
	 * - calls getHeadMsg to get either the head msg of outBuf or null
	 * - Sends the msg on UDP socket.
	 *
	 * ------------------------------------------------------------------------------
	 */
	class NetworkSender extends Thread {

		public void run() {

			if (DEBUG) System.out.println("Run thread for NetworkSender running!");
			
			// Stay in loop until the close method of UDatagramSocket is
			// called
			while (!stopSender) {
				
				try {
					Thread.yield();
					
					Message msg = getNextMsg();
					
					if (msg != null) {
						if (DEBUG) System.out.println("Sending a message");
						
						callSuperSend( msg.packet );
					}
				} catch (InterruptedException ie) {
					
					if (DEBUG) System.err.println("InterruptedException in NetworkedSender");
					
				} catch (IllegalMonitorStateException imse) {
					
					throw new Error("Not an owner of the  monitor.");
					
				} catch (Exception f) {
					
					throw new Error("Sending message " + f.getMessage() + " failed");
				}
				
			} //end while-loop
			
			emptyOutputBuffer();
			
			// Close the DatagramSocket
			closeSocket();
			
			if (DEBUG) System.out.println("NetworkSender is closed.");
			
		} //end run
			
		private void emptyOutputBuffer()
		{

			// Push out the remaining messages in the queue
			while ( !outBuf.isEmpty() ) {
				
				try {
					
					Message msg = getNextMsg();
					
					if (msg != null) {
						if (DEBUG) System.out.println("Sending a message");
						
						callSuperSend( msg.packet );
					}
				} catch (InterruptedException ie) {
					
					if (DEBUG) System.err.println("InterruptedException in NetworkedSender");
					
				} catch (IllegalMonitorStateException imse) {
					
					throw new Error("Not an owner of the  monitor.");
					
				} catch (Exception f) {
					
					throw new Error("Sending message " + f.getMessage() + " failed");
				}
			}
			
		} // end emptyOutputBuffer

	} //end NetworkSender

}//end UnreliableDatagramSocket class

/**
 * Class Message
 *
 * Stores message data and number of errors the message has
 * gone through.
 *
 * Attributes :
 * ------------
 * data : Message data
 * errorsCount: Number of errors applied to this message.
 * ------------------------------------------------------------------------------
 */
class Message {

	public DatagramPacket packet = null;

	// number of errors packet has been subjected to
	public int errorsCount = 0;

	Message( DatagramPacket dgp ) {

		byte[] byteData = new byte[dgp.getLength()];

		System.arraycopy(dgp.getData(), 0, byteData, 0, dgp.getLength());

		packet = new DatagramPacket(byteData, dgp.getLength(), 
				dgp.getAddress(), dgp.getPort());
		
	} //end Constructor

} //end Message	
