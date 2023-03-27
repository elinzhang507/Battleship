import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StrategyPanel implements ActionListener{	
	static int[][] userGrid = new int[10][10]; 
	static JButton[][] userShipGrid = new JButton[10][10];
	
	String userChoice = ""; 
	
	// row and column of button pressed 
	int rowButtonPressed = 0; 
	int columnButtonPressed = 0; 
	
	// end row and column for horizontal ship
	// right-most 
	int shipEndRowHorizontalRight = 0; 
	int shipEndColumnHorizontalRight = 0; 
	
	// end row and column for horizontal ship 
	// left-most 
	int shipEndRowHorizontalLeft; 
	int shipEndColumnHorizontalLeft; 
	
	// end row and column for vertical ship 
	// bottom
	int shipEndRowVerticalBottom = 0; 
	int shipEndColumnVerticalBottom = 0; 
	
	// end row and column for vertical ship 
	// top 
	int shipEndRowVerticalTop = 0; 
	int shipEndColumnVerticalTop = 0; 

	// row and column of the button the user picked (end of the ship)
	int rowButtonOrientation = 0; 
	int columnButtonOrientation = 0; 
	
	static int numOfShips = 0;
	
	// boolean variable to check if the ship will overlap another ship 
	boolean overlapHorizontalShipRight = false; 
	boolean overlapHorizontalShipLeft = false; 
	boolean overlapVerticalShipTop = false; 
	boolean overlapVerticalShipBottom = false; 
	
	static JFrame frame = BattleshipMain.frame; //variable so it's easy to reference main frame
	
	static JComboBox comboBox;
	static JButton readyButton;
	
	static int counter = 0;
	
//	static Color ogColour = Color.BLACK;
	
	public  StrategyPanel() {
		JPanel strategyPanel = new JPanel();
		strategyPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(strategyPanel, "strategyPanel");
		strategyPanel.setLayout(null);
		
		JPanel strategyTitlePanel = new JPanel();
		strategyTitlePanel.setBackground(Color.BLACK);
		strategyTitlePanel.setBounds(6, 6, 534, 62);
		strategyPanel.add(strategyTitlePanel);
		
		JLabel strategyTitle = new JLabel("STRATEGY PANEL");
		strategyTitle.setFont(new Font("Black Ops One", Font.PLAIN, 50));
		strategyTitle.setForeground(Color.GREEN);
		strategyTitle.setBackground(Color.BLACK);
		strategyTitlePanel.add(strategyTitle);
		
		JPanel strategyShipListPanel = new JPanel();
		strategyShipListPanel.setBackground(Color.BLACK);
		strategyShipListPanel.setBounds(16, 80, 143, 350);
		strategyPanel.add(strategyShipListPanel);
		strategyShipListPanel.setLayout(null);
		
		String[] shipTypes = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
		comboBox = new JComboBox(shipTypes);
		comboBox.setBounds(6, 5, 131, 27);
		strategyShipListPanel.add(comboBox);
		comboBox.setName("shipComboBox");
		comboBox.addActionListener(this);
		comboBox.setSelectedIndex(0);

		
		JLabel shipInfoImage = new JLabel();
		shipInfoImage.setIcon(new ImageIcon("images/shipinfo.png"));
		shipInfoImage.setBounds(16, 160, 110, 153);
		strategyShipListPanel.add(shipInfoImage);
		
		JPanel strategyGridPanel = new JPanel();
		strategyGridPanel.setBackground(Color.BLACK);
		strategyGridPanel.setBounds(173, 80, 350, 350);
		strategyPanel.add(strategyGridPanel);
		strategyGridPanel.setLayout(new GridLayout(10, 10));
		
		
//		userGrid = new int [10][10]; 
		
//		for (int i = 0; i < userGrid.length; i++) {
//			for (int j = 0; j < userGrid[i].length; j++) {
//				userGrid[i][j] = 0;  
//			}
//		}
		
		for(int i = 0; i < 10; i++) {
			for(int j  = 0; j < 10; j++) {
				userShipGrid[i][j] = new JButton();
			}
		}

		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				JButton button = userShipGrid[i][j];
				button.setName("userShipGrid");
				button.setBackground(Color.BLACK);
				button.setBorder(BorderFactory.createLineBorder(Color.green));
				button.setOpaque(true);
				strategyGridPanel.add(button);
//				button.addMouseListener(new MouseAdapter() {
//					public void mouseEntered(MouseEvent evt) {
//						button.setBackground(Color.GRAY);
//					}
//					public void mouseExited(MouseEvent evt) {
//						button.setBackground(Color.BLACK);
//					}
//			    });
				userShipGrid[i][j].addActionListener(this); 
			}
		}
		
		JPanel strategyIconPanel = new JPanel();
		strategyIconPanel.setBackground(Color.BLACK);
		strategyIconPanel.setBounds(6, 450, 534, 39);
		strategyIconPanel.setLayout(null);
		strategyPanel.add(strategyIconPanel);
		
		readyButton = new JButton("READY");
		readyButton.setBounds(46, 5, 74, 29);
		readyButton.setFont(new Font("Russo One", Font.PLAIN, 13));
		readyButton.setBackground(Color.BLACK);
		readyButton.setForeground(Color.GREEN);
		readyButton.setBorder(BorderFactory.createLineBorder(Color.white));
		readyButton.setOpaque(true);
		readyButton.setEnabled(false);
		//When mouse hovers over button, change colour
		readyButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				readyButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				readyButton.setBackground(Color.BLACK);
			}
	    });
		readyButton.setName("readyButton");
		readyButton.addActionListener(this);
		readyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				((CardLayout)frame.getContentPane().getLayout()).show(frame.getContentPane(), "battlefieldPanel"); 
				frame.setBounds(100, 100, 1420, 612);
