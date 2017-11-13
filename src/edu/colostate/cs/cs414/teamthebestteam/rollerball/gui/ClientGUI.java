package edu.colostate.cs.cs414.teamthebestteam.rollerball.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.ClientInterface;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Client;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.player.MoveTransition;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece.PieceType;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientGUI implements ClientInterface {

	//Board Object
	private Board rollBoard;

	//dimensions of board panel
	private static final Dimension BOARD_DIMENSIONS = new Dimension(400,400);
	//tiles size
	private static final Dimension TILE_DIMENSION = new Dimension(10,10);

	//board panel Tiles/Peices
	public BoardPanel boardPanel;
	private Tile tilePieceIsOn;
	private Tile destinationTile;
	private Piece movedByPlayer;
	
	//Client Object
	private Client client;
	
	//Main Menu Objects
	private JFrame mmFrame;
	private JPanel mmPanel;
	private JButton loginMMButton;
	private JButton registerMMButton;
	private JButton createGameButton;
	private JButton viewProfileButton;
	private JButton invitesButton;
	private JButton viewGamesButton;
	
	//Game Frame objects
	private JFrame gameFrame;
	private JPanel right;
	private JLayeredPane left;
	private JButton quitButton;
	private JLabel colorLabel; 
	private JTextArea statusArea;
	private JTextField messageField;
	private JPanel mainPane;
	private JTextArea textBox;
	private JButton sendButton;
	
	//Login Frame Objects
	private JFrame loginFrame;
	private JPanel loginPanel;
	private JButton loginButton;
	private JTextField userField;
	private JTextField passField;
	
	//Accept Invite Frame objects
	private JFrame inviteFrame;
	private JPanel invitePanel;
	private JButton acceptButton;
	private JButton declineButton;
	JList<String> userInviteList;
	private ArrayList<String> userInvites;
	
	//View Game Frame objects viewGame
	private JFrame viewGameFrame;
	private JPanel viewGamePanel;
	private JButton joinButton;
	private JButton forfeitButton;
	JList<String> gameList;
	private ArrayList<String> gameArray;

	//View Profile Frame Objects
	private JFrame viewProfileFrame;
	private JPanel viewProfilePanel;
	private JFrame profileSelectionFrame;
	private JPanel profileSelectionPanel;
	private JComboBox<String> userProfileList;

	//Receive Invite Frame Objects
	private JFrame receiveFrame;
	private JPanel receivePanel;

	//Create Game Frame Objects
	private JFrame createGameFrame;
	private JPanel createGamePanel;
	private JComboBox<String> userList;
	
	//Unregister Frame Objects
	private JFrame unRegFrame;
	private JPanel unRegPanel;
	private JTextField emailField;
	private JTextField unRegPassField;
	private JButton unregisterButton;
	
	//Global Variables
	private ArrayList<String> users;	
	private String currentOpponent;
	private String gameOpponent;
	private String gameCreator;
	private String thisUserID;
	private String gameId;
	final Config con = new Config();
	
	//int runningGame;
	
	public ClientGUI()throws Exception
	{
//*****************INITIALIZE MAIN MENU COMPONENTS***********************		
		//MainMenu Frame
		mmFrame = new JFrame("Main Menu");
		mmFrame.setBounds(100, 100, 400, 400);
		mmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//login panel
		mmPanel = new JPanel();
		mmPanel.setLayout(null);
		mmPanel.setBackground(Color.lightGray);
		mmFrame.add(mmPanel);

		loginMMButton = new JButton("Login");
		loginMMButton.setToolTipText("Make sure information in all fields is correct");
		loginMMButton.addActionListener(new loginMMListener());
		loginMMButton.setBounds(80, 10, 125, 25);
		mmPanel.add(loginMMButton);

		registerMMButton = new JButton("Register");
		registerMMButton.setToolTipText("Make sure information in all fields is correct");
		registerMMButton.addActionListener(new registerListener());
		registerMMButton.setBounds(80, 60, 125, 25);
		mmPanel.add(registerMMButton);

		createGameButton = new JButton("Create Game");
		createGameButton.setEnabled(true);
		createGameButton.addActionListener(new createGameListener());
		createGameButton.setBounds(80, 110, 125, 25);
		mmPanel.add(createGameButton);
		createGameButton.setEnabled(false);
		
		invitesButton = new JButton("View Invites");
		invitesButton.setEnabled(true);
		invitesButton.addActionListener(new invitesListener());
		invitesButton.setBounds(80, 160, 125, 25);
		mmPanel.add(invitesButton);
		invitesButton.setEnabled(false);
	
		viewProfileButton = new JButton("View Profile");
		viewProfileButton.addActionListener(new profileSelectionListener());
		viewProfileButton.setBounds(80, 210, 125, 25);
		mmPanel.add(viewProfileButton);
		viewProfileButton.setEnabled(false);
		
		viewGamesButton = new JButton("View Games");
		viewGamesButton.addActionListener(new viewGamesListener());
		viewGamesButton.setBounds(80, 260, 125, 25);
		mmPanel.add(viewGamesButton);
		viewGamesButton.setEnabled(false);

//*****************INITIALIZE UNREGISTER FRAME COMPONENTS***********************		
		//Unregister Frame
		unRegFrame = new JFrame("Unregister");
		unRegFrame.setBounds(100, 100, 300, 250);
		unRegFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		unRegFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        mmFrame.setVisible(true);
		    }
		});

		//Unregister panel
		unRegPanel = new JPanel();
		unRegPanel.setLayout(null);
		unRegPanel.setBackground(Color.lightGray);
		unRegFrame.add(unRegPanel);

		//label for email
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(10, 10, 80, 25);
		unRegPanel.add(emailLabel);
		
		//label for password
		JLabel unregPassLabel = new JLabel("Password");
		unregPassLabel.setBounds(10, 60, 80, 25);
		unRegPanel.add(unregPassLabel);

		//Email Field
		emailField = new JTextField();
		emailField.setBounds(80, 10, 160, 25);
		emailField.addKeyListener(new EnterPressed());
		unRegPanel.add(emailField);
		
		//Password Field
		unRegPassField = new JPasswordField();
		unRegPassField.setBounds(80, 60, 160, 25);
		unRegPassField.addKeyListener(new EnterPressed());
		unRegPanel.add(unRegPassField);

		//Unregister Button on Unregister panel
		unregisterButton = new JButton("Unregister");
		unregisterButton.addActionListener(new unregisterListener());
		unregisterButton.setBounds(75, 150, 100, 25);
		unRegPanel.add(unregisterButton);

	
