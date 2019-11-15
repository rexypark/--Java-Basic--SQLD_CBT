package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.UserVO;

public class ExamDAO extends{
	public static void main(String[] args) {
	DriverLoad dload = new DriverLoad();
	if (dload.checkDbIn == 0) {
		dload.driverLoad();	
	}
	
	}
		
	public List<ExamVO> selectAll() {
		List<ExamVO> list = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT QUESTION, ANSWER, SECTION, EXAM_SEQNUM");
			sql.append("  FROM EXAM_INFO ");//¡∂¿Œ
			sql.append(" ORDER BY QUESTION");
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			 list.add(new ExamVO(rs.getString("QUESTION"),
						rs.getString("ANSWER"),
						rs.getString("SECTION"),
						rs.getString("EXAM_SEQNUM")));
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
	}
	

}
























