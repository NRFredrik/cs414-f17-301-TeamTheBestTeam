package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public final class OccupiedTile extends Tile{
	Piece pieceOnTile;
	OccupiedTile(int tileCoordinate, Piece pieceOnTile) {		
		super(tileCoordinate);
		this.pieceOnTile = pieceOnTile;
	}
	@Override
	public boolean isTileOccupided() {
		return true;
	}
	@Override
	public Piece getPiece() {
		return this.pieceOnTile;
	}
	
	

}
