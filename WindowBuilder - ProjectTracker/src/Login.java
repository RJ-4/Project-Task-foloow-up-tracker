import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/* This class is used to create the home page of the application and allows the user to input his/her credentials to log in the app
*/
public class Login {

	/* Global declarations */
	public JFrame loginFrame;
	private static JTextField usernameText;						//TextField for username for Login
	private static JPasswordField passwordText;					//PasswordField for Login
	
	private Connection connection = null;						//Establish a connection with the database
	
	public static boolean checkManager = false;					//Check if the user that logged in is manager or not

	public static String usernameValidation;					//Gets the username of the user who logged in 
	public static String passwordValidation;					//Gets the password of the user who logged in
	public static String nameValidation;						//Gets the name of the user who logged in 
	
	public static boolean getNotification = true;				//Boolean taken to prevent redundancy of the notification
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {					//Used the concept of multithreading to increase efficiency
			public void run() {
				try {
					Login window = new Login();
					window.loginFrame.setVisible(true);			//Makes the window visible on the application
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public Login() {			//Constructor
		
		/* Making a connection to the database via DatabaseConnection class*/
		connection = DatabaseConnection.dbConnection();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		loginFrame = new JFrame();
		loginFrame.setResizable(false);														//Cannot be maximized
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage(); 	//Title icon
		loginFrame.setIconImage(menuImage);
		loginFrame.setTitle("Task Tracker");														//Set the title on the tile menu
		loginFrame.getContentPane().setBackground(new Color(230, 230, 250)); 				//Background color for this frame
		loginFrame.setBounds(100, 100, 812, 550);											//Coordinates of this window
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);							//Terminates the application when closed
		
		addLabels();
		
		addTextFields();
		
		addLoginButton();
		
	}
	
	/*-------------------------------------- Create the login button -----------------------------------------------------------*/
	private void addLoginButton(){
		

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(357, 422, 98, 41);
		
		/* Functionality of the login button*/
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {											
				
				try{
					String queryEmployee = "Select *from login where username = ? and password = ?";		//Fetch the data from the login table
					String queryManager = "Select *from manager_login where username = ? and password =?";	//Fetch the data from manager table
					
					PreparedStatement psEmployee = connection.prepareStatement(queryEmployee);
					PreparedStatement psManager = connection.prepareStatement(queryManager);
					
					usernameValidation = usernameText.getText();							//Gets the data from the input 
					passwordValidation = passwordText.getText();							//input fields and stores them in variables
					
					psEmployee.setString(1, usernameValidation);			//Substituting the values of '?' of 
					psEmployee.setString(2, passwordValidation);			//the queryEmployee
					
					psManager.setString(1, usernameValidation);				//Substituting the values of '?' of
					psManager.setString(2, passwordValidation);				//the queryManager
					
					ResultSet rsEmployee = psEmployee.executeQuery();		//Executes the query and stores the result 
					ResultSet rsManager = psManager.executeQuery();
					
					
					// If data is found in the manager table, then the manager is logging in 
					if(rsManager.next()){
						
						nameValidation = rsManager.getString(3);		// Fetch the name of the manager
						loginFrame.dispose();							// Close the window
						ManagerScreen manager = new ManagerScreen();	// Open the Manager Screen Menu
						manager.setVisible(true);
						checkManager = true;							// It is the manager
					}
					
					// If data is found in the employee table, then the employee is logging in 
					else if(rsEmployee.next()){
						
						nameValidation = rsEmployee.getString(3);		// Fetch the name of the employee
						loginFrame.dispose();							// Close the window
						EmployeeScreen employee = new EmployeeScreen();	// Open the Employee Screen Menu
						employee.setVisible(true);
						checkManager = false;							// It is not the manager
					}
					
					
					// If the data entered is found neither in employee or manager table then, it is an invalid user
					else if(rsManager.next() == false && rsEmployee.next() == false){
						
						JOptionPane.showMessageDialog(null, "Invalid username or password");
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		loginButton.setForeground(new Color(0, 0, 205));
		loginButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		loginFrame.getContentPane().add(loginButton);
		
		
	}
	
	/*------------------------------------- Function to create all the TextFields on the frame --------------------------------*/
	private void addTextFields(){
		
		usernameText = new JTextField();
		usernameText.setBounds(362, 319, 154, 25);
		usernameText.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		loginFrame.getContentPane().add(usernameText);
		usernameText.setColumns(10);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(362, 371, 154, 25);
		passwordText.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		loginFrame.getContentPane().add(passwordText);
	}
	
	/*------------------------------------- Function to create all the labels -------------------------------------------------*/
	private void addLabels(){
		
		JLabel hclLabel = new JLabel("");
		hclLabel.setBounds(85, 22, 617, 83);
		Image image = new ImageIcon(this.getClass().getResource("/hcl.png")).getImage();
		loginFrame.getContentPane().setLayout(null);
		hclLabel.setIcon(new ImageIcon(image));
		loginFrame.getContentPane().add(hclLabel);
		
		JLabel loginLabel = new JLabel("");
		loginLabel.setBounds(26, 284, 223, 209);
		Image loginImage = new ImageIcon(this.getClass().getResource("/login_icon.png")).getImage();
		loginLabel.setIcon(new ImageIcon(loginImage));
		loginFrame.getContentPane().add(loginLabel);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(254, 313, 98, 32);
		usernameLabel.setForeground(new Color(0, 0, 205));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		loginFrame.getContentPane().add(usernameLabel);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(254, 365, 98, 32);
		lblNewLabel.setForeground(new Color(0, 0, 205));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginFrame.getContentPane().add(lblNewLabel);
		
		
		JLabel imageLabel1 = new JLabel("");
		Image image1 = new ImageIcon(this.getClass().getResource("/login_1.jpg")).getImage();
		imageLabel1.setIcon(new ImageIcon(image1));
		imageLabel1.setBounds(183, 156, 408, 96);
		loginFrame.getContentPane().add(imageLabel1);
		
		JLabel imageLabel2 = new JLabel("");
		Image image2 = new ImageIcon(this.getClass().getResource("/login_2.png")).getImage();
		imageLabel2.setIcon(new ImageIcon(image2));
		imageLabel2.setBounds(574, 284, 200, 209);
		loginFrame.getContentPane().add(imageLabel2);
	
	}
}
