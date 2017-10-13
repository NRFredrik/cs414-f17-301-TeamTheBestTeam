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
import java.util.List;
import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class Table {

	private JFrame gameFrame;
	private final Board rollBoard;

	private static Dimension OUTER_DIMENSION = new Dimension(600,600);
	//dimensions of board panel
	private static final Dimension BOARD_DIMENSIONS = new Dimension(450,350);
	//tiles size
	private static final Dimension TILE_DIMENSION = new Dimension(10,10);

	//represents board panel
	private BoardPanel boardPanel;
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;

	public Table()
	{
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
	private class BoardPanel extends JPanel
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
	}

	//visual component representing the tiles on chess board
	//maps to a tile in game
	private class TilePanel extends JPanel
	{
		private int tileID;


		public TilePanel(BoardPanel boardPanel, int tileID)
		{
			super(new GridBagLayout());
			this.tileID = tileID;
			setPreferredSize(TILE_DIMENSION);
			//used to distinguish dark from light tiles
			assignTileColor();

			assignTilePieceIcon(rollBoard);
			
			//listen for mouse click events
			addMouseListener(new MouseListener(){

				//listen for clicks on all tiles
				//if click occurs get tileid and assign to source tile
				@Override
				public void mouseClicked(MouseEvent e) {
					
					//when user right clicks a tile it unselects chosen piece
				/*	if(isRightMouseButton(event))
					{
						//if tile is clicked and not empty set it 
						if(sourceTile == null)
						{
							sourceTile = rollBoard.getTile(tileID);
							humanMovedPiece = sourceTile.getPiece();
							
							if(humanMovedPiece == null)
							{
								sourceTile = null;
							}
						}
					}
					//something was picked and is being moved (2nd click)
					else if(isLeftMouseButton(event))
					{
						destinationTile = rollBoard.getTile(tileID);
						
						//the move based on source and destination tiles that user selected
						final Moves move = null;
						
						//MoveTransition
					}*/
					
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

		private void assignTilePieceIcon(final Board board)
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
		private void assignTileColor() 
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
}







