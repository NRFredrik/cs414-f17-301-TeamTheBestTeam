package edu.colostate.cs.cs414.teamthebestteam.rollerball.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.RollIF;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Rollerball;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;




public class ClientTable implements RollIF {

	private JFrame gameFrame;
	private Board rollBoard;

	private static Dimension OUTER_DIMENSION = new Dimension(600,600);
	//dimensions of board panel
	private static final Dimension BOARD_DIMENSIONS = new Dimension(400,400);
	//tiles size
	private static final Dimension TILE_DIMENSION = new Dimension(10,10);

	//represents board panel
	public BoardPanel boardPanel;
	private Tile tilePieceIsOn;
	private Tile destinationTile;
	private Piece movedByPlayer;
	public Rollerball roll;
	public int test;

	
	private JFrame mainFrame;
    private JFrame loginFrame;
    private JPanel loginPanel;
    private JTextField messageField;
    private JTextField userField;
    private JTextField passField;
    private JPanel mainPane;
    private JTextArea textBox;
    private JButton sendButton;
    private JButton loginButton;
    private JButton registerButton;
    private JButton logoffButton;
    private JLabel colorLabel; 
    private JLayeredPane left;
    private Timer timer;
    private JTextArea statusArea;
    
	public ClientTable()throws Exception
	{
	
		mainFrame = new JFrame();
        mainFrame.setTitle("Rollerball");
        mainFrame.setBounds(200, 200, 770, 550);
        mainFrame.setSize(1000,500);
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new ExitWindow());
        
        mainPane = new JPanel(new GridLayout(1,1));
        mainPane.setBackground(SystemColor.inactiveCaptionBorder);
        mainPane.setBackground(Color.lightGray);
        
        
        //login frame
	   	loginFrame = new JFrame("Login");
	   	loginFrame.setBounds(100, 100, 300, 250);
	   	loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //login panel
		loginPanel = new JPanel();
		loginPanel.setLayout(null);
		loginPanel.setBackground(Color.lightGray);
		loginFrame.add(loginPanel);
       
       //Label for user field
       JLabel userLabel = new JLabel("User");
       userLabel.setBounds(10, 70, 80, 25);
       loginPanel.add(userLabel);
       
       //User field on login panel
       userField = new JTextField();
       userField.setText("username");
       userField.setBounds(100, 70, 160, 25);
       userField.addKeyListener(new EnterPressed());
       loginPanel.add(userField);

       //label for password
       JLabel passLabel = new JLabel("Password");
       passLabel.setBounds(10, 100, 80, 25);
       loginPanel.add(passLabel);
       
       //Pasword Field
       passField = new JTextField();
       passField.setText("password\r\n");
       passField.setBounds(100, 100, 160, 25);
       passField.addKeyListener(new EnterPressed());
       loginPanel.add(passField);
       
       //Login Button on login panel
       loginButton = new JButton("Login");
       loginButton.setToolTipText("Make sure information in all fields is correct");
       loginButton.addActionListener(new loginListener());
       loginButton.setBounds(75, 150, 100, 25);
       loginPanel.add(loginButton);
       
       registerButton = new JButton("Register");
       registerButton.setToolTipText("Make sure information in all fields is correct");
       registerButton.addActionListener(new registerListener());
       registerButton.setBounds(180, 150, 100, 25);
       loginPanel.add(registerButton);
        
       //rollerboard panel
		this.rollBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.boardPanel.setBounds(140, 450, 530, 45);
		
		
		left = new JLayeredPane();
        mainPane.setBackground(SystemColor.inactiveCaptionBorder);
        mainPane.setBackground(Color.lightGray);
        mainFrame.getContentPane().add(mainPane);

        
		logoffButton = new JButton("Logoff");
        logoffButton.setToolTipText("Say Good bye :)");
        logoffButton.setEnabled(false);
        //logoffButton.addActionListener(new logoffListener());
        logoffButton.setBounds(10, 5, 115, 25);
        left.add(logoffButton);
	

       

        //Pane to show users 
        JScrollPane statusScrollPane = new JScrollPane();
        statusScrollPane.setBounds(5, 33, 130, 350);
        left.add(statusScrollPane);
        
        //Area to show users
        statusArea = new JTextArea();
        statusScrollPane.setViewportView(statusArea);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Arial", Font.PLAIN, 15));
        statusArea.setBackground(Color.lightGray);
        
        
        //Pane for main message feed
        JScrollPane textBoxScrollPane = new JScrollPane();
        textBoxScrollPane.setBounds(140, 33, 355, 350);
        left.add(textBoxScrollPane);
        
