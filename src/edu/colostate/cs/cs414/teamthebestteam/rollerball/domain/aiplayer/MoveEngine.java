package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.aiplayer;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;

public interface MoveEngine {

	Move execute(Board board, int depth);
}
