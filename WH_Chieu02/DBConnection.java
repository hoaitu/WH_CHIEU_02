package TTHT1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static String USER = "root";
	static String PASS = "";
	static String hostName = "localhost";
	static String driver = "jdbc:mysql:";
	static String addressConfig = "localhost:3306/control";
	static String source = "localhost:3306/dwh_1";
	static String dbNameConfig = "myconfig";
	static String dbNameLog = "logs";
	static Connection con;
	static Connection connectionSource;

	public static Connection createConnection() throws SQLException {
		System.out.println("Connecting database....");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(driver + "//" + addressConfig, USER, PASS);
			System.out.println("Ok rồi nha !!!!!");
			System.out.println("----------------------------------------------------------------");
		} catch (ClassNotFoundException e) {
			System.out.println("KO k.noi đk nha!!!!!!!!!!");
			System.out.println("----------------------------------------------------------------");
		}
		return con;
	}

	public static void main(String[] args) throws SQLException {
		DBConnection db = new DBConnection();
		db.createConnection();
		System.out.println("Thanh công");
	}

}