//*****************INITIALIZE LOGIN FRAME COMPONENTS***********************
		//login frame
		loginFrame = new JFrame("Login");
		loginFrame.setBounds(100, 100, 400, 400);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loginFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        mmFrame.setVisible(true);
		        loginMMButton.setText("Login");
		        registerMMButton.setText("Register");
		    }
		});

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
		userField.setBounds(100, 70, 160, 25);
		userField.addKeyListener(new EnterPressed());
		loginPanel.add(userField);

		//label for password
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(10, 100, 80, 25);
		loginPanel.add(passLabel);

		//Pasword Field
		passField = new JPasswordField();
		passField.setBounds(100, 100, 160, 25);
		passField.addKeyListener(new EnterPressed());
		loginPanel.add(passField);

		//Login Button on login panel
		loginButton = new JButton("Login");
		loginButton.setToolTipText("Make sure information in all fields is correct");
		loginButton.addActionListener(new loginListener());
		loginButton.setBounds(75, 150, 100, 25);
		loginPanel.add(loginButton);

//*****************INITIALIZE GAME FRAME COMPONENTS***********************
		gameFrame = new JFrame();
		gameFrame.setTitle("Client");
		gameFrame.setBounds(200, 200, 770, 550);
		gameFrame.setSize(1000,500);

		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        mmFrame.setVisible(true);
		    }
		});
		gameFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		gameFrame.setResizable(false);
		mainPane = new JPanel(new GridLayout(1,1));
		mainPane.setBackground(SystemColor.inactiveCaptionBorder);
		mainPane.setBackground(Color.lightGray);
		
		
		//rollerboard panel
		this.rollBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.boardPanel.setBounds(140, 450, 530, 45);

		left = new JLayeredPane();
		mainPane.setBackground(SystemColor.inactiveCaptionBorder);
		mainPane.setBackground(Color.lightGray);
		gameFrame.getContentPane().add(mainPane);

		quitButton = new JButton("Quit Game");
		quitButton.setEnabled(false);
		quitButton.addActionListener(new quitListener());
		quitButton.setBounds(365, 5, 115, 25);
		left.add(quitButton);
		quitButton.setVisible(true);

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

		right = boardPanel;
		mainPane.add(left);
		mainPane.add(right);
		gameFrame.getContentPane().add(mainPane);
		this.gameFrame.setVisible(false);
		this.mmFrame.setVisible(true);
		this.loginFrame.setVisible(false);
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
							SwingUtilities.invokeLater(new Runnable()
							{
								@Override
								public void run()
								{
									//client.handleMessageFromClientUI(rollBoard);
									boardPanel.drawBoard(rollBoard);
								}
							});
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
									
									
										System.out.println("YOU DUN MOVED");
										//Send Move to server
										client.handleMessageFromClientUI(tilePieceIsOn.getTileCoord() + "," + destinationTile.getTileCoord());
										System.out.println("MAKE BOARD");
										rollBoard = trans.getBoard();
										//race condition?!?
										System.out.println("JUST WANT TO SAVE");
										//now save the board
										String boardString = rollBoard.breakDownBoard(rollBoard);
										System.out.println("SERIALIZED BOARD" + boardString);
										client.handleMessageFromClientUI("#save,"+ boardString);
										//check white pieces King to see if he landed on opposing Kings starting tile
										//if so, GAME OVER
									
									for(Piece p : trans.getBoard().getWhitePieces())
									{
										if(p.getPieceType().equals(PieceType.King))
										{
											if(p.getPiecePosition() == 10)
											{
												JOptionPane.showMessageDialog(null, "GAME OVER. WHITE TEAM WINS");
												System.out.println("GAME OVER. WHITE TEAM WINS");
												
												//Update the record to database and increment the win count
												String update = "UPDATE `Rollerball`.`record` SET `winner`='WhitePlayer' WHERE `recordID`='17'";
											}
										}
									}
									for(Piece p : trans.getBoard().getBlackPieces())
									{
										if(p.getPieceType().equals(PieceType.King))
										{
											if(p.getPiecePosition() == 38)
											{
												JOptionPane.showMessageDialog(null, "GAME OVER. BLACK TEAM WINS");
												System.out.println("GAME OVER. BLACK TEAM WINS");
												//Update the record to database and increment the win count
												String update = "UPDATE `Rollerball`.`record` SET `winner`='BlackPlayer' WHERE `recordID`='17'";
											}
										}
									}
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

		private void highlightMoves(Board board)
		{
			//System.out.println("FFFFFFFFFFFFFFFFFFFFFF");

			for (Move m : currentMoves(board))
			{
				if(m.getDestCoordinate() == this.tileID)
				{
					try{
						add(new JLabel(new ImageIcon(ImageIO.read(new File("pictures/highlightMove.png")))));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}

		private Collection<Move> currentMoves(Board board) 
		{
			if(movedByPlayer != null  && movedByPlayer.getPieceAssociation() == board.currentPlayer().getAlliance())
			{
				return movedByPlayer.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}

		//TODO will need to update this once we make highlighting legal moves functionality
		public void drawTile(Board rollBoard) 
		{
			setColorOfTile();
			setTilesIcon(rollBoard);
			validate();
			repaint();
			highlightMoves(rollBoard);
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
	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO DISPLAYING INFORMATION SENT FROM SERVER
	//************************************************************************
	//************************************************************************
	
	//Informs user of their color
	public void displayColor(String message) 
	{
		colorLabel = new JLabel(message);
		colorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		colorLabel.setBounds(130, 7, 120, 21);
		left.add(colorLabel);
		colorLabel.setVisible(true);
	}

	//NOT BEING USED RIGHT NOW
	//WILL DISPLAY INVITE IF SENT ONE WHILE LOGGED IN
	public void displayInvite(String userID) 
	{
		currentOpponent = userID;
		System.out.println(currentOpponent);


		//login frame
		receiveFrame = new JFrame("Login");
		receiveFrame.setBounds(100, 100, 300, 250);
		receiveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//login panel
		receivePanel = new JPanel();
		receivePanel.setLayout(null);
		receivePanel.setBackground(Color.lightGray);
		receiveFrame.add(receivePanel);
	}

	//Takes coordinates sent by server and rebuilds it into new board
	public void displayBoard(int curCoord, int endCoord)
	{
		System.out.println("IN DISPALY COORD");
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
				boardPanel.drawBoard(rollBoard);
			}
		});
	}

	//handles all server messages
	@Override
	public void display(Object message) 
	{
		if(message instanceof String)
		{
			//
			if(message.toString().contains("login"))
			{
				List<String> items = Arrays.asList(((String) message).split(","));
				String color =items.get(1);
				displayColor(color);
			}
			else if(message.toString().contains("invite"))
			{
				List<String> items = Arrays.asList(((String) message).split(","));
				String user =items.get(1);

				displayInvite(user);
			}
			else if(message.toString().contains("decline"))
			{
				JOptionPane.showMessageDialog(loginFrame, "Your invite has been declined!");
			}
			else if(message.toString().contains("start"))
			{
				mmFrame.setVisible(false);
				quitButton.setVisible(true);
				quitButton.setEnabled(true);
				//int recordID = con.getGameRecordID(thisUserID, currentOpponent);
				//runningGame = recordID;
				try {
					//rollBoard = Board.createSavedBoard(runningGame);
					rollBoard = Board.createStandardBoard();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				boardPanel.drawBoard(rollBoard);
				gameFrame.setVisible(true);
			}
			else if(message.toString().contains("quit"))
			{
				JOptionPane.showMessageDialog(loginFrame, "Your opponent has quit!");
				//System.out.println("quit");
				//currentOpponent = null;
				//gameFrame.setVisible(false);
				//mmFrame.setVisible(true);
			}
			else
			{
				//rollerboard= board.desirialize(serverBoard);
				
				/*SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						boardPanel.drawBoard(rollBoard);
					}
				});*/
				
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

	

	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO MAIN MENU LISTENERS
	//************************************************************************
	//************************************************************************
	
	//Login listener for main menu login button
	private class loginMMListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{   
			String loginMMText = loginMMButton.getText();

			if (loginMMText.equals("Login"))
			{
				loginMMButton.setText("Logoff");
				mmFrame.setVisible(false);
				loginFrame.setVisible(true);
				registerMMButton.setText("Unregister");
				createGameButton.setEnabled(true);
				invitesButton.setEnabled(true);
				viewProfileButton.setEnabled(true);
				viewGamesButton.setEnabled(true);
			}

			//open login window
			else if(loginMMText.equals("Logoff"))
			{
				createGameButton.setEnabled(false);
				invitesButton.setEnabled(false);
				loginMMButton.setText("Login"); 
				loginFrame.setVisible(false);
				client.quit();
				registerMMButton.setText("Register");
				viewProfileButton.setEnabled(false);
				viewGamesButton.setEnabled(false);
			}
		}
	}
	
	private class profileSelectionListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{
			//login frame
			profileSelectionFrame = new JFrame("Invite Player");
			profileSelectionFrame.setBounds(100, 100, 300, 250);
			profileSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			profileSelectionFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			        mmFrame.setVisible(true);
			    }
			});
			
			//login panel
			profileSelectionPanel = new JPanel();
			profileSelectionPanel.setLayout(null);
			profileSelectionPanel.setBackground(Color.lightGray);
			profileSelectionFrame.add(profileSelectionPanel);
	
			//Label for user field
			JLabel userLabel = new JLabel("User");
			userLabel.setBounds(10, 70, 80, 25);
			profileSelectionPanel.add(userLabel);
			
			
			ArrayList<String> tmp = users;
			tmp.add(1, thisUserID);
			
			userProfileList = new JComboBox<>();
			userProfileList.setEnabled(false);
			userProfileList.setModel(new DefaultComboBoxModel(users.toArray()));
			userProfileList.addItemListener(new viewProfileListener());
			userProfileList.setBounds(100, 70, 160, 25);
			userProfileList.setBackground(Color.WHITE);
			profileSelectionPanel.add(userProfileList); 
			userProfileList.setEnabled(true);
			profileSelectionFrame.setVisible(true);
			mmFrame.setVisible(false);
			tmp.remove(1);
			
		}
	}

	//Register listener for register button on main Menu
	private class registerListener implements ActionListener 
	{

		public void actionPerformed(ActionEvent event) 
		{
			String registerMMText = registerMMButton.getText();

			if(registerMMText.equals("Register"))
			{
				Register register = new Register();
				register.frame.setVisible(true);
			}
			else
			{
				unRegFrame.setVisible(true);
				mmFrame.setVisible(false);
			}

		}
	}
	private class viewProfileListener implements ItemListener 
	{
		@Override
		public void itemStateChanged(ItemEvent e) 
		{	
			if (e.getStateChange() == ItemEvent.SELECTED) 
			{
			
				profileSelectionFrame.setVisible(false);
				String selectedUser = (String)userProfileList.getSelectedItem();
					//login frame
					viewProfileFrame = new JFrame("Profile");
					viewProfileFrame.setBounds(100, 100, 400, 400);
					viewProfileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					viewProfileFrame.addWindowListener(new java.awt.event.WindowAdapter() {
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					        mmFrame.setVisible(true);
					    }
					});
					//login panel
					viewProfilePanel = new JPanel();
					viewProfilePanel.setLayout(null);
					viewProfilePanel.setBackground(Color.lightGray);
					viewProfileFrame.add(viewProfilePanel);
					
					//informations to add to the profile page
					
					//add the current username
					JLabel userLabel = new JLabel("Username:");
					userLabel.setBounds(10, 10, 80, 25);
					viewProfilePanel.add(userLabel);
					
					JLabel username = new JLabel(selectedUser);
					username.setBounds(100, 10, 80, 25);
					viewProfilePanel.add(username);
					
					//add the total win-loss-draw for the user
					int winCount = 0;
					int lossCount = 0;
					int tieCount = 0;
					
					JLabel totalRecordLabel = new JLabel("W-L-T:");
					totalRecordLabel.setBounds(300, 10, 80, 25);
					viewProfilePanel.add(totalRecordLabel);
					
					//add user game history to the frame
					int x_axis = 10;
					int x_axis2 = 40;
					int x_axis3 = 100;
					
					int y_axis = 40;
					
					ArrayList<String> userGameHistoryList = new ArrayList<String>();
					userGameHistoryList = con.getUserGameHistory(selectedUser);
					
					for(int i=0; i<userGameHistoryList.size(); i++){
						//System.out.println("**********userGameHistoryList["+i+"]: "+userGameHistoryList.get(i));
						String temp = userGameHistoryList.get(i);
						String parts[] = temp.split(";");
						
						String opponent = parts[0];
						//System.out.println("**************opponent: "+opponent);
						
						String startDate = parts[1];
						//System.out.println("**************startDate: "+startDate);
	
						String endDate =parts[2];
						//System.out.println("**************endDate: "+endDate);
	
						String winner =parts[3];
						//System.out.println("**************winner: "+winner);
	
						String loser =parts[4];
						//System.out.println("**************loser: "+loser);
	
						String status = parts[5];
						//System.out.println("**************status: "+status);
						
						
						y_axis += 10;
						
						//---------add opponent
						JLabel opponentLabel = new JLabel("Opponent:");
						opponentLabel.setBounds(x_axis, y_axis, 80, 25);
						viewProfilePanel.add(opponentLabel);
						
						
						JLabel retrievedOpponent = new JLabel(opponent);
						retrievedOpponent.setBounds(x_axis3, y_axis, 80, 25);
						viewProfilePanel.add(retrievedOpponent);
					
						//-------------
						y_axis += 40; //increment the the y axis so you dont't write on top of the previous stuffs
						
						//------------add game status
						JLabel statusLabel = new JLabel("Game Status:");
						statusLabel.setBounds(x_axis2, y_axis, 80, 25);
						viewProfilePanel.add(statusLabel);
						
						JLabel retrievedStatus = new JLabel(status);
						retrievedStatus.setBounds(140, y_axis, 150, 25);
						viewProfilePanel.add(retrievedStatus);
						
						//-----------------------
						
						y_axis += 40;
						
						//---------add start date
						JLabel startDateLabel = new JLabel("Start Date:");
						startDateLabel.setBounds(x_axis2, y_axis, 80, 25);
						viewProfilePanel.add(startDateLabel);
						
						JLabel retrievedStartDate = new JLabel(startDate);
						retrievedStartDate.setBounds(140, y_axis, 150, 25);
						viewProfilePanel.add(retrievedStartDate);
						
						//-----------------------------------
						
						y_axis += 40;
						
						//-----------add end date
						JLabel endDateLabel = new JLabel("End Date:");
						endDateLabel.setBounds(x_axis2, y_axis, 80, 25);
						viewProfilePanel.add(endDateLabel);
						
						JLabel retrievedEndDate = new JLabel(endDate);
						retrievedEndDate.setBounds(140, y_axis, 150, 25);
						viewProfilePanel.add(retrievedEndDate);
						
						//-------------
						y_axis += 40;
						
						//----------add winner or loser 
						if(winner.equals(selectedUser)){
							winCount ++;
							
							JLabel winnerLabel = new JLabel("Result:");
							winnerLabel.setBounds(x_axis2, y_axis, 80, 25);
							viewProfilePanel.add(winnerLabel);
							
							JLabel retrievedWinner = new JLabel("winner!");
							retrievedWinner.setBounds(140, y_axis, 150, 25);
							viewProfilePanel.add(retrievedWinner);
	
						}
						if(loser.equals(selectedUser)){
							lossCount ++;
							
							JLabel loserLabel = new JLabel("Result:");
							loserLabel.setBounds(x_axis2, y_axis, 80, 25);
							viewProfilePanel.add(loserLabel);
							
							JLabel retrievedLoser = new JLabel("loser!");
							retrievedLoser.setBounds(140, y_axis, 150, 25);
							viewProfilePanel.add(retrievedLoser);
	
						}
						
						//-------------------
						
						y_axis += 90;
		
					}
	
					//-------------add the total win-loss-draw values for the user
					//System.out.println("**********************winCount: "+winCount);
					//System.out.println("**********************lossCount: "+lossCount);
					
					JLabel win = new JLabel(Integer.toString(winCount));
					win.setBounds(360, 10, 40, 25);
					viewProfilePanel.add(win);
					
					//----add a dash in between
					JLabel dash = new JLabel("-");
					dash.setBounds(370, 10, 40, 25);
					viewProfilePanel.add(dash);
					//-----
					
					JLabel loss = new JLabel(Integer.toString(lossCount));
					loss.setBounds(380, 10, 40, 25);
					viewProfilePanel.add(loss);
					
					//----add a dash in between
					JLabel dash2 = new JLabel("-");
					dash2.setBounds(390, 10, 40, 25);
					viewProfilePanel.add(dash2);
					//-----
					
					JLabel tie = new JLabel(Integer.toString(tieCount));
					tie.setBounds(400, 10, 40, 25);
					viewProfilePanel.add(tie);
					
					
					//takes you to the profile page
					viewProfileFrame.setVisible(true);
					mmFrame.setVisible(false);
			}
			
		}
		
	}

	private class createGameListener implements ActionListener 
	{

		public void actionPerformed(ActionEvent event) 
		{

			//login frame
			createGameFrame = new JFrame("Invite Player");
			createGameFrame.setBounds(100, 100, 300, 250);
			createGameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			createGameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			        mmFrame.setVisible(true);
			    }
			});
			
			//login panel
			createGamePanel = new JPanel();
			createGamePanel.setLayout(null);
			createGamePanel.setBackground(Color.lightGray);
			createGameFrame.add(createGamePanel);

			//Label for user field
			JLabel userLabel = new JLabel("User");
			userLabel.setBounds(10, 70, 80, 25);
			createGamePanel.add(userLabel);


			userList = new JComboBox<>();
			userList.setEnabled(false);
			userList.setModel(new DefaultComboBoxModel(users.toArray()));
			userList.addItemListener(new UserListListener());
			userList.setBounds(100, 70, 160, 25);
			userList.setBackground(Color.WHITE);
			createGamePanel.add(userList); 
			userList.setEnabled(true);
			createGameFrame.setVisible(true);
			mmFrame.setVisible(false);
			
			
		}
		
	} 
	
	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO UNREGISTER FRAME LISTENERS
	//************************************************************************
	//************************************************************************
	
	// Button Listener for unregister button on Unregister Frame
	private class unregisterListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{

			String email = emailField.getText();
			String password = unRegPassField.getText();
			
			if(con.userExistsEmail(email,password))
			{
				con.removeUser(email);
				unRegFrame.setVisible(false);
				mmFrame.setVisible(true);
				loginMMButton.doClick();
			}
		}
	}
	
	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO LOGIN FRAME LISTENERS
	//************************************************************************
	//************************************************************************
	
	//login listener for login button on login frame
	private class loginListener implements ActionListener 
	{
		//ask server for updated list of users
		public void serverRequestUsers() 
		{
			client.handleMessageFromClientUI(("#userList"));
		}
		
		public void actionPerformed(ActionEvent event) 
		{
			String userID = userField.getText();
			thisUserID = userID;
			String password = passField.getText();

			//if user exists, log them in
			if(con.userExists(userID,password))
			{
				try {
					client = new Client(userID,"localhost", 5555, ClientGUI.this);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("you're logged in");
				serverRequestUsers();
				mmFrame.setVisible(true);
				loginFrame.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(loginFrame, "This user does not exist. Register?");
			}
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

	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO VIEW INVITES FRAME LISTENERS
	//************************************************************************
	//************************************************************************
	
	//accept button listener on invite frame
	private class acceptListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{	
			
			client.handleMessageFromClientUI("#accept,"+currentOpponent);
			inviteFrame.setVisible(false);
			
			gameCreator = currentOpponent;
			gameOpponent = thisUserID;
			con.createGameRecord(gameCreator, gameOpponent);
			con.acceptInviteDB(currentOpponent, thisUserID);
			
			quitButton.setVisible(true);
			quitButton.setEnabled(true);

			try {
				//rollBoard = Board.createSavedBoard(runningGame);
				rollBoard = Board.createStandardBoard();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			//***********creating game record***************
			gameCreator = currentOpponent;
			gameOpponent = thisUserID;
			con.createGameRecord(gameCreator, gameOpponent);
			con.acceptInviteDB(currentOpponent, thisUserID);

			boardPanel.drawBoard(rollBoard);
			mmFrame.setVisible(false);
			gameFrame.setVisible(true);
		
		}
	}

	//decline button listener on invite frame
	private class declineListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{
			client.handleMessageFromClientUI("#decline,"+currentOpponent);
			currentOpponent = null;
			receiveFrame.setVisible(false);
			
			con.declineInviteDB(currentOpponent, thisUserID);

		}
	} 
	
	//handles inviting user
	private class invitesListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event) 
		{
			userInvites = con.populateInviteList(thisUserID);
			//login frame
			inviteFrame = new JFrame("Invites");
			inviteFrame.setBounds(100, 100, 300, 250);
			inviteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			inviteFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			        mmFrame.setVisible(true);
			    }
			});
			//login panel
			invitePanel = new JPanel();
			invitePanel.setLayout(null);
			invitePanel.setBackground(Color.lightGray);
			inviteFrame.add(invitePanel);
			
			userInviteList = new JList(userInvites.toArray());
			userInviteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			userInviteList.setLayoutOrientation(JList.VERTICAL);
			userInviteList.addListSelectionListener(new inviteListSelectionHandler());
			
			JScrollPane listScroller = new JScrollPane(userInviteList);
			
			
			listScroller.setVisible(true);
			listScroller.setEnabled(true);
			listScroller.setBounds(10, 10, 250, 100);
			invitePanel.add(listScroller); 
			
			
			acceptButton = new JButton("Accept");
			acceptButton.addActionListener(new acceptListener());;
			acceptButton.setBounds(60, 150, 100, 25);
			acceptButton.setEnabled(false);
			invitePanel.add(acceptButton);
			

			declineButton = new JButton("Decline");
			declineButton.addActionListener(new declineListener());
			declineButton.setBounds(165, 150, 100, 25);
			declineButton.setEnabled(false);
			invitePanel.add(declineButton);

			
			inviteFrame.setVisible(true);
			mmFrame.setVisible(false);
		}
		
		//handles selecting a user to invite on the invite frame
		class inviteListSelectionHandler implements ListSelectionListener 
		{
			@Override
		    public void valueChanged(ListSelectionEvent event)
		    {
		    	currentOpponent= userInviteList.getSelectedValue().toString();
		    	
		    	acceptButton.setEnabled(true);
		    	declineButton.setEnabled(true);
		    }
		 }
	}
	
	//handles inviting user
		private class viewGamesListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent event) 
			{
				//gameArray = con.populateActiveGameList(thisUserID);
				gameArray = con.getCurrentGames(thisUserID);
				//login frame
				viewGameFrame = new JFrame("Invites");
				viewGameFrame.setBounds(100, 100, 300, 250);
				viewGameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				viewGameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				        mmFrame.setVisible(true);
				    }
				});
				//login panel
				viewGamePanel = new JPanel();
				viewGamePanel.setLayout(null);
				viewGamePanel.setBackground(Color.lightGray);
				viewGameFrame.add(viewGamePanel);
				
				gameList = new JList(gameArray.toArray());
				gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				gameList.setLayoutOrientation(JList.VERTICAL);
				gameList.addListSelectionListener(new gameListSelectionHandler());
				
				JScrollPane listScroller = new JScrollPane(gameList);
				
				
				listScroller.setVisible(true);
				listScroller.setEnabled(true);
				listScroller.setBounds(10, 10, 250, 100);
				viewGamePanel.add(listScroller); 
				
				
				joinButton = new JButton("Join");
				joinButton.addActionListener(new joinListener());;
				joinButton.setBounds(60, 150, 100, 25);
				joinButton.setEnabled(false);
				viewGamePanel.add(joinButton);
				

				forfeitButton = new JButton("Forfeit");
				forfeitButton.addActionListener(new forfeitListener());
				forfeitButton.setBounds(165, 150, 100, 25);
				forfeitButton.setEnabled(false);
				viewGamePanel.add(forfeitButton);

				
				viewGameFrame.setVisible(true);
				mmFrame.setVisible(false);
			}
			
			//handles selecting a user to invite on the invite frame
			class gameListSelectionHandler implements ListSelectionListener 
			{
				@Override
			    public void valueChanged(ListSelectionEvent event)
			    {
			    	gameId= gameList.getSelectedValue().toString();
			    	System.out.println("CURRENT OPPONENT SELECTED: " + gameId);
			    	joinButton.setEnabled(true);
			    	forfeitButton.setEnabled(true);
			    }
			 }
		}
		
		//decline button listener on invite frame
		private class joinListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent event) 
			{
				currentOpponent = gameList.getSelectedValue().toString();
				
				System.out.println("JOIN LISTENER OPPOENET: " + gameId);
				System.out.println("thisUserID: " + thisUserID);
				client.handleMessageFromClientUI("#join,"+ gameId+","+currentOpponent);
				viewGameFrame.setVisible(false);
				quitButton.setVisible(true);
				quitButton.setEnabled(true);
				//get board
				String serialBoard = con.getSelectedGame(gameId);
				String turn = con.getSelectedGamesTurn(gameId);
				System.out.println("IM IN JOINLISTENER");
				try {//if serialboard = 0 just createnewboard
					rollBoard = Board.rebuildBoard(serialBoard,turn);
					
					//rollBoard = Board.createStandardBoard();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				System.out.println("AFTER ROLLBAORDM IN JOINRELISTENER");
				//gameCreator = currentOpponent;
				//gameOpponent = thisUserID;
				
				
				boardPanel.drawBoard(rollBoard);
				mmFrame.setVisible(false);
				gameFrame.setVisible(true);

			}
		} 
		
		//forfeit button listener on invite frame
		private class forfeitListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent event) 
			{
				gameCreator = currentOpponent;
				gameOpponent = thisUserID;
				con.finishGameRecord(gameCreator, gameOpponent);
			}
		} 

	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO GAME FRAME LISTENERS
	//************************************************************************
	//************************************************************************
	
	//Quit Button Listen on Game Frame
	private class quitListener implements ActionListener 
	{

		public void actionPerformed(ActionEvent event) 
		{
			System.out.println("QUITTER OF GAMEID: " + gameId);
			con.setSaveStatusOff(gameId);
			System.out.println("YOU QUITTTTTT");
			client.handleMessageFromClientUI("#quit,"+currentOpponent);
			currentOpponent = null;
			gameFrame.setVisible(false);
			mmFrame.setVisible(true);
		}
	} 
	
	//updates the status area with online users and their status
	public void updateUsers(ArrayList<String> users) 
	{
		statusArea.setText("");

		this.users = users;

		for (int i = 0; i < users.size(); i++) 
		{
			statusArea.append( users.get(i)+"\n");
		}
	}
	
	
	//************************************************************************
	//************************************************************************
	//BELOW SECTION IS RELATED TO CREATE GAME/INVITEUSER LISTENERS
	//************************************************************************
	//************************************************************************
	
	//handles inviting users
	private class UserListListener implements ItemListener 
	{
		public void itemStateChanged(ItemEvent event) 
		{
			if (event.getStateChange() == ItemEvent.SELECTED) 
			{
				String user = (String)userList.getSelectedItem();

				//System.out.println("#invite," + user);
				client.handleMessageFromClientUI("#invite," + user);
				createGameFrame.setVisible(false);
				mmFrame.setVisible(true);
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{

		//Board board = Board.createSavedBoard();

		ClientGUI table = new ClientGUI();
	}

}

