import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

/* This class is exclusively for the MANAGER and performs certain functions:
 * 1. Update the records of an existing employee
 * 2. Delete an existing employee
 * 3. Delete an existing task
 * */
public class UpdateAndDeleteEmployee extends JFrame {

	private JPanel contentPane;
	private JTextField updateEmployeeText;
	private Connection connection = null;
	
	public static String employeeUsername;
	public static boolean employeeExists = false;		// Take a boolean variable to check whether the manager wants to update the info 
	private JTextField deleteEmployeeText;
	private JTextField deleteTaskText;
	
	private JTabbedPane tabbedPane;

	/**
	 * Create the frame.
	 */
	public UpdateAndDeleteEmployee() {
		
		connection = DatabaseConnection.dbConnection();		//Make a connection to the database
		setResizable(false);
		setTitle("Task Tracker");
		Image iconImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();	// Set the Icon image
		setIconImage(iconImage);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 882, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		// Create a tabbedPane to create a window comprising of multiple tabs
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(230, 230, 250));
		tabbedPane.setBounds(10, 11, 856, 549);
		contentPane.add(tabbedPane);
		
		// Update employee tab
		updateEmployeeTab();
		
		
		// Delete employee tab
		deleteEmployeeTab();
		
		
		//Delete task tab
		deleteTaskTab();
		
	}
	/*-------------------------------------------- Delete a task Tab -----------------------------------------------------*/
	private void deleteTaskTab(){
		
		JPanel deleteTaskPanel = new JPanel();
		deleteTaskPanel.setBackground(new Color(230, 230, 250));
		tabbedPane.addTab("Delete Task", null, deleteTaskPanel, null);
		deleteTaskPanel.setLayout(null);
		
		// Label for heading
		JLabel deleteTaskTitle = new JLabel("DELETE TASK");
		deleteTaskTitle.setHorizontalAlignment(SwingConstants.CENTER);
		deleteTaskTitle.setForeground(new Color(0, 0, 139));
		deleteTaskTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
		deleteTaskTitle.setBounds(208, 24, 411, 49);
		deleteTaskPanel.add(deleteTaskTitle);
		
		
		// Label for adding image
		JLabel deleteTaskImageLabel = new JLabel("");
		Image deleteTaskImage = new ImageIcon(this.getClass().getResource("/delete_task.png")).getImage();
		deleteTaskImageLabel.setIcon(new ImageIcon(deleteTaskImage));
		deleteTaskImageLabel.setBounds(49, 104, 411, 406);
		deleteTaskPanel.add(deleteTaskImageLabel);
		
		
		// Label for task no.
		JLabel deleteTaskLabel = new JLabel("Enter the task no. to be deleted");
		deleteTaskLabel.setForeground(new Color(0, 0, 205));
		deleteTaskLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		deleteTaskLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deleteTaskLabel.setBounds(528, 120, 275, 43);
		deleteTaskPanel.add(deleteTaskLabel);
		
		
		//TextField for entering task No.
		deleteTaskText = new JTextField();
		deleteTaskText.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		deleteTaskText.setBounds(562, 198, 214, 35);
		deleteTaskPanel.add(deleteTaskText);
		deleteTaskText.setColumns(10);
		
		
		// Delete button to delete the task 
		JButton deleteTaskButton = new JButton("Delete");
		deleteTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteTask();	// Method call to delete the task when the delete button is clicked
			}
		});
		deleteTaskButton.setForeground(new Color(0, 0, 205));
		deleteTaskButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		deleteTaskButton.setBounds(550, 290, 107, 43);
		deleteTaskPanel.add(deleteTaskButton);
		
		
		// Back button
		JButton deleteTaskBackButton = new JButton("Back");
		deleteTaskBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ManagerScreen managerScreen = new ManagerScreen();
				managerScreen.setVisible(true);
			}
		});
		deleteTaskBackButton.setForeground(new Color(0, 0, 205));
		deleteTaskBackButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		deleteTaskBackButton.setBounds(685, 290, 107, 43);
		deleteTaskPanel.add(deleteTaskBackButton);
		tabbedPane.setBackgroundAt(2, new Color(255, 255, 255));
	}

	/*-------------------------------------------- Delete an employee Tab ------------------------------------------------------*/
	private void deleteEmployeeTab(){
		
		JPanel deleteEmployeePanel = new JPanel();
		deleteEmployeePanel.setBackground(new Color(230, 230, 250));
		tabbedPane.addTab("Delete Employee", null, deleteEmployeePanel, null);
		tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
		deleteEmployeePanel.setLayout(null);
		
		// Label for heading
		JLabel deleteEmployeeTitle = new JLabel("DELETE EMPLOYEE");
		deleteEmployeeTitle.setForeground(new Color(0, 0, 139));
		deleteEmployeeTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
		deleteEmployeeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		deleteEmployeeTitle.setBounds(211, 11, 394, 51);
		deleteEmployeePanel.add(deleteEmployeeTitle);
		
		// Label for adding image
		JLabel deleteEmployeeImageLabel =new JLabel("");
		deleteEmployeeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Image deleteEmployeeImage = new ImageIcon(this.getClass().getResource("/delete_employee.png")).getImage();
		deleteEmployeeImageLabel.setIcon(new ImageIcon(deleteEmployeeImage));
		deleteEmployeeImageLabel.setBounds(37, 107, 406, 403);
		deleteEmployeePanel.add(deleteEmployeeImageLabel);
		
		
		// Label for username
		JLabel deleteEmployeeLabel = new JLabel("Enter the username of the employee");
		deleteEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deleteEmployeeLabel.setForeground(new Color(0, 0, 205));
		deleteEmployeeLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		deleteEmployeeLabel.setBounds(507, 113, 287, 53);
		deleteEmployeePanel.add(deleteEmployeeLabel);
		
		
		// TextField for entering username
		deleteEmployeeText = new JTextField();
		deleteEmployeeText.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		deleteEmployeeText.setBounds(551, 200, 204, 33);
		deleteEmployeePanel.add(deleteEmployeeText);
		deleteEmployeeText.setColumns(10);
		
		
		// Delete button to delete employee record
		JButton deleteEmployeeButton = new JButton("Delete");
		deleteEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteEmployee();	// Call the method to delete the employee record
			}
		});
		deleteEmployeeButton.setForeground(new Color(0, 0, 205));
		deleteEmployeeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		deleteEmployeeButton.setBounds(526, 286, 104, 42);
		deleteEmployeePanel.add(deleteEmployeeButton);
		
		
		// Back button 
		JButton deleteEmployeeBackButton = new JButton("Back");
		deleteEmployeeBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ManagerScreen manager = new ManagerScreen();
				manager.setVisible(true);
			}
		});
		deleteEmployeeBackButton.setForeground(new Color(0, 0, 205));
		deleteEmployeeBackButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		deleteEmployeeBackButton.setBounds(658, 287, 104, 41);
		deleteEmployeePanel.add(deleteEmployeeBackButton);
	}

	/*------------------------------------------- Update Employee Tab -----------------------------------------------------*/
	private void updateEmployeeTab(){
		
		JPanel updateEmployeePanel = new JPanel();
		updateEmployeePanel.setBackground(new Color(230, 230, 250));
		tabbedPane.addTab("Update Employee", null, updateEmployeePanel, null);
		tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		updateEmployeePanel.setLayout(null);
		
		// Label to display image 
		JLabel updateEmployeeImageLabel = new JLabel("");
		Image updateEmployeeImage = new ImageIcon(this.getClass().getResource("/update_employee.png")).getImage();
		updateEmployeeImageLabel.setIcon(new ImageIcon(updateEmployeeImage));
		updateEmployeeImageLabel.setBounds(48, 92, 405, 406);
		updateEmployeePanel.add(updateEmployeeImageLabel);
		
		// Label to display heading 
		JLabel updateEmployeeTitle = new JLabel("UPDATE EMPLOYEE INFORMATION");
		updateEmployeeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		updateEmployeeTitle.setForeground(new Color(0, 0, 139));
		updateEmployeeTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
		updateEmployeeTitle.setBounds(211, 23, 437, 47);
		updateEmployeePanel.add(updateEmployeeTitle);
		
		// Label for username of the employee
		JLabel updateEmployeeLabel = new JLabel("Enter the username of the employee");
		updateEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateEmployeeLabel.setForeground(new Color(0, 0, 205));
		updateEmployeeLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		updateEmployeeLabel.setBounds(481, 129, 311, 47);
		updateEmployeePanel.add(updateEmployeeLabel);
		
		
		// TextField to input the username of the employee
		updateEmployeeText = new JTextField();
		updateEmployeeText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		updateEmployeeText.setBounds(518, 202, 223, 33);
		updateEmployeePanel.add(updateEmployeeText);
		updateEmployeeText.setColumns(10);
		
		
		// Submit button
		JButton updateEmployeeButton = new JButton("Submit");
		updateEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					String query = "Select *from login where username = ?";
					
					employeeUsername = updateEmployeeText.getText();			// Fetches the username entered by the manager in the textfield
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, employeeUsername);
					ResultSet rs = ps.executeQuery();		//Execute the query
					int count = 0;
					while(rs.next()){						//Continue the loop to find no. of rows that conatin the result
						count++;
					}
					if(count == 1){							// If one employee is found with corresponding username
						employeeExists = true;				//(employee is found and can be updated using this variable)
						dispose();							// Close this window and go the AddEmployee.java class to update the records
						AddEmployee employee = new AddEmployee();
						employee.setVisible(true);
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Employee does not exist");	// If no row is found, then the employee does not exist for the corresponding username
						employeeExists = false;
					}
					ps.close(); 	//Terminate the connection to the database
					rs.close();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Unable to fetch data");
					ex.printStackTrace();
				}
			}
		});
		updateEmployeeButton.setForeground(new Color(0, 0, 205));
		updateEmployeeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		updateEmployeeButton.setBounds(518, 287, 111, 39);
		updateEmployeePanel.add(updateEmployeeButton);
		
		
		// Back button
		JButton updateEmployeeBackButton = new JButton("Back");
		updateEmployeeBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				ManagerScreen manager = new ManagerScreen();
				manager.setVisible(true);
			}
		});
		updateEmployeeBackButton.setForeground(new Color(0, 0, 205));
		updateEmployeeBackButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		updateEmployeeBackButton.setBounds(655, 287, 111, 39);
		updateEmployeePanel.add(updateEmployeeBackButton);
		
		
	}
	
	/*---------------------------------------------- Delete Employee ---------------------------------------------------------------------------*/
	private void deleteEmployee(){
		
		try{
			
			String query = "Delete from login where username = ?";			// SQL query for deletion
			PreparedStatement ps = connection.prepareStatement(query);		// Create a preparedStatement
			ps.setString(1, deleteEmployeeText.getText());					// Get the employee username entered by the manager in the textfield
			int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this employee record?"	
					, "Delete Employee", JOptionPane.YES_NO_OPTION);		// Display a prompt which asks for confirmation
			
			if(action == 0){												// If yes them execute the query to delete the employee
				ps.execute();												// from the database
				JOptionPane.showMessageDialog(null, "Employee deleted");
				ps.close();
				dispose();													// Go back to the Manager Screen Menu 
				ManagerScreen manager = new ManagerScreen();
				manager.setVisible(true);
			}			
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Employee does not exist");
			e.printStackTrace();
		}
	}
	
	/*----------------------------------------------------------- Delete Task -------------------------------------------------------------------------------*/
	private void deleteTask(){
		
		try{
			
			String query = "Delete from taskdata where Task_no = ?";		// Delete query for deletion of a task form the taskdata table
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, deleteTaskText.getText());						// Get the task no. entered by the manager in the textField
			
			int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this task?"
					, "Delete Task", JOptionPane.YES_NO_OPTION);				// Display a prompt asking the manager for confirmation
		
			if(action == 0){													// If yes, then execute the query and 
				ps.execute();													// delete the task from the table
				JOptionPane.showMessageDialog(null, "Task deleted");			// and show a confirmation message
				ps.close();
				dispose();														// Close this window and go back to the 
				ManagerScreen manager = new ManagerScreen();					// Manager Screen menu
				manager.setVisible(true);
			}
			ps.close();															//Close the connection to the database
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Task does not exist");
			e.printStackTrace();
		}
	}
	
}
