/*
 * This class is used to add the date and time to the add/update task window
 * */

import java.util.GregorianCalendar;

public class Clock {

	public void clock(){
		
		// Create a new Thread to successively increase time (dynamic)
		Thread clock = new Thread(){
			
			public void run(){
				
				try {
					
					// Infinite loop to increase time indefinitely
					for(;;){
						
						GregorianCalendar calender = new GregorianCalendar();
						int day = calender.get(GregorianCalendar.DAY_OF_MONTH);
						int month = calender.get(GregorianCalendar.MONTH) + 1;
						int year = calender.get(GregorianCalendar.YEAR);
						
						int getHour = calender.get(GregorianCalendar.HOUR_OF_DAY);
						int getMinute = calender.get(GregorianCalendar.MINUTE);
						int getSecond = calender.get(GregorianCalendar.SECOND);
						
						String hour = "", minute = "", second = "";
						
						hour = hour + getHour;
						minute = minute + getMinute;
						second = second + getSecond;
						
						// If single digit add 0 before it
						if(getHour < 10){
							hour = "0" + getHour; 
						}
						
						if(getMinute < 10){
							minute = "0" + getMinute;
						}
						
						if(getSecond < 10){
							second = "0" + getSecond;
						}
						
						// Set the date and time
						AddTask.clockLabel.setText(day + "/" + month + "/" + year + "                " + hour + ":" + minute + ":" + second);
						
						// Sleep for 1000 milliseconds
						sleep(1000);
					}
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// Start the thread
		clock.start();
		
	}
}
