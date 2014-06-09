
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class SimpleGUI extends JFrame implements ActionListener{
	protected NumberPrinter go;

	// ID number for serialization
	private static final long serialVersionUID = 1L;
	
	public SimpleGUI(NumberPrinter g )
	{
		this("GUI Thread");
		this.go = g;
	}

	public SimpleGUI(String guiName )
	{
		super(guiName);
		
		this.setSize( new Dimension(100, 100));

		Container contentPane = this.getContentPane();
		
		// Button to be pressed when to display numbers
		JButton numberButton= new JButton("Print Numbers");
		
		contentPane.add( numberButton );
		
		// Register a listener for the request game button
		numberButton.addActionListener( this);
		
		// Make the window visible
		this.setVisible(true);
		
		// Stop the program when the window is closed.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	} // end TicToeBoard constructor
	
			
	public void actionPerformed(ActionEvent event) 
	{
		int lineCounter = 1;
		for(int i = 300; i < 400; i = i + 10){
			
			go.printTenNumbers(i);
			
			try {
				   Thread.sleep((long)(Math.random() * 10));
			} catch (InterruptedException e) {}

					
		}
		
		System.out.println( this.getName() + " completed.");

	} // end actionPerformed

	
} // end SimpleGUI class

