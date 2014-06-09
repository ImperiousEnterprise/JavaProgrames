import javax.swing.*;

/**
 *Class creates a new JFrame 
 *@author Femi Adeymi
 */
public class About  {
	
	public About(){
		
	}
	
	 /**
	  *Displays the name of the application and who it 
	  *was done by.
	  */
	public void Aboutbox(){
		JFrame cake = new JFrame("About");
		cake.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel core = new JPanel();
		JTextArea low = new JTextArea(10,20);
		low.setText("Application Name: CSA Editor - 271" + " \n" + "Done By: Femi Adeyemi");
		
		core.add(low);
		cake.getContentPane().add(core);
		cake.pack();
		cake.setVisible(true);
	}
}




