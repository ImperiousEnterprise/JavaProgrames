import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Implementation of the selective repeat protocol from "Computer 
 * Networking" by James F. Kurose and Keith Ross.
 * 
 */
public class  SelectiveRepeatSender {
	
	// Maximum number of bytes of data that can be carried
	// in a packet
	public static final int MAX_DATA_IN_PACKET = 50;
	
	// Number of bytes of overhead carried in each packet
	// Includes the sequence number and checksum
	public static final int NUMBER_OF_OVERHEAD_BYTES = 6;

	// Socket for sending and receiving data
	DatagramSocket dgSock;
	
	// IP address and port number to which to send all data
	InetSocketAddress destination;
	
	// Thread responsible for sending packets
	protected SendThread sendThread = new SendThread();
	
	// Thread that is responsible for receiving acknowledgements.
	protected ReceiveThread receiveThread = new ReceiveThread();
	
	// Size of transmission window.
	protected static final int WINDOW_SIZE = 8;
	
	// Bottom of the transmission window.
	protected int base = 0;
	
	// Index of next packet to be sent.
	protected int nextPacketIndex = 0;
	
	// Other private or protected data members
	// TODO	
	int currentSequence = 0;
	
	BufferedData[] buffer = new BufferedData[1000];
	int recieve = 0;
	int not = 1;
	int sendBase = 0;
	int packsize = 0;
	BufferedData rock;
	// Reference for timer used to track timeouts
	Timer timer;

	// Flag to indicate if a timeout has been scheduled
	boolean timerRunning = false;
	public int tmpBufSize = 0;
	private int bufferSize = 100;
	int acknowledgedSequenceNumber = 0;
	short negCkSm = 0;
	short pop = 0;
	int ackNUM = 0;
	BufferedData b1;
	public CircularBuffer<BufferedData> sendBuffer = new CircularBuffer<BufferedData>(
			bufferSize);
	public DatagramPacket dg;
	boolean lastPacketAcked = true;
	/**
	 * Creates a "reliable connection" to the specified address 
	 * and port number. Objects of this class will be used to 
	 * send data using selective repeat protocol.
	 * 
	 * @param destinationAddress
	 *            IP address of the receiving host
	 * @param destinationPort
	 *            port number for the receiving application
	 */
	public SelectiveRepeatSender (String destinationAddress, int destinationPort ) 
	{
		try {
			
			dgSock = new UDatagramSocket();
			dgSock.setSoTimeout( 10 );
			destination = new InetSocketAddress( destinationAddress, destinationPort);
		
		} catch (SocketException e) {
			
			System.err.println("Error: could not create a connection.");
		}
		
		// Start sending and receiving threads
		sendThread.start();
		receiveThread.start();

		
	} // end SelectiveRepeatSender constructor
	
	/**
	 * errr... updates the base thingy. "slides" the window along the array, if
	 * you will.
	 * 
	 * @param ackNum
	 *            the number of the next base number (calculate base +
	 *            data.length for this)
	 * 
	 */
	synchronized public void updateSendBase(int ack) {
		
		sendBase = ack;
	}
	/**
	 * A class I created in order to easily implement a circular buffer.
	 * Arraylist's inherent insertion ability is pathed with a tricky little
	 * boolean
	 * 
	 * @author Femi Adeyemi
	 */
	class CircularBuffer<E> extends java.util.ArrayList<E> {

		private static final long serialVersionUID = 0L;

		/** The placeholder for the buffer* */
		private int counter = 0;
		/** the capacity or size of the buffer (figuratively)* */
		private int cap = 0;
		/** has the buffer been filled such that removals are now required* */
		private boolean looped = false;

		/**
		 * Instatiates the class as an arraylist with a couple extras and an
		 * overriden method or two
		 * 
		 * @param capacity
		 *            The size you want the buffer to be
		 */
		public CircularBuffer(int capacity) {
			super();
			cap = capacity - 1;
		}

		/**
		 * a special add method that will always replace the placeholder's index
		 * value with the Element passed in
		 * 
		 * @param o
		 *            the object to add to the buffer
		 */
		public boolean add(E o) {

			if (looped)
				super.remove(counter);
			super.add(counter, o);
			System.out.println("Data added to: " + counter);
			counter++;

			if (counter > cap) {
				counter = 0;
				looped = true;
			}
			return true;
		}

		/**
		 * return the buffer size
		 * 
		 * @return the capacity/size of the buffer
		 */
		public int getCapacity() {
			return cap;
		}

