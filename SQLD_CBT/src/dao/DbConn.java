package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

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
//			System.out.println(">> JDBC Driver Loading Success.");
			result = 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] 드라이버 로딩 실패!!!");
		}
	}
	
	public static void clearScreen() { //80칸 공백용 메소드
		for (int k = 0; k < 80; k++) {
			System.out.println("");
		}// clearScreen End
	}
	
	static String getDateTime() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		String mm = month < 10 ? "0" + month : ""+ month;
		
		int date = cal.get(Calendar.DATE);
		String dd = date < 10 ? "0" + date : ""+ date;
		
		String dateTime = cal.get(Calendar.YEAR) 
				+ "-" + mm 
				+ "-" + dd
				+ " " + cal.get(Calendar.HOUR)	
				+ ":" + cal.get(Calendar.MINUTE)
				+ ":" + cal.get(Calendar.SECOND);
		
		return dateTime;
	}
	
	
	
}
