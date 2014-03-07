package DB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBConnection {

	private Properties properties; // file containing the connection properties
	private String jdbcDriver; // String containing the driver Class name
	private String url; // Address to the database
	private Connection conn;

	
	public DBConnection(String propertiesFilename) throws IOException, ClassNotFoundException, SQLException {
		File f = new File(propertiesFilename);
		properties = new Properties();
		properties.load(new FileInputStream(f));
		jdbcDriver = properties.getProperty("jdbcDriver");
		url = properties.getProperty("url");
	}
	
	
	public DBConnection(Properties properties) throws ClassNotFoundException, SQLException {
		jdbcDriver = properties.getProperty("jdbcDriver");
		url = properties.getProperty("url");
		this.properties=properties;
	}
	
	
	public void initialize() throws ClassNotFoundException, SQLException {
		
		Class.forName(jdbcDriver);
		Properties info = new Properties();
	
		info.setProperty("user", properties.getProperty("user"));
		info.setProperty("password", properties.getProperty("user"));
		conn = DriverManager.getConnection(url, info);
	}
	
	
	public ResultSet makeSingleQuery(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}
	
	
	public void makeSingleUpdate(String sql) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}
	
	
	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	
	public void close() throws SQLException {
		conn.close();
	}

}


package util;
import java.sql.Connection;
import java.sql.DriverManager;

//Brukes for å koble applikasjonen opp mot serveren
//Returnerer en connection man kan bruke for å lage MySQL statements(spørringer) og andre ting
//ConnectionConfiguration configuration = new ConnectionConfiguration(); --> Skriv configuration. gir forskjellige muligheter for å 
//gjøre en spørring

public class ConnectionConfiguration {

	private static String driver = "com.mysql.jdbc.Driver";
	
	// Database connection settings
	private static String dbConnectionString = "jdbc:mysql://mysql.stud.ntnu.no:3306/martinoy_felles";
	private static final String brukernavn = "martinoy";
	private static final String passord = "nisse123";
	
	
	// Returns a connection to database specified in connection settings
	public static Connection getConnection(){
		Connection con = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(dbConnectionString, brukernavn , passord);
				
			} catch (Exception e) {
				System.out.println("Could not establish connection to server. Please check your internet connection and connection settings. \n" + e.toString());
			}
			
		return con;
	}


}
