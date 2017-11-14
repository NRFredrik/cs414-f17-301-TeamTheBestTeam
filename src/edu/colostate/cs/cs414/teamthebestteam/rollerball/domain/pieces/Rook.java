package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Tile;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Piece.PieceType;


public class Rook extends Piece{

	/**
	 * The rook can move any number of steps orthogonally in a straight line in a forward direction, 
	 * or any number of steps orthogonally sideways. 
	 * It can also move one step orthogonally backward on its ring. 
	 * When moving along the outer ring, the rook may "rebound" off corner square & continue its forward journey at 90 degrees. 
	 * moving +-1 will satisfy moving straight
	 * moving +-7 will satisfy moving sideways
	 * This all depends on which direction is considered forward for each player
	 * Note if you're in first or last column +-1 won't work (corner cases)
	 */
	private static int[] CANDIDATE_MOVE_COORDINATES = {-7, -1, 1, 7};
	//public static Set<Integer> CANDIDATE_MOVE_COORDINATES = null;

	public Rook(final int piecePosition, final Alliance pieceAlliance) 
	{
		super(PieceType.Rook, piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) 
	{
		//List will hold all valid moves and will be returned
		final List<Move> legalMove = new ArrayList<>();

		//Keep track of when rebound was used
		boolean didRebound = false;
		/**
		 * depending on which quadrant, rook can go "backwards" handle that separately
		 * Need to Get the quadrant that the piece is in and column
		 */
		int quadrant = BoardUtilities.getQuadrantCoordinates(this.piecePosition);
		int column = BoardUtilities.getColumn(this.piecePosition);
		boolean backwards = false; //keep track if backwards was added to legal moves yet or not 

		/**
		 * loop through valid moves and while adding multiples of the 
		 * CANDIDATE_MOVE_COORDINATES is within bounds add to legal moves
		 */
		for(int candidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
			int candidateDestinationCoordinate = this.piecePosition;
			
			//check for backwards move
			if (this.Q1.contains(this.piecePosition) && candidateOffset == -1 && !backwards) {
				backwards = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
						/*
						 * if(backwards = true && (!(quadrant == 3 &&
						 * candidateOffset == 1) || !(quadrant == 1 &&
						 * candidateOffset == -1) || !(quadrant == 2 &&
						 * candidateOffset == -7) || !(quadrant == 4 &&
						 * candidateOffset == 7))) { legalMove.add(new
						 * Move.BasicMove(board, this,
						 * candidateDestinationCoordinate)); }
						 */

						legalMove.add(new Move.BasicMove(board, this, candidateDestinationCoordinate));
					}
					// there is some piece there. Find out what it is and do
					// stuff
					else {

						// get the piece at this location
						final Piece pieceAtDestination = candidateTile.getPiece();

						// get the association of the piece
						final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

						// if THIS piece that we are examining is not = to piece
						// association that is at our destination tile
						// we know this is an enemy piece. So do a capture move
						if (this.pieceAlliance != pieceAlliance) {
							// need board, piece, destination tile, and piece
							// that is being captured
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			//check quad 2
			if (this.Q2.contains(this.piecePosition) && candidateOffset == -7 && !backwards) {
				backwards = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
						/*
						 * if(backwards = true && (!(quadrant == 3 &&
						 * candidateOffset == 1) || !(quadrant == 1 &&
						 * candidateOffset == -1) || !(quadrant == 2 &&
						 * candidateOffset == -7) || !(quadrant == 4 &&
						 * candidateOffset == 7))) { legalMove.add(new
						 * Move.BasicMove(board, this,
						 * candidateDestinationCoordinate)); }
						 */

						legalMove.add(new Move.BasicMove(board, this, candidateDestinationCoordinate));
					}
					// there is some piece there. Find out what it is and do
					// stuff
					else {

						// get the piece at this location
						final Piece pieceAtDestination = candidateTile.getPiece();

						// get the association of the piece
						final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

						// if THIS piece that we are examining is not = to piece
						// association that is at our destination tile
						// we know this is an enemy piece. So do a capture move
						if (this.pieceAlliance != pieceAlliance) {
							// need board, piece, destination tile, and piece
							// that is being captured
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}
			//check quad 3
			if (this.Q3.contains(this.piecePosition) && candidateOffset == 1 && !backwards) {
				backwards = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
						/*
						 * if(backwards = true && (!(quadrant == 3 &&
						 * candidateOffset == 1) || !(quadrant == 1 &&
						 * candidateOffset == -1) || !(quadrant == 2 &&
						 * candidateOffset == -7) || !(quadrant == 4 &&
						 * candidateOffset == 7))) { legalMove.add(new
						 * Move.BasicMove(board, this,
						 * candidateDestinationCoordinate)); }
						 */

						legalMove.add(new Move.BasicMove(board, this, candidateDestinationCoordinate));
					}
					// there is some piece there. Find out what it is and do
					// stuff
					else {

						// get the piece at this location
						final Piece pieceAtDestination = candidateTile.getPiece();

						// get the association of the piece
						final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

						// if THIS piece that we are examining is not = to piece
						// association that is at our destination tile
						// we know this is an enemy piece. So do a capture move
						if (this.pieceAlliance != pieceAlliance) {
							// need board, piece, destination tile, and piece
							// that is being captured
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}
			
			//check quad 4
			if (this.Q4.contains(this.piecePosition) && candidateOffset == 7 && !backwards) {
				backwards = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
						/*
						 * if(backwards = true && (!(quadrant == 3 &&
						 * candidateOffset == 1) || !(quadrant == 1 &&
						 * candidateOffset == -1) || !(quadrant == 2 &&
						 * candidateOffset == -7) || !(quadrant == 4 &&
						 * candidateOffset == 7))) { legalMove.add(new
						 * Move.BasicMove(board, this,
						 * candidateDestinationCoordinate)); }
						 */

						legalMove.add(new Move.BasicMove(board, this, candidateDestinationCoordinate));
					}
					// there is some piece there. Find out what it is and do
					// stuff
					else {

						// get the piece at this location
						final Piece pieceAtDestination = candidateTile.getPiece();

						// get the association of the piece
						final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

						// if THIS piece that we are examining is not = to piece
						// association that is at our destination tile
						// we know this is an enemy piece. So do a capture move
						if (this.pieceAlliance != pieceAlliance) {
							// need board, piece, destination tile, and piece
							// that is being captured
							//System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}
			//while coordinate is still in bounds
			while(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				if (isColumnExclusion(candidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
				//add offset and check if new destination is valid
				candidateDestinationCoordinate += candidateOffset;
				// REBOUNDING
				// If quadrant one rebound tile == 6
				if (BoardUtilities.QUADRANT_ONE.contains(this.piecePosition) && candidateDestinationCoordinate == 6 && !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = 6;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {

						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += 7;//seven is moving downward orthagonally

						if (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
							// get tile of the board of the destination
							// coordinate
							// where you want to move your piece
							final Tile candidateTile = board.getTile(tempCandidateDestinationCoordinate);

							// if tile is NOT occupied add to the list of valid
							// moves and continue back to while loop
							if (!candidateTile.isTileOccupided()) {
								/*
								 * if(backwards = true && (!(quadrant == 3 &&
								 * candidateOffset == 1) || !(quadrant == 1 &&
								 * candidateOffset == -1) || !(quadrant == 2 &&
								 * candidateOffset == -7) || !(quadrant == 4 &&
								 * candidateOffset == 7))) { legalMove.add(new
								 * Move.BasicMove(board, this,
								 * candidateDestinationCoordinate)); }
								 */

								legalMove.add(new Move.BasicMove(board, this, tempCandidateDestinationCoordinate));
							}
							// there is some piece there. Find out what it is
							// and do
							// stuff
							else {
								// get the piece at this location
								final Piece pieceAtDestination = candidateTile.getPiece();

								// get the association of the piece
								final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

								// if THIS piece that we are examining is not =
								// to
								// piece association that is at our destination
								// tile
								// we know this is an enemy piece. So do a
								// capture
								// move
								if (this.pieceAlliance != pieceAlliance) {
									// need board, piece, destination tile, and
									// piece that is being captured
									legalMove.add(new Move.CaptureMove(board, this, tempCandidateDestinationCoordinate,
											pieceAtDestination));
								}
								break; // break when we encounter an occupied
										// tile
							}
						} // end if isValidTileCoordinate
					} // end while isValidTileCoordinate
				}//end Q1 rebound
				// If quadrant two rebound tile == 48
				if (BoardUtilities.QUADRANT_TWO.contains(this.piecePosition) && candidateDestinationCoordinate == 48 && !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = 48;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {

						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += -1;//seven is moving left orthagonally

						if (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
							// get tile of the board of the destination
							// coordinate
							// where you want to move your piece
							final Tile candidateTile = board.getTile(tempCandidateDestinationCoordinate);

							// if tile is NOT occupied add to the list of valid
							// moves and continue back to while loop
							if (!candidateTile.isTileOccupided()) {
								/*
								 * if(backwards = true && (!(quadrant == 3 &&
								 * candidateOffset == 1) || !(quadrant == 1 &&
								 * candidateOffset == -1) || !(quadrant == 2 &&
								 * candidateOffset == -7) || !(quadrant == 4 &&
								 * candidateOffset == 7))) { legalMove.add(new
								 * Move.BasicMove(board, this,
								 * candidateDestinationCoordinate)); }
								 */

								legalMove.add(new Move.BasicMove(board, this, tempCandidateDestinationCoordinate));
							}
							// there is some piece there. Find out what it is
							// and do
							// stuff
							else {
								// get the piece at this location
								final Piece pieceAtDestination = candidateTile.getPiece();

								// get the association of the piece
								final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

								// if THIS piece that we are examining is not =
								// to
								// piece association that is at our destination
								// tile
								// we know this is an enemy piece. So do a
								// capture
								// move
								if (this.pieceAlliance != pieceAlliance) {
									// need board, piece, destination tile, and
									// piece that is being captured
									legalMove.add(new Move.CaptureMove(board, this, tempCandidateDestinationCoordinate,
											pieceAtDestination));
								}
								break; // break when we encounter an occupied
										// tile
							}
						} // end if isValidTileCoordinate
					} // end while isValidTileCoordinate
				}//end Q2 rebound
				// If quadrant three rebound tile == 42
				if (BoardUtilities.QUADRANT_THREE.contains(this.piecePosition) && candidateDestinationCoordinate == 42 && !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = 42;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {

						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += -7;//seven is moving downward orthagonally

						if (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
							// get tile of the board of the destination
							// coordinate
							// where you want to move your piece
							final Tile candidateTile = board.getTile(tempCandidateDestinationCoordinate);

							// if tile is NOT occupied add to the list of valid
							// moves and continue back to while loop
							if (!candidateTile.isTileOccupided()) {
								/*
								 * if(backwards = true && (!(quadrant == 3 &&
								 * candidateOffset == 1) || !(quadrant == 1 &&
								 * candidateOffset == -1) || !(quadrant == 2 &&
								 * candidateOffset == -7) || !(quadrant == 4 &&
								 * candidateOffset == 7))) { legalMove.add(new
								 * Move.BasicMove(board, this,
								 * candidateDestinationCoordinate)); }
								 */

								legalMove.add(new Move.BasicMove(board, this, tempCandidateDestinationCoordinate));
							}
							// there is some piece there. Find out what it is
							// and do
							// stuff
							else {
								// get the piece at this location
								final Piece pieceAtDestination = candidateTile.getPiece();

								// get the association of the piece
								final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

								// if THIS piece that we are examining is not =
								// to
								// piece association that is at our destination
								// tile
								// we know this is an enemy piece. So do a
								// capture
								// move
								if (this.pieceAlliance != pieceAlliance) {
									// need board, piece, destination tile, and
									// piece that is being captured
									legalMove.add(new Move.CaptureMove(board, this, tempCandidateDestinationCoordinate,
											pieceAtDestination));
								}
								break; // break when we encounter an occupied
										// tile
							}
						} // end if isValidTileCoordinate
					} // end while isValidTileCoordinate
				}//end Q3 rebound
				// If quadrant four rebound tile == 0
				if (BoardUtilities.QUADRANT_FOUR.contains(this.piecePosition) && candidateDestinationCoordinate == 0 && !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = 0;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {

						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += 1;//seven is moving downward orthagonally

						if (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
							// get tile of the board of the destination
							// coordinate
							// where you want to move your piece
							final Tile candidateTile = board.getTile(tempCandidateDestinationCoordinate);

							// if tile is NOT occupied add to the list of valid
							// moves and continue back to while loop
							if (!candidateTile.isTileOccupided()) {
								/*
								 * if(backwards = true && (!(quadrant == 3 &&
								 * candidateOffset == 1) || !(quadrant == 1 &&
								 * candidateOffset == -1) || !(quadrant == 2 &&
								 * candidateOffset == -7) || !(quadrant == 4 &&
								 * candidateOffset == 7))) { legalMove.add(new
								 * Move.BasicMove(board, this,
								 * candidateDestinationCoordinate)); }
								 */

								legalMove.add(new Move.BasicMove(board, this, tempCandidateDestinationCoordinate));
							}
							// there is some piece there. Find out what it is
							// and do
							// stuff
							else {
								// get the piece at this location
								final Piece pieceAtDestination = candidateTile.getPiece();

								// get the association of the piece
								final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

								// if THIS piece that we are examining is not =
								// to
								// piece association that is at our destination
								// tile
								// we know this is an enemy piece. So do a
								// capture
								// move
								if (this.pieceAlliance != pieceAlliance) {
									// need board, piece, destination tile, and
									// piece that is being captured
									legalMove.add(new Move.CaptureMove(board, this, tempCandidateDestinationCoordinate,
											pieceAtDestination));
								}
								break; // break when we encounter an occupied
										// tile
							}
						} // end if isValidTileCoordinate
					} // end while isValidTileCoordinate
				}//end Q4 rebound
				if(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate))
				{
					//get tile of the board of the destination coordinate where you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					//if tile is NOT occupied add to the list of valid moves and continue back to while loop
					if(!candidateTile.isTileOccupided())
					{
						/*if(backwards = true && (!(quadrant == 3 && candidateOffset == 1) || !(quadrant == 1 && candidateOffset == -1) 
											|| !(quadrant == 2 && candidateOffset == -7) || !(quadrant == 4 && candidateOffset == 7)))
						{
							legalMove.add(new Move.BasicMove(board, this, candidateDestinationCoordinate));
						}*/
						
						legalMove.add(new Move.BasicMove(board, this, candidateDestinationCoordinate));
					}
					//there is some piece there. Find out what it is and do stuff
					else
					{
						//get the piece at this location
						final Piece pieceAtDestination = candidateTile.getPiece();

						//get the association of the piece
						final Alliance pieceAlliance = pieceAtDestination.getPieceAssociation();

						//if THIS piece that we are examining is not = to piece association that is at our destination tile
						//we know this is an enemy piece. So do a capture move
						if(this.pieceAlliance != pieceAlliance)
						{
							//need board, piece, destination tile, and piece that is being captured
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break; //break when we encounter an occupied tile
					}
				}//end if isValidTileCoordinate
			}//end while isValidTileCoordinate
		}//end loop through CANDIDATE_MOVE_COORDINATES
		return ImmutableList.copyOf(legalMove);
	}


	//used for printing since I don't have gui
	@Override
	public String toString()
	{
		return PieceType.Rook.toString();
	}

	@Override
	public Piece movePiece(Move move) 
	{
		return new Rook(move.getDestCoordinate(), move.getMovedPiece().getPieceAssociation());
	}
	
	private static boolean isColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
		return (BoardUtilities.FIRST_COLUMN[candidateDestinationCoordinate] && (currentCandidate == -1))
				|| (BoardUtilities.LAST_COLUMN[candidateDestinationCoordinate] && (currentCandidate == 1));
	}
}
