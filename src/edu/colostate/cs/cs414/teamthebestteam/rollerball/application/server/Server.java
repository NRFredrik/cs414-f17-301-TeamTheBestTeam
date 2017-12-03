package edu.colostate.cs.cs414.teamthebestteam.rollerball.application.server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.application.manageuser.HashPassword;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.application.manageuser.ManageUser;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.domain.game.Game;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.technicalservice.databaseconnector.DatabaseConnection;


public class Server extends AbstractServer 
{
	
	// Class variables *************************************************
	final public static int DEFAULT_PORT = 5555;
	Board board;
	//static Table table;
	ManageUser conf = new ManageUser(new DatabaseConnection());
	ArrayList<String> userList;
	//Game game;
	
	int recordID = 0;
	
	protected boolean isClosed;
	// Constructors ****************************************************
	public Server(int port) {
		super(port);
		this.isClosed = false;
		
		//MOVE TO FUTURE REGISTER MESSAGE
		userList = conf.populateUserList();
	}


	public void handleMessageFromClient(Object message, ConnectionToClient client) 
	{
		
		if(((String)message).contains("#login"))
		{
			
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			String password =items.get(2);
			
			login(client,userID,password);
		}
		else if(((String)message).contains("#unregister"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String email =items.get(1);
			String password =items.get(2);
			
			if(conf.userExistsEmail(email,password))
			{
				conf.removeUser(email);
				msgToCli("unregisterSuccess", client);
			}
			else
			{
				msgToCli("unregisterFail", client);
			}
		}
		else if(((String)message).contains("#userList"))
		{
			msgToCli(userList, client);
		}
		else if(((String)message).contains("#needInvites"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String thisUserID =items.get(1);
			
			ArrayList<String> inviteList = conf.populateInviteList(thisUserID);
			inviteList.add(0, "invites");
			msgToCli(inviteList, client);
		}
		else if(((String)message).contains("#needGames"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String thisUserID =items.get(1);
			
			ArrayList<String> gameArray = conf.getCurrentGames(thisUserID);
			
			
			for(int i = 0; i < gameArray.size(); i++)
			{
				String opponent = conf.getGameOpponent(gameArray.get(i), thisUserID);
				gameArray.set(i, gameArray.get(i)+": "+opponent);
			}
			gameArray.add(0, "games");
			msgToCli(gameArray, client);
		}
		else if(((String)message).contains("#invite"))
		{
			System.out.println("Invite Received by Server");
			
			List<String> items = Arrays.asList(((String) message).split(","));
			String opponentID =items.get(1);
			invite(client,opponentID);
		}
		else if(((String)message).contains("#accept"))
		{
			
			List<String> items = Arrays.asList(((String) message).split(","));
			String thisUserID =items.get(1);
			String opponnentUserID =items.get(2);
			accept(client,thisUserID,opponnentUserID);
		}
		else if(((String)message).contains("#decline"))
		{		
			List<String> items = Arrays.asList(((String) message).split(","));
			String inviterID =items.get(1);
			conf.declineInviteDB(inviterID, (String)client.getInfo("userID"));
			
			//decline(client,inviterID);
		}
		else if(((String)message).contains("#quit"))
		{
			/*
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			quitGame(client,userID);*/
		}
		else if(((String)message).contains("#save"))
		{
			List<String> items = Arrays.asList(((String)message).split(","));
			String newGameState = items.get(1);
			String gameID = items.get(2);
			saveGame(client,newGameState,gameID);
		}
		
		else if(((String)message).contains("#join"))
		{
			 //Get serial board
			List<String> items = Arrays.asList(((String)message).split(","));
			String gameId = items.get(1);
			
			joinGame(client,gameId);
		}
		else if(((String)message).contains("#whiteWin"))
		{
			
			List<String> items = Arrays.asList(((String)message).split(","));
			
			String thisUserID = items.get(1);
			String oppo = items.get(2);
			String gameID=items.get(3);
			winGame(client,gameID);
			
			int recordId = conf.getGameRecordID( thisUserID,  oppo);
			
			String updateWinner = "UPDATE `Rollerball`.`record` SET `winner`='" + thisUserID + "' WHERE `recordID`='"+recordId+"'";
			String updateLoser = "UPDATE `Rollerball`.`record` SET `loser`='"+ oppo+"' WHERE `recordID`='"+recordId+"'";
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date();
			String endDate = dateFormat.format(curDate);
			String updateEndDate = "UPDATE `Rollerball`.`record` SET `endDate`='"+ endDate+"' WHERE `recordID`='"+recordId+"'";
			
			String status = "finished";
			String updateStatus = "UPDATE `Rollerball`.`record` SET `status`='"+ status+"' WHERE `recordID`='"+recordId+"'";

			conf.updateWinLossRecord(updateWinner);												
			conf.updateWinLossRecord(updateLoser);
			conf.updateWinLossRecord(updateEndDate);
			conf.updateWinLossRecord(updateStatus);
			conf.finishGameState(gameID);
		}
		else if(((String)message).contains("#blackWin"))
		{
			List<String> items = Arrays.asList(((String)message).split(","));
			
			String gameOpponent = items.get(1);
			String gameCreator = items.get(2);
			String gameId = items.get(3);
			winGame(client,gameId);
			
			int recordId = conf.getGameRecordID(gameCreator,  gameOpponent);
			//Update the record to database and increment the win count
			String updateWinner = "UPDATE `Rollerball`.`record` SET `winner`='" + gameOpponent + "' WHERE `recordID`='"+recordId+"'";
			String updateLoser = "UPDATE `Rollerball`.`record` SET `loser`='"+ gameCreator +"' WHERE `recordID`='"+recordId+"'";
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date();
			String endDate = dateFormat.format(curDate);
			String updateEndDate = "UPDATE `Rollerball`.`record` SET `endDate`='"+ endDate+"' WHERE `recordID`='"+recordId+"'";
			
			String status = "finished";
			String updateStatus = "UPDATE `Rollerball`.`record` SET `status`='"+ status+"' WHERE `recordID`='"+recordId+"'";
			
			conf.updateWinLossRecord(updateWinner);
			conf.updateWinLossRecord(updateLoser);
			conf.updateWinLossRecord(updateEndDate);
			conf.updateWinLossRecord(updateStatus);
			conf.finishGameState(gameId);
		}
		else if(((String)message).contains("#gameHistory"))
		{
			List<String> items = Arrays.asList(((String)message).split(","));
			String selectedUser = items.get(1);
			ArrayList<String> userGameHistoryList = conf.getUserGameHistory(selectedUser);
			
			userGameHistoryList.add(0, "profile");
			msgToCli(userGameHistoryList, client);
		}
		else if(((String)message).contains("#finishGame"))
		{
			List<String> items = Arrays.asList(((String)message).split(","));
			String gameCreator=items.get(1);
			String gameOpponent=items.get(2);
			String gameID=items.get(3);
			
			conf.finishGameRecord(gameCreator, gameOpponent);
			conf.finishGameState(gameID);
		}
		else if(((String)message).contains("#register"))
		{
			List<String> items = Arrays.asList(((String)message).split(","));
			String userID=items.get(1);
			String email=items.get(2);
			String password =items.get(3);
			register(client,userID,email,password);
		}
	}
	
	protected void joinGame(ConnectionToClient joiningClient, String gameId)
	{
		System.out.println(gameId);
		String currentgamestate = conf.getSelectedGame(gameId);
		
		String color=conf.getUserColor(gameId, (String)joiningClient.getInfo("userID"));
		String turnColor = conf.getSelectedGamesTurn(gameId);
		boolean turn =conf.getUserTurn(gameId, (String)joiningClient.getInfo("userID"));
		
		//Send client current game state, their color, and if its their move
		msgToCli("join,"+currentgamestate+","+color+","+turnColor+","+turn,joiningClient);
	}

	
	protected void saveGame(ConnectionToClient savingClient, String savedGame, String gameID) 
	{
		String turnColor = conf.getSelectedGamesTurn(gameID);
		if(turnColor.equals("white"))
		{
			turnColor = "black";
			
		}
		else 
		{
			turnColor = "white";
		}	
		conf.insertSavedGame(gameID,savedGame, turnColor);

		String currentgamestate = conf.getSelectedGame(gameID);
		String color=conf.getUserColor(gameID, (String)savingClient.getInfo("userID"));
		boolean turn =conf.getUserTurn(gameID, (String)savingClient.getInfo("userID"));
		
		//Send client current game state, their color, and if its their move
		msgToCli("move,"+gameID+","+currentgamestate+","+color+","+turnColor+","+turn,savingClient);
		
		
		
		
		//SEND OTHER CLIENT INFOR ON NEW GAME STATE IF ONLINE
		String opponentUserID = conf.getGameOpponent(gameID,(String)savingClient.getInfo("userID"));
		ConnectionToClient opposingClient =null;

		Thread[] clientThreadList = getClientConnections();
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(opponentUserID))
			{
				opposingClient = (ConnectionToClient)clientThreadList[i];
			}
			
		}
		String opColor=conf.getUserColor(gameID, opponentUserID);
		boolean opTurn =conf.getUserTurn(gameID, opponentUserID);
		
		msgToCli("move,"+gameID+","+currentgamestate+","+opColor+","+turnColor+","+opTurn,opposingClient);
		
		
	}
	
	protected void winGame(ConnectionToClient savingClient, String gameID) 
	{

		//Send client current game state, their color, and if its their move
		msgToCli("over",savingClient);

		//SEND OTHER CLIENT INFOR ON NEW GAME STATE IF ONLINE
		String opponentUserID = conf.getGameOpponent(gameID,(String)savingClient.getInfo("userID"));
		ConnectionToClient opposingClient =null;

		Thread[] clientThreadList = getClientConnections();
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(opponentUserID))
			{
				opposingClient = (ConnectionToClient)clientThreadList[i];
			}
			
		}
		msgToCli("over",opposingClient);
	
	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	protected void serverClose() {
		// System.out.println("calling quit.");
		try {
			// using A-Server method sendToAllClients(), send a message to all
			// clients in this
			// thread that tells them the server is terminating
			this.sendToAllClients("Server on port " + getPort() + " is closing...");
			Thread.sleep(2000);
			this.sendToAllClients("Server on port " + getPort() + " has closed. Goodbye.");

			// call A-Server's close method
			this.close();
			this.isClosed = true;
			// print to the server, that server has succesfully terminated
			System.out.println("Server has closed successfully.");

		} catch (Throwable exception) {
			System.out.println("Server on port " + getPort() + " could not be closed...Sorry");
		}
	}

