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
			//1. JDBC 드라이버 로딩
			Class.forName(DRIVER);
			System.out.println(">> JDBC 드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(">> JDBC 드라이버 로딩 실패");
		}
		
		
		}

	
	//회원가입 정보 입력
	//true이면 userLog id, name, act(회원가입)
	public int inputUserInfo(UserVO user) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//SQL문장을 작성해서 Statement에 전달하고 sql문 실행 요청
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_INFO");
			sql.append("     (SELECT USER_ID, USER_NAME, USER_PW, USER_PHONE, USER_EMAIL,USER_SEQNUM)");
			sql.append("VALUES(?,?,?,?,?,SEQ_USER_INFO.NEXTVAL) "); //SEQ_USER_INFO 시퀀스 생성***
			pstmt = conn.prepareStatement(sql.toString());
			
			//?(Q바인딩변수)에 저장시키기
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
	
	
	//로그인 ID/PW 체크
	//true이면 userLog id, name, act(접속)
	public boolean checkIdPassword(String id, String password) {
		boolean result = false;
		
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			
			
			//3. Statement문 실행(SQL문 실행)  
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
				System.out.println(">> " + id + " 데이터 있음");
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
			//사용한 객체 close
			JDBC_Close.closeConnectionStmtRs(conn, pstmt, rs);
			
		}
		
		return result;
	}







	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
