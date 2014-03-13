package Main;

import java.util.ArrayList;
import java.util.Scanner;

import DB.*;


public class EditAvtale {
	Database db = new Database();
	SendEmail se = new SendEmail();
	
	public static void main(String[] args){
		EditAvtale ea = new EditAvtale();
//		ea.editMeny();
	}

	public void editMeny(){
		System.out.println("Angi avtaleid");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		int tall = 0;
		try {
			tall = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.out.println("Dette var ikke et tall. Føkk deg");
		}
		
		System.out.println("1: endre starttid");
		System.out.println("2: endre sluttid");
		System.out.println("3: endre dato");
		System.out.println("4: endre sted");
		System.out.println("5: endre beskrivelse");
		System.out.println("6: endre moterom");
		System.out.println("7: slette avtale");
		System.out.println("8: legg til deltakere");
		sc = new Scanner(System.in);
		int com = sc.nextInt();
		
		if(com == 1)
			editStart(tall);
			else if(com == 2)
				editSlutt(tall);
			else if(com == 3)
				editDato(tall);
			else if(com ==4)
				editSted(tall);
			else if(com == 5)
				editBeskrivelse(tall);
			else if(com == 6)
				editMoterom(tall);
			else if(com == 7)
				removeMote(tall);
			else if(com == 8)
				leggTilDeltaker(tall);
			else
				System.out.println("ikke gyldig tall");	
		}
	
	public void removeMote(int id){
		String sjef = db.getMoteleder(id);
		db.removeMeeting(id);
	}
	
	private void leggTilDeltaker(int id) {
		
		//TODO: sjekk om innlogga bruker er møteleder
		/*
		if( .... != ....) {
			System.out.println("Du er ikke møteleder");
			return;
		}
		*/
		
		ArrayList<String> brukernavnliste = db.getUsername();
		
		Scanner sc = new Scanner(System.in);
		boolean fortsett = true;
		
		while ( fortsett ) {
			System.out.println("Inviter deltakere til møte");
			
			
			System.out.println("For å legge til deltaker, skriv inn brukernavnet til deltakeren du vil invitere");
			System.out.println("Skriv inn \'listbrukere\' for å få ei lsite over alle brukere");
			System.out.println("Skriv inn \'Avslutt\' for å avslutte\n");
			
			String innputt = sc.nextLine();
			
			
			if(innputt.equalsIgnoreCase("Avslutt")) {
				fortsett = false;
			} else if( innputt.equalsIgnoreCase("listbrukere")) {
				for(int i = 0; i < brukernavnliste.size(); i++) {
					System.out.print(brukernavnliste.get(i) + ", ");
				}
				System.out.println();
			} else if( brukernavnliste.contains(innputt)) {
				db.addParticipants(innputt, id);
				
			} else {
				System.out.println(innputt+ " er ikke en gyldig kommando eller brukernavn. Prøv på nytt");
			}
			
			
		
		}
	}
	
	public void editStart(int ID){
		System.out.println("Legg til ny starttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editStart(ID, com);
//		se.sendEmail(ID, "Ny starttid for mote " + ID + " er " + com);
	}
	public void editSlutt(int ID){
		System.out.println("Legg til ny sluttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editEnd(ID, com);
//		se.sendEmail(ID, "Ny sluttid for mote " + ID + " er " + com);
	}
	public void editDato(int ID){
		System.out.println("Legg til ny dato(YYYY-MM-DD): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editDate(ID, com);
//		se.sendEmail(ID, "Ny dato for mote " + ID + " er " + com);
	}
	public void editSted(int ID){
		System.out.println("Legg til ny stedsbeskrivelse: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editPlace(ID, com);
//		se.sendEmail(ID, "Ny plass for mote " + ID + " er " + com);
	}
	public void editBeskrivelse(int ID){
		System.out.println("Legg til ny beskrivelse: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editBeskrivelse(ID, com);
//		se.sendEmail(ID, "Ny beskrivelse for mote " + ID + " er " + com);
	}
	public void editMoterom(int ID){
		System.out.println("Legg til nytt moterom: ");
		ArrayList<Integer> avRooms = db.getAvRoom(ID);
		System.out.println("Ledige room er:");
		System.out.println(avRooms);
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		int tall2 = 0;
		try {
			tall2 = Integer.parseInt(com);
		} catch (NumberFormatException e) {
			System.out.println("Dette var ikke et tall");
		}
		db.editMeetingRoom(ID, tall2);
//		se.sendEmail(ID, "Nytt moterom for mote " + ID + " er " + tall2);
	}
}
