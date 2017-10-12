package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;

public abstract class Piece {
	protected final int  piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	
	Piece(final int piecePosition, final Alliance pieceAlliance ){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = false;
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
	
	//used for printing since I dont have gui
	public enum PieceType
	{
		Pawn("pawn"),
		Rook("rook"),
		Bishop("bishop"),
		King("king");
		
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
	}
	
	public abstract List<Move> calculateLegalMoves(final Board board);

	public Alliance getColor() {
		return this.pieceAlliance;
	}
	
}
