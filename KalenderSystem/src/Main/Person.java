package Main;

public class Person {
	private String username;
	private String password;
	private String query;
	
	public Person(String username, String password) {
		query = "INSERT INTO Ansatt VALUES ("+username+", "+password+");";
	}
	
	public static void main(String args[]) {
		
	}
	
	public void setName(String name) {
		query = "INSERT INTO Ansatt VALUES ("+username+", "+password+");";
	}
	
	public void getRowByUsername(String u) {
		query = "SELECT * FROM Ansatt WHERE username="+u+");";
	}
}
