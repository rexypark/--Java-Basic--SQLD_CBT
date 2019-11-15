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
			sql.append("  FROM EXAM_INFO ");//¡∂¿Œ
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
	

}
























