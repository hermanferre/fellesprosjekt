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

	public KalenderSystem() {
		db = new Database();
		ea = new EditAvtale();
		System.out.println(db.getRoomCap(201));
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
			printMenu();

			switch (scanner.nextInt()) {
			case 0:
				System.out.println(Text.avsluttet);
				run = false;
				break;
			case 1:
				addAppointment();
				break;
			case 2:
				ea.editMeny();
				break;
			case 3:
				addEmployee();
				break;
			case 4:
				editEmployee();
				break;
			case 5:
				addMeetingRoom();
				break;
			case 6:
				editMeetingRoom();
				break;
			default:
				System.out.println(Text.provigjen);
			}
		}
	}

	private void addAppointment() {
		// TODO Auto-generated method stub
		
	}

	private void editAppointment() {
		// TODO Auto-generated method stub
		
	}

	private void addEmployee() {
		// TODO Auto-generated method stub
		
	}

	private void editEmployee() {
		// TODO Auto-generated method stub
		
	}

	private void addMeetingRoom() {
		// TODO Auto-generated method stub
		
	}

	private void editMeetingRoom() {
		// TODO Auto-generated method stub
		
	}

	public void printMenu() {
		System.out.println(Text.velkommen);
		for (int i = 0; i < Text.valg.length; i++) {
			System.out.println(i+".\t"+Text.valg[i]);
		}
	}

}
