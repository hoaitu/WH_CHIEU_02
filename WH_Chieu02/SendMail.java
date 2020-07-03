package TTHT1;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public static boolean sendMail(final String from, String to, final String passfrom, String content,
			String subject) {

		// Get properties object
		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", 465);// SSL ;
		p.put("mail.smtp.EnableSSL.enable", "true");
//
		p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.setProperty("mail.smtp.socketFactory.fallback", "false");
		p.setProperty("mail.smtp.socketFactory.port", "465");

		// get Session
//		Session.getInstance
		Session s = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passfrom);
			}
		});

		// compose message
		try {
			MimeMessage message = new MimeMessage(s);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(content);

			// send message
			Transport.send(message);

			System.out.println("Message sent successfully");
		} catch (MessagingException e) {
			System.out.println("ERRRRRRRRRRor");
//			throw new RuntimeException(e);
			return false;
		}
		return false;
	}

//	public static void main(String[] args) {
////		SendMail s = new SendMail("datawarehouse1999@gmail.com", "hoaitugl@gmail.com", "datawarehouse2020",
////				"Welcom to ABC", "Testing 12345");
//		SendMail s = new SendMail();
//		s.sendMail("datawarehouse1999@gmail.com", "hoaitugl@gmail.com", "datawarehouse2020", "Welcom to ABC",
//				"Testing 12345");
//		System.out.println("OKKKKkk");
//	}
}
