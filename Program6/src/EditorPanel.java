
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.*;
import java.util.Scanner;


/**
 * This class sets up the CSA Editor GUI
 * 
 * @author Lukasz Opyrchal
 * @author Femi Adeyemi
 */
public class EditorPanel extends JPanel {

	// constants for menus and menu items
	private final static String FILE = "File";
	private final static String NEW = "New";
	private final static String OPEN = "Open";
	private final static String SAVE = "Save";
	private final static String SAVEAS = "Save As";
	private final static String EXIT = "Exit";
	private final static String EDIT = "Edit";
	private final static String FIND = "Find";
	private final static String FINDNEXT = "Find Next";
	private final static String REPLACE = "Replace";
	private final static String HELP = "Help";
	private final static String ABOUT = "About";

	private String v; // Stores anything written in editor
	private int jok; //Stores indexOf editor
	public File file; //Stores name of File
	private JTextArea editor; // the editor text area
	private Scanner scan; //Allows for each line in a file to read and moved to editor
	private JFrame frame; // reference to the frame -

	// so that I can change the title bar
	public EditorPanel(JFrame frame, JMenuBar menuBar) {

		this.frame = frame;

		editor = new JTextArea(20, 80);
		editor.setTabSize(4);

		JMenu fileMenu = makeFileMenu();
		JMenu editMenu = makeEditMenu();
		JMenu helpMenu = makeHelpMenu();

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JScrollPane pane = new JScrollPane(editor);
		add(pane);
	}

	/**
	 * Create and return the File Menu You will need to edit this function to
	 * add appropriate listener(s)
	 * 
	 * @return the created file menu
	 */
	private JMenu makeFileMenu() {
		JMenuItem item;
		JMenu fileMenu = new JMenu(FILE);

		menuListener no = new menuListener();
		item = new JMenuItem(NEW);
		item.addActionListener(no);
		fileMenu.add(item);

		item = new JMenuItem(OPEN);
		item.addActionListener(no);
		fileMenu.add(item);

		item = new JMenuItem(SAVE);
		item.addActionListener(no);
		fileMenu.add(item);

		item = new JMenuItem(SAVEAS);
		item.addActionListener(no);
		fileMenu.add(item);

		item = new JMenuItem(EXIT);
		item.addActionListener(no);
		fileMenu.add(item);

		return fileMenu;
	}

	/**
	 * Create and return the Help Menu You will need to edit this function to
	 * add appropriate listener(s)
	 * 
	 * @return the created file menu
	 */
	private JMenu makeHelpMenu() {
		JMenu helpMenu = new JMenu(HELP);
		menuListener zo = new menuListener();
		JMenuItem item = new JMenuItem(ABOUT);
		item.addActionListener(zo);
		helpMenu.add(item);

		return helpMenu;
	}

	/**
	 * Create and return the Edit Menu You will need to edit this function to
	 * add appropriate listener(s)
	 * 
	 * @return the created file menu
	 */
	private JMenu makeEditMenu() {
		menuListener po = new menuListener();

		JMenu editMenu = new JMenu(EDIT);
		JMenuItem item = new JMenuItem(FIND);
		item.addActionListener(po);
		editMenu.add(item);
		item = new JMenuItem(FINDNEXT);
		item.addActionListener(po);
		editMenu.add(item);
		item = new JMenuItem(REPLACE);
		item.addActionListener(po);
		editMenu.add(item);

		return editMenu;
	}

