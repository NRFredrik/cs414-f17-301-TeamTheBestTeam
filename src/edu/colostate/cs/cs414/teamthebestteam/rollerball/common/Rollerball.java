package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gui.Table;


public class Rollerball {
	public static void main(String[] args) {
		//this is board froma starting position
		Board board = Board.createStandardBoard();
		//print board. Takes advantage of tosting() for each of the pieces 
		//to print this string representation of the board
		
		System.out.println(board);
		
		//Define the table to be a 600X600 table and is visibkle right now
		@SuppressWarnings("unused")
		Table table = new Table();
	}

}
