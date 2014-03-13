package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DB.Database;
import Main.Appointment;

public class BuildWeek extends JDialog implements ActionListener {
	
	private static final int ANTALL_RADER = 24;
	private static final int ANTALL_KOLONNER = 8; 
	
	private JButton nesteukeknapp, forjeukeknapp, lukkeknapp;
	private JLabel labelukenummer;
	private JScrollPane skrolleomrade;
	
	private String[] kolonner = {"Klokkeslett", "Mandag","Tirsdag", "Onsdag", "Torsdag", 
			"Fredag", "Lørdag", "Søndag"};
	
	private JLabel[][] tabell;
	private JPanel tabellbeholder;
	
	private int ukenummer;
	
	public BuildWeek() {
		setTitle("ukeoversikt");
		
		Calendar cal = Calendar.getInstance();
		ukenummer = cal.get(Calendar.WEEK_OF_YEAR);
		
		Container cont = getContentPane();
		
		JPanel verktøylinje = new JPanel();
		nesteukeknapp = new JButton("->");
		forjeukeknapp = new JButton("<-");
		nesteukeknapp.addActionListener(this);
		forjeukeknapp.addActionListener(this);
		
		labelukenummer = new JLabel(""+ukenummer);
		
		verktøylinje.add(forjeukeknapp);
		verktøylinje.add(labelukenummer);
		verktøylinje.add(nesteukeknapp);
		
		
		lukkeknapp = new JButton("Lukk");
		lukkeknapp.addActionListener(this);
		
		skrolleomrade = new JScrollPane();
		tabellbeholder = new JPanel(new GridLayout(ANTALL_RADER, ANTALL_KOLONNER));
		tabell = new JLabel[ANTALL_RADER][ANTALL_KOLONNER];
	
		for(int rad = 0; rad < ANTALL_RADER; rad++) {
			for(int kol = 0; kol < ANTALL_KOLONNER; kol++) {
				tabell[rad][kol] = new JLabel(" ");
				tabellbeholder.add(tabell[rad][kol]);
				System.out.println(rad+ " " + kol);
			}
		}

		
		skrolleomrade.add(tabellbeholder);
		cont.add(skrolleomrade, BorderLayout.CENTER);
		
		
		cont.add(verktøylinje, BorderLayout.NORTH);
		cont.add(lukkeknapp, BorderLayout.SOUTH);
		pack();
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource(); 
		
		if(source == lukkeknapp) {
			setVisible(false);
			dispose();
		} else if(source == nesteukeknapp) {
			ukenummer++;
			oppdaterukevisning();
		} else if(source == forjeukeknapp) {
			ukenummer--;
			oppdaterukevisning();
			
		}
		
	}
	
	public void oppdaterukevisning() {
		labelukenummer.setText(""+ukenummer);
	}
	
	public static void buidlWeek(String brukernavn, int ukenummer) {
		Database db = new Database();
		
		
		ArrayList<Appointment> avtaleliste = db.getAppointments(brukernavn);
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, ukenummer);
		
		
		//set kalenderobjektet til mandag
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		
		//løkk igjennom uka
		for( int j = 0; j < 7; j++) {
			//System.out.println(dateFormat.format(cal.getTime()));
			
			
			for(int i = 0; i < avtaleliste.size(); i++) {
				//System.out.println(avtaleliste.get(i).dato);
				//System.out.println(dateFormat.format(cal.getTime()));
				
				Appointment avtale = avtaleliste.get(i);
				
				if(avtale.dato.equals(dateFormat.format(cal.getTime()))) {
					System.out.print(avtale.description);
				}
			}
			
			cal.add(Calendar.DATE, 1);

		}
		

		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//buidlWeek("Hallvard", 11);
		
		System.out.println("woethwohit3");
		BuildWeek buildweek = new BuildWeek();
		buildweek.setVisible(true);

	}

}
