package edu.colostate.cs.cs414.teamthebestteam.rollerball.technicalservice.databaseconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private Connection connection;
	private Statement stmt;

	public DatabaseConnection() {
		try {
			String connURL = "jdbc:mysql://localhost:3306/rollerball?useSSL=false";
			this.connection = DriverManager.getConnection(connURL, "root", "password");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Statement getStatement(){
		return this.stmt;
	}
}
