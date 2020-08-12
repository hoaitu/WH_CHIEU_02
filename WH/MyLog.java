package TTHT1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Pattern;

public class MyLog {
//trạng thái đưa lên log 
	public static String setStatus() {
		if (DowloadFile.dowloadFile().equalsIgnoreCase("SUCCESS")) {
			return "ER";
		}
		return "ERORR";
	}

	public static void wiriteLog(String path) throws SQLException, IOException {
		MyLog log = new MyLog();
		System.out.println("ChilkatExample");
		File file = new File(path);
		System.out.println("File");
		try {
			if (file.isDirectory()) {

				File[] listFile = file.listFiles();
				for (int i = 0; i < listFile.length; i++) {
					int numberOfLine = readLine(listFile[i]);
					log.setupLog(listFile[i].getName(), log.setStatus(), numberOfLine);

				}
			} else if (!file.exists()) {
				System.out.println("No fine path");

			}
			DBConnection.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int readLine(File fileName) throws IOException {
		int result = 0;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		if (fileName.getPath().endsWith(".txt")) {
			String line = br.readLine();

			if (Pattern.matches("^[0-9]*$", line.substring(0, 1))) {
				result++;
			}
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty())
					result++;
			}
			br.close();
		}

		return result;
	}

	private static void setupLog(String name, String status, int numberOfLine) throws SQLException {
		String query = "INSERT INTO control.logs (file_name,    file_status,   staging_load_count,    file_timestamp)"
				+ " VALUES(?, ?, ?,?)";
		PreparedStatement st = DBConnection.con.prepareStatement(query);
		st.setString(1, name);

		st.setString(2, status);
		st.setInt(3, numberOfLine);
		st.setString(4, new Timestamp(System.currentTimeMillis()).toString().substring(0, 19));

		st.execute();

	}

}
