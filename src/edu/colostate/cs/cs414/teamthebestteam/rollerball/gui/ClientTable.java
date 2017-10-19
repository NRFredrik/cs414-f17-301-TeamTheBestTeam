package edu.colostate.cs.cs414.teamthebestteam.rollerball.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.RollIF;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Rollerball;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move.FactoryMove;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//testasdsadas

public class ClientTable implements RollIF {

	private JFrame gameFrame;
	private Board rollBoard;

	private static Dimension OUTER_DIMENSION = new Dimension(600,600);
	//dimensions of board panel
	private static final Dimension BOARD_DIMENSIONS = new Dimension(450,350);
	//tiles size
	private static final Dimension TILE_DIMENSION = new Dimension(10,10);

	//represents board panel
	public BoardPanel boardPanel;
	private Tile tilePieceIsOn;
	private Tile destinationTile;
	private Piece movedByPlayer;
	public Rollerball roll;
	public int test;
	
	
	public ClientTable()throws Exception
	{
		roll = new Rollerball("localhost", 5555, this);
		
		//create the new 600 by 600 frame with the name Rollerball
		this.gameFrame = new JFrame("Rollerball");
		this.gameFrame.setLayout(new BorderLayout());
		this.gameFrame.setSize(OUTER_DIMENSION);
		JMenuBar menuBar = new JMenuBar();
		fillMenu(menuBar);

		//associate menu bar with the gameframe
		this.gameFrame.setJMenuBar(menuBar);

		this.rollBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();

		//add the boardPanle to the game frame and use center layout
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

		this.gameFrame.setVisible(true);
		
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
							roll.handleMessageFromClientUI(tilePieceIsOn.getTileCoord() + "," + destinationTile.getTileCoord());
							/*System.out.println("Destination tile: "+ destinationTile.getTileCoord()+ " was selected\n");
							
							//factory method will check for the desired move in the list of legal moves and return the move if its in there, or null
							Move move = Move.FactoryMove.createMove(rollBoard, tilePieceIsOn.getTileCoord(), destinationTile.getTileCoord());
		
							MoveTransition trans;
							
							try {
								trans = rollBoard.currentPlayer().movePlayer(move);
							
							if(trans.getStatus().isDone())
							{
								roll.handleMessageFromClientUI(tilePieceIsOn.getTileCoord() + "," + destinationTile.getTileCoord());
								rollBoard = trans.getBoard();
								//TODO add the move to a log for debugging
							}
							
							else
							{
								System.out.println("THAT MOVE IS NOT VALID FOR THE CHOSEN PIECE. REFER TO RULE BOOK AND TRY AGAIN\n");
							}
							tilePieceIsOn = null;
							destinationTile = null;
							movedByPlayer = null;
							
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}//end else

						//need the GUI to update
						SwingUtilities.invokeLater(new Runnable()
						{
							@Override
							public void run()
							{
								//roll.handleMessageFromClientUI(rollBoard);
								boardPanel.drawBoard(rollBoard);
							}
						});
						
					*/	
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
	
		//this is board from a starting position
		Board board = Board.createStandardBoard();
		//print board. Takes advantage of tosting() for each of the pieces 
		//to print this string representation of the board
		
		//System.out.println(board);
		
		//Define the table to be a 600X600 table and is visible right now
		ClientTable table = new ClientTable();
	}

	@Override
	public void display(Object message) 
	{
		List<String> items = Arrays.asList(((String) message).split(","));
		int curCoord =Integer.parseInt(items.get(0));
		int endCoord =Integer.parseInt(items.get(1));
		
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
}







