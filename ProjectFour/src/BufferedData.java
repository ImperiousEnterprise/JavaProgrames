import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.util.Timer;
import java.util.TimerTask;



/**
 * @author bachmaer
 *
 * Class for holding buffered data in relation to the GBN or selective repeat 
 * protocols from "Computer Networking" by Kurose and Ross. Holds a packet 
 * of the data as well as the sequence number for the data, the time the data 
 * was sent, and whether the data has been acknowledged or not. Sequence
 * numbers are in accordance with TCP as long as BufferedData objects are 
 * instantiated in the order they will be sent.
 * 
 * Packet contains the data. Checksum is calculated, but IP address and port 
 * number are not set.
 */
public class BufferedData 
{
	// Used to keep track of which sequence number should be asigned to 
	// the next BufferData object that is created.
	public static int next_sequence_number = 0;
	
	// Number of times the packet has been transmitted
	protected int numberOfTransmits = 0;
	
	// True if the data has been acknowledged, false otherwise
	protected boolean acked = false;
	
	// The data minus the sequence number and checksum
	private byte[] data;
	
	// Sequence number for the data
	private int seqNum;
	
	// Time the data was last sent
	protected long timeSent = Long.MAX_VALUE;
	
	// Flag to signal need for transmission
	protected boolean transmissionFlag = true;
	
	// Stream for putting the data into the packet
	protected ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
	protected DataOutputStream dataOutput = new DataOutputStream( byteArrayOut );
	
	// Reference for timer used to track timeouts 
	protected Timer timer;
	
	// Flag to indicate if a timeout has been scheduled
	protected boolean timerRunning = false;

	public int ackNum;
	
	/**
	 * Constructs the packet to be sent based on the input 
	 * parameters. Assumes BufferedData objects are created in 
	 * the order in which they will be transmitted.
	 * 
	 * @param seqNum sequence number to be used for the data
	 * @param data the data to be sent.
	 */
	public BufferedData( byte[] data )
	{
		seqNum = next_sequence_number;
		
		ackNum = seqNum + data.length;
		
		acked = false;
		
		
		
		setData( data ); // fill the byte array
		
		updateStaticSequenceNumberCounter();
		
	} // end  Constructor
	
	/**
	 * Accesor method for a copy of the data packet.
	 * 
	 * @return packet containing the data
	 */
	public DatagramPacket getPacket()
	{
		return new DatagramPacket(this.byteArrayOut.toByteArray(), 
				this.byteArrayOut.size() );
		
	} // end getDataPacket
	
	
	/**
	 * Increase the count of the number of times this data has
	 * been sent by one. Normally called each time the data is transmitted.
	 *
	 */
	public void incrementTransmissionCounter()
	{	
		this.numberOfTransmits++;
		
	} // end incrementTransmissionCounter
	
	
	/**
	 * Returns the value of a variable that can be used to track
	 * the number of times the data associated with this object
	 * has been transmitted. Accuracy of the values depends on
	 * incrementTransmissionCounter being called at the appropriate
	 * times.
	 * 
	 * @return the value of the transmission counter
	 */
	public int getNumberOfTransmissions()
	{
		return this.numberOfTransmits;
		
	} // end getNumberOfTransmissions
	
	/**
	 * Sets the acked flag to true and stops any timer that is running.
	 * Should be called when the packet is acknowledged. 
	 * 
	 */
	public void setAcked( )
	{
		acked = true;
		
		stopTimer();
		
	} // end setAcked
	
	/**
	 * Starts a timer on the packet. Should be called right after a packet is sent.
	 * 
	 * @param timeoutInterval time in milliseconds to wait before timing out.
	 */
	public void startTimer(long timeoutInterval) {
		
		if ( timerRunning == false ){
			
			timer = new Timer();
			timer.schedule( new TimeoutResponse(), timeoutInterval );
			timerRunning = true;
			
			// Indicate that the packet does not need to be resent
			// at the present time
			setTransmissionFlag( false );
			setTimeSent();
			
			
		}
		
	} // end startTimer
	
	
	/**
	 * Accessor method for the transmission flag. The transmission
	 * flag indicates whether of not the packet needs to be transmitted.
	 * Returns true if the packet needs to be retransmitted. 
	 * 
	 * @return transmission flag
	 */
	public boolean getTransmissionFlag()
	{
		return this.transmissionFlag;

	} // end getTransmissionFlag
	
