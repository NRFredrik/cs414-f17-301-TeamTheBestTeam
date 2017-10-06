package edu.colostate.cs.cs414.teamthebestteam.rollerball.gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField typeUsername;
	private JPasswordField typePassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * @author kb
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane title = new JTextPane();
		title.setText("Login To Play Rollerball");
		title.setBounds(6, 6, 438, 31);
		title.setBackground(new Color(255,199,148));
		frame.getContentPane().add(title);
		
		typeUsername = new JTextField();
		typeUsername.setToolTipText("password");
		typeUsername.setColumns(10);
		typeUsername.setBounds(140, 75, 163, 31);
		frame.getContentPane().add(typeUsername);
		
		typePassword = new JPasswordField();
		typePassword.setBounds(140, 118, 163, 31);
		frame.getContentPane().add(typePassword);
		
		Config con = new Config();
		
		JButton typeLogin = new JButton("Login");
		typeLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String uname = typeUsername.getText();
				@SuppressWarnings("deprecation")
				String pass = typePassword.getText();
				
				if(con.userExists(uname,pass))
				{
					//TODO   Allow user to proceed to start game
					
					System.out.println("you're logged in");
					System.out.println("Now we need functionality to send user to start game\n");
				}
				else
				{
					// TODO  Redirect user to register page
					System.out.println("User is not registered");
					System.out.println("Now we need functionality to send user to register page\n");
				}
				
			}
		});
		typeLogin.setBounds(140, 160, 163, 29);
		frame.getContentPane().add(typeLogin);
		
		JTextPane txtEnterUsername = new JTextPane();
		txtEnterUsername.setText("Enter Username");
		txtEnterUsername.setBounds(6, 82, 111, 24);
		frame.getContentPane().add(txtEnterUsername);
		
		JTextPane txtEnterPassword = new JTextPane();
		txtEnterPassword.setText("Enter Password");
		txtEnterPassword.setBounds(6, 125, 111, 24);
		frame.getContentPane().add(txtEnterPassword);
	}
}
