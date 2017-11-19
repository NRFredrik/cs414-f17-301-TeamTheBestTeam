package edu.colostate.cs.cs414.teamthebestteam.rollerball.application.server;
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
			userList = conf.populateUserList();

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
			String opponnentUserID =items.get(1);
			accept(client,opponnentUserID);
		}
		else if(((String)message).contains("#decline"))
		{
			List<String> items = Arrays.asList(((String) message).split(","));
			String userID =items.get(1);
			decline(client,userID);
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
	}
	
	protected void joinGame(ConnectionToClient joiningClient, String gameId)
	{
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
	
	protected void accept(ConnectionToClient acceptingClient, String opponnentUserID) 
	{
			
		//MOVE BELOW COMMENT TO CLIENT GUI
		//game = new Game(opponnentUserID,(String)thisClient.getInfo("userID"),1); //creator,opponent,status = 1 (in prog)
		
		//CREATE NEW GAME RECORD
		String newGameID = conf.insertFirstSavedGame(opponnentUserID,(String)acceptingClient.getInfo("userID"), 1,"white",1); //inviter,opp,status,turn,isnew
		
		//INFORM OPPONENT THAT USER HAS ACCEPTED INVITE
		//msgToCli("start,"+(String)thisClient.getInfo("userID"),thisClient);
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
		/*
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
		*/
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