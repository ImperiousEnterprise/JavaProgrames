import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * Class basically saves saves and loads the data
 * from CheckersPanel.
 * 
 * @author Femi Adeyemi
 * 
 */

public class Filesave {
	/**  loads game data from a binary file.
	 * 
	 *  バイナリファイルからゲームセーブをロードする。
	 */
	public JButton[][] loadgame (String filename)
	{   
		JButton Return [][] = null;
		
		filename = filename + ".bin";
		if (filename.length () > 0)
		{			
			try
			{
				FileInputStream fis = new FileInputStream (filename);
				ObjectInputStream ois = new ObjectInputStream (fis);
				Return = (JButton[][]) ois.readObject();
				ois.close ();
				fis.close ();
				System.out.println("File Loaded");
			}
			catch (Exception e)
			{
				e.printStackTrace ();
			}
			
	
		}else{
			JOptionPane
			.showMessageDialog(null,
					"No file name was given.");
		}
		return Return;
	}
	 
	/**  Saves game data to a binary file.
	 * 
	 * バイナリファイルにゲームセーブをする
	 */
	 
	public void savegame (String filename,JButton [][] Key)
	{	
		filename = filename + ".bin";
		if (filename.length () > 0)
		{		
			try
			{
				
		
				
				FileOutputStream fos = new FileOutputStream (filename);
				ObjectOutputStream oos = new ObjectOutputStream (fos);
				//JButton [][] trail = c.arrayshot();
				oos.writeObject (Key);
				oos.flush();
				fos.close ();
				
			}
			catch (Exception e)
			{
				e.printStackTrace ();
			}
			
		}else{
			JOptionPane
			.showMessageDialog(null,
					"Please give file name.");
		}
	}
	 
}
