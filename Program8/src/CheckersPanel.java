import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Basically sets up board and moves for each piece.
 * 
 * @author Femi Adeyemi
 * 
 */

public class CheckersPanel extends JPanel implements Serializable {

	public JButton Key[][] = new JButton[8][8];
	private final static String FILE = "File";
	private final static String NEW = "New Game";
	private final static String LOAD = "Load Game";
	private final static String SAVE = "Save Game";
	private JLabel Turns;
	private JPanel grid = new JPanel(new GridLayout(8, 8));;
	private JPanel gr;
	private int i, k;
	private int turn = 1;
	private int torow, tocol;
	private int fromrow, fromcol;
	private int whitefromrow, whitefromcol;
	private int whitetorow, whitetocol;
	private int blackfromrow, blackfromcol;
	private int blacktorow, blacktocol;
	private int jumpro, jumpco;
	private int scoreblack = 0, scoreWhite = 0;
	private String no;
	private boolean face = true;

	public CheckersPanel() {
		setLayout(new BorderLayout());
		JMenu fileMenu = makeFilemenu();
		JMenuBar kool = new JMenuBar();
		JMenuBar nool = new JMenuBar();
		kool.add(fileMenu);
		add(kool, BorderLayout.NORTH);
		NewGame();
		add(grid, BorderLayout.CENTER);
		Turns = new JLabel("White goes first. ");
		nool.add(Turns);
		add(nool, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(450, 450));
	}

	private JMenu makeFilemenu() {
		JMenuItem item;
		Butlistener no = new Butlistener();
		JMenu fileMenu = new JMenu(FILE);

		item = new JMenuItem(NEW);
		item.addActionListener(no);
		fileMenu.add(item);

		item = new JMenuItem(LOAD);
		item.addActionListener(no);
		fileMenu.add(item);

		item = new JMenuItem(SAVE);
		item.addActionListener(no);
		fileMenu.add(item);

		return fileMenu;
	}

