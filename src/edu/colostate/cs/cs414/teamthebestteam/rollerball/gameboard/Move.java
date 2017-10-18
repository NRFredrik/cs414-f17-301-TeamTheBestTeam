package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board.Builder;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece.PieceType;

public abstract class Move {
	static Board board;

	//piece that was moved
	final Piece movedPiece;

	//where the piece moved to
	final int destinationCoordinate;
	
	public static final Move GARBAGE_MOVE = new GarbageMove();

	/**
	 * Need board, piece, and where the piece moved
	 * @param board
	 * @param movedPiece
	 * @param destinationCoordinate
	 */
	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate)
	{
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}

	//return a new board with the newly executed move/piece position
	public Board executeMove() 
	{
		final Builder builder = new Builder();

		//go through incoming players pieces
		//place all of the non moved pieces on the new board
		for(Piece p : this.board.currentPlayer().getActivePieces())
		{
			//TODO need equals method for pieces
			if(!this.movedPiece.equals(p))
			{
				builder.setPiece(p);
			}
		}

		//go through other players pieces  and set them on board
		for(Piece p : this.board.currentPlayer().getOpponent().getActivePieces())
		{
			builder.setPiece(p);
		}

		//move the moved piece
		builder.setPiece(this.movedPiece.movePiece(this));
		//set make move for opponent now
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

		return builder.build();
	}	

	/**
	 *sub class that will define a basic move where no other piece occupies the tile
	 */
	public static final class BasicMove extends Move
	{
		public BasicMove(final Board bord, final Piece movedPiece, final int destinationCoordinate) 
		{
			super(bord, movedPiece, destinationCoordinate);
			// TODO Auto-generated constructor stub
		}
	}

	/**
	 *subclass will define a type of move that results in a capture
	 */
	public static class CaptureMove extends Move
	{
		//keep track of piece being captured
		final Piece capturedPiece;

		public CaptureMove(final Board board, final Piece movedPiece, 
				final int destinationCoordinate, final Piece capturedPiece) 
		{

			super(board, movedPiece, destinationCoordinate);
			this.capturedPiece = capturedPiece;	
		}
		
		@Override
		public boolean isAttack()
		{
			return true;
		}
		
		@Override
		public Board executeMove()
		{
			return null;
		}
		
		@Override
		public Piece getAttackedPiece()
		{
			return this.capturedPiece;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if(this == o)
			{
				return true;
			}
			if(!(o instanceof CaptureMove))
			{
				return false;
			}
			CaptureMove other = (CaptureMove) o;
			 return super.equals(other) && getAttackedPiece().equals(other.getAttackedPiece());
		}
		
		@Override
		public int hashCode()
		{
			return this.capturedPiece.hashCode() + super.hashCode();
		}
	}
	
	/**
	 *sub class that will define a pawn move 
	 */
	public static final class PawnMove extends Move
	{
		public PawnMove(final Board bord, final Piece movedPiece, final int destinationCoordinate) 
		{
			super(bord, movedPiece, destinationCoordinate);
			// TODO Auto-generated constructor stub
		}
	}
	
	/**
	 *sub class that will define a pawn capture move 
	 */
	public static final class PawnCaptureMove extends CaptureMove
	{
		public PawnCaptureMove(final Board bord, final Piece movedPiece, final int destinationCoordinate,final Piece capturedPiece) 
		{
			super(bord, movedPiece, destinationCoordinate,capturedPiece);
			// TODO Auto-generated constructor stub
		}
	}
	
	/**
	 *sub class that will define a pooh trash move
	 */
	public static final class GarbageMove extends Move
	{
		public GarbageMove() 
		{
			super(null, null, -1);
			// TODO Auto-generated constructor stub
		}
		
		public Board execute()
		{
			throw new RuntimeException("cant execute garbage move");
		}
	}
	
	
	public static final class FactoryMove
	{
		private FactoryMove()
		{
			throw new RuntimeException("Do not construct me");
		}
		
		public static Move createMove(final Board bord, final int coordinate, final int destinationCoordinate) 
		{
			for(Move m : board.getAllLegalMoves())
			{
				if(m.getCurrentCoordinate() == coordinate && m.getDestCoordinate() == destinationCoordinate)
				{
					return m;
				}
			}
			return GARBAGE_MOVE;	
		}
	}


	public Piece getMovedPiece()
	{
		return this.movedPiece;
	}
	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePosition();
	}

	public int getDestCoordinate()
	{
		return this.destinationCoordinate;
	}
	
	public boolean isAttack()
	{
		return false;
		
	}
	
	public Piece getAttackedPiece()
	{
		return null;
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Move))
		{
			return false;
		}
		 Move other = (Move) o;
		 return getDestCoordinate() == other.getDestCoordinate() && getMovedPiece().equals(other.getMovedPiece());
	}
	
	@Override
	public int hashCode()
	{
		int res = this.movedPiece.getPiecePosition();
		res = 53 * res + this.destinationCoordinate;
		return res = 53 * res + this.movedPiece.hashCode();
	}
	
}
