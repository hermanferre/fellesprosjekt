package DB;

import java.sql.*;
import java.util.ArrayList;

import Main.Appointment;

public class Database {
	
	DBConnection db;
	static Database databaseTest;
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		databaseTest = new Database();
//		databaseTest.addMeeting("14:00:00","15:00:00","2014-01-01","hei","fhdsja","Hallvard");
//		databaseTest.addParticipants("Hallvard", 4);
//		databaseTest.addParticipants("Hallvard", 8);
//		databaseTest.getAppointments("Hallvard");
//		databaseTest.getEmail("Hallvard");
//		databaseTest.getEmails(2);
//		databaseTest.getAvRoom(22);
//		String hei = databaseTest.getDate(26);
//		System.out.println(hei);
//		System.out.println(databaseTest.getAvtaleId());
//		databaseTest.addParticipants("Hallvard", 43);
//		databaseTest.removeParticipants("Hallvard", 43);
//		System.out.println(databaseTest.getMail("Hallvard"));
		System.out.println(databaseTest.getAvtaleId());
	}
	
	public Database(){
		db = new DBConnection();
	}
	
	public void setSkjult(String user, int id){
		String query = "update Deltaker set sjult = true where ansatt = '"+user+"' and avtale = "+id+";";
		db.updateQuery(query);
		setStatus(user, id, false);
	}
	
	public void setStatus(String user, int id, boolean status){
		String query = "update Deltaker set deltar = "+status+" where ansatt = '"+user+"' and avtale = "+id+";";
		db.updateQuery(query);
	}
	
	
	public void editUser(String user, String newUser){
		String query = "update Ansatt set brukernavn = '"+newUser+"' where brukernavn = '"+user+"';";
		db.updateQuery(query);
	}
	
	public void editPw(String user, String newPw){
		String query = "update Ansatt set passord = '"+newPw+"' where brukernavn = '"+user+"';";
		db.updateQuery(query);
	}
	
	public void editEmail(String user, String newMail){
		String query = "update Ansatt set epost = '"+newMail+"' where brukernavn = '"+user+"';";
		db.updateQuery(query);
	}
	
	public String getMail(String user){
		String epost = null;
		String query = "select epost from Ansatt where brukernavn = '"+user+"';";
		ResultSet rs = db.readQuery(query);
		try{
			if(rs.next()){
				epost = rs.getString("epost");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return epost;
	}
	
	public ArrayList<String> getParticipants(int id){
		ArrayList<String> par = new ArrayList<String>();
		String query = "select ansatt from Deltaker where avtale = "+id+";";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				par.add(rs.getString("ansatt"));
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return par;
	}
	
	public boolean isHidden(int avtaleid, String brukernavn) {
		
		String query = "select sjult from Deltaker where avtale = "+avtaleid+
				" and ansatt = '" + brukernavn +"';";
		ResultSet rs = db.readQuery(query);
		
		try{
			if(rs.next())
				return rs.getBoolean("sjult");
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		return false;
		
	}
	
	public ArrayList<String> getAtParticipants(int id, boolean status){
		ArrayList<String> atPar = new ArrayList<String>();
		String query = "select ansatt from Deltaker where avtale = "+id+" and deltar = "+status+";";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				atPar.add(rs.getString("ansatt"));
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return atPar;
	}
	
	public void addParticipants(String user, int id){
		String query1 = "insert into Deltaker (ansatt, avtale) values ('" + user + "', " + id + ");";
		String query2 = "select ansatt, avtale from Deltaker where ansatt = '" +user+ "' and avtale = " + id + ";";
		ResultSet rs = db.readQuery(query2);
		try {
			if(!(rs.next())){
				db.updateQuery(query1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void removeParticipants(String user, int id){
		String query = "delete from Deltaker where ansatt = '"+user+"' and avtale = "+id+";";
		db.updateQuery(query);
	}
	
	public int getAvtaleId(){
		String query = "select max(avtaleid) + 1 from Avtale;";
		ResultSet rs = db.readQuery(query);
		int id = 0;
		try{
			if(rs.next()){
				id = rs.getInt("max(avtaleid) + 1");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return id;
	}
	
	public ArrayList<Integer> getAvRoom(int id){
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		ArrayList<Integer> opRooms = new ArrayList<Integer>();
		ArrayList<Integer> avRooms = new ArrayList<Integer>();
		String start = getStartTime(id);
		String end = getEndTime(id);
		String date = getDate(id);
		String query1 = "select moterom from Avtale where not ((starttid <= '"+start+"' and sluttid <= '"+start+"' or starttid >= '"+end+"' and sluttid >= '"+end+"')) and dato = '"+date+"';";
		ResultSet rs = db.readQuery(query1);
		try{
			while(rs.next()){
				opRooms.add(rs.getInt("moterom"));
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		rooms = getRooms();
		for(int i = 0; i < rooms.size(); i++){
			if(opRooms.contains(rooms.get(i))){
				
			}else{
				avRooms.add(rooms.get(i));
			}
		}
		return avRooms;
	}
	
	public ArrayList<String> getEmails(int id){
		String query = "select epost from Ansatt natural join Deltaker where ansatt = brukernavn and avtale = "+id+";";
		ArrayList<String> emails = new ArrayList<String>();
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				emails.add(rs.getString("epost"));
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return emails;
	}
	
	public String getEmail(String user){
		String query = "select epost from Ansatt where brukernavn = '"+user+"';";
		ResultSet rs = db.readQuery(query);
		String epost = null;
		try{
			if(rs.next()){
				epost = rs.getString("epost");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		System.out.println(epost);
		return epost;
	}
	
	public ArrayList<Appointment> getAppointmentsMoteleder(String user){
		ArrayList<Appointment> liste = new ArrayList<Appointment>();
		String query = "select avtaleid, starttid, sluttid, dato, sted, beskrivelse, moterom, motesjef from Avtale where motesjef = '" + user + "';";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				Appointment ap = new Appointment();
				ap.startTime = rs.getString("starttid");
				ap.endtime = rs.getString("sluttid");
				ap.dato = rs.getString("dato");
				ap.meetingLeader = rs.getString("motesjef");
				ap.description = rs.getString("beskrivelse");
				ap.place = rs.getString("sted");
				if(rs.getInt("moterom")==0){
					ap.meetingRoom = "Ikke angitt";
				}else{
					ap.meetingRoom = "" + rs.getInt("moterom");
				}
				ap.meetingID = rs.getInt("avtaleid");
				liste.add(ap);
			}
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return liste;
	}
	
	public ArrayList<Appointment> getAppointments(String user){
		ArrayList<Appointment> liste = new ArrayList<Appointment>();
		String query = "select avtaleid, starttid, sluttid, dato, sted, beskrivelse, moterom, motesjef, deltar, sjult from Deltaker natural join Ansatt natural join Avtale where '" + user + "' = ansatt and avtale = avtaleid and brukernavn = '" + user + "'";
		ResultSet rs = db.readQuery(query);
		try{
			while(rs.next()){
				Appointment ap = new Appointment();
				ap.startTime = rs.getString("starttid");
				ap.endtime = rs.getString("sluttid");
				ap.dato = rs.getString("dato");
				ap.meetingLeader = rs.getString("motesjef");
				ap.description = rs.getString("beskrivelse");
				if(rs.getInt("moterom")==0){
					ap.meetingRoom = "Ikke angitt";
				}else{
					ap.meetingRoom = "" + rs.getInt("moterom");
				}
				ap.meetingID = rs.getInt("avtaleid");
				ap.status = rs.getBoolean("deltar");
				ap.skjult = rs.getBoolean("sjult");
				liste.add(ap);
			}
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return liste;
	}
	
	public String getMoteleder(int id){
		String query = "select motesjef from Avtale where avtaleid = "+id+";";
		ResultSet rs = db.readQuery(query);
		String leder = null;
		try{
			if(rs.next()){
				leder = rs.getString("motesjef");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		return leder;
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
	}//christian er kul
	
	public String getVarighet(int AVTALEID) {
		String sql1 = "select STARTTID from Avtale where AVTALEID = "+AVTALEID;
		String sql2 = "select SLUTTID from Avtale where AVTALEID = "+AVTALEID;
		String sql3 = "select TIMEDIFF (("+sql2+"), (" +sql1+"));";
		ResultSet rs = db.readQuery(sql3);
		System.out.println("JONGE RULER!!!");
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
	
	
	public void addMeeting(String start, String end, String date, String sted, String beskrivelse, String moteleder){
		String query = "insert into Avtale (starttid, sluttid, dato, sted, beskrivelse, motesjef) values ('"+start+"','"+end+"','"+date+"','"+sted+"','"+beskrivelse+"', '"+moteleder+"');";
		db.updateQuery(query);
		int id = getAvtaleId();
		String query2 = "insert into Deltaker values('"+moteleder+"', "+id+", true, false);";
		db.updateQuery(query2);
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
	
	public void addPerson(String user, String pw, String email){
		ArrayList<String>brukernavn = getUsername();
		if(brukernavn.contains(user)){
			System.out.println("Brukeren finnes allerede, velg et annet brukernavn!");
		} else{
			String query = "insert into Ansatt values ('" + user + "', '" + pw + "', '"+email+"');";
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