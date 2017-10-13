package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;

public class King extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {};

	public King(final int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);

	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		int possibleDestinationCoordinate;
		final List<Move> legalMoves = new ArrayList<>();
		for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
			possibleDestinationCoordinate = this.piecePosition + currentCandidate;
			if (BoardUtilities.isValidTileCoordinate(possibleDestinationCoordinate)) {
				final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

				// check if tile is occupied
				// if not add it to moves
				// else check whether black or white occupies
				if (!possibleDestinationTile.isTileOccupided()) {
					legalMoves.add(new Move());
				} else {
					//get the piece at tile 
					final Piece pieceAtDestination = possibleDestinationTile.getPiece();
					//check if opponent or same team
					final Alliance pieceAlliance = pieceAtDestination.getColor();
					//if opponent on tile add to moves
					//later on remove opponent tile TODO
					if (this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new Move());
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	//POSSIBLY CAN REMOVE TWO OF THESE AND THEY ARE UNFINISHED
	//flags to make sure you are not moving off the board
	private static boolean isOuterRingQuadrantOneExclusion(final int currentPosition, final int candidateOffset){
		return BoardUtilities.OUTER_RING[currentPosition] && BoardUtilities.QUADRANT_ONE[currentPosition];
	}
	
	private static boolean isInnerRingQuadrantOneExclusion(final int currentPosition, final int candidateOffset){
		return BoardUtilities.INNER_RING[currentPosition] && BoardUtilities.QUADRANT_ONE[currentPosition];
	}
	
	private static boolean isOuterRingQuadrantTwoExclusion(final int currentPosition, final int candidateOffset){
		return BoardUtilities.OUTER_RING[currentPosition] && BoardUtilities.QUADRANT_TWO[currentPosition];
	}
	
	private static boolean isInnerRingQuadrantTwoExclusion(final int currentPosition, final int candidateOffset){
		return BoardUtilities.INNER_RING[currentPosition] && BoardUtilities.QUADRANT_TWO[currentPosition];
	}

}
