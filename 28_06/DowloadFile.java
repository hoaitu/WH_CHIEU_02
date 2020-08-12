package TTHT;

import java.sql.SQLException;

import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

public class DowloadFile {
	static {
		try {
			System.loadLibrary("chilkat"); // copy file chilkat.dll vao thu muc
											// project
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	public static void dowloadFile() {
		CkSsh ssh = new CkSsh();
		CkGlobal ck = new CkGlobal();
		ck.UnlockBundle("Hello ......................");
		// String hostname = "drive.ecepvn.org";
		int port = 2227;
		boolean success = ssh.Connect(DBControl.host, port);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return;
		}
		ssh.put_IdleTimeoutMs(5000);
		// username ; pas
		success = ssh.AuthenticatePw(DBControl.username, DBControl.password);
		if (success != true) {
			System.out.println(ssh.lastErrorText());
			return;
		}
		CkScp scp = new CkScp();

		success = scp.UseSsh(ssh);
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}
		scp.put_SyncMustMatch("sinhvien*.*");// down tat ca cac file bat dau
												// bang sinhvien
		// nếu ko muốn dow tất cae file :
		// String remotePath = "/volume1/ECEP/song.nguyen/DW_2020/data";
		// String localPath = "F:\\NAM3_KY2\\DT Warhouse\\SCP"; // thu muc muon
		// down file ve
		// String localPath = "F:\\NAM3_KY2\\DT Warhouse\\SCP\\t.xlsx"; //thu
		// muc muon down file ve+ tên muốn đặt
		success = scp.SyncTreeDownload(DBControl.remotePath,
				DBControl.localPath, 2, false);
		// success = scp.DownloadFile(remotePath,localPath); //dow 1 file về
		if (success != true) {
			System.out.println(scp.lastErrorText());
			return;
		}

		System.out.println("SCP dowload file success");

		ssh.Disconnect();
	}

}
