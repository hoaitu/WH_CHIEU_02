package TTHT;

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

	public static void dowloadFile() {
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
			return;
		}
		ssh.put_IdleTimeoutMs(5000);
		// 2. authentication: user && pass
		// Authenticate with the SSH server: demonstrates SSH password
		success = ssh.AuthenticatePw(DBControl.username, DBControl.password);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return;
		}
		CkScp scp = new CkScp();
		// 3. open port ssh to go
		success = scp.UseSsh(ssh);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}
		scp.put_SyncMustMatch("sinhvien*.*");// down tat ca cac file bat dau
												// bang sinhvien
		// 4. dowload file in local
		// 5. 2: file was already exits not dow continute
		success = scp.SyncTreeDownload(DBControl.remotePath,
				DBControl.localPath, 2, false);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}
		System.out.println("SCP dowload file success");

		ssh.Disconnect();
	}

}
