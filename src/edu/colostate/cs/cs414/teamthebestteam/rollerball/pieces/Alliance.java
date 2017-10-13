package edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces;

//enum for black or white piece
public enum Alliance {
	/**
	 * To start off, white travels up the board in a "negative" direction
	 * and black is the opposite We can add logic from here
	 */
	WHITE
	{
		@Override
		public int getDirection()
		{
			return -1;
		}
	},
	
	BLACK
	{
		@Override
		public int getDirection()
		{
			return 1;
		}
	};
	
	//need a way to determine direction
	public abstract int getDirection();
}
