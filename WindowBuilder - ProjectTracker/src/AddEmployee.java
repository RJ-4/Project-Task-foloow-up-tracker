import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

/* This class is for the MANAGER and performs the following functions:
 * 1. Save a new employee to the database
 * 2. Update the information of an existing employee and save the updated information in the database 
 */

public class AddEmployee extends JFrame {

	private JPanel contentPane;
	private JTextField newUsernameText;
	private JPasswordField newPasswordText;
	private JTextField newEmpNameText;
	private JTextField contactText;
	private JTextField emailText;
	private Connection connection = null;
	private JPasswordField confirmPasswordText;
	
	
	private JLabel headingLabel;
	
	// String variables used for configuring back Button used in addBackButton()
	String usernameBackButton = "", passwordBackButton = "", confirmPasswordBackButton = "", nameBackButton = "", contactBackButton = "", emailBackButton = "";
	

	/**
	 * Create the frame.
	 */
	public AddEmployee() {
		
		connection = DatabaseConnection.dbConnection();
		setResizable(false);
		setTitle("Task Tracker");
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();	// Set the icon
		setIconImage(menuImage);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 891, 620);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addLabels();		//Add all the labels to the window
			
		addTextFields();	//Add all the textFields to the window
		
		addSaveButton();	//Add save button to the window
		
		
		if(UpdateAndDeleteEmployee.employeeExists == true){		// If the manager wants to update data of an existing employee, 
			fetchData();										// then load the current data of that employee in the input fields
		}
		
		addBackButton();		// Add the back button to the window
		
	}
	
	
	/*------------------------------------- Add labels --------------------------------------------------------------------*/
	private void addLabels(){
		
		// Heading Label
		if(UpdateAndDeleteEmployee.employeeExists == true){		// If the manager wants to update data of an existing employee,
			headingLabel = new JLabel("UPDATE EMPLOYEE");		// then set the heading to "UPDATE EMPLOYEE"
		}
		else{													// Else, the manager wants to add a new employee, then set
			headingLabel = new JLabel("ADD AN EMPLOYEE");		// the heading to "ADD AN EMPLOYEE"
		}		
		
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setForeground(new Color(0, 0, 139));
		headingLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		headingLabel.setBounds(261, 32, 328, 55);
		contentPane.add(headingLabel);

		// Username Label
		JLabel newUsernameLabel = new JLabel("Username");
		newUsernameLabel.setForeground(new Color(0, 0, 205));
		newUsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newUsernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		newUsernameLabel.setBounds(449, 122, 125, 39);
		contentPane.add(newUsernameLabel);
		
		
		// New Password Label
		JLabel newPasswordLabel = new JLabel("Password");
		newPasswordLabel.setForeground(new Color(0, 0, 205));
		newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newPasswordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		newPasswordLabel.setBounds(449, 185, 115, 39);
		contentPane.add(newPasswordLabel);
		
		
		// Confirm Password Label
		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		confirmPasswordLabel.setForeground(new Color(0, 0, 205));
		confirmPasswordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		confirmPasswordLabel.setBounds(449, 258, 140, 30);
		contentPane.add(confirmPasswordLabel);
		
		
		// Employee Name Label
		JLabel newNameLabel = new JLabel("Employee Name");
		newNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newNameLabel.setForeground(new Color(0, 0, 205));
		newNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		newNameLabel.setBounds(449, 318, 125, 39);
		contentPane.add(newNameLabel);
		
		// Label for image
		JLabel imageLabel = new JLabel("");
		Image image = new ImageIcon(this.getClass().getResource("/add_employee.png")).getImage();
		imageLabel.setIcon(new ImageIcon(image));
		imageLabel.setBounds(27, 122, 403, 402);
		contentPane.add(imageLabel);
		
		// Contact No. Label
		JLabel contactLabel = new JLabel("Contact No.");
		contactLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contactLabel.setForeground(new Color(0, 0, 205));
		contactLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		contactLabel.setBounds(449, 385, 125, 30);
		contentPane.add(contactLabel);
		
		// Email Id Label
		JLabel emailLabel = new JLabel("Email ID");
		emailLabel.setForeground(new Color(0, 0, 205));
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		emailLabel.setBounds(449, 442, 125, 33);
		contentPane.add(emailLabel);
	}
	
	
	/*--------------------------------------Add TextFields-------------------------------------------------------------------*/
	private void addTextFields(){
		
		// Username TextField
		newUsernameText = new JTextField();
		newUsernameText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		newUsernameText.setBounds(628, 125, 230, 34);
		contentPane.add(newUsernameText);
		newUsernameText.setColumns(10);
		
		
		// Password passwordField
		newPasswordText = new JPasswordField();
		newPasswordText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		newPasswordText.setBounds(628, 188, 230, 34);
		contentPane.add(newPasswordText);
		
		// Confirm Password passwordField
		confirmPasswordText = new JPasswordField();
		confirmPasswordText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		confirmPasswordText.setBounds(628, 259, 230, 30);
		contentPane.add(confirmPasswordText);
		
		// Employee name Text Field
		newEmpNameText = new JTextField();
		newEmpNameText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		newEmpNameText.setBounds(628, 321, 230, 35);
		contentPane.add(newEmpNameText);
		newEmpNameText.setColumns(10);
		
		// Contact No. Text Field
		contactText = new JTextField();
		contactText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		contactText.setBounds(628, 386, 230, 30);
		contentPane.add(contactText);
		contactText.setColumns(10);
		
		
		// Email Id Text Field
		emailText = new JTextField();
		emailText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailText.setBounds(628, 444, 230, 30);
		contentPane.add(emailText);
		emailText.setColumns(10);
	}
	
	
	/*-------------------------------------- Add Save Button --------------------------------------------------------------------*/
	private void addSaveButton(){
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// If the manager is updating data of an existing employee, then when save button is pressed, a prompt will appear
				// asking him/her whether he/she wants to update data. The prompt will have 2 options: Yes and No. 
				if(UpdateAndDeleteEmployee.employeeExists == true){
					
					//Create the prompt and store its response in an integer variable 
					int action = JOptionPane.showConfirmDialog(null, "Update data?", "Update", JOptionPane.YES_NO_OPTION);
					
					// If the manager has selected "Yes"(action = 0) then call the method updateData(), else "No"(action = 1), stay on the screen  
					if(action == 0){
						updateData();
					}
				}
				
				
				//Else, the manager is entering data of a new employee. When save button is pressed, a prompt will appear asking
				// him/her whether he/she wants to save data. The prompt will have 2 options: Yes and No 
				else{
					
					//Create the prompt and store its response in an integer variable
					int action = JOptionPane.showConfirmDialog(null, "Save data?", "Save", JOptionPane.YES_NO_OPTION);
					
					//If the manager has selected "Yes"(action = 0) then call the method saveData(), else "No"(action = 1), stay on the screen
					if(action == 0){
						saveData();
					}
				}
			}
		});
		saveButton.setForeground(new Color(0, 0, 205));
		saveButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		saveButton.setBounds(573, 507, 107, 37);
		contentPane.add(saveButton);
	}
	
	/*--------------------------------------- Add Back Button -------------------------------------------------------------*/
	private void addBackButton(){
		
		JButton backButton = new JButton("Back");
		
		// If the manager has selected "Update Employee Information" then fetch the data in the input fields and store them
		// in String variables
		if(UpdateAndDeleteEmployee.employeeExists == true){
			usernameBackButton = newUsernameText.getText();
			passwordBackButton = newPasswordText.getText();
			confirmPasswordBackButton = confirmPasswordText.getText();
			nameBackButton = newEmpNameText.getText();
			contactBackButton = contactText.getText();
			emailBackButton = emailText.getText();
		}
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Call the method to implement the functionality
				backButton(usernameBackButton, passwordBackButton, confirmPasswordBackButton, nameBackButton, contactBackButton,
						emailBackButton);
			}
		});
		backButton.setForeground(new Color(0, 0, 205));
		backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		backButton.setBounds(690, 506, 115, 39);
		contentPane.add(backButton);		
	}
	
	
	/*--------------------------------------- Update an existing employee data --------------------------------------------*/
	private void updateData(){
		
		try{
			
			String query = "Update login set username = ?, password = ?, Employee_name= ?, Contact_No = ?,"
					+ "Email_Id = ? where username = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			
			// Get the updated values entered by the manager in the input fields and store them in String variables
			String username = newUsernameText.getText();
			String password = newPasswordText.getText();
			String name = newEmpNameText.getText();
			String confirmPassword = confirmPasswordText.getText();
			String contact = contactText.getText();
			String email = emailText.getText();
			
			// Validate the updated information by using constraints declared in the validate() method of the 
			// AddEmployeeValidation class and store the return value(true or false) in the boolean variable "check"
			AddEmployeeValidation employee = new AddEmployeeValidation(username, password, confirmPassword, name, contact, email);
			boolean check = employee.validate();
			// If the conditions are satisfied and the data is validated update the values in the table "login"
			if(check == true){
			
				ps.setString(1, username);		//Here the index values correspond to the '?' in the query in order of occurance
				ps.setString(2, password);
				ps.setString(3, name);
				ps.setString(4, contact);
				ps.setString(5, email);
				ps.setString(6, UpdateAndDeleteEmployee.employeeUsername);
				ps.execute();					// Since the result is being stored in the table and not fetched from the table,
												// just execute the query without storing the result in ResultSet
				
				JOptionPane.showMessageDialog(null, "Employee Updated");	// Display a pop stating "Employee Updated"
				ps.close();						// Close the connection to the database
				
				UpdateAndDeleteEmployee.employeeExists = false;		// Set the value to false and go to the Manager Screen menu
				dispose();
				ManagerScreen manager = new ManagerScreen();
				manager.setVisible(true);
			}
			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Username already exists");		
			e.printStackTrace();
		}
		
	}
	
	
	/*--------------------------------- Save a new employee to the database ----------------------------------------------------*/
	private void saveData(){
		
		try{
			String query = "insert into login values(?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(query);
			
			// Get the data entered by the user in the input fields
			String newUsername = newUsernameText.getText();
			String newPassword = newPasswordText.getText();
			String confirmPassword = confirmPasswordText.getText();
			String newEmpName = newEmpNameText.getText();
			String contact = contactText.getText();
			String email = emailText.getText();
			
			// Check the validity conditions and store the return value in a boolean variable
			AddEmployeeValidation employeeValidation = new AddEmployeeValidation(newUsername, newPassword, confirmPassword, newEmpName 
													,contact, email);
			
			boolean check = employeeValidation.validate();
			if(check == false){
				return;
			}
			
			// Enter the values in the table and close the connection and go back to the Manager Screen menu
			ps.setString(1, newUsername);
			ps.setString(2, newPassword);
			ps.setString(3, newEmpName);
			ps.setString(4, contact);
			ps.setString(5, email);
			
			ps.execute();
			JOptionPane.showMessageDialog(null, "Data saved");
			ps.close();
			UpdateAndDeleteEmployee.employeeExists = false;
			dispose();
			ManagerScreen manager = new ManagerScreen();
			manager.setVisible(true);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Username already exists");
			e.printStackTrace();
		}
	}
	
	/*------------------------------ Fetch data from database and insert it into text fields -----------------------------------*/
	// This function is exclusive only when the manager selects "Update Employee Information"
	private void fetchData(){
		
		try{
			
			String query = "Select *from login where username = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, UpdateAndDeleteEmployee.employeeUsername);		//Fetch the username that the manager had entered when he 
			ResultSet rs = ps.executeQuery();								//was asked to enter the username of the employee whose records  
																			//he wanted to update
			// If a row is found set the values in the input fields according to the column index of that table
			if(rs.next()){
				newUsernameText.setText(rs.getString(1));
				newPasswordText.setText(rs.getString(2));
				confirmPasswordText.setText(rs.getString(2));
				newEmpNameText.setText(rs.getString(3));
				contactText.setText(rs.getString(4));
				emailText.setText(rs.getString(5));
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Failed to fetch data ");
			e.printStackTrace();
		}
		
	}
	
	/*----------------------------------------Back button -----------------------------------------------------------------*/
	private void backButton(String username, String password, String confirmPassword, String name, String contact, String email){
		
		// If the manager accessed this window via "Update Employee info" and changed data of any input field, throw a prompt asking
		//whether to discard changes when back button is pressed. If yes is pressed, go back to the screen where the manager is asked
		// to input the username of the employee for updation
		if(UpdateAndDeleteEmployee.employeeExists == true){
			
			if(!(username).equals(newUsernameText.getText()) || !(password).equals(newPasswordText.getText()) ||
					!(confirmPassword).equals(confirmPasswordText.getText()) || !(name).equals(newEmpNameText.getText()) ||
					!(contact).equals(contactText.getText()) || !(email).equals(emailText.getText())){
				
				int check = JOptionPane.showConfirmDialog(null, "Discard changes and go back?", "Go Back", JOptionPane.YES_NO_OPTION);
				if(check == 0){
					UpdateAndDeleteEmployee.employeeExists = false;
					dispose();
					UpdateAndDeleteEmployee employee = new UpdateAndDeleteEmployee();
					employee.setVisible(true);
				}
			}
			
			// If no change is made to the input fields, then simply go back without displaying the prompt
			else{
				UpdateAndDeleteEmployee.employeeExists = false;
				dispose();
				UpdateAndDeleteEmployee employee = new UpdateAndDeleteEmployee();
				employee.setVisible(true);
			}
		}
		
		// Else, if the manager arrived at the current window using "Add new Employee" option then if the inpput fields are all
		// empty, then go back to the Manager Screen menu without displaying a prompt
		else{
			
			if(newUsernameText.getText().equals("") && newPasswordText.getText().equals("") &&
					confirmPasswordText.getText().equals("") && newEmpNameText.getText().equals("") &&
					contactText.getText().equals("") && emailText.getText().equals("")){
			
				dispose();
				ManagerScreen manager = new ManagerScreen();
				manager.setVisible(true);
			}
			
			// Else, the user has entered some data in any text field. In this case ask a prompt whether to discard the changes
			// and go back or stay 
			else{
				
				int check = JOptionPane.showConfirmDialog(null, "Discard changes and go back?", "Go Back", JOptionPane.YES_NO_OPTION);
				if(check == 0){
					
					dispose();
					ManagerScreen manager = new ManagerScreen();
					manager.setVisible(true);
				}
			}
		}
	}
}