//				BattlefieldPanel.placeComputer();
			}
		});
		strategyIconPanel.add(readyButton);

		JButton fileButton = new JButton("Read file");
		fileButton.setBounds(168, 5, 100, 29);
		fileButton.setBackground(Color.BLACK);
		fileButton.setForeground(Color.WHITE);
		fileButton.setFont(new Font("Russo One", Font.PLAIN, 13));
		fileButton.setBorder(BorderFactory.createLineBorder(Color.white));
		fileButton.setOpaque(true);
		//When mouse hovers over button, change colour
		fileButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				fileButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				fileButton.setBackground(Color.BLACK);
			}
	    });
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eraseGrid();
				
				BattleshipMain.buttonClickSFX();
				JFileChooser fileChooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt"); 
		        fileChooser.setAcceptAllFileFilterUsed(false); 
		        fileChooser.setFileFilter(filter);
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int response = fileChooser.showOpenDialog(null);

				if(response == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						readFile(file);
						displayGrid();
						comboBox.setEnabled(false);
						readyButton.setEnabled(true);
						for(int i = 0; i < 10; i++) {
							for(int j = 0; j < 10; j++) {
								userShipGrid[i][j].setEnabled(false);
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(strategyPanel, "Error with file");
						return;
					}
				} else {
					return;
				}
			}
		});
		strategyIconPanel.add(fileButton);
		
		JButton randomizeButton = new JButton("Randomize");
		randomizeButton.setBounds(294, 5, 100, 29);
		randomizeButton.setBackground(Color.BLACK);
		randomizeButton.setForeground(Color.WHITE);
		randomizeButton.setFont(new Font("Russo One", Font.PLAIN, 13));
		randomizeButton.setBorder(BorderFactory.createLineBorder(Color.white));
		randomizeButton.setOpaque(true);
		//When mouse hovers over button, change colour
		randomizeButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				randomizeButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				randomizeButton.setBackground(Color.BLACK);
			}
	    });
		randomizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				eraseGrid();
				userGrid = BattlefieldPanel.placeComputer();
				displayGrid();
				disableButtons(userShipGrid);
				comboBox.setEnabled(false);
				readyButton.setEnabled(true);
			}
		});
		strategyIconPanel.add(randomizeButton);
		
		JButton clearButton = new JButton("Clear board");
		clearButton.setBounds(417, 5, 100, 29);
		clearButton.setBackground(Color.BLACK);
		clearButton.setForeground(Color.RED);
		clearButton.setFont(new Font("Russo One", Font.PLAIN, 13));
		clearButton.setBorder(BorderFactory.createLineBorder(Color.white));
		clearButton.setOpaque(true);
		//When mouse hovers over button, change colour
		clearButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				clearButton.setBackground(Color.GRAY);
			}
			public void mouseExited(MouseEvent evt) {
				clearButton.setBackground(Color.BLACK);
			}
	    });
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BattleshipMain.buttonClickSFX();
				comboBox.setEnabled(true);
				readyButton.setEnabled(false);
				eraseGrid();
				numOfShips = 0;
				try {
					comboBox.removeAllItems();
				} catch (Exception e2) {}
				
				comboBox.addItem("Carrier");
				comboBox.addItem("Battleship");
				comboBox.addItem("Cruiser");
				comboBox.addItem("Submarine");
				comboBox.addItem("Destroyer");

				comboBox.setSelectedIndex(0);

			}
		});
		strategyIconPanel.add(clearButton);
	}
	
	// here are all the methods 
	
	
	public static void readFile(File file) throws FileNotFoundException {
		Scanner reader = new Scanner(file);
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				userGrid[i][j] = reader.nextInt();
			}
		}
	}
	
	public static void eraseGrid() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(userGrid[i][j] != 0) {
					userShipGrid[i][j].setBackground(Color.BLACK);
					userGrid[i][j] = 0;
					userShipGrid[i][j].setEnabled(false);
				}
			}
		}
	}
	
	public static void displayGrid() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(userGrid[i][j] != 0) {
					userShipGrid[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	
	// calculate the end index 
	public static int calculateIndex (int columnOrRow, int number) {
		int index = columnOrRow + number; 
		return index; 
	}
	
	// check if the proposed horizontal ship will overlap over pre-existing ships 
	public static boolean checkForOverlapHorizontal(int[][] userGrid, int startValue, int endValue, int buttonRow) {
		for (int i = startValue; i <= endValue; i++) {
			if (userGrid[buttonRow][i] != 0) {
				return true; 
			} 
		}
		return false; 
	}
	// check if the proposed vertical ship will overlap over pre-existing ships 
	public static boolean checkForOverlapVertical(int[][] userGrid, int startValue, int endValue, int buttonColumn) {
		for (int i = startValue; i <= endValue; i++) {
			if (userGrid[i][buttonColumn] != 0) {
				return true; 
			} 
		}
		return false; 
	}
	
	// do actions to the proposed ends of the ships (up, down, left, right)
	public static void identifyShipEnds(JButton[][] userGrid, int row, int column, String name) {
		userGrid[row][column].setOpaque(true); 
		Color colour = new Color(102, 255, 102); 
//		ogColour = colour;
		userGrid[row][column].setBackground(colour); 
		
		userGrid[row][column].setEnabled(true);
		userGrid[row][column].setName(name);
	}
	
	// write numbers to int array that will store the ships numerically 
	public static void writeToUserGrid(String shipType, int[][] userGrid, int row, int column) {
		if (shipType.equals("Carrier")) {
			userGrid[row][column] = 5; 
		} else if (shipType.equals("Battleship")) {
			userGrid[row][column] = 4; 
		} else if (shipType.equals("Cruiser")) {
			userGrid[row][column] = 3; 
		} else if (shipType.equals("Submarine")) {
			userGrid[row][column] = 2; 
		} else if (shipType.equals("Destroyer")) {
			userGrid[row][column] = 1; 
		}
	}
	
	// method to disable buttons surrounding ships to prevent ships from touching 
	public static void editBorderShipButtons(JButton[][] userGridVisual, int[][] userGridNumbers, int row, int column) {
		userGridVisual[row][column].setEnabled(false); 
		userGridNumbers[row][column] = -1; 
	}
	
	// method to set colour to the ship 
	public static void setColourToShip(JButton[][] userGrid, int row, int column) {
		userGrid[row][column].setOpaque(true); 
		//Color colour = new Color(0, 204, 0); 
		userGrid[row][column].setBackground(Color.WHITE);
		userGrid[row][column].setEnabled(false);
	}
	
	// method to change anything not needed on the visual user grid 
	// method to change anything not needed on the visual user grid 
    public static void changeBack(JButton[][] userVisualGrid, int[][] userNumberGrid, String ship) {
        for (int i = 0; i < userVisualGrid.length; i++) {
            for (int j = 0; j < userVisualGrid[i].length; j++) {
                if (ship.equals("Destroyer")) {
                    if ( (userNumberGrid[i][j] == 0) || (userNumberGrid[i][j] == -1) ) {
                        if (userNumberGrid[i][j] == 0) {
                            userVisualGrid[i][j].setEnabled(true);
                        }
                        userVisualGrid[i][j].setOpaque(true); 
                        userVisualGrid[i][j].setBackground(Color.BLACK); 
                        userVisualGrid[i][j].setName("userShipGrid");
                    }
                } else {
                    if (userNumberGrid[i][j] == 0) {
                        userVisualGrid[i][j].setEnabled(true);
                        userVisualGrid[i][j].setOpaque(true); 
                        userVisualGrid[i][j].setBackground(Color.BLACK); 
                        userVisualGrid[i][j].setName("userShipGrid");
                    }
                }
            }
        }
    }
	
	public static void disableButtons(JButton[][] userShipGrid) {
		for (int i = 0; i < userShipGrid.length; i++) {
			for (int j = 0; j < userShipGrid[i].length; j++) {
				userShipGrid[i][j].setEnabled(false);
			}
		}
	}
	
	public static void disableAllButtons() {
		if(numOfShips == 5) {
			for(int r = 0; r < userShipGrid.length; r++) {
				for(int c = 0; c < userShipGrid[r].length; c++) {
					userShipGrid[r][c].setEnabled(false);
				}
			}
			readyButton.setEnabled(true);
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		Component source = (Component)event.getSource(); 
		String sourceName = source.getName(); 

		if(sourceName.equals("shipComboBox")) {
	        JComboBox cb = (JComboBox)event.getSource();
	        userChoice = (String)cb.getSelectedItem();
	        counter++;
			
	        if(counter > 1) {
				changeBack(userShipGrid, userGrid, userChoice);  
	        }
	        
		} else if (sourceName.equals("userShipGrid")){	
			outer: 
			for (int i = 0; i < userShipGrid.length; i++) {
				for (int j = 0; j < userShipGrid[i].length; j++) {
					
					if (userShipGrid[i][j].equals(source)) {
						// colour that button grey 
						userShipGrid[i][j].setOpaque(true);
						userShipGrid[i][j].setBackground(Color.LIGHT_GRAY); 
						
						rowButtonPressed = i; 
						columnButtonPressed = j; 
						
						break outer; 
					}
				}
			}
			
			// set values to these indexes 
			// horizontal ships have same row 
			shipEndRowHorizontalRight = rowButtonPressed; 
			shipEndRowHorizontalLeft = rowButtonPressed; 
			
			// vertical ships have same column 
			shipEndColumnVerticalBottom = columnButtonPressed; 
			shipEndColumnVerticalTop = columnButtonPressed; 
			
			// calculating the indexes of the very end of the ship 
			// 2 horizontals, 2 verticals 
			if (userChoice.equals("Carrier")) {
				shipEndColumnHorizontalRight = calculateIndex(columnButtonPressed, 4); 
				shipEndColumnHorizontalLeft = calculateIndex(columnButtonPressed, -4); 
				
				shipEndRowVerticalBottom = calculateIndex(rowButtonPressed, 4); 
				shipEndRowVerticalTop = calculateIndex(rowButtonPressed, - 4); 
				
			} else if (userChoice.equals("Battleship")) {
				shipEndColumnHorizontalRight = calculateIndex(columnButtonPressed, 3); 
				shipEndColumnHorizontalLeft = calculateIndex(columnButtonPressed, - 3); 
				
				shipEndRowVerticalBottom = calculateIndex(rowButtonPressed, 3); 
				shipEndRowVerticalTop = calculateIndex(rowButtonPressed, - 3); 
				
			} else if (userChoice.equals("Cruiser") || userChoice.equals("Submarine")) {
				shipEndColumnHorizontalRight = calculateIndex(columnButtonPressed, 2); 
				shipEndColumnHorizontalLeft = calculateIndex(columnButtonPressed, - 2); 
				
				shipEndRowVerticalBottom = calculateIndex(rowButtonPressed, 2);
				shipEndRowVerticalTop = calculateIndex(rowButtonPressed, - 2); 
				
			} else if (userChoice.equals("Destroyer")){
				shipEndColumnHorizontalRight = calculateIndex(columnButtonPressed, 1); 
				shipEndColumnHorizontalLeft = calculateIndex(columnButtonPressed, - 1); 
				
				shipEndRowVerticalBottom = calculateIndex(rowButtonPressed, 1); 
				shipEndRowVerticalTop = calculateIndex(rowButtonPressed, - 1); 
			}
			
			// setEnable(false) to all buttons at first 
			for (int i = 0; i < userShipGrid.length; i++) {
				for (int j = 0; j < userShipGrid[i].length; j++) {
					userShipGrid[i][j].setEnabled(false);
				}
			}
			
			if (shipEndColumnHorizontalRight <= 9) {
				
				// check to see if any of the holes of the ship will overlap with another ship --> check horizontal right ship 
				overlapHorizontalShipRight = checkForOverlapHorizontal(userGrid, columnButtonPressed, shipEndColumnHorizontalRight, rowButtonPressed); 
				
				if (overlapHorizontalShipRight == false) {
					identifyShipEnds(userShipGrid, shipEndRowHorizontalRight, shipEndColumnHorizontalRight, "horizontalShipRight"); 
				}
			}
			
			if (shipEndColumnHorizontalLeft >= 0) {
				
				// check to see if any of the holes of the ship will overlap with another ship --> horizontal left ship 
				overlapHorizontalShipLeft = checkForOverlapHorizontal(userGrid, shipEndColumnHorizontalLeft, columnButtonPressed, rowButtonPressed); 				
				
				if (overlapHorizontalShipLeft == false) {
					identifyShipEnds(userShipGrid, shipEndRowHorizontalLeft, shipEndColumnHorizontalLeft, "horizontalShipLeft"); 
				}
			} 
			
			if (shipEndRowVerticalBottom <= 9) {
				
				// check to see if any of the holes of the ship will overlap with another ship --> vertical bottom ship 
				overlapVerticalShipBottom = checkForOverlapVertical(userGrid, rowButtonPressed, shipEndRowVerticalBottom, columnButtonPressed); 
				
				if (overlapVerticalShipBottom == false) {
					identifyShipEnds(userShipGrid, shipEndRowVerticalBottom, shipEndColumnVerticalBottom, "verticalShipBottom"); 
				}
			} 
			
			if (shipEndRowVerticalTop >= 0) {
				// check to see if any of the holes of the ship will overlap with another ship --> vertical top ship 				
				overlapVerticalShipTop = checkForOverlapVertical(userGrid, shipEndRowVerticalTop, rowButtonPressed, columnButtonPressed); 
				
				if (overlapVerticalShipTop == false) {
					identifyShipEnds(userShipGrid, shipEndRowVerticalTop, shipEndColumnVerticalTop, "verticalShipTop"); 
				}
			}
			
		} else if (sourceName.equals("horizontalShipRight")) {
			
			if(comboBox.getItemCount() > 1) {
				comboBox.removeItem(userChoice);
			} else if (comboBox.getItemCount() == 1) {
				comboBox.setSelectedItem(0);
				disableButtons(userShipGrid); 
				comboBox.setEnabled(false);
			}
			numOfShips++;
			
			for (int i = columnButtonPressed; i <= shipEndColumnHorizontalRight; i++) {
				setColourToShip(userShipGrid, shipEndRowHorizontalRight, i); 
				
				if (i == columnButtonPressed) {
					if (columnButtonPressed - 1 >= 0) {
						editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed, columnButtonPressed - 1); 
						
						if (rowButtonPressed + 1 <= 9) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed + 1, columnButtonPressed - 1); 
						}
						if (rowButtonPressed - 1 >= 0) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, columnButtonPressed - 1); 	
						}
					}
				}
				
				if (i == shipEndColumnHorizontalRight) {
					if (i + 1 <= 9) {
						editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed, i + 1); 
						
						if (rowButtonPressed + 1 <= 9) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed + 1, i + 1); 	
						}
						if (rowButtonPressed - 1 >= 0) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, i + 1); 
						}	
					}
				}
				
				if (rowButtonPressed - 1 >= 0) {
					editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, i); 	
				}
				if (rowButtonPressed + 1 <= 9) {
					editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed + 1, i); 	
				}
				
				writeToUserGrid(userChoice, userGrid, shipEndRowHorizontalRight, i); 				
				changeBack(userShipGrid, userGrid, userChoice);  
