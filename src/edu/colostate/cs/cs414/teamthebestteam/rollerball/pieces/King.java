package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;



public class King extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATES = {-8,-7,-6,-1,1,6,7,8};

	public King(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.King,piecePosition, pieceAlliance);

	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		int possibleDestinationCoordinate;
		final List<Move> legalMoves = new ArrayList<>();
		for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
			possibleDestinationCoordinate = this.piecePosition + currentCandidate;
			if (BoardUtilities.isValidTileCoordinate(possibleDestinationCoordinate)) {
				final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

				//don't want to do any of this if we are in corner cases
				if(isFirstColumnExclusion(this.piecePosition, currentCandidate) || isLastColumnExclusion(this.piecePosition, currentCandidate)){
					continue;
				}

				// check if tile is occupied
				// if not add it to moves
				// else check whether black or white occupies
				if (!possibleDestinationTile.isTileOccupided()) {
					//pass in board, current piece that we are on,
					legalMoves.add(new Move.BasicMove(board, this, possibleDestinationCoordinate));
				} else {
					//get the piece at tile 
					final Piece pieceAtDestination = possibleDestinationTile.getPiece();
					//check if opponent or same team
					final Alliance pieceAlliance = pieceAtDestination.getColor();
					//if opponent on tile add to moves
					//later on remove opponent tile TODO
					if (this.pieceAlliance != pieceAlliance) {
						//need board, piece, destination tile, and piece that is being captured
						legalMoves.add(new Move.CaptureMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
					}
				}//end else for tile being occupied
			}//end if isValidTileCoordinate
		}//end for loop through CANDIDATE_MOVE_COORDINATES
		return ImmutableList.copyOf(legalMoves);
	}

	//corner case for first column
	//NOT SURE IF THIS IS ALL OF THEM
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		//return true if your current piece in the first column (0,7,14...)
		//and if candidate moves is one of the moves that doesnt apply when piece is in first column
		return BoardUtilities.FIRST_COLUMN[currentPosition] && (candidateOffset == -8 || candidateOffset == -1 || candidateOffset == 6);
	}


	//corner case for last column
	//NOT SURE IF THIS IS ALL OF THE EXCLUSIONS
	private static boolean isLastColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtilities.FIRST_COLUMN[currentPosition] && (candidateOffset == 8 || candidateOffset == 1 || candidateOffset == -6);
	}

	
	@Override
	public String toString()
	{
		return PieceType.King.toString();
	}
	
	@Override
	public Piece movePiece(Move move) 
	{
		return new King(move.getDestCoordinate(), move.getMovedPiece().getPieceAssociation());
	}

}
