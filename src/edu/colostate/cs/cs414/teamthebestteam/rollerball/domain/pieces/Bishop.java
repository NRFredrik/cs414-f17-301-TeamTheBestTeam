package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Tile;

public class Bishop extends Piece {
	// bishops move diagonally
	private static int[] CANDIDATE_MOVE_COORDINATES = { -8, -6,6, 8};

	public Bishop(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.Bishop, piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		// List will hold all valid moves and will be returned
		final List<Move> legalMove = new ArrayList<>();

		// Keep track of when rebound was used
		boolean didRebound = false;
		/**
		 * depending on which quadrant, rook can go "backwards" handle that
		 * separately Need to Get the quadrant that the piece is in and column
		 */
		int quadrant = BoardUtilities.getQuadrantCoordinates(this.piecePosition);
		int column = BoardUtilities.getColumn(this.piecePosition);
		// keep track if backwards was added to legal
		boolean backwards1 = false;// multiples of 8
		boolean backwards2 = false;// multiples of 6
									// moves yet or not

		/**
		 * loop through valid moves and while adding multiples of the
		 * CANDIDATE_MOVE_COORDINATES is within bounds add to legal moves
		 */
		for (int candidateOffset : CANDIDATE_MOVE_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			// check if backwards is set
			// add offset and check if new destination is valid

			// CHeck for backwards move in quadrant one and up to the left
			// DOES NOT BREAK THE BISHOP
			if (this.Q1.contains(this.piecePosition) && candidateOffset == -8 && !backwards1) {
				backwards1 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;
			}
			// -------------------------------THIS BREAKS THE
			// BISHOP-------------------------------------------------//
			// CHeck for backwards move in quadrant one and down to the left
			if (this.Q1.contains(this.piecePosition) && candidateOffset == 6 && !backwards2) {
				backwards2 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			// check quad 2
			if (this.Q2.contains(this.piecePosition) && candidateOffset == -8 && !backwards1) {
				backwards1 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			if (this.Q2.contains(this.piecePosition) && candidateOffset == -6 && !backwards2) {
				backwards2 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			// check quad 3
			if (this.Q3.contains(this.piecePosition) && candidateOffset == 8 && !backwards1) {
				backwards1 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			if (this.Q3.contains(this.piecePosition) && candidateOffset == -6 && !backwards2) {
				backwards2 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			// check quad 4
			if (this.Q4.contains(this.piecePosition) && candidateOffset == 8 && !backwards1) {
				backwards1 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			if (this.Q4.contains(this.piecePosition) && candidateOffset == 6 && !backwards2) {
				backwards2 = true;

				candidateDestinationCoordinate += candidateOffset;

				if (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
					// get tile of the board of the destination coordinate where
					// you want to move your piece
					final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

					// if tile is NOT occupied add to the list of valid moves
					// and continue back to while loop
					if (!candidateTile.isTileOccupided()) {
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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						continue; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
				continue;

			}

			// while coordinate is still in bounds
			while (BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if (isFirstColumnExclusion(candidateOffset, candidateDestinationCoordinate)
						|| isSeventhColumnExclusion(candidateOffset, candidateDestinationCoordinate)) {
					break;
				}
				candidateDestinationCoordinate += candidateOffset;

				// Rebound Q1 Inner Ring
				// direction should be -6
				if (BoardUtilities.QUADRANT_ONE.contains(candidateDestinationCoordinate)
						&& BoardUtilities.INNER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(-6, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(-6, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += -6;// upward
																	// diagonal
																	// to the
																	// right

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
				} // end quad 1 inner quad rebound

				// Rebound Q1 Outer Ring
				// direction should be 8
				if (BoardUtilities.QUADRANT_ONE.contains(candidateDestinationCoordinate)
						&& BoardUtilities.OUTER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(8, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(8, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += 8;// downward
																// diagonal
																// to the
																// right

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
				} // end quad 1 Outer quad rebound

				// Rebound Q2 Inner Ring
				// direction should be 8
				if (BoardUtilities.QUADRANT_TWO.contains(candidateDestinationCoordinate)
						&& BoardUtilities.INNER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(8, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(8, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += 8;// downward
																// diagonal
																// to the
																// right

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
				} // end quad 2 inner quad rebound

				// Rebound Q1 Inner Ring
				// direction should be 8
				if (BoardUtilities.QUADRANT_TWO.contains(candidateDestinationCoordinate)
						&& BoardUtilities.OUTER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(6, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(6, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += 6;// downward
																// diagonal
																// to the
																// left

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
				} // end quad 2 outer quad rebound

				// Rebound Q3 Inner Ring
				// direction should be 6
				if (BoardUtilities.QUADRANT_THREE.contains(candidateDestinationCoordinate)
						&& BoardUtilities.INNER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(6, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(6, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += 6;// downward
																// diagonal
																// to the
																// left

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
				} // end quad 3 inner quad rebound

				// Rebound Q3 Inner Ring
				// direction should be 6
				if (BoardUtilities.QUADRANT_THREE.contains(candidateDestinationCoordinate)
						&& BoardUtilities.OUTER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(-8, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(-8, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += -8;// upward
																	// diagonal
																	// to the
																	// left

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
				} // end quad 3 outer quad rebound

				// Rebound Q4 Inner Ring
				// direction should be -6
				if (BoardUtilities.QUADRANT_FOUR.contains(candidateDestinationCoordinate)
						&& BoardUtilities.INNER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(-8, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(-8, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += -8;// upward
																	// diagonal
																	// to the
																	// right

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
				} // end quad 4 inner quad rebound

				// Rebound Q4 Outer Ring
				// direction should be -8
				if (BoardUtilities.QUADRANT_FOUR.contains(candidateDestinationCoordinate)
						&& BoardUtilities.OUTER_RING.contains(candidateDestinationCoordinate)
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupided()) {
					int tempCandidateDestinationCoordinate = candidateDestinationCoordinate;
					while (BoardUtilities.isValidTileCoordinate(tempCandidateDestinationCoordinate)) {
						if (isFirstColumnExclusion(-6, tempCandidateDestinationCoordinate)
								|| isSeventhColumnExclusion(-6, tempCandidateDestinationCoordinate)) {
							break;
						}
						// add offset and check if new destination is valid
						tempCandidateDestinationCoordinate += -6;// upward
																	// diagonal
																	// to the
																	// left

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
				} // end quad 4 outer quad rebound

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
							System.out.println("ATTACK!!!!!");
							legalMove.add(new Move.CaptureMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						break; // break when we encounter an occupied tile
					}
				} // end if isValidTileCoordinate
			} // end while isValidTileCoordinate
		} // end loop through CANDIDATE_MOVE_COORDINATES
		return ImmutableList.copyOf(legalMove);
	}

	@Override
	public String toString() {
		return PieceType.Bishop.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Bishop(move.getDestCoordinate(), move.getMovedPiece().getPieceAssociation());
	}

	private static boolean isFirstColumnExclusion(final int currentCandidate,
			final int candidateDestinationCoordinate) {
		return (BoardUtilities.FIRST_COLUMN[candidateDestinationCoordinate]
				&& ((currentCandidate == -8) || (currentCandidate == 6)));
	}

	private static boolean isSeventhColumnExclusion(final int currentCandidate,
			final int candidateDestinationCoordinate) {
		return BoardUtilities.LAST_COLUMN[candidateDestinationCoordinate]
				&& ((currentCandidate == -6) || (currentCandidate == 8));
	}

}
