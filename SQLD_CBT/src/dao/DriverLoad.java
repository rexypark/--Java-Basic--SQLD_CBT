package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public static class DriverLoad {
	//1. JDBC ����̹� �ε�
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	public static final String USER = "SQLD_CBT";
	public static final String PASSWORD = "sqld";
	static int checkDbIn = 0;
	
	public  static Connection conn;
	public  static PreparedStatement pstmt;
	public  static ResultSet rs;
	
	public int driverLoad() {
		int checkDbIn = 0;
		try {
			Class.forName(DRIVER);
			System.out.println(">> ���� ����.");
			return checkDbIn;
			
		} catch (ClassNotFoundException e) {
			System.out.println("[���ܹ߻�] ����̹� �ε� ����!!!");
			return checkDbIn; 
		}
	}
	
	
	public void dbConn() {
		if (checkDbIn == 0) {
			driverLoad();	
		}
}
