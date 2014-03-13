package Main;

import java.util.Scanner;

import DB.*;
import GUI.*;

public class KalenderSystem {

	/**
	 * Jeg driver på med denne nå! - Herman
	 * @param args
	 */

	Database db;
	Scanner scanner;
	boolean loggedin;
	boolean run;
	EditAvtale ea;
	Avtale a;

	public KalenderSystem() {
		db = new Database();
		ea = new EditAvtale();
		a = new Avtale();
		scanner = new Scanner(System.in);
		loggedin = false;
		run = true;
	}

	public static void main(String[] args) {
		KalenderSystem ks = new KalenderSystem();
		ks.run();
	}

	public void run() {
		while (!loggedin) {
			String[] data = new String[2];
			System.out.println(Text.logginn);
			System.out.println(Text.brukernavn);
			data[0] = scanner.next();
			System.out.println(Text.passord);
			data[1] = scanner.next();

			if (data[1].equals(db.getPassword(data[0]))) {
				System.out.println(Text.logginn_ok);
				loggedin = true;
			} else {
				System.out.println(Text.logginn_feil);
				//System.out.println("Passord skulle vært \""+db.getPassword(data[0])+"\", men var \""+data[1]+"\"");
			}
		}

		while (run) {

			System.out.println(Text.velkommen);

			switch (printMenu(Text.hovedvalg)) {
			case 0:
				System.out.println(Text.avsluttet);
				run = false;
				break;
			case 1:
				myCalendar();
				break;
			case 2:
				appointmentMenu();
				break;
			case 3:
				employeeMenu();
				break;
			case 4:
				meetingRoomMenu();
				break;
			default:
				System.out.println(Text.provigjen);
			}
		}
	}

	private void myCalendar() {
		System.out.println("Vise kalender");
		
	}

	private void appointmentMenu() {
		switch(printMenu(Text.avtalevalg)) {
		case 0: break;
		case 1: a.addMeet(); break;
		case 2: ea.editMeny(); break;
		}
		
	}

	private void employeeMenu() {
		switch(printMenu(Text.ansattvalg)) {
		case 0: break;
		
		}
		
	}

	private void meetingRoomMenu() {
		switch(printMenu(Text.romvalg)) {
		case 0: break;
		
		}
		
	}

	public int printMenu(String[] valg) {
		for (int i = 0; i < valg.length; i++) {
			System.out.println(i+".\t"+valg[i]);
		}
		return scanner.nextInt();
	}

}
