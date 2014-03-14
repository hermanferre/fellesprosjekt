package Main;

import java.util.ArrayList;
import java.util.Scanner;

import DB.*;


public class EditAvtale {
	Database db = new Database();
	SendEmail se = new SendEmail();

	public static void main(String[] args){
		EditAvtale ea = new EditAvtale();
		ea.editMeny();
	}

	public void editMeny(){
		ArrayList<Appointment> avtale = db.getAppointmentsMoteleder(KalenderSystem.getUser());
		for(int i = 0; i < avtale.size(); i++){
			System.out.println(avtale.get(i).meetingID+": Starttid: "+avtale.get(i).startTime+" Sluttid: "+
					avtale.get(i).endtime+" Dato: "+avtale.get(i).dato+" Sted: "+avtale.get(i).place+
					" Beskrivelse: "+avtale.get(i).description+" Moterom: "+avtale.get(i).meetingRoom);
		}
		System.out.println("Angi avtaleid du vil endre pï¿½");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		int tall = 0;
		try {
			tall = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.out.println("Dette var ikke et tall. FÃ¸kk deg");
		}

		/*if ()  {

		}*/
		String sjef = db.getMoteleder(tall);
		String leder = KalenderSystem.getUser();
		try{
			if(sjef.equals(leder)){
				System.out.println("1: endre starttid og sluttid");
				System.out.println("2: endre dato");
				System.out.println("3: endre sted");
				System.out.println("4: endre beskrivelse");
				System.out.println("5: endre moterom");
				System.out.println("6: slette avtale");
				System.out.println("7: legg til deltakere");
				System.out.println("8: fjern deltaker");
				sc = new Scanner(System.in);
				int com = sc.nextInt();

				if(com == 1)
					editStartAndEnd(tall);
				else if(com == 2)
					editDato(tall);
				else if(com ==3)
					editSted(tall);
				else if(com == 4)
					editBeskrivelse(tall);
				else if(com == 5)
					editMoterom(tall);
				else if(com == 6)
					removeMote(tall);
				else if(com == 7)
					leggTilDeltaker(tall);
				else if(com == 8)
					removeDeltaker(tall);
				else
					System.out.println("ikke gyldig tall");
			}else{
				System.out.println("Du er ikke moteleder");
			}

		} catch (NullPointerException e) {
			System.out.println("Ugyldig. Prøv igjen");
			editMeny();
		}
	}

	public void removeDeltaker(int id){
		System.out.println("Deltakerliste:");
		System.out.println(db.getParticipants(id));
		System.out.println("Skriv inn brukernavn du vil fjerne:");
		Scanner sc = new Scanner(System.in);
		String user = sc.next();
		db.removeParticipants(user, id);
		se.sendEmail(id, "Du er ikke lengre med på mote " + id);
	}

	public void removeMote(int id){
		String sjef = db.getMoteleder(id);
		String leder = KalenderSystem.getUser();
		if(sjef.equals(leder)){
			db.removeMeeting(id);
//			se.sendEmail(id, "Mote " + id + " er slettet");
		}else{
			System.out.println("Du er ikke moteleder for denne avtalen");
		}
	}

	private void leggTilDeltaker(int id) {

		//TODO: sjekk om innlogga bruker er mÃ¸teleder
		/*
		if( .... != ....) {
			System.out.println("Du er ikke mÃ¸teleder");
			return;
		}
		 */

		ArrayList<String> brukernavnliste = db.getUsername();
		String leder = db.getMoteleder(id);
		if(brukernavnliste.contains(leder)){
			brukernavnliste.remove(leder);
		}

		Scanner sc = new Scanner(System.in);
		boolean fortsett = true;

		while ( fortsett ) {
			System.out.println("Inviter deltakere til mote");


			System.out.println("For a legge til deltaker, skriv inn brukernavnet til deltakeren du vil invitere");
			System.out.println("Skriv inn \'listbrukere\' for a fa ei lsite over alle brukere");
			System.out.println("Skriv inn \'Avslutt\' for a avslutte\n");

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
				String message = "Du er lagt til i mote nr "+id;
				//				se.sendEmailOne(id, message, innputt);

			} else {
				System.out.println(innputt+ " er ikke en gyldig kommando eller brukernavn. Prov pa nytt");
			}



		}
	}

	public void editStartAndEnd(int ID){
		System.out.println("Legg til ny starttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editStart(ID, com);
		System.out.println("Legg til ny sluttid(HH:MM:SS): ");
		String com1 = sc.nextLine();
		db.editEnd(ID, com1);
		db.removeMeetingRoom(ID);
		System.out.println("Onsker du ï¿½ legge til nytt moterom: 1 - ja  2 - nei");
		int command = sc.nextInt();
		if(command == 1){
			editMoterom(ID);
		}
		//		se.sendEmail(ID, "Ny starttid for mote " + ID + " er " + com);
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
		ArrayList<Integer> roomCap = new ArrayList<Integer>();
		for(int i = 0; i < avRooms.size(); i++){
			roomCap.add(db.getRoomCap(avRooms.get(i)));
		}
		System.out.println("Ledige room er:");
		System.out.println(avRooms);
		System.out.println("med henholdsvis romkapasitet:");
		System.out.println(roomCap);
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
