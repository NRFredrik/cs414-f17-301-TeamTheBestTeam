package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public abstract class Tile {
	//can only be set at construction and is only visible to sub classes
	protected final int tileCoordinate;
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();
	
	public static Tile createTile(final int tileCoordinate, final Piece piece){
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
	}
	
	private Tile(int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer,EmptyTile> emptyTileMap = new HashMap<>();
		for(int i = 0; i < 40; i++){
			emptyTileMap.put(i,new EmptyTile(i));
		}
		return ImmutableMap.copyOf(emptyTileMap);		
	}

	public abstract boolean isTileOccupided();
	
	public abstract Piece getPiece();
	
	
}
