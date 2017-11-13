package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.util.Arrays;
import java.util.List;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece.PieceType;


public class Game {
		
	String creator;
	String opponent;
	int status;
	boolean userLeft;
	String save;
	Config conf;
	
		public Game(String creator, String opponent, int status) {
			this.creator = creator;
			this.opponent = opponent;
			this.status = status;

			
		}
		
	
		
		///send to server
		public void breakDownBoard(Board board) {
			System.out.println("BREAKDOWN BOARD");
			List<Tile> tiles = board.getGameBoard();
			int [] boardList = new int[48];
			//System.out.println("INVITER:  " + creator + " ACCEPTOR" + opponent);
			for(Tile t: tiles) {
				if(t.isTileOccupided())
				{
					int idNum = 0;
					//System.out.println("GAME BOARD " + t.getTileCoord() + " " + t.isTileOccupided() + " " +
							//t.getPiece().getPieceAssociation() + " " + t.getPiece()); //DELETE!!!
					 
					
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
			System.out.println("PRRRRRRRRINT");
			for(int i = 0 ; i < boardList.length; i++)
			{
				System.out.print(boardList[i]);
				
			}
			System.out.println();	
		}

		
		public void userLeft() {
			System.out.println("USER LEFT GAME");
			userLeft = true;
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
		
		

		public void updateStatus(int status) {
			this.status = status;
		}
		
		/*public boolean insertSave() {
			return conf.insertSavedGame(creator.getUserName(), opponent.getUserName(), this.status);
		}*/
		
		public String getOpponent() {
			return opponent;
		}
		
		public String getCreator() {
			return creator;
		}
		
		public int getStatus() {
			return status;
		}
		
		public void setCreator(String creator) {
			this.creator = creator;
		}
		
		public void setOpponent(String opponent) {
			this.opponent = opponent;
		}
}
