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
		System.out.println(databaseTest.getUsername());
//		databaseTest.addPerson("h", "pw");
//		databaseTest.addPerson("h", "pw");
//		databaseTest.getUsername();
//		databaseTest.removePerson("h");
//		databaseTest.getUsername();
//		databaseTest.getRooms();
//		System.out.println(databaseTest.getRooms());
//		databaseTest.addRoom(1, 2);
	}
	
	public Database(){
		db = new DBConnection();
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
		String query = "delete from Ansatt where brukernavn = '" + user + "';";
		db.updateQuery(query);
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
		System.out.println(pw);
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
	
	
	
	public void getStartEndTime(int avtaleID){
		String query;
	}
	
	public void getMeetingRoom(int avtaleID){
		String query;
	}

}
