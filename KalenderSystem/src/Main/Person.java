package Main;

import java.sql.*;

public class Person {
	private String username;
	private String password;
	private String name;
	private String query;
	
	public Person(String username, String password) {
		query = "INSERT INTO Ansatt VALUES ("+username+", "+password+");";
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		System.out.println(getName("hermannf"));
		System.out.println(getName("edvards"));
	}
	
	public void setName(String name) {
		query = "INSERT INTO Ansatt VALUES ("+username+", "+password+");";
	}
	
	public static String getName(String u) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/hallvarh_fp", "hallvarh", "gruppe42");
		Statement stmt = conn.createStatement();
		String query = "SELECT passord FROM Ansatt WHERE brukernavn='"+u+"';";
		//System.out.println(query);
		ResultSet rs = stmt.executeQuery(query);
		return rs.next() ? rs.getString("passord") : "Feil";
	}
}
