package exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vo.ExamVO;
import dao.JDBCConn;

public class Quiz {
	
	private static Scanner scan = new Scanner(System.in); 
	
	public static void quizStart() {
		Scanner scan = new Scanner(System.in);
		String input;
		System.out.println("Quiz방에 입장하셨습니다.");
		System.out.println("방장 >> 키워드 - sqld1 : 과목 1");
		System.out.println("방장>> 키워드 - sqld2 : 과목 2");
		System.out.println("해당 키워드를 입력 하시면 해당 과목의 문제가 출제됩니다.");
		
		while (true) {
			//1 
			System.out.print(dao.UserDAO.userInfo.getId() + " >>");
			String key = keyword(scan.nextLine());
			if (key.equals("exit")) break;
				
			switch (keyword(key)) {
				case "1" :
					quesPrint(1);
					break;
				case "2" :
					quesPrint(2);
					break;
				default :
					break;
			}
			if (key =="1" || key =="2") {
				continue;
			} else {
				System.out.println(key);	
			}
		}
	}//main
	
	public static void quesPrint(int section) {
		
		ExamVO vo = selectQues(section);
		String userAnswer;
		System.out.println("===========================");
		System.out.println(vo.getQwestion());//문제
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(vo.getAnswerInfo() + "\n");
		
		System.out.println("==============================================================");

		
	}
	
	public static ExamVO selectQues(int section) {
		
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		ExamVO examVO = null;
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT *");
			sql.append("  FROM (SELECT* FROM EXAM_INFO ");//조인
			sql.append("  WHERE SECTION = ?  ");//조인
			sql.append("  ORDER BY DBMS_RANDOM.VALUE)" + 
					"WHERE ROWNUM = 1");//조인
			
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.pstmt.setInt(1, section);
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
			
			if (JDBCConn.rs.next()) {
				examVO = new ExamVO(JDBCConn.rs.getString("QUESTION"),
									JDBCConn.rs.getString("ANSWER"),
									JDBCConn.rs.getString("SECTION"),
									JDBCConn.rs.getString("EXAM_SEQNUM"), 
									JDBCConn.rs.getString("ANSWER_INFO"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}
		return examVO;
	}
	
	public static String keyword(String usrInput) {
		String result = "0";
		
		if (usrInput.equals("sqld1"))  {
			result = "1";
			return result;
		} else if (usrInput.equals("sqld2")) {
			result = "2";
			return result;
		} 
		return usrInput;
	}
}
