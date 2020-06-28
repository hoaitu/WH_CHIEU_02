package TTHT;

import java.io.IOException;
import java.sql.SQLException;

public class main {
	public static void main(String[] args) throws SQLException, IOException {
		ExtractData ex = new ExtractData();
		DBControl db = new DBControl();
		MyLog myLog = new MyLog();
		DBControl.getConfig(1);
		System.out.println("READ data");
		System.out.println("tới phần gây cấn nhất insert ");
		ex.insertData();
		ex.extractData();
		DowloadFile dowFile = new DowloadFile();
		dowFile.dowloadFile();
		System.out.println("dow thành công ");
		myLog.wiriteLog(DBControl.dirLog);
		System.out.println("OK ");
	}

}
