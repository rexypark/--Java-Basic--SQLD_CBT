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
import dao.DbConn;
import dao.JDBC_Close;

public class Quiz {
	
	private static Scanner scan = new Scanner(System.in); 
	
	public static void quizStart() {
		Scanner scan = new Scanner(System.in);
		String input;
		System.out.println("Quiz�濡 �����ϼ̽��ϴ�.");
		System.out.println("���� >> Ű���� - sqld1 : ���� 1");
		System.out.println("����>> Ű���� - sqld2 : ���� 2");
		System.out.println("�ش� Ű���带 �Է� �Ͻø� �ش� ������ ������ �����˴ϴ�.");
		
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
		}
	}//main
	
	public static void quesPrint(int section) {
		
		ExamVO vo = selectQues(section);
		String userAnswer;
		System.out.println("===========================");
		System.out.println(vo.getQwestion());//����
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(vo.getAnswerInfo() + "\n");
		
		System.out.println("==============================================================");

		
	}
	
	public static ExamVO selectQues(int section) {
		
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		ExamVO examVO = null;
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT *");
			sql.append("  FROM (SELECT* FROM EXAM_INFO ");//����
			sql.append("  WHERE SECTION = ?  ");//����
			sql.append("  ORDER BY DBMS_RANDOM.VALUE)" + 
					"WHERE ROWNUM = 1");//����
			
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.pstmt.setInt(1, section);
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			if (DbConn.rs.next()) {
				examVO = new ExamVO(DbConn.rs.getString("QUESTION"),
									DbConn.rs.getString("ANSWER"),
									DbConn.rs.getString("SECTION"),
									DbConn.rs.getString("EXAM_SEQNUM"), 
									DbConn.rs.getString("ANSWER_INFO"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
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
