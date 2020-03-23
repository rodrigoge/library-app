package br.com.library.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import br.com.library.models.User;

public class JavaMail {
	
	private User user = new User();
	String text;
	
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
			MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(text, "utf-8", "html");
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Recuperação de senha | Library App");
			text = "<p>Olá, se você recebeu este email é porque foi solicitada uma alteração de sua senha no cadastro do <b>Library App</b>.</p> "
					+ "<p>Para alterar sua senha clique <a href='http://localhost:8080/library-app/Update.xhtml'>aqui.</a></p>"
					+ "<p>Caso não tenha solicitado nenhuma alteração, desconsidere este email.</p>";
			message.setContent(text, "text/html");

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