//				disableButtons(userShipGrid);
				disableAllButtons();
			}
			
		} else if (sourceName.equals("horizontalShipLeft")) {
			
			if(comboBox.getItemCount() > 1) {
				comboBox.removeItem(userChoice);
			} else if (comboBox.getItemCount() == 1) {
				comboBox.setSelectedItem(0);
				disableButtons(userShipGrid); 
				comboBox.setEnabled(false);
			}
			numOfShips++;
			
			for (int i = shipEndColumnHorizontalLeft; i <= columnButtonPressed; i++) {
				setColourToShip(userShipGrid, shipEndRowHorizontalLeft, i); 
		
				if (i == shipEndColumnHorizontalLeft) {
					if (shipEndColumnHorizontalLeft - 1 >= 0) {
						editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed, i - 1); 
						
						if (rowButtonPressed + 1 <= 9) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed + 1, i - 1); 	
						}
						if (rowButtonPressed - 1 >= 0) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, i - 1); 	
						}						
					}
				}
				
				if (i == columnButtonPressed) {
					if (columnButtonPressed + 1 <= 9) {
						editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed, columnButtonPressed + 1); 

						if (rowButtonPressed + 1 <= 9) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed + 1, columnButtonPressed + 1); 
						}
						if (rowButtonPressed - 1 >= 0) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, columnButtonPressed + 1); 
						}	
					}
				}
				
				if (rowButtonPressed - 1 >= 0) {
					editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, i); 
				}
				if (rowButtonPressed + 1 <= 9) {
					editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed + 1, i); 
				}
				
				writeToUserGrid(userChoice, userGrid, shipEndRowHorizontalLeft, i); 		
				changeBack(userShipGrid, userGrid, userChoice); 
