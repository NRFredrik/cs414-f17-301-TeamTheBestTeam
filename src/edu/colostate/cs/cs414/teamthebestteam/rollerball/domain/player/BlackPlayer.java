package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player;

import java.util.Collection;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Piece;

public class BlackPlayer extends Player{

	public BlackPlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
		super(board, blackLegalMoves, whiteLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}

}
