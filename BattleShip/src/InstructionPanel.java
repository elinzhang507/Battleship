import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InstructionPanel extends JPanel {
	
	static JFrame frame = BattleshipMain.frame; //variable so it's easy to reference main frame
	
	public static void displayInstructionPanel() {
		InstructionPanel instructionPanel = new InstructionPanel();
		instructionPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(instructionPanel, "instructionPanel");
		instructionPanel.setLayout(null);
		
		JLabel instructionTitleLabel = new JLabel("INSTRUCTIONS");
		instructionTitleLabel.setBackground(Color.BLACK);
		instructionTitleLabel.setForeground(Color.GREEN);
		instructionTitleLabel.setOpaque(true);
		instructionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		instructionTitleLabel.setFont(new Font("Black Ops One", Font.PLAIN, 50));
		instructionTitleLabel.setBounds(81, 29, 395, 56);
		instructionPanel.add(instructionTitleLabel);
		
		JButton linkButton = new JButton("Click to link to instructions");
		linkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Desktop browser = Desktop.getDesktop();
				try {
					BattleshipMain.buttonClickSFX();
					browser.browse(new URI("https://www.hasbro.com/common/instruct/Battleship.PDF"));
				} catch (Exception err){
					
				}
			}
		});
		linkButton.setBackground(Color.BLACK);
		linkButton.setForeground(Color.WHITE);	
		linkButton.setBorder(BorderFactory.createLineBorder(Color.white));
		linkButton.setOpaque(true);
		linkButton.setFont(new Font("Russo One", Font.PLAIN, 15));
		linkButton.setBounds(160, 218, 237, 29);
		linkButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				linkButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				linkButton.setBackground(Color.BLACK);
			}
	    });
		instructionPanel.add(linkButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "startScreen"); 
			}
		});
		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.GREEN);	
		backButton.setBorder(BorderFactory.createLineBorder(Color.green));
		backButton.setOpaque(true);
		backButton.setFont(new Font("Russo One", Font.PLAIN, 15));
		backButton.setBounds(53, 379, 117, 69);
		backButton.setBounds(214, 376, 117, 29);
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				backButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				backButton.setBackground(Color.BLACK);
			}
	    });
		instructionPanel.add(backButton);

	}

}
