import javax.swing.JFrame;
import javax.swing.JMenuBar;


/**
 * Setup the frame
 * 
 * You may need to edit this file to implement window closing functionality
 * 
 * @author Lukasz Opyrchal
 *
 */
public class CSAEdit {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("CSA Editor");
		
		//create menu bar
		JMenuBar menuBar = new JMenuBar();
		EditorPanel content = new EditorPanel(frame, menuBar);
		
		frame.setJMenuBar( menuBar );

		frame.getContentPane().add(content);
		frame.pack();  // Set to preferred size of contents
		frame.setLocation(100,100);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible(true);
	}

}


