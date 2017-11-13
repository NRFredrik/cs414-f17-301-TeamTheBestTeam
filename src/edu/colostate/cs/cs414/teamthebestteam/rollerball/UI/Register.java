package edu.colostate.cs.cs414.teamthebestteam.rollerball.UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.Config;
import edu.colostate.cs.cs414.teamthebestteam.rollerball.common.HashPassword;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;

import javax.swing.Action;
import javax.swing.ImageIcon;

public class Register {

	public JFrame frame;
	private JTextField typeUsername;
	private JTextField typeEmail;
	private JPasswordField typePassword;
	private JPasswordField typePasswordFinal;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
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
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
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
				boolean isValid = false;
				//check to see if email is unique
				while(isValid == false)
				{
					String mail = typeEmail.getText(); //holds current email address entered
					Config con =  new Config();
					if(!con.isUniqueEmail(mail))
					{
						//Alert user to enter unique email
						System.out.println("email is not unique");
					}//end of if
					else
					{
						
							//TODO get specifications from professor how long password needs to be 
							//TODO check password length and other properties
							String hashedPass = "";
							//salt password
							HashPassword hash = new HashPassword();
							try {
								hashedPass = hash.hashPassword(typePasswordFinal.getText()); //holds hashed version of password
							} catch (NoSuchAlgorithmException e1) {
								e1.printStackTrace();
							}
							String uname = typeUsername.getText();
							
							/**
							 * store user in database with name and salted password
							 * if result is true, user should be in database
							 */
							boolean addedUser = con.addNewUser(uname, mail, hashedPass);
							
							//break out of while loop
							isValid = true;
							
							//indicator telling user they registered and now need to login
							JOptionPane.showMessageDialog(null,"You have Successfully registered. Please login now");
							
							/**
							 * redirect to login page
							 */
							//Login login = new Login();
							//login.frame.setVisible(true);
							frame.setVisible(false);
						}
					}
				}
			
			else
			{
				System.out.println("passwords do not match");
			}
		}//end actionPerformed
	}//end SwingAction class
}
