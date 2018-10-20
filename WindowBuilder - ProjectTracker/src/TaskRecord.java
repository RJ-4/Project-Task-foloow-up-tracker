
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/* This class fetches and displays the task records/ task data for both manager and employee
 */
public class TaskRecord extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Connection connection = null;
	private JButton backButton;
	private DefaultTableModel model;


	/**
	 * Create the frame.
	 */
	public TaskRecord() {
		
		connection = DatabaseConnection.dbConnection();										//Establish a database connection
		
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();	//Set the title icon
		setIconImage(menuImage);
		setTitle("Task Tracker");														//Set the title 
		setBounds(100, 100, 747, 483);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));									//Set a border with dimensions
		contentPane.setLayout(new GridBagLayout());											//Set a gridbaglayout
		setContentPane(contentPane);
		
		backButton();
		
		
		table = new JTable();		//Create a new table to store the data fetched from the database
		
		// Create a new model for the table
		model = new DefaultTableModel(new String[]{"Task No.", "Task Name", "Task Description", "Task Registered By", "Task Members' Usernames"
									, "Task Members' Names", "Task Closed By", "Task Registration Date", "Task Closing Date"
									, "Task Status"}, 0);
		
		addGridBagConstraints();	//Add GridBagConstraints
		
		
		if(Login.checkManager == true){			//If this window is opened by the manager, then call the method "fetchDataManager()"
			fetchDataManager();
		}
		else{									//Else, this window is opened by employee, then call the method "fetchDataEmployee()"
			fetchDataEmployee();
		}
	
	}// End of constructor
	
	/*-------------------------------------- Add GridBagConstraints ----------------------------------------------------*/
	private void addGridBagConstraints(){
		
		// Define the GridBagConstraints for the gridBagLayout
				GridBagConstraints gc = new GridBagConstraints();
				gc.anchor = GridBagConstraints.FIRST_LINE_START;
				gc.fill = GridBagConstraints.BOTH;
				gc.gridx = 0;
				gc.gridy = 0;
				gc.weightx = 1;
				gc.weighty = 1;
				contentPane.add(new JScrollPane(table), gc);	//Add the table to the window subject to the GridBagConstraints
				
				
				// Alter the GridBagConstraints for the position of back button on the window
				gc.anchor = GridBagConstraints.CENTER;
				gc.insets = new Insets(10, 0, 5, 0);
				gc.fill = GridBagConstraints.NONE;
				gc.gridx = 0;
				gc.gridy = 1;
				gc.weightx = 0;
				gc.weighty = 0;
				contentPane.add(backButton, gc);		//Add the back button to the window subject to the new GridBagConstraints
				
	}
	
	/*------------------------------------ Back Button ------------------------------------------------------*/
	private void backButton(){
		
		backButton = new JButton("Back");
		backButton.setForeground(new Color(0, 0, 205));
		backButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		backButton.setBounds(600, 414, 103, 30);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(Login.checkManager == true){							//If the current user accessing this window is the manager,
					ManagerScreen manager = new ManagerScreen();		//then go back to the Manager Screen menu window	
					manager.setVisible(true);
				}
				else{
					EmployeeScreen emp = new EmployeeScreen();			//If the current user accessing this window is the employee,
					emp.setVisible(true);								//then go back to the Employee Screen menu window
				}
			}
		});
	}
	
	/*------------------------------------- Fetches and displays data exclusively to the manager-------------------------------------------*/
	private void fetchDataManager(){
		
		try {
			String query = "Select *from taskdata";									//Select all the records of the "taskdata" table in the "projecttracker" database
			PreparedStatement preparedStatement = connection.prepareStatement(query);	//establish the connection and prepare the statement 
			ResultSet resultSet = preparedStatement.executeQuery();						//Execute the query and store it in a resultSet
			while(resultSet.next()){							// Continue for all the matching rows
				
				String no = resultSet.getString(1);					//Get the task no.
				String name = resultSet.getString(2);				//Get the task name
				String description = resultSet.getString(3);		//Get the task description
				String rBy = resultSet.getString(4);				//Get the name of the task who registered the task
				String membersUsername = resultSet.getString(10);	//Get the task members' usernames
				String members = resultSet.getString(5);			//Get the task members
				String rDate = resultSet.getString(6);				//Get the registration date
				String cBy = resultSet.getString(7);				//Get the name of the person who closed the task 
				String cDate = resultSet.getString(8);				//Get the closing date
				String status = resultSet.getString(9);				//Get the status of the task
				
				membersUsername = membersUsername.substring(0, membersUsername.length() - 2);
				members = members.substring(0, members.length() - 2);
				
				model.addRow(new Object[]{no, name, description, rBy, membersUsername, members, rDate, cBy, cDate, status}); //Add the data to the model
			}
			table.setModel(model);		
			preparedStatement.close();						//Close the connection to the database
			resultSet.close();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cannot fetch data");
			e.printStackTrace();
		}
	}

	/*---------------------------------- Fetches and displays data to the employee ----------------------------------------------*/
	private void fetchDataEmployee(){
		
		try {
			String query = "Select *from taskdata where Task_member_username like ?";		//Select all the data in the database where Task_Members contain
			PreparedStatement preparedStatement = connection.prepareStatement(query);	//the name of the person who is currently logged in i.e.
			preparedStatement.setString(1, '%'+Login.usernameValidation+'%');				//"Login.nameValidation"
			ResultSet resultSet = preparedStatement.executeQuery();						//Execute the query and store the data fetched in resultset
			while(resultSet.next()){							// Continue for all the matching rows
				
				String no = resultSet.getString(1);					//Get the task no.
				String name = resultSet.getString(2);				//Get the task name
				String description = resultSet.getString(3);		//Get the task description
				String rBy = resultSet.getString(4);				//Get the name of the task who registered the task
				String membersUsername = resultSet.getString(10);	//Get the task members' usernames
				String members = resultSet.getString(5);			//Get the task members
				String rDate = resultSet.getString(6);				//Get the registration date
				String cBy = resultSet.getString(7);				//Get the name of the person who closed the task 
				String cDate = resultSet.getString(8);				//Get the closing date
				String status = resultSet.getString(9);				//Get the status of the task
				
				members = members.substring(0, members.length() - 2);
				membersUsername = membersUsername.substring(0, membersUsername.length() - 2);
				
				model.addRow(new Object[]{no, name, description, rBy, membersUsername, members, rDate, cBy, cDate, status});	// Add the data to the model
			}
			table.setModel(model);
			preparedStatement.close();													//Close the connection
			resultSet.close();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cannot fetch data");
		}
		
	}
}
