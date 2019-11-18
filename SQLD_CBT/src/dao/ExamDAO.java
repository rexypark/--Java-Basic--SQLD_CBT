package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.ExamVO;
import vo.UserVO;

public class ExamDAO {
	
	
	public List<ExamVO> selectAll() {
		
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		List<ExamVO> list = new ArrayList<>();
		
		try {
			
			if (DbConn.result == 0) {
				DbConn.driverLoad();
			}
			
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT QUESTION, ANSWER, SECTION, EXAM_SEQNUM");
			sql.append("  FROM EXAM_INFO ");//조인
			sql.append(" ORDER BY QUESTION");
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			while (DbConn.rs.next()) {
			 list.add(new ExamVO(DbConn.rs.getString("QUESTION"),
								 DbConn.rs.getString("ANSWER"),
								 DbConn.rs.getString("SECTION"),
								 DbConn.rs.getString("EXAM_SEQNUM"), null));
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}
		
		return list;
	}
	
	
	public static int insertOne(ExamVO SCORE_INFO ,String oX) {
		   int result = 0;
		   
		   try {
		      //DB연결 - Connection 객체 생성(DB와 연결된)
			   DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
		      
		      //SQL문장을 작성해서 Statement에 전달하고 SQL문 실행 요청
		      StringBuilder sql = new StringBuilder();
		      sql.append("INSERT INTO SCORE_INFO ");
		      sql.append("       (S_SECTION, S_EXAM_SEQNUM, S_USER_ID, O_X, SAVE_TIME) ");
		      sql.append("VALUES (?, ?, ?, ?, (TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))) ");
		      DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
		      
		      // ?(바인딩변수)에 값 매칭 시키기
		      DbConn.pstmt.setString(1, SCORE_INFO.getSection());
		      DbConn.pstmt.setString(2, SCORE_INFO.getExamSeq());
		      DbConn.pstmt.setString(3, UserDAO.userInfo.getId());
		      DbConn.pstmt.setString(4, oX);
		      
		      //SQL 실행 요청 - INSERT, UPDATE, DELETE : executeUpdate()
		      result = DbConn.pstmt.executeUpdate();
		   } catch (SQLException e) {
		      e.printStackTrace();
		   } finally {
		      JDBC_Close.closeConnStmt(DbConn.conn, DbConn.pstmt);
		   }
		   
		   return result;
		  }

}
























