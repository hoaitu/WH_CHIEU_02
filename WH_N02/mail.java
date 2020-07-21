//package TTHT1;
//
//import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//
//
//public class mail {
//	String from;
//	String to;
//	String passfrom;
//	String content;
//	String subject;
//
//	public mail(String from, String to, String passfrom, String content,
//			String subject) {
//		super();
//		this.from = from;
//		this.to = to;
//		this.passfrom = passfrom;
//		this.content = content;
//		this.subject = subject;
//
//	}
//
//	public void sendMail() {
//
//		// Get properties object
//		Properties p = new Properties();
//		p.put("mail.smtp.auth", "true");
//		p.put("mail.smtp.starttls.enable", "true");
//		p.put("mail.smtp.host", "smtp.gmail.com");
//		p.put("mail.smtp.port", 587);
//
//		// get Session
//		Session s = Session.getInstance(p, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(from, passfrom);
//			}
//		});
//
//		// compose message
//		try {
//			Message message = new MimeMessage(s);
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse(to));
//			message.setSubject(subject);
//			message.setText(content);
//
//			// send message-
//			Transport.send(message);
//
//			System.out.println("Message sent successfully");
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public static void main(String[] args) {
//		mail s = new mail( "hoaitugl@gmail.com","tranghoang13199@gmail.com",
//				"hoaitu79", "Welcom to ABC", "Testing");
//		s.sendMail();
//	}
//}
// public class MailConfig {
//
// public static final String HOST_NAME = "smtp.gmail.com";
//
// public static final int SSL_PORT = 465; // Port for SSL
//
// public static final int TSL_PORT = 587; // Port for TLS/STARTTLS
//
// public static final String APP_EMAIL = "hoaitugl@gmail.com"; // your
// // email
//
// public static final String APP_PASSWORD = "hoaitu79"; // your
// // password
//
// public static final String RECEIVE_EMAIL = "tranghoang13199@gmail.com";
// }
//
// public static void main(String[] args) {
//
// // Get properties object
// Properties props = new Properties();
// props.put("mail.smtp.auth", "true");
// props.put("mail.smtp.host", MailConfig.HOST_NAME);
// props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
// props.put("mail.smtp.socketFactory.class",
// "javax.net.ssl.SSLSocketFactory");
// props.put("mail.smtp.port", MailConfig.SSL_PORT);
//
// // get Session
// Session session = Session.getDefaultInstance(props,
// new javax.mail.Authenticator() {
// protected PasswordAuthentication getPasswordAuthentication() {
// return new PasswordAuthentication(MailConfig.APP_EMAIL,
// MailConfig.APP_PASSWORD);
// }
// });
//
// // compose message
// try {
// MimeMessage message = new MimeMessage(session);
// message.setRecipients(Message.RecipientType.TO,
// InternetAddress.parse(MailConfig.RECEIVE_EMAIL));
// message.setSubject("Testing Subject");
// message.setText("Welcome to gpcoder.com");
//
// // send message
// Transport.send(message);
//
// System.out.println("Message sent successfully");
// } catch (MessagingException e) {
// throw new RuntimeException(e);
// }
// }
// }
