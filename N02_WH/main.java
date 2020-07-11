package TTHT1;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class main {
	public static void main(String[] args) throws SQLException, IOException, EncryptedDocumentException,
			InvalidFormatException, ClassNotFoundException {
		ExtractData ex = new ExtractData();
		DBControl db = new DBControl();
		MyLog myLog = new MyLog();
		DBControl.getConfig(1);
		System.out.println("READ data");
		System.out.println("tới phần gây cấn nhất insert ");
		ex.readValuesXLSX(db.dataPath);
		System.out.println("........");
		ex.insertValues(db.dataPath);
//		ex.insertData();
		ex.extractData();
//		DowloadFile dowFile = new DowloadFile();
//		System.out.println(dowFile.dowloadFile());
//		System.out.println("dow thành công ");
//		myLog.wiriteLog(DBControl.dirLog);
//		System.out.println("Check send mail");
//		dowFile.checkSendMail();
		System.out.println("OK ");
	}

}
