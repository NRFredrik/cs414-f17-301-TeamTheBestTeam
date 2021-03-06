// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.colostate.cs.cs414.teamthebestteam.rollerball.application.server;

/**
 * This interface implements the abstract method used to display
 * objects onto the client or server UIs.
 *
 * @author Dr Robert Lagani&egrave;re
 * @author Dr Timothy C. Lethbridge
 * @version July 2000
 */
public interface ClientInterface{
  /**
   * Method that when overriden is used to display objects onto
   * a UI.
   */
	//Changed to object from string
  public abstract void display(Object message);
}
