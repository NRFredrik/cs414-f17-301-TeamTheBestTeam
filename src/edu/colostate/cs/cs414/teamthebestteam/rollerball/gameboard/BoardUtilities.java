package edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard;

import java.util.ArrayList;
import java.util.List;

public class BoardUtilities {
	
	//Constants for Boundary Exclusionary Cases with Movements
	//All the tile coordinates for the outer ring of the board
	public static final boolean[] OUTER_RING = null;
	//All the tile coordinates for the inner ring of the board
	public static final boolean[] INNER_RING = null;
	//All the tile coordinates where forward = to the right
	public static final boolean[] QUADRANT_ONE = null;
	//All the tile coordinates where moving forward is down
	public static final boolean[] QUADRANT_TWO = null;
	//All the tile coordinates where moving forward is to the left
	public static final boolean[] QUADRANT_THREE = null;
	//All the tile coordinates where moving forward is up
	public static final boolean[] QUADRANT_FOUR = null;
	
	
	private BoardUtilities(){
		throw new RuntimeException("this is private. you cannot instantiate this object.");
		
	}
	
	public static boolean isValidTileCoordinate(int tileCoordinate){
		//will look into making this cleaner
		if(tileCoordinate >= 0 && tileCoordinate <=48 && tileCoordinate != 16 && tileCoordinate != 17 
				&& tileCoordinate != 18 && tileCoordinate != 23 && tileCoordinate != 24 && tileCoordinate != 25
				&& tileCoordinate != 30 && tileCoordinate != 31 && tileCoordinate != 32){
			return true;
		} else {
			return false;
		}
	}
}
