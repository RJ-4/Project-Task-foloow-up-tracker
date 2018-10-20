import javax.swing.JOptionPane;

// This class is used by the MANAGER to validate the employee information entered by the manager either while adding a new employee
// or while updating the information of an existing one
public class AddEmployeeValidation {

	private String newUsername;
	private String newPassword;
	private String newEmpName;
	private String confirmPassword;
	private String contact;
	private String email;
	
	
	public AddEmployeeValidation(String newUsername, String newPassword, String confirmPassword, String newEmpName, String contact, String email) {
		
		this.newUsername = newUsername;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
		this.newEmpName = newEmpName;
		this.contact = contact;
		this.email = email;
	}
	
	public boolean validate(){
		
		if(newUsername.equals("")){											// If the username textField is kept empty show an error
			JOptionPane.showMessageDialog(null, "Enter a valid username");
			return false;
		}
		
		if(newPassword.equals("")){											//If the "new password" field is empty, show an error
			JOptionPane.showMessageDialog(null, "Enter a valid password");
			return false;
		}
		
		if(newEmpName.equals("")){											//If the "Employee name" field is empty, show an error
			JOptionPane.showMessageDialog(null, "Enter a valid employee name");
			return false;
		}
		
		if(contact.equals("")){												//If the "Contact No." field is empty, show an error
			JOptionPane.showMessageDialog(null, "Enter a valid contact number");
			return false;
		}
		
		if(email.equals("")){												// If the "Email id" field is empty, show an error
			JOptionPane.showMessageDialog(null, "Enter a valid email id");
			return false;
		}
		
		if(!confirmPassword.equals(newPassword)){							//If the content of "confirm password" field and 
			JOptionPane.showMessageDialog(null, "Passwords don't match");	//"new password" field do not match, show an error
			return false;
		}
		
		int i = 0;
		while(i < newEmpName.length() ){
			char c = newEmpName.charAt(i);		// For each character of the of "Employee Name" field:
			if(c < 65 || c > 90){			// If any entered character is not between (A-Z)
				if(c < 97 || c > 122){		// If any entered character is not between (a-z)
					if(c != 32){			// If any entered character is not a " "
						JOptionPane.showMessageDialog(null, "Enter a valid employee name"); 	//then display an error
						return false;
					}
				}
			}
			i++;
		}
		
		if(contact.length() != 10){				// If the contact number contains more or less than 10 digits, display an error
			JOptionPane.showMessageDialog(null, "Enter a valid contact number");
			return false;
		}
		
		i=0;
		while(i < contact.length()){			// If the contact number has any character other than 0-9, display an error
			char c = contact.charAt(i);
			if(c < 48 || c > 57){
				JOptionPane.showMessageDialog(null, "Enter a valid contact number");
				return false;
			}
			i++;
		}
		
		
		// Email validation 
		if(email.indexOf("@") == -1){
			JOptionPane.showMessageDialog(null, "Enter a valid email address");
			return false;
		}
		
		if(email.indexOf(".") == -1){
			JOptionPane.showMessageDialog(null, "Enter a valid email address");
			return false;
		}
		
		int atpos = email.indexOf("@");
		int dotpos = email.indexOf(".");
		if(atpos < 1 || (dotpos - atpos) < 2){
			JOptionPane.showMessageDialog(null, "Enter a valid email address");
			return false;
		}
			
		return true; 	//If all these conditions are false, the data entered is correct, return true
	}
}