//				disableButtons(userShipGrid);
				disableAllButtons();
			}
			
		} else if (sourceName.equals("verticalShipBottom")) {
			
			if(comboBox.getItemCount() > 1) {
				comboBox.removeItem(userChoice);
			} else if (comboBox.getItemCount() == 1) {
				comboBox.setSelectedItem(0);
				disableButtons(userShipGrid); 
				comboBox.setEnabled(false);
			}
			numOfShips++;
			
			for (int i = rowButtonPressed; i <= shipEndRowVerticalBottom; i++) {
				setColourToShip(userShipGrid, i, columnButtonPressed); 
				
				if (i == rowButtonPressed) {
					
					if (rowButtonPressed - 1 >= 0) {
						editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, columnButtonPressed); 
						if (columnButtonPressed - 1 >= 0) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, columnButtonPressed - 1); 	
						}
						if (columnButtonPressed + 1 <= 9) {
							editBorderShipButtons(userShipGrid, userGrid, rowButtonPressed - 1, columnButtonPressed + 1); 
						}
					}
				}
				if (i == shipEndRowVerticalBottom) {
					if (i + 1 <= 9) {
						editBorderShipButtons(userShipGrid, userGrid, i + 1, columnButtonPressed); 
						
						if (columnButtonPressed - 1 >= 0) {
							editBorderShipButtons(userShipGrid, userGrid, i + 1, columnButtonPressed - 1); 
						}
						if (columnButtonPressed + 1 <= 9) {						
							editBorderShipButtons(userShipGrid, userGrid, i + 1, columnButtonPressed + 1); 
						}
					}
				}
				
				if (columnButtonPressed - 1 >= 0) {
					editBorderShipButtons(userShipGrid, userGrid, i, columnButtonPressed - 1); 
				}
				if (columnButtonPressed + 1 <= 9) {					
					editBorderShipButtons(userShipGrid, userGrid, i, columnButtonPressed + 1); 
				}
				
				writeToUserGrid(userChoice, userGrid, i, columnButtonPressed); 			
				changeBack(userShipGrid, userGrid, userChoice);  
//				disableButtons(userShipGrid); 
				disableAllButtons();
			}
		
		} else if (sourceName.equals("verticalShipTop")) {
			
			if(comboBox.getItemCount() > 1) {
				comboBox.removeItem(userChoice);
			} else if (comboBox.getItemCount() == 1) {
				comboBox.setSelectedItem(0);
				disableButtons(userShipGrid); 
				comboBox.setEnabled(false);
			}
			numOfShips++;
			
			for (int i = shipEndRowVerticalTop; i <= rowButtonPressed; i++) {
				setColourToShip(userShipGrid, i, columnButtonPressed); 

				if (i == shipEndRowVerticalTop) {
					
					if (i - 1 >= 0) {					
						editBorderShipButtons(userShipGrid, userGrid, i - 1, columnButtonPressed); 

						if (columnButtonPressed - 1 >= 0) {	
							editBorderShipButtons(userShipGrid, userGrid, i - 1, columnButtonPressed - 1); 
						}
						if (columnButtonPressed + 1 <= 9) {
							editBorderShipButtons(userShipGrid, userGrid, i - 1, columnButtonPressed + 1); 
						}
					}
				}
				if (i == rowButtonPressed) {
					if (i + 1 <= 9) {						
						editBorderShipButtons(userShipGrid, userGrid, i + 1, columnButtonPressed); 
						if (columnButtonPressed - 1 >= 0) {						
							editBorderShipButtons(userShipGrid, userGrid, i + 1, columnButtonPressed - 1); 
						}
						if (columnButtonPressed + 1 <= 9) {	
							editBorderShipButtons(userShipGrid, userGrid, i + 1, columnButtonPressed + 1); 
						}
					}
				}
				
				if (columnButtonPressed - 1 >= 0) {
					editBorderShipButtons(userShipGrid, userGrid, i, columnButtonPressed - 1); 
				}
				if (columnButtonPressed + 1 <= 9) {	
					editBorderShipButtons(userShipGrid, userGrid, i, columnButtonPressed + 1); 
				}
				
				writeToUserGrid(userChoice, userGrid, i, columnButtonPressed); 
				changeBack(userShipGrid, userGrid, userChoice);  
//				disableButtons(userShipGrid);
				disableAllButtons();
			}
			
		} else if (sourceName.equals("readyButton")) {
			for (int i = 0; i < userGrid.length; i++) {
				for (int j = 0; j < userGrid[i].length; j++) {
					if (userGrid[i][j] == - 1) {
						userGrid[i][j] = 0;
					}
					System.out.print(userGrid[i][j] + "  ");
				}
				System.out.println();

			}
		}


	}
	
}
