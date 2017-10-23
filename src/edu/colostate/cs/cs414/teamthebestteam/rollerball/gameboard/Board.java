package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.*;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Bishop;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.King;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Pawn;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Rook;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.player.BlackPlayer;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.player.Player;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.player.WhitePlayer;



public class Board {

	//collection of tiles that make up the game board
	private final List<Tile> gameBoard;

	//keep track of white pieces 
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	public final Player currentPlayer;

	//Both players
	public WhitePlayer white;
	public BlackPlayer black;

	

	public Board(Builder builder)throws Exception
	{
		
			
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		final Collection<Move> whiteStandardLegalMove = calculateLegalMove(this.whitePieces);
		final Collection<Move> blackStandardLegalMove = calculateLegalMove(this.blackPieces);

		this.white = new WhitePlayer(this, whiteStandardLegalMove, blackStandardLegalMove);
		this.black = new BlackPlayer(this, whiteStandardLegalMove, blackStandardLegalMove);

		//TODO
		this.currentPlayer = builder.nextMoveMaker.playersTurn(this.white,this.black);
		System.out.println("TURN: "+ this.currentPlayer.getAlliance() + " player's turn to go\n");
		//accept();
		
	}

	//used to view the board since I dont have gui
	//old version of this method saved
	@Override 
	public String toString()
	{
		return "";
	}

	/**
	 * Calculate legal moves for a given set of pieces
	 * @param pieces
	 * @return
	 */
	private Collection<Move> calculateLegalMove(final Collection<Piece> pieces) 
	{
		final List<Move> legalMove = new ArrayList<>();

		//loop through pieces and calculate legal moves for current board and add the legal moves
		//This might throw error if legalMoves not implemented for a piece type
		if(pieces.size() > 0)
		{
			for(final Piece p : pieces)
			{
				legalMove.addAll(p.calculateLegalMoves(this));
			}
		}
		return ImmutableList.copyOf(legalMove);
	}

	//uses tiles and association to make calculations and keep track of black and white pieces
	private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance association) 
	{
		List<Piece> activePieces = new ArrayList<>();

		for(final Tile t : gameBoard)
		{
			//if tile is occupied get its piece
			//and if the piece's association is = association passed in, then add to active pieces
			if(t.isTileOccupided())
			{
				final Piece piece = t.getPiece();
				if(piece.getPieceAssociation() == association)
				{
					activePieces.add(piece);
				}
			}
		}
		return ImmutableList.copyOf(activePieces);
	}


	private static List<Tile> createGameBoard(final Builder builder)
	{
		//populate a list of tiles 0 - 48
		final Tile[] tiles = new Tile[BoardUtilities.NUM_TILES];

		//map a piece onto tile id
		for(int i = 0; i < BoardUtilities.NUM_TILES; i++)
		{
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}

	/*build the initial board setting pieces where they go at start
	according to board in wiki
	Might have to come back to make the 3x3 white squares, or figure out how to handle that*/
	public static Board createStandardBoard()throws Exception
	{
		final Builder builder = new Builder();

		//Set the Black Pieces for Rook 
		builder.setPiece(new Rook(2, Alliance.BLACK));
		builder.setPiece(new Rook(9, Alliance.BLACK));
		//Set the Black Pieces for Bishop
		builder.setPiece(new Bishop(3, Alliance.BLACK));
		//Set the Black Pieces for King
		builder.setPiece(new King(10, Alliance.BLACK));
		//Set the Black Pieces for Pawn
		builder.setPiece(new Pawn(4, Alliance.BLACK));
		builder.setPiece(new Pawn(11, Alliance.BLACK));


		//Set the White Pieces for Rook
		builder.setPiece(new Rook(39, Alliance.WHITE));
		builder.setPiece(new Rook(46, Alliance.WHITE));
		//Set the White Pieces for Bishop
		builder.setPiece(new Bishop(45, Alliance.WHITE));
		//Set the White Pieces for King
		builder.setPiece(new King(38, Alliance.WHITE));
		//Set the White Pieces for Pawn
		builder.setPiece(new Pawn(37, Alliance.WHITE));
		builder.setPiece(new Pawn(44, Alliance.WHITE));

		//set turn to White player initially
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}


	public Tile getTile(int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}

	/**
	 * @author kb
	 *building instance of a board
	 */
	public static class Builder 
	{
		//map tile id to a given piece on that tile id
		Map<Integer, Piece> boardConfig;

		//keep track of which player's turn it is to move
		Alliance nextMoveMaker;

		public Builder()
		{
			this.boardConfig = new HashMap<>();
		}

		//setting property of the current builder and returning it back to where ever it was called from
		public Builder setPiece(final Piece piece)
		{
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}

		//setting property of the current builder and returning it back to where ever it was called from
		public Builder setMoveMaker(final Alliance nextMoveMaker)
		{
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}

		public Board build()throws Exception
		{
			return new Board(this);
		}
	}

	public Player whitePlayer()
	{
		return this.white;
	}

	public Player blackPlayer()
	{
		return this.black;
	}

	public Collection<Piece> getBlackPieces() 
	{
		return this.blackPieces;
	}

	public Collection<Piece> getWhitePieces() 
	{
		return this.whitePieces;
	}

	public Player currentPlayer() {
		return this.currentPlayer;
	}

	//return an iterable of both players moves
	public Iterable<Move> getAllLegalMoves() {
		Iterable<Move> obj =  Iterables.unmodifiableIterable(Iterables.concat(this.white.getLegalMoves(), this.black.getLegalMoves()));
		return obj;
	}
	
	

}
