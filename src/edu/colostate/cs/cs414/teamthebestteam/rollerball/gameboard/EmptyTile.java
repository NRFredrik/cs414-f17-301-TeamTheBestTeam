package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public final class EmptyTile extends Tile{
	EmptyTile(final int coordinate){
		super(coordinate);
	}

	@Override
	public boolean isTileOccupided() {
		return false;
	}

	@Override
	public Piece getPiece() {
		return null;
	}
}
