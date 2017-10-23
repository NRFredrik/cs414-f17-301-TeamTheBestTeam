package edu.colostate.cs.cs414.teamthebestteam.rollerball.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;

import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.gui.ClientTable.BoardPanel;

public class ClientGUI 
{
	//class variables
    private JFrame mainFrame;
    private JOptionPane commandPane;
    private JFrame loginFrame;
    private JPanel loginPanel;
    private JTextField messageField;
    private JTextField userField;
    private JTextField passField;
    private JFormattedTextField portField;
    private JFormattedTextField hostField;
    private JLayeredPane mainPane;
    private JTextArea statusArea;
    private Timer timer;
    private JComboBox channelMenu;
    private JComboBox commandMenu;
    private JTextArea textBox;
    private JButton sendButton;
    private JButton loginButton;
    private JButton logoffButton;
    private JButton sketchButton;
    private JButton statusButton;
    private Vector<String> channels;
    private Vector<String> commands;
    private ClientTable client = null;
    public BoardPanel boardPanel;
    
    //main for staring GUI
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                try 
                {
                    ClientGUI simplechat = new ClientGUI();
                    simplechat.mainFrame.setVisible(true);
                   // simplechat.loginFrame.setVisible(true);
                    
                    
                } catch (Exception e) {e.printStackTrace();}
            }
        });
    }

    //GUI constructor
    public ClientGUI() 
    {
    	try {
			//client = new ClientTable(ClientGUI.this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        executeGUI();
    }

    //Setup the contents of the GUI
    private void executeGUI() 
    {

		//Main GUI Frame
		
        mainFrame = new JFrame();
        mainFrame.setTitle("FBA SimpleChat");
        mainFrame.setBounds(100, 100, 770, 550);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new ExitWindow());
        
        
        //Main GUI Panel
        mainPane = new JLayeredPane();
        mainPane.setBackground(SystemColor.inactiveCaptionBorder);
        mainPane.setBackground(Color.lightGray);
        mainFrame.getContentPane().add(mainPane);
        /*
         //login frame
    	loginFrame = new JFrame("Login");
    	loginFrame.setBounds(100, 100, 300, 250);
    	loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
         //login panel
		loginPanel = new JPanel();
		loginPanel.setLayout(null);
		loginPanel.setBackground(Color.lightGray);
		loginFrame.add(loginPanel);
		
        //Label For Host Field
        JLabel hostLabel = new JLabel("Host");
        hostLabel.setBounds(10, 10, 80, 25);
        loginPanel.add(hostLabel);

        //Host Field on login panel
        hostField = new JFormattedTextField();
        hostField.setToolTipText("Host name, any string");
        hostField.setText("localhost");
        hostField.setBounds(100, 10, 160, 25);
        hostField.addKeyListener(new EnterPressed());
        loginPanel.add(hostField);

        //Label for Port Field
        JLabel portLabel = new JLabel("Port");
        portLabel.setBounds(10, 40, 80, 25);
        loginPanel.add(portLabel);

        //Port Field on login panel
        portField = new JFormattedTextField();
        portField.setToolTipText("port number, only valid integers from 0 - 65535");
        portField.setText("5555");
        portField.setBounds(100, 40, 160, 25);
        portField.addKeyListener(new EnterPressed());
        loginPanel.add(portField);
        
        //Label for user field
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 70, 80, 25);
        loginPanel.add(userLabel);
        
        //User field on login panel
        userField = new JTextField();
        userField.setText("username");
        userField.setBounds(100, 70, 160, 25);
        userField.addKeyListener(new EnterPressed());
        loginPanel.add(userField);

        //label for password
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(10, 100, 80, 25);
        loginPanel.add(passLabel);
        
        //Pasword Field
        passField = new JTextField();
        passField.setText("password\r\n");
        passField.setBounds(100, 100, 160, 25);
        passField.addKeyListener(new EnterPressed());
        loginPanel.add(passField);
        
        //Login Button on login panel
        loginButton = new JButton("Login");
        loginButton.setToolTipText("Make sure information in all fields is correct");
        loginButton.addActionListener(new loginListener());
        loginButton.setBounds(100, 150, 80, 25);
        loginPanel.add(loginButton);
         */
        //Button for logging in and off on main panel
        logoffButton = new JButton("Logoff");
        logoffButton.setToolTipText("Say Good bye :)");
        logoffButton.setEnabled(false);
        logoffButton.addActionListener(new logoffListener());
        logoffButton.setBounds(10, 5, 115, 25);
        mainPane.add(logoffButton);
        /*
        //Button to change status between unavailable and available
        statusButton = new JButton("Unavailable");
        statusButton.setToolTipText("Set your status");
        statusButton.setEnabled(false);
        statusButton.addActionListener(new statusListener());
        statusButton.setBounds(10, 35, 115, 25);
        mainPane.add(statusButton);
        
        //Label For channel menu
        JLabel channelLabel = new JLabel("Channel:");
        channelLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        channelLabel.setBounds(340, 7, 102, 21);
        mainPane.add(channelLabel);
        
        
        //Chanel menu to switch between channels
        channelMenu = new JComboBox();
        channelMenu.setToolTipText("Switch channels. Main is the default chat room.");
        channelMenu.setEnabled(false);
        channels = new Vector<String>();
        channels.add("Main");
        channelMenu.setModel(new DefaultComboBoxModel(channels));
        channelMenu.addItemListener(new ChannelMenuListener());
        channelMenu.setBounds(410, 5, 97, 25);
        channelMenu.setBackground(Color.WHITE);
        mainPane.add(channelMenu); 
        
        //Command menu to execute commands
        commandMenu = new JComboBox();
        commandMenu.setToolTipText("Pick a command");
        commandMenu.setEnabled(false);
        commands = new Vector<String>();
        commands.add("Commands");
        commands.add("Check Channel Status");
        commands.add("Block A User");
        commands.add("Block The Server");
        commands.add("Unblock A User");
        commands.add("Unblock All");
        commands.add("Start A Channel");
        commands.add("Join A Channel");
        commands.add("Leave A Channel");
        commands.add("Start Forwarding");
        commands.add("Stop Forwarding");
        commands.add("Send A Private Message");
        commands.add("See Who You Block");
        commands.add("See Who Blocks You");
        commandMenu.setModel(new DefaultComboBoxModel(commands));
        commandMenu.addItemListener(new CommandMenuListener());
        commandMenu.setBounds(595, 5, 160, 25);
        commandMenu.setBackground(Color.WHITE);
        mainPane.add(commandMenu); 
		*/
        //Pane to show users and statuses
        JScrollPane statusScrollPane = new JScrollPane();
        statusScrollPane.setBounds(4, 95, 130, 348);
        mainPane.add(statusScrollPane);
        
        //Label for status window
        JLabel stausWindowLabel = new JLabel("Users Online");
        stausWindowLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        stausWindowLabel.setBounds(30, 75, 102, 21);
        mainPane.add(stausWindowLabel);
        
        //Area to show users and status
        statusArea = new JTextArea();
        statusScrollPane.setViewportView(statusArea);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Arial", Font.PLAIN, 15));
        statusArea.setBackground(Color.lightGray);

        //Pane for main message feed
        JScrollPane textBoxScrollPane = new JScrollPane();
        textBoxScrollPane.setBounds(140, 33, 615, 410);
        mainPane.add(textBoxScrollPane);

        //Textbox for main message feed
        textBox = new JTextArea();
        textBox.setFont(new Font("Dialog", Font.PLAIN, 17));       
        textBoxScrollPane.setViewportView(textBox);
        textBox.setEditable(false);
        textBox.setBackground(Color.lightGray);
        //message for for typing messages
        messageField = new JTextField();
        messageField.setEnabled(false);
        messageField.setBounds(140, 450, 530, 45);
        //messageField.addKeyListener(new EnterPressed());
        mainPane.add(messageField);
        
        /*
        //button to send messages
        sendButton = new JButton("Send");
        sendButton.setName("Send");
        sendButton.setToolTipText("Send message");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new sendAction());
        sendButton.setBounds(680, 450, 75, 22);
        mainPane.add(sendButton);
        */
      
    }

    //show incoming and outgoing messages 
    public void displayMessage(String message) 
    {
        if (textBox.getText() != null) 
        {
            textBox.append("> " + message + "\n");
        } else 
        {
            textBox.setText("> " + message + "\n");
        }
        
    }

    //updates the channel menu when user joins or leaves channels
    public void updateChannels(String[] channelList) 
    {
        for (int i = 1; i < channelList.length; i++) 
        {
            if (!channels.contains(channelList[i])) 
            {
                channels.add(channelList[i]);
                channelMenu.setSelectedItem(channelList[i]);
            }
        }
        
    }
    /*
    //Sends requests to the server for the users channels
    private class RequestUserChannels implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            if (channelMenu.isFocusOwner())
            {
                return;
            }

            client.sendM("#userChannelList");
        }
    }
    
    //sends requests to server for online users and their status
    private class RequestOnlineUsers implements ActionListener 
    {

        public void actionPerformed(ActionEvent event) 
        {
            client.sendM("#userStatusList");
        }
    }
    
    //updates the status area with online users and their status
    public void updateUsers(ArrayList<ArrayList<String>> users) 
    {
        statusArea.setText("");
        
        for (int i = 0; i < users.size(); i++) 
        {
            statusArea.append( users.get(i).get(0) + ": " + users.get(i).get(1) +"\n");
        }
    }
    */
    /*
    //logs client off if they are ever disconnected
    private class ConnectionChecker implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            if (!client.client.isConnected()) 
            {
                logoffButton.doClick();
            }
        }
    }
    
    //starts timer that asks server for connection status, online users,
    //and channels every second
    private void ServerRequests() 
    {
        timer = new Timer(1000, new ConnectionChecker());
        timer.setInitialDelay(0);
        timer.setDelay(1000);
        timer.addActionListener(new RequestOnlineUsers());
        timer.addActionListener(new RequestUserChannels());     
        timer.start();
    }

    //handles pressing of login button in the login pane
    private class loginListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            if (portField.getBackground().equals(Color.red)) 
            {
                portField.setBackground(Color.white);
            }

            if (userField.getBackground().equals(Color.red)) 
            {
                userField.setBackground(Color.white);
            }

            if (passField.getBackground().equals(Color.red)) 
            {
                passField.setBackground(Color.white);
            }

            int port = 0;
            
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) 
            {
                portField.setBackground(Color.red);
                displayMessage("Error: Invalid Port");
                return;
            }

            if (port <= 0) 
            {
                portField.setBackground(Color.red);
                displayMessage("Error: Invalid Port");
                return;
            }

            String host = hostField.getText();
            String userID = userField.getText();
            String password = passField.getText();

            try {
				client = new ClientTable();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
            /*
            if (!client.client.isConnected()) {
                displayMessage("Error: Incorrect Login Information.");
                return;
            }
            
            //hides login window and enables all components on GUI
            loginFrame.setVisible(false);
            loginButton.setEnabled(false);
            logoffButton.setEnabled(true);
            statusButton.setEnabled(true);
            channelMenu.setEnabled(true);
            commandMenu.setEnabled(true);
            messageField.setEnabled(true);
            sendButton.setEnabled(true);
            sketchButton.setEnabled(true);
            userField.setEnabled(false);
            passField.setEnabled(false);
            hostField.setEnabled(false);
            portField.setEnabled(false);
            ServerRequests();//start sending requests to server
        }

    }
	*/
    //stops the timer
    private void stopTimer() 
    {
        timer.stop();
        timer = null;
    }
    
    //handles pressing of logoff button
    private class logoffListener implements ActionListener 
    {

        public void actionPerformed(ActionEvent event) 
        {
        	String message = logoffButton.getText();
        	
        	//log user off and disable GUI components
        	 if (message.equals("Logoff"))
             {
        		logoffButton.setText("Login");
	            stopTimer();
	            //client.sendM("#logoff");
	            client = null;
	
	            statusArea.setText("");
	            loginButton.setEnabled(true);
	            statusButton.setEnabled(false);
	            sendButton.setEnabled(false);
	            sketchButton.setEnabled(false);
	            channelMenu.setEnabled(false);
	            commandMenu.setEnabled(false);
	            messageField.setEnabled(false);
	            userField.setEnabled(true);
	            passField.setEnabled(true);
	            hostField.setEnabled(true);
	            portField.setEnabled(true);
             }
        	 
        	 //open login window
        	 else if(message.equals("Login"))
        	 {
        		 logoffButton.setText("Logoff");
        		 logoffButton.setEnabled(false);
        		 loginFrame.setVisible(true);
        	 }
        }

    }

    /*
    //handles pressing of status button
    private class statusListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            String message = statusButton.getText();
            //set user unavailable disable components
            if (message.equals("Unavailable"))
            {
            	 client.sendM("#unavailable");
            	 commandMenu.setEnabled(false);
            	 messageField.setEnabled(false);
            	 sendButton.setEnabled(false);
            	 sketchButton.setEnabled(false);
            	 statusButton.setText("Available");
            }
            //set user available enable components
            else if(message.equals("Available"))
            {
            	commandMenu.setEnabled(true);
            	messageField.setEnabled(true);
            	sendButton.setEnabled(true);
            	sketchButton.setEnabled(true);
            	client.sendM("#available");
            	statusButton.setText("Unavailable");
            }
        }
    }
    
    /*
    //handles send button pressing
    private class sendAction implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            String message = messageField.getText();
            
            //don't do anything if nothing was typed
            if (message == null || message.isEmpty()) 
            {
                return;
            }
            messageField.setText(null);

            client.sendM(message);
        }
    }
    
    //handles hitting enter in any of the field components on the GUI
    private class EnterPressed implements KeyListener 
    {

        public void keyPressed(KeyEvent hitEnter) 
        {

        }
        
        public void keyReleased(KeyEvent hitEnter) 
        {
            if (hitEnter.getSource().equals(messageField)) 
            {
                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    sendButton.doClick();
                }
            }
            else if (hitEnter.getSource().equals(hostField)) 
            {
                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    portField.requestFocusInWindow();
                }
            }
            else if (hitEnter.getSource().equals(portField)) 
            {
                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    userField.requestFocus();
                }
            }
            else if (hitEnter.getSource().equals(userField)) 
            {
                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    passField.requestFocus();
                }
            }
            else if (hitEnter.getSource().equals(passField)) 
            {
                if (hitEnter.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    loginButton.doClick();
                }
            }
        }
        public void keyTyped(KeyEvent hitEnter) {

        }
    }
  */
    
    //Logs user out if they close the GUI
    private class ExitWindow implements WindowListener 
    {
        public void windowActivated(WindowEvent event) 
        {
        	
        }

        public void windowClosing(WindowEvent event)
        {
        	//client.sendM("#logoff");
        }

        public void windowDeactivated(WindowEvent event) 
        {
        	
        }

        public void windowDeiconified(WindowEvent event) 
        {
        	
        }

        public void windowIconified(WindowEvent event) 
        {
        	
        }

        public void windowOpened(WindowEvent event) 
        {
        	
        }

        public void windowClosed(WindowEvent e) 
        {
        	
        }
    }
}

