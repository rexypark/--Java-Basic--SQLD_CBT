package com.mystudy.sqld_cbt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	private static final String IP = "localhost";
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	
	private static final String URL = "jdbc:oracle:thin:@"+ IP + ":1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public static UserVO userInfo;
	
	static {
		try {
			//1. JDBC ����̹� �ε�
			Class.forName(DRIVER);
			System.out.println(">> JDBC ����̹� �ε� ����");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(">> JDBC ����̹� �ε� ����");
		}
		
		
		}

	
	//ȸ������ ���� �Է�
	//true�̸� userLog id, name, act(ȸ������)
	public int inputUserInfo(UserVO user) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//SQL������ �ۼ��ؼ� Statement�� �����ϰ� sql�� ���� ��û
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_INFO");
			sql.append("     (SELECT USER_ID, USER_NAME, USER_PW, USER_PHONE, USER_EMAIL,USER_SEQNUM)");
			sql.append("VALUES(?,?,?,?,?,SEQ_USER_INFO.NEXTVAL) "); //SEQ_USER_INFO ������ ����***
			pstmt = conn.prepareStatement(sql.toString());
			
			//?(Q���ε�����)�� �����Ű��
			pstmt.setString(1, user.getUser_id());
			pstmt.setString(2, user.getUser_name());
			pstmt.setString(3, user.getUser_pw());
			pstmt.setString(4, user.getUser_phone());
			pstmt.setString(5, user.getUser_email());
			
			System.out.println("sql.toString() : " + sql.toString());
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt);
		}
		return result;
	}
	
	
	//�α��� ID/PW üũ
	//true�̸� userLog id, name, act(����)
	public boolean checkIdPassword(String id, String password) {
		boolean result = false;
		
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			
			
			//3. Statement�� ����(SQL�� ����)  
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ? AND USER_PW = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			System.out.println("sql.toString() : " + sql.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println(">> " + id + " ������ ����");
				result = true;
				userInfo = new UserVO(rs.getString("USER_ID"), 
								  	 rs.getString("USER_NAME"), 
									 rs.getString("USER_PW"), 
									 rs.getString("USER_PHONE"), 
									 rs.getString("USER_EMAIL"));
				System.out.println(userInfo.toString());
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//����� ��ü close
			JDBC_Close.closeConnectionStmtRs(conn, pstmt, rs);
			
		}
		
		return result;
	}







	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
