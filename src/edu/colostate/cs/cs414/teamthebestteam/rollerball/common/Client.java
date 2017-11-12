package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

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
		//System.out.println(msg);
			clientUI.display(msg);	
		
	}
	public void handleMessageFromClientUI(Object message) 
	{
		try 
		{	
			//System.out.println(message);
			sendToServer(message);
		} catch (IOException error) 
		{
			System.out.println(error);
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
