//package TTHT1;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.NoSuchElementException;
//import java.util.StringTokenizer;
//import java.util.regex.Pattern;
//
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class ExtractData {
//	static final String NUMBER_REGEX = "^[0-9]+$";
//	static int countofline;
//	static String fileName;
//
//	private static String readLines(String value, String delim) {
//		String values = "";
//		// tạo ra một lớp StringTokenizer dựa trên chuỗi chỉ định và dấu phân cách.
//		StringTokenizer stoken = new StringTokenizer(value, delim);
//
//		// Trả về tổng số lượng của các token > 0
//		if (stoken.countTokens() > 0) {
//			// Trả về token tiếp theo khi duyệt đối tượng StringTokenizer.
//			stoken.nextToken();
//		}
//		int countToken = stoken.countTokens();
//		String lines = "(";
//		// duyêt các token trong chuỗi
//		for (int j = 0; j < countToken; j++) {
//			// Trả về token tiếp theo khi duyệt StringTokenizer.
//			String token = stoken.nextToken();
//			if (Pattern.matches(NUMBER_REGEX, token)) {
//				// nếu duyệt thấy token kế tiếp là số thì ko cần thêm dấu nháy đơn
//				lines += (j == countToken - 1) ? token.trim() + ")," : token.trim() + ",";
//				// values:(202452,'Sinh học 1',2,'KHOA HỌC','',''),(212339,'Kỹ năng giao
//				// tiếp',2,'MÔI TRƯỜNG VÀ TN','','')
//			} else {
//				lines += (j == countToken - 1) ? "'" + token.trim() + "')," : "'" + token.trim() + "',";
//			}
//			values += lines;
//			lines = "";
//		}
//		System.out.println("..................");
//		System.out.println(values);
//		return values;
//	}
//	////
//
//	public static String readValuesXLSX(File s_file) {
//		String values = "";
//		String value = "";
//		String delimeter = DBControl.delimeter; // đấu "|"
//		System.out.println("delimeter" + delimeter);
//		try {
//			FileInputStream fileIn = new FileInputStream(s_file);
//			XSSFWorkbook workBooks = new XSSFWorkbook(fileIn);
//			XSSFSheet sheet = workBooks.getSheetAt(0);
//			Iterator<Row> rows = sheet.iterator();
//			rows.next();
//			int countRow = 0;
//			while (rows.hasNext()) {
//				Row row = rows.next();
//				Iterator<Cell> cells = row.cellIterator();
//				int count = 0;
//				while (count < DBControl.numOfcol) {
//					cells.hasNext();
//					try {
//						Cell cell = cells.next();
//						CellType cellType = cell.getCellType();
//						switch (cellType) {
//						case NUMERIC:
//							if (DateUtil.isCellDateFormatted(cell)) {
//								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//								value += dateFormat.format(cell.getDateCellValue()) + "|";
//							} else {
//								value += (long) cell.getNumericCellValue() + delimeter;
//							}
//							break;
//						case STRING:
//							value += cell.getStringCellValue() + delimeter;
//							break;
//
//						case BLANK:
//							value += " " + delimeter;
//							break;
//						default:
//							if (cell.getStringCellValue().toString().equalsIgnoreCase("")) {
//								value += " " + delimeter;
//							}
//							break;
//						}
//					} catch (Exception e) {
//						value += " " + delimeter;
//					}
//
//					count++;
//				}
//				if (value.length() > 0) {
//					values += readLines(value.substring(0, value.length() - 1), delimeter);
//				}
//
////				System.out.println("values:" + values);
//				value = "";
//				System.out.println("values:" + values);
//
////				if (count != numOfcol) {
////					break;
////				}
//			}
//			workBooks.close();
//			fileIn.close();
//			return values.substring(0, values.length() - 1);
//		} catch (IOException e) {
//			return null;
//		}
//	}
//
//	//// XLSX
//	public static String insertValues(String path) throws ClassNotFoundException {
//
//		String sql = null;
//		File file = new File(path);
//		System.out.println(file);
//		if (file.isDirectory())
//
//		{
//
//			File[] listFile = file.listFiles();
//			for (int i = 0; i < listFile.length; i++) {
//
//				///
//				String values = readValuesXLSX(listFile[i]);
//				sql = "INSERT INTO " + DBControl.nameConfig + "." + DBControl.tableName + "(" + DBControl.listofcol
//						+ ") VALUES " + values;
//				System.out.println(sql);
//				try {
//
//					PreparedStatement pst = DBConnection.createConnection().prepareStatement(sql);
//					System.out.println("pst" + pst);
//					pst.executeUpdate();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				System.out.println(sql);
//				System.out.println("TRRRRRRRRR");
//			}
//		}
//
//		System.out.println("sql" + sql);
//		return sql;
//	}
//
//	public static void changeLogs(String id) throws SQLException, IOException, ClassNotFoundException {
//		Connection con;
//		PreparedStatement pre;
//		String succeed = "";
//		String fail = "";
//
//		PreparedStatement getAll = (PreparedStatement) DBConnection.createConnection()
//				.prepareStatement("SELECT * FROM control.logs where id_config=? and file_status = ?;");
//		getAll.setString(1, id);
//		getAll.setString(2, "ER");
//		System.out.println("ok");
//		ResultSet result = getAll.executeQuery();
//
//		result.next();
//		String file_name = result.getString("file_name");
//		String status = result.getString("file_status");
//		if (status.equals("ER")) {
//			System.out.println("++++++++++++++++");
//			System.out.println(result.getString("file_name"));
//			System.out.println("++++++++++++++++");
//			fileName = result.getString("file_name");
//			System.out.println(result.getString("file_name"));
//			// local == datapath : thu muc chua ; filename : tên file trên logs
//
////				 "sinhvien_chieu_nhom2.xlsx"
//
//			File file = new File(DBControl.dataPath + "\\" + fileName);
//			System.out.println(file);
//			System.out.println("xong file");
//			////////////////////////////////////////////
//			if (!file.exists()) {
//				System.out.println("File không tồn tại");
//			} else {
//				/// doc file
//				ExtractData t = new ExtractData();
//				String readFile = t.readValuesXLSX(file);
//				//
//				System.out.println("Doc file thanh cong");
//				String values = "";
//				System.out.println("========");
//
//				if (readFile != null) {
//					System.out.println("Chuan bi insert du lieu: " + file);
//					// connect to stagin
//
//					// end
////						if (t.writeDataToBD(DBControl.target_table, DBControl.listofcol, readFile)) {
//					ExtractData ex = new ExtractData();
//					if (ExtractData.insertValues(DBControl.dataPath) != null) {
//						MyLog mL = new MyLog();
////						countofline = mL.readLine(file); // đếm số dòng
//						if (countofline > 0) {
//							/// Kiem tra so dong duoc load vao staging
//							String sqlupdate = "UPDATE control.logs SET file_status = ?,file_timestamp =? WHERE id = ?";
//
//							try {
//								pre = DBConnection.createConnection().prepareStatement(sqlupdate);
//								pre.setString(1, "TR");
////								pre.setInt(2, countofline);
//								pre.setString(2, new Timestamp(System.currentTimeMillis()).toString().substring(0, 19));
////								pre.setInt(3, result.getInt("id"));
//								succeed += fileName + " " + "TR" + " "
//										+ new Timestamp(System.currentTimeMillis()).toString().substring(0, 19);
//								pre.execute();
//								System.out.println("Update success.......");
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//						}
//					} else {
//						String sqlupdate = "UPDATE control.logs SET file_status = ?, file_timestamp =? WHERE id = ?";
//
//						try {
//							pre = DBConnection.createConnection().prepareStatement(sqlupdate);
//							pre.setString(1, "Fail");
////							pre.setInt(2, countofline);
//							pre.setString(2, new Timestamp(System.currentTimeMillis()).toString().substring(0, 19));
//							pre.setInt(3, result.getInt("id"));
//							fail += fileName + " " + "ERROR" + " "
//									+ new Timestamp(System.currentTimeMillis()).toString().substring(0, 19);
//							pre.execute();
//							System.out.println("Update success.......");
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//
//					}
//				}
//			}
//		}
//
//	}
//
//	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException,
//			ClassNotFoundException, SQLException {
//
//		DBControl db = new DBControl();
//		MyLog myLog = new MyLog();
//		ExtractData t = new ExtractData();
//		DowloadFile dowFile = new DowloadFile();
//		String path = DBControl.dataPath;
//		for (int i = 0; i < args.length; i++) {
//
//			String id_config_SV = args[i];
//			System.out.println("id của config : " + id_config_SV);
//			DBControl.getConfig(id_config_SV);// 1: sinh viên //2: mon học //3: dky; 4: lop
//			changeLogs(id_config_SV);
////			insertValues(id_config_SV);
//
//			System.out.println("ok");
//		}
//
//	}
//}