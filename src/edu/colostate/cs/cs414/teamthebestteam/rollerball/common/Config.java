package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Config {
	
	Connection conn;
	Statement stmt;
	ResultSet result;
	//-----------
	ResultSet result2;
	//------------
	int res;
	
	public Config()
	{
		try {
			String connURL = "jdbc:mysql://localhost:3306/rollerball?useSSL=false";
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
	 * @author kb
	 * @param s
	 * @return true if the username entered is unique
	 */
	public boolean isUniqueUser(String s) 
	{
		//execute query
		try {
			result = stmt.executeQuery("select nick_name from users where nick_name = '"+ s + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(result.next())
			{
				System.out.println("The user "+ result.getString("nick_name") + " is not unique");
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
		if(isUniqueEmail(email) && isUniqueUser(username))
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
	
	/**
	 * @param username
	 * @param password
	 * @return true if user is found in database
	 * Check to see if user has registered to our system and if password matches
	 */
	public boolean userExists(String username, String password)
	{
		String query = "Select nick_name,password from users where nick_name = '"+username+"';"; //returns user and hashed password
		try {
			result = stmt.executeQuery(query);
			while(result.next())
			{
				//check to see if password user entered matches password in database
				String hashedPass = "";
				try {
					hashedPass = HashPassword.hashPassword(password); //get has of password that was entered in login
					System.out.println(hashedPass);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				if(hashedPass.equals(result.getObject("password")))
				{
					return true; //true if user exists and password matches
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/*
	 * Create game record:
	 * this function will add game in progress record to the database
	 * there are no winner or loser yet since the game is in progress. Therefore, winner and loser are set to NULL
	 * the game status is set to in progress
	 * @param gameCreator
	 * @param gameOpponent
	 * 
	 */
	
	public boolean createGameRecord(String gameCreator, String gameOpponent){

		String status = "inProgress";
		
		//formatting the date to match the datetime format in mysql database
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date();
		String startDate = dateFormat.format(curDate);
		
		//insert the record into the database
		String addRecord = "INSERT INTO record " + "VALUES (NULL,'"+gameCreator+"','"+gameOpponent+"','"+status+"','"+startDate+"', NULL,'"+"ladji"+"',NULL)";
		
		//System.out.println("*********addRecord: "+addRecord);
		
		try {
			int effected = stmt.executeUpdate(addRecord);
			//if there were rows affected that means record was added
			if(effected != 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return false;
	}
	
	
	
	
	
	/*
	 * get a particular user game history
	 * 	@param user
	 * return a ArrayList of the user game history
	 */
	
	public ArrayList<String> getUserGameHistory(String user) 
	{
		// create an array list
	    ArrayList<String> userGameHistoryList = new ArrayList<String>();
		int counter = 0;
		String opponent = "";
		String startDate ="";
		String endDate ="";
		String winner ="";
		String loser ="";
		String status ="";
		
		String gameHistory ="";

//*************************This get executed if the user created a game*********************************
		//execute query
		try {
			 result = stmt.executeQuery("select opponent,startDate,endDate,winner,loser,status,creator from record where creator = '"+ user + "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			
			while(result.next())
			{
				opponent = result.getString("opponent");
				//System.out.println("*****************Opponent: "+ opponent);
				
	
				startDate = result.getString("startDate");
				//System.out.println("*****************startDate: "+ startDate);
				
				endDate = result.getString("endDate");
				//System.out.println("*****************endDate: "+ endDate);

				winner = result.getString("winner");
				//System.out.println("*****************winner: "+ winner);
				
				loser = result.getString("loser");
				//System.out.println("*****************loser: "+ loser);

				status = result.getString("status");
				//System.out.println("*****************status: "+ status);
				
				gameHistory = opponent+";"+startDate+";"+endDate+";"+winner+";"+loser+";"+status;
				//System.out.println("****************gameHistory: "+gameHistory);
				
				userGameHistoryList.add(counter,gameHistory);
				
				counter++;
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
//*************************This get executed if the user is an opponent of a game*********************************
		
		//execute query
			try {
				 result2 = stmt.executeQuery("select creator,startDate,endDate,winner,loser,status from record where opponent = '"+ user + "';");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
		
		try {
			
			while(result2.next())
			{
				opponent = result2.getString("creator");
				//System.out.println("*****************Opponent: "+ opponent);
				
	
				startDate = result2.getString("startDate");
				//System.out.println("*****************startDate: "+ startDate);
				
				endDate = result2.getString("endDate");
				//System.out.println("*****************endDate: "+ endDate);

				winner = result2.getString("winner");
				//System.out.println("*****************winner: "+ winner);
				
				loser = result2.getString("loser");
				//System.out.println("*****************loser: "+ loser);

				status = result2.getString("status");
				//System.out.println("*****************status: "+ status);
				
				gameHistory = opponent+";"+startDate+";"+endDate+";"+winner+";"+loser+";"+status;
				//System.out.println("****************gameHistory: "+gameHistory);
				
				userGameHistoryList.add(counter,gameHistory);
				
				counter++;
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return userGameHistoryList;  
	}
	
	
	
	
	
	
	
}

