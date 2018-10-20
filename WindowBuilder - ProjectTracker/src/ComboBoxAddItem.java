
public class ComboBoxAddItem {
	
	private int date, year;
	
	private String month[] =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", 
			"October", "November", "December"};
	
	private String cMonth[] = new String[12];
	
	private int i, selectedRegistrationYear, selectedClosingYear;
	
	private String selectedMonth, selectedClosingMonth;
	
	
	public void addItem(){		
		
		for(i = 0, year = 2017; i < 34; i++, year++){
			AddTask.registrationYearBox.insertItemAt(year, i);
		}
		
		
		for(i = 0; i < 12; i++){
			AddTask.registrationMonthBox.insertItemAt(month[i], i);
		}
	
		
		for(i = 0, date = 1; i < 31; i++, date++){
			AddTask.registrationDateBox.insertItemAt(date, i);
		}
		
		
		if(UpdateCheck.taskExists == true){			// Only add the following ComboBoxes if the user is updating an existing task

			for(i = 0, year = 2017; i < 34; i++, year++){
				AddTask.closingYearBox.insertItemAt(year, i);
			}
			
			cMonth.equals(month);
			for(i = 0; i < 12; i++){
				AddTask.closingMonthBox.insertItemAt(month[i], i);
			}
		
			for(i = 0, date = 1; i < 31; i++, date++){
				AddTask.closingDateBox.insertItemAt(date, i);
			}
		}
	}
	
	/*-----------------------------------Adjusting for February if registration year is selected first------------------------------------------*/
	public void selectRegistrationDateYear(){
		
		selectedRegistrationYear = (int)AddTask.registrationYearBox.getSelectedItem();
		selectedMonth = (String)AddTask.registrationMonthBox.getSelectedItem();
		
		try{
			if(selectedRegistrationYear % 4 == 0 && selectedMonth.equals("February")){
					
				int count = AddTask.registrationDateBox.getItemCount();
				if(count == 31){
					AddTask.registrationDateBox.removeItem(31);
					AddTask.registrationDateBox.removeItem(30);
				}
				else if(count == 30){
					AddTask.registrationDateBox.removeItem(30);
				}
				else if(count == 28){
					AddTask.registrationDateBox.insertItemAt(29, 28);
				}
			}
				
			if(selectedMonth.equals("February") && selectedRegistrationYear % 4 != 0){
				
				int count = AddTask.registrationDateBox.getItemCount();
				if(count == 31){
					AddTask.registrationDateBox.removeItem(31);
					AddTask.registrationDateBox.removeItem(30);
					AddTask.registrationDateBox.removeItem(29);
				}
				else if(count == 30){
					AddTask.registrationDateBox.removeItem(30);
					AddTask.registrationDateBox.removeItem(29);
				}
				else if(count == 29){
					AddTask.registrationDateBox.removeItem(29);
				}
			}
		}
		catch (Exception e) {
		}
	}
	
	
	
	/*-----------------------------------Adjusting for February if registration month is selected first------------------------------------------*/
	public void selectRegistrationMonth(){
		
		selectedMonth = (String)AddTask.registrationMonthBox.getSelectedItem();
		int count = AddTask.registrationDateBox.getItemCount();
		
		if(selectedMonth.equals("January") || selectedMonth.equals("March") || selectedMonth.equals("May") || selectedMonth.equals("July") || 
				selectedMonth.equals("August") || selectedMonth.equals("October") || selectedMonth.equals("December")){
			
				
				
				if(count == 28){
					AddTask.registrationDateBox.insertItemAt(29, 28);
					AddTask.registrationDateBox.insertItemAt(30, 29);
					AddTask.registrationDateBox.insertItemAt(31, 30);
				}
				
				else if(count == 29){
					AddTask.registrationDateBox.insertItemAt(30, 29);
					AddTask.registrationDateBox.insertItemAt(31, 30);
				}
				
				else if(count == 30){
					AddTask.registrationDateBox.insertItemAt(31, 30);
				}
		}
		
		if(selectedMonth.equals("April") || selectedMonth.equals("June") || selectedMonth.equals("September") || 
				selectedMonth.equals("November")){				
				
				if(count == 31){
					AddTask.registrationDateBox.removeItem(31);
				}
				if(count == 28){
					AddTask.registrationDateBox.insertItemAt(29, 28);
					AddTask.registrationDateBox.insertItemAt(30, 29);
				}
				else if(count == 29){
					AddTask.registrationDateBox.insertItemAt(30, 29);
				}
				
		}
		
		if(selectedMonth.equals("February")){
			
			
			try{
				
				if(count == 31){
					AddTask.registrationDateBox.removeItem(31);
					AddTask.registrationDateBox.removeItem(30);
					AddTask.registrationDateBox.removeItem(29);
				}
				else if(count == 30){
					AddTask.registrationDateBox.removeItem(30);
					AddTask.registrationDateBox.removeItem(29);
				}
				else if(count == 29){
					AddTask.registrationDateBox.removeItem(29);
				}
				
			}
			catch (Exception e) {
			}
		}
	}
	
	/*-----------------------------------Adjusting for February if closing year is selected first------------------------------------------*/
	public void selectClosingDateYear(){
		
		try{
			selectedClosingYear = (int)AddTask.closingYearBox.getSelectedItem();
			selectedClosingMonth = (String)AddTask.closingMonthBox.getSelectedItem();
				
			if(selectedClosingYear % 4 == 0){
				
				try{
					
					if(selectedClosingMonth.equals("February")){
						
						int count = AddTask.closingDateBox.getItemCount();
						if(count == 31){
							AddTask.closingDateBox.removeItem(31);
							AddTask.closingDateBox.removeItem(30);
						}
						else if(count == 30){
							AddTask.closingDateBox.removeItem(30);
						}
						else if(count == 28){
							AddTask.closingDateBox.insertItemAt(29, 28);
						}
					}
				}	
				catch (Exception e) {
				}
			}
			
			
			else{
				try{
					if(selectedClosingMonth.equals("February")){
						
						int count = AddTask.closingDateBox.getItemCount();
						if(count == 31){
							AddTask.closingDateBox.removeItem(31);
							AddTask.closingDateBox.removeItem(30);
							AddTask.closingDateBox.removeItem(29);
						}
						else if(count == 30){
							AddTask.closingDateBox.removeItem(30);
							AddTask.closingDateBox.removeItem(29);
						}
						else if(count == 29){
							AddTask.closingDateBox.removeItem(29);
						}
					}
				}
				catch (Exception e) {
				}
			}
		}
		catch (Exception e) {
		}
	}
	
	/*-----------------------------------Adjusting for February if closing month is selected first------------------------------------------*/
	public void selectClosingMonth(){
		
		try{
			
			selectedClosingMonth = (String)AddTask.closingMonthBox.getSelectedItem();
			
			if(selectedClosingMonth.equals("January") || selectedClosingMonth.equals("March") || selectedClosingMonth.equals("May") || selectedClosingMonth.equals("July") || 
					selectedClosingMonth.equals("August") || selectedClosingMonth.equals("October") || selectedClosingMonth.equals("December")){
				
					int count = AddTask.closingDateBox.getItemCount();
					
					if(count == 28){
						AddTask.closingDateBox.insertItemAt(29, 28);
						AddTask.closingDateBox.insertItemAt(30, 29);
						AddTask.closingDateBox.insertItemAt(31, 30);
					}
					
					else if(count == 29){
						AddTask.closingDateBox.insertItemAt(30, 29);
						AddTask.closingDateBox.insertItemAt(31, 30);
					}
					
					else if(count == 30){
						AddTask.closingDateBox.insertItemAt(31, 30);
					}
			}
			
			if(selectedClosingMonth.equals("April") || selectedClosingMonth.equals("June") || selectedClosingMonth.equals("September") || 
					selectedClosingMonth.equals("November")){
				
					int count = AddTask.closingDateBox.getItemCount();
					
					if(count == 31){
						AddTask.closingDateBox.removeItem(31);
					}
					if(count == 28){
						AddTask.closingDateBox.insertItemAt(29, 28);
						AddTask.closingDateBox.insertItemAt(30, 29);
					}
					else if(count == 29){
						AddTask.closingDateBox.insertItemAt(30, 29);
					}
			}
			
			if(selectedClosingMonth.equals("February")){
				
				try{
					selectedClosingYear = (int)AddTask.closingYearBox.getSelectedItem();
					
					if(selectedClosingYear % 4 == 0){
						
						int count = AddTask.closingDateBox.getItemCount();
						if(count == 31){
							AddTask.closingDateBox.removeItem(31);
							AddTask.closingDateBox.removeItem(30);
						}
						else if(count == 30){
							AddTask.closingDateBox.removeItem(30);
						}
						else if(count == 28){
							AddTask.closingDateBox.insertItemAt(29, 28);
						}
					}
				
					else{
						
						int count = AddTask.closingDateBox.getItemCount();
						if(count == 31){
							AddTask.closingDateBox.removeItem(31);
							AddTask.closingDateBox.removeItem(30);
							AddTask.closingDateBox.removeItem(29);
						}
						else if(count == 30){
							AddTask.closingDateBox.removeItem(30);
							AddTask.closingDateBox.removeItem(29);
						}
						else if(count == 29){
							AddTask.closingDateBox.removeItem(29);
						}
					}
				}
				catch (Exception e) {
				}
			}
		}
		catch (Exception e) {
		}
	}
	
}
