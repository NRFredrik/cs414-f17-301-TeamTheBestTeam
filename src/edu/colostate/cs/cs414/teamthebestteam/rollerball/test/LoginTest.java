package edu.colostate.cs.cs414.teamthebestteam.rollerball.test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.HashPassword;

public class LoginTest {
	/**
	 * Database variables
	 */
	Connection con;
	Statement stmt;
	ResultSet result;
	Config conf;

	@Before
	public void initialize()
	{
		/**
		 * @author kb
		 * establish a connection to database and setup a statement object
		 */
		try {
			String connURL = "jdbc:mysql://localhost:3306/Rollerball?useSSL=false";
			this.con = DriverManager.getConnection(connURL, "root", "password");
			this.stmt = con.createStatement();
			conf = new Config();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author kb
	 * checks to make sure verification of user works.
	 * This test has non existent user
	 */
	@Test
	public void notLoggedInFalseUser() 
	{
		//add a unique user to the database
		String uname = "testName";
		String hashedPass = null;
		try {
			hashedPass = HashPassword.hashPassword("password");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		boolean addedUser = conf.userExists(uname, hashedPass);
		assertEquals(false, addedUser);
	}

	/**
	 * @author kb
	 * checks to make sure verification of user works.
	 * This test has good user but wrong password
	 */
	@Test
	public void notLoggedInFalsePassword() 
	{
		//add a unique user to the database
		String uname = "kbthetruth";
		String hashedPass = null;
		try {
			hashedPass = HashPassword.hashPassword("pass");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		boolean addedUser = conf.userExists(uname, hashedPass);
		assertEquals(false, addedUser);
	}

	/**
	 * @author kb
	 * checks to make sure verification of user works.
	 * This test has good user and good password
	 */
	@Test
	public void LoggedIn() 
	{

		//add a user to the database
		String uname = "testName";
		String mail = "test@test.com";
		String hashedPass = null;
		try {
			hashedPass = HashPassword.hashPassword("password");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		boolean addedUser = conf.addNewUser(uname, mail, hashedPass);

		String uname2 = "testName";
		String hashedPass2 = "password";

		boolean addedUser2 = conf.userExists(uname2, hashedPass2);
		assertEquals(true, addedUser2);
		conf.removeUser("test@test.com");	 //remove test user from database
	}

}