		/**
		 * return the buffer placeholder value
		 * 
		 * @return the place we are in the buffer
		 */
		public int getCounter() {
			return counter;
		}

	}
	/**
	 * Thread that is responsible for sending messages. 
	 * 
	 * @author Femi Adeyemi
	 *
	 */
	class SendThread extends Thread {
		
		public void run(){
			
			System.out.println("SendThread started");
			
			// Loop to transmit packets
			// TODO
			
			while (lastPacketAcked) {
				yield();
				// keeps sending packets as long as it can
				while (sendBuffer.getCounter() == tmpBufSize)
					;
				try {
					DatagramPacket dg = sendBuffer.get(tmpBufSize).getPacket();
					dg.setSocketAddress(destination);
					dgSock.send(dg);
					System.out.println("Packet Sent " + tmpBufSize + " "
							+ sendBuffer.getCounter());
				} catch (SocketException e) {
					System.out.println("Connection Closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (tmpBufSize >= sendBuffer.getCapacity())
					tmpBufSize = 0;
				else
					tmpBufSize++;
				// for circular buffering
			}
		}
} // end SendThread class 
	
	/**
	 * Thread that is responsible for receiving acknowledgements.
	 * 
	 * @author 
	 *
	 */
	class ReceiveThread extends Thread {
		
		public void run(){
			
			
			System.out.println("ReceiveThread started");
		
			
			// Loop to receive acknowledgements
			// Recieves the acknowledgements and checks them out off in the buffer
			while (lastPacketAcked) {
				
				try {
					byte[] a = new byte[NUMBER_OF_OVERHEAD_BYTES+2];

					dg = new DatagramPacket(a, a.length);

					dgSock.receive(dg);

					System.out.println("ACK RECEIVED");
					ByteArrayInputStream bais = new ByteArrayInputStream(dg.getData());
					DataInputStream dis = new DataInputStream(bais);

					// check sequenceNumber here
					int ack = dis.readInt();
					short sum = dis.readShort();

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(baos);
					dos.writeInt(ack);

					Short check = CheckSum
							.calculateChecksum(baos.toByteArray());

					if ((check + sum) != 0) {
						System.out.println("Checksum failure in ACK PACKET");
					} else {

						int arraySize = 0;

						if (sendBuffer.size() > sendBuffer.getCapacity()) {
							arraySize = sendBuffer.getCapacity();
						} else {
							arraySize = sendBuffer.size();
						}

						int index = 0;
						for (int i = 0; i < arraySize; i++) {
							
							if (sendBuffer.get(i).ackNum == ack) {
								index = i;
								System.out.println("ACK FOUND: " + i + " " + sendBuffer.get(i).ackNum);
							}
						}

						updateSendBase(sendBuffer.get(index).ackNum);
						sendBuffer.get(index).setAcked();


						if (sendBase == BufferedData.next_sequence_number) {
							sendBuffer.get(index).stopTimer();
						} 

					}
				} catch (SocketTimeoutException e) {
					System.out.println("Connection Timeout but will continue to send packets");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	}//run end
} // end ReceiveThread class


	/**
	 * Accepts data contained in a byte array to be transmitted
	 * using one or more packets. If the size of the data array 
	 * exceeds the maximum packet size, the data will be sent in 
	 * multiple packets.
	 * 
	 * Returns true as long as there is room to buffer packets.
	 * If the send buffer is full, it will return false. 
	 * 
	 * @param data byte array containing data to be sent.
	 * @return true if the data was accepted for transmission 
	 * false if the data was not accepted 
	 * @throws IOException 
	 */
	public boolean rdt_send(byte[] data) throws IOException 
	{ 
		//TODO
		

		b1 = new BufferedData(data);
		sendBuffer.add(b1);
		return true;

		
	} //end rdt_send

 
	// end ReceiveThread class
	
	
		/**
	 * Closes the selective connection by sending an empty message to
	 * the receiver. The empty message still contains a checksum 
	 * and sequence number. After a correct acknowledgement is 
	 * received, the connection is closed. If no acknowledgement is 
	 * received after three timeouts, an error message is 
	 * displayed to the console and the connection is closed.
	 * @throws IOException 
	 * 
	 */
	public void close() throws IOException 
	{
		// TODO
//		rdt_send(new byte[0]);
//		dgSock.close();
		
		rdt_send(new byte[0]);

		// makes sure the close method does not cut off the sendThread
		while (sendBuffer.getCounter() != tmpBufSize + 1)
			;
		while (!sendBuffer.get(tmpBufSize + 1).acked)
			;
		System.out.println("File Transfer Complete");
		
		dgSock.disconnect();
		dgSock.close();
		lastPacketAcked = false;	
		
	} // end close
	
	

	} // end  SelectiveRepeatSender class
