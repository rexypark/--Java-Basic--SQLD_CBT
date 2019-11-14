package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Close {
	public static void closeConnStmtRs(Connection conn,
			PreparedStatement pstmt) {
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
	
	
	
	public static void closeConnectionStmtRs(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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
	
	//5. 클로징 처리에 의한 자원 반납(객체 생성 순서와 역순으로 진행)
	//static 선언시 .을 이용하여 사용 가능한듯????
	//JDBC_Close.closeConnStmtRs(conn, pstmt, rs); 이렇게 사용가능하다 
	//왜냐하면 메인메소드가 static이니깐
	
	public static void closeConnStmtRs(Connection conn,
			PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	public static void closeConnStmt(Connection conn,
			PreparedStatement pstmt) {
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

}
