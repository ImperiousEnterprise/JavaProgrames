import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/*
 * Created on Mar 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Eric Bachmann
 * @author Lukasz Opyrchal
 *	
 */
public class CheckSum 
{

	/**
	 * Calculates a negative checksum for the supplied sequence number and data using 
	 * exclusive or and negation. The negative checksum is returned as a short.
	 * 
	 * @param data data for which the negative checksum should be calculated
	 * @return negative checksum
	 */
	public static short calculateNegativeChecksum( byte[] data )
	{
		return (short) -calculateChecksum( data );		
		
	} // end calculateChecksum
	
	
	/**
	 * Calculates a checksum for the supplied data using 
	 * exclusive or. The checksum is returned as a short.
	 * 
	 * @param data data for which the checksum should be calculated
	 * @return checksum
	 */	
	public static short calculateChecksum( byte[] data )
	{
		short checksum = 0;
		
		//System.out.println("CheckSum: data length: " + data.length);
		DataInputStream dataArrayStream = new DataInputStream( new ByteArrayInputStream( data ) );
		
		int loopLimit = data.length / 2;
		
		for(int i = 0; i < (loopLimit); i++) {
			
			try {
				checksum = (short) (checksum ^ dataArrayStream.readShort());
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		if ( data.length % 2 == 1 ) {
			
			try {
				checksum = (short) (checksum ^ (short)dataArrayStream.readByte());
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		

		return checksum;	
		
	} // end calculateChecksum	


}