	/**
	 * Calls Actionlistner for all buttons and menu items.
	 * 
	 * すべてのボタンとメニュー項目のためにActionlistnerと呼びます。. 
	 */
	private class Butlistener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			CheckerGUI kook = new CheckerGUI();
			Filesave mi = new Filesave();
			if (e.getActionCommand() == SAVE) {
				String dame = JOptionPane
						.showInputDialog("Enter a file name");
				mi.savegame(dame,Key);
			} else if (e.getActionCommand() == LOAD) {
				String go = JOptionPane
						.showInputDialog("Enter a file name");
				boolean confirm = go != null;
				ReturnGame(confirm,go);
			} else if (e.getActionCommand() == NEW) {
				switch(turn){
				case 1:
					JOptionPane
					.showMessageDialog(null,
							"White resigns!!!Black wins. **Congratulations**");
					break;
				case 2:
					JOptionPane
					.showMessageDialog(null,
							"Black resigns!!White wins. **Congratulations**");
				}
				kook.close();
				kook.display();
			} else {
				for (i = 0; i < 8; i++) {

					for (k = 0; k < 8; k++) {
						if ((i % 2) == (k % 2)) {
							Key[i][k].setEnabled(false);
						} else {
							if (e.getSource() == Key[i][k]) {
								if (turn == 1) {
									whitemove();
								} else if (turn == 2) {
									blackmove();
								}
							}
						}
					}//for loop
				}// for loop

			}//else 
		}
	}

	/**
	 * Defines whites moves.
	 * 
	 * 白の動きを定義します。
	 */
	public void whitemove() {
		if (face == true) {
			if (Key[i][k].getText().equals("W")) {
				whitefromrow = i;
				whitefromcol = k;
				whiteforcejump();
			} else if (Key[i][k].getText().equals("KW")) {
				Kingmove();

			}
		} else {
			turn = 2;
			torow = i;
			tocol = k;
			face = true;
			whitetorow = i;
			whitetocol = k;
			whiteillegalmove();
			dojump(whitefromrow, whitetorow, whitefromcol, whitetocol);
			King(whitetorow);
			Turns.setText("It's black turn");
		}
		if (scoreWhite == 12) {
			JOptionPane.showMessageDialog(null,
					"White wins. **Congratulations**");
			CheckerGUI kook = new CheckerGUI();
			kook.display();
		}
	}

	/**
	 * Define blacks moves.
	 * 
	 * 黒の動きを定義します。
	 */
	public void blackmove() {
		if (face == true) {
			if (Key[i][k].getText().equals("B")) {
				blackfromrow = i;
				blackfromcol = k;
				blackforcejump();

			} else if (Key[i][k].getText().equals("KB")) {
				blackfromrow = i;
				blackfromcol = k;
				Kingmove();
			}
		} else {
			face = true;
			torow = i;
			tocol = k;
			blacktorow = i;
			blacktocol = k;
			turn = 1;
			blackillegalmove();
			if (jump() == true) {
				dojump(blackfromrow, blacktorow, blackfromcol, blacktocol);
			} else {

				setpiece(blacktorow,blacktocol);
			}
			King(blacktorow);
			Turns.setText("It's whites turn");
		}
		if (scoreblack == 12) {
			JOptionPane.showMessageDialog(null,
					"Black wins. **Congratulations**");
			CheckerGUI kook = new CheckerGUI();
			kook.display();
		}
	}

	/**
	 * Checks if jump is being done.
	 * 
	 * ジャンプが完了しているかどうかチェックします。. 
	 */
	private boolean jump() {
		if ((whitefromrow - whitetorow == 2 || whitefromrow - whitetorow == -2)) {
			return true;
		} else if ((blackfromrow - blacktorow == 2 || blackfromrow - blacktorow == -2)) {
			return true;
		}
		return (fromrow - torow == 2 || fromrow - torow == -2);
	}

	/**
	 * Forces black piece to jump anytime near white piece file.
	 * 
	 * 黒い部分に白い部分の近くにいつでもとぶことを強います
	 */
	public void blackforcejump() {
		if (inRange(blackfromrow - 1, blackfromcol + 1)
				&& inRange(blackfromrow - 2, blackfromcol + 2)
				&& (Key[blackfromrow - 1][blackfromcol + 1].getText().equals(
						"W") || Key[blackfromrow - 1][blackfromcol + 1]
						.getText().equals("KW"))
				&& Key[blackfromrow - 2][blackfromcol + 2].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Force Jump applied");
			turn = 1;
			torow = i;
			tocol = k;
			face = true;
			blacktorow = i;
			blacktocol = k;
			Key[i][k].setText("");
			Key[blackfromrow - 1][blackfromcol + 1].setText("");
			Key[blackfromrow - 2][blackfromcol + 2].setForeground(Color.BLACK);
			Key[blackfromrow - 2][blackfromcol + 2].setText("B");
			blackdoublejumps(blacktorow - 2, blacktocol + 2);
			Turns.setText("It's white turn");
			scoreblack++;
		} else if (inRange(blackfromrow - 1, blackfromcol - 1)
				&& inRange(blackfromrow - 2, blackfromcol - 2)
				&& Key[blackfromrow - 1][blackfromcol - 1].getText()
						.equals("W")
				&& Key[blackfromrow - 2][blackfromcol - 2].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Force Jump applied");
			turn = 1;
			torow = i;
			tocol = k;
			face = true;
			blacktorow = i;
			blacktocol = k;
			Key[i][k].setText("");
			Key[blackfromrow - 1][blackfromcol - 1].setText("");
			Key[blackfromrow - 2][blackfromcol - 2].setForeground(Color.BLACK);
			Key[blackfromrow - 2][blackfromcol - 2].setText("B");
			blackdoublejumps(blacktorow - 2, blacktocol - 2);
			// yok[blacktorow - 2][blacktocol - 2] = 2;
			Turns.setText("It's white turn");
			scoreblack++;
		} else {
			Key[i][k].setText("");
			no = "B";
			face = false;
			fromrow = i;
			fromcol = k;

		}
	}

	/**
	 * Forces white piece to jump anytime near black piece file.
	 * 
	 * 白い部分に黒い部分の近くにいつでもとぶことを強います
	 */
	public void whiteforcejump() {
		if (inRange(whitefromrow + 1, whitefromcol + 1)
				&& inRange(whitefromrow + 2, whitefromcol + 2)
				&& Key[whitefromrow + 1][whitefromcol + 1].getText()
						.equals("B")
				&& Key[whitefromrow + 2][whitefromcol + 2].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Force Jump applied");
			turn = 2;
			torow = i;
			tocol = k;
			face = true;
			whitetorow = i;
			whitetocol = k;
			Key[i][k].setText("");
			Key[whitefromrow + 1][whitefromcol + 1].setText("");
			Key[whitefromrow + 2][whitefromcol + 2].setForeground(Color.WHITE);
			Key[whitefromrow + 2][whitefromcol + 2].setText("W");
			scoreWhite++;
			whitedoublejumps(whitefromrow + 2, whitefromcol + 2);
			Turns.setText("It's black turn");
		} else if (inRange(whitefromrow + 1, whitefromcol - 1)
				&& inRange(whitefromrow + 2, whitefromcol - 2)
				&& Key[whitefromrow + 1][whitefromcol - 1].getText()
						.equals("B")
				&& Key[whitefromrow + 2][whitefromcol - 2].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Force Jump applied");
			turn = 2;
			torow = i;
			tocol = k;
			face = true;
			whitetorow = i;
			whitetocol = k;
			Key[i][k].setText("");
			Key[whitefromrow + 1][whitefromcol - 1].setText("");
			Key[whitefromrow + 2][whitefromcol - 2].setForeground(Color.WHITE);
			Key[whitefromrow + 2][whitefromcol - 2].setText("W");
			scoreWhite++;
			whitedoublejumps(whitefromrow + 2, whitefromcol - 2);
			Turns.setText("It's black turn");
		} else {
			Key[i][k].setText("");
			no = "W";
			face = false;
			fromrow = i;
			fromcol = k;
		}
	}

	/**
	 * Checks to make sure all numbers are in range.
	 * 
	 * すべての数が範囲にあるのを確実にするチェック. 
	 */
	private boolean inRange(int a, int yo) {
		if (a < 8 && yo < 8 && a >= 0 && yo >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Looks for all illegal black moves in file. Then, if none are found moves
	 * to all legal moves.
	 * 
	 * すべての不法な黒い移動を探します。  そして、なにもないなら、すべての法的な移動に移動を設立します。
	 */
	public void blackillegalmove() {
		if (Key[blacktorow][blacktocol].getText().equals("W")) {
			JOptionPane.showMessageDialog(null, "illegal move");
			if (Key[blackfromrow][blackfromcol].getText().equals("B")) {
				Key[blackfromrow][blackfromcol].setText("B");
				Key[blacktorow][blacktocol].setForeground(Color.WHITE);
				Key[blacktorow][blacktocol].setText("W");
			} else {
				Key[blackfromrow][blackfromcol].setText("KB");
				Key[blacktorow][blacktocol].setForeground(Color.WHITE);
				Key[blacktorow][blacktocol].setText("W");
			}
			JOptionPane.showMessageDialog(null, "Loss of Turn");
		} else if (Key[blacktorow][blacktocol].getText().equals("KW")) {
			JOptionPane.showMessageDialog(null, "illegal move");
			if (Key[blackfromrow][blackfromcol].getText().equals("B")) {
				Key[blackfromrow][blackfromcol].setText("B");
				Key[blacktorow][blacktocol].setForeground(Color.WHITE);
				Key[blacktorow][blacktocol].setText("KW");
			} else {
				Key[blackfromrow][blackfromcol].setText("KB");
				Key[blacktorow][blacktocol].setForeground(Color.WHITE);
				Key[blacktorow][blacktocol].setText("KW");
			}
			JOptionPane.showMessageDialog(null, "Loss of Turn");
		} else {
			checkblacklegalmoves();
		}
	}

	/**
	 * Looks for all illegal white moves in file. Then, if none are found moves
	 * to all legal moves.
	 * 
	 * すべての不法な白い移動を探します。.  そして、なにもないなら、すべての法的な移動に移動を設立します。
	 */
	public void whiteillegalmove() {

		if (Key[whitetorow][whitetocol].getText().equals("B")) {
			JOptionPane.showMessageDialog(null, "illegal move");
			if (Key[whitefromrow][whitefromcol].getText().equals("W")) {
				Key[whitefromrow][whitefromcol].setText("W");
				Key[whitetorow][whitetocol].setForeground(Color.BLACK);
				Key[whitetorow][whitetocol].setText("B");
			} else {
				Key[whitefromrow][whitefromcol].setText("KW");
				Key[whitetorow][whitetocol].setForeground(Color.BLACK);
				Key[whitetorow][whitetocol].setText("B");
			}
			JOptionPane.showMessageDialog(null, "Loss of Turn");
		} else if (Key[whitetorow][whitetocol].getText().equals("KB")) {
			JOptionPane.showMessageDialog(null, "illegal move");
			if (Key[whitefromrow][whitefromcol].getText().equals("W")) {
				Key[whitefromrow][whitefromcol].setText("W");
				Key[whitetorow][whitetocol].setForeground(Color.BLACK);
				Key[whitetorow][whitetocol].setText("KB");
			} else {
				Key[whitefromrow][whitefromcol].setText("KW");
				Key[whitetorow][whitetocol].setForeground(Color.BLACK);
				Key[whitetorow][whitetocol].setText("KB");
			}
			JOptionPane.showMessageDialog(null, "Loss of Turn");
		} else {
			checkwhitelegalmove();
		}
	}

	/**
	 * Does move piece but if piece moves out side of allowed parameter player
	 * must redo its turn.
	 * 
	 * 移動はつなぎあわせられますが、断片が許容パラメタの外に動くなら、プレーヤーは回転をやり直さなければなりません。.
	 */
	public void checkwhitelegalmove() {
		if (Key[whitefromrow][whitefromcol].getText().equals("KW")
				|| no.equals("KW")) {
			CheckKingmoves(whitefromrow, whitefromcol, whitetorow, whitetocol);
		} else {
			if (inRange(whitefromrow + 1, whitefromcol + 1)
					&& Key[whitefromrow + 1][whitefromcol + 1].getText()
							.equals("") && whitefromrow + 1 == whitetorow
					&& whitefromcol + 1 == whitetocol) {
				setpiece(i, k);
			} else if (inRange(whitefromrow + 1, whitefromcol - 1)
					&& Key[whitefromrow + 1][whitefromcol - 1].getText()
							.equals("") && whitefromrow + 1 == whitetorow
					&& whitefromcol - 1 == whitetocol) {
				setpiece(i, k);
			} else {
				redo(whitefromrow, whitefromcol);
			}
		}
	}

	/**
	 * Does move piece but if piece moves out side of allowed parameter player
	 * must redo its turn.
	 * 
	 * 移動はつなぎあわせられますが、断片が許容パラメタの外に動くなら、プレーヤーは回転をやり直さなければなりません。.
	 */
	public void checkblacklegalmoves() {

		if (Key[blackfromrow][blackfromcol].getText().equals("KB")
				|| no.equals("KB")) {
			CheckKingmoves(blackfromrow, blackfromcol, blacktorow, blacktocol);

		} else {
			if (inRange(blackfromrow - 1, blackfromcol + 1)
					&& Key[blackfromrow - 1][blackfromcol + 1].getText()
							.equals("") && blackfromrow - 1 == blacktorow
					&& blackfromcol + 1 == blacktocol) {
				setpiece(i, k);
			} else if (inRange(blackfromrow - 1, blackfromcol - 1)
					&& Key[blackfromrow - 1][blackfromcol - 1].getText()
							.equals("") && blackfromrow - 1 == blacktorow
					&& blackfromcol - 1 == blacktocol) {
				setpiece(i, k);
			} else if (jump() == true) {
				dojump(blackfromrow, blacktorow, blackfromcol, blacktocol);
			} else {
				redo(blackfromrow, blackfromcol);
			}
		}
	}

	/**
	 * Looks for all double jumps a black piece to can make and then forces it.
	 * 
	 * 黒い断片がすることができるすべての二重ジャンプを探して、次に、それを強制します。. 
	 */
	public void blackdoublejumps(int torow, int tocol) {
		if (inRange(torow - 2, tocol + 2)
				&& Key[torow - 1][tocol + 1].getText().equals("W")
				&& Key[torow - 2][tocol + 2].getText().equals("")) {
			Key[torow][tocol].setText("");
			Key[torow - 1][tocol + 1].setText("");
			Key[torow - 2][tocol + 2].setForeground(Color.BLACK);
			Key[torow - 2][tocol + 2].setText("B");
			blacktorow = torow - 2;
			blacktocol = tocol + 2;
			scoreblack ++;
			King(blacktorow);
		} else if (inRange(torow - 2, tocol - 2)
				&& Key[torow - 1][tocol - 1].getText().equals("W")
				&& Key[torow - 2][tocol - 2].getText().equals("")) {
			Key[torow][tocol].setText("");
			Key[torow - 1][tocol - 1].setText("");
			Key[torow - 2][tocol - 2].setForeground(Color.BLACK);
			Key[torow - 2][tocol - 2].setText("B");
			blacktorow = torow - 2;
			blacktocol = tocol - 2;
			scoreblack ++;
			King(blacktorow);
		}
	}

	/**
	 * Looks for all double jumps a white piece to can make and then forces it.
	 * 
	 * 白い断片がすることができるすべての二重ジャンプを探して、次に、それを強制します。.
	 */
	public void whitedoublejumps(int torow, int tocol) {
		if (inRange(torow + 2, tocol + 2)
				&& Key[torow + 1][tocol + 1].getText().equals("B")
				&& Key[torow + 2][tocol + 2].getText().equals("")) {
			Key[torow][tocol].setText("");
			Key[torow + 1][tocol + 1].setText("");
			Key[torow + 2][tocol + 2].setForeground(Color.WHITE);
			if (no.equals("W")) {
				Key[torow + 2][tocol + 2].setText("W");
			} else if (no.equals("KW")) {
				Key[torow + 2][tocol + 2].setText("W");
			}
			whitetorow = torow + 2;
			whitetocol = tocol + 2;
			King(whitetorow);
		} else if (inRange(torow + 2, tocol - 2)
				&& Key[torow + 1][tocol - 1].getText().equals("B")
				&& Key[torow + 2][tocol - 2].getText().equals("")) {
			Key[torow][tocol].setText("");
			Key[torow + 1][tocol - 1].setText("");
			Key[torow + 2][tocol - 2].setForeground(Color.WHITE);
			Key[torow + 2][tocol - 2].setText("W");
			whitetorow = torow + 2;
			whitetocol = tocol - 2;
			King(whitetorow);
		}
	}

	/**
	 * Determines if player earned a king by taking either piece torow 
	 * passing it through.
	 * 
	 * プレーヤーがそれを通過するどちらの断片torow変数もやり通すことによってキングを稼いだかどうか決定します。. 
	 */
	public boolean King(int dook) {
		if (dook == 7 && whitetorow == 7) {
			Key[whitetorow][whitetocol].setForeground(Color.WHITE);
			Key[whitetorow][whitetocol].setText("KW");
			return true;
		} else if (dook == 0 && blacktorow == 0) {
			Key[blacktorow][blacktocol].setForeground(Color.BLACK);
			Key[blacktorow][blacktocol].setText("KB");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Basically allows any King to movement.
	 * 
	 * どんなキングも動きに許容します。
	 */
	public void Kingmove() {
		if (Key[i][k].getText().equals("KW")) {
			whitefromrow = i;
			whitefromcol = k;
			no = "KW";
			face = false;
		} else {
			blackfromrow = i;
			blackfromcol = k;
			Key[i][k].setText("");
			no = "KB";
			face = false;
		}

	}

	/**
	 * Does jumping for any piece.
	 * 
	 * 白と黒のためにもジャンプすること。. 
	 */
	public void dojump(int ac, int rac, int arg, int gry) {
		jumpro = (ac + rac) / 2;
		jumpco = (arg + gry) / 2;

		if (Key[jumpro][jumpco].getText().equals("B") && jump() == true) {
			Key[jumpro][jumpco].setText("");
			Key[ac][arg].setText("");
			setpiece(rac, gry);
			scoreWhite++;
		} else if (Key[jumpro][jumpco].getText().equals("KB") && jump() == true) {
			Key[jumpro][jumpco].setText("");
			Key[ac][arg].setText("");
			setpiece(rac, gry);
			scoreWhite++;
		} else if (Key[jumpro][jumpco].getText().equals("W") && jump() == true) {
			Key[jumpro][jumpco].setText("");
			Key[ac][arg].setText("");
			setpiece(rac, gry);
			scoreblack++;
		} else if (Key[jumpro][jumpco].getText().equals("KW") && jump() == true) {
			Key[jumpro][jumpco].setText("");
			Key[ac][arg].setText("");
			setpiece(rac, gry);
			scoreblack++;
		}
	}

	/**
	 * Method to make player redo move again.
	 * 
	 * プレーヤーが再び移動をやり直すのを作るための方法. 
	 */
	public void redo(int ju, int ku) {
		if (no.equals("W") || no.equals("KW")) {
			face = true;
			turn = 1;
			Key[ju][ku].setText(no);
			JOptionPane.showMessageDialog(null, "Redo Move");
		} else {
			face = true;
			turn = 2;
			Key[ju][ku].setText(no);
			JOptionPane.showMessageDialog(null, "Redo Move");
		}
	}

	/**
	 * Makes it easier to set any piece instead of using redundant code.
	 * 
	 * 冗長なコードを使用せずに任意の部分を設定しやすくなります。
	 */
	public void setpiece(int ho, int izzo) {
		if (no.equals("W") || no.equals("KW")) {
			Key[ho][izzo].setForeground(Color.WHITE);
			Key[ho][izzo].setText(no);
			Turns.setText("It's blacks turn");
			turn = 2;
		} else {
			Key[ho][izzo].setForeground(Color.BLACK);
			Key[ho][izzo].setText(no);
			Turns.setText("It's whites turn");
			turn = 1;
		}
	}

	/**
	 * Looks for all the moves any King can make and sets piece.
	 * 
	 * キングも移動することができ実行するすべての動きを捜します。
	 */
	public void CheckKingmoves(int a, int b, int c, int d) {
		if (inRange(a - 1, b + 1) && Key[a - 1][b + 1].getText().equals("")
				&& a - 1 == c && b + 1 == d) {
			Key[a][b].setText("");
			setpiece(a - 1, b + 1);
		} else if (inRange(a + 1, b + 1)
				&& Key[a + 1][b + 1].getText().equals("") && a + 1 == c
				&& b + 1 == d) {
			Key[a][b].setText("");
			setpiece(a + 1, b + 1);
		} else if (inRange(a - 1, b - 1)
				&& Key[a - 1][b - 1].getText().equals("") && a - 1 == c
				&& b + 1 == d) {
			Key[a][b].setText("");
			setpiece(a - 1, b - 1);
		} else if (inRange(a + 1, b - 1)
				&& Key[a + 1][b - 1].getText().equals("") && a + 1 == c
				&& b - 1 == d) {
			Key[a][b].setText("");
			setpiece(a + 1, b - 1);
		}
	}

	/**
	 * Creates basic board of game.
	 * 
	 * ゲームボーアドを作成する。
	 */
	public void NewGame() {
		
		Butlistener ko = new Butlistener();

		for (int i = 0; i < Key.length; i++) {

			for (int col = 0; col < Key[i].length; col++) {

				Key[i][col] = new JButton();
				Key[i][col].addActionListener(ko);
				if ((i % 2) == (col % 2)) {
					Key[i][col].setBackground(Color.BLACK);
				} else {
					Key[i][col].setBackground(Color.RED);
					if (i < 3) {

						Key[i][col].setForeground(Color.WHITE);
						Key[i][col].setText("W");
					}
					if (i > 4) {
						Key[i][col].setText("B");
					}
				}
				grid.add(Key[i][col]);
			}
		}
		
	}
	/** Loads Game from Save File
	 * 
	 * セーブファイルからゲームロードする
	 */
	public void ReturnGame(boolean confirm, String go){
		Filesave mi = new Filesave();
		if(confirm == true){
		Key = mi.loadgame(go);
		remove(grid);
		validate();
		repaint();
		gr = new JPanel(new GridLayout(8, 8));;
		
		for(int x = 0 ; x < Key.length; x++){
			for(int y = 0; y < Key[x].length;y++){
				Key[x][y].addActionListener(new Butlistener());
				gr.add(Key[x][y]);
			}
		}
		add(gr, BorderLayout.CENTER);
		validate();
		repaint();
		}else{
			
		}
	}
	/** Passes JButton Array over to Filesave class.
	 * 
	 * FilesaveクラースにJButtonアライをパスする
	 */
	public JButton[][] arrayshot() {
		return this.Key;

	}

}