        //Textbox for main message feed
        textBox = new JTextArea();
        textBox.setFont(new Font("Dialog", Font.PLAIN, 17));       
        textBoxScrollPane.setViewportView(textBox);
        textBox.setEditable(false);
        textBox.setBackground(Color.lightGray);
        
        //message feild for typing messages
        messageField = new JTextField();
        messageField.setEnabled(false);
        messageField.setBounds(10, 400, 350, 45);
        //messageField.addKeyListener(new EnterPressed());
        left.add(messageField);
        
        //button to send messages
        sendButton = new JButton("Send");
        sendButton.setName("Send");
        sendButton.setToolTipText("Send message");
        sendButton.setEnabled(false);
        //sendButton.addActionListener(new sendAction());
        sendButton.setBounds(365, 400, 115, 25);
        left.add(sendButton);
		
        JPanel right = boardPanel;
		mainPane.add(left);
		mainPane.add(right);
		mainFrame.getContentPane().add(mainPane);
		this.mainFrame.setVisible(true);
		this.loginFrame.setVisible(true);
	}

	private void fillMenu(JMenuBar menuBar) 
	{
		menuBar.add(createMenu());
	}

	private JMenu createMenu() {
		//Our menu for the frame
		JMenu fileMenu = new JMenu("File");

		//a menu item that we will add to the menu above
		JMenuItem quitGame = new JMenuItem("Quit");

		//add a listener so when the menu item is clicked
		//the defined action will happen. In this case a system print
		quitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("The Quit button was pressed");
			}
		});
		fileMenu.add(quitGame);
		return fileMenu;
	}

	//visual component representing the board
	//adds 49 tiles to list and added to board panel
	public class BoardPanel extends JPanel
	{
		List<TilePanel> boardTile;

		public BoardPanel()
		{
			//grid layout will be 7x7 representing the 49 squares in rollerball
			super(new GridLayout(7,7));
			this.boardTile = new ArrayList<>();

			//loop NUM_TILES times and add tile to jpanel
			for(int i = 0; i < BoardUtilities.NUM_TILES; i++)
			{
				//pass baord panel and tileid
				TilePanel tilePanel = new TilePanel(this, i);

				//add tiles to board
				this.boardTile.add(tilePanel);
				//add the panel
				add(tilePanel);
			}
			setPreferredSize(BOARD_DIMENSIONS);
			validate();
		}

		/**
		 * clear board and re-draw tile
		 * @param rollBoard
		 */
		public void drawBoard(Board rollBoard) 
		{
			removeAll();
			for(TilePanel t : boardTile)
			{
				t.drawTile(rollBoard);
				add(t);
			}
			validate();
			repaint();
		}
	}

	//visual component representing the tiles on chess board
	//maps to a tile in game
	private class TilePanel  extends JPanel
	{
		private int tileID;


		public TilePanel(final BoardPanel boardPanel, final int tileID) 
		{
			super(new GridBagLayout());
			this.tileID = tileID;
			setPreferredSize(TILE_DIMENSION);
			//used to distinguish dark from light tiles
			setColorOfTile();

			setTilesIcon(rollBoard);

			//listen for mouse click events for each tile
			addMouseListener(new MouseListener(){

				//listen for clicks on all tiles
				//if click occurs get tileid and assign to source tile
				@Override
				public void mouseClicked(MouseEvent e) 
				{

					//cancel all selections out. Means player changed mind 
					if(SwingUtilities.isRightMouseButton(e))
					{
						tilePieceIsOn = null;
						destinationTile = null;
						movedByPlayer = null;
						System.out.println("Right mouse button pressed, so select your source again");
					}
					//something was picked and is being moved (2nd click)
					else if(SwingUtilities.isLeftMouseButton(e))
					{
						//player has not made source tile selection yet
						if(tilePieceIsOn == null)
						{
							//assign this tile to the source tile
							tilePieceIsOn = rollBoard.getTile(tileID);
							System.out.println("Source tile: "+ tilePieceIsOn.getTileCoord()+ " was selected");
							movedByPlayer = tilePieceIsOn.getPiece();
							//if there's no piece on tile
							if(movedByPlayer == null)
							{
								tilePieceIsOn = null;
							}
						}
						//there's a piece so lets move it
						//TODO need move implementation
						else
						{
							destinationTile = rollBoard.getTile(tileID);
							
							Move move = Move.FactoryMove.createMove(rollBoard, tilePieceIsOn.getTileCoord(), destinationTile.getTileCoord());
							MoveTransition trans;
							try {
								Board tempBoard = rollBoard;
								trans = tempBoard.currentPlayer().movePlayer(move);
							
							if(trans.getStatus().isDone())
							{
								/*
								if(rollBoard.currentPlayer.equals(rollBoard.white))
								{
									roll.handleMessageFromClientUI("white," + tilePieceIsOn.getTileCoord() + "," + destinationTile.getTileCoord());
								}
								else
								{
									roll.handleMessageFromClientUI("black," +tilePieceIsOn.getTileCoord() + "," + destinationTile.getTileCoord());
								}
								*/	
								roll.handleMessageFromClientUI(tilePieceIsOn.getTileCoord() + "," + destinationTile.getTileCoord());
								rollBoard = trans.getBoard();
							}
							else
							{
								System.out.println("THAT MOVE IS NOT VALID FOR THE CHOSEN PIECE. REFER TO RULE BOOK AND TRY AGAIN\n");
							}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
					}//end else if
				}
				
			}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});



			validate();
		}

		//TODO will need to update this once we make highlighting legal moves functionality
		public void drawTile(Board rollBoard) 
		{
			setColorOfTile();
			setTilesIcon(rollBoard);
			validate();
			repaint();
		}

		private void setTilesIcon(final Board board)
		{
			this.removeAll();
			//if the tile is occupied get piece name and match it with pic in folder
			if(board.getTile(this.tileID).isTileOccupided())
			{
				try {
					//get the pieces COLOR + _ + name of piece and append picture extension
					final BufferedImage img = ImageIO.read(new File("pictures/" + board.getTile(this.tileID).getPiece().getPieceAssociation().toString() + "_"+
							board.getTile(this.tileID).getPiece().toString() + ".gif"));
					//add the icons to the frame
					add(new JLabel(new ImageIcon(img)));
				} catch (IOException e) {
					e.getStackTrace();
				}

				//add a lable and image
			}
		}

		//draw tiles according to game specs giving them color
		private void setColorOfTile() 
		{ 
			//setting color for odd tiles.
			if(BoardUtilities.FIRST_ROW[this.tileID] || BoardUtilities.THIRD_ROW[this.tileID]  || BoardUtilities.FIFTH_ROW[this.tileID] || BoardUtilities.LAST_ROW[this.tileID])
			{
				if(Tile.isNotUsableTile(this.tileID))
				{
					setBackground(Color.WHITE);
				}

				else if(this.tileID % 2 == 0)
				{
					Color color = new Color (203,128,93);
					setBackground(color);
				}
				else
				{
					Color color = new Color (255,199,148);
					setBackground(color);
				}
			}

			//setting color for even tiles
			else if(BoardUtilities.SECOND_ROW[this.tileID] || BoardUtilities.FOURTH_ROW[this.tileID]  || BoardUtilities.SIXTH_ROW[this.tileID])
			{
				if(Tile.isNotUsableTile(this.tileID))
				{
					setBackground(Color.WHITE);
				}
				else if(this.tileID % 2 != 0)
				{
					Color color = new Color (255,199,148);
					setBackground(color);
				}
				else
				{
					Color color = new Color (203,128,93);
					setBackground(color);
				}
			}

		}
	}
	public static void main(String[] args) throws Exception
	{
	
		Board board = Board.createStandardBoard();
		
		ClientTable table = new ClientTable();
	}


	public void displayColor(String message) 
	{
		colorLabel = new JLabel(message);
		colorLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        colorLabel.setBounds(130, 7, 102, 21);
		left.add(colorLabel);
		colorLabel.setVisible(true);
	}
	
	public void displayBoard(int curCoord, int endCoord)
	{
		Move move = Move.FactoryMove.createMove(rollBoard, curCoord, endCoord);
		
		MoveTransition trans;
		
		try {
			trans = rollBoard.currentPlayer().movePlayer(move);
		
		if(trans.getStatus().isDone())
		{
			rollBoard = trans.getBoard();
			//TODO add the move to a log for debugging
		}
		tilePieceIsOn = null;
		destinationTile = null;
		movedByPlayer = null;
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				//roll.handleMessageFromClientUI(rollBoard);
				boardPanel.drawBoard(rollBoard);
			}
		});
	}
	
	@Override
	public void display(Object message) 
	{
		if(message instanceof String)
		{
			if(message.toString().contains("login"))
			{
				List<String> items = Arrays.asList(((String) message).split(","));
				String color =items.get(1);
				displayColor(color);
			}
			else
			{
				List<String> items = Arrays.asList(((String) message).split(","));
				int curCoord =Integer.parseInt(items.get(0));
				int endCoord =Integer.parseInt(items.get(1));
				displayBoard(curCoord,endCoord);
			}
		}
		else
		{
			updateUsers((ArrayList<String>) message);
		}
	}
	
	 private class ExitWindow implements WindowListener 
	    {
	        public void windowActivated(WindowEvent event) 
	        {
	        	
	        }

	        public void windowClosing(WindowEvent event)
	        {
	        	//client.sendM("#logoff");
	        }

	        public void windowDeactivated(WindowEvent event) 
	        {
	        	
	        }

	        public void windowDeiconified(WindowEvent event) 
	        {
	        	
	        }

	        public void windowIconified(WindowEvent event) 
	        {
	        	
	        }

	        public void windowOpened(WindowEvent event) 
	        {
	        	
	        }

	        public void windowClosed(WindowEvent e) 
	        {
	        	
	        }
	    }
	
	  private class EnterPressed implements KeyListener 
	    {

	        public void keyPressed(KeyEvent hitEnter) 
	        {

	        }
	        
	        public void keyReleased(KeyEvent hitEnter) 
	        {
	            
	            if (hitEnter.getSource().equals(userField)) 
	            {
	                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
	                {
	                    passField.requestFocus();
	                }
	            }
	            else if (hitEnter.getSource().equals(passField)) 
	            {
	                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
	                {
	                    loginButton.doClick();
	                }
	            }
	        }
	        public void keyTyped(KeyEvent hitEnter) {

	        }
	    }
	 
	//updates the status area with online users and their status
	    public void updateUsers(ArrayList<String> users) 
	    {
	        statusArea.setText("");
	        
	        for (int i = 0; i < users.size(); i++) 
	        {
	            statusArea.append( users.get(i)+"\n");
	        }
	    }
	  
	  private class loginListener implements ActionListener 
	    {
	        public void actionPerformed(ActionEvent event) 
	        {
	        	final Config con = new Config();

	            if (userField.getBackground().equals(Color.red)) 
	            {
	                userField.setBackground(Color.white);
	            }

	            if (passField.getBackground().equals(Color.red)) 
	            {
	                passField.setBackground(Color.white);
	            }

	            System.out.println(userField.getText());
	            System.out.println(passField.getText());
	            String userID = userField.getText();
	            String password = passField.getText();

	            if(con.userExists(userID,password))
				{
					//TODO   Allow user to proceed to start game
	            	try {
						roll = new Rollerball(userID,"localhost", 5555, ClientTable.this);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("you're logged in");
					loginFrame.setVisible(false);
					ServerRequests();//start sending requests to server
					
				}
				else
				{
					JOptionPane.showMessageDialog(loginFrame, "This user does not exist. Register?");
				}
	       
	            
	            /*
	            //hides login window and enables all components on GUI
	            loginFrame.setVisible(false);
	            loginButton.setEnabled(false);
	            logoffButton.setEnabled(true);
	            statusButton.setEnabled(true);
	            channelMenu.setEnabled(true);
	            commandMenu.setEnabled(true);
	            messageField.setEnabled(true);
	            sendButton.setEnabled(true);
	            sketchButton.setEnabled(true);
	            userField.setEnabled(false);
	            passField.setEnabled(false);
	            hostField.setEnabled(false);
	            portField.setEnabled(false);*/
	           
	            
	        }

	    }
	  
	  private class registerListener implements ActionListener 
	    {
		  
	        public void actionPerformed(ActionEvent event) 
	        {
					Register register = new Register();
					register.frame.setVisible(true);
			}
	    }  
	  
	  private class ConnectionChecker implements ActionListener 
	    {
	        public void actionPerformed(ActionEvent event) 
	        {
	            if (!roll.isConnected()) 
	            {
	                //logoffButton.doClick();
	            }
	        }
	    }
	  
	  private class RequestOnlineUsers implements ActionListener 
	    {
	        public void actionPerformed(ActionEvent event) 
	        {
	            roll.handleMessageFromClientUI(("#userList"));
	        }
	    }
	  
	  private void ServerRequests() 
	    {

	        timer = new Timer(1000, new ConnectionChecker());
	        timer.setInitialDelay(0);
	        timer.setDelay(1000);
	        timer.addActionListener(new RequestOnlineUsers());   
	        timer.start();
	    }
	  
	
}







