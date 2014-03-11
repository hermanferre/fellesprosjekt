package Main;

import java.util.Scanner;

import DB.DBMoterom;
import DB.DBAnsatt;
import DB.DBAvtale;

public class EditAvtale {
	DBMoterom dbm = new DBMoterom();
	DBAvtale dbavtale = new DBAvtale();
	DBAnsatt dbansatt = new DBAnsatt();
	public static void main(String [] args){
		EditAvtale ea = new EditAvtale();
		ea.editMeny();
	}

	public void editMeny(){
		System.out.println("Angi avtaleid)");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		int tall;
		try {
			tall = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.out.println("Dette var ikke et tall. Føkk deg");
		}
		
		System.out.println("0: endre starttid");
		System.out.println("1: endre sluttid");
		System.out.println("2: endre dato");
		System.out.println("3: endre sted");
		System.out.println("4: endre beskrivelse");
		System.out.println("5: endre moterom");
		sc = new Scanner(System.in);
		String com = sc.nextLine();
		
		if(com == "0"){
			editStart(tall);
			else if(com == "1")
				editSlutt(tall);
			else if(com == "2")
				editDato(tall);
			else if(com =="3")
				editSted(tall);
			else if(com == "4")
				editBeskrivelse(tall);
			else if(com =="5")
				editMoterom(tall);
			else
				System.out.println("ikke gyldig tall");	
		}
	public void editStart(int ID){
		System.out.println("Legg til ny starttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		dbavtale.editStart(ID, com);
	}
	public void editSlutt(int ID){
		System.out.println("Legg til ny sluttid(HH:MM:SS): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		dbavtale.editEnd(ID, com);
	}
	public void editDato(int ID){
		System.out.println("Legg til ny dato(YYYY-MM-DD): ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		dbavtale.editDate(ID, com);
	}
	public void editSted(int ID){
		System.out.println("Legg til ny stedsbeskrivelse: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		dbavtale.editPlace(ID, com);
	}
	public void editBeskrivelse(int ID){
		System.out.println("Legg til ny beskrivelse: ");
		Scanner sc = new Scanner(System.in);
		String com = sc.nextLine();
		dbavtale.editBeskrivelse(ID, com);
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
		dbavtale.editMeetingRoom(ID, tall2);
	}
}
