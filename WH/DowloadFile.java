package TTHT1;

import java.sql.SQLException;

import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

public class DowloadFile {
	// Load native code: thêm chill.dll
	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public static String dowloadFile() {
		CkSsh ssh = new CkSsh();
		CkGlobal ck = new CkGlobal();
		ck.UnlockBundle("Hello ......................");
		// 1. Connect source
		// String hostname = "drive.ecepvn.org";
		int port = 2227;
		// Connect to an SSH server and establish the SSH:
		boolean success = ssh.Connect(DBControl.host, port);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return "FAIL";
		}
		ssh.put_IdleTimeoutMs(5000);
		// 2. authentication: user && pass
		// Authenticate with the SSH server: demonstrates SSH password
		success = ssh.AuthenticatePw(DBControl.username, DBControl.password);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return "FAIL";
		}
		CkScp scp = new CkScp();
		// 3. open port ssh to go
		success = scp.UseSsh(ssh);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return "FAIL";
		}
		scp.put_SyncMustMatch("sinhvien*.*");// down tat ca cac file bat dau
												// bang sinhvien
		// 4. dowload file in local
		// 5. 2: file was already exits not dow continute
		success = scp.SyncTreeDownload(DBControl.remotePath, DBControl.localPath, 2, false);
		//
//		DBControl.download = DowloadFile.dowloadFile();

		if (success != true) {
			System.out.println(scp.lastErrorText());
			return "FAIL";
		}

		System.out.println("SCP dowload file success");
		ssh.Disconnect();

		return "SUCCESS";
	}

	public static void checkSendMail() {
		DBControl.download = DowloadFile.dowloadFile();

		if (DBControl.download.equalsIgnoreCase("SUCCESS")) {

			// . Thông báo thành công ra màn hình
			System.out.println("Dowload success file name: ");
			// send mail
			SendMail.sendMail("datawarehouse1999@gmail.com", "hoaitugl@gmail.com", "datawarehouse2020", "Welcom to ABC",
					"THANH CONG 12345");

		} else if (DBControl.download.equalsIgnoreCase("FAIL")) {

			// . In dòng thông báo file Không tồn tại
			System.out.println("File không tồn tại, idFile: ");
			// .Gửi mail thông báo lỗi download file
//				System.out.println("Dowload không success file name: " );
			// send mail
			SendMail.sendMail("datawarehouse1999@gmail.com", "hoaitugl@gmail.com", "datawarehouse2020", "Welcom to ABC",
					"ko THANH CONG 12345");

		}
	}
}
