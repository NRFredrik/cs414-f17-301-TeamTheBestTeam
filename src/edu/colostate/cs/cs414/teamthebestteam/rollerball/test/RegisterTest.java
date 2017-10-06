package edu.colostate.cs.cs414.teamthebestteam.rollerball.test;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;


import org.junit.Before;
import org.junit.Test;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.HashPassword;

public class RegisterTest {
/**
 * Database variables
 */
	Connection con;
	Statement stmt;
	ResultSet result;
	Config conf;
	
	/**
	 * Use for Hashing
	 */
	HashPassword hash;
	
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
			this.hash = new HashPassword();
			conf = new Config();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author kb
	 * Test to make ensure that no user can register when using an existing email
	 */
	@Test
	public void addUserDuplicateEmail() 
	{
		//add a user to the database
		String uname = "testName";
		String mail = "test@test.com";
		String hashedPass = null;
		try {
			hashedPass = hash.hashPassword("password");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		boolean addedUser = conf.addNewUser(uname, mail, hashedPass);
		
		/**
		 * if user was added successfully, addedUser will be true
		 * and we can proceed with adding another user to the database using the same email
		 */
		if(addedUser)
		{
			String uname2 = "testName2";
			String mail2 = "test@test.com";
			String hashedPass2 = null;
			try {
				hashedPass2 = hash.hashPassword("password");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			//this boolean should have value of 0 indicating the add failed
			boolean addedUser2 = conf.addNewUser(uname2, mail2, hashedPass2);
			//remove test user from database
			conf.removeUser(mail);
			
			assertEquals(false, addedUser = addedUser2);
		}
	}

}