	/*
	 * This method is used to stop a server from listening for new clients.
	 * Existing clients may still use the server until they log out of it. This
	 * method should not interfere with existing clients.
	 */
	protected void stop() {
		// System.out.println("calling stop.");
		try {
			// using A-Server method sendToAllClients(), send a message to all
			// clients in this
			// thread that tells them the server is terminating
			this.sendToAllClients("Server on port " + getPort() + " will no longer be accepting new clients.");
			this.sendToAllClients("You may continue to chat on this server.");
			this.sendToAllClients("However, if you log out of this server, you will not be able to log back into it.");
			Thread.sleep(2000);
			this.sendToAllClients("Server on port " + getPort() + " is no longer open to new clients. Thank you.");

			// call A-Server's stop method
			this.stopListening();

			// print to the server, that server has succesfully terminated
			System.out.println("Server has stopped listening successfully.");

		} catch (Throwable exception) {
			System.out.println("Server on port " + getPort() + " could not be stopped...Sorry");
		}
	}

	/*
	 * This method is used to start a server to listening for new clients.
	 * Existing clients may still use the server. This method should not
	 * interfere with existing clients.
	 */
	protected void start() {
		// System.out.println("calling start.");
		try {
			// using A-Server method sendToAllClients(), send a message to all
			// clients in this
			// thread that tells them the server is terminating
			this.sendToAllClients("Server on port " + getPort() + " is starting...");
			Thread.sleep(2000);
			this.sendToAllClients("Server on port " + getPort() + " is now accepting new clients.");

			// call A-Server's stop method
			this.listen();

			// print to the server, that server has successfully terminated
			System.out.println("Server has started listening successfully.");

		} catch (Throwable t) {
			System.out.println("Server on port " + getPort() + " could not be started...Sorry");
		}
	}

