package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DriverLoad {
	//1. JDBC ����̹� �ε�
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";
	static int checkDbIn = 0;
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
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
}
