import javax.swing.JFrame;


/**
 * Driver class for the Fraction Calculator application.
 * this class contains only the main method which
 * starts the GUI
 * 
 * @author Lukasz Opyrchal
 *
 */
public class Calculator {

	
	   public static void main(String[] args) {
		      JFrame frame = new JFrame("Simple Calculator");
		      CalcPanel content = new CalcPanel();
		      
		      frame.getContentPane().add(content);
		      frame.pack();  // Set to preferred size of contents
		      frame.setLocation(100,100);
		      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		      frame.setVisible(true);
		   }
}
