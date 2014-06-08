import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 */

/**
 * @author adeyemar
 *
 */
public class ReadingWritingData {

	/**
	 * @param args
	 */
	protected String filename;

	protected int int1, int2;
	protected float float1, float2;
	protected double double1, double2;
	protected long long1, long2;
	public ReadingWritingData(String filename) {

		this.filename = filename;

		readFile(); // Call reading method

		writeNumericValues();// Call writing methods
		writeStringValues(); 
		writeToSocket();
		


	} // end constructor
	public void readFile() 
	{
		try {
			FileInputStream fis;
			fis = new FileInputStream(this.filename);
			DataInputStream dis = new DataInputStream (fis);
			int1 = dis.readInt();	
			int2 = dis.readInt();
			float1 = dis.readFloat();
			float2 = dis.readFloat();
			double1 = dis.readDouble();
			double2 = dis.readDouble();
			long1 = dis.readLong();
			long2 = dis.readLong();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
	} // end readFile

	public void writeNumericValues() 
	{
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("numericTypes.dat");
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(int1);
			dos.writeInt(int2);
			dos.writeFloat(float1);
			dos.writeFloat(float2);
			dos.writeDouble(double1);
			dos.writeDouble(double2);
			dos.writeLong(long1);
			dos.writeLong(long2);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	} // end writeNumericValues
	public void writeStringValues() 
	{
		FileWriter fw;
		try {
			fw = new FileWriter("textTypes.dat");
			BufferedWriter bw = new BufferedWriter(fw);	
			bw.write(int1 + " " + int2 + " " + float1 + " " + float2 + " " + double1 + " " + double2 + " " + long1 + " " + long2 + " ");
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end writeStringValues
	
	public void writeToSocket(){
		DataOutputStream dos;
		Socket socket;
		
		try {
			socket = new Socket(InetAddress.getByName("10.33.2.203"),32100);
			
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeInt(int1);
			dos.writeInt(int2);
			dos.writeFloat(float1);
			dos.writeFloat(float2);
			dos.writeDouble(double1);
			dos.writeDouble(double2);
			dos.writeLong(long1);
			dos.writeLong(long2);
			
			
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void sendPrimitiveValues() 
	{
		//DataOutputStream dos;
		//Socket socket;
		
		

	} // end sendPrimitiveValues


	
	public static void main(String[] args) {
		//System.out.println("FEMI");
		// TODO Auto-generated method stub
		new ReadingWritingData("differentTypes.dat");
	}

}