	/*
	 * This command is used to terminate a server gracefully It will let current
	 * users know that it is ending and that they will no longer be able to chat
	 */
	//test
	protected void quit() {
		try {
			System.out.println("SERVER QUIT");
			this.sendToAllClients("Server on port " + getPort() + " is terminating...");
			Thread.sleep(2000);
			this.sendToAllClients("Server on port " + getPort() + " has been terminated.");

			// print to the server, that server has successfully terminated
			System.out.println("Server has terminated successfully.");

			this.stopListening();
			this.close();
			System.exit(0);
		} catch (Throwable t) {
			System.out.println("Server on port " + getPort() + " could not be terminated...Sorry");
		}
	}

	/*
	 * this method is used to change the port of a server it should only be
	 * usable when current server is closed
	 */
	protected void setThePort(int newPort) 
	{
		if (this.isClosed) {
			try {
				this.setPort(newPort);
				System.out.println("Successfully changed server port to " + newPort);
			} catch (Throwable t) {
				System.out.println("ERROR - Could not set new port.");
			}
		} else {
			System.out.println("ERROR - Please close the server first. Then set port.");
		}
	}


	protected void login(ConnectionToClient client, String userID, String password) 
	{
		boolean correctLogin = conf.userExists(userID,password);
		if(correctLogin)
		{
			client.setInfo("userID", userID);
			msgToCli("login,"+correctLogin,client);
		}
		else
		{
			msgToCli("login,"+correctLogin,client);
		}
	}
	
