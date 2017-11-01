package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public class BoardUtilities {

	// Constants for Boundary Exclusionary Cases with Movements
	// All the tile coordinates for the outer ring of the board
	public static final Set<Integer> OUTER_RING = initOuterRing();
	// All the tile coordinates for the inner ring of the board
	public static final Set<Integer> INNER_RING = initInnerRing();
	// All the tile coordinates where forward = to the right
	public static final Set<Integer> QUADRANT_ONE = initQuadrantOne();
	// All the tile coordinates where moving forward is down
	public static final Set<Integer> QUADRANT_TWO = initQuadrantTwo();
	// All the tile coordinates where moving forward is to the left
	public static final Set<Integer> QUADRANT_THREE = initQuadrantThree();
	// All the tile coordinates where moving forward is up
	public static final Set<Integer> QUADRANT_FOUR = initQuadrantFour();

	public static final int NUM_TILES = 49; // these will be constant
	public static final int NUM_TILES_PER_ROW = 7; // also constant

	// declare starting points for each white piece
	final int[] WHITE_PAWN_STARTING_POINTS = { 4, 11 };
	final int[] WHITE_ROOK_STARTING_POINTS = { 2, 9 };
	final int[] WHITE_BISHOP_STARTING_POINTS = { 3 };
	final int[] WHITE_KING_STARTING_POINTS = { 10 };

	// declare starting points for each white piece
	final int[] BLACK_PAWN_STARTING_POINTS = { 37, 44 };
	final int[] BLACK_ROOK_STARTING_POINTS = { 39, 46 };
	final int[] BLACK_BISHOP_STARTING_POINTS = { 45 };
	final int[] BLACK_KING_STARTING_POINTS = { 38 };

	// populate columns with values from the board
	// Use this to help determine which moves are legal and for direction
	// I think we really only need the first and last column
	public static final Set<Integer> COLUMN_ZERO = populateColumn(0);
	public static final Set<Integer> COLUMN_ONE = populateColumn(1);
	public static final Set<Integer> COLUMN_TWO = populateColumn(2);
	public static final Set<Integer> COLUMN_THREE = populateColumn(3);
	public static final Set<Integer> COLUMN_FOUR = populateColumn(4);
	public static final Set<Integer> COLUMN_FIVE = populateColumn(5);
	public static final Set<Integer> COLUMN_SIX = populateColumn(6);

	/**
	 * These boolean arrays are used to tell if a piece is in a specific column
	 * This is important because different pieces have certain corner cases
	 * where some of their "legal moves won't work" This can also be used to
	 * determine if a rebound can occur
	 */
	// initialize all entries in first column to true
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	// public static final boolean[] SECOND_COLUMN = initColumn(1);
	// public static final boolean[] THIRD_COLUMN = initColumn(2);
	// public static final boolean[] FOURTH_COLUMN = initColumn(3);
	// public static final boolean[] FIFTH_COLUMN = initColumn(4);
	// public static final boolean[] SIXTH_COLUMN = initColumn(5);
	// initialize all entries in last column to true
	public static final boolean[] LAST_COLUMN = initColumn(6);

	/**
	 * we can use these boolean arrays to know when we are at a corner square
	 * corner squares are where certain pieces can "rebound"
	 */
	// initialize tile id's that begin the row
	public static final boolean[] FIRST_ROW = initRow(0);
	public static final boolean[] SECOND_ROW = initRow(7);
	public static final boolean[] THIRD_ROW = initRow(14);
	public static final boolean[] FOURTH_ROW = initRow(21);
	public static final boolean[] FIFTH_ROW = initRow(28);
	public static final boolean[] SIXTH_ROW = initRow(35);
	public static final boolean[] LAST_ROW = initRow(42);
	
	private static Set<Integer> initOuterRing() {
		Integer[] o_ring = { 0,1,2,3,4,5,6,13,20,27,34,41,48,47,46,45,44,43,42,35,28,21,14,7};
		Set<Integer> out_ring = new HashSet<>(Arrays.asList(o_ring));
		return out_ring;
	}
	
	private static Set<Integer> initInnerRing() {
		Integer[] i_ring = { 8,9,10,11,12,19,26,33,40,39,38,37,36,29,22,15};
		Set<Integer> in_ring = new HashSet<>(Arrays.asList(i_ring));
		return in_ring;
	}
	
	private static boolean[] initColumn(int columnNumber) {
		// initialize spot in column to true then increment counter by 7 to make
		// whole column true
		final boolean[] column = new boolean[NUM_TILES];
		do {
			column[columnNumber] = true;
			columnNumber += 7;
		} while (columnNumber < NUM_TILES);
		return column;
	}

	private static boolean[] initRow(int i) {
		final boolean[] row = new boolean[NUM_TILES];

		do {
			row[i] = true;
			i++;
		} while (i % NUM_TILES_PER_ROW != 0);

		return row;
	}

	/**
	 * @author kb
	 * @return quadrant with values { 0-4, 7-11 }
	 */
	private static Set<Integer> initQuadrantOne() {
		Set<Integer> quadrant = new HashSet<>();
		for (int i = 0; i < 12; i++) {
			if (i != 5 || i != 6) {
				quadrant.add(i);
			}
		}
		return quadrant;
	}

	/**
	 * @author kb
	 * @return quadrant with values { 5,6,12,13,19,20,26,27,33,34 }
	 */
	private static Set<Integer> initQuadrantTwo() {
		Integer[] quad_two = { 5, 6, 12, 13, 19, 20, 26, 27, 33, 34 };
		Set<Integer> quadrant = new HashSet<>(Arrays.asList(quad_two));
		return quadrant;
	}

	/**
	 * @author kb
	 * @return quadrant with values { 37-41, 44-48 }
	 */
	private static Set<Integer> initQuadrantThree() {
		Set<Integer> quadrant = new HashSet<>();
		for (int i = 37; i < 49; i++) {
			if (i != 42 || i != 43) {
				quadrant.add(i);
			}
		}
		return quadrant;
	}

	/**
	 * @author kb
	 * @return quadrant with values { 42,43,35,36,28,29,21,22,14,15 }
	 */
	private static Set<Integer> initQuadrantFour() {
		Integer[] quad_two = { 42, 43, 35, 36, 28, 29, 21, 22, 14, 15 };
		Set<Integer> quadrant = new HashSet<>(Arrays.asList(quad_two));
		return quadrant;
	}

	/**
	 * @author kb
	 * @param columnNumber
	 * @return a set of values. Used to populate each column with values from
	 *         the board We can use this to help determine direction and which
	 *         moves are legal
	 */
	private static Set<Integer> populateColumn(int columnNumber) {
		Set<Integer> column = new HashSet<>();
		int num = columnNumber + 7;
		for (int i = columnNumber; i < num; i++) {
			column.add(i);
		}
		return column;
	}

	/**
	 * 
	 * @param coordinate
	 * @return quadrant number the piece is in
	 */
	public static int getQuadrantCoordinates(int coordinate) {

		if (QUADRANT_ONE.contains(coordinate)) {
			return 1;
		}

		else if (QUADRANT_TWO.contains(coordinate)) {
			return 2;
		}

		else if (QUADRANT_THREE.contains(coordinate)) {
			return 3;
		}

		else if (QUADRANT_FOUR.contains(coordinate)) {
			return 4;
		}
		return -1;
	}

	private BoardUtilities() {
		throw new RuntimeException("this is private. you cannot instantiate this object.");

	}

	public static boolean isValidTileCoordinate(int tileCoordinate) {
		// will look into making this cleaner
		if (tileCoordinate >= 0 && tileCoordinate <= 48 && isNotUsableTile(tileCoordinate) == false) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author kb
	 * @param coordinate
	 * @return true if the coordinate received is one of the non visible
	 *         coordinates (in the 3x3)
	 */
	public static boolean isNotUsableTile(int coordinate) {
		// there are 49 possible tiles since board is 7x7, but there is a 3x3
		// missing from middle
		Integer[] quad_two = { 16, 17, 18, 23, 24, 25, 30, 31, 32 };
		Set<Integer> restrictedTiles = new HashSet<>(Arrays.asList(quad_two));
		return restrictedTiles.contains(coordinate);
	}

	/**
	 * @author kb
	 * @param coordinate
	 * @return true if tile is a corner tile where Rook is allowed to rebound
	 */
	public boolean isCornerReboundRing(int coordinate) {
		if (coordinate == 0 || coordinate == 6 || coordinate == 48 || coordinate == 42) {
			return true;
		}
		return false;
	}

	/**
	 * @author kb
	 * @param piece
	 * @return This method can be used to get the starting position of each
	 *         piece useful when promoting certain pieces, and getting to other
	 *         kings starting position
	 */
	public int[] getStartingPosition(Piece piece) {
		// determine if white player
		if (piece.getPieceAssociation().equals(Alliance.WHITE)) {
			if (piece.toString().equals("pawn")) {
				return WHITE_PAWN_STARTING_POINTS;
			} else if (piece.toString().equals("rook")) {
				return WHITE_ROOK_STARTING_POINTS;
			} else if (piece.toString().equals("bishop")) {
				return WHITE_BISHOP_STARTING_POINTS;
			} else {
				return WHITE_KING_STARTING_POINTS;
			}
		} else {
			if (piece.toString().equals("pawn")) {
				return BLACK_PAWN_STARTING_POINTS;
			} else if (piece.toString().equals("rook")) {
				return BLACK_ROOK_STARTING_POINTS;
			} else if (piece.toString().equals("bishop")) {
				return BLACK_BISHOP_STARTING_POINTS;
			} else {
				return BLACK_KING_STARTING_POINTS;
			}
		}
	}

	public static int getColumn(int piecePosition) {
		if (COLUMN_ZERO.contains(piecePosition)) {
			return 0;
		}

		if (COLUMN_ONE.contains(piecePosition)) {
			return 1;
		}

		else if (COLUMN_TWO.contains(piecePosition)) {
			return 2;
		}

		else if (COLUMN_THREE.contains(piecePosition)) {
			return 3;
		}

		else if (COLUMN_FOUR.contains(piecePosition)) {
			return 4;
		}

		else if (COLUMN_FIVE.contains(piecePosition)) {
			return 5;
		}

		else if (COLUMN_SIX.contains(piecePosition)) {
			return 6;
		} else
			return -1;
	}
}
