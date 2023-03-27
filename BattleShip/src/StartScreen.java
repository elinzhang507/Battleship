import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JPanel {
//	Put battleship image as background
	private Image img;
	public StartScreen(Image img) {
		this.img = img;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null, null);
	}
	
	static JFrame frame = BattleshipMain.frame; //variable so it's easy to reference main frame
	
	public static void displayStartScreen() throws Exception {	
		//read in image
		StartScreen startPanel = new StartScreen(ImageIO.read(new File("images/battleshipimage.jpg")));
		
		//Panel properties
		startPanel.setBounds(0, 0, 546, 506);
		frame.getContentPane().add(startPanel, "startScreen");
		startPanel.setLayout(null);
		
		//Title
		JLabel titleLabel = new JLabel("BATTLESHIP");
		titleLabel.setFont(new Font("Black Ops One", Font.PLAIN, 60));
		titleLabel.setBounds(76, 80, 451, 113);
		startPanel.add(titleLabel);
		
		//Easy button
		JButton easyButton = new JButton("Easy");
		easyButton.setFont(new Font("Russo One", Font.PLAIN, 15));
		//When easy button is clicked, go to strategy panel
		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "coinPanel"); 
			}
		});
		easyButton.setBounds(92, 272, 117, 29);
		easyButton.setBackground(Color.BLACK);
		easyButton.setForeground(Color.GREEN);
		easyButton.setBorder(BorderFactory.createLineBorder(Color.green));
		easyButton.setOpaque(true);
		//When mouse hovers over button, change colour
		easyButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				easyButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				easyButton.setBackground(Color.BLACK);
			}
	    });
		startPanel.add(easyButton);
		
		
		
		//Hard button
		JButton hardButton = new JButton("Hard");
		hardButton.setFont(new Font("Russo One", Font.PLAIN, 15));
		//When hard button is clicked, go to strategy panel
		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "coinPanel"); 
			}
		});
		hardButton.setBounds(324, 272, 117, 29);
		hardButton.setBackground(Color.BLACK);
		hardButton.setForeground(Color.GREEN);
		hardButton.setBorder(BorderFactory.createLineBorder(Color.green));
		hardButton.setOpaque(true);
		//When mouse hovers over button, change colour
		hardButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				hardButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				hardButton.setBackground(Color.BLACK);
			}
	    });
		startPanel.add(hardButton);	
		
		//Insturction button
		JButton instructionButton = new JButton("Instructions");
		instructionButton.setFont(new Font("Russo One", Font.PLAIN, 15));
		instructionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "instructionPanel"); 
			}
		});
		instructionButton.setBounds(210, 370, 117, 29);
		instructionButton.setBackground(Color.BLACK);
		instructionButton.setForeground(Color.GREEN);
		instructionButton.setBorder(BorderFactory.createLineBorder(Color.green));
		instructionButton.setOpaque(true);
		//When mouse hovers over button, change colour
		instructionButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				instructionButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				instructionButton.setBackground(Color.BLACK);
			}
	    });
		startPanel.add(instructionButton);
	}
}
