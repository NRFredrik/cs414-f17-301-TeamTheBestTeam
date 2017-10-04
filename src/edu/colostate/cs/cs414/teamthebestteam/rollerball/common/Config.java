package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Config {
	
	Connection conn;
	Statement stmt;
	ResultSet result;
	int res;
	
	public Config()
	{
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Rollerball", "root", "password");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
