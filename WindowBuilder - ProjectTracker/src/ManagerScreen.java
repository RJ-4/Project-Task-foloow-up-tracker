import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;



/* The features of this class can only be accessed by the manager which include:  
	1. Display all the tasks
	2. Add a new employee
	3. Display the list of all the employees
	4. Update employee information
	5. Delete an employee from the database
	6. Delete a task from the database
	7. Add a new task for the employee
*/
public class ManagerScreen extends JFrame {

	private JPanel contentPane;
	public static boolean userIsManager = false;

	/**
	 * Create the frame.
	 */
	public ManagerScreen() {
		setResizable(false);
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();
		setIconImage(menuImage);
		setTitle("Task Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 612);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addLabels();
		
		addButtons();
		
	}
	/*----------------------------------------- Create buttons -------------------------------------------------------*/
	private void addButtons(){
		
		//'Display tasks' Button: Displays all the tasks
				JButton displayTasksButton = new JButton("Display Tasks");			
				displayTasksButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();													//Close this window
						TaskRecord taskRecord = new TaskRecord();					//Create an object of the TaskRecord class
						taskRecord.setVisible(true);								//Open the TaskRecord class
						
					}
				});
				displayTasksButton.setForeground(new Color(0, 0, 205));
				displayTasksButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
				displayTasksButton.setBounds(777, 278, 195, 51);
				contentPane.add(displayTasksButton);								//Add the button
				
				
				//Button to "LOG OUT"
				JButton logoutButton = new JButton("Log Out");
				logoutButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();													//Close this window
						Login login = new Login();									//Go to the login window (Login.java class)
						login.loginFrame.setVisible(true);							
					}
				});
				logoutButton.setForeground(new Color(0, 0, 205));
				logoutButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
				logoutButton.setBounds(777, 446, 195, 51);
				contentPane.add(logoutButton);										//Add the button
				
				
				//Button to "ADD AN EMPLOYEE" 
				JButton addEmployeeButton = new JButton("Add Employee");
				addEmployeeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();													//Close this window 
						AddEmployee employee = new AddEmployee();					//Go to the 'Add employee' window (AddEmployee.java class)
						employee.setVisible(true);
					}
				});
				addEmployeeButton.setForeground(new Color(0, 0, 205));
				addEmployeeButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
				addEmployeeButton.setBounds(44, 124, 195, 51);
				contentPane.add(addEmployeeButton);									//Add the button
				
				
				//Button to "DISPLAY THE LIST OF ALL EMPLOYEES"
				JButton displayEmployeeButton = new JButton("Display Employees");
				displayEmployeeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();													//Close this window
						DisplayEmployees employees = new DisplayEmployees();		//Go to the DisplayEmployees.java class
						employees.setVisible(true);
					}
				});
				displayEmployeeButton.setForeground(new Color(0, 0, 205));
				displayEmployeeButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
				displayEmployeeButton.setBounds(777, 124, 195, 51);
				contentPane.add(displayEmployeeButton);								//Add the button
				
				
				
				//Button to UPDATE EMPLOYEE INFORMATION, DELETE EMPLOYEE FROM DATABASE and DELETE A TASK from the database
				JButton updateEmployeeInfo = new JButton("Update/Delete Information");
				updateEmployeeInfo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();													//Close this window
						UpdateAndDeleteEmployee employee = new UpdateAndDeleteEmployee();	//Go to the UpdateAndDeleteEmployee.java class
						employee.setVisible(true);
					}
				});	
				updateEmployeeInfo.setFont(new Font("Times New Roman", Font.BOLD, 15));
				updateEmployeeInfo.setForeground(new Color(0, 0, 205));
				updateEmployeeInfo.setBounds(38, 446, 245, 51);
				contentPane.add(updateEmployeeInfo);								//Add the button
				
				
				//Button to add a new task
				JButton addTaskButton = new JButton("Add Task");
				addTaskButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						userIsManager = true;
						dispose();
						AddTask task = new AddTask();
						task.setVisible(true);
					}
				});
				addTaskButton.setForeground(new Color(0, 0, 205));
				addTaskButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
				addTaskButton.setBounds(44, 278, 195, 51);
				contentPane.add(addTaskButton);
				
	}
	
	/*----------------------------------------- Create label ---------------------------------------------------------*/
	private void addLabels(){
		
		JLabel welcomeLabel = new JLabel("");
		welcomeLabel.setText("Welcome " + Login.nameValidation);
		welcomeLabel.setForeground(new Color(0, 0, 139));
		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(354, 21, 312, 51);
		contentPane.add(welcomeLabel);
		
		JLabel imageLabel = new JLabel("");
		Image image = new ImageIcon(this.getClass().getResource("/manager_screen.png")).getImage();
		imageLabel.setIcon(new ImageIcon(image));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(300, 125, 458, 427);
		contentPane.add(imageLabel);
			
	}
	
}
