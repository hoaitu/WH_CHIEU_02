package TTHT1;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class main {
	public static void main(String[] args) throws SQLException, IOException, EncryptedDocumentException,
			InvalidFormatException, ClassNotFoundException {
		///// vieers cau lenh jtruyen tham so

		String id_config = args[0];
		System.out.println(id_config);

		////
		ExtractData ex = new ExtractData();
		DBControl db = new DBControl();
		MyLog myLog = new MyLog();
		DBControl.getConfig(id_config);
		System.out.println("READ data");
//		System.out.println("tới phần gây cấn nhất insert ");
//		File file = new File("F:\\NAM3_KY2\\DT Warhouse\\SCP");

//		ex.readValuesXLSX(file);
		System.out.println("........");
//		ex.insertValues(db.dataPath);
//		ex.insertData();
//		ex.extractData();
		DowloadFile dowFile = new DowloadFile();
		System.out.println(dowFile.dowloadFile());
//		System.out.println("tải hết file môn học ; và sinh viên :: ");
//		System.out.println("dow thành công ");
//		myLog.wiriteLog(DBControl.dirLog);
//		System.out.println("Check send mail");
//		dowFile.checkSendMail();
		String id_confi = args[1];
		System.out.println("OK xong cái Sinh viên :::");
		DBControl.getConfig(id_confi);// 2: Môn học
//
		System.out.println(dowFile.dowloadFile());
//		System.out.println("h tới môn học:::");
////		dowFile.dowloadFile_MonHoc();
//		System.out.println("ok xong môn học::::");
//
//		//
//		DBControl.getConfig(3);// 3: Đăng ký
//
//		System.out.println(dowFile.dowloadFile());
//		System.out.println("h tới đăng ký:::");
////		dowFile.dowloadFile_MonHoc();
//		System.out.println("ok xong đăng ký::::");
//
//		//
//		DBControl.getConfig(4);// 4: Lớp Học
//
//		System.out.println(dowFile.dowloadFile());
//		System.out.println("h tới lớp học:::");
////		dowFile.dowloadFile_MonHoc();
//		System.out.println("ok xong lớp học::::");
//
//		System.out.println("h tới môn học:::");
////		dowFile.dowloadFile_MonHoc();
//		System.out.println("ok xong môn học::::");
		/////////////
//		DBControl.getConfig(5);
//		Date_Dim dd = new Date_Dim();
//		dd.sqlCreatTable();
		System.out.println("ok");

	}

}
///
//1, 2005-01-01, 1,1, Saturday,January,2005,2005-Jan,1,1,52,2004-W52,2004-12-26,53,2004-W53,2004-12-27,2005-Q01,1,Non-Holiday,Weekend
