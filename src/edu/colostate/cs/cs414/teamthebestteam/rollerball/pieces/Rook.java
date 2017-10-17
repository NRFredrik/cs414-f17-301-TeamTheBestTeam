package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.BoardUtilities;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Tile;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece.PieceType;


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
			 * loop through valid moves and while adding multiples of the 
			 * CANDIDATE_MOVE_COORDINATES is within bounds add to legal moves
			 */
			for(int candidateOffset : CANDIDATE_MOVE_COORDINATES)
			{
				int candidateDestinationCoordinate = this.piecePosition;
				
				//while coordinate is still in bounds
				while(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate))
				{
					if(/*Some Corner Case*/false) 
							
					{
						//continue;
					}
						
					//add offset and check if new destination is valid
					candidateDestinationCoordinate += candidateOffset;
					
					if(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate))
					{
						//get tile of the board of the destination coordinate where you want to move your piece
						final Tile candidateTile = board.getTile(candidateDestinationCoordinate);

						//if tile is NOT occupied add to the list of valid moves and continue back to while loop
						if(!candidateTile.isTileOccupided())
						{
							//pass in board, current piece that we are on,
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
}
