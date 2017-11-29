package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;


import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Alliance;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Bishop;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.King;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Pawn;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Rook;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces.Piece.PieceType;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.BlackPlayer;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.Player;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.WhitePlayer;



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
		if(builder.nextMoveMaker == null) {
			System.out.println("builder NEXT MOVE MAKER NULLLLLLLLLLLLLLLLLLLLLLLLLL");
		}
		if(this.white == null) {
			System.out.println("WHITE NULL");
		}
		if(this.black == null) {
			System.out.println("BLACK NULL");
		}
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
		//System.out.println("getting tile?");
		//System.out.println(gameBoard.get(tileCoordinate).toString());
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
		Move transitionMove;

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
		
		public Builder setMoveMakerString(String color) {
			if(color.equals("white")) {
				return setMoveMaker(Alliance.WHITE);
			}
			else {
				return setMoveMaker(Alliance.BLACK);
			}
		}

		//setting property of the current builder and returning it back to where ever it was called from
		public Builder setMoveMaker(final Alliance nextMoveMaker)
		{
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Builder setMoveTransition(final Move transitionMove) {
            this.transitionMove = transitionMove;
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
	public final List<Tile> getGameBoard(){
		return this.gameBoard;
	}
	
	public String breakDownBoard(Board board) {
		System.out.println("BREAKDOWN BOARD");
		List<Tile> tiles = board.getGameBoard();
		int [] boardList = new int[49];
		//System.out.println("INVITER:  " + creator + " ACCEPTOR" + opponent);
		for(Tile t: tiles) {
			if(t.isTileOccupided())
			{
				int idNum = 0;
				System.out.println("GAME BOARD " + t.getTileCoord() + " " + t.isTileOccupided() + " " +
						t.getPiece().getPieceAssociation() + " " + t.getPiece()); //DELETE!!!
				 
				
				if(t.getPiece().getColor().isWhite()) {
					idNum = getPieceId(t.getPiece().getPieceType());
				}
				else { //its black so add 4 to it
					idNum = 4 + getPieceId(t.getPiece().getPieceType());
				}
				
				System.out.println("coord" + t.getTileCoord() + " idNum " + idNum);
				boardList[t.getTileCoord()] = idNum;
			}
		}
		StringBuilder sb = new StringBuilder();
		
		System.out.println("PRRRRRRRRINT");
		for(int i = 0 ; i < boardList.length; i++)
		{
		
			sb.append(boardList[i]);
			
		}
	return sb.toString();	
	}
	
	public static Board rebuildBoard(String serialBoard,String turn) throws Exception {
		
		char cary[] = serialBoard.toCharArray();
		Builder builder = new Builder();
		for(int i=0; i < cary.length;i++) {
			System.out.println(cary[i]);
			Piece newPiece = getPiecefromNum(cary[i],i);
			if(newPiece != null) {
				builder.setPiece(getPiecefromNum(cary[i],i));
				
				builder.setMoveMakerString(turn);
			}
			
			
			
		}

		return builder.build();
	}
	 private static Piece getPiecefromNum(char piece, int position) {
		 Piece newPiece;
		 switch(piece) {
			case '1':
				///builder.setPiece(new Rook(2, Alliance.BLACK));
				newPiece = new Pawn(position,Alliance.WHITE);
				break;
			case '2':
				newPiece = new Bishop(position,Alliance.WHITE);
				break;
			case '3':
				newPiece = new Rook(position,Alliance.WHITE);
				break;
			case '4':
				newPiece = new King(position,Alliance.WHITE);
				break;
			case '5':
				newPiece = new Pawn(position,Alliance.BLACK);
				break;
			case '6':
				newPiece = new Bishop(position,Alliance.BLACK);
				break;
			case '7':
				newPiece = new Rook(position,Alliance.BLACK);
				break;
			case '8':
				newPiece = new King(position,Alliance.BLACK);
				break;
			default:
				return null;
			
			
			}
		 return newPiece;
	 }
	
		private static  char[] stringtoArray(String intString) {
		    
		    char ary[] = intString.toCharArray();
		    return ary;
		}
	

	private int getPieceId(PieceType pieceType) {
		String type = pieceType.toString();
		switch(type) {
		case "pawn": return 1;
		case "bishop":return 2;
		case "rook": return 3;
		case "king": return 4;
		default: return 0;
		}
		
	}
	
	

}
