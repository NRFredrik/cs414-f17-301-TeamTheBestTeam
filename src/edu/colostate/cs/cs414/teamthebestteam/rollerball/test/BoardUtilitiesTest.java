package edu.colostate.cs.cs414.teamthebestteam.rollerball.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardUtilitiesTest {

	//list/create member variables here so rest of test suite can use them
	
	@Before
	public void initialize()
	{
		//initialize member variables here so rest of test suite can use them
	}
	
	/**
	 * Should only pass if piece is on a corner tile (0,6,48,42)
	 */
	@Test
	public void isCornerReboundRingTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should fail if piece is NOT on a corner tile (0,6,48,42)
	 */
	@Test
	public void isNotCornerReboundRingTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should fail if piece is NOT on his starting position per wiki rules/setup
	 */
	@Test
	public void getWrongStartingPositionTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if piece is on his starting position per wiki rules/setup
	 */
	@Test
	public void getCorrectStartingPositionTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if tile coordinate falls in the 3x3 of unusable tiles per wiki rules/setup
	 */
	@Test
	public void isNotUsableTileTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if tile coordinate does NOT falls in the 3x3 of unusable tiles per wiki rules/setup
	 */
	@Test
	public void isUsableTileTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if candidate tile coordinate goes out of bounds/domain of board per wiki rules/setup
	 */
	@Test
	public void isNotValidTileCoordinateTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if candidate tile coordinate does NOT out of bounds/domain of board per wiki rules/setup
	 */
	@Test
	public void isValidTileCoordinateTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if tiles in the given column have values that are true/initialized
	 */
	@Test
	public void initColumnTest() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Should pass if tiles in the given row have values that are true/initialized
	 */
	@Test
	public void initRowTest() 
	{
		fail("Not yet implemented");
	}

}
