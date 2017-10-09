package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public abstract class Tile {
	int tileCoordinate;
	
	Tile(int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	public abstract boolean isTileOccupided();
	
	public abstract Piece getPiece();
	
	
}
