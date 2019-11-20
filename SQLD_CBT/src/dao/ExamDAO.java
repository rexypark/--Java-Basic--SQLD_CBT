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
		
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		List<ExamVO> list = new ArrayList<>();
		
		try {
			
			if (JDBCConn.result == 0) {
				JDBCConn.driverLoad();
			}
			
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT QUESTION, ANSWER, SECTION, EXAM_SEQNUM");
			sql.append("  FROM EXAM_INFO ");//����
			sql.append(" ORDER BY QUESTION");
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
			
			while (JDBCConn.rs.next()) {
			 list.add(new ExamVO(JDBCConn.rs.getString("QUESTION"),
								 JDBCConn.rs.getString("ANSWER"),
								 JDBCConn.rs.getString("SECTION"),
								 JDBCConn.rs.getString("EXAM_SEQNUM"), null));
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}
		
		return list;
	}
	
	
	public static int insertOne(ExamVO SCORE_INFO ,String oX) {
		   int result = 0;
		   
		   try {
		      //DB���� - Connection ��ü ����(DB�� �����)
			   JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
		      
		      //SQL������ �ۼ��ؼ� Statement�� �����ϰ� SQL�� ���� ��û
		      StringBuilder sql = new StringBuilder();
		      sql.append("INSERT INTO SCORE_INFO ");
		      sql.append("       (S_SECTION, S_EXAM_SEQNUM, S_USER_ID, O_X, SAVE_TIME) ");
		      sql.append("VALUES (?, ?, ?, ?, (TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))) ");
		      JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
		      
		      // ?(���ε�����)�� �� ��Ī ��Ű��
		      JDBCConn.pstmt.setString(1, SCORE_INFO.getSection());
		      JDBCConn.pstmt.setString(2, SCORE_INFO.getExamSeq());
		      JDBCConn.pstmt.setString(3, UserDAO.userInfo.getId());
		      JDBCConn.pstmt.setString(4, oX);
		      
		      //SQL ���� ��û - INSERT, UPDATE, DELETE : executeUpdate()
		      result = JDBCConn.pstmt.executeUpdate();
		   } catch (SQLException e) {
		      e.printStackTrace();
		   } finally {
			   JDBCConn.closeConnStmt(JDBCConn.conn, JDBCConn.pstmt);
		   }
		   
		   return result;
		  }

}
























