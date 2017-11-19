package edu.colostate.cs.cs414.teamthebestteam.rollerball.application.manageuser;

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

import edu.colostate.cs.cs414.teamthebestteam.rollerball.technicalservice.databaseconnector.DatabaseConnection;

public class ManageUser {
	DatabaseConnection db;
	ResultSet result;
	// -----------
	ResultSet result2;
	// ------------
	int res;

	public ManageUser(DatabaseConnection db) {
		this.db = db;
	}

	/**
	 * @author kb
	 * @param s
	 * @return true if the email entered is unique
	 */
	public boolean isUniqueEmail(String s) {
		// execute query
		try {
			result = db.getStatement().executeQuery("select email from users where email = '" + s + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (result.next()) {
				System.out.println("The email " + result.getString("email") + " is not unique");
				return false; // email found in database, so it's not unique
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true; // email was unique
	}

	/**
	 * @author kb
	 * @param s
	 * @return true if the username entered is unique
	 */
	public boolean isUniqueUser(String s) {
		// execute query
		try {
			result = this.db.getStatement().executeQuery("select nick_name from users where nick_name = '" + s + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (result.next()) {
				System.out.println("The user " + result.getString("nick_name") + " is not unique");
				return false; // email found in database, so it's not unique
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true; // email was unique
	}

	/**
	 * 
	 * @author kb
	 * @param username
	 * @param email
	 * @param password
	 * @return true if user was added to database successfully
	 */
	public boolean addNewUser(String username, String email, String password) {
		if (isUniqueEmail(email) && isUniqueUser(username)) {
			String addUser = "INSERT INTO users " + "VALUES (NULL,'" + username + "','" + email + "','" + password
					+ "')";
			// insert the user into the database
			try {
				int effected = this.db.getStatement().executeUpdate(addUser);
				// if there were rows affected that means user was added
				if (effected != 0) {
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
	 *            remove a user using the user's email
	 */
	public void removeUser(String email) {
		String delete_him = "DELETE FROM `Rollerball`.`users` WHERE `email`='" + email + "';";
		try {
			int res = this.db.getStatement().executeUpdate(delete_him);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param username
	 * @param password
	 * @return true if user is found in database Check to see if user has
	 *         registered to our system and if password matches
	 */
	public boolean userExists(String username, String password) {
		String query = "Select nick_name,password from users where nick_name = '" + username + "';"; // returns
																										// user
																										// and
																										// hashed
																										// password
		try {
			result = this.db.getStatement().executeQuery(query);
			while (result.next()) {
				// check to see if password user entered matches password in
				// database
				String hashedPass = "";
				try {
					hashedPass = HashPassword.hashPassword(password); // get has
																		// of
																		// password
																		// that
																		// was
																		// entered
																		// in
																		// login
					//System.out.println(hashedPass);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				if (hashedPass.equals(result.getObject("password"))) {
					return true; // true if user exists and password matches
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean userExistsEmail(String email, String password) {
		String query = "Select nick_name,password from users where email = '" + email + "';"; // returns
																								// user
																								// and
																								// hashed
																								// password
		try {
			result = this.db.getStatement().executeQuery(query);
			while (result.next()) {
				// check to see if password user entered matches password in
				// database
				String hashedPass = "";
				try {
					hashedPass = HashPassword.hashPassword(password); // get has
																		// of
																		// password
																		// that
																		// was
																		// entered
																		// in
																		// login
					//System.out.println(hashedPass);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				if (hashedPass.equals(result.getObject("password"))) {
					return true; // true if user exists and password matches
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Create game record: this function will add game in progress record to the
	 * database there are no winner or loser yet since the game is in progress.
	 * Therefore, winner and loser are set to NULL the game status is set to in
	 * progress
	 * 
	 * @param gameCreator
	 * 
	 * @param gameOpponent
	 * 
	 */

	public ArrayList<String> populateUserList() {
		ArrayList<String> userList = new ArrayList<String>();
		String userQuery = "select * from users;";
		try {
			result = this.db.getStatement().executeQuery(userQuery);
			while (result.next()) {
				userList.add(result.getString("nick_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public boolean addInvite(String sender, String reciever) {
		boolean sendInvite = false;

		ArrayList<String> userList = new ArrayList<String>();
		String userQuery = "select * from invites WHERE `reciever`='" + reciever
				+ "' AND `status` = 'pending' AND `sender` = '" + sender + "';";
		try {
			result = this.db.getStatement().executeQuery(userQuery);
			while (result.next()) {
				userList.add(result.getString("sender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (userList.size() < 1) {
			sendInvite = true;
		}

		if (sendInvite) {
			String addInvite = "INSERT INTO invites " + "VALUES (NULL,'" + sender + "','" + reciever
					+ "',NULL , 'pending')";
			// insert the user into the database
			try {
				int effected = this.db.getStatement().executeUpdate(addInvite);
				// if there were rows affected that means user was added
				if (effected != 0) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean acceptInviteDB(String sender, String reciever) {

		String updateInvite = "UPDATE invites SET `status` = 'accepted' WHERE `reciever`='" + reciever
				+ "' AND `sender` = '" + sender + "';";
		//System.out.println(updateInvite);

		// insert the user into the database
		try {
			int effected = this.db.getStatement().executeUpdate(updateInvite);
			// if there were rows affected that means user was added
			if (effected != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean declineInviteDB(String sender, String reciever) {

		String updateInvite = "UPDATE invites SET `status` = 'declined' WHERE `reciever`='" + reciever
				+ "' AND `sender` = '" + sender + "';";
		// insert the user into the database
		try {
			int effected = this.db.getStatement().executeUpdate(updateInvite);
			// if there were rows affected that means user was added
			if (effected != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public ArrayList<String> populateInviteList(String reciever) {
		ArrayList<String> userList = new ArrayList<String>();
		String userQuery = "select * from invites WHERE `reciever`='" + reciever + "' AND `status` = 'pending';";
		try {
			result = db.getStatement().executeQuery(userQuery);
			while (result.next()) {
				userList.add(result.getString("sender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public String createGameRecord(String gameCreator, String gameOpponent) {

		String status = "inProgress";

		// formatting the date to match the datetime format in mysql database
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date();
		String startDate = dateFormat.format(curDate);

		// insert the record into the database
		String addRecord = "INSERT INTO record " + "VALUES (NULL,'" + gameCreator + "','" + gameOpponent + "','"
				+ status + "','" + startDate + "', NULL,NULL,NULL);";

		//System.out.println("*********addRecord: " + addRecord);

		try {
			int effected = this.db.getStatement().executeUpdate(addRecord);
			// if there were rows affected that means record was added

			if (effected != 0) {
				return startDate;
			}

			return startDate;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return startDate;
	}

	public int getGameRecordID(String gameCreator, String gameOpponent) {
		// insert the record into the database
		String getID = "Select recordID from record WHERE `creator`='" + gameCreator + "' AND `opponent`='"
				+ gameOpponent + "' AND `status` = 'inProgress';";
		//System.out.println(getID);
		try {
			result = this.db.getStatement().executeQuery(getID);
			// if there were rows affected that means record was added
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int recordID = 0;
		try {
			while (result.next()) {
				recordID = result.getInt(1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("recordID: " + recordID);

		return recordID;
	}

	public boolean finishGameRecord(String gameCreator, String gameOpponent) {

		String updateInvite = "UPDATE record SET `status` = 'finished', `winner`='" + gameCreator + "', `loser`='"
				+ gameOpponent + "' WHERE `creator`='" + gameCreator + "' AND `opponent` = '" + gameOpponent + "';";

		// insert the user into the database
		try {
			int effected = this.db.getStatement().executeUpdate(updateInvite);
			// if there were rows affected that means user was added
			if (effected != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean saveState(String query) {
		try {
			int effected = this.db.getStatement().executeUpdate(query);
			// if there were rows affected that means record was added
			if (effected != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public String retrievePiece(String query, String piece) {
		try {
			result = this.db.getStatement().executeQuery(query);
			while (result.next()) {
				return result.getString(piece);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * get a particular user game history
	 * 
	 * @param user return a ArrayList of the user game history
	 */

	public ArrayList<String> getUserGameHistory(String user) {
		// create an array list
		ArrayList<String> userGameHistoryList = new ArrayList<String>();
		int counter = 0;
		String opponent = "";
		String startDate = "";
		String endDate = "";
		String winner = "";
		String loser = "";
		String status = "";

		String gameHistory = "";

		// *************************This get executed if the user created a
		// game*********************************
		// execute query
		try {
			result = this.db.getStatement().executeQuery(
					"select opponent,startDate,endDate,winner,loser,status,creator from record where creator = '" + user
							+ "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			while (result.next()) {
				opponent = result.getString("opponent");
				// System.out.println("*****************Opponent: "+ opponent);

				startDate = result.getString("startDate");
				// System.out.println("*****************startDate: "+
				// startDate);

				endDate = result.getString("endDate");
				// System.out.println("*****************endDate: "+ endDate);

				winner = result.getString("winner");
				// System.out.println("*****************winner: "+ winner);

				loser = result.getString("loser");
				// System.out.println("*****************loser: "+ loser);

				status = result.getString("status");
				// System.out.println("*****************status: "+ status);

				gameHistory = opponent + ";" + startDate + ";" + endDate + ";" + winner + ";" + loser + ";" + status;
				// System.out.println("****************gameHistory:
				// "+gameHistory);

				userGameHistoryList.add(counter, gameHistory);

				counter++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// *************************This get executed if the user is an opponent
		// of a game*********************************

		// execute query
		try {
			result2 = this.db.getStatement()
					.executeQuery("select creator,startDate,endDate,winner,loser,status from record where opponent = '"
							+ user + "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			while (result2.next()) {
				opponent = result2.getString("creator");
				// System.out.println("*****************Opponent: "+ opponent);

				startDate = result2.getString("startDate");
				// System.out.println("*****************startDate: "+
				// startDate);

				endDate = result2.getString("endDate");
				// System.out.println("*****************endDate: "+ endDate);

				winner = result2.getString("winner");
				// System.out.println("*****************winner: "+ winner);

				loser = result2.getString("loser");
				// System.out.println("*****************loser: "+ loser);

				status = result2.getString("status");
				// System.out.println("*****************status: "+ status);

				gameHistory = opponent + ";" + startDate + ";" + endDate + ";" + winner + ";" + loser + ";" + status;
				// System.out.println("****************gameHistory:
				// "+gameHistory);

				userGameHistoryList.add(counter, gameHistory);

				counter++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userGameHistoryList;
	}

	// insert save into db, return the id
	public void insertSavedGame(String gameID, String gameBoard, String turn) {

		//System.out.println("TURN IS NOW" + turn);

		//System.out.println("SETTING TURN TO : " + turn + " in DB");
		String saveStmnt = "UPDATE saves SET game = '" + gameBoard + "',turn = '" + turn + "',isNew = 0 WHERE savesId ="
				+ gameID + ";";
		// insert the user into the database
		// System.out.println("UPDATED GAME: " + gameID);
		try {
			int effected = this.db.getStatement().executeUpdate(saveStmnt);
			// if there were rows affected that means user was added

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// insert save into db, return the id
	public String insertFirstSavedGame(String inviter, String opponent, int status, String turn, int isFirst) {
		// System.out.println("IM BROKEN");

		String gameID = ""; // verifiy?
		// System.out.println("IM BROKEN");
		String saveStmnt = "INSERT INTO saves " + "VALUES (NULL,'" + inviter + "','" + opponent + "','" + "0" + "','"
				+ status + "','" + turn + "','" + isFirst + "');";
		// insert the user into the database
		//System.out.println(saveStmnt);
		try {
			// System.out.println("INSERT");
			int insert = this.db.getStatement().executeUpdate(saveStmnt);
			// System.out.println("INSERT: " + insert);
			// if there were rows affected that means user was added

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// System.out.println("GETTING GAME BACK");
		// Now get back the game I
		ResultSet getGame;
		try {
			// System.out.println("INVITER: " + inviter);

			getGame = this.db.getStatement().executeQuery("SELECT savesId FROM saves WHERE inviter = '" + inviter + "' AND opponent = '"
					+ opponent + "' AND isNew='1';");
			while (getGame.next()) {
				gameID = getGame.getString("savesId");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gameID;

	}

	public ArrayList<String> getCurrentGames(String user) {

		String getGames = "SELECT savesId FROM saves WHERE inviter = '" + user + "' OR opponent = '" + user + "';";
		ResultSet findGames;
		//System.out.println("QUERY: " + getGames);
		ArrayList<String> activeGames = new ArrayList<String>();
		try {
			// System.out.println("INSERT");
			findGames = this.db.getStatement().executeQuery(getGames);
			while (findGames.next()) {
				String activeId = findGames.getString("savesId");
				activeGames.add(activeId);
				//System.out.println("ADDED: " + activeId);
			}
			// System.out.println("INSERT: " + insert);
			// if there were rows affected that means user was added

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activeGames;
	}

	public String getSelectedGame(String gameId) {

		String getBoard = "SELECT game FROM saves WHERE savesId = " + gameId + ";";
		ResultSet rs;
		String serialBoard = "";
		try {

			rs = this.db.getStatement().executeQuery(getBoard);
			if (rs.next()) {
				serialBoard = rs.getString("game");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serialBoard;
	}
	
	public String getGameOpponent(String gameId, String userID) 
	{

		String getBoard = "SELECT inviter, opponent FROM saves WHERE savesId = " + gameId + ";";
		ResultSet rs;
		String inviter = "";
		String opponent = "";
		try {

			rs = this.db.getStatement().executeQuery(getBoard);
			if (rs.next()) {
				inviter = rs.getString("inviter");
				opponent = rs.getString("opponent");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(inviter.equals(userID))
		{
			return opponent;
		}
		else
		{
			return inviter;
		}
	}
	
	public String getUserColor(String gameId,String userID) {

		String getBoard = "SELECT inviter FROM saves WHERE savesId = " + gameId + ";";
		ResultSet rs;
		String inviterID = "";
		try {

			rs = this.db.getStatement().executeQuery(getBoard);
			if (rs.next()) {
				inviterID = rs.getString("inviter");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String color;
		
		if(userID.equals(inviterID))
		{
			color = "white";
		}
		else
		{
			color = "black";
		}

		return color;
	}
	
	public boolean getUserTurn(String gameId,String userID) {

		String getBoard = "SELECT turn, inviter FROM saves WHERE savesId = " + gameId + ";";
		ResultSet rs;
		String turnColor = "";
		String inviterID = "";
		try {

			rs = this.db.getStatement().executeQuery(getBoard);
			if (rs.next()) {
				turnColor = rs.getString("turn");
				inviterID = rs.getString("inviter");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userColor;
		
		if(userID.equals(inviterID))
		{
			userColor = "white";
		}
		else
		{
			userColor = "black";
		}
		
		boolean turn;
		if(turnColor.equals(userColor))
		{
			turn = true;
		}
		else
		{
			turn = false;
		}

		return turn;
	}

	public String getSelectedGamesTurn(String gameId) {
		String getBoard = "SELECT turn FROM saves WHERE savesId = " + gameId + ";";
		//System.out.println("GETTING BOARD" + gameId);
		//System.out.println(getBoard);
		ResultSet rs;
		String serialBoard = "";
		try {

			rs = this.db.getStatement().executeQuery(getBoard);
			if (rs.next()) {
				serialBoard = rs.getString("turn");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serialBoard;
	}

	// if client pressed quit change status
	public void setSaveStatusOff(String gameId) {
		String status = "UPDATE saves SET status = 0 WHERE savesId ='" + gameId + "';";
		try {
			int effected = this.db.getStatement().executeUpdate(status);
			// if there were rows affected that means user was added

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int getSavedStatus(String gameId) {
		String getstate = "SELECT status FROM saves WHERE savesId='" + gameId + "';";
		//System.out.println("GET SAVE: " + getstate);
		ResultSet rs;
		int state = 0;
		try {

			rs = this.db.getStatement().executeQuery(getstate);
			if (rs.next()) {
				state = rs.getInt("status");
				//System.out.println("BOARD STATUS: " + state);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return state;

	}

	public boolean isPlayerTurn(String gameId, String player) {
		String statement = "SELECT inviter, turn FROM saves WHERE savesId='" + gameId + "';";
		ResultSet rs;
		String inviter = "";
		String turn = "";
		int state = 0;
		try {

			rs = this.db.getStatement().executeQuery(statement);
			if (rs.next()) {
				inviter = rs.getString("inviter");
				turn = rs.getString("turn");
				// System.out.println("*****************Opponent: "+ opponent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inviter.equals(player) && turn.equals("white")) {
			return true;
		} else if ((!inviter.equals(player)) && turn.equals("black")) {
			return true;
		}

		return false;

	}

	public void updateWinLossRecord(String query) {
		try {
			int effected = this.db.getStatement().executeUpdate(query);
			// if there were rows affected that means user was added

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getOpponent(String gameId) {
		String getBoard = "SELECT opponent FROM record WHERE savesId = " + gameId + ";";
		//System.out.println("GETTING BOARD" + gameId);
		//System.out.println(getBoard);
		ResultSet rs;
		String serialBoard = "";
		try {

			rs = this.db.getStatement().executeQuery(getBoard);
			if (rs.next()) {
				serialBoard = rs.getString("turn");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serialBoard;
	}

	public int getRecordId(String date) {
		//System.out.println("d: " + date);
		String getrecord = "SELECT recordID FROM record WHERE startDate='" + date + "';";
		//System.out.println(getrecord);
		ResultSet rs;
		int state = 0;
		try {

			rs = this.db.getStatement().executeQuery(getrecord);
			if (rs.next()) {
				state = rs.getInt("recordID");
				return state;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

}
