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


/*This class is for EMPLOYEE and creates a menu for the employee to access certain features which include:
 * 1. Display all the tasks that the employee is part of
 * 2. Add a new task which provides employee with the access to add a new task
 * 3. Update an existing task 
 * 4. Log out
 */
public class EmployeeScreen extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public EmployeeScreen() {
		
		setResizable(false);
		setTitle("Task Tracker");
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();		// Add icon image
		setIconImage(menuImage);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1028, 634);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Adds the label on the window
		addLabel();
		
		// Add buttons on the window
		addButtons();
		
		// Display the notification for the pending tasks
		if(Login.getNotification == true){
			Notification notification = new Notification();
			notification.addNotification();
			Login.getNotification = false;
		}
		
		
	}

	/*------------------------------- Add buttons -------------------------------------------------------*/
	private void addButtons(){
		
		// Display Tasks button: Displays all the tasks that the employee is part of
		JButton displayTasks = new JButton("Display Tasks");
		displayTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				TaskRecord taskRecord = new TaskRecord();
				taskRecord.setVisible(true);
			}
		});
		displayTasks.setForeground(new Color(0, 0, 205));
		displayTasks.setFont(new Font("Times New Roman", Font.BOLD, 18));
		displayTasks.setBounds(817, 131, 160, 60);
		contentPane.add(displayTasks);
				
				
		// Log out Button
		JButton logoutButton = new JButton("Log Out");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Login login = new Login();
				login.loginFrame.setVisible(true);
				Login.getNotification = true;			// Get the notification when the next time employee logs in
			}
		});
		logoutButton.setForeground(new Color(0, 0, 205));
		logoutButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		logoutButton.setBounds(817, 430, 160, 60);
		contentPane.add(logoutButton);
		
		// Add a new task button
		JButton addTaskButton = new JButton("Add Task");
		addTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddTask task = new AddTask();		// Go to AddTask class
				task.setVisible(true);
			}
		});
		addTaskButton.setForeground(new Color(0, 0, 205));
		addTaskButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		addTaskButton.setBounds(40, 131, 160, 60);
		contentPane.add(addTaskButton);
				
				
		// Button to update an existing task
		JButton updateButton = new JButton("Update Task");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				UpdateCheck update = new UpdateCheck();
				update.setVisible(true);
			}
		});
		updateButton.setForeground(new Color(0, 0, 205));
		updateButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		updateButton.setBounds(40, 430, 160, 60);
		contentPane.add(updateButton);	
		
		
	}
	
	/*------------------------------- Add label ---------------------------------------------------------*/
	private void addLabel(){
		
		// Heading label 
		JLabel welcomeLabel = new JLabel("Welcome " + Login.nameValidation);	// Get the name of the employee from the login table
		welcomeLabel.setForeground(new Color(0, 0, 139));
		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(349, 26, 295, 48);
		contentPane.add(welcomeLabel);
		
		// Image label
		JLabel imageLabel = new JLabel("");
		Image image = new ImageIcon(this.getClass().getResource("/employee_screen.png")).getImage();
		imageLabel.setIcon(new ImageIcon(image));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(223, 105, 576, 458);
		contentPane.add(imageLabel);
	}
}
