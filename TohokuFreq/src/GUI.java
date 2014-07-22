// By:　Adefemi Adeyemi
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

public class GUI {
	
	/**テキストファイル入力 **/
	JTextField Input;
	/**テキストファイル検索 **/
	JTextField Search;
	/**テキストファイル出力フィールド **/
	JTextArea Output;
	/**JPanelはJTextField、JTextArea、JButtonを保持します**/
	JPanel viewr;
	/**単語が現れるという回の数を数えます。**/
	int count = 0;
	
	public static void main(String[] args) {
		display();
	}

	public static void display(){
		JFrame no = new JFrame("文字頻度カウンタ");
		GUI g = new GUI();
		no.setContentPane(g.BuildViewer());
		no.pack();
		no.setLocation(100,100);
		no.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		no.setSize(500, 400);
		no.setVisible(true);
		
		}
	public JPanel BuildViewer(){
		SpringLayout layout = new SpringLayout();
		viewr = new JPanel(layout);
	
		Input = new JTextField(25);
		Input.setEditable(false);
		Search = new JTextField(20);
		Search.setEditable(false);
		
		Dimension buttonsize = new Dimension(130,20);
		ButtonListener Button = new ButtonListener();
		
		JButton InputBrowse = new JButton();
		InputBrowse.setText("ファイル選択");
		InputBrowse.setPreferredSize(buttonsize);
		InputBrowse.addActionListener(Button);
		

		JButton Submit = new JButton();
		Submit.setText("文字頻度ゲート");
		Submit.setPreferredSize(new Dimension(140,20));
		Submit.addActionListener(Button);
		
		JButton Serch = new JButton();
		Serch.setText("検索");
		Serch.setPreferredSize(buttonsize);
		Serch.addActionListener(Button);
		
		JButton Save = new JButton();
		Save.setText("出力保存");
		Save.setPreferredSize(buttonsize);
		Save.addActionListener(Button);
		
		Output = new JTextArea();
		Output.setEditable(false);
		JScrollPane scroll = new JScrollPane(); 
		scroll.setViewportView(Output);
		scroll.setPreferredSize(new Dimension(400,200));
		
	
		JLabel SearchLabel = new JLabel("テキストサーチ: ");
		
	    viewr.add(Input);
	    viewr.add(InputBrowse);
	    viewr.add(Submit);
	    
	    viewr.add(SearchLabel);
	    viewr.add(Search);
	    viewr.add(Serch);
	    
	    viewr.add(scroll);
	    
	    viewr.add(Save);

	    layout.putConstraint(SpringLayout.WEST, InputBrowse,5,SpringLayout.WEST, viewr);
	    layout.putConstraint(SpringLayout.NORTH, InputBrowse,15,SpringLayout.NORTH, viewr);
	    
	    layout.putConstraint(SpringLayout.WEST, Input, 5, SpringLayout.EAST, InputBrowse);
	    layout.putConstraint(SpringLayout.NORTH, Input, 15, SpringLayout.NORTH, viewr);
	    
	    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Submit, 0, SpringLayout.HORIZONTAL_CENTER, viewr);
	    layout.putConstraint(SpringLayout.NORTH, Submit, 10, SpringLayout.SOUTH, InputBrowse);
	    
	    layout.putConstraint(SpringLayout.WEST, SearchLabel, 5, SpringLayout.WEST, viewr);
	    layout.putConstraint(SpringLayout.NORTH, SearchLabel, 10, SpringLayout.SOUTH, Submit);
	    
	    layout.putConstraint(SpringLayout.WEST, Search, 5, SpringLayout.EAST, SearchLabel);
	    layout.putConstraint(SpringLayout.NORTH, Search, 10, SpringLayout.SOUTH, Submit);
	    
	    layout.putConstraint(SpringLayout.WEST, Serch, 5, SpringLayout.EAST, Search);
	    layout.putConstraint(SpringLayout.NORTH, Serch, 10, SpringLayout.SOUTH, Submit);
	    
	    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scroll, 0, SpringLayout.HORIZONTAL_CENTER, viewr);
	    layout.putConstraint(SpringLayout.NORTH, scroll, 10, SpringLayout.SOUTH, Serch);
	    
	    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Save, 0, SpringLayout.HORIZONTAL_CENTER, viewr);
	    layout.putConstraint(SpringLayout.NORTH, Save, 10, SpringLayout.SOUTH, scroll);
	    
	    return viewr;
	 	}
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser pick = new JFileChooser();
			FileInput in;
			if(e.getActionCommand()=="ファイル選択"){
				
			    pick.setCurrentDirectory(new java.io.File("."));
			    pick.setDialogTitle("テキストファイルかテキストファイルディレクトリを選択してください。");
				pick.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			
				int option = pick.showOpenDialog(viewr);
	            if (option == JFileChooser.APPROVE_OPTION) {
	              String filelist = pick.getSelectedFile().getPath().toString();
	              Input.setText(filelist);
	              Output.setText("");
	              Search.setText("");
	            }
				
			}else if(e.getActionCommand() == "文字頻度ゲート" && !Input.getText().isEmpty()){
				in = new FileInput(Input.getText());
				String p = in.MapToString();
				Output.setEditable(true);
				Search.setEditable(true);
				Output.setText(p);
				
			}else if(e.getActionCommand() == "検索" && !Search.getText().isEmpty()){
				find(Search.getText());
			}else if(e.getActionCommand() == "出力保存" && !Output.getText().isEmpty()){
				Save(pick,Output.getText());
			}
			
		}
	}
	  /**
     * 出力JTextAreaに言葉を探す。
     * 
     * @param ｆind
     *            探す言葉
     */
	 public void find(String find){
	
			String con = Output.getText();
			int in = con.indexOf(find, 0);
			if (in >= 0 && count == 0) {
				int end = in + find.length();
				Output.requestFocus();

				Output.setCaretPosition(in);
				Output.moveCaretPosition(end);
				count++;
			}else if(count > 0){
				int	jok = Output.getText().indexOf(find, Output.getCaretPosition());
				int en = jok + find.length();
				
				if (en == -1 || jok == -1) {
						JOptionPane.showMessageDialog(null, "他の"
								+ find + "が見つけれなかったんです。");
						count = 0;
					} else {
						count++;
						Output.requestFocus();
						Output.setCaretPosition(jok);
						Output.moveCaretPosition(en);
					}
				
			} else {
				JOptionPane.showMessageDialog(null, find + "が見つけれなかったんです。");
			}
	 }
	  /**
	     * 出力テキストをファイルに保存する
	     * 
	     * @param choose
	     *            ファイル保存ツール
	     * @param name
	     *            出力テキスト
	     */
	 public void Save(JFileChooser choose, String name){
		choose.setDialogTitle("ファイル拡張子を除いた出力ファイル名を書いてください。");
		int option = choose.showSaveDialog(viewr);
		 
         if (option == JFileChooser.APPROVE_OPTION) {
         	String save = choose.getSelectedFile().getAbsolutePath()+".txt";
             BufferedWriter bw;
             try {
                 bw = new BufferedWriter(new FileWriter(save));
                 bw.write(name);
                 bw.flush();
                 bw.close();       
             }               
             catch (IOException e1)
             {
                 e1.printStackTrace();
             }
         }
         
         JOptionPane.showMessageDialog(null,"出力ファイルを作成しました");
		 
	 }
}
