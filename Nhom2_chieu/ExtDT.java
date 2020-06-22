package TTHT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;

public class ExtDT {
	static final String NUMBER_REGEX = "^[0-9]+$";
	private Connection connectionDB1;// db 1
										// //==jdbc:mysql://localhost:3306/dwh_1
	private static String USER = "root";
	private static String PASS = "";

	private static String hostName = "localhost";
	private static String driver = "jdbc:mysql:";
	private static String addressConfig = "localhost:3306/control";
	private static String source = "localhost:3306/dwh_1";
	private static String dbName = "myconfig";
	private static Connection con;
	private static Connection connectionSource;

	String tableName;
	String fileType;
	int numOfcol;
	int numOfdata; // có 20 sv ==> = 20
	ArrayList<String> columnsList = new ArrayList<String>();
	int numberColumns;
	static String dataPath;
	String delimiter;

	// private static String connectionURL = "jdbc:mysql://" + hostName +
	public void createConnection() throws SQLException {
		System.out.println("Connecting database....");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(driver + "//" + addressConfig,
					USER, PASS);
			connectionSource = DriverManager.getConnection(driver + "//" + source,
					USER, PASS);
			System.out.println("Ok rồi nha !!!!!");
			System.out.println("----------------------------------------------------------------");
		} catch (ClassNotFoundException e) {
			System.out.println("KO k.noi đk nha!!!!!!!!!!");
			System.out.println("----------------------------------------------------------------");
		}
	}

	public void getConfig(int id) throws SQLException {
		PreparedStatement pre = (PreparedStatement) con
				.prepareStatement("SELECT * FROM control.myconfig where id=?;");
		pre.setInt(1, id);
		ResultSet tmp = pre.executeQuery();
		tmp.next();
		tableName = tmp.getString("nameTable");
		numOfcol = Integer.parseInt(tmp.getString("numOfCol"));
		String listofcol = tmp.getString("listField");
		dataPath = tmp.getString("dataPath");
		// URL db đưa lên:: jdbc:mysql://localhost:3306/dwh_1
		// dấu phân cách
		delimiter = tmp.getString("delimiter");
		StringTokenizer tokens = new StringTokenizer(listofcol, delimiter);
		while (tokens.hasMoreTokens()) {
			columnsList.add(tokens.nextToken());
		}
		System.out.println("Get config: complete!!!!");
	}

	// truyền dấu phân cách , đường dẫn file ở đâu
	public void load(String delimiter, String pathFile)
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
		String[] fields = lineText.split(delimiter);
		System.out.println(fields.length);
		System.out.println(lineText);
	}

	// đọc dữ liệu
	public void readData() throws IOException, EncryptedDocumentException,
			InvalidFormatException {
		System.out.println("Connect DB Successfully Read file:)");
		File f = new File(dataPath);
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

	// |
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

	//
	public String sqlCreatTable() {
		String preSql = "CREATE TABLE " + tableName + " (";
		preSql += columnsList.get(0) + " INT PRIMARY KEY NOT NULL,";
		for (int i = 1; i < numOfcol; i++) {
			preSql += columnsList.get(i) + " VARCHAR(100),";
		}
		preSql = preSql.substring(0, preSql.length() - 1) + ");";
		System.out.println(preSql);
		// idSV,name,DateOfBirth,gender,phone
		System.out.println("creat thành công");
		return preSql;
	}

	public String insertData() throws SQLException {
		String preSql = "INSERT INTO " + tableName + "(";
		for (int i = 0; i < numOfcol; i++) {
			preSql += columnsList.get(i) + ",";
		}
		preSql = preSql.substring(0, preSql.length() - 1) + ") VALUES   "
				+ readValuesTXT(dataPath, delimiter) + ";";

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
		boolean tableStatus = false;
		boolean readDataStatus = false;

		try {
			System.out.println("Creating table " + tableName + ".......");
			PreparedStatement state = connectionSource.prepareStatement(sqlCreateTb);
			System.out.println(state);
			state.execute();
			tableStatus = true;
			System.out.println("Create Table: Complete!!!");
			System.out
					.println("----------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println("Can't create table " + tableName);
			System.out
					.println("----------------------------------------------------------------");
		}
		if (tableStatus) {
			try {
				System.out.println("Insert  table " + tableName + ".......");
				PreparedStatement stateInsert = connectionSource
						.prepareStatement(sqlInsertData);
				System.out.println(stateInsert);
				stateInsert.execute();
				tableStatus = true;
				System.out.println("Insert Table: Complete!!!");
				System.out
						.println("----------------------------------------------------------------");
			} catch (Exception e) {
				System.out.println("Can't Insert table " + tableName);
				System.out
						.println("----------------------------------------------------------------");
			}

		}

	}

	public static void main(String[] args) throws SQLException, IOException,
			EncryptedDocumentException, InvalidFormatException {
		ExtDT ex = new ExtDT();
		ex.createConnection();
		ex.getConfig(1);
		System.out.println("READ data");
		System.out.println(ex.sqlCreatTable());// đọc file csv : đưa vào :))) cả
												// các trường luôn :)))
		System.out.println("test VTT");
		System.out.println("exit test VTT");
		System.out.println("tới phần gây cấn nhất insert ");
		ex.insertData();
		ex.extractData();
		System.out.println("OK ");

	}
}
//dow fill thoi gian : khoang 10 fill; làm state update thời gian ; đếm bao nhiu row ,có bị rớt ko 
//dolw file về , inser vào file log vào luôn ER ;
//insert dòng log trạng thái ER trên local log db
//tự động insert 1 dòng trong tb log : trạng thái đang chờ log , láy dòng đó ktra , bỏ vào db stagging log qua db w.house 
//đuôi mở rộng : config ; insert 1 dòng trong log ; inser kiểm tra trong file log đã có chua : if thah công thì ko tải nữa 
//bc1: kiemr tra trong log dl import trong he thong chua : thanh cong, transfoem ... chua , chỉ import cái mới thâu
//mọi lỗi thông báo qua mail :)))))))))))) khó 
//ông số 3: kiểm tra từng dòng db có svien do chua , trùng mã số ko ; so sánh ; lưu history ; 
//o2: lấy data từ cònig
//ong 3: láy data từ tble này sang tble khác
