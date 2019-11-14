package com.mystudy.sqld_cbt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//DAO, Dao : Data Access Object / Database Access Object
//데이터(데이터베이스-DB)와 연동해서 CRUD를 구현한 클래스
public class UserLogDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	//드라이버 로딩 처리
	//static 초기화 구문 (JVM이 실행될 때 한 번만 실행된다.)
	static{
		try {
			Class.forName(DRIVER);
			System.out.println(">> JDBC Driver Loading Success");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] JDBC Driver Loading Fail");
			
		}
	}
	
	//회원가입, 접속, 종료 Event log method
	public static int userLog(String id, String name, String act) {
		int result = 0;
		try {
			//DB연결 - Connection 객체 생성(DB와 연결된)
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//SQL문장을 작성해서 Statement에 전달하고 SQL문 실행 요청
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_LOG ");
			sql.append("(L_USER_ID, L_USER_NAME, USER_ACT, USER_TIME) ");
			sql.append("VALUES (?, ?, ?, "
					+ "(TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))) ");
			pstmt = conn.prepareStatement(sql.toString());
				
			// ? (바인딩변수)에 값 매칭 시키기
			int idx = 1;
			pstmt.setString(idx++, id);        //회원 아이디                    (L_USER_ID)
			pstmt.setString(idx++, name);      //회원 이름                       (L_USER_NAME)
			pstmt.setString(idx++, act);       //회원가입, 접속, 종료    (USER_ACT)
			
			System.out.println("요청전");
			
			//SQL 실행 요청
			result = pstmt.executeUpdate();
			
			System.out.println("요청후");
			
			//실패 하면 3회 retry 한다.
			for (int i = 0; i < 3; i++) {
				if(result == 0) {
					result += pstmt.executeUpdate();
				}else {
					break;
				}
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmt(conn, pstmt);		
		}
		
		return result;
	}// userLog End

}
