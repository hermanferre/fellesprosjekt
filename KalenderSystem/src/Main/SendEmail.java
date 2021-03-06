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
	}
	
	public ArrayList<String> getEmails(int id){
		ArrayList<String> emails = new ArrayList<String>();
		emails = db.getEmails(id);
		return emails;
	}
	
	public String getEmail(String user){
		String email = null;
		email = db.getEmail(user);
		return email;
	}
	
	public void sendEmailOne(int id, String message, String user){
		String email = getEmail(user);
		System.out.println(email);
		if(!email.equals("")){
			try{
				//for (emails : email)
				
				Send("pu.gruppe42", "gruppe42fp", email, "Emne", message);
			}catch(AddressException e){
				e.printStackTrace();
			}catch(MessagingException e){
				e.printStackTrace();
			}
		}else{
			System.out.println("Brukeren har ikke epost");
		}
	}
	
	public void sendEmail(int id, String message){
		ArrayList<String> emails = getEmails(id);
		try{
			for(int i = 0; i < emails.size(); i++){
			//for (emails : email)
				if(!emails.get(i).equals("")){
					Send("pu.gruppe42", "gruppe42fp", emails.get(i), "Emne", message);
				}else{
					System.out.println("Brukeren har ikke epost");
				}
			}
		}catch(AddressException e){
			e.printStackTrace();
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}

	SendEmail() {
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