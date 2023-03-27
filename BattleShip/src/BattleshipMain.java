import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BattleshipMain {
	
	public static JFrame frame;
	public static boolean isComputerTurn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleshipMain window = new BattleshipMain();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 * @wbp.parser.entryPoint
	 */
	public BattleshipMain () throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Battleship");
		frame.setBounds(100, 100, 546, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		StartScreen.displayStartScreen();
		InstructionPanel.displayInstructionPanel();
		CoinPanel.displayCoinPanel();
		StrategyPanel sp = new StrategyPanel();
//		StrategyPanel.displayStrategyPanel();
		BattlefieldPanel bp = new BattlefieldPanel();
		BattlefieldPanel.fillCoords(BattlefieldPanel.coordinates);

		
		//Add music
//		try {
//			File wavFile = new File("audio/War Thunder Soundtrack - Naval Battle Music 1.wav");
//		    Clip clip = AudioSystem.getClip();
//		    clip.open(AudioSystem.getAudioInputStream(wavFile));
//		    clip.start();
//		    clip.loop(Clip.LOOP_CONTINUOUSLY);
//		} catch (Exception e) {}
	

		
	}
	
	public static void buttonClickSFX() {
		try {
			File wavFile = new File("audio/Button_Plate Click (Minecraft Sound) - Sound Effect for editing.wav");
		    Clip clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(wavFile));
		    clip.start();
		} catch (Exception e) {}
	}
	
	public static void hitSFX() {
		try {
			File wavFile = new File("audio/mixkit-sea-mine-explosion-1184.wav");
		    Clip clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(wavFile));
		    clip.start();
		} catch (Exception e) {}
	}
	
	public static void missSFX() {
		try {
			File wavFile = new File("audio/Dropping-Something-in-Water-A2-www.fesliyanstudios.com.wav");
		    Clip clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(wavFile));
		    clip.start();
		} catch (Exception e) {}
	}
	
	public static void sunkSFX() {
		try {
			File wavFile = new File("audio/consecutive_exposions-[AudioTrimmer.com].wav");
		    Clip clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(wavFile));
		    clip.start();
		} catch (Exception e) {}
	}
}
