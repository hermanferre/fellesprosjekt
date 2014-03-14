package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import DB.Database;
import Main.Appointment;

public class BuildWeek extends JDialog implements ActionListener {
	
	private static final int ANTALL_RADER = 25;
	private static final int ANTALL_KOLONNER = 8;
	private static final int TABELLMELLOMROM = 0; //piksler
	private static final Color AVTALEFARGE = new Color(0x816AEB);
	private static final Color MOTELEDERFARGE = new Color(0x4DD397);
	private static final Border SVART_GRENSE =  BorderFactory.createLineBorder(Color.black);
	
	private static Color STANDARDFARGE = Color.LIGHT_GRAY;
	
	private JButton nesteukeknapp, forjeukeknapp, lukkeknapp;
	private JLabel labelukenummer;
	private JScrollPane skrolleomrade;
	
	private static final String[] kolonneoverskrift = {"Klokka", "Mandag","Tirsdag", "Onsdag", "Torsdag", 
			"Fredag", "Lørdag", "Søndag"};
	
	private JLabel[][] tabell;
	private JPanel tabellbeholder;
	
	private int ukenummer, aar;
	private String brukernavn;
	Database db;
	
	public BuildWeek(String brukernavn) {
		STANDARDFARGE = getBackground();
		
		
		setTitle(brukernavn + " sin ukeoversikt");
		this.brukernavn = brukernavn;
		db = new Database();
		
		Calendar cal = Calendar.getInstance();
		ukenummer = cal.get(Calendar.WEEK_OF_YEAR);
		aar = cal.get(Calendar.YEAR);
		
		Container cont = getContentPane();
		
		JPanel verktoylinje = new JPanel();
		nesteukeknapp = new JButton("->");
		forjeukeknapp = new JButton("<-");
		nesteukeknapp.addActionListener(this);
		forjeukeknapp.addActionListener(this);
		
		labelukenummer = new JLabel(""+ukenummer);
		
		verktoylinje.add(forjeukeknapp);
		verktoylinje.add(labelukenummer);
		verktoylinje.add(nesteukeknapp);
		
		
		lukkeknapp = new JButton("Lukk");
		lukkeknapp.addActionListener(this);
		
		
		initTabell();

		skrolleomrade = new JScrollPane(tabellbeholder);
		cont.add(skrolleomrade, BorderLayout.CENTER);
		
		
		cont.add(verktoylinje, BorderLayout.NORTH);
		cont.add(lukkeknapp, BorderLayout.SOUTH);
		pack();
		
	}
	
	private void initTabell() {
		
		
		tabellbeholder = new JPanel(
				new GridLayout(ANTALL_RADER, ANTALL_KOLONNER,
						TABELLMELLOMROM, TABELLMELLOMROM));
		tabell = new JLabel[ANTALL_RADER][ANTALL_KOLONNER];
	
		//initialiser Jlabelene
		for(int rad = 0; rad < ANTALL_RADER; rad++) {
			for(int kol = 0; kol < ANTALL_KOLONNER; kol++) {
				tabell[rad][kol] = new JLabel(" ");
				tabellbeholder.add(tabell[rad][kol]);
				//System.out.println(rad+ " " + kol);
				tabell[rad][kol].setBorder(SVART_GRENSE); 
			}
		}
		
		//lag klokkeslett i første kolonne
		for(int rad = 1; rad < ANTALL_RADER; rad++) {
			tabell[rad][0].setText(String.format("%02d", rad-1));
		}
		
		
		
		oppdaterukevisning();
	}
	
