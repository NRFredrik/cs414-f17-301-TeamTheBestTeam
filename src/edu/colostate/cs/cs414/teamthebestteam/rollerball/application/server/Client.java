package edu.colostate.cs.cs414.teamthebestteam.rollerball.application.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
//test
public class Client extends AbstractClient 
{
	ClientInterface clientUI;
	
	public Client(String userID, String host, int port, ClientInterface clientUI) throws IOException 
	{	
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
		handleMessageFromClientUI("#login," + userID);		
	}
	@Override
	protected void handleMessageFromServer(Object msg) 
	{
			clientUI.display(msg);	
	}
	public void handleMessageFromClientUI(Object message) 
	{
		try 
		{	
			//System.out.println("CLIENT:" + message);
			sendToServer(message);
		} catch (IOException error) 
		{
			System.out.println("COULD NOT SEND MESSAGE: " + message);
			System.out.println("BECAUSE ERROR:" + error);
			quit();
		}
	}
	
	public void quit() {
		try {
			//handleMessageFromClientUI("#logoff ");
			closeConnection();
		} catch (IOException error) 
		{
			System.exit(0);
		}
		
	}
	
	
	




	
}
