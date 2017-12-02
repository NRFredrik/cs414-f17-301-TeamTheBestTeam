package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Tile;

public class Pawn extends Piece {

	/**
	 * one step orthogonally forward on the ring on which it currently stands,
	 * or one step diagonally forward to either ring. A pawn does not move
	 * backward or sideways, and there is no initial two-step option. A pawn
	 * promotes to rook or bishop when reaching either of the two starting
	 * squares of the opponent's pawns. CANDIDATE_MOVE_COORDINATES will be an
	 * array that gets populated by determining which Quadrant a player is in.
	 * This will help enforce direction
	 */
	public static Set<Integer> CANDIDATE_MOVE_COORDINATES = null;

	public Pawn(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.Pawn, piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return PieceType.Pawn.toString();
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		// List will hold all valid moves and will be returned
		final List<Move> legalMove = new ArrayList<>();

		/**
		 * Get the quadrant that the piece is in
		 */
		int quadrant = BoardUtilities.getQuadrantCoordinates(this.piecePosition);
		System.out.println("BU GET QUAD: " + quadrant);
		int column = BoardUtilities.getColumn(this.piecePosition);

		CANDIDATE_MOVE_COORDINATES = determineCoordinates(quadrant, column, this.piecePosition);

		for (int candidateOffset : CANDIDATE_MOVE_COORDINATES) {
			int possibleDestinationCoordinate = this.piecePosition + candidateOffset;

			if (!BoardUtilities.isValidTileCoordinate(possibleDestinationCoordinate)) {
				continue;
			}

			// get tile of the board of the destination coordinate where you
			// want to move your piece
			final Tile candidateTile = board.getTile(possibleDestinationCoordinate);

			//if moving to a none occupied tile, add to legal moves
			//pass in board, current piece that we are on,
			if(!candidateTile.isTileOccupided())
			{
				//if the pawn can land on a promtion square without an attack
				if(this.pieceAlliance.isPawnPromotionSquare(possibleDestinationCoordinate)){
					legalMove.add(new Move.pawnPromotion(new Move.BasicMove(board, this, possibleDestinationCoordinate), new Rook(possibleDestinationCoordinate,this.pieceAlliance)));
					//legalMove.add(new Move.pawnPromotion(new Move.BasicMove(board, this, possibleDestinationCoordinate), new Bishop(possibleDestinationCoordinate,this.pieceAlliance)));

				}
				//System.out.println(possibleDestinationCoordinate);
				legalMove.add(new Move.BasicMove(board, this, possibleDestinationCoordinate));
			}

			// There is a piece at this coordinate
			else {
				// get the piece at this location
				final Piece pieceAtDestination = candidateTile.getPiece();

				// get the association of the piece
				final Alliance pieceAssociation = pieceAtDestination.getPieceAssociation();

				// if THIS piece that we are examining is not = to piece
				// association that is at our destination tile
				// we know this is an enemy piece. So do a capture move
				if (this.getPieceAssociation() != pieceAssociation) {
					// need board, piece, destination tile, and piece that is
					// being captured
					//if the pawn can land on a promtion square without an attack
					if(this.pieceAlliance.isPawnPromotionSquare(possibleDestinationCoordinate)){
						//legalMove.add(new Move.pawnPromotion(new Move.CaptureMove(board, this, possibleDestinationCoordinate, pieceAtDestination),new Rook(possibleDestinationCoordinate,this.pieceAlliance)));
						legalMove.add(new Move.pawnPromotion(new Move.CaptureMove(board, this, possibleDestinationCoordinate, pieceAtDestination),new Bishop(possibleDestinationCoordinate,this.pieceAlliance)));
					}
					legalMove.add(new Move.CaptureMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
				}
			}
		} // end loop through

		return ImmutableList.copyOf(legalMove);
	}// end calculateLegalMove

	/**
	 * @author kb
	 * @author cm
	 * @param quadrant
	 * @param column
	 * @return possible move coordinates depending on which quadrant you are in
	 */

	// This is an excellent method just need a couple tweeks to the coordinates
	private Set<Integer> determineCoordinates(int quadrant, int column, int idOfTile) {
		Set<Integer> coords = new HashSet<>();
		Integer[] r1 = { 0, 7, 14, 21, 28, 35, 42 };
		Set<Integer> rowZero = new HashSet<>(Arrays.asList(r1));
		Integer[] r2 = { 6, 13, 20, 27, 34, 41, 48 };
		Set<Integer> rowSix = new HashSet<>(Arrays.asList(r2));
		System.out.println("QUAD: " + quadrant);
		System.out.println("COLU: " + column);
		System.out.println("TILE: " + idOfTile);
		// 6 is never in q1
		// 0 is however
		if (quadrant == 1) {
			if (idOfTile == 7) {
				coords.add(-6);
				coords.add(-7);
			} else if (idOfTile == 0) {
				coords.add(1);
				coords.add(8);
			} else {
				coords.add(8);
				coords.add(1);
				coords.add(-6);
			}
		}

		if (quadrant == 2) {
			if (idOfTile == 5) {
				coords.add(8);
				coords.add(1);
			} else if (idOfTile == 6) {
				coords.add(7);
				coords.add(6);
			} else if(idOfTile == 33){
				coords.add(6);
				coords.add(7);
				coords.add(8);
			}

			else {
				if(BoardUtilities.INNER_RING.contains(idOfTile)){
					coords.add(8);
					coords.add(7);
				}
				
				if(BoardUtilities.OUTER_RING.contains(idOfTile)){
					coords.add(6);
					coords.add(7);
				}
			}
		}

		if (quadrant == 3) {
			if (idOfTile == 41) {
				coords.add(6);
				coords.add(7);
			} else if (idOfTile == 48) {
				coords.add(-1);
				coords.add(-8);
			} else {
				coords.add(-8);
				coords.add(-1);
				coords.add(6);
			}
		}

		if (quadrant == 4) {
			// if you are NOT in last row
			if (idOfTile == 43) {
				coords.add(-8);
				coords.add(-1);

			} else if (idOfTile == 42) {
				coords.add(-7);
				coords.add(-6);
			} else if(idOfTile==15){
				coords.add(-8);
				coords.add(-7);
				coords.add(-6);
			}
			

			else {
				if(BoardUtilities.INNER_RING.contains(idOfTile)){
					coords.add(-8);
					coords.add(-7);
				}
				
				if(BoardUtilities.OUTER_RING.contains(idOfTile)){
					coords.add(-6);
					coords.add(-7);
				}
			}
		}
		return coords;
	}

	@Override
	public Piece movePiece(Move move) {
		return new Pawn(move.getDestCoordinate(), move.getMovedPiece().getPieceAssociation());
	}

	public Piece getPromotionPiece() {
		// TODO Auto-generated method stub
		return null;
	}

}
