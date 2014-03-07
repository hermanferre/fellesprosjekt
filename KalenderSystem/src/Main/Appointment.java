package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Appointment {

	private Date startTime, endtime, date, alarmTime;
	private int meetingID;
	private Person meetingLeader;
	
	private String description, place;
	
	
	public Appointment() {
		
	}
	
	public void addParticipant(Person participant) {
		
	}
	
	public void removeParticipant(Person participant) {
		
	}
	
	public void setMeetingLeader(Person leader) {
		meetingLeader = leader;
	}
	
	
	public Person getMeetingLeader() {
		return meetingLeader;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date newStartTime) {
		startTime = newStartTime;
	}
	
	//tar inn klokkeslett i mormat HHmm (eksmpel 20:20 =  2020 )
	public void setStartTime( String strTime) {
		
		Date newDate = parseDate("HHmm", strTime);
	      
		
		if( newDate != null) {
			startTime = newDate;
		}
		
	}
	
	public void setEndtime(Date newStartTime) {
		endtime = newStartTime;
	}
	
	//tar inn klokkeslett i mormat HHmm (eksmpel 20:20 =  2020 )
	public void setEndtime( String strTime) {
		
		Date newDate = parseDate("HHmm", strTime);
	      
		
		if( newDate != null) {
			endtime = newDate;
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
	
	// returner varighet i minutter
	public int getDuration() {
		
		double varighet = endtime.getTime() - startTime.getTime();
		
		return (int)( (varighet / 60000) + 0.5 );
		
	}
	
	public void setDuration(int minutter) {
		long milliskeunder = startTime.getTime() + minutter * 60000;
		endtime = new Date(milliskeunder);
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
