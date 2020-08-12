package TTHT1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExtractData {
	static final String NUMBER_REGEX = "^[0-9]+$";

	// truyền dấu phân cách , đường dẫn file ở đâu
	public void load(String delimeter, String pathFile)
			throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Connect DB Successfully :)");
		File f = new File(pathFile);
		if (!f.exists()) {
			System.out.println("File not exist!");
			return;
		}
		BufferedReader lineReader = new BufferedReader(new FileReader(f));
		String lineText = null;
		int count = 0;
		String sql;
		lineText = lineReader.readLine();
		String[] fields = lineText.split(delimeter);
		System.out.println(fields.length);
		System.out.println(lineText);
	}

	// đọc dữ liệu
	public void readData() throws IOException, EncryptedDocumentException,
			InvalidFormatException {
		System.out.println("Connect DB Successfully Read file:)");
		File f = new File(DBControl.dataPath);
		System.out.println(f);
		if (!f.exists()) {
			System.out.println("File not exist!");
			return;
		}
		// File file = new File(f);//lấy đường dẫn file từ db config xuống
		BufferedReader lineReader = new BufferedReader(new FileReader(f));
		String lineText = null;
		int count = 0;
		String sql;
		lineText = lineReader.readLine();
		System.out.println(lineText);
		FileInputStream fis = new FileInputStream(f);
	}

	private String readLines(String value, String delim) {
		String values = "";
		StringTokenizer stoken = new StringTokenizer(value, delim);
		if (stoken.countTokens() > 0) {
		}
		int countToken = stoken.countTokens();
		String lines = "(";
		for (int j = 0; j < countToken; j++) {
			String token = stoken.nextToken();
			if (Pattern.matches(NUMBER_REGEX, token)) {
				lines += (j == countToken - 1) ? token.trim() + ")," : token
						.trim() + ",";
			} else {
				lines += (j == countToken - 1) ? "'" + token.trim() + "'),"
						: "'" + token.trim() + "',";
			}
			values += lines;
			lines = "";
		}
		return values;
	}

	public String readValuesTXT(String s_file, String delim) {
		String values = "";
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(s_file)));
			String line;
			while ((line = bReader.readLine()) != null) {
				values += readLines(line, delim);
			}
			bReader.close();
			return values.substring(0, values.length() - 1);
		} catch (NoSuchElementException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String sqlCreatTable() {
		String preSql = "CREATE TABLE " + DBControl.tableName + " (";
		preSql += DBControl.columnsList.get(0) + " INT PRIMARY KEY NOT NULL,";
		for (int i = 1; i < DBControl.numOfcol; i++) {
			preSql += DBControl.columnsList.get(i) + " VARCHAR(100),";
		}
		preSql = preSql.substring(0, preSql.length() - 1) + ");";
		System.out.println(preSql);
		// idSV,name,DateOfBirth,gender,phone
		System.out.println("creat thành công");
		return preSql;
	}

	public String insertData() throws SQLException {
		String preSql = "INSERT INTO " + DBControl.tableName + "(";
		for (int i = 0; i < DBControl.numOfcol; i++) {
			preSql += DBControl.columnsList.get(i) + ",";
		}
		preSql = preSql.substring(0, preSql.length() - 1) + ") VALUES   "
				+ readValuesTXT(DBControl.dataPath, DBControl.delimeter) + ";";

		System.out.println("............Rd ...");
		System.out.println(preSql);
		System.out.println("INSERT  thành công");
		return preSql;

	}

	public void extractData() throws SQLException {
		String sqlCreateTb = sqlCreatTable();

		String sqlInsertData = insertData();
		System.out.println("String sqlInsertData = insertData ();");
		System.out.println(sqlInsertData);
		// create table
		boolean tableStatus = true;
		boolean readDataStatus = false;

		try {
			// kết nối vs thằng URL
			System.out.println("chờ kết nối URL");
			DBConnection.connectionSource = (Connection) DriverManager
					.getConnection(DBConnection.driver + "//"
							+ DBControl.source, DBConnection.USER,
							DBConnection.PASS);
			System.out.println("kết nối URL thành công ");

			System.out.println("Creating table " + DBControl.tableName
					+ ".......");
			PreparedStatement state = DBConnection.createConnection()
					.prepareStatement(sqlCreateTb);
			System.out.println(state);
			state.execute();
			tableStatus = true;
			System.out.println("Create Table: Complete!!!");
			System.out
					.println("----------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println("Can't create table " + DBControl.tableName);
			System.out
					.println("----------------------------------------------------------------");
		}
		if (tableStatus) {
			try {
				System.out.println("Insert  table " + DBControl.tableName
						+ ".......");
				PreparedStatement stateInsert = DBConnection.createConnection()
						.prepareStatement(sqlInsertData);
				System.out.println(stateInsert);
				stateInsert.execute();
				tableStatus = true;
				System.out.println("Insert Table: Complete!!!");
				System.out
						.println("------------------------------//----------------------------------");
			} catch (Exception e) {
				System.out.println("Can't Insert table " + DBControl.tableName);
				System.out
						.println("-------------------------------//---------------------------------");
			}
		}
	}
}
