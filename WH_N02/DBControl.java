package TTHT1;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DBControl {

	static String tableName;
	String fileType;
	static int numOfcol;
	int numOfdata; // có 20 sv ==> = 20
	static ArrayList<String> columnsList = new ArrayList<String>();
	int numberColumns;
	static String dataPath;
	static String delimeter;
	static String source;
	// lấy cofig để tải file trên nó
	static String host;
	static int port;
	static String username;
	static String password;
	static String remotePath;
	static String localPath;
	static String dirLog;
	static String download;
	static String listofcol;
	static String nameConfig;
	static String put_SyncMustMatch; // dùng cho bên dowload về : tên file

	public static void getConfig(String id) throws SQLException {
		PreparedStatement pre = (PreparedStatement) DBConnection.createConnection()
				.prepareStatement("SELECT * FROM control.myconfig where id=?;");
		pre.setString(1, id);
		ResultSet tmp = pre.executeQuery();
		tmp.next();
		tableName = tmp.getString("nameTable");
		System.out.println(tableName);
		numOfcol = Integer.parseInt(tmp.getString("numOfCol"));
		listofcol = tmp.getString("listField");

		dataPath = tmp.getString("dataPath");
		// URL db đưa lên:: jdbc:mysql://localhost:3306/dbstaging
		delimeter = tmp.getString("delimiter");
		StringTokenizer tokens = new StringTokenizer(listofcol, delimeter);
		System.out.println("......Source URL.");
		source = tmp.getString("SourceURL");
		System.out.println(source);
		//
		nameConfig = tmp.getString("nameConfig");
		///
		put_SyncMustMatch = tmp.getString("put_SyncMustMatch");
		System.out.println("ĐAy là put_SyncMustMatch::" + put_SyncMustMatch);
		// using step1 dow file
		host = tmp.getString("hostAcess");
		username = tmp.getString("userNameAcess");
		password = tmp.getString("passAcess");
		remotePath = tmp.getString("remotePathAcess");
		localPath = tmp.getString("localPathAcesss");
		port = Integer.parseInt(tmp.getString("port"));
		// dirLog
		dirLog = tmp.getString("dirLog");

//		while (tokens.hasMoreTokens()) {
//			columnsList.add(tokens.nextToken());
//		}
		System.out.println("Get config: complete!!!!");
	}

//	public static void main(String[] args) throws SQLException {
//		DBControl d = new DBControl();
//		d.getConfig(2);
//		System.out.println("---------------------");
//		d.getConfig(1);
//	}

}
