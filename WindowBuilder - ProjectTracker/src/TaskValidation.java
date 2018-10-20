import javax.swing.JOptionPane;


/* This class is used to validate all the input fields while adding a new task or updating an existing one 
 * */
public class TaskValidation {

	private String no, name, description, members, status;
	
	private int rDate, rMonth, rYear, cDate, cMonth, cYear;
	
	
	// Constructor for fetching data of input fields in a new task and update task for validation
	public TaskValidation(String no, String name, String description, String members){
		
		this.no = no;
		this.name = name;
		this.description = description;
		this.members = members;
	}

	public TaskValidation(String no, String name, String description, String members, String status){
		
		this.no = no;
		this.name = name;
		this.description = description;
		this.members = members;
		this.status = status;
	}
	
	// Method to validate input fields of an existing task (update task validation)
	public boolean updateTaskValidate(){
		
		
		final boolean check = newTaskValidate();		// Call the new task validation method to validate all the input fields
														// except closing date and status
		if(check == false){								// If the value sent by the newTaskValidate() method is false, that means
			return false;								// that an error has occurred while update the task in those fields and
		}												// there is no need to check further, return false
														// However, check for the validation of closing date and status if the 
		else{											// above fields are correct 
			
			
			if(status.equals("Closed")){
				
				cDate = AddTask.closingDateBox.getSelectedIndex();
				cMonth = AddTask.closingMonthBox.getSelectedIndex();
				cYear = AddTask.closingYearBox.getSelectedIndex();
				
				if(cDate == -1 || cMonth == -1 || cYear == -1){
					
						JOptionPane.showMessageDialog(null, "Enter a valid closing date");
						return false;
				}
				
				/*-------------------------------Compare closing date and registration date--------------------------------------*/
				
				/*------------------------------ Check if closing year > registration year --------------------------------------*/
				
				if(rYear > cYear){
					
					JOptionPane.showMessageDialog(null, "Enter a valid closing year");
					return false;
				}
				
				/*------------------------------Check if closing month is valid wrt registration month ---------------------------*/
				
				if(rYear == cYear){
					
					if(rMonth > cMonth){
						
						JOptionPane.showMessageDialog(null, "Enter a valid closing month");
						return false;
					}
				}
				
				/*-----------------------------Check if closing date is valid wrt registration date -------------------------------*/
				
				if(rYear == cYear){
					
					if(rMonth == cMonth){
						
						if(rDate > cDate){
							
							JOptionPane.showMessageDialog(null, "Enter a valid closing date");
							return false;
						
						}
					}
				}
		
			}
		}	
		return true;
	}
		
		
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	// Method to validate input fields of a new task
	public boolean newTaskValidate(){
		
		int i = 0, j=0;
		
		if(no.equals("")){				// If the input field Task No. is left blank, show an error
			JOptionPane.showMessageDialog(null, "Enter a valid Task No.");
			return false;
		}
		
		if(name.equals("")){			// If the input field Task Name is left blank, show an error
			JOptionPane.showMessageDialog(null, "Enter a valid Task Name");
			return false;
		}
		
		if(members.equals("")){			// If the input field Task Members is left blank, show an error
			JOptionPane.showMessageDialog(null, "Enter valid Task Members");
			return false;
		}
		
		while(i < description.length()){		// If the length of the Task Description field is more than 100 characters
			if(i > 100){						// show an error
				JOptionPane.showMessageDialog(null, " Task Description word limit exceeded... (maximum 100 characters)");
				return false;
			}
			i++;
		}
		
				
		while(j < members.length()){		// If the length of of the Task Members is more than 100 characters, show an error
			if(j > 100){
				JOptionPane.showMessageDialog(null, "Task Members  word limit exceeded... (maximum 100 characters)");
				return false;
			}
			j++;
		}

		
		rDate = (int)AddTask.registrationDateBox.getSelectedIndex();
		rMonth = AddTask.registrationMonthBox.getSelectedIndex();
		rYear = (int)AddTask.registrationYearBox.getSelectedIndex();
		
		if(rDate == -1 || rMonth == -1 || rYear == -1){
			JOptionPane.showMessageDialog(null, "Enter a valid registration date");
			return false;
		}
		/*---------------------------------------------------------------------------------------------------------------*/
		
		return true;					// If all these errors are avoided, the inputs are correct, return true
	}
}
