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


public class Rook extends Piece{

	private static Set<Integer> CANDIDATE_MOVE_COORDINATES = null;
	
	public Rook(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.Rook,piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		legalMoves.add(new Move.BasicMove(board, this, 36));
		return legalMoves;
	}
	
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