	/**
	 * Accessor method for the sent time.
	 * 
	 * @return the sent time
	 */
	public long getTimeSent()
	{
		return timeSent;

	} // end getTimeSet
	
	
	/**
	 * Return the amount of time in milliseconds since the packet was sent.
	 *
	 */
	public long getTimeSinceSent()
	{
		return System.currentTimeMillis() - timeSent;
		
	} // end setTimeSent
	
	/**
	 * Accessor method for the data.
	 * 
	 * @return the data contained in the packet.
	 */
	public byte[] getData()
	{
		byte [] dataCopy = new byte[data.length]; // copy the data
		System.arraycopy( this.data, 0, dataCopy, 0, data.length );
		
		return dataCopy;
		
	} // end getData
	
	
	/**
	 * Accessor method for the number of bytes of data in 
	 * the segment.
	 * 
	 * @return the number of bytes of data.
	 */
	public int dataLength()
	{
		if ( data != null ) {
			return data.length;
		}
		else {
			return 0;
		}
		
	} // end dataLength
	
	
	/**
	 * Update the counter for sequence numbers. Assumes 
	 * BufferedData objects are created in the order in which
	 * they will be transmitted.
	 * 
	 * Note: THE WRAP AROUND HAS NOT BE FULLY TESTED.
	 */
	protected void updateStaticSequenceNumberCounter()
	{
		long bigTemp = (int)next_sequence_number + data.length;
			
		next_sequence_number = (int)(bigTemp % Integer.MAX_VALUE);		
	}
	
	
	/**
	 * Create the packet containing the data.
	 *
	 * Packet contains the data. Checksum is calculated, but IP address and port 
	 * number are not set.
	 *
	 */
	protected void createPacket()
	{
		try {
			
			// Write packet contents to the byte array stream
			dataOutput.write( data );
			dataOutput.writeInt( seqNum );
			dataOutput.writeShort( CheckSum.calculateNegativeChecksum( byteArrayOut.toByteArray() ) );
			

		}
		catch( Exception e ){
			
			e.printStackTrace();	
			System.err.println("Error creating a data packet");
		}			
		
	} // end createPacket
	
	
	protected void stopTimer() {
		
		if ( timerRunning == true ){
			
			timer.cancel();
			timerRunning = false;

		}
		
		// Indicate that packet no longer needs to be resent
		setTransmissionFlag( false );
		
	} // end stopTimer
	
	
	class TimeoutResponse extends TimerTask {
		
		public void run() {
			
			//System.out.println("timer task ran ");
			
			// Clean up the timer so threads will not hang.
			timer.cancel();
			timerRunning = false;
			
			// Indicate that the packet needs to be resent
			setTransmissionFlag( true );
		}
		
	} // end TimerTask
	
		
	/**
	 * Set the sent time for the packet to the current time
	 *
	 */
	protected void setTimeSent()
	{
		timeSent = System.currentTimeMillis();
		
	} // end setTimeSent
		
	
	/**
	 * Set the transmission flag to the value of the input parameter.
	 * True indicates that packet needs to be sent. False indicates the
	 * packet does not need to transmitted.
	 *
	 */
	protected void setTransmissionFlag( boolean flagValue)
	{
		this.transmissionFlag = flagValue;
		
	} // end setTransmissionFlag


	/**
	 * Set the data for the packet. Acknowledgement is set to false.
	 * 
	 * @param data data to be in the packet to be sent.
	 */
	protected void setData( byte[] data )
	{

		this.data = new byte[data.length]; // copy the data
		System.arraycopy( data, 0, this.data, 0, data.length );
		
		createPacket();
		
	} // end setData
	
	
	/**
	 * Accessor method for the sequence number. 
	 * 
	 * @return the sequence number for the data.
	 */
	public int getSeqNum( )
	{
		return seqNum;
		
	} // end getSeqNum
	

	/**
	 * Mutator method for the sequence number. 
	 * 
	 * @return the sequence number for the data.
	 */
	protected void setSeqNum( int seqNum )
	{
		this.seqNum = seqNum;
		createPacket();
		
	} // end setSeqNum
		
	
} // end BufferedData 
