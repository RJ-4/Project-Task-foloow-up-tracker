import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;



/* This class is used by the MANAGER to display the information of all the employees
 * */
public class DisplayEmployees extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Connection connection = null;
	private JButton backButton;
	private DefaultTableModel model;
	
	public DisplayEmployees(){
		
		connection = DatabaseConnection.dbConnection();
		
		Image menuImage = new ImageIcon(this.getClass().getResource("/t.png")).getImage();
		setIconImage(menuImage);
		setTitle("Display Employees");
		setBounds(100, 100, 747, 483);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);
		
		
		// Add back Button
		addbackButton();
		
		// Create a new model for the table
		model = new DefaultTableModel(new String[]{"Username", "Password", "Employee Name", "Contact No.", "Email Id"}, 0);
		
		//Create a new table
		table = new JTable();
		
		// Specify GridBagConstraints and add table and back button to the screen
		addGridBagConstraints();
	
		// Fetch data from the database and insert it in the table
		fetchData();
	}
	/*---------------------------- Specify the GridBagConstraints -----------------------------------------------*/
	private void addGridBagConstraints(){
		
		// GridBagConstraints for the table
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		contentPane.add(new JScrollPane(table), gc);
				
				
		// GridBagConstraints for the back button
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(10, 0, 5, 0);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 0;
		gc.weighty = 0;
		contentPane.add(backButton, gc);
	}
	
	/*---------------------------- Fetch the data from the table and prints it in the table------------------*/
	private void fetchData(){
		
		try {
			String query = "Select *from login ";					// Display all employees
			PreparedStatement preparedStatement = connection.prepareStatement(query);	 
			ResultSet resultSet = preparedStatement.executeQuery();						// Execute the query
			while(resultSet.next()){							// Continue till all matching rows
				
				String username = resultSet.getString(1);		// Get the username
				String password = resultSet.getString(2);		// Get the password
				String empName = resultSet.getString(3);		// Get the Employee Name
				String contact = resultSet.getString(4);		// Get the Contact No.
				String email = resultSet.getString(5);			// Get the Email Id
				model.addRow(new Object[]{username, password, empName, contact, email});	// Add the data to the model
			}
			table.setModel(model);														// Set the model into the table
			preparedStatement.close();													
			resultSet.close();															// Close the connection to the database
		} 
		
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot fetch data");
			e.printStackTrace();
		}
		
	}
	
	/*--------------------------- Add back button -----------------------------------------*/
	private void addbackButton(){
		
		backButton = new JButton("Back");
		backButton.setForeground(new Color(0, 0, 205));
		backButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		backButton.setBounds(600, 414, 103, 30);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ManagerScreen manager = new ManagerScreen();
				manager.setVisible(true);
			}
		});
	}
}
