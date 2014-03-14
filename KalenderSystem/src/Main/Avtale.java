package Main;

import java.util.ArrayList;
import java.util.Scanner; 

import DB.*;

public class Avtale {
	Database db = new Database();
	EditAvtale ea = new EditAvtale();
	
	public static void main(String[] args){
		Avtale a = new Avtale();
		a.statusMeny();
	}
	
	public void statusMeny(){
		ArrayList<Appointment> avtale = db.getAppointments(KalenderSystem.getUser());
		for(int i = 0; i < avtale.size(); i++){
			System.out.println(avtale.get(i).meetingID+": "+avtale.get(i).description+" deltar: "+avtale.get(i).status+" skjult: "+avtale.get(i).skjult);
		}
		System.out.println("0: sett status   1: skjul");
		Scanner sc = new Scanner(System.in);
		int command = sc.nextInt();
		if(command == 0){
			setStatus();
		}else if(command == 1){
			setSkjult();
		}else{
			System.out.println("Feil input");
		}
	}
	
	public void setStatus(){
		ArrayList<Appointment> avtale = db.getAppointments(KalenderSystem.getUser());
		for(int i = 0; i < avtale.size(); i++){
			System.out.println(avtale.get(i).meetingID+": "+avtale.get(i).description+" deltar: "+avtale.get(i).status+" skjult: "+avtale.get(i).skjult);
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Angi avtale du vil sette status:");
		int id = sc.nextInt();
		System.out.println("0: deltar    1: deltar ikke");
		int deltar = sc.nextInt();
		if(deltar == 0){
			db.setStatus(KalenderSystem.getUser(), id, true);
		}else if(deltar == 1){
			db.setStatus(KalenderSystem.getUser(), id, false);
		}else{
			System.out.println("Ikke gyldig");
		}
	}
	
	public void setSkjult(){
		ArrayList<Appointment> avtale = db.getAppointments(KalenderSystem.getUser());
		for(int i = 0; i < avtale.size(); i++){
			System.out.println(avtale.get(i).meetingID+": "+avtale.get(i).description+" deltar: "+avtale.get(i).status+" skjult: "+avtale.get(i).skjult);
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Angi avtale du vil skjule:");
		int id = sc.nextInt();
		db.setSkjult(KalenderSystem.getUser(), id);
	}
	
	public void addMeet(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Angi starttid:(HH:MM:SS)");
		String start = sc.next();
		System.out.println("Angi sluttid:(HH:MM:SS)");
		String end = sc.next();
		System.out.println("Angi dato:(YYYY-MM-DD)");
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
