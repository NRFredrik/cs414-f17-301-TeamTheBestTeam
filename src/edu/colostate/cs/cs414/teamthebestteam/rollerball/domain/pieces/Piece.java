package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Move;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Alliance;

public abstract class Piece {
	protected final int  piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final boolean didRebound; //only apply's to rook and bishop
	protected final PieceType pieceType;
	protected final Set<Integer> Q1;
	protected final Set<Integer> Q2;
	protected final Set<Integer> Q3;
	protected final Set<Integer> Q4;
	
	Piece(PieceType pieceType, final int piecePosition, final Alliance pieceAlliance ){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = false;
		this.didRebound = false;
		this.pieceType = pieceType;
		Q1 = quad1();
		Q2 = quad2();
		Q3 = quad3();
		Q4 = quad4();
	}
	
	//Quadrant sets to be used for backwards movements
	//quad1
	private Set<Integer> quad1() {
		Set<Integer> quadrant = new HashSet<>();
		for(int i = 0; i < 12; i++){
			if(i != 5 || i != 6){
				quadrant.add(i);
			}
		}
		return quadrant;
	}
	//quad2
	private Set<Integer> quad2() {
		Integer[] quad_two = { 5,6,12,13,19,20,26,27,33,34 };
		Set<Integer> quadrant = new HashSet<>(Arrays.asList(quad_two));
		return quadrant;
	}
	//quad3
	private Set<Integer> quad3(){
		Set<Integer> quadrant = new HashSet<>();
		for(int i = 37; i < 49; i++){
			if(i != 42 || i != 43){
				quadrant.add(i);
			}
		}
		return quadrant;
	}
	//quad4
	private Set<Integer> quad4(){
		Integer[] quad_four = { 42,43,35,36,28,29,21,22,14,15 } ;
		Set<Integer> quadrant = new HashSet<>(Arrays.asList(quad_four));
		return quadrant;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Piece))
		{
			return false;
		}
		Piece other = (Piece) o;	
		return piecePosition == other.getPiecePosition() && pieceType == other.getPieceType() && pieceAlliance == other.getPieceAssociation() && isFirstMove == other.isFirstMove() ;
	}
	
	public PieceType getPieceType() 
	{
		return this.pieceType;
	}

	@Override
	public int hashCode()
	{
		int res = pieceType.hashCode();
		res = 53 * res + pieceAlliance.hashCode();
		res = 53 * res + piecePosition;
		return res = 53 * res + (isFirstMove ? 1 : 0);
	}
	
	public int getPiecePosition()
	{
		return this.piecePosition;
	}
	public int getPieceValue() 
	{
		return this.pieceType.getPieceValue();
	}
	
	public Alliance getPieceAssociation()
	{
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove()
	{
		return this.isFirstMove;
	}
	
	//used for printing since I don't have GUI
	public enum PieceType
	{
		Pawn(100,"pawn") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		Rook(500,"rook") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		Bishop(300,"bishop") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		King(1000,"king") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		private String pieceName;
		private int pieceValue;
		
		PieceType(int pieceValue, final String pieceName)
		{
			this.pieceName = pieceName;
			this.pieceValue = pieceValue;
		}
		
		
		public int getPieceValue() {
			// TODO Auto-generated method stub
			return this.pieceValue;
		}

		@Override
		public String toString()
		{
			return this.pieceName;
		}
		public abstract boolean isKing();
		
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	public abstract Piece movePiece(Move move); //return new piece with updated position value

	public Alliance getColor() {
		return this.pieceAlliance;
	}
	
}