	private class menuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == NEW) {
				New();
			} else if (e.getActionCommand() == OPEN) {
				Open();
			} else if (e.getActionCommand() == SAVEAS) {

				try {
					Saveas();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (e.getActionCommand() == SAVE) {
				try {
					Save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (e.getActionCommand() == EXIT) {
				Exit();
			} else if (e.getActionCommand() == FIND) {
				Find();
			} else if (e.getActionCommand() == FINDNEXT) {
				FindNext();
			} else if (e.getActionCommand() == REPLACE) {
				Replace();
			} else if (e.getActionCommand() == ABOUT) {
				About tor = new About();
				tor.Aboutbox();
			}

		}

	}
	/**
	 * Basically creates a new document 
	 */
	public void New() {
		int again = JOptionPane.showConfirmDialog(this,
				"Do you want to save the current file first?");
		if (again == JOptionPane.YES_OPTION) {
			try {
				Saveas();
				frame.setTitle("CSA Editor");
				editor.setText("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			frame.setTitle("CSA Editor");
			editor.setText("");
		}
	}
	/**
	 * Opens a new document by using JFileChooser to select a file. 
	 */
	private void Open() {
		if (!(editor.getText().equals(""))) {
			New();
		}
		JFileChooser choose = new JFileChooser();
		int stat = choose.showOpenDialog(null);
		if (stat != JFileChooser.APPROVE_OPTION) {
			editor.setText("No File Chosen");
		} else {
			file = choose.getSelectedFile();

			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			frame.setTitle("CSA Editor - " + file.getName());
			String info = "";
			while (scan.hasNext())
				info += scan.nextLine() + "\n";
			editor.setText(info);
		}

	}
	/**
	 * Handles the Save As function by using JFileChooser 
	 * to store the file in File file then using BufferedWriter
	 * to write the file. 
	 */
	public void Saveas() throws IOException {
		JFileChooser chose = new JFileChooser();
		int stat = chose.showSaveDialog(null);
		file = chose.getSelectedFile();
		if (stat != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file saved.");
		} else {
			if (file.exists() == true) {
				int show = JOptionPane
						.showConfirmDialog(null,
								"File Already exist. Do you want to overwrite the existing file?");
				if (show == JOptionPane.YES_OPTION) {
					Save();
				} else if (show == JOptionPane.NO_OPTION) {
					Offsave();
				}
			} else {
				BufferedWriter rile = new BufferedWriter(new FileWriter(file));
				frame.setTitle("CSA Editor - " + file.getName());
				String s = editor.getText();
				rile.write(s);
				try {
					if (rile != null) {
						rile.flush();
						rile.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		}

	}
	/**
	 * Works with the Save method to allow user to save a file 
	 * if chosen not to overwrite file. 
	 */
	public void Offsave() throws IOException {
		JFileChooser choose = new JFileChooser();
		choose.showSaveDialog(null);
		File lile = choose.getSelectedFile();
		BufferedWriter kile = new BufferedWriter(new FileWriter(lile));
		frame.setTitle("CSA Editor - " + lile.getName());
		String s = editor.getText();
		kile.write(s);
		try {
			if (kile != null) {
				kile.flush();
				kile.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Overwrites the previous content of a file.
	 */
	public void Save() throws IOException {
		if (file == null) {
			Saveas();
		} else {
			File like = file;
			BufferedWriter kile = new BufferedWriter(new FileWriter(like));
			frame.setTitle("CSA Editor - " + like.getName());
			String s = editor.getText();
			kile.write(s);
			try {
				if (kile != null) {
					kile.flush();
					kile.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Just exits the program.
	 * Asks user if the want to save
	 * if not simply executes an exit.
	 */
	public void Exit() {
		if ((!(editor.getText().equals(""))) && file == null ) {			
			int show = JOptionPane.showConfirmDialog(null,
					"Do you want to save?");
			if (show == JOptionPane.YES_OPTION) {
				try {
					Offsave();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(1);
			} else {
				System.exit(0);
			}
		} else {
			int low = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit?");
			if (low == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		}
	}
	/**
	 * Search the text for the given term (starting from beginning) 
	 * and highlight the first occurrence (if found)
	 */
	public void Find() {
		String go = JOptionPane.showInputDialog("Enter a word");

		v = go;
		if (v.length() <= 0) {
			JOptionPane.showMessageDialog(null, "Term " + v + " not found");
		}
		String con = editor.getText();
		int in = con.indexOf(v, 0);
		if (in >= 0) {
			int end = in + v.length();
			editor.requestFocus();

			editor.setCaretPosition(in);
			editor.moveCaretPosition(end);
		} else {
			JOptionPane.showMessageDialog(null, "Term " + v + " not found.");
		}
	}
	/**
	 * Searchs for the previously entered search 
	 * term starting at the current cursor position 
	 */
	public void FindNext() {

		String doz = v;
		String du = editor.getText();
		if (du == null || doz == null) {
			JOptionPane.showMessageDialog(null,
					"Nothing found. Press the Find button first.");
		} else {
			jok = du.indexOf(doz);
			jok = du.indexOf(doz, editor.getCaretPosition());

			int en = jok + doz.length();
			if (en == -1 || jok == -1) {
				JOptionPane.showMessageDialog(null, "No more occurances of "
						+ doz + " found");
			} else {
				editor.requestFocus();

				editor.setCaretPosition(jok);
				editor.moveCaretPosition(en);
			}
		}
	}
	/**
	 * Replaces all occurances of search term.
	 */
	public void Replace() {
		String door = JOptionPane.showInputDialog("Type word to be replaced.");
		String koor = JOptionPane.showInputDialog("Type replacement word. ");
		if (door == null || koor == null) {
			JOptionPane.showMessageDialog(null, "Nothing replaced.");
		} else {
			String poor = editor.getText().replaceAll(door, koor);
			editor.setText(poor);
		}

	}

}
