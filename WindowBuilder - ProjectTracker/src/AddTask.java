import java.awt.Image;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.DocFlavor.STRING;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/* This class is used exclusively by the EMPLOYEE and performs following functions:
 * 1. Add a new task
 * 2. Update an existing task
 * 3. Fetch the data from the database and insert it into the input fields when the user selects "update task"
 * */
public class AddTask extends JFrame {

	private JPanel contentPane;
	
	private JLabel lblNewLabel;
	
	private JTextField noText;
	private JTextField nameText;
	private JTextArea membersUsernameTextArea;
	private JTextArea descriptionText;
	private JTextArea membersText;
	private JTextField closingDateText;
	private JTextField closingMonthText;
	private JTextField closingYearText;
	private JRadioButton openRadioButton;
	private JRadioButton closedRadioButton;
	private ButtonGroup group;
	private JComboBox memberComboBox;
	
	private String taskStatus = "Open";
	
	// String variables for configuring back button 
	private String noTextBackButton, nameTextBackButton, descriptionTextBackButton, membersTextBackButton;
	private int rMonthTextBackButton, cMonthTextBackButton;
	private int rDateTextBackButton, rYearTextBackButton, cDateTextBackButton, cYearTextBackButton;
	
	private Connection connection = null;
	
	// ScrollPanes for textareas
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	
	// Label for red cross delete image
	private JLabel deleteImageLabel1;
	
	// ComboBoxes for registration and closing dates
	public static JComboBox registrationDateBox;
	public static JComboBox registrationMonthBox;
	public static JComboBox registrationYearBox;
	public static JComboBox closingDateBox;
	public static JComboBox closingMonthBox;
	public static JComboBox closingYearBox;
	
	// Label to display Clock 
	public static JLabel clockLabel;
	
	/**
	 * Create the frame.
	 */
	public AddTask() {
		
		connection = DatabaseConnection.dbConnection();			//Connect to the database
		setTitle("Task Tracker");
		setResizable(false);
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();
		setIconImage(menuImage);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1192, 740);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		// Add all the labels to the window
		addLabels();
		
		// Add all the text fields to the window
		addTextFields();
		
		// Add Combo Boxes to the window
		addComboBoxes();
		
		//Add items in combo box
		addItemsComboBox();
		
		// Add the save button
		addSaveButton();
		
		// Add reset button
		addResetButton();
		
		// Add radio buttons for Task Status
		addRadioButtons();
		
		// Get the data from database and store it in the input fields
		fetchDataFromDatabase();
		
