package Main;

import java.sql.SQLException;
import java.util.Scanner;

import DB.Database;

public class Ansatt {
	static Database db = new Database();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ansatt a = new Ansatt();
	}
	public static void addAnsatt(){
		boolean ok = false;
		while(!ok){
			Scanner sc = new Scanner(System.in);
			System.out.println("Angi brukernavn:");
			String user = sc.next();
			System.out.println("Angi passord:");
			String pw = sc.next();
			System.out.println("Angi epost:");
			String email = sc.next();
			try{
				db.addPerson(user, pw, email);
				ok = true;
			}catch(SQLException e){
				
			}
		}
	}

	public static void editAnsatt(){
		System.out.println("Hva vil du endre?");
		System.out.println("0: Tilbake");
		System.out.println("1: Endre brukernavn");
		System.out.println("2: Endre passord");
		System.out.println("3: Endre epost");
		Scanner sc = new Scanner(System.in);
		int com = sc.nextInt();
		if(com == 0){
			
		}else if(com == 1){
			editUsername();
		}else if(com == 2){
			editPassword();
		}else if(com == 3){
			editEmail();
		}
	}
	
	public static void editUsername(){
		boolean ok = false;
		while(!ok){
			Scanner sc = new Scanner(System.in);
			String user = KalenderSystem.getUser();
			System.out.println("Angi nytt brukernavn:");
			String newUser = sc.next();
			try{
				db.editUser(user, newUser);
				ok = true;
			}catch(SQLException e){
				
			}
		}
	}
	
	public static void editPassword(){
		boolean ok = false;
		while(!ok) { 
			Scanner sc = new Scanner(System.in);
			String user = KalenderSystem.getUser();
			System.out.println("Angi nytt passord:");
			String newPw = sc.next();
			try{
				db.editPw(user, newPw);
				ok = true;
			}catch(SQLException e){
				
			}
		}
	}
	
	public static void editEmail(){
		boolean ok = false;
		while(!ok){
			Scanner sc = new Scanner(System.in);
			String user = KalenderSystem.getUser();
			System.out.println("Angi ny email:");
			String newEm = sc.next();
			try{
				db.editEmail(user, newEm);
				ok = true;
			}catch(SQLException e){
				
			}
		}
	}
}
