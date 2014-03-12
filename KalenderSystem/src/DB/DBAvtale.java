package DB;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class DBAvtale {
	DBConnection db = new DBConnection();
	DBMoterom dbm = new DBMoterom();
	DBAnsatt dbansatt = new DBAnsatt();
	DBAvtale dbavtale = new DBAvtale();
	public static void main(String[] args) throws SQLException{
	
	}
	public DBAvtale(){

		System.out.println(getDate(1));

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
	
	
	public void addMeeting(int id, String start, String end, String date, String sted, String beskrivelse, String leader){
		String query = "insert into Avtale values ("+id+",'"+start+"','"+end+"','"+date+"','"+sted+"','"+beskrivelse+"', null,'"+leader+"');";
		ArrayList<Integer> ap = getAppointments();
		ArrayList<String> user = dbansatt.getUsername();
		if(ap.contains(id)){
			System.out.println("Denne avtaleid finnes allerede");
		}else if(!user.contains(leader)){
			System.out.println("Ikke gyldig brukernavn");
		}else{
			System.out.println("HEI");
			db.updateQuery(query);
		}
	}
	
	public void removeMeetingRoom(int id){
		String query = "update Avtale set moterom = null where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editStart(int id, String start){
	String query = "update Avtale set starttid = " + start + " where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editEnd(int id, String end){
		String query = "update Avtale set sluttid = " + end + " where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editDate(int id, String date){
		String query = "update Avtale set dato = " + date + " where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editPlace(int id, String sted){
		String query = "update Avtale set sted = " + sted + " where avtaleid = " + id + ";";
		db.updateQuery(query);
	}
	
	public void editBeskrivelse(int id, String be){
		String query = "update Avtale set beskrivelse = '" + be + " where avtaleid = "' + id + ";";
		db.updateQuery(query);
	}
	
	public void editMeetingRoom(int id, int room){
		String query = "update Avtale set moterom = " + room + " where avtaleid = " + id + ";";
		ArrayList<Integer> rooms = new ArrayList<Integer>();
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
	
	public void getAllApp(String user){
		String query = "select avtaleid, starttid, sluttid, dato from Ansatt an natural join Deltaker d natural join Avtale av where brukernavn = '" + user + "'and an.brukernavn = d.ansatt and d.avtale = av.avtaleid;";
				//select avtaleid, starttid, sluttid, dato from Ansatt an natural join Deltaker d natural join Avtale av where brukernavn = 'Hallvard' and an.brukernavn = d.ansatt and d.avtale = av.avtaleid;
	}
}