	private void rengjorTable() {
		
		for(int rad = 1; rad < ANTALL_RADER; rad++) {
			for(int kol = 1; kol < ANTALL_KOLONNER; kol++) {
			
				tabell[rad][kol].setText(" "); 
				tabell[rad][kol].setBackground(STANDARDFARGE);
				tabell[rad][kol].setBorder(SVART_GRENSE); 
				tabell[rad][kol].setOpaque(false);
				tabell[rad][kol].setToolTipText(null);
			}
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource(); 
		
		if(source == lukkeknapp) {
			setVisible(false);
			dispose();
		} else if(source == nesteukeknapp) {
			ukenummer++;
			
			if(ukenummer > 52) {
				aar++;
				ukenummer = 1;
			}
			
			oppdaterukevisning();
		} else if(source == forjeukeknapp) {
			ukenummer--;
			
			if(ukenummer < 1) {
				aar--;
				ukenummer = 52;
			}
			
			oppdaterukevisning();
			
		}
		
	}
	
	public void oppdaterukevisning() {
		labelukenummer.setText("uke "+ukenummer+ " "+aar);
		rengjorTable();
		
		//lag kolonneoversikt
		Calendar cal = getMandagKalender();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM");
		
		tabell[0][0].setText(kolonneoverskrift[0]);
		
		for(int kol = 1; kol < ANTALL_KOLONNER; kol++) {
			tabell[0][kol].setText(kolonneoverskrift[kol]);
			
			
			String tekst = kolonneoverskrift[kol]+ " ( " + 
					dateFormat.format(cal.getTime()) + " ) ";
			
			
			tabell[0][kol].setText(tekst);
			
			cal.add(Calendar.DATE, 1);
				
		}
		
		ArrayList<Appointment> avtaleliste = db.getAppointments(brukernavn);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal = getMandagKalender();
		
		
		//løkk igjennom uka
		for( int kol = 1; kol < 8; kol++) {

			//System.out.println(dateFormat.format(cal.getTime()));
			
			
			for(int i = 0; i < avtaleliste.size(); i++) {
				//System.out.println(avtaleliste.get(i).dato);
				//System.out.println(dateFormat.format(cal.getTime()));
				
				Appointment avtale = avtaleliste.get(i);
				
				if(avtale.dato.equals(dateFormat.format(cal.getTime()))) {
					
					int klokkeindeksStart = Integer.parseInt(avtale.startTime.substring(0,2));
					int klokkeindeksSlutt = Integer.parseInt(avtale.endtime.substring(0,2));

					//sett beskrivelse som tekst på første rute
					tabell[klokkeindeksStart][kol].setText(avtale.description);
					
					
					//set fage på alle relevante rueter
					for(int klokka = klokkeindeksStart; klokka < klokkeindeksSlutt; klokka++) {
						
						JLabel rute = tabell[klokka][kol];
						
						String deltakerliste = "<html>";
						ArrayList<String> listeOverInviterte = db.getParticipants(avtale.meetingID);
						ArrayList<String> listeOverDeltar = db.getAtParticipants(avtale.meetingID);
						
						listeOverInviterte.removeAll(listeOverDeltar);
						
						rute.setOpaque(true);
						rute.setForeground(Color.white);

						if(brukernavn.equals(avtale.meetingLeader)) {
							rute.setBackground(MOTELEDERFARGE);
						} else {
							rute.setBackground(AVTALEFARGE);
							deltakerliste += "Møteleder: "+avtale.meetingLeader+"<br>";
							
						}
						rute.setBorder(BorderFactory.createLineBorder(
								rute.getBackground()));
						
						if(!deltakerliste.isEmpty()) {
							deltakerliste += "Deltar:<br>";
							for(int j = 0; j < listeOverDeltar.size(); j++)
								deltakerliste += listeOverDeltar.get(j) + "<br>";
						}
						
						if(!listeOverInviterte.isEmpty()) {
							deltakerliste += "Er invitert:<br>";
							for(int j = 0; j < listeOverInviterte.size(); j++)
								deltakerliste += listeOverInviterte.get(j) + "<br>";
						}
						
						deltakerliste += "</html>";
						rute.setToolTipText(deltakerliste);
						
					}
				}
			}
			
			cal.add(Calendar.DATE, 1);

		}
		
		
	}
	
	private Calendar getMandagKalender() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, ukenummer);
		cal.set(Calendar.YEAR, aar);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		return cal;
	}
		

		
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//buidlWeek("Hallvard", 11);
		
		//String brukernavn =JOptionPane
		
//		BuildWeek buildweek = new BuildWeek("Hallvard");
//		buildweek.setVisible(true);

	}

}
