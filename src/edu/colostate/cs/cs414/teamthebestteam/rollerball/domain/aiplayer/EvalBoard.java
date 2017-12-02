package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.aiplayer;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;

/**
 * 
 * the more positive the number means the given player is winning
 *
 */
public interface EvalBoard {
	int eval(Board board, int depth);
}
