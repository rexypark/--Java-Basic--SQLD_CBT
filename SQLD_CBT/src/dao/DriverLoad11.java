package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DriverLoad11 {
	//1. JDBC 드라이버 로딩
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	public static final String USER = "SQLD_CBT";
	public static final String PASSWORD = "sqld";
	static int checkDbIn = 0;
	
	public  Connection conn;
	public  PreparedStatement pstmt;
	public  ResultSet rs;
	
	
	public static String getUrl() {
		return URL;
	}

	public static String getUser() {
		return USER;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public int driverLoad() {
		int result = 0;
		try {
			Class.forName(DRIVER);
			System.out.println(">> JDBC Driver Loading Success.");
			result = 1;
			return result;
			
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] 드라이버 로딩 실패!!!");
			return result; 
		}
	}
}
