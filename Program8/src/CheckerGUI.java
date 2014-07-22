import javax.swing.JFrame;

/**
 * Main class that adds CheckersPanel to the frame.
 * 
 * @author Femi Adeyemi
 * 
 */

public class CheckerGUI {
	public static JFrame no ;
	public static void main(String[] args) {
		display();
	}
	public static void close(){
		no.dispose();
	}
	public static void display(){
		no = new JFrame("Femi's Checker Board");
		CheckersPanel joke = new CheckersPanel();
		no.getContentPane().add(joke);
		no.pack();
		no.setLocation(100,100);
		no.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		no.setVisible(true);
		
	}

}
