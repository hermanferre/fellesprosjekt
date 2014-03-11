package DB;

import java.sql.*;
import java.util.ArrayList;

public class Database {
	
	DBConnection db;
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		Database databaseTest = new Database();
//		databaseTest.addMeeting("14:00:00","15:00:00","2014-01-01","hei","fhdsja","Hallvard");
//		databaseTest.addParticipants("Hallvard", 4);
		databaseTest.getAppointments("Hallvard");
	}
	
	public Database(){
		db = new DBConnection();
	}
	
	public void getAppointments(String user){
		String query = "select avtaleid, starttid, sluttid, dato, sted, beskrivelse, moterom from Deltaker natural join Ansatt natural join Avtale where '" + user + "' = ansatt and avtale = avtaleid and brukernavn = '" + user + "'";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				String hei = rs.getString("starttid");
				String hallo = rs.getString("sluttid");
				System.out.println(hei);
				System.out.println(hallo);
			}
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void addParticipants(String user, int id){
		String query1 = "insert into Deltaker (ansatt, avtale) values ('" + user + "', " + id + ");";
		String query2 = "select ansatt, avtale from Deltaker where ansatt = '" + "' and avtale = " + id + ";";
		ResultSet rs = db.readQuery(query2);
		try {
			if(rs.getString("ansatt") == "null"){
				db.updateQuery(query1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
//		db.updateQuery(query);
	}

	public String getDate(int avtaleID){
		String query = "select dato from Avtale where avtaleid = " + avtaleID + ";";
		Date dato = null;
		ResultSet rs = db.readQuery(query);
		try{
			if(rs.next()){
				dato = rs.getDate("dato");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		String date = dato.toString();
		return date;
	}
	
	public String getVarighet(int AVTALEID) {
		String sql1 = "select STARTTID from Avtale where AVTALEID = "+AVTALEID;
		String sql2 = "select SLUTTID from Avtale where AVTALEID = "+AVTALEID;
		String sql3 = "select TIMEDIFF (("+sql2+"), (" +sql1+"));";
		ResultSet rs = db.readQuery(sql3);
		String varighet = null;
		try {
			if (rs.next()){
				varighet = rs.getString(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return varighet;
	}
	

	
	public String getStartTime(int avtaleID){
		String query = "select starttid from Avtale where avtaleid = " + avtaleID + ";";
		ResultSet rs = db.readQuery(query);
		Time start = null;
		try{
			if(rs.next()){
				start = rs.getTime("starttid");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		String time = start.toString();
		return time;
	}
	
	public String getEndTime(int avtaleID){
		String query = "select sluttid from Avtale where avtaleid = " + avtaleID + ";";
		ResultSet rs = db.readQuery(query);
		Time end = null;
		try{
			if(rs.next()){
				end = rs.getTime("sluttid");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		String time = end.toString();
		return time;
	}
	
	public int getMeetingRoom(int avtaleID){
		String query = "select moterom from Avtale where avtaleid = " + avtaleID + ";";
		ResultSet rs = db.readQuery(query);
		int room = 0;
		try{
			if(rs.next()){
				room = rs.getInt("moterom");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return room;
	}
	
	
	public void addMeeting(String start, String end, String date, String sted, String beskrivelse, String leader){
		String antall = "select count(*) from Avtale;";
		ResultSet rs = db.readQuery(antall);
		int id = 0;
		try{
			if(rs.next()){
				id = rs.getInt(1);
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		id++;
		String query = "insert into Avtale values ("+id+",'"+start+"','"+end+"','"+date+"','"+sted+"','"+beskrivelse+"', null,'"+leader+"');";
		db.updateQuery(query);
	}
	
	public void removeMeetingRoom(int id){
		String query = "update Avtale set moterom = null where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editStart(int id, String start){
	String query = "update Avtale set starttid = '" + start + "' where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editEnd(int id, String end){
		String query = "update Avtale set sluttid = '" + end + "' where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editDate(int id, String date){
		String query = "update Avtale set dato = '" + date + "' where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editPlace(int id, String sted){
		String query = "update Avtale set sted = '" + sted + "' where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editBeskrivelse(int id, String be){
		String query = "update Avtale set beskrivelse = '" + be + "' where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editMeetingRoom(int id, int room){
		String query = "update Avtale set moterom = " + room + " where avtaleid = " + id + ";";
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		rooms = getRooms();
		if(rooms.contains(room)){
			db.updateQuery(query);
		}else{
			System.out.println("Ikke gyldig rom");
		}
	}
	
	public ArrayList<Integer> getAppointments(){
		ArrayList<Integer> ap = new ArrayList<Integer>();
		String query = "select avtaleid from Avtale;";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				int a = rs.getInt("avtaleid");
				ap.add(a);
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
		return ap;
	}
	
	public void removeMeeting(int id){
		String query1 = "delete from Deltaker where avtale = " + id + ";";
		String query2 = "delete from Avtale where avtaleid = " + id + ";";
		db.updateQuery(query1);
		db.updateQuery(query2);
	}
	
	public ArrayList<String> getUsername(){
		ArrayList<String> brukernavn = new ArrayList<String>();
		String query = "select brukernavn from Ansatt;";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				brukernavn.add(rs.getString("brukernavn"));
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
		return brukernavn;
	}
	
	public void addPerson(String user, String pw) throws SQLException{
		ArrayList<String>brukernavn = getUsername();
		if(brukernavn.contains(user)){
			System.out.println("Brukeren finnes allerede, velg et annet brukernavn!");
		} else{
			String query = "insert into Ansatt values ('" + user + "', '" + pw + "');";
			db.updateQuery(query);
		}
	}
	
	public void removePerson(String user){
		String query1 = "delete from Deltaker where brukernavn = '" + user + "';";
		String query2 = "delete from Ansatt where brukernavn = '" + user + "';";
		db.updateQuery(query1);
		db.updateQuery(query2);
	}
	
	public String getPassword(String username){
		String query = "select passord from Ansatt where brukernavn = '" + username + "';";
		ResultSet rs = db.readQuery(query);
		String pw = null;
		try {
			if(rs.next()){
				pw = rs.getString("passord");
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return pw;
	}
	
	public int getRoomCap(int roomNr){
		String query = "select * from Moterom where romnr = '" + roomNr + "';";
		ResultSet rs = db.readQuery(query);
		int cap = 0;
		try{
			if(rs.next()){
				cap = rs.getInt("kapasitet");
			}
		} catch(SQLException e){
			throw new RuntimeException();
		}
		return cap;
	}
	
	public ArrayList<Integer> getRooms(){
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		String query = "select romnr from Moterom;";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				int room = rs.getInt("romnr");
				rooms.add(room);
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
		return rooms;
	}
	
	public void addRoom(int roomNr, int cap) throws SQLException{
		ArrayList<Integer> rooms = getRooms();
		if(rooms.contains(roomNr)){
			System.out.println("Rommet finnes allerede, velg et annet brukernavn!");
		} else{
			String query = "insert into Moterom values (" + roomNr + ", " + cap + ");";
			db.updateQuery(query);
		}
	}
}