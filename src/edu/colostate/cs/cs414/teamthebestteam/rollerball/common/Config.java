package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Config {
	
	public Config()
	{
		
	}
	
	public void Connect()
	{
		try {
			//get database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Rollerball", "root", "password");
			
			//create statement 
			Statement stmt = conn.createStatement();
			//execute query
			ResultSet result = stmt.executeQuery("select * from users");
			
			while(result.next())
			{
				System.out.println(result.getString("nick_name") +" "+ result.getString("email"));
			}
			
			//process result set
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
