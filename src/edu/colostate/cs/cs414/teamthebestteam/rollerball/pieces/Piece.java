package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;

public abstract class Piece {
	protected final int  piecePosition;
	protected final Alliance pieceAlliance;
	
	Piece(final int piecePosition, final Alliance pieceAlliance ){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
	}
	
	public abstract List<Move> calculateLegalMoves(final Board board);

	public Alliance getColor() {
		return this.pieceAlliance;
	}
	
}
