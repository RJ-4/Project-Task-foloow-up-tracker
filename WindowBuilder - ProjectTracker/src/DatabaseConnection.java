import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

// This class is used to connect to the MYSQL database 
public class DatabaseConnection {

	private static Connection connection = null;			//Initializing a connection variable
	
	public static Connection dbConnection(){
		try{
			
			//Load the driver class for mysql
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connecting to the database named 'projecttracker' in mysql at port no '3306' where the username is 'root' and password does not exist
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projecttracker", "root", null);	
			
			//return the connection 
			return connection;
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Connection Failed");
			return null;
		}
	}
}
