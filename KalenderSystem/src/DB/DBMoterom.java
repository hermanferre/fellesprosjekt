package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBMoterom {
	DBConnection db;
	public static void main(String[] args) throws SQLException{
		DBMoterom dbm = new DBMoterom();
	}
	public DBMoterom(){
		db = new DBConnection();
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
