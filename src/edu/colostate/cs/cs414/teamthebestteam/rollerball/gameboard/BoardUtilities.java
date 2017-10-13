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
	public static final boolean[] OUTER_RING = null;
	//All the tile coordinates for the inner ring of the board
	public static final boolean[] INNER_RING = null;
	//All the tile coordinates where forward = to the right
	public static final boolean[] QUADRANT_ONE = null;
	//All the tile coordinates where moving forward is down
	public static final boolean[] QUADRANT_TWO = null;
	//All the tile coordinates where moving forward is to the left
	public static final boolean[] QUADRANT_THREE = null;
	//All the tile coordinates where moving forward is up
	public static final boolean[] QUADRANT_FOUR = null;

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
