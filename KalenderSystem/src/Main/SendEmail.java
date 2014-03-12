package Main;

import java.util.ArrayList;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import DB.Database;
import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;



public class SendEmail {
	Database db = new Database();
	
	public static void main(String[] args){
		SendEmail se = new SendEmail();
		se.getEmails(2);
		
		try {
			for(int i=0;i<10;i++){
			Send("pu.gruppe42", "gruppe42fp", "rubschmi@gmail.com", "Fuck yeeah", "Dette er en personlig beskjed fra din elskede");
			}
		}catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public ArrayList<String> getEmails(int id){
		ArrayList<String> emails = new ArrayList<String>();
		emails = db.getEmails(id);
		return emails;
	}
	
	public void sendEmail(int id){
		ArrayList<String> emails = getEmails(id);
		try{
			for(int i = 0; i < emails.size(); i++){
			//for (emails : email)
				Send("pu.gruppe42", "gruppe42fp", emails.get(i), "Emne", "text");
			}
		}catch(AddressException e){
			e.printStackTrace();
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}

	private SendEmail() {
	}
	public static void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
		SendEmail.Send(username, password, recipientEmail, "", title, message);
	}
	public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false");
		Session session = Session.getInstance(props, null);
		final MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username + "@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
		if (ccEmail.length() > 0) {
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
		}
		msg.setSubject(title);
		msg.setText(message, "utf-8");
		msg.setSentDate(new Date());
		SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
		t.connect("smtp.gmail.com", username, password);
		t.sendMessage(msg, msg.getAllRecipients());      
		t.close();
	}







}