import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class BattlefieldPanel implements ActionListener {

	static JButton[][] yourButtons = new JButton[10][10];
	static JButton[][] oppButtons = new JButton[10][10];
	static JFrame frame = BattleshipMain.frame; // variable so it's easy to reference main frame

	// counters to see how many holes the ship have
	// used to determine if a ship is sunken
	int carrierHoles = 5;
	int battleshipHoles = 4;
	int cruiserHoles = 3;
	int submarineHoles = 3;
	int destroyerHoles = 2;

	int oppCarrierHoles = 5;
	int oppBattleshipHoles = 4;
	int oppCruiserHoles = 3;
	int oppSubmarineHoles = 3;
	int oppDestroyerHoles = 2;
	
	// constants used to represent what everything means
	static final int WATER_SYMBOL = 0;
	static final int DESTROYER_SYMBOL = 1;
	static final int SUBMARINE_SYMBOL = 2;
	static final int CRUISER_SYMBOL = 3;
	static final int BATTLESHIP_SYMBOL = 4;
	static final int CARRIER_SYMBOL = 5;

	// row and columnButtonFire of the coordinate user chooses to fire
	int rowButtonFire;
	int columnButtonFire;

	static // test case for computer array
	int[][] computerGrid;

	// array to show the user how many holes on the ship have been fired
	static JLabel[] oppDestroyerLabels = new JLabel[2];
	static JLabel[] oppSubmarineLabels = new JLabel[3];
	static JLabel[] oppCruiserLabels = new JLabel[3];
	static JLabel[] oppBattleshipLabels = new JLabel[4];
	static JLabel[] oppCarrierLabels = new JLabel[5];

	
	static JLabel[] yourDestroyerLabels = new JLabel[2];
	static JLabel[] yourSubmarineLabels = new JLabel[3];
	static JLabel[] yourCruiserLabels = new JLabel[3];
	static JLabel[] yourBattleshipLabels = new JLabel[4];
	static JLabel[] yourCarrierLabels = new JLabel[5];
	
	// keep track of indexes of the above array labels
	int destroyerLabelIndex = 0;
	int submarineLabelIndex = 0;
	int cruiserLabelIndex = 0;
	int battleshipLabelIndex = 0;
	int carrierLabelIndex = 0;
	
	static int oppDestroyerLabelIndex = 0;
	static int oppSubmarineLabelIndex = 0;
	static int oppCruiserLabelIndex = 0;
	static int oppBattleshipLabelIndex = 0;
	static int oppCarrierLabelIndex = 0;

	// number hits, misses
	int hits = 0;
	int misses = 0;

	static JLabel yourHitsLabel;
	static JLabel yourMissLabel;
	static JLabel yourTotalLabel;
	
	
	static JLabel oppHitsLabel;
	static JLabel oppMissLabel;
	static JLabel oppTotalLabel;

    public static int[][] placeComputer() {
        int gridSize = 10;
        int[][] computerGrid = new int[10][10];
        Random random = new Random(); // create random generator
        int[][] grid = new int[10][10]; // create a 2D array of size 10 by 10

        for (int c = 5; c >= 1; c--) {
            boolean isFree;
            int x;
            int y;
            boolean vertical;
            int i;
            if(c==1 || c==2) {
                
                i=c+1;
            
            } else {
                
                i =c;
            }

            do {
                x = random.nextInt(gridSize);
                y = random.nextInt(gridSize);
                vertical = random.nextBoolean();

                // change the starting point to fit the grid if necessary
                if (vertical) {
                    if (y + i > gridSize) {
                        y -= i; // go down by i units
                    }
                } else if (x + i > gridSize) {
                    x -= i; // go up by i units
                }

                isFree = true;

                // check for free space
                if (vertical) { // if going vertically
                    for (int m = y; m < y + i; m++) {
                        if (grid[m][x] != 0) {
                            isFree = false; // if there is not enough space set it to false
                            break; // stop the loop
                        }
                    }
                } else { // if going horizontally
                    for (int n = x; n < x + i; n++) {
                        if (grid[y][n] != 0) {
                            isFree = false;
                            break; // stop the loop
                        }
                    }
                }
            } while (!isFree); // repeat the loop to make a new coordinate if there is not enough space create
                                // an new coordinate

            // fill in the adjacent cells of the ship coordinate
            if (vertical) { // if going up or down

                for (int n = Math.max(0, y - 1); n < Math.min(gridSize, y + i + 1); n++) { // for loop for vertical

                    for (int m = Math.max(0, x - 1); m < Math.min(gridSize, x + 2); m++) { // for loop for horizontal
                                                                                            // adjacent cells
                        grid[n][m] = i; // fill the ship cells and adjacent cell
                    }
                }
            } else { // if going left or right

                for (int m = Math.max(0, y - 1); m < Math.min(gridSize, y + 2); m++) {

                    for (int n = Math.max(0, x - 1); n < Math.min(gridSize, x + i + 1); n++) {

                        grid[m][n] = i; // fill the ship cells and adjacent cell
                    }
                }
            }
            // fill in the ship cells only in another 2d
            for (int j = 0; j < i; j++) { // for the length of the ship
                
                    computerGrid[y][x] = c;
                
                if (vertical) { // if vertical increment y by one
                    y++;
                } else { // if horizontal increment x by one
                    x++;
                }
            }
        
        }
        
        for(int i = 0; i < 10; i++) {
        	for(int j = 0; j < 10; j++) {
        		System.out.print(computerGrid[i][j] + " ");
        	}
        	System.out.println();
        }
        
        return computerGrid;
        
    }
    
    
	public BattlefieldPanel() throws IOException, InterruptedException {		
		computerGrid = placeComputer();

		JPanel battlefieldPanel = new JPanel();
		frame.getContentPane().add(battlefieldPanel, "battlefieldPanel");
		battlefieldPanel.setBackground(Color.BLACK);
		battlefieldPanel.setLayout(null);

		// your turn title
		JLabel yourTitleLabel = new JLabel("YOUR TURN");
		yourTitleLabel.setBackground(Color.BLACK);
		yourTitleLabel.setForeground(Color.GREEN);
		yourTitleLabel.setFont(new Font("Black Ops One", Font.PLAIN, 30));
		yourTitleLabel.setBounds(156, 26, 176, 50);
		battlefieldPanel.add(yourTitleLabel);

		// opponent's grid (displays on your side)
		JPanel oppGridPanel = new JPanel();
		oppGridPanel.setBounds(68, 119, 350, 350);
		battlefieldPanel.add(oppGridPanel);
		oppGridPanel.setLayout(new GridLayout(10, 1));

		// opp number axis
		JLabel oppNumberLabel = new JLabel(
				"    1         2         3         4         5        6         7         8        9       10");
		oppNumberLabel.setFont(new Font("Black Ops One", Font.PLAIN, 13));
		oppNumberLabel.setForeground(Color.WHITE);
		oppNumberLabel.setBounds(68, 88, 350, 32);
		battlefieldPanel.add(oppNumberLabel);

		// opp letter axis
		JPanel oppLetterPanel = new JPanel();
		oppLetterPanel.setBackground(Color.BLACK);
		oppLetterPanel.setBounds(33, 119, 35, 350);
		battlefieldPanel.add(oppLetterPanel);
		oppLetterPanel.setLayout(new GridLayout(10, 1));
		for (int i = 0; i < 10; i++) {
			JLabel letterLabel = new JLabel("" + ((char) (i + 65)));
			oppLetterPanel.add(letterLabel);
			letterLabel.setHorizontalAlignment(JLabel.CENTER);
			letterLabel.setForeground(Color.WHITE);
			letterLabel.setFont(new Font("Black Ops One", Font.PLAIN, 12));
		}

		// label that displays grid above is opponent's
		JLabel oppGridLabel = new JLabel("OPPONENT'S GRID");
		oppGridLabel.setFont(new Font("Black Ops One", Font.PLAIN, 20));
		oppGridLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oppGridLabel.setForeground(Color.WHITE);
		oppGridLabel.setBounds(97, 469, 292, 34);
		battlefieldPanel.add(oppGridLabel);

		// display your hits, misses, and total shots (which displays on your side)
		JPanel yourStatPanel = new JPanel();
		yourStatPanel.setBackground(Color.BLACK);
		FlowLayout flowLayout = (FlowLayout) yourStatPanel.getLayout();
		flowLayout.setHgap(30);
		yourStatPanel.setBounds(68, 515, 350, 26);
		battlefieldPanel.add(yourStatPanel);

		yourHitsLabel = new JLabel("Hits: 0");
		yourHitsLabel.setForeground(Color.WHITE);
		yourHitsLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
		yourStatPanel.add(yourHitsLabel);

		yourMissLabel = new JLabel("Misses: 0");
		yourMissLabel.setForeground(Color.WHITE);
		yourMissLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
		yourStatPanel.add(yourMissLabel);

		yourTotalLabel = new JLabel("Total shots: 0");
		yourTotalLabel.setForeground(Color.WHITE);
		yourTotalLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
		yourStatPanel.add(yourTotalLabel);

		// your fleet (which displays on opp's side);
		JPanel yourFleetPanel = new JPanel();
		yourFleetPanel.setBackground(Color.BLACK);
		yourFleetPanel.setBounds(1154, 101, 231, 381);
		battlefieldPanel.add(yourFleetPanel);
		yourFleetPanel.setLayout(null);

		JLabel yourFleetLabel = new JLabel("YOUR FLEET");
		yourFleetLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourFleetLabel.setBounds(13, 5, 204, 26);
		yourFleetLabel.setFont(new Font("Black Ops One", Font.PLAIN, 20));
		yourFleetLabel.setForeground(Color.WHITE);
		yourFleetPanel.add(yourFleetLabel);

		JPanel yourDestroyerPanel = new JPanel();
		yourDestroyerPanel.setBounds(80, 43, 70, 35);
		yourFleetPanel.add(yourDestroyerPanel);
		yourDestroyerPanel.setLayout(new GridLayout(1, 2));
		yourDestroyerLabels = new JLabel[2];
		for (int i = 0; i < 2; i++) {
			JLabel destroyerLabel = new JLabel();
			destroyerLabel.setBackground(Color.BLACK);
			destroyerLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			destroyerLabel.setOpaque(true);
			yourDestroyerPanel.add(destroyerLabel);
			yourDestroyerLabels[i] = destroyerLabel;
		}
		JLabel yourDestroyerLabel = new JLabel("Destroyer");
		yourDestroyerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourDestroyerLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		yourDestroyerLabel.setForeground(Color.WHITE);
		yourDestroyerLabel.setBounds(80, 80, 70, 16);
		yourFleetPanel.add(yourDestroyerLabel);

		JPanel yourSubmarinePanel = new JPanel();
		yourSubmarinePanel.setBounds(61, 107, 105, 35);
		yourFleetPanel.add(yourSubmarinePanel);
		yourSubmarinePanel.setLayout(new GridLayout(1, 3));
		yourSubmarineLabels = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			JLabel submarineLabel = new JLabel();
			submarineLabel.setBackground(Color.BLACK);
			submarineLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			submarineLabel.setOpaque(true);
			yourSubmarinePanel.add(submarineLabel);
			yourSubmarineLabels[i] = submarineLabel;
		}
		JLabel yourSubmarineLabel = new JLabel("Submarine");
		yourSubmarineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourSubmarineLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		yourSubmarineLabel.setForeground(Color.WHITE);
		yourSubmarineLabel.setBounds(61, 142, 105, 16);
		yourFleetPanel.add(yourSubmarineLabel);

		JPanel yourCruiserPanel = new JPanel();
		yourCruiserPanel.setBounds(61, 175, 105, 35);
		yourFleetPanel.add(yourCruiserPanel);
		yourCruiserPanel.setLayout(new GridLayout(1, 3));
		yourCruiserLabels = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			JLabel cruiserLabel = new JLabel();
			cruiserLabel.setBackground(Color.BLACK);
			cruiserLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			cruiserLabel.setOpaque(true);
			yourCruiserPanel.add(cruiserLabel);
			yourCruiserLabels[i] = cruiserLabel;
		}
		JLabel yourCruiserLabel = new JLabel("Cruiser");
		yourCruiserLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourCruiserLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		yourCruiserLabel.setForeground(Color.WHITE);
		yourCruiserLabel.setBounds(61, 211, 105, 16);
		yourFleetPanel.add(yourCruiserLabel);

		JPanel yourBattleshipPanel = new JPanel();
		yourBattleshipPanel.setBounds(46, 242, 140, 35);
		yourFleetPanel.add(yourBattleshipPanel);
		yourBattleshipPanel.setLayout(new GridLayout(1, 4));
		yourBattleshipLabels = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			JLabel battleshipLabel = new JLabel();
			battleshipLabel.setBackground(Color.BLACK);
			battleshipLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			battleshipLabel.setOpaque(true);
			yourBattleshipPanel.add(battleshipLabel);
			yourBattleshipLabels[i] = battleshipLabel;
		}
		JLabel yourBattleshipLabel = new JLabel("Carrier");
		yourBattleshipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourBattleshipLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		yourBattleshipLabel.setForeground(Color.WHITE);
		yourBattleshipLabel.setBounds(28, 346, 175, 16);
		yourFleetPanel.add(yourBattleshipLabel);

		JPanel yourCarrierPanel = new JPanel();
		yourCarrierPanel.setBounds(28, 309, 175, 35);
		yourFleetPanel.add(yourCarrierPanel);
		yourCarrierPanel.setLayout(new GridLayout(1, 5));
		yourCarrierLabels = new JLabel[5];
		for (int i = 0; i < 5; i++) {
			JLabel carrierLabel = new JLabel();
			carrierLabel.setBackground(Color.BLACK);
			carrierLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			carrierLabel.setOpaque(true);
			yourCarrierPanel.add(carrierLabel);
			yourCarrierLabels[i] = carrierLabel;
		}
		JLabel yourCarrierLabel = new JLabel("Battleship");
		yourCarrierLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourCarrierLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		yourCarrierLabel.setForeground(Color.WHITE);
		yourCarrierLabel.setBounds(46, 278, 140, 16);
		yourFleetPanel.add(yourCarrierLabel);
		
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		// opponent's turn title
		JLabel oppTitleLabel = new JLabel("OPPONENT'S TURN");
		oppTitleLabel.setBackground(Color.BLACK);
		oppTitleLabel.setForeground(Color.GREEN);
		oppTitleLabel.setFont(new Font("Black Ops One", Font.PLAIN, 30));
		oppTitleLabel.setBounds(791, 26, 292, 50);
		battlefieldPanel.add(oppTitleLabel);

		// your grid (which displays on opponent's side)
		JPanel yourGridPanel = new JPanel();
		yourGridPanel.setBounds(764, 119, 350, 350);
		battlefieldPanel.add(yourGridPanel);
		yourGridPanel.setLayout(new GridLayout(10, 0));

		// your number axis
		JLabel yourNumberLabel = new JLabel(
				"    1         2         3         4         5        6         7         8        9       10");
		yourNumberLabel.setForeground(Color.WHITE);
		yourNumberLabel.setFont(new Font("Black Ops One", Font.PLAIN, 13));
		yourNumberLabel.setBounds(764, 88, 350, 32);
		battlefieldPanel.add(yourNumberLabel);

		// your letter axis
		JPanel yourLetterPanel = new JPanel();
		yourLetterPanel.setBackground(Color.BLACK);
		yourLetterPanel.setBounds(729, 119, 35, 350);
		battlefieldPanel.add(yourLetterPanel);
		yourLetterPanel.setLayout(new GridLayout(10, 1));
		for (int i = 0; i < 10; i++) {
			JLabel letterLabel = new JLabel("" + ((char) (i + 65)));
			yourLetterPanel.add(letterLabel);
			letterLabel.setHorizontalAlignment(JLabel.CENTER);
			letterLabel.setForeground(Color.WHITE);
			letterLabel.setFont(new Font("Black Ops One", Font.PLAIN, 12));
		}

