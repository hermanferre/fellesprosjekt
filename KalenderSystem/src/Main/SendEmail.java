package Main;

import java.util.ArrayList;

import DB.Database;

//import com.sun.mail.smtp.SMTPTransport;
//import java.security.Security;
//import java.util.Date;
//import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;


public class SendEmail {
	Database db = new Database();
	
	public static void main(String[] args){
		SendEmail se = new SendEmail();
		se.getEmails(2);
		
		try {
			Send("pu.felles", "nisse123", "lars.krakevik@gmail.com", "unnskyld", "jeg lover a aldri sende spam igjen");
		} catch (AddressException e) {
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
	

//		public static void main(String [] args){
//			try {
//				Send("pu.felles", "nisse123", "lars.krakevik@gmail.com", "unnskyld", "jeg lover a aldri sende spam igjen");
//			} catch (AddressException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (MessagingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}		
//		}
//	    private GoogleMail() {
//	    }
	    public static void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
	        GoogleMail.Send(username, password, recipientEmail, "", title, message);
	    }
//	    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
//	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//	        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//	        Properties props = System.getProperties();
//	        props.setProperty("mail.smtps.host", "smtp.gmail.com");
//	        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//	        props.setProperty("mail.smtp.port", "465");
//	        props.setProperty("mail.smtp.socketFactory.port", "465");
//	        props.setProperty("mail.smtps.auth", "true");
//	        props.put("mail.smtps.quitwait", "false");
//	        Session session = Session.getInstance(props, null);
//	        final MimeMessage msg = new MimeMessage(session);
//	        msg.setFrom(new InternetAddress(username + "@gmail.com"));
//	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
//	        if (ccEmail.length() > 0) {
//	            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//	        }
//	        msg.setSubject(title);
//	        msg.setText(message, "utf-8");
//	        msg.setSentDate(new Date());
//	        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
//	        t.connect("smtp.gmail.com", username, password);
//	        t.sendMessage(msg, msg.getAllRecipients());      
//	        t.close();
//	    }
//	
	
	
	
	
	
	
}