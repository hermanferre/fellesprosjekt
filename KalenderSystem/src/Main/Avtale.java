package Main;

import java.util.Scanner;

import DB.*;

public class Avtale {
	Database db = new Database();
	
	public static void main(String[] args){
		Avtale a = new Avtale();
//		a.addMeet();
	}
	
	public void addMeet(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Angi starttid:");
		String start = sc.next();
		System.out.println("Angi sluttid:");
		String end = sc.next();
		System.out.println("Angi dato:");
		String date = sc.next();
		System.out.println("Angi sted:");
		String sted = sc.next();
		System.out.println("Angi beskrivelse:");
		String be = sc.next();
		db.addMeeting(start, end, date, sted, be, "Hallvard");
	}
	
	public void addParticipants(String user, int id){
		db.addParticipants(user, id);
	}
	
}
