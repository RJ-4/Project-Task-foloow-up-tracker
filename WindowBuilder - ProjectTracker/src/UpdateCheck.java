import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;


/* This class is used by EMPLOYEE and requests the Task No. of the task he/she wants to update
 * It then checks if the employee requesting access to the modification of the task is actually a part of it (member) or not
 * and takes action accordingly
 * */
public class UpdateCheck extends JFrame {

	private JPanel contentPane;
	private JTextField taskNoText;
	private Connection connection = null;
	
	public static String taskNoUpdate;			// Used to store the Task no. entered by the employee in the textfield

	public static boolean taskExists = false;	// Used to differentiate between a new task and an existing task


	/**
	 * Create the frame.
	 */
	public UpdateCheck() {
		
		connection = DatabaseConnection.dbConnection();
		setTitle("Task Tracker");
		setResizable(false);
		Image icon = new ImageIcon(this.getClass().getResource("/t.png")).getImage();
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 555);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		// Add labels to the window
		addLabels();
		
		// Add TextField to input the Task No
		addTextField();
		
		// Add and implement submit button
		addSubmitButton();
		
		// Add back button
		addBackButton();
		
	}
	/*-------------------------------------------- Add Submit button ------------------------------------------------------*/
	private void addSubmitButton(){
		
		// Submit button: Checks for the validity of the Task No and issues control
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				try{
							
					// SQL query so that the employee can access only his tasks ("Task_members like" is used to get the 
					// name of the employee requesting access and allow him to only him to open the task if he is a member
					// of that task
					String query = "Select *from taskdata where Task_no = ? and Task_member_username like ?";		
							
							
							
					taskNoUpdate = taskNoText.getText();					// Fetches the data from the textField
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, taskNoUpdate);							// Substitute the value of 1st '?' in the query
					ps.setString(2, '%' + Login.usernameValidation +'%');			// Substitute the value of 2nd '?' in the query
																						// with the username of the employee logged in
					ResultSet rs = ps.executeQuery();							// Execute the query
					int count = 0;
					while(rs.next()){								// Continue the loop till all the rows are found
						count++;
					}
					if(count == 1){									// If a record is found, then set the value of taskExists = true
						taskExists = true;						// to implement the "Update Task" feature in the AddTask.java 
						dispose();									// class. Close the current window and go the aforementioned class
						AddTask task = new AddTask();
						task.setVisible(true);
					}
					else if(count == 0){							// Is the record is not found, display a prompt
						taskExists = false;
						JOptionPane.showMessageDialog(null, "Task not found");
					}
					ps.close();										// Close the connection to the database
					rs.close();
				}
				
				catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Failed to check data");
					e1.printStackTrace();
				} 
			}
		});
		submitButton.setForeground(new Color(0, 0, 205));
		submitButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		submitButton.setBounds(621, 287, 107, 40);
		contentPane.add(submitButton);
	}
	
	/*-------------------------------------------- Add Back button ---------------------------------------------------------*/
	private void addBackButton(){
		
		// Add back button 
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				EmployeeScreen employeeScreen = new EmployeeScreen();
				employeeScreen.setVisible(true);
			}
		});
		backButton.setForeground(new Color(0, 0, 205));
		backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		backButton.setBounds(621, 354, 107, 40);
		contentPane.add(backButton);
		
	}
	
	/*-------------------------------------------- Add TextField ----------------------------------------------------------*/
	private void addTextField(){
		
		// TextField to enter the Task No 
		taskNoText = new JTextField();
		taskNoText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		taskNoText.setBounds(562, 208, 220, 40);
		contentPane.add(taskNoText);
		taskNoText.setColumns(10);
	}
	
	/*------------------------------------------- Add labels---------------------------------------------------------------*/
	private void addLabels(){
		
		// Label for adding an image
		JLabel imageLabel = new JLabel("");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(43, 102, 403, 401);
		contentPane.add(imageLabel);
		Image updateImage = new ImageIcon(this.getClass().getResource("/update_check.png")).getImage();
		imageLabel.setIcon(new ImageIcon(updateImage));
				
				
		// Label for task no.
		JLabel taskNoLabel = new JLabel("Enter Task No.");
		taskNoLabel.setForeground(new Color(0, 0, 205));
		taskNoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		taskNoLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		taskNoLabel.setBounds(562, 131, 220, 46);
		contentPane.add(taskNoLabel);
		
		
		// Label for heading
		JLabel lblNewLabel = new JLabel("TASK VERIFICATION");
		lblNewLabel.setForeground(new Color(0, 0, 139));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(123, 26, 584, 65);
		contentPane.add(lblNewLabel);
		
	}
}
