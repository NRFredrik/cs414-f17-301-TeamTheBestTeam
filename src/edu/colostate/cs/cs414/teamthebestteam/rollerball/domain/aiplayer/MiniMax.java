package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.aiplayer;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.MoveTransition;

public class MiniMax implements MoveEngine{

	private final EvalBoard evalBoard;
	
	public MiniMax()
	{
		this.evalBoard = new BaordEvaluator();
	}
	
	@Override
	public String toString()
	{
		return "MiniMax";
	}
	
	/**
	 * Use MiniMax algorithm to calculate best move using eval function
	 */
	@Override
	public Move execute(Board board, int depth) {
		
		//keeping track of time it takes to calculate
		final long start = System.currentTimeMillis();
		Move moveDetermined = null;
		int high = Integer.MIN_VALUE;
		int low = Integer.MAX_VALUE;
		int curr = 0;
		
		System.out.println(board.currentPlayer() + " Calculating depth = " + depth); 
		int numMoves = board.currentPlayer().getLegalMoves().size();
		
		for(Move m : board.currentPlayer().getLegalMoves())
		{
			try {
				MoveTransition trans = board.currentPlayer().movePlayer(m);
				if(trans.getStatus().isDone())
				{
					if(board.currentPlayer().getAlliance().isWhite())
					{
						curr = min(trans.getBoard(), depth - 1);
					}
					else
					{
						curr = max(trans.getBoard(), depth - 1);
					}
				}
				
				if(board.currentPlayer().getAlliance().isWhite() && curr >= high)
				{
					high = curr;
					moveDetermined = m;
				}
				else if(board.currentPlayer().getAlliance().isBlack() && curr <= low)
				{
					low = curr;
					moveDetermined = m;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//keeping track of time it takes to calculate
		final long totalTime  = System.currentTimeMillis() - start;
		
		return moveDetermined;
	}

	public int min(Board board, int depth)
	{
		if(depth == 0)
		{
			return this.evalBoard.eval(board, depth);
		}
		
		int currentLow = Integer.MAX_VALUE;
		//loop through and make each possible move
		//and score them, after comparing, then return lowest value
		for(Move m : board.currentPlayer().getLegalMoves())
		{
			try {
				MoveTransition trans  = board.currentPlayer.movePlayer(m);
				if(trans.getStatus().isDone())
				{
					int currentVal = max(trans.getBoard(), depth -1);
					if(currentVal <= currentLow)
					{
						currentLow = currentVal;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return currentLow;
	}
	
	public int max(Board board, int depth)
	{
		if(depth == 0)
		{
			return this.evalBoard.eval(board, depth);
		}
		
		int currentMax = Integer.MIN_VALUE;
		//loop through and make each possible move
		//and score them, after comparing, then return max value
		for(Move m : board.currentPlayer().getLegalMoves())
		{
			try {
				MoveTransition trans  = board.currentPlayer.movePlayer(m);
				if(trans.getStatus().isDone())
				{
					int currentVal = min(trans.getBoard(), depth -1);
					if(currentVal >= currentMax)
					{
						currentMax = currentVal;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return currentMax;
	}
}
