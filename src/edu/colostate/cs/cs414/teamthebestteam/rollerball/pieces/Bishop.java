package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;


public class Bishop extends Piece{

	public Bishop(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.Bishop,piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		legalMoves.add(new Move.BasicMove(board, this, 1));
		return legalMoves;
	}
	
	@Override
	public String toString()
	{
		return PieceType.Bishop.toString();
	}

	@Override
	public Piece movePiece(Move move) 
	{
		return new Bishop(move.getDestCoordinate(), move.getMovedPiece().getPieceAssociation());
	}

}
