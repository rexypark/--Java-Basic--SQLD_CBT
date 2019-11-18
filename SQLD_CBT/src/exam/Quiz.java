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
		System.out.println("Quiz방에 입장하셨습니다.");
		System.out.println("방장 >> 키워드 - sqld1 : 과목 1");
		System.out.println("방장>> 키워드 - sqld2 : 과목 2");
		System.out.println("방장>> 키워드 - sqld3 : 과목 3");
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
				case "3" :
					quesPrint(3);
					break;
				default :
					break;
			}
			System.out.println(key);
		}
	}//main
	
	public static void quesPrint(int section) {
		
		ExamVO vo = selectQues(section);
		String userAnswer;
		System.out.println("===========================");
		System.out.println(vo.getQwestion());//문제
		System.out.print("정답 입력 : ");	

		userAnswer = scan.nextLine();
		System.out.println("==============================================================");
		if(userAnswer.equals(vo.getAnswer())) {
			System.out.println(" /// 정답입니다 !!! ///\n");

		}else {
			System.out.println(" /// 오답입니다 !!! ///\n ");
		}
		System.out.println(vo.getAnswerInfo() + "\n");
		System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
		scan.nextLine();
		
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
			sql.append("  FROM (SELECT* FROM EXAM_INFO ");//조인
			sql.append("  WHERE SECTION = ?  ");//조인
			sql.append("  ORDER BY DBMS_RANDOM.VALUE)" + 
					"WHERE ROWNUM = 1");//조인
			
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
		} else if (usrInput.equals("sqld3")) {
			result = "3";
			return result;
		}
		
		return usrInput;
	}
}
