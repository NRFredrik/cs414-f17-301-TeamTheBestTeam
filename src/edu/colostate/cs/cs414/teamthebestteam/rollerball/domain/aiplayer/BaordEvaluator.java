package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.aiplayer;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.Player;

public class BaordEvaluator implements EvalBoard {

	private static final int IN_CHECK = 50;
	private static final int CHECK_MATE = 10000;
	@Override
	public int eval(Board board, int depth) {
	
		return score(board, board.whitePlayer(), depth) - 
				score(board,board.blackPlayer(), depth);
	}

	/**
	 * 
	 * @param board
	 * @param player
	 * @param depth
	 * @return score that includes value of a potential move
	 */
	private int score(Board board, Player player, int depth) {
		return pieceVal(player) + options(player) + inCheck(player) + checkMate(player, depth);
	}

	private int checkMate(Player player, int depth) {
		if(player.getOpponent().isCheckMate())
		{
			return  CHECK_MATE * depthScore(depth);
		}
		return  0;
	}

	private int depthScore(int depth) {
		if(depth == 0)
		{
			return 1;
		}
		else
		{
			return depth * 100;
		}
	}

	private static int inCheck(Player player) {
		if(player.getOpponent().isInCheck())
		{
			return  IN_CHECK;
		}
		return  0;
	}

	/**
	 * 
	 * @param player
	 * @return number of legal moves
	 */
	private int options(Player player) {
		return player.getLegalMoves().size();
	}

	private static int pieceVal(Player player) {
		
		int pieceScore = 0;
		for(Piece p : player.getActivePieces())
		{
			pieceScore = p.getPieceValue() + pieceScore;
		}
		return pieceScore;
	}

}