//		label that displays grid above is opponent's
		JLabel yourGridLabel = new JLabel("YOUR GRID");
		yourGridLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourGridLabel.setForeground(Color.WHITE);
		yourGridLabel.setFont(new Font("Black Ops One", Font.PLAIN, 20));
		yourGridLabel.setBounds(791, 469, 292, 34);
		battlefieldPanel.add(yourGridLabel);

		// opponent's hits/misses/total shots (which displays on opponent's side)
		JPanel oppStatPanel = new JPanel();
		oppStatPanel.setBackground(Color.BLACK);
		FlowLayout flowLayout2 = (FlowLayout) oppStatPanel.getLayout();
		flowLayout2.setHgap(30);
		oppStatPanel.setBounds(764, 515, 350, 26);
		battlefieldPanel.add(oppStatPanel);

		oppMissLabel = new JLabel("Misses: 0");
		oppMissLabel.setForeground(Color.WHITE);
		oppMissLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
		oppStatPanel.add(oppMissLabel);

		oppHitsLabel = new JLabel("Hits: 0");
		oppHitsLabel.setForeground(Color.WHITE);
		oppHitsLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
		oppStatPanel.add(oppHitsLabel);

		oppTotalLabel = new JLabel("Total shots: 0");
		oppTotalLabel.setForeground(Color.WHITE);
		oppTotalLabel.setFont(new Font("Russo One", Font.PLAIN, 15));
		oppStatPanel.add(oppTotalLabel);

		// opponent's fleet (which displays on your side)
		JPanel oppFleetPanel = new JPanel();
		oppFleetPanel.setBounds(458, 101, 231, 381);
		battlefieldPanel.add(oppFleetPanel);
		oppFleetPanel.setLayout(null);
		oppFleetPanel.setBackground(Color.BLACK);

		JLabel oppFleetLabel = new JLabel("OPPONENT'S FLEET");
		oppFleetLabel.setForeground(Color.WHITE);
		oppFleetLabel.setFont(new Font("Black Ops One", Font.PLAIN, 20));
		oppFleetLabel.setBounds(13, 5, 204, 26);
		oppFleetPanel.add(oppFleetLabel);

		JPanel oppDestroyerPanel = new JPanel();
		oppDestroyerPanel.setBounds(80, 43, 70, 35);
		oppFleetPanel.add(oppDestroyerPanel);
		oppDestroyerPanel.setLayout(new GridLayout(1, 2));
		// JLabel[] oppDestroyerLabels = new JLabel[2];
		for (int i = 0; i < 2; i++) {
			JLabel destroyerLabel = new JLabel();
			destroyerLabel.setBackground(Color.BLACK);
			destroyerLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			destroyerLabel.setOpaque(true);
			oppDestroyerPanel.add(destroyerLabel);
			oppDestroyerLabels[i] = destroyerLabel;
		}
		JLabel oppDestroyerLabel = new JLabel("Destroyer");
		oppDestroyerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oppDestroyerLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		oppDestroyerLabel.setForeground(Color.WHITE);
		oppDestroyerLabel.setBounds(82, 80, 66, 16);
		oppFleetPanel.add(oppDestroyerLabel);

		JPanel oppSubmarinePanel = new JPanel();
		oppSubmarinePanel.setBounds(61, 107, 105, 35);
		oppFleetPanel.add(oppSubmarinePanel);
		oppSubmarinePanel.setLayout(new GridLayout(1, 3));
		// JLabel[] oppSubmarineLabels = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			JLabel submarineLabel = new JLabel();
			submarineLabel.setBackground(Color.BLACK);
			submarineLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			submarineLabel.setOpaque(true);
			oppSubmarinePanel.add(submarineLabel);
			oppSubmarineLabels[i] = submarineLabel;
		}
		JLabel oppSubmarineLabel = new JLabel("Submarine");
		oppSubmarineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oppSubmarineLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		oppSubmarineLabel.setForeground(Color.WHITE);
		oppSubmarineLabel.setBounds(61, 142, 105, 16);
		oppFleetPanel.add(oppSubmarineLabel);

		JPanel oppCruiserPanel = new JPanel();
		oppCruiserPanel.setBounds(61, 175, 105, 35);
		oppFleetPanel.add(oppCruiserPanel);
		oppCruiserPanel.setLayout(new GridLayout(1, 3));
		// JLabel[] oppCruiserLabels = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			JLabel cruiserLabel = new JLabel();
			cruiserLabel.setBackground(Color.BLACK);
			cruiserLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			cruiserLabel.setOpaque(true);
			oppCruiserPanel.add(cruiserLabel);
			oppCruiserLabels[i] = cruiserLabel;
		}
		JLabel oppCruiserLabel = new JLabel("Cruiser");
		oppCruiserLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oppCruiserLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		oppCruiserLabel.setForeground(Color.WHITE);
		oppCruiserLabel.setBounds(61, 211, 105, 16);
		oppFleetPanel.add(oppCruiserLabel);

		JPanel oppBattleshipPanel = new JPanel();
		oppBattleshipPanel.setBounds(46, 242, 140, 35);
		oppFleetPanel.add(oppBattleshipPanel);
		oppBattleshipPanel.setLayout(new GridLayout(1, 4));
		// JLabel[] oppBattleshipLabels = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			JLabel battleshipLabel = new JLabel();
			battleshipLabel.setBackground(Color.BLACK);
			battleshipLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			battleshipLabel.setOpaque(true);
			oppBattleshipPanel.add(battleshipLabel);
			oppBattleshipLabels[i] = battleshipLabel;
		}
		JLabel oppBattleshipLabel = new JLabel("Carrier");
		oppBattleshipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oppBattleshipLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		oppBattleshipLabel.setForeground(Color.WHITE);
		oppBattleshipLabel.setBounds(29, 346, 175, 16);
		oppFleetPanel.add(oppBattleshipLabel);

		JPanel oppCarrierPanel = new JPanel();
		oppCarrierPanel.setBounds(29, 309, 175, 35);
		oppFleetPanel.add(oppCarrierPanel);
		oppCarrierPanel.setLayout(new GridLayout(1, 5));
		// JLabel[] oppCarrierLabels = new JLabel[5];
		for (int i = 0; i < 5; i++) {
			JLabel carrierLabel = new JLabel();
			carrierLabel.setBackground(Color.BLACK);
			carrierLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			carrierLabel.setOpaque(true);
			oppCarrierPanel.add(carrierLabel);
			oppCarrierLabels[i] = carrierLabel;
		}
		JLabel oppCarrierLabel = new JLabel("Battleship");
		oppCarrierLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oppCarrierLabel.setFont(new Font("Russo One", Font.PLAIN, 13));
		oppCarrierLabel.setForeground(Color.WHITE);
		oppCarrierLabel.setBounds(46, 278, 140, 16);
		oppFleetPanel.add(oppCarrierLabel);

		// ----------------------------------------------------------------------

		// fill both grids with buttons
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				yourButtons[i][j] = new JButton();
				oppButtons[i][j] = new JButton();
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JButton yourButton = yourButtons[i][j];
				JButton oppButton = oppButtons[i][j];

				yourButtons[i][j].addActionListener(this);
				yourButtons[i][j].setName("chooseCoordinate");

				yourButton.setBackground(Color.BLACK);
				yourButton.setBorder(BorderFactory.createLineBorder(Color.green));
				yourButton.setOpaque(true);
				oppGridPanel.add(yourButton);
				/*
				 * yourButton.addMouseListener(new MouseAdapter() { public void
				 * mouseEntered(MouseEvent evt) { yourButton.setBackground(Color.GRAY); } public
				 * void mouseExited(MouseEvent evt) { yourButton.setBackground(Color.BLACK); }
				 * });
				 */
				oppButton.setBackground(Color.BLACK);
				oppButton.setBorder(BorderFactory.createLineBorder(Color.green));
				oppButton.setOpaque(true);
				yourGridPanel.add(oppButton);
				/*
				 * oppButton.addMouseListener(new MouseAdapter() { public void
				 * mouseEntered(MouseEvent evt) { oppButton.setBackground(Color.GRAY); } public
				 * void mouseExited(MouseEvent evt) { oppButton.setBackground(Color.BLACK); }
				 * });
				 */
			}
		}

	}

	

	
	public static void setColour(JButton[][] fireGrid, JLabel[] shipArray, int index, int row, int column) {
		Color colour = new Color(255, 102, 102);
		fireGrid[row][column].setBackground(colour);
		shipArray[index].setBackground(colour);
	}

	public void actionPerformed(ActionEvent e) {
		
		Component source = (Component)e.getSource(); 
		String sourceName = source.getName(); 
		
		if (sourceName.equals("chooseCoordinate")) {
			
			// find the rowButtonFire and columnButtonFire of button pressed 
			outer:
			for (int i = 0; i < yourButtons.length; i++) {
				for (int j = 0; j < yourButtons[i].length; j++) {
					if (yourButtons[i][j].equals(source)) {
						yourButtons[i][j].setEnabled(false); // user cannnot press button anymore
						rowButtonFire = i; 
						columnButtonFire = j; 
						break outer; 
					}
				}
			}
	
			// determine hit or miss 
            if (computerGrid[rowButtonFire][columnButtonFire] == WATER_SYMBOL) {
				yourButtons[rowButtonFire][columnButtonFire].setBackground(Color.LIGHT_GRAY);
				misses++; 
				BattleshipMain.missSFX();
            } else  if (computerGrid[rowButtonFire][columnButtonFire] == DESTROYER_SYMBOL) {
            	setColour(yourButtons, oppDestroyerLabels, destroyerLabelIndex, rowButtonFire, columnButtonFire); 
        		destroyerHoles --;  
//        		if(destroyerHoles == 0) {
//        			BattleshipMain.sunkSFX();
//        		} else {
        			BattleshipMain.hitSFX();
//        		}
        		destroyerLabelIndex++; 
        		hits++; 
        	} else if (computerGrid[rowButtonFire][columnButtonFire] == SUBMARINE_SYMBOL) {
            	setColour(yourButtons, oppSubmarineLabels, submarineLabelIndex, rowButtonFire, columnButtonFire); 
        		submarineHoles--; 
//        		if(submarineHoles == 0) {
//        			BattleshipMain.sunkSFX();
//        		} else {
        			BattleshipMain.hitSFX();
//        		}
        		submarineLabelIndex++; 
        		hits++; 
        	
        	} else if (computerGrid[rowButtonFire][columnButtonFire] == CRUISER_SYMBOL) {
            	setColour(yourButtons, oppCruiserLabels, cruiserLabelIndex, rowButtonFire, columnButtonFire); 
        		cruiserHoles--; 
//        		if(cruiserHoles == 0) {
//        			BattleshipMain.sunkSFX();
//        		} else {
            		BattleshipMain.hitSFX();
//        		}
        		cruiserLabelIndex++; 
        		hits++; 
        		
        	} else if (computerGrid[rowButtonFire][columnButtonFire] == BATTLESHIP_SYMBOL) {
            	setColour(yourButtons, oppBattleshipLabels, battleshipLabelIndex, rowButtonFire, columnButtonFire); 
        		battleshipHoles--; 
//        		if(battleshipHoles == 0) {
//        			BattleshipMain.sunkSFX();
//        		} else {
            		BattleshipMain.hitSFX();
//        		}
        		battleshipLabelIndex++; 
        		hits++; 
        	} else {
            	setColour(yourButtons, oppCarrierLabels, carrierLabelIndex, rowButtonFire, columnButtonFire); 
        		carrierHoles--; 
//        		if(carrierHoles == 0) {
//        			BattleshipMain.sunkSFX();
//        		} else {
            		BattleshipMain.hitSFX();
//        		}
        		carrierLabelIndex++; 
        		hits++;
        	}
            
            yourHitsLabel.setText("Hits: " + hits); 
            yourMissLabel.setText("Misses: " + misses); 
            yourTotalLabel.setText("Total shots: " + (hits + misses));
            
            if (hits == 17) {
            	
            	// win screen
				JDialog winScreen = new JDialog();
				winScreen.setSize(100, 100);
				winScreen.setBounds(244, 345, 200, 150);
				winScreen.setVisible(true);
				winScreen.getContentPane().setBackground(Color.BLACK);
				((JComponent) winScreen.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.green, 3));
				winScreen.setLayout(null);
				
				JLabel youWinLabel = new JLabel("YOU WIN");
				youWinLabel.setHorizontalAlignment(SwingConstants.CENTER);
				youWinLabel.setFont(new Font("Black Ops One", Font.PLAIN, 35));
				youWinLabel.setForeground(Color.GREEN);
				youWinLabel.setBounds(18, 20, 167, 29);
				winScreen.add(youWinLabel);
				
				JButton againButton = new JButton("Again");
				againButton.setBackground(Color.BLACK);
				againButton.setFont(new Font("Russo One", Font.PLAIN, 15));
				againButton.setForeground(Color.GREEN);
				againButton.setBorder(BorderFactory.createLineBorder(Color.green));
				againButton.setOpaque(true);
				againButton.setBounds(24, 65, 69, 29);
				againButton.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						againButton.setBackground(Color.GRAY);
					}
					public void mouseExited(MouseEvent evt) {
						againButton.setBackground(Color.BLACK);
					}
			    });
				againButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BattleshipMain.buttonClickSFX();
						((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "coinPanel"); 
						frame.setBounds(100, 100, 546, 534);
						winScreen.setVisible(false);
						CoinPanel.tossCoinButton.setEnabled(true);
					}
				});
				winScreen.add(againButton);
				
				JButton homeButton = new JButton("Home");
				homeButton.setBackground(Color.BLACK);
				homeButton.setFont(new Font("Russo One", Font.PLAIN, 15));
				homeButton.setForeground(Color.GREEN);
				homeButton.setBorder(BorderFactory.createLineBorder(Color.green));
				homeButton.setOpaque(true);
				homeButton.setBounds(105, 65, 69, 29);
				homeButton.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						homeButton.setBackground(Color.GRAY);
					}
					public void mouseExited(MouseEvent evt) {
						homeButton.setBackground(Color.BLACK);
					}
			    });
				homeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BattleshipMain.buttonClickSFX();
						((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "startScreen"); 
						frame.setBounds(100, 100, 546, 534);
						winScreen.setVisible(false);
					}
				});
				winScreen.add(homeButton);	
        		
            }
            
		}	
