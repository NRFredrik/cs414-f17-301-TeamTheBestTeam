package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gameboard.Board;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.gui.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Server extends AbstractServer 
{
	// Class variables *************************************************
	final public static int DEFAULT_PORT = 5555;
	Board board;
	static Table table;
	
	
	protected boolean isClosed;
	// Constructors ****************************************************
	public Server(int port) {
		super(port);
		this.isClosed = false;	
	}


	public void handleMessageFromClient(Object message, ConnectionToClient client) 
	{
				
		if((message.equals("login")))
		{
			
		}
		
		this.sendToAllClients((String)message);
			
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


	protected void login(String userID, ConnectionToClient client, String password) {
		boolean validUser = true;
		boolean passCorrect = true;
		
		client.setInfo("user", userID);
			
		if (validUser == true && passCorrect == true) {
			System.out.println(userID + " has connected.");
			this.sendToAllClients(userID + " has logged in.");
		}
		else
		{
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	protected void logoff(String userID) 
	{
		System.out.println(userID + " has disconnected.");
		this.sendToAllClients(userID + " has logged off.");

	}
	

	protected void msgToCli(Object msg, ConnectionToClient client) {
		try {
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
