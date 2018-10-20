import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

/* This class is used to connect with the database to ascertain whether or not the employee has an "open" task or not
 * If there is an open task, then the notification is next as soon as the employee logs in
*/
public class Notification {

	Connection connection = null;
	public static int numberOfOpenTasks;			// To determine how many "open" tasks the employee has
	
	public void addNotification(){
		
		connection = DatabaseConnection.dbConnection();
		try{
			
			// Get the no. of tasks of the logged in employee with the task status as "open"
			String query = "Select count(status) from taskdata where status = ? and Task_member_username like ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, "Open");
			ps.setString(2, '%' + Login.usernameValidation + '%');
			ResultSet rs = ps.executeQuery();
			if(rs.next()){								//If record is found, then store the no. of pending tasks 
														// in the integer variable using getInt() method and the first 
				numberOfOpenTasks = rs.getInt(1);	// column of the result set
				
				if(numberOfOpenTasks > 0){			// If the no. of open tasks are more than 0, then display a message
					JOptionPane.showMessageDialog(null, "You have " + numberOfOpenTasks + " pending tasks");
				}
				
			}
			rs.close();									// Terminate the database connection
			ps.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to get notification");
			e.printStackTrace();
		}
	}
}
