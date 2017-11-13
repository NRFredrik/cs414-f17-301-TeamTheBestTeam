package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Game;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gui.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Server extends AbstractServer 
{
	
	// Class variables *************************************************
	final public static int DEFAULT_PORT = 5555;
	Board board;
	static Table table;
	Config con = new Config();
	ArrayList<String> userList;
	Game game;
	
	
	protected boolean isClosed;
	// Constructors ****************************************************
	public Server(int port) {
		super(port);
		this.isClosed = false;
		//System.out.println(userList);
	}


	public void handleMessageFromClient(Object message, ConnectionToClient client) 
	{
		
		if(((String)message).contains("#login"))
		{
			
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			login(client,userID);
		}
		else if(((String)message).contains("#userList"))
		{
			userList = con.populateUserList();

			ArrayList<String> tmpList = userList;
			tmpList.add(0, "User List");
			tmpList.remove(client.getInfo("userID"));
			msgToCli(tmpList, client);
		}
		else if(((String)message).contains("#invite"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			invite(client,userID);
			
		}
		else if(((String)message).contains("#accept"))
		{
			
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			accept(client,userID);
		}
		else if(((String)message).contains("#decline"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			decline(client,userID);
		}
		else if(((String)message).contains("#quit"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			quitGame(client,userID);
		}
		else if(((String)message).contains("#save"))
		{
			List<String> items = Arrays.asList(((String)message).split(","));
			String save = items.get(1);
			saveGame(client,save);
		}
		
		 else if(((String)message).contains("#join"))
		{
			 //Get serial board
			List<String> items = Arrays.asList(((String)message).split(","));
			String gameId = items.get(1);
			String userid = items.get(2);
			//FIX!?!?
			String currentgamestate = con.getSelectedGame(gameId);
			System.out.println("OPPONENET?!??!!: " + gameId);
			joinGame(client, gameId,userid);
			msgToCli(currentgamestate,client);
		}
		else
		{
			if((boolean) client.getInfo("turn"))
			{
				System.out.println("TURNNNING" + client.getName());
				move(client,(String)message);
							
			}
		}		
	}
	
	protected void joinGame(ConnectionToClient sendingClient, String gameId, String opponentUserID)
	{
		/*//tell server to look for opponent to the gameid
		ConnectionToClient opposingClient =null;
		System.out.println("JOIN GAME@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("WHO ARE YOU?" + sendingClient.getInfo("userId"));
		
		Thread[] clientThreadList = getClientConnections();
		
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(sendingClient.getInfo("opponent")))
			{
				
				opposingClient = (ConnectionToClient)clientThreadList[i];
				System.out.println("YOU FOUND THE OPPOSING PLAYER" + opposingClient.toString());
				System.out.println("YOU ARE BOTH ON!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*****#&#$*#&$#(*$#($*$");
				System.out.println("OPPOSINGCLIENT FOUND with oppoent:" + opposingClient.getInfo("opponent"));
			}
			
		}
		if(opposingClient==null) {
			System.out.println("COULD NOT FIND PLAYER");
		}
		//
		//msgToCli(move,sendingClient);*/
		
		System.out.println("start");
		Config conf = new Config();
		ConnectionToClient startingClient =null;
		
		Thread[] clientThreadList = getClientConnections();
		
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(opponentUserID))
			{
				startingClient = (ConnectionToClient)clientThreadList[i];
			}
		}
		
		System.out.println("SETTING INFO CLIENT");
		
		startingClient.setInfo("opponent",sendingClient.getInfo("userID"));
		//startingClient.setInfo("color", "white");
		//startingClient.setInfo("turn", true);
		msgToCli("login,Your Color: White", startingClient);
		System.out.println("STARTINGCLIENT OPPONENT: " + startingClient.getInfo("opponent"));
		System.out.println("SETTING INFO SENDINGGGGGG");
		
		sendingClient.setInfo("opponent",opponentUserID);
		System.out.println("SENDING CLIENT OPPONENT: " + sendingClient.getInfo("opponent"));
		//sendingClient.setInfo("color", "black");
		//sendingClient.setInfo("turn", false);
		msgToCli("login,Your Color: Black", sendingClient);
		System.out.println("NAME: " + (String)startingClient.getInfo("opponent") + " USERID: " + opponentUserID );
		System.out.println("INVITER: " + (String)startingClient.getInfo("userID") + "OPPON: " + (String)sendingClient.getInfo("userID"));
		game = new Game((String)sendingClient.getInfo("userID"),opponentUserID,1); //creator,opponent,status = 1 (in prog)
		System.out.println("DATABSE CALL");
		String newGameID = conf.insertFirstSavedGame((String)startingClient.getInfo("userID"), (String)sendingClient.getInfo("userID"), 1,"white",1); //inviter,opp,status,turn,isnew
		System.out.println("GOT NEWGAMEID: " + newGameID);
		startingClient.setInfo("gameId", newGameID);
		sendingClient.setInfo("gameId", newGameID);
		System.out.println("sending name: " + sendingClient.getInfo("gameId") + " userUD" + opponentUserID );
		msgToCli("start",startingClient);
		
		
	}

	
	protected int saveGame(ConnectionToClient sendingClient, String savedGame) 
	{
		//save game in DB, return gameID
		Config conf = new Config();
		System.out.println("HOW CAN U KNOW SENIGN: " +sendingClient.toString());
		String gameId = (String) sendingClient.getInfo("gameId");
		//color isssue
		System.out.println("SAVING GAME");
		String turn = conf.getSelectedGamesTurn(gameId);
		if(turn.equals("white"))
		{
			//then its black
			turn = "black";
			
		}
		else {
			turn = "white";
		}
		
		System.out.println("INSERTING TURN COLOR: " + turn);
		conf.insertSavedGame(gameId,savedGame, turn);
		
		return 0;
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


	protected void login(ConnectionToClient client, String userID) 
	{
		client.setInfo("userID", userID);
		
	}

	protected void logoff(String userID) 
	{
		System.out.println(userID + " has disconnected.");
		this.sendToAllClients(userID + " has logged off.");

	}
	
	protected void invite(ConnectionToClient sendingClient, String recieverUserID) 
	{
		
		if(userList.contains(recieverUserID))
		{
			con.addInvite((String)sendingClient.getInfo("userID"), recieverUserID);
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
				System.out.println("FOUND THE CLIENT YOU MOVE WITH");
			}
			
		}
		System.out.println("SENDING MOVE MESSAGE");
		msgToCli(move,sendingClient);
		
		System.out.println("ASSIGNING MOVES");
		sendingClient.setInfo("turn", false);
		opposingClient.setInfo("turn", true);
	}
	
	protected void accept(ConnectionToClient sendingClient, String userID) 
	{
		System.out.println("start");
		Config conf = new Config();
		ConnectionToClient startingClient =null;
		
		Thread[] clientThreadList = getClientConnections();
		
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(userID))
			{
				startingClient = (ConnectionToClient)clientThreadList[i];
			}
		}
		
		System.out.println("SETTING INFO CLIENT");
		
		startingClient.setInfo("opponent",sendingClient.getInfo("userID"));
		startingClient.setInfo("color", "white");
		startingClient.setInfo("turn", true);
		msgToCli("login,Your Color: White", startingClient);
		System.out.println("STARTINGCLIENT OPPONENT: " + startingClient.getInfo("opponent"));
		System.out.println("SETTING INFO SENDINGGGGGG");
		
		sendingClient.setInfo("opponent",userID);
		System.out.println("SENDING CLIENT OPPONENT: " + sendingClient.getInfo("opponent"));
		sendingClient.setInfo("color", "black");
		sendingClient.setInfo("turn", false);
		msgToCli("login,Your Color: Black", sendingClient);
		System.out.println("NAME: " + (String)startingClient.getInfo("opponent") + " USERID: " + userID );
		System.out.println("INVITER: " + (String)startingClient.getInfo("userID") + "OPPON: " + (String)sendingClient.getInfo("userID"));
		game = new Game((String)sendingClient.getInfo("userID"),userID,1); //creator,opponent,status = 1 (in prog)
		System.out.println("DATABSE CALL");
		String newGameID = conf.insertFirstSavedGame((String)startingClient.getInfo("userID"), (String)sendingClient.getInfo("userID"), 1,"white",1); //inviter,opp,status,turn,isnew
		System.out.println("GOT NEWGAMEID: " + newGameID);
		startingClient.setInfo("gameId", newGameID);
		sendingClient.setInfo("gameId", newGameID);
		System.out.println("sending name: " + sendingClient.getInfo("gameId") + " userUD" + userID );
		msgToCli("start",startingClient);
		
		
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
	
	
	protected void quitGame(ConnectionToClient sendingClient, String userID) 
	{
		
		
		//ConnectionToClient opposingClient =null;
		
		Thread[] clientThreadList = getClientConnections();
		
		for (int i = 0; i < clientThreadList.length; i++) 
		{
			
			if(((ConnectionToClient) clientThreadList[i]).getInfo("userID").equals(sendingClient.getInfo("opponent")))
			{
				msgToCli("quit",(ConnectionToClient)clientThreadList[i]);
				//opposingClient = (ConnectionToClient)clientThreadList[i];
			}
			
		}
		
		
		/*
		opposingClient.setInfo("opponent",null);
		opposingClient.setInfo("color", null);
		opposingClient.setInfo("turn", null);
		
		
		sendingClient.setInfo("opponent",null);
		sendingClient.setInfo("color", null);
		sendingClient.setInfo("turn", null);
		 */
		
	}
	

	protected void msgToCli(Object msg, ConnectionToClient client) {
		try {
			System.out.println("MSGTOCLIC: " + msg + client.getId());
			client.sendToClient(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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