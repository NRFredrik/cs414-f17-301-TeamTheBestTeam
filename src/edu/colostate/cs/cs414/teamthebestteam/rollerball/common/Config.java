package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Config {
	
	Connection conn;
	Statement stmt;
	ResultSet result;
	int res;
	
	public Config()
	{
		try {
			String connURL = "jdbc:mysql://localhost:3306/Rollerball?useSSL=false";
			this.conn = DriverManager.getConnection(connURL, "root", "password");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author kb
	 * @param s
	 * @return true if the email entered is unique
	 */
	public boolean isUniqueEmail(String s) 
	{
		//execute query
		try {
			result = stmt.executeQuery("select email from users where email = '"+ s + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(result.next())
			{
				System.out.println("The email "+ result.getString("email") + " is not unique");
				return false; //email found in database, so it's not unique
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true; //email was unique
	}
	
	/**
	 * 
	 *@author kb
	 * @param username
	 * @param email
	 * @param password
	 * @return true if user was added to database successfully 
	 */
	public boolean addNewUser(String username, String email, String password) 
	{
		if(isUniqueEmail(email))
		{
			String addUser = "INSERT INTO users " + "VALUES (NULL,'"+username+"','"+email+"','"+password+"')";
			//insert the user into the database
			try {
				int effected = stmt.executeUpdate(addUser);
				//if there were rows affected that means user was added
				if(effected != 0)
				{
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * @author kb
	 * @param email
	 * remove a user using the user's email
	 */
	public void removeUser(String email)
	{
		String delete_him = "DELETE FROM `Rollerball`.`users` WHERE `email`='"+email+"';";
		try {
			stmt.executeUpdate(delete_him);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
