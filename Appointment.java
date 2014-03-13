package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Appointment {

	private Date startTime, endtime, date, alarmTime;
	private int meetingID, duration, meetingLeaderID;
	
	private String description, place;
	
	
	public Appointment() {
		
	}
	
	public void addParticipant(Person participant) {
		
	}
	
	public void removeParticipant(Person participant) {
		
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date newStartTime) {
		startTime = newStartTime;
	}
	
	public void setStartTime( String strTime) {
		
		Date newDate = parseDate("ddMMyyyy", strTime);
	      
		
		if( newDate != null) {
			startTime = newDate;
		}
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate( Date newDate) {
		date = newDate;
	}
	
	//tar inn dato i format ddMMyyyy (eksempel 6. juni 2006: 06062006)
	public void setDate( String strDate ) {
	      
	      Date newDate = parseDate("ddMMyyyy", strDate);
	      
	      if( newDate != null) {
	    	  date = newDate;
	      }
		
	}
	
	private Date parseDate(String format, String strDate) {
		SimpleDateFormat dateFomater = new SimpleDateFormat ("format");
	      
	      Date newDate = null;
	      try { 
	          newDate = dateFomater.parse(strDate); 
	      } catch (ParseException e) { 
	          System.out.println("Unparseable using " + dateFomater); 
	      }
	      
	      return newDate;
	}
	
	
	

}
