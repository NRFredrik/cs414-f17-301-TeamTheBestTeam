package edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.pieces;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.BlackPlayer;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.Player;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.player.WhitePlayer;

//enum for black or white piece
public enum Alliance {
	/**
	 * To start off, white travels up the board in a "negative" direction
	 * and black is the opposite We can add logic from here
	 */
	WHITE
	{
		@Override
		public boolean isWhite() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isBlack() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Player playersTurn(WhitePlayer white, BlackPlayer black) {
			return white;
		}

		@Override
		public boolean isPawnPromotionSquare(int position) {
			if(position == 4 || position == 11){
				return true;
			}
			return false;
		}
	},
	
	BLACK
	{

		@Override
		public boolean isWhite() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isBlack() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public Player playersTurn(WhitePlayer white, BlackPlayer black) {
			return black;
		}

		@Override
		public boolean isPawnPromotionSquare(int position) {
			if(position == 44 || position == 37){
				return true;
			}
			return false;
		}
		
	};
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	public abstract Player playersTurn(WhitePlayer white, BlackPlayer black); //determine who's turn it is
	public abstract boolean isPawnPromotionSquare(int position);
}
