package TTHT1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;
import com.mysql.jdbc.PreparedStatement;

import java.util.Properties;
//
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DowloadFile {
	// Load native code
	static {

		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public static String dowloadFile(String host, int port, String username, String password, String put_SyncMustMatch,
			String remotePath, String localPath) {
		CkSsh ssh = new CkSsh();// server
		CkGlobal ck = new CkGlobal();

		ck.UnlockBundle("Hello ......................");
		// 1. Connect source
		// String hostname = "drive.ecepvn.org";

//		int port = 2227;
		// Connect to an SSH server and establish the SSH:
		boolean success = ssh.Connect(host, port);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			ssh.Disconnect();
			return "FAIL";

		}

		// 2. authentication: user && pass
		// Authenticate with the SSH server: demonstrates SSH password
		success = ssh.AuthenticatePw(username, password);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			ssh.Disconnect();
			return "FAIL";
		}
		CkScp scp = new CkScp();
		// 3. open port ssh to go
		success = scp.UseSsh(ssh);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			ssh.Disconnect();
			return "FAIL";
		}
//		scp.put_SyncMustMatch("sinhvien*.*");// down tat ca cac file bat dau
		// bang sinhvien; môn học .....
		scp.put_SyncMustMatch(put_SyncMustMatch);

		success = scp.SyncTreeDownload(remotePath, localPath, 2, false);

		if (success != true) {
			System.out.println(scp.lastErrorText());
			ssh.Disconnect();
			return "FAIL";
		}

		System.out.println("SCP dowload file success");
		ssh.Disconnect();

		return "SUCCESS";
	}

	///
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
			return false;
		}
		return false;
	}

	///

	public static Connection createConnection() throws SQLException {
		String USER = "root";
		String PASS = "";
		String hostName = "localhost";
		String driver = "jdbc:mysql:";
		String addressConfig = "localhost:3306/control";
		String source = "localhost:3306/dbstaging";
		String dbNameConfig = "myconfig";
		String dbNameLog = "logs";
		Connection con = null;
		Connection connectionSource;
		System.out.println("Connecting database....");

		try {
			// Kết nối thành công
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(driver + "//" + addressConfig, USER, PASS);
			System.out.println("Ok rồi nha !!!!!");
			System.out.println("----------------------------------------------------------------");
		} catch (ClassNotFoundException e) {
			// Không thành công
			System.out.println("Ko k.nối được nha!!!!!!!!!!");
			System.out.println("----------------------------------------------------------------");
			con.close();

		}

		return con;
	}

	public static void saveDataToLocal(String id) throws SQLException {
		MyLog log = new MyLog();

		// 1.Kết nối DB control
		Connection conn = createConnection();
		PreparedStatement pre = null;
		String sql = "SELECT * FROM control.myconfig where id=?;";
		pre = (PreparedStatement) conn.prepareStatement(sql);
		// 2. Tìm các file trong control.myconfig có id nhập từ bàn phím
		pre.setString(1, id);
		// 3. Nhận result set chứa các record thỏa yêu cầu truy xuất
		ResultSet tmp = pre.executeQuery();
		// 4. Duyệt record trong result set

		if (tmp.next()) {

			String host = tmp.getString("hostAcess"); // drive.ecepvn.org
			String username = tmp.getString("userNameAcess"); // guest_access
			String password = tmp.getString("passAcess"); // 123456
			String remotePath = tmp.getString("remotePathAcess"); /// volume1/ECEP/song.nguyen/DW_2020/data
			String localPath = tmp.getString("localPathAcesss"); /// F:\\NAM3_KY2\\DT Warhouse\\MONHOC....
			int port = Integer.parseInt(tmp.getString("port"));// 2227
			String put_SyncMustMatch = tmp.getString("put_SyncMustMatch"); // sinhvien*.xlsx ; Monhoc*.xlsx....
			// 5. Gọi phương thức DowloadFile.dowloadFile() để tải files về local.
			String download = DowloadFile.dowloadFile(host, port, username, password, put_SyncMustMatch, remotePath,
					localPath);
//
			// Báo thành công
			if (download.equalsIgnoreCase("SUCCESS")) {

				// 6. Show màn hình : tải thành công
				System.out.println("Dowload success file name: ");
				// 7. Gửi mail thông báo thành công
				sendMail("datawarehouse1999@gmail.com", "hoaitugl@gmail.com", "datawarehouse2020",
						"Welcom Tú mess notify: Dowload file from " + remotePath + "  into " + localPath
								+ "Dowload file success at: " + new Timestamp(System.currentTimeMillis()),
						"Dowload files success  ");
				// 8. Gọi phương thức : wiriteLog()
				log.wiriteLog(id);

			} else if (download.equalsIgnoreCase("FAIL")) {

//				11. Show màn hình : tải không thành công
				System.out.println("Dowload không thành công  ");
				// send mail
				sendMail("datawarehouse1999@gmail.com", "hoaitugl@gmail.com", "datawarehouse2020",
						"Welcom Tú mess notify: Dowload file from " + remotePath + "  into " + localPath
								+ " Dowload file is NOT success at: " + new Timestamp(System.currentTimeMillis()),
						"Dowload files NOT success  ");
				conn.close();

			}
		}
		// đóng kết nối:
		tmp.close();
		pre.close();
		conn.close();
	}

	public static void main(String[] args) throws SQLException, IOException {
		MyLog myLog = new MyLog();
		DowloadFile dowFile = new DowloadFile();

		for (int i = 0; i < args.length; i++) {

			String id_config_SV = args[i];
			saveDataToLocal(id_config_SV); // 1: sinh viên //2: mon học //3: dky; 4: lop
			System.out.println("ok");
		}

	}

}
