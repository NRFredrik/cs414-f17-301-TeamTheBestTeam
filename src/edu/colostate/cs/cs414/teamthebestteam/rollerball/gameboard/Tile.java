package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.pieces.Piece;

public abstract class Tile {
	//can only be set at construction and is only visible to sub classes
	protected final int tileCoordinate;
	
	//empty tiles cache
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();
	
	//this will be the only way to create a tile
	//this will return new Occupied tile, or a cashed empty tile
	public static Tile createTile(final int tileCoordinate, final Piece piece){
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
	}
	
	private Tile(int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer,EmptyTile> emptyTileMap = new HashMap<>();
		for(int i = 0; i < 40; i++){
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return ImmutableMap.copyOf(emptyTileMap);		
	}

	public abstract boolean isTileOccupided();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile{
		private EmptyTile(final int coordinate){
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
	
	public static final class OccupiedTile extends Tile{
		//cannot be referenced without getter
		private final Piece pieceOnTile;
		
		private OccupiedTile(final int tileCoordinate, Piece pieceOnTile) {		
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
	
}