	protected void register(ConnectionToClient client, String userID, String email, String password) 
	{
		//check to see if email is unique
			if(!conf.isUniqueEmail(email))
			{
				//Alert user to enter unique email
				//System.out.println("email is not unique");
				msgToCli("incorrectRegister",client);

			}//end of if
			else
			{
				
					//TODO get specifications from professor how long password needs to be 
					//TODO check password length and other properties
					String hashedPass = "";
					//salt password
					HashPassword hash = new HashPassword();
					try {
						hashedPass = hash.hashPassword(password); //holds hashed version of password
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}		
					
					/**
					 * store user in database with name and salted password
					 * if result is true, user should be in database
					 */
					boolean addedUser = conf.addNewUser(userID, email, hashedPass);
					
					if (addedUser)
					{
						msgToCli("correctRegister",client);
					}
					else
					{
						msgToCli("incorrectRegister",client);
					}	
			}
	}

	protected void logoff(String userID) 
	{
		System.out.println(userID + " has disconnected.");
		this.sendToAllClients(userID + " has logged off.");

	}
	
	protected void invite(ConnectionToClient sendingClient, String recieverUserID) 
	{
		System.out.println(userList);
		if(userList.contains(recieverUserID))
		{
			System.out.println("Invite Added To Database");
			conf.addInvite((String)sendingClient.getInfo("userID"), recieverUserID);
		}
		else
		{
		
		}
	}
	
	protected void move(ConnectionToClient sendingClient, String move) 
	{
		ConnectionToClient opposingClient =null;
		
		Thread[] clientThreadList = getClientConnections();
		
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(sendingClient.getInfo("opponent")))
			{
				msgToCli(move,(ConnectionToClient)clientThreadList[i]);
				opposingClient = (ConnectionToClient)clientThreadList[i];
			}
			
		}
		msgToCli(move,sendingClient);

	}
	
	protected void accept(ConnectionToClient acceptingClient,String thisUserID, String opponnentUserID) 
	{
		//CREATE NEW GAME RECORD
		conf.insertFirstSavedGame(opponnentUserID,(String)acceptingClient.getInfo("userID"), 1,"white",1); //inviter,opp,status,turn,isnew
		conf.acceptInviteDB(opponnentUserID, thisUserID);		
		
	}
	
	protected void decline(ConnectionToClient sendingClient, String userID) 
	{
		
		ConnectionToClient startingClient =null;
		
		Thread[] clientThreadList = getClientConnections();
		
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(userID))
			{
				startingClient = (ConnectionToClient)clientThreadList[i];
			}
		}
		
		msgToCli("decline",startingClient);
		
	}
	
	
	

	protected void msgToCli(Object msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			System.out.println("CLIENT DISCONNECTED FOLLOWING MESSAGE FAILED TO SEND: " + msg);
		}
	}

	/**
	 * This method is responsible for the creation of the server instance (there
	 * is no UI in this phase).
	 *
	 * @param args[0]
	 *            The port number to listen on. Defaults to 5555 if no argument
	 *            is entered.
	 */
	public static void main(String[] args) throws Exception 
	{
		
		int port = 0; // Port to listen on
		
		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		
		Server eServer = new Server(port);

		try {
			eServer.listen(); // Start listening for connections
		} catch (Exception exception) {
			System.out.println("ERROR - Could not listen for clients!");
		}
		//table = new Table();
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) {
				message = fromConsole.readLine();
				// System.out.println(message);
				switch (message) {
				case "#close":
					eServer.serverClose();
					break;
				case "#stop":
					eServer.stop();
					break;
				case "#start":
					eServer.start();
					break;
				case "#quit":
					eServer.quit();
					break;
				default:
					break;
				}
			}
		} catch (Exception exception) {
			System.out.println("Unexpected error while reading from console!");
		}	
	}
}