		// Add back button
		addBackButton();
		
		
	}// End of constructor
	
	/*---------------------------------- Save data for NEW Task---------------------------------------*/
	private void saveData(){
		
		try{
			String query = "insert into taskdata values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 	//'?' corresponds to columns in the taskdata table
			PreparedStatement ps = connection.prepareStatement(query);
			
			
			String taskNo = noText.getText();					// Fetch the value of Task No. from the input field
			String taskName = nameText.getText();				// Fetch the value of task Name from the input field
			String taskDescription = descriptionText.getText();	// Fetch the value of task Description from the input field
			String taskRegBy = Login.nameValidation;				// Fetch the value of task Registered By 
			String taskMembers = membersText.getText();			// Fetch the value of task Members from the input field
			String taskClosedBy = "";							// Set the value to be blank
			String taskClosingDate = "";							// Set the value to be blank
			taskStatus = "Open";							// Set the task status as open
			String username = membersUsernameTextArea.getText();			// Fetch the value of the employee username
			
			
			// Perform validation on the input values
			TaskValidation validation = new TaskValidation(taskNo, taskName, taskDescription, taskMembers);
			boolean check = validation.newTaskValidate();	// If the validation fails, check = false, else check = true
			
			
			// If inputs are approved, complete the query
			if(check == true){
				
				// If all the fields are validated, get the data from the combo boxes
				int regDate = (int)registrationDateBox.getSelectedItem();
				String regMonth = (String)registrationMonthBox.getSelectedItem();
				int regYear = (int)registrationYearBox.getSelectedItem();
				
				String taskRegDate = regDate + " " + regMonth + " " + regYear;	//Append the values of Registration date, month and year to form the complete date
				
				ps.setString(1, taskNo);
				ps.setString(2, taskName);
				ps.setString(3, taskDescription);
				ps.setString(4, taskRegBy);
				ps.setString(5, taskMembers);
				ps.setString(6, taskClosedBy);
				ps.setString(7, taskRegDate);
				ps.setString(8, taskClosingDate);
				ps.setString(9, taskStatus);
				ps.setString(10, username);
			
				ps.execute();		// Execute the query
			
				JOptionPane.showMessageDialog(null, "Task Saved");	// Show a message if the task is successfully saved
				ps.close();			// Terminate the connection to the database
				
				if(ManagerScreen.userIsManager == false){
					
					UpdateCheck.taskExists = false;		// TO prevent the screen to go into update mode next time , set the variable as false
					dispose();									// Close this window and go back to the Employee Screen menu
					EmployeeScreen emp = new EmployeeScreen();	
					emp.setVisible(true);
				}
				else{
					
					ManagerScreen.userIsManager = false;
					dispose();
					ManagerScreen manager = new ManagerScreen();
					manager.setVisible(true);
				}
			}
		}
		catch(Exception e1){
			JOptionPane.showMessageDialog(null, "Task No. already exists");
			e1.printStackTrace();
		}
	}
	
	
	/*----------------------------------- Update data for updating an existing task--------------------------*/
	private void updateData(){
		
		try{
			// SQL Query to update data
			String query = "Update taskdata set Task_No = ?, Task_name = ?, Task_Description = ?, Task_members = ?,"
					+ "Task_Closed_By = ?, Task_Registration_date = ?, Task_Closed_date = ?, status = ?, Task_member_username = ?"
					+ " where Task_No = ? and Task_Member_Username like ?";
			
			PreparedStatement ps = connection.prepareStatement(query);
			
			String no = noText.getText();					// Fetch the value of Task No. from the input field 
			String name = nameText.getText();				// Fetch the value of Task Name from the input field
			String description = descriptionText.getText();	// Fetch the value of Task Description from the input field
			String members = membersText.getText();			// Fetch the value of Task Members from the input field
			String membersUsername = membersUsernameTextArea.getText(); // Fetch the value of Task Members' Username from the input field
			
			
			String taskClosedBy = Login.nameValidation;	// Set the value as the person who is currently logged in and decides to close the task
			
			
			taskStatus = "Open";
			
			
			
			// Validate the inputs 
			TaskValidation taskValidation = new TaskValidation(no, name, description, members, taskStatus);
			boolean check = taskValidation.updateTaskValidate();		// If the validation fails, check = false, else check = true
			boolean checkClosingDate = false;
			// If inputs are approved, complete the query
			if(check == true){
				
				
				
				int regDate = (int)registrationDateBox.getSelectedItem();		// Fetch the value of registration date from the input field
				String regMonth = (String)registrationMonthBox.getSelectedItem();	// Fetch the value of registration month from the input field
				int regYear = (int)registrationYearBox.getSelectedItem(); 	// Fetch the value of registration year from the input field
				
				String taskRegDate = regDate + " " + regMonth + " " + regYear;	//Append the registration date, month and year to form a complete registration date
				
				String taskCloseDate = "";
				
				
				if(openRadioButton.isSelected()){		// If the open radio button is selected, then set the status as open and
					taskStatus = "Open";					// change the values of "task closed by" and "task closed date" as ""
					
					taskCloseDate = "";
					
					taskClosedBy = "";
					
					checkClosingDate = true;
				}
				
				else{									// Else if the closed task button is selected, then set the status as closed
					
					taskStatus = "Closed";
					TaskValidation taskValidation2 = new TaskValidation(no, name, description, members, taskStatus);
					checkClosingDate = taskValidation2.updateTaskValidate();
					if(checkClosingDate == true){
						
						int clDate = (int)closingDateBox.getSelectedItem();		// Fetch the value of closing date from the input field
						String clMonth = (String)closingMonthBox.getSelectedItem();		// Fetch the value of closing month from the input field
						int clYear = (int)closingYearBox.getSelectedItem();		// Fetch the value of closing year from the input field
						
						taskCloseDate = clDate + " " + clMonth + " " + clYear;	//Append the closing date, month and year to form a complete closing date
					}
				}
				
				if(checkClosingDate == true){
					ps.setString(1, no);
					ps.setString(2, name);
					ps.setString(3, description);
					ps.setString(4, members);	
					ps.setString(5, taskClosedBy);
					ps.setString(6, taskRegDate);
					ps.setString(7, taskCloseDate);
					ps.setString(8, taskStatus);
					ps.setString(9, membersUsername);
					ps.setString(10, UpdateCheck.taskNoUpdate);
					ps.setString(11, '%' + Login.usernameValidation + '%');
					
					ps.execute();												// Execute the query 
					
					JOptionPane.showMessageDialog(null, "Task Updated");		// Show a message if the task is updated
					ps.close();													// Close the connection to the database
					
					UpdateCheck.taskExists = false;					// Prevent the update task screen from loading instead 
					dispose();											// of new task. Close this window and open the Employee
					EmployeeScreen emp = new EmployeeScreen();			// Screen menu
					emp.setVisible(true);
				}
			}
			ps.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Task No. already exists");
			e.printStackTrace();
		}
	}
	
	
	/*----------------------------------- Add items in combo box --------------------------------------------*/
	private void addItemsComboBox(){
		
		try{
			
			String query = "Select username from login";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			memberComboBox.addItem("Select");
			while(rs.next()){
				
				memberComboBox.addItem(rs.getString(1));
			}
			rs.close();
			ps.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot fetch data");
			e.printStackTrace();
		}
		
		ComboBoxAddItem item = new ComboBoxAddItem();
		item.addItem();
		
		
	}
	
	
	/*----------------------------------- Code for Back Button ----------------------------------------------*/
	private void backButton(String no, String name, String description, String members, int rDate, int rMonth, int rYear,
			int cDate, int cMonth, int cYear){
		
		// If the user selects "update task"
		if(UpdateCheck.taskExists == true){
			
			// If any of the input field is altered
			if(!no.equals(noText.getText()) || !name.equals(nameText.getText()) || !description.equals(descriptionText.getText()) ||
					!members.equals(membersText.getText()) || rDate != registrationDateBox.getSelectedIndex() || 
					rMonth != registrationMonthBox.getSelectedIndex() || rYear != registrationYearBox.getSelectedIndex() || 
					cDate != closingDateBox.getSelectedIndex() || cMonth != closingMonthBox.getSelectedIndex() || 
					cYear != closingYearBox.getSelectedIndex()){
				
				// When the back button is pressed, show a prompt asking for confirmation
				int action = JOptionPane.showConfirmDialog(null, "Discard changes and go back?", "Back", JOptionPane.YES_NO_OPTION);
				
				// If yes, then close the window and open the window asking for the task no for updating a task
				if(action == 0){
					UpdateCheck.taskExists = false;
					dispose();
					UpdateCheck update = new UpdateCheck();
					update.setVisible(true);
				}
			}
			
			// If no change is made, simply exit this window and open the aforementioned window
			else{
				UpdateCheck.taskExists = false;
				dispose();
				UpdateCheck update = new UpdateCheck();
				update.setVisible(true);
			}
		}
	
		
		// Else if the user has selected "new task"
		else{
			
			// If no change is made and all the fields contain default values, when back button is pressed, simply close this 
			// window and open the previous one
			if(noText.getText().equals("") && nameText.getText().equals("") && descriptionText.getText().equals("") && 
					membersText.getText().equals("") && registrationDateBox.getSelectedIndex() == -1 && 
					registrationMonthBox.getSelectedIndex() == -1 && registrationYearBox.getSelectedIndex() == -1){
				
				if(ManagerScreen.userIsManager == false){
					
					UpdateCheck.taskExists = false;
					dispose();
					EmployeeScreen emp = new EmployeeScreen();
					emp.setVisible(true);
				}
				else{
					
					ManagerScreen.userIsManager = false;
					dispose();
					ManagerScreen manager = new ManagerScreen();
					manager.setVisible(true);
				}
				
			}
			
			// If any of the input field is changed, when the back button is pressed, ask the user for the confirmation
			else{
				
				int action = JOptionPane.showConfirmDialog(null, "Discard changes and go back?", "Back", JOptionPane.YES_NO_OPTION);
				
				// If yes, then close the current window and open the previous one
				if(action == 0 && ManagerScreen.userIsManager == false){
					
					UpdateCheck.taskExists = false;
					dispose();
					EmployeeScreen emp = new EmployeeScreen();
					emp.setVisible(true);
				}
				
				else if(action == 0 && ManagerScreen.userIsManager == true){
					
					ManagerScreen.userIsManager = false;
					dispose();
					ManagerScreen manager = new ManagerScreen();
					manager.setVisible(true);
				}
			}
		}
	}

	
	/*------------------------------------Code for Reset Button-----------------------------------------------*/
	private void resetButton(){
		
		// If all fields are the same as when the screen was opened then do nothing
		if(noText.getText().equals("") && nameText.getText().equals("") && descriptionText.getText().equals("") && 
				membersText.getText().equals("")){}
		
		// Otherwise if the any change is made ask the user for the confirmation if he/she wants to reset the data when the reset button is clicked
		else{
			
			int action = JOptionPane.showConfirmDialog(null, "Reset the data?", "Reset", JOptionPane.YES_NO_OPTION);
			
			// If yes, then set the fields to their default values
			if(action == 0){
				noText.setText("");
				nameText.setText("");
				descriptionText.setText("");
				membersText.setText("");
				membersUsernameTextArea.setText("");
			}
		}
	}
	
	
	/*------------------------Fetches the data from database and insert it into the fields during Update Task----------------------*/
	
	private void fetchDataFromDatabase(){
		
		if(UpdateCheck.taskExists == true){
			
			//Adding data from database to fields
			try{
				String fetchDataQuery = "Select *from taskdata where Task_no = ? and Task_member_username like ?";
					
				PreparedStatement ps = connection.prepareStatement(fetchDataQuery);
				ps.setString(1, UpdateCheck.taskNoUpdate);			// Pass the task no entered by the user on the previous screen (UpdateCheck.java)
				ps.setString(2, '%' + Login.usernameValidation + '%');		// Pass the name of the employee currently logged in
				ResultSet rs = ps.executeQuery();						// Execute the query
					
				if(rs.next()){											// If a record is found
					
					String regDate = "" , clDate = "";				// Take string variables to segregate the combined date fetched
					String regMonth = "" , clMonth = "";			// from the database and store it as date, month and year for
					String regYear = "" , clYear = "";				// different text fields
					
					//Get Task Registration Date from the database
					String registration = rs.getString(7);
					
					//Get Task Closing Date from database
					String closing = rs.getString(8);
					
					
					if(registration.charAt(2) == ' '){
						
						regDate = regDate + registration.charAt(0) + registration.charAt(1);	
						
						regMonth = registration.substring(3, registration.length() - 5);
						
						regYear = registration.substring(registration.length() - 4, registration.length());
					}
					
					else if(registration.charAt(1) == ' '){
						
						regDate = regDate + registration.charAt(0);
						
						regMonth = registration.substring(2, registration.length() - 5);
						
						regYear = registration.substring(registration.length() - 4, registration.length());
					}
					
					int rDate = Integer.parseInt(regDate);
					int rYear = Integer.parseInt(regYear);
					
					// Fetch the values and set them in the input fields
					noText.setText(rs.getString(1));
					nameText.setText(rs.getString(2));
					descriptionText.setText(rs.getString(3));
					membersText.setText(rs.getString(5));
					membersUsernameTextArea.setText(rs.getString(10));
				
					registrationDateBox.setSelectedItem(rDate);
					registrationMonthBox.setSelectedItem(regMonth);
					registrationYearBox.setSelectedItem(rYear);
					
					// If the closing date in the database has a value and is not "", separate the date, month and year text fields
					// and insert the data similar to registration date above
					if(!"".equals(closing)){
				
						if(closing.charAt(2) == ' '){
							
							clDate = clDate + closing.charAt(0) + closing.charAt(1);	
							
							clMonth = closing.substring(3, closing.length() - 5);
							
							clYear = closing.substring(closing.length() - 4, closing.length());
						}
						
						else if(closing.charAt(1) == ' '){
							
							clDate = clDate + closing.charAt(0);
							
							clMonth = closing.substring(2, closing.length() - 5);
							
							clYear = closing.substring(closing.length() - 4, closing.length());
						}
						
						int cDate = Integer.parseInt(clDate);
						int cYear = Integer.parseInt(clYear);
						
						closingDateBox.setSelectedItem(cDate);
						closingMonthBox.setSelectedItem(clMonth);
						closingYearBox.setSelectedItem(cYear);
					}
					
					
					String status = "";
					
					//Fetch Task status. If the Task status in database is specified as "open", then select the open radio 
					// button. Else if the Task status in the database is "closed", then select the closed radio button
					status = rs.getString(9);
					if(status.equals("Open")){
						openRadioButton.setSelected(true);
					}
					else if(status.equals("Closed")){
						closedRadioButton.setSelected(true);
					}
				}
				rs.close();			// Terminate the connection to the database
				ps.close();
 			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Failed to retrieve data");
				e.printStackTrace();
			}
		}
	}

	
	/*---------------------------------------- Add Combo Boxes --------------------------------------------------*/
	private void addComboBoxes(){
		
		
		// ComboBox for Task Member Username
		memberComboBox = new JComboBox();
		memberComboBox.setBackground(new Color(255, 255, 255));
		
		memberComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String query = "Select username, Employee_Name from login where username = ?";
					PreparedStatement ps = connection.prepareStatement(query);
					
					String itemSelected = (String)memberComboBox.getSelectedItem();
					ps.setString(1, itemSelected);
					
					
					String usernameText = membersUsernameTextArea.getText();
					if(usernameText.indexOf(itemSelected) == -1){
						
						ResultSet rs = ps.executeQuery();
						
						if(rs.next()){						
						
							membersUsernameTextArea.append(" " + rs.getString(1) + ", ");
							membersText.append(" " + rs.getString(2) + ", ");
						}
					}
					ps.close();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to fetch data");
					e.printStackTrace();
				}
			}
		});
		memberComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		memberComboBox.setBounds(901, 328, 235, 28);
		contentPane.add(memberComboBox);
		
		
		
		
		// ComboBox for Registration Year
		registrationYearBox = new JComboBox();
		registrationYearBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ComboBoxAddItem item = new ComboBoxAddItem();
				item.selectRegistrationDateYear();
			}
		});
		registrationYearBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		registrationYearBox.setBounds(1069, 547, 67, 28);
		contentPane.add(registrationYearBox);
		
		

		// ComboBox for Registration Month
		registrationMonthBox = new JComboBox();
		registrationMonthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComboBoxAddItem item = new ComboBoxAddItem();
				item.selectRegistrationMonth();
			}
		});
		registrationMonthBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		registrationMonthBox.setBounds(962, 547, 97, 28);
		contentPane.add(registrationMonthBox);
		
		
		// ComboBox for Registration Date
		registrationDateBox = new JComboBox();
		registrationDateBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		registrationDateBox.setBounds(900, 547, 56, 28);
		contentPane.add(registrationDateBox);
				
	
		if(UpdateCheck.taskExists == true){			// Only add the following TextFields if the user is updating an existing task

			// ComboBox for Closing Year
			closingYearBox = new JComboBox();
			closingYearBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ComboBoxAddItem item = new ComboBoxAddItem();
					item.selectClosingDateYear();
				}
			});
			closingYearBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			closingYearBox.setBounds(1069, 589, 67, 28);
			contentPane.add(closingYearBox);
		
			// ComboBox for Closing Month
			closingMonthBox = new JComboBox();
			closingMonthBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ComboBoxAddItem item = new ComboBoxAddItem();
					item.selectClosingMonth();
				}
			});
			closingMonthBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			closingMonthBox.setBounds(962, 589, 97, 28);
			contentPane.add(closingMonthBox);
					
			// ComboBox for Closing Date
			closingDateBox = new JComboBox();
			closingDateBox.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			closingDateBox.setBounds(900, 589, 56, 28);
			contentPane.add(closingDateBox);
		}
	}
	
	
	/*---------------------------------------- Add Radio Buttons for Task Status --------------------------------*/
	private void addRadioButtons(){
		
		if(UpdateCheck.taskExists == true){						// If the user is updating the task
			
			// Radio Button for Task Status: "Open"
			openRadioButton = new JRadioButton("Open");				
			openRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					closingDateBox.setSelectedIndex(-1);		// Set the closing date, month and year fields as non-editable
					closingDateBox.setEnabled(false);			// and set the default value when the open button is selected
					
					closingMonthBox.setSelectedIndex(-1);
					closingMonthBox.setEnabled(false);
					
					closingYearBox.setSelectedIndex(-1);
					closingYearBox.setEnabled(false);
				}
			});
			openRadioButton.setBackground(new Color(230, 230, 250));
			openRadioButton.setForeground(new Color(0, 0, 0));
			openRadioButton.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			openRadioButton.setBounds(896, 620, 67, 32);
			contentPane.add(openRadioButton);
		
			
			// Radio Button for Task Status: "Closed"
			closedRadioButton = new JRadioButton("Closed");
			closedRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					closingDateBox.setEnabled(true);	// If Closed radio button is selected, then set the closing date,
					closingMonthBox.setEnabled(true);	// month and year text fields as editable
					closingYearBox.setEnabled(true);
				}
			});
			closedRadioButton.setBackground(new Color(230, 230, 250));
			closedRadioButton.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			closedRadioButton.setBounds(962, 620, 67, 32);
			contentPane.add(closedRadioButton);
			
			
			// Declare a new ButtonGroup and add the Open and Closed Radio buttons to it, so that when one radio button is selected
			// the other is automatically deselected
			group = new ButtonGroup();
			group.add(openRadioButton);
			group.add(closedRadioButton);
			
			// Insert the values from the database into the input fields
			fetchDataFromDatabase();
			
			// If the open radio button is selected 
			if(openRadioButton.isSelected()){
				
				closingDateBox.setSelectedIndex(-1);		// Set the closing date, month and year fields as non-editable
				closingDateBox.setEnabled(false);			// and set the default value when the open button is selected
				
				closingMonthBox.setSelectedIndex(-1);
				closingMonthBox.setEnabled(false);
				
				closingYearBox.setSelectedIndex(-1);
				closingYearBox.setEnabled(false);				
							
			}
			
			// Else if the closed radio button is selected
			else{
				
				closingDateBox.setEnabled(true);	// If Closed radio button is selected, then set the closing date,
				closingMonthBox.setEnabled(true);	// month and year text fields as editable
				closingYearBox.setEnabled(true);
			}
				
		}
	}
	
	
	/*-------------------------------------------- Add Save/Update Button ----------------------------------------------*/
 	private void addSaveButton(){
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action; 
				
				// If the user is adding a new task, when the save button is clicked, a prompt is displayed asking for the confirmation
				if(UpdateCheck.taskExists == false){
					action = JOptionPane.showConfirmDialog(null, "Save Task?", "Save", JOptionPane.YES_NO_OPTION);
				}
				
				// If If the user is updating an existing task, when the save button is clicked, a prompt is displayed asking for the confirmation
				else{
					action = JOptionPane.showConfirmDialog(null, "Update Task?", "Update", JOptionPane.YES_NO_OPTION);
				}
				
				//action = 0 -> Yes button 
				if(action == 0){
					
					//Create a new record and save it in database 
					if(UpdateCheck.taskExists == false){
						saveData();
					}
					
					//Update the existing record and save it in the database
					else{
						updateData();
					}
				}
			}
		});
		saveButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		saveButton.setForeground(new Color(0, 0, 205));
		saveButton.setHorizontalAlignment(SwingConstants.CENTER);
		saveButton.setBounds(874, 661, 115, 39);
		contentPane.add(saveButton);
	}

 	
 	/*--------------------------------------------- Add Back Button ----------------------------------------------------*/
 	private void addBackButton(){
 		
 		JButton backButton = new JButton("<< Back");			
		backButton.setForeground(new Color(0, 0, 205));
		backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		backButton.setBounds(45, 69, 89, 32);
		contentPane.add(backButton);
		
		
		// If the employee is updating the data, then when the window is opened, fetch the values in the input fields and set them
		// into string variables declared globally

		if(UpdateCheck.taskExists == true){
			noTextBackButton = noText.getText();
			nameTextBackButton = nameText.getText();
			descriptionTextBackButton = descriptionText.getText();
			membersTextBackButton = membersText.getText();
			rDateTextBackButton = (int)registrationDateBox.getSelectedIndex();
			rMonthTextBackButton = (int)registrationMonthBox.getSelectedIndex();
			rYearTextBackButton = (int)registrationYearBox.getSelectedIndex();
			cDateTextBackButton = (int)closingDateBox.getSelectedIndex();
			cMonthTextBackButton = (int)closingMonthBox.getSelectedIndex();
			cYearTextBackButton = (int)closingYearBox.getSelectedIndex();
		}
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Pass the values stored above through the backButton() method to implement its functionality
				backButton(noTextBackButton, nameTextBackButton, descriptionTextBackButton, membersTextBackButton,
						rDateTextBackButton, rMonthTextBackButton, rYearTextBackButton, cDateTextBackButton, cMonthTextBackButton,
						cYearTextBackButton);
			}
		});	
 	}
	
	
 	/*-------------------------------------------- Add Reset Button -----------------------------------------------------*/
	private void addResetButton(){
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					resetButton();					// Call the reset method
				}
				catch(Exception e1){
				}
			}
		});
		resetButton.setForeground(new Color(0, 0, 205));
		resetButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		resetButton.setBounds(999, 661, 115, 39);
		contentPane.add(resetButton);
	}

	
	/*-------------------------------------------- Add Text Fields ------------------------------------------------------*/
	private void addTextFields(){
		
		// TextField for Task No.
		noText = new JTextField();
		noText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		noText.setBounds(901, 117, 235, 32);
		contentPane.add(noText);
		noText.setColumns(10);
		
		// TextField for Task Name
		nameText = new JTextField();
		nameText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		nameText.setBounds(900, 167, 236, 32);
		contentPane.add(nameText);
		nameText.setColumns(10);
		
		
		
		// TextField for Task Description
		descriptionText = new JTextArea();
		descriptionText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(descriptionText);
		scrollPane.setBounds(900, 216, 235, 94);
		contentPane.add(scrollPane);
		
		
		// TextArea for Task Members Username
		membersUsernameTextArea = new JTextArea();
		membersUsernameTextArea.setEditable(false);
		membersUsernameTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		membersUsernameTextArea.setLineWrap(true);
		membersUsernameTextArea.setWrapStyleWord(true);
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(membersUsernameTextArea);
		scrollPane_1.setBounds(901, 367, 235, 71);
		contentPane.add(scrollPane_1);
		
		
	
		// TextField for Task Members
		membersText = new JTextArea();
		membersText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		membersText.setLineWrap(true);
		membersText.setWrapStyleWord(true);
		membersText.setEditable(false);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportView(membersText);
		scrollPane_2.setBounds(900, 457, 235, 71);
		contentPane.add(scrollPane_2);		
	
	}
	
	
	/*-------------------------------------------- Add Labels ------------------------------------------------------------*/
	private void addLabels(){
		
		// Heading label
		if(UpdateCheck.taskExists == false){					// If the user clicked new task, set the heading as
			 lblNewLabel = new JLabel("NEW TASK");			// New Task
		}
		else{													// Else if the use clicked update task, set the heading as
			 lblNewLabel = new JLabel("UPDATE TASK");		// Update Task
		}
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 0, 139));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(369, 11, 407, 65);
		contentPane.add(lblNewLabel);
		
		
		// Label to add the image
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(21, 129, 671, 571);
		contentPane.add(imageLabel);
		Image addTaskImage = new ImageIcon(this.getClass().getResource("/add_task.jpg")).getImage();
		imageLabel.setIcon(new ImageIcon(addTaskImage));
		
		
		// Label to add Task No.
		JLabel noLabel = new JLabel("Task No.");
		noLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noLabel.setForeground(new Color(0, 0, 205));
		noLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		noLabel.setBounds(713, 113, 177, 39);
		contentPane.add(noLabel);
		
		
		// Label to add Task Name
		JLabel nameLabel = new JLabel("Task Name");
		nameLabel.setForeground(new Color(0, 0, 205));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		nameLabel.setBounds(713, 163, 177, 39);
		contentPane.add(nameLabel);
		
		
		// Label to add Task Description
		JLabel descriptionLabel = new JLabel("Task Description");
		descriptionLabel.setForeground(new Color(0, 0, 205));
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		descriptionLabel.setBounds(713, 213, 177, 39);
		contentPane.add(descriptionLabel);
		
		
		// Label to add Task Member Username
		JLabel taskMemberUsername = new JLabel("Task Member Username");
		taskMemberUsername.setForeground(new Color(0, 0, 205));
		taskMemberUsername.setFont(new Font("Times New Roman", Font.BOLD, 16));
		taskMemberUsername.setHorizontalAlignment(SwingConstants.CENTER);
		taskMemberUsername.setBounds(713, 322, 177, 39);
		contentPane.add(taskMemberUsername);
		
		
		
		//Delete text image label 1
		deleteImageLabel1 = new JLabel("");
		deleteImageLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				membersUsernameTextArea.setText("");
				membersText.setText("");
			}
		});
		Image deleteImage1 = new ImageIcon(this.getClass().getResource("/delete_button.png")).getImage();
		deleteImageLabel1.setIcon(new ImageIcon(deleteImage1));
		deleteImageLabel1.setBounds(1138, 367, 23, 21);
		contentPane.add(deleteImageLabel1);
		
		
		// Label to add Task Members
		JLabel membersLabel = new JLabel("Task Members");
		membersLabel.setForeground(new Color(0, 0, 205));
		membersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		membersLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		membersLabel.setBounds(713, 447, 177, 39);
		contentPane.add(membersLabel);
		
		// Label to add Task Registration Date
		JLabel regDateLabel = new JLabel("Task Registration Date");
		regDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		regDateLabel.setForeground(new Color(0, 0, 205));
		regDateLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		regDateLabel.setBounds(713, 536, 177, 39);
		contentPane.add(regDateLabel);
		
		
		// Label to add clock 
		clockLabel = new JLabel("");
		clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		clockLabel.setForeground(new Color(0, 0, 139));
		clockLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		clockLabel.setBounds(901, 51, 235, 39);
		contentPane.add(clockLabel);
		Clock clock = new Clock();
		clock.clock();
		
		
		
		if(UpdateCheck.taskExists == true){					// Only show this label when the user is updating a task
			
			// Label to add Task Closing Date
			JLabel closingDateLabel = new JLabel("Task Closing Date");
			closingDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
			closingDateLabel.setForeground(new Color(0, 0, 205));
			closingDateLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			closingDateLabel.setBounds(713, 578, 177, 39);
			contentPane.add(closingDateLabel);
		
			// Label to add Task status
			JLabel lblNewLabel_1 = new JLabel("Task Status");
			lblNewLabel_1.setForeground(new Color(0, 0, 205));
			lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(713, 618, 177, 32);
			contentPane.add(lblNewLabel_1);		
		}
	}
}
