package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import cbt_mulit_socket.CBTServer;
import cbt_mulit_socket.CBTServer.ServerReceiver;

public class JDBCConn {
	
	//1. JDBC 드라이버 로딩
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	public static final String USER = "SQLD_CBT";
	public static final String PASSWORD = "sqld";
	public static int result = 0;
	
	public static Connection conn;
	public static PreparedStatement pstmt;
	public static ResultSet rs;
	
	public static void closeConnStmt(Connection conn, PreparedStatement pstmt) {
		try {
			if (pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static void closeConnStmtRs(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try { 
			pstmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		try { 
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void driverLoad() {
		try {
			Class.forName(DRIVER);
//			System.out.println(">> JDBC Driver Loading Success.");
			result = 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] 드라이버 로딩 실패!!!");
		}
	}
	
	public static void clearScreen(DataInputStream in, DataOutputStream out) throws IOException { //80칸 공백용 메소드
		for (int k = 0; k < 80; k++) {
			out.writeUTF("");
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
