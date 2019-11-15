package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbConn {
	
	//1. JDBC 드라이버 로딩
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	public static final String USER = "SQLD_CBT";
	public static final String PASSWORD = "sqld";
	public static int result = 0;
	
	public static Connection conn;
	public static PreparedStatement pstmt;
	public static ResultSet rs;
	
	
	public static void driverLoad() {
		try {
			Class.forName(DRIVER);
			System.out.println(">> JDBC Driver Loading Success.");
			result = 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] 드라이버 로딩 실패!!!");
		}
	}
}
