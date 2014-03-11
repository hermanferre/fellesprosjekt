package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBAnsatt {
	DBConnection db;
	DBAvtale dbavtale;
	DBMoterom dbm;
	public static void main(String[] args) throws SQLException{
		DBAnsatt dbm = new DBAnsatt();
	}
	public DBAnsatt(){
		db = new DBConnection();
		dbavtale = new DBAvtale();
		dbm = new DBMoterom();
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
		return pw;
	}
}
