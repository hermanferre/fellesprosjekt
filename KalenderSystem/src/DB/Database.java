package DB;

import java.sql.SQLException;

public class Database {
	
	DBConnection db;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void getUsername(String username){
		String query;
	}
	
	public void getParrword(String username){
		String query;
	}
	
	public void getStartEndTime(int avtaleID){
		String query;
	}
	
	public void getMeetingRoom(int avtaleID){
		String query;
	}
	
	public void getRoom(int roomNr) throws ClassNotFoundException, SQLException{
		String query = String.format("select * from moterom where romnr = %d", roomNr);
		db.initialize();
		
	}

}
