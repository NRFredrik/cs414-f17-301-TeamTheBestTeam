package edu.colostate.cs.cs414.teamthebestteam.rollerball.player;

import java.util.Collection;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public class WhitePlayer extends Player{

	public WhitePlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
		super(board, whiteLegalMoves, blackLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

}
