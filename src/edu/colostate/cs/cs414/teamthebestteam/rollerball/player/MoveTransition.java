package edu.colostate.cs.cs414.teamthebestteam.rollerball.player;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;

/**
 * keep info so you have it when you transition to a new board
 *
 */
public class MoveTransition {
	
	private final Board transBoard;
	private final Move move;
	private final StatusOfMove status; // //status of move (if move is legal, if in check etc)
	
	public MoveTransition(final Board transBoard, final Move move, final StatusOfMove status)
	{
		this.move = move;
		this.status = status;
		this.transBoard = transBoard;
	}
	
	public StatusOfMove getStatus()
	{
		return this.status;
	}

	public Board getBoard() {
		return this.transBoard;
	}
}
