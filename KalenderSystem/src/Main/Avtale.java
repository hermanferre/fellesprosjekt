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
		String moteleder = KalenderSystem.getUser();
		db.addMeeting(start, end, date, sted, be, moteleder);
		int id = db.getAvtaleId();
		System.out.println("AvtaleIDen er " + id);
		System.out.println("Vil du legge til moterom: 1 for ja, 2 for nei");
		String se = sc.next();
		int se1 = 0;
		try{
			se1 = Integer.parseInt(se);
		}catch(RuntimeException e){
			throw new RuntimeException(e);
		}
		if(se1 == 1){
			ea.editMoterom(id);
		}
		else if(se1 == 2){
			
		}
	}
	
	
	public void addParticipants(String user, int id){
		
		db.addParticipants(user, id);
	}
	
}
