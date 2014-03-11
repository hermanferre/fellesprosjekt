package Main;

import java.util.Scanner;

import DB.*;
import GUI.*;

public class KalenderSystem {

	/**
	 * Jeg driver p� med denne n�! - Herman
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
<<<<<<< HEAD
		a = new Avtale();
=======
>>>>>>> 54da783608a3d85447a15e22a3ae8e88afce5509
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
				//System.out.println("Passord skulle v�rt \""+db.getPassword(data[0])+"\", men var \""+data[1]+"\"");
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
				a.addMeet();
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
		for (int i = 0; i < Text.hovedvalg.length; i++) {
			System.out.println(i+".\t"+Text.hovedvalg[i]);
		}
	}

}
