package TTHT1;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class TEST_DT {
	public static void main(String[] args) throws SQLException, IOException, EncryptedDocumentException,
			InvalidFormatException, ClassNotFoundException {
		///// viet cau lenh truyen tham so
		DBControl db = new DBControl();
		MyLog myLog = new MyLog();
		ExtractData t = new ExtractData();
		DowloadFile dowFile = new DowloadFile();
		for (int i = 0; i < args.length; i++) {

			String id_config_SV = args[i];
			System.out.println("SINH VIEN: " + id_config_SV);
			DBControl.getConfig(id_config_SV);// 1: sinh viên //2: mon học //3: dky; 4: lop
			System.out.println(dowFile.dowloadFile());
			myLog.wiriteLog(DBControl.dirLog);
			t.changeLogs(id_config_SV);

			System.out.println("ok");
		}

	}

}
