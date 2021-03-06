package Main;

import java.sql.SQLException;
import java.util.Scanner;

import DB.*;
import GUI.*;

public class KalenderSystem {

	/**
	 * Jeg driver p� med denne n�! - Herman
	 * @param args
	 */

	Database db;
	static Scanner scanner;
	boolean loggedin;
	boolean run;
	EditAvtale ea;
	Avtale a;
	private static String user;

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
		ks.startMenu();
		ks.run();
	}

	public void startMenu() {
		System.out.println(Text.velkommen);
		while (!loggedin && run) {
			switch (printMenu(Text.brukervalg)) {
			case 0:
				System.out.println(Text.avsluttet);
				run = false;
				break;
			case 1: login(); break;
			case 2: addUser(); break;
			}
		}
	}

	public void run() {
		while (run) {
			System.out.println(Text.innloggetsom + user);
			System.out.println(Text.valg);
			
			try{
				switch (printMenu(Text.hovedvalg)) {
				case 0:
					System.out.println(Text.avsluttet);
					run = false;
					break;
				case 1:
					//myCalendar();
					viewCalendar();
					break;
				case 2:
					appointmentMenu();
					break;
				case 3:
					employeeMenu();
					break;
				default:
					System.out.println(Text.provigjen);
				}
			}catch(Exception e){
				scanner.nextLine();
			}
		}
	}


	public void login() {
		while (!loggedin) {
			String[] data = new String[2];
			System.out.println(Text.brukervalg[1]);
			System.out.println(Text.brukernavn);
			data[0] = scanner.next();
			System.out.println(Text.passord);
			data[1] = scanner.next();

			if (data[1].equals(db.getPassword(data[0]))) {
				System.out.println(Text.logginn_ok);
				loggedin = true;
				user = data[0];
			} else {
				System.out.println(Text.logginn_feil);
				//System.out.println("Passord skulle v�rt \""+db.getPassword(data[0])+"\", men var \""+data[1]+"\"");
			}
		}
	}

	public void addUser() {
		boolean ok = false;
		while(!ok){
			String[] data = new String[4];
			System.out.println(Text.brukervalg[2]);
			System.out.println(Text.brukernavn);
			data[0] = scanner.next();
			if (db.getPassword(data[0]) != null) {
				System.out.println(Text.opprett_feilb);
				System.out.println(Text.returneres);
			} else {
				System.out.println(Text.passord);
				data[1] = scanner.next();
				System.out.println(Text.gjenta_pw);
				data[2] = scanner.next();
				if (!data[1].equals(data[2])) {
					System.out.println(Text.opprett_feilp);
					System.out.println(Text.returneres);
				} else {
					try{
						System.out.println(Text.email);
						data[3] = scanner.next();
						loggedin = true;
						user = data[0];
						System.out.println(Text.opprett_ok + user);
						db.addPerson(data[0], data[1], data[3]);
						ok = true;
					}catch(SQLException e){
						
					}
				}
			}
		}
	}

	private void viewCalendar() {
		switch(printMenu(Text.kalvalg)) {
		case 0: break;
		case 1: showCalendar(getUser()); break;
		case 2: a.statusMeny(); break;
		case 3: 
			System.out.println(Text.hvilkenbruker);
			showCalendar(scanner.next()); break;
		}

	}

	private void showCalendar(String user) {
		BuildWeek bw = new BuildWeek(user);
		bw.setVisible(true);
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
		case 1: Ansatt.addAnsatt(); break;
		case 2: Ansatt.editAnsatt(); break;
		}

	}

	public static int printMenu(String[] valg) {
		boolean ok = false;
		int valgnummer = 0;
			while(!ok) {
			try {
				for (int i = 0; i < valg.length; i++) {
					System.out.println(i+".\t"+valg[i]);
				}
				valgnummer =  scanner.nextInt();
				ok = true;
			} catch (Exception e) {
				scanner.nextLine();
			}
		}
		
		return valgnummer;
	}

	public static String getUser() {
		return user;
	}

}
