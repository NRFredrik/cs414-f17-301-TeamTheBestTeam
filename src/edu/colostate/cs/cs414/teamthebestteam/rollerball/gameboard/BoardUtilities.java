package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;



public class BoardUtilities {

	//Constants for Boundary Exclusionary Cases with Movements
	//All the tile coordinates for the outer ring of the board
	public static final boolean[] OUTER_RING = new boolean[48];
	//All the tile coordinates for the inner ring of the board
	public static final boolean[] INNER_RING = new boolean[48];
	//All the tile coordinates where forward = to the right
	public static final boolean[] QUADRANT_ONE = new boolean[48];
	//All the tile coordinates where moving forward is down
	public static final boolean[] QUADRANT_TWO = new boolean[48];
	//All the tile coordinates where moving forward is to the left
	public static final boolean[] QUADRANT_THREE = new boolean[48];
	//All the tile coordinates where moving forward is up
	public static final boolean[] QUADRANT_FOUR = new boolean[48];

	public static final int NUM_TILES = 49; //these will be constant
	public static final int NUM_TILES_PER_ROW = 7; //also constant

	//declare starting points for each white piece
	final int[] WHITE_PAWN_STARTING_POINTS ={4,11};
	final int[] WHITE_ROOK_STARTING_POINTS ={2,9};
	final int[] WHITE_BISHOP_STARTING_POINTS ={3};
	final int[] WHITE_KING_STARTING_POINTS ={10};

	//declare starting points for each white piece
	final int[] BLACK_PAWN_STARTING_POINTS ={37,44};
	final int[] BLACK_ROOK_STARTING_POINTS ={39,46};
	final int[] BLACK_BISHOP_STARTING_POINTS ={45};
	final int[] BLACK_KING_STARTING_POINTS ={38};
	
	/**
	 * These boolean arrays are used to tell if a piece is in a specific column
	 * This is important because different pieces have certain corner cases where some of their "legal moves won't work"
	 * This can also be used to determine if a rebound can occur
	 */
	//initialize all entries in first column to true 
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	//public static final boolean[] SECOND_COLUMN = initColumn(1);
	//public static final boolean[] THIRD_COLUMN = initColumn(2);
	//public static final boolean[] FOURTH_COLUMN = initColumn(3);
	//public static final boolean[] FIFTH_COLUMN = initColumn(4);
	//public static final boolean[] SIXTH_COLUMN = initColumn(5);
	//initialize all entries in last column to true
	public static final boolean[] LAST_COLUMN = initColumn(6);


	/**
	 * we can use these boolean arrays to know when we are at a corner square 
	 * corner squares are where certain pieces can "rebound"
	 */
	//initialize tile id's that begin the row
	public static final boolean[] FIRST_ROW = initRow(0);
	public static final boolean[] SECOND_ROW = initRow(7);
	public static final boolean[] THIRD_ROW = initRow(14);
	public static final boolean[] FOURTH_ROW = initRow(21);
	public static final boolean[] FIFTH_ROW = initRow(28);
	public static final boolean[] SIXTH_ROW = initRow(35);
	public static final boolean[] LAST_ROW = initRow(42);
	
	
	private static boolean[] initColumn(int columnNumber)
	{
		//initialize spot in column to true then increment counter by 7 to make whole column true
		final boolean[] column = new boolean[NUM_TILES];
		do{
			column[columnNumber] = true;
			columnNumber += 7;
		}while(columnNumber < NUM_TILES);
		return column;
	}

	private static boolean[] initRow(int i) 
	{
		final boolean[] row = new boolean[NUM_TILES];

		do{
			row[i] = true;
			i++;
		}while(i % NUM_TILES_PER_ROW != 0);

		return row;
	}


	private BoardUtilities(){
		throw new RuntimeException("this is private. you cannot instantiate this object.");

	}

	public static boolean isValidTileCoordinate(int tileCoordinate){
		//will look into making this cleaner
		if(tileCoordinate >= 0 && tileCoordinate <=48 && isNotUsableTile(tileCoordinate)){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param coordinate
	 * @return true if the coordinate received is one of the non visible coordinates (in the 3x3)
	 */
	public static boolean isNotUsableTile(int coordinate)
	{
		//there are 49 possible tiles since board is 7x7, but there is a 3x3 missing from middle
		//  TODO figure what to do about the 3x3 in middle (17,18,19), (24,25,26), (31,32,33)
		Set<Integer> restrictedTiles = new HashSet<>();
		restrictedTiles.add(16);
		restrictedTiles.add(17);
		restrictedTiles.add(18);
		restrictedTiles.add(23);
		restrictedTiles.add(24);
		restrictedTiles.add(25);
		restrictedTiles.add(30);
		restrictedTiles.add(31);
		restrictedTiles.add(32);

		return restrictedTiles.contains(coordinate);
	}
	
	/**
	 * @author kb
	 * @param coordinate
	 * @return true if tile is a corner tile where Rook is allowed to rebound
	 */
	public boolean isCornerReboundRing(int coordinate)
	{
		if(coordinate == 0 || coordinate == 6 || coordinate == 48 || coordinate == 42)
		{
			return true;
		}
		return false;
	}

	/**
	 * @author kb
	 * @param piece
	 * @return
	 * This method can be used to get the starting position of each piece
	 * useful when promoting certain pieces, and getting to other kings starting position
	 */
	public int[] getStartingPosition(Piece piece)
	{
		//determine if white player
		if(piece.getPieceAssociation().equals(Alliance.WHITE))
		{
			if(piece.toString().equals("pawn"))
			{
				return WHITE_PAWN_STARTING_POINTS;
			}
			else if(piece.toString().equals("rook"))
			{
				return WHITE_ROOK_STARTING_POINTS;
			}
			else if(piece.toString().equals("bishop"))
			{
				return WHITE_BISHOP_STARTING_POINTS;
			}
			else 
			{
				return WHITE_KING_STARTING_POINTS;
			}
		}
		else
		{
			if(piece.toString().equals("pawn"))
			{
				return BLACK_PAWN_STARTING_POINTS;
			}
			else if(piece.toString().equals("rook"))
			{
				return BLACK_ROOK_STARTING_POINTS;
			}
			else if(piece.toString().equals("bishop"))
			{
				return BLACK_BISHOP_STARTING_POINTS;
			}
			else 
			{
				return BLACK_KING_STARTING_POINTS;
			}
		}
	}
}
