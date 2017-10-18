package edu.colostate.cs.cs414.teamthebestteam.rollerball.common;

import java.io.IOException;
//test
public class Rollerball extends AbstractClient 
{
	RollIF clientUI;
	
	public Rollerball(String host, int port, RollIF clientUI) throws IOException 
	{	
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
		//handleMessageFromClientUI("login");		
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
			sendToServer(message);
			System.out.println("Sent");
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
		} catch (IOException error) {
		}
		System.exit(0);
	}
	
	
	




	
}
