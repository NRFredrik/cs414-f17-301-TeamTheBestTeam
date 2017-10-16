package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.Collection;
import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;

public abstract class Piece {
	protected final int  piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final boolean didRebound; //only apply's to rook and bishop
	protected final PieceType pieceType;
	
	Piece(PieceType pieceType, final int piecePosition, final Alliance pieceAlliance ){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = false;
		this.didRebound = false;
		this.pieceType = pieceType;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Piece))
		{
			return false;
		}
		Piece other = (Piece) o;	
		return piecePosition == other.getPiecePosition() && pieceType == other.getPieceType() && pieceAlliance == other.getPieceAssociation() && isFirstMove == other.isFirstMove() ;
	}
	
	public PieceType getPieceType() 
	{
		return this.pieceType;
	}

	@Override
	public int hashCode()
	{
		int res = pieceType.hashCode();
		res = 53 * res + pieceAlliance.hashCode();
		res = 53 * res + piecePosition;
		return res = 53 * res + (isFirstMove ? 1 : 0);
	}
	
	public int getPiecePosition()
	{
		return this.piecePosition;
	}
	
	public Alliance getPieceAssociation()
	{
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove()
	{
		return this.isFirstMove;
	}
	
	//used for printing since I don't have GUI
	public enum PieceType
	{
		Pawn("pawn") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		Rook("rook") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		Bishop("bishop") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		King("king") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		private String pieceName;
		
		PieceType(final String pieceName)
		{
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString()
		{
			return this.pieceName;
		}
		public abstract boolean isKing();
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	public abstract Piece movePiece(Move move); //return new piece with updated position value

	public Alliance getColor() {
		return this.pieceAlliance;
	}
	
}
