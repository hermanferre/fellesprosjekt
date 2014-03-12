package Main;

import java.util.Scanner; 

import DB.*;

public class Avtale {
	Database db = new Database();
	EditAvtale ea = new EditAvtale();
	
	public static void main(String[] args){
		Avtale a = new Avtale();
		System.out.println("HEI");
		a.addMeet();
	}
	
	public void addMeet(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Angi starttid:");
		String start = sc.next();
		System.out.println("Angi sluttid:(HH:MM:SS)");
		String end = sc.next();
		System.out.println("Angi dato:");
		String date = sc.next();
		System.out.println("Angi sted:");
		String sted = sc.next();
		System.out.println("Angi beskrivelse:");
		String be = sc.next();
		db.addMeeting(start, end, date, sted, be);
		System.out.println("Vil du legge til moterom: 1 for ja, 2 for nei");
		int se = sc.nextInt();
		if(se == 1){
			System.out.println("Angi avtaleid");
			int id = sc.nextInt();
			ea.editMoterom(id);
		}
		else if(se == 2){
			
		}
	}
	
	
	public void addParticipants(String user, int id){
		
		db.addParticipants(user, id);
	}
	
}
