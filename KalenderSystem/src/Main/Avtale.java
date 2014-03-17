package Main;

import java.sql.SQLException;
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
		boolean ok = false;
		while(!ok) {
			ArrayList<Appointment> avtale = db.getAppointments(KalenderSystem.getUser());
			for(int i = 0; i < avtale.size(); i++){
				System.out.println(avtale.get(i).meetingID+": "+avtale.get(i).description+" deltar: "+avtale.get(i).status+" skjult: "+avtale.get(i).skjult);
			}
			
			Scanner sc = new Scanner(System.in);
			try {
				System.out.println("0: sett status   1: skjul 2: avslutt");
				int command = sc.nextInt();
				if(command == 0){
					setStatus();
				}else if(command == 1){
					setSkjult();
				}else{
					System.out.println("Feil input");
				}
				
				ok = true;
			} catch(Exception e) {
				sc.nextLine();
			}
		}
	}
	
	public void setStatus(){
		boolean ok = false;
		while(!ok){
			ArrayList<Appointment> avtale = db.getAppointments(KalenderSystem.getUser());
			for(int i = 0; i < avtale.size(); i++){
				System.out.println(avtale.get(i).meetingID+": "+avtale.get(i).description+" deltar: "+avtale.get(i).status+" skjult: "+avtale.get(i).skjult);
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("Angi avtale du vil sette status:");
			int id = sc.nextInt();
			System.out.println("0: deltar    1: deltar ikke");
			int deltar = sc.nextInt();
			try{
				if(deltar == 0){
					db.setStatus(KalenderSystem.getUser(), id, true);
					db.setSkjult(KalenderSystem.getUser(), id, false);
				}else if(deltar == 1){
					db.setStatus(KalenderSystem.getUser(), id, false);
				}else{
					System.out.println("Ikke gyldig");
				}
				ok = true;
			}catch(SQLException e){
				
			}
		}
	}
	
	public void setSkjult(){
		boolean ok = false;
		while(!ok){
			ArrayList<Appointment> avtale = db.getAppointments(KalenderSystem.getUser());
			for(int i = 0; i < avtale.size(); i++){
				System.out.println(avtale.get(i).meetingID+": "+avtale.get(i).description+" deltar: "+avtale.get(i).status+" skjult: "+avtale.get(i).skjult);
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("Angi avtale du vil skjule:");
			int id = sc.nextInt();
			try{
				db.setSkjult(KalenderSystem.getUser(), id, true);
				ok = true;
			}catch(SQLException e){
				
			}
		}
	}
	
	public void addMeet(){
		boolean ok = false;
		while(!ok){
			Scanner sc = new Scanner(System.in);
			System.out.println("Angi starttid:(HH:MM:SS)");
			String start = sc.nextLine();
			System.out.println("Angi sluttid:(HH:MM:SS)");
			String end = sc.nextLine();
			System.out.println("Angi dato:(YYYY-MM-DD)");
			String date = sc.nextLine();
			System.out.println("Angi sted:");
			String sted = sc.nextLine();
			System.out.println("Angi beskrivelse:");
			String be = sc.nextLine();
			String moteleder = KalenderSystem.getUser();
			try{
				db.addMeeting(start, end, date, sted, be, moteleder);
				ok = true;
			}catch(SQLException e){
				continue;
			}
			int id = db.getAvtaleId();
			System.out.println("AvtaleIDen er " + id);
			System.out.println("Vil du legge til moterom: 1 for ja, 2 for nei");
			String se = sc.nextLine();
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
	}
	
	
	public void addParticipants(String user, int id){
		
		db.addParticipants(user, id);
	}
	
}
