/**
 * CalcPanel Class 
 * Sets Up main panel of calculator
 * author:  Femi Adeyemi
 *  */
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class CalcPanel extends JPanel

{
	private JLabel Numerator, Denominator;
	private JTextField numer, deno;
	private JRadioButton num, den;
	private JPanel ZPanel = new JPanel(new GridLayout(3,3,3,3));
	private JPanel Panel = new JPanel(new GridLayout(3,3,2,1));
	private JPanel LPanel = new JPanel(new FlowLayout());
	private JPanel TPanel = new JPanel(new FlowLayout());
	private JPanel KPanel = new JPanel(new BorderLayout());
	private JPanel GPanel = new JPanel(new BorderLayout());
	private JButton Key[] = new JButton[18];
	String value = "";
	String alue = "";
	char o;
	public int v1, v2, v3, v4;

	public CalcPanel() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ButtonListener listener = new ButtonListener();

		Numerator = new JLabel("numerator");
		numer = new JTextField(8);
		num = new JRadioButton("");
		num.addActionListener(listener);

		Denominator = new JLabel("denominator");
		deno = new JTextField(8);
		den = new JRadioButton("");
		den.addActionListener(listener);

		setPreferredSize(new Dimension(300, 400));
		/**
		 * Puts JField, JRadioButton, JLabel in each of their own Flow Panel
		 */
		TPanel.add(Numerator);
		TPanel.add(numer);
		TPanel.add(num);
		add(TPanel);

		LPanel.add(Denominator);
		LPanel.add(deno);
		LPanel.add(den);
		add(LPanel);
		
		/**
		 * Sets first 9 buttons into the Key array
		 */
		for (int i = 1; i <= 9; i++) {

			Key[i] = new JButton(String.valueOf(i));
			Key[i].addActionListener(listener);
			Panel.add(Key[i]);
		}
		/**
		 * Puts all button functions on the center panel of KPanel which 
		 * then is added to the main panel.
		 */
		KPanel.add(Panel, BorderLayout.CENTER);
		add(KPanel);

		/**
		 * Sets main math buttons into the rest of Key array
		 */
		Key[11] = new JButton("+");
		Key[11].addActionListener(listener);
		ZPanel.add(Key[11]);

		// subtract
		Key[12] = new JButton("-");
		Key[12].addActionListener(listener);
		ZPanel.add(Key[12]);

		// Multiply
		Key[13] = new JButton("*");
		Key[13].addActionListener(listener);
		ZPanel.add(Key[13]);
		
		// Divide
		Key[14] = new JButton("/");
		Key[14].addActionListener(listener);
		ZPanel.add(Key[14]);
		
		// Equal
		Key[15] = new JButton("=");
		Key[15].addActionListener(listener);
		ZPanel.add(Key[15]);

		Key[16] = new JButton("inv");
		Key[16].addActionListener(listener);
		ZPanel.add(Key[16]);
		
		/**
		 * Puts mathmatic functions on the center panel of GPanel which 
		 * then is added to the main panel.
		 */
		GPanel.add(ZPanel, BorderLayout.CENTER);
		add(GPanel);
		
		/**
		 * Puts 0 and C on the south panel of KPanel which 
		 * then is added to the main panel.
		 */
		Panel = new JPanel(new GridLayout(2,2));

		Key[0] = new JButton("0");
		Key[0].addActionListener(listener);
		Panel.add(Key[0]);
		
		Key[17] = new JButton("C");
		Key[17].addActionListener(listener);
		Panel.add(Key[17]);
		KPanel.add(Panel, BorderLayout.SOUTH);

	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			/**
			 * Calls arithmetic class
			 */
			arithmetic a = new arithmetic();
			/**
			 * Sets desired action if either radio button is selected			 
			 */
			if (num.isSelected()) {
				for (int i = 0; i <= 9; i++) {
					if (event.getSource() == Key[i]) {
						value += i;
						numer.setText(value);
						v1 = Integer.parseInt(numer.getText());
					}
				}
			}else if (den.isSelected()) {
				for (int i = 0; i <= 9; i++) {
					if (event.getSource() == Key[i]) {
						alue += i;
						deno.setText(alue);
						v2 = Integer.parseInt(deno.getText());
					}
				}
			}
			if (event.getSource() == Key[11]) {
				v1 = Integer.parseInt(numer.getText());
				v2 = Integer.parseInt(deno.getText());
				o = '+';
				value = "";
				alue = "";
				numer.setText("" + value);
				deno.setText("" + alue);
			}
			if (event.getSource() == Key[12]) {
				v1 = Integer.parseInt(numer.getText());
				v2 = Integer.parseInt(deno.getText());
				o = '-';
				value = "";
				alue = "";
				numer.setText("" + value);
				deno.setText("" + alue);
			}
			if (event.getSource() == Key[13]) {
				v1 = Integer.parseInt(numer.getText());
				v2 = Integer.parseInt(deno.getText());
				o = '*';
				value = "";
				alue = "";
				numer.setText("" + value);
				deno.setText("" + alue);
			}
			if (event.getSource() == Key[14]) {
				v1 = Integer.parseInt(numer.getText());
				v2 = Integer.parseInt(deno.getText());
				o = '/';
				value = "";
				alue = "";
				numer.setText("" + value);
				deno.setText("" + alue);
			}

			/**
			 * Runs the error message if either field is empty or a letter is entered.
			 */
			try {
				if (event.getSource() == Key[11] ) {
					o = '+';
					value = "";
					alue = "";
					numer.setText("" + value);
					deno.setText("" + alue);
				} else if(event.getSource() == Key[12]){
					o = '-';
					value = "";
					alue = "";
					numer.setText("" + value);
					deno.setText("" + alue);
				}
				
				if (event.getSource() == Key[15]) {
					value = "";
					alue = "";
					v3 = Integer.parseInt(numer.getText());
					v4 = Integer.parseInt(deno.getText());

					/**
					 * Displays error messages if negative in denominator 
					 * or zero in numerator
					 */
					if (v1 == 0 || v2 <= 0 || v3 == 0 || v4 <= 0) {
						JOptionPane.showMessageDialog(null,"Error. Either Zero in denominator or negative in denominator.Stop it. Shame on you");
					} else {
						if (o == '+') {
							a.addition(v1, v2, v3, v4);
						} else if (o == '-') {
							a.subtraction(v1, v2, v3, v4);
						} else if (o == '*') {
							a.multiplication(v1, v2, v3, v4);
						} else if (o == '/') {
							a.divide(v1, v2, v3, v4);
						} else {
						}
						numer.setText("" + a.reveal());
						deno.setText("" + a.reveal2());
					}
				}
				if (event.getSource() == Key[16]) {
					int box = Integer.parseInt(numer.getText());
					int box2 = Integer.parseInt(deno.getText());
					a.inverse(box, box2);
					numer.setText("" + a.reveal2());
					deno.setText("" + a.reveal());
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Error. Shame on you. Enter a number");
			}
			if (event.getSource() == Key[17]) {
				value = "";
				alue = "";
				numer.setText("");
				deno.setText("");
			}

		}
	}
}
