package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	
	private static Connection connection = null;
	private static String connectionURL = "jdbc:mysql://mysql.stud.ntnu.no:3306/hallvarh_fp";
	private static Statement stat = null;
	private static String user = "hallvarh";
	private static String pw = "gruppe42";
	private static String database = "hallvarh";
	
	//Logger på databasen
	public DBConnection(){
		try{
			connection = DriverManager.getConnection(connectionURL, user, pw);
			stat = connection.createStatement();
		} catch(Exception e){
			System.out.println("Tilkobling til databaseserver feilet: " + e.getMessage());
			System.out.println("Må være på NTNU-nettet for å koble til");
		}
	}
	
	//Stenger tilkoblingen
	public void close(){
		try{
			connection.close();
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public ResultSet readQuery(String sql){
		Statement s;
		try{
			s = connection.createStatement();
			ResultSet rs;
			
			rs = s.executeQuery(sql);
			return rs;
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void updateQuery(String sql){
		Statement s;
		try{
			s = connection.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