//		BattleshipMain.isComputerTurn = false;

		Timer timer = new Timer("delayTimer");
		timer.schedule(new TimerTask() {
			public void run() {
				computerTurn();
			}
		}, 1000);
		
	}

	
//	static int[][] board = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//						    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//						    {0, 1, 0, 0, 2, 0, 0, 0, 0, 0},
//						    {0, 1, 0, 0, 2, 0, 3, 3, 3, 0},
//						    {0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
//						    {0, 0, 0, 0, 0, 0, 0, 5, 0, 0},
//						    {0, 0, 0, 0, 0, 0, 0, 5, 0, 0},
//						    {0, 4, 4, 4, 4, 0, 0, 5, 0, 0},
//						    {0, 0, 0, 0, 0, 0, 0, 5, 0, 0},
//						    {0, 0, 0, 0, 0, 0, 0, 5, 0, 0}};
	
//	static int[] shipCount = {2, 3, 3, 4, 5};
	static boolean isHit = false;
	static int hitCount = 0;
	static int missCount = 0;
	
	static ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(100);
	static ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
		
	static Coordinate curCoord;
	static int curCoordVal;
	
	public static void computerTurn() {
		System.out.println("computer turn");
		if(hitCount == 17) {
			JDialog loseScreen = new JDialog();
			loseScreen.setSize(100, 100);
			loseScreen.setBounds(244, 345, 200, 150);
			loseScreen.setVisible(true);
			loseScreen.getContentPane().setBackground(Color.BLACK);
			((JComponent) loseScreen.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.red, 3));
			loseScreen.setLayout(null);
			
			JLabel youWinLabel = new JLabel("YOU LOSE");
			youWinLabel.setHorizontalAlignment(SwingConstants.CENTER);
			youWinLabel.setFont(new Font("Black Ops One", Font.PLAIN, 30));
			youWinLabel.setForeground(Color.red);
			youWinLabel.setBounds(15, 20, 167, 29);
			loseScreen.add(youWinLabel);
			
			JButton againButton = new JButton("Again");
			againButton.setBackground(Color.BLACK);
			againButton.setFont(new Font("Russo One", Font.PLAIN, 15));
			againButton.setForeground(Color.RED);
			againButton.setBorder(BorderFactory.createLineBorder(Color.red));
			againButton.setOpaque(true);
			againButton.setBounds(24, 65, 69, 29);
			againButton.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					againButton.setBackground(Color.GRAY);
				}
				public void mouseExited(MouseEvent evt) {
					againButton.setBackground(Color.BLACK);
				}
		    });
			againButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BattleshipMain.buttonClickSFX();
					((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "coinPanel"); 
					frame.setBounds(100, 100, 546, 534);
					loseScreen.setVisible(false);
					CoinPanel.tossCoinButton.setEnabled(true);
				}
			});
			loseScreen.add(againButton);
			
			JButton homeButton = new JButton("Home");
			homeButton.setBackground(Color.BLACK);
			homeButton.setFont(new Font("Russo One", Font.PLAIN, 15));
			homeButton.setForeground(Color.RED);
			homeButton.setBorder(BorderFactory.createLineBorder(Color.red));
			homeButton.setOpaque(true);
			homeButton.setBounds(105, 65, 69, 29);
			homeButton.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					homeButton.setBackground(Color.GRAY);
				}
				public void mouseExited(MouseEvent evt) {
					homeButton.setBackground(Color.BLACK);
				}
		    });
			homeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BattleshipMain.buttonClickSFX();
					((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "startScreen"); 
					frame.setBounds(100, 100, 546, 534);
					loseScreen.setVisible(false);
				}
			});
			loseScreen.add(homeButton);
		}

		
		Random rand = new Random();
		int randIndex = rand.nextInt(coordinates.size()-1);
		curCoord = coordinates.get(randIndex);
		coordinates.remove(curCoord);
		//
		//			System.out.println(curCoord.toString());
		//
		System.out.println(curCoord.toString());
		curCoordVal = StrategyPanel.userGrid[coordinates.get(randIndex).getY()-1][coordinates.get(randIndex).getX()-1];
		System.out.println(curCoordVal);
		if(curCoordVal <= 0) {
			StrategyPanel.userGrid[curCoord.getY()-1][curCoord.getX()-1] = 9;
			oppButtons[curCoord.getY()-1][curCoord.getX()-1].setBackground(Color.LIGHT_GRAY);
			BattleshipMain.missSFX();
			missCount++;
		} else if(curCoordVal > 0){
			hitCount++;
			System.out.println("hit");
    		BattleshipMain.hitSFX();
//			isHit = true;
			if(curCoordVal == DESTROYER_SYMBOL) {
				setColour(oppButtons, yourDestroyerLabels, oppDestroyerLabelIndex, curCoord.getY()-1, curCoord.getX()-1);
				oppDestroyerLabelIndex++;
			} else if (curCoordVal == SUBMARINE_SYMBOL) {
				setColour(oppButtons, yourSubmarineLabels, oppSubmarineLabelIndex, curCoord.getY()-1, curCoord.getX()-1);
				oppSubmarineLabelIndex++;
			} else if (curCoordVal == CRUISER_SYMBOL) {
				setColour(oppButtons, yourCruiserLabels, oppCruiserLabelIndex, curCoord.getY()-1, curCoord.getX()-1);
				oppCruiserLabelIndex++;
			} else if (curCoordVal == BATTLESHIP_SYMBOL) {
				setColour(oppButtons, yourBattleshipLabels, oppBattleshipLabelIndex, curCoord.getY()-1, curCoord.getX()-1);
				oppBattleshipLabelIndex++;
			} else if (curCoordVal == CARRIER_SYMBOL) {
				setColour(oppButtons, yourCarrierLabels, oppCarrierLabelIndex, curCoord.getY()-1, curCoord.getX()-1);
				oppCarrierLabelIndex++;
			}
			StrategyPanel.userGrid[curCoord.getY()-1][curCoord.getX()-1] *= -1;
//        	setColour(yourButtons, oppBattleshipLabels, battleshipLabelIndex, rowButtonFire, columnButtonFire); 

		}

	    oppHitsLabel.setText("Hits: " + hitCount); 
	    oppMissLabel.setText("Misses: " + missCount); 
	    oppTotalLabel.setText("Total shots: " + (hitCount + missCount));
		BattleshipMain.isComputerTurn = false;
	}

	
	
	
	
	
	public static void fillCoords(ArrayList<Coordinate> coordinates) {
		for(int i = 1; i <= 10; i++) {
			for(int j = 1; j <= 10; j++) {
				Coordinate coord = new Coordinate(i, j);
				coordinates.add(coord);
//				System.out.println(coord.toString());
			}
		}
	}
	

}
