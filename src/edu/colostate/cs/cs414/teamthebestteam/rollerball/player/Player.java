package edu.colostate.cs.cs414.teamthebestteam.rollerball.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.google.common.collect.ImmutableList;
//import com.sun.corba.se.spi.ior.MakeImmutable;



import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.King;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece.PieceType;

public abstract class Player {

	//keep track of board where player is playing
	protected final Board board;
	
	//player needs to know where King is
	protected final King playersKing;
	
	//need a collection of all legal moves
	protected final Collection<Move> legalMoves;
	
	private final boolean isInCheck;
	
	
	public Player(final Board board, Collection<Move> legalMoves, Collection<Move> opponentsMoves)
	{
		this.board = board;
		this.playersKing = establishKing();
		this.legalMoves = legalMoves;
		
		//does opponents moves collection attack current players king position?
		//if collection not empty then the current player is in check
		this.isInCheck = !Player.calculateAttacksOnTile(this.playersKing.getPiecePosition(), opponentsMoves).isEmpty();
	}

	/**
	 * check to see id the enemies destination moves overlap with kings coordinate
	 * if so, these moves attack the king
	 * @param piecePosition
	 * @param moves
	 * @return list of attack moves on king
	 */
	private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {

		final List<Move> attackMoves = new ArrayList<>();
		for(Move m : moves)
		{
			//if kings piece position is same as destination coord
			if(piecePosition == m.getDestCoordinate())
			{
				attackMoves.add(m);
			}
		}
		return ImmutableList.copyOf(attackMoves);
	}

	/*
	 * check for king
	 * TODO Not sure if this Piece.PieceType will be analyzing the piece in the for loop
	 */
	private King establishKing() 
	{
		for(Piece p : getActivePieces())
		{
			if(p.getPieceType() == PieceType.King)
			{
				return (King) p;
			}
		}
		//if you don't return a king in the for loop then game is in an invalid state
		throw new RuntimeException("There is no king on this board");
	}
	
	public boolean isMoveValid(Move move)
	{
		return this.legalMoves.contains(move);
	}
	
	//when you move, keep track of all info about the move that you made so you have it on next board
	public MoveTransition movePlayer(final Move move)throws Exception
	{
		//if the move is not legal, return same board with status of Illegal
		if(!isMoveValid(move))
		{
			return new MoveTransition(board, move, StatusOfMove.ILLEGAL);
		}
		
		//the move is legal so execute it and return new board
		final Board transitionBoard = move.executeMove();
		
		//need to know where current person kings location, and list of opponent moves to see if they could hurt current player or not
		//if this list is not empty this is bad move
		Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
										transitionBoard.currentPlayer().getLegalMoves());
		
		//Collection<Move> kingAttacks = new ArrayList<>();
		//are there any attacks on current players king if so return same board and status letting us know its bad move
		if(!kingAttacks.isEmpty())
		{
			//System.out.println("not empty.");
			return new MoveTransition(this.board, move, StatusOfMove.RESULTS_IN_CHECK);
		}
		
		return  new MoveTransition(transitionBoard, move, StatusOfMove.Done); //all is well so return transitionboard and done status
	}
	
	public Collection<Move> getLegalMoves()
	{
		return this.legalMoves;
	}
	public King getPlayerKing()
	{
		return this.playersKing;
	}
	
	//return true if king is in check and has no escape moves
	public boolean isCheckMate()
	{
		return this.isInCheck && !hasEscape();
	}
	
	//Current player is not in check yet and does not have any escape moves
	public boolean isStale()
	{
		return !this.isInCheck && !hasEscape();
	}

	//go through each of the players moves and make them on an theoretical board. After making move, 
	//check board and see if move (w/o being in check) is possible return true
	protected boolean hasEscape() 
	{
		for(Move m : this.legalMoves)
		{
			MoveTransition trans;
			try {
				trans = movePlayer(m);
			
			if(trans.getStatus().isDone())
			{
				return true;
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean isMoveLegal(Move move)
	{
		return this.legalMoves.contains(move);
	}
	
	//returns true if king is in check
	public boolean isInCheck()
	{
		return this.isInCheck;
	}
	
	
	//These are Polymorphically determined and returns the boards black/white pieces
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
}
