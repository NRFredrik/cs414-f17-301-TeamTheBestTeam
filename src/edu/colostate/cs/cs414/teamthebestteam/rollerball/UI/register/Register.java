package edu.colostate.cs.cs414.teamthebestteam.rollerball.UI.register;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.UI.client.ClientGUI;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.application.manageuser.HashPassword;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.application.server.Client;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.application.server.ClientInterface;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.Action;
import javax.swing.ImageIcon;

public class Register implements ClientInterface {

	public JFrame frame;
	private JTextField typeUsername;
	private JTextField typeEmail;
	private JPasswordField typePassword;
	private JPasswordField typePasswordFinal;
	private final Action action = new SwingAction();
	Client client;

	public Register(String host, int port) 
	{
		try {
			client = new Client(host, port, Register.this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Register");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

		    }
		});
		frame.getContentPane().setLayout(null);
		
		JLabel username = new JLabel("username");
		username.setForeground(Color.RED);
		username.setBounds(30, 34, 73, 16);
		frame.getContentPane().add(username);
		
		JLabel email = new JLabel("email");
		email.setForeground(Color.RED);
		email.setBounds(30, 89, 73, 16);
		frame.getContentPane().add(email);
		
		JLabel passwordTest = new JLabel("password");
		passwordTest.setForeground(Color.RED);
		passwordTest.setBounds(30, 150, 73, 16);
		frame.getContentPane().add(passwordTest);
		
		JLabel passwordFinal = new JLabel("re-enter password");
		passwordFinal.setForeground(Color.RED);
		passwordFinal.setBounds(30, 207, 200, 16);
		frame.getContentPane().add(passwordFinal);

		JTextPane title = new JTextPane();
		title.setBackground(Color.WHITE);
		title.setText("Register For Client");
		title.setBounds(30, 6, 143, 16);
		frame.getContentPane().add(title);
		
		JButton btn_create_account = new JButton("Create Account");
		btn_create_account.setAction(action);
		btn_create_account.setForeground(Color.BLACK);
		btn_create_account.setBackground(Color.RED);
		btn_create_account.setOpaque(true);
		btn_create_account.setBounds(30, 300, 200, 29);
		frame.getContentPane().add(btn_create_account);
		
		typeUsername = new JTextField();
		typeUsername.setToolTipText("Enter your desired screen name");
		typeUsername.setForeground(Color.LIGHT_GRAY);
		typeUsername.setBounds(30, 51, 200, 26);
		frame.getContentPane().add(typeUsername);
		typeUsername.setColumns(10);
		
		typeEmail = new JTextField();
		typeEmail.setToolTipText("Enter a unique email");
		typeEmail.setForeground(Color.LIGHT_GRAY);
		typeEmail.setColumns(10);
		typeEmail.setBounds(30, 109, 200, 26);
		frame.getContentPane().add(typeEmail);
		
		typePassword = new JPasswordField();
		typePassword.setToolTipText("Type password");
		typePassword.setBounds(30, 175, 200, 26);
		frame.getContentPane().add(typePassword);
		
		typePasswordFinal = new JPasswordField();
		typePasswordFinal.setToolTipText("Type password");
		typePasswordFinal.setBounds(30, 225, 200, 26);
		frame.getContentPane().add(typePasswordFinal);

		JLabel image = new JLabel("");
		ImageIcon img = new ImageIcon("pictures/rollerball"+ ".PNG");
		image.setIcon(img);
		image.setBounds(257, 50, 164, 180);
		frame.getContentPane().add(image);
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Create Account");
			putValue(SHORT_DESCRIPTION, "Create Your Account");
		}
		public void actionPerformed(ActionEvent e) {
			
			if(typePasswordFinal.getText().equals(typePassword.getText()))
			{
				String email = typeEmail.getText(); //holds current email address entered
				String userID = typeUsername.getText();
				String password = typePasswordFinal.getText();
				client.handleMessageFromClientUI("#register,"+ userID +","+email +","+password);
				//frame.setVisible(false);
			}
			else
			{
				System.out.println("passwords do not match");
			}
		}//end actionPerformed
	}//end SwingAction class

	@Override
	public void display(Object message) 
	{
			
		if(message instanceof String)
		{
			if(message.toString().contains("incorrectRegister"))
			{
				JOptionPane.showMessageDialog(frame, "Registration failed. Is this user already registered?");
			}
			else if(message.toString().contains("correctRegister"))
			{
				JOptionPane.showMessageDialog(frame,"You have Successfully registered. Please login now");
				frame.setVisible(false);
				client.quit();
			}
		}
	}
}
