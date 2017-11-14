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
		
	};
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	public abstract Player playersTurn(WhitePlayer white, BlackPlayer black); //determine who's turn it is
}
