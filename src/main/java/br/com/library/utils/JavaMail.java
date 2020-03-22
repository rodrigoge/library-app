package br.com.library.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.library.models.User;

public class JavaMail {
	
	private User user = new User();
	
	public void sendEmail(String toEmail) {
		
		// Parameters for connection Gmail
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//parameters of who is sending
				return new PasswordAuthentication("digitragencia@gmail.com", "d1g1t@L12");
			}
		});
		
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@admin.com.br"));

			//receptors for message
			InternetAddress[] toUser = InternetAddress.parse(toEmail);
			
			//content email
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Recuperação de senha | Library App");
			message.setText("Seu nova senha é 12345");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}