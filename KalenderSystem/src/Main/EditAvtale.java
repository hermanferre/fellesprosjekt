package Main;

import java.util.Scanner;
import DB.*;


public class EditAvtale {
	Database db = new Database();
	
	public static void main(String[] args){
		EditAvtale ea = new EditAvtale();
		ea.editMeny();
	}

	public void editMeny(){
		System.out.println("Angi avtaleid)");
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
			else
				System.out.println("ikke gyldig tall");	
		}
	public void editStart(int ID){
		System.out.println("Legg til ny starttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editStart(ID, com);
	}
	public void editSlutt(int ID){
		System.out.println("Legg til ny sluttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editEnd(ID, com);
	}
	public void editDato(int ID){
		System.out.println("Legg til ny dato(YYYY-MM-DD): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editDate(ID, com);
	}
	public void editSted(int ID){
		System.out.println("Legg til ny stedsbeskrivelse: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editPlace(ID, com);
	}
	public void editBeskrivelse(int ID){
		System.out.println("Legg til ny beskrivelse: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		db.editBeskrivelse(ID, com);
	}
	public void editMoterom(int ID){
		System.out.println("Legg til nytt møterom: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		int tall2 = 0;
		try {
			tall2 = Integer.parseInt(com);
		} catch (NumberFormatException e) {
			System.out.println("Dette var ikke et tall");
		}
		db.editMeetingRoom(ID, tall2);
	}
}
