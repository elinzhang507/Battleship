import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CoinPanel extends JPanel {

	static JFrame frame = BattleshipMain.frame; //variable so it's easy to reference main frame
	static JButton tossCoinButton;
	
	public static void displayCoinPanel() {
		CoinPanel coinPanel = new CoinPanel();
		coinPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(coinPanel, "coinPanel");
		coinPanel.setLayout(null);
		
		JLabel coinTitleLabel = new JLabel("COIN TOSS");
		coinTitleLabel.setBackground(Color.BLACK);
		coinTitleLabel.setForeground(Color.GREEN);
		coinTitleLabel.setOpaque(true);
		coinTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		coinTitleLabel.setFont(new Font("Black Ops One", Font.PLAIN, 50));
		coinTitleLabel.setBounds(81, 29, 395, 56);
		coinPanel.add(coinTitleLabel);
		
		tossCoinButton = new JButton("Toss coin");
		tossCoinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				BattleshipMain.isComputerTurn = whoFirst(BattleshipMain.isComputerTurn);
				JLabel whoFirstLabel = new JLabel("");
				if(BattleshipMain.isComputerTurn == true) {
					whoFirstLabel.setText("Computer goes first");
				} else {
					whoFirstLabel.setText("You go first");
				}
				whoFirstLabel.setBounds(190, 277, 170, 19);
				coinPanel.add(whoFirstLabel);
				whoFirstLabel.setHorizontalAlignment(SwingConstants.CENTER);
				whoFirstLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
				whoFirstLabel.setForeground(Color.WHITE);		
				tossCoinButton.setEnabled(false);
				JButton okButton = new JButton("OK");
				okButton.setBounds(260, 372, 37, 27);
				coinPanel.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BattleshipMain.buttonClickSFX();
						((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "strategyPanel");
						coinPanel.remove(okButton);
						coinPanel.remove(whoFirstLabel);
					}
				});
				okButton.setOpaque(true);
				okButton.setForeground(Color.GREEN);
				okButton.setFont(new Font("Russo One", Font.PLAIN, 15));
				okButton.setBorder(BorderFactory.createLineBorder(Color.green));
				okButton.setBackground(Color.BLACK);
				okButton.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						okButton.setBackground(Color.GRAY);
					}
					public void mouseExited(MouseEvent evt) {
						okButton.setBackground(Color.BLACK);
					}
			    });		
			}
		});
		tossCoinButton.setBackground(Color.BLACK);
		tossCoinButton.setForeground(Color.WHITE);	
		tossCoinButton.setBorder(BorderFactory.createLineBorder(Color.white));
		tossCoinButton.setOpaque(true);
		tossCoinButton.setFont(new Font("Russo One", Font.PLAIN, 15));
		tossCoinButton.setBounds(216, 172, 122, 29);
		tossCoinButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				tossCoinButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				tossCoinButton.setBackground(Color.BLACK);
			}
	    });
		coinPanel.add(tossCoinButton);	

	}
	
	public static boolean whoFirst(boolean isComputerTurn) {
		int random = (int) (Math.random() * 2 + 1);
		if (random == 1) { 
			return isComputerTurn = true;
		} else { 
			return isComputerTurn = false; 
		}
	}
	
}
