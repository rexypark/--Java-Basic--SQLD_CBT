package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vo.ExamVO;

public class MockTestDAO {

	static Scanner scan = new Scanner(System.in);

	public static void mockTestAll() {
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		DbConn.clearScreen(); // 화면 Clear
		int examValue; //문제 개수 입력 받는 변수
		String answer; //문제 정답 입력 받는 변수
		List<ExamVO> list = new ArrayList<>();
		boolean mainWhile = true;
		while(mainWhile) {//main while문
			try {
				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				System.out.println("|========================================================|");
				System.out.println("|               환영 합니다 모의고사를 시작하겠습니다.             |");
				System.out.println("|           SQLD시험은 총 2과목을 시험과목으로 보고 있습니다.        |");
				System.out.println("|--------------------------------------------------------|");
				System.out.println("|      1. 데이터 모델링의 이해    |     2. SQL 기본 및 활용                  |");
				System.out.println("|--------------------------------------------------------|");
				System.out.print("   => 각 과목당 풀 문제의 수를 입력해 주세요 !!! : ");
				examValue = scan.nextInt();
				scan.nextLine();
				int[] success = new int[2]; //정답 카운트용 배열 변수 선언
				int[] fail = new int[2];    //오답 카운트용 배열 변수 선언
				
				for (int i = 1; i <= 2 ; i++) { //SELECT FOR문 시작
					StringBuilder sql = new StringBuilder();
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					sql.append(" ORDER BY DBMS_RANDOM.VALUE"); //랜덤으로 데이터를 가져 온다.
					
					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					DbConn.rs = DbConn.pstmt.executeQuery();
				
					for (int j = 0 ; j < examValue; j++) {
						while (DbConn.rs.next()) {
							
							 list.add(new ExamVO(DbConn.rs.getString("QUESTION"),
												 DbConn.rs.getString("ANSWER"),
												 DbConn.rs.getString("SECTION"),
												 DbConn.rs.getString("EXAM_SEQNUM"),
												 DbConn.rs.getString("ANSWER_INFO")));
							break;
						}
					}
					
				 	for (ExamVO mvo : list ) { // examValue만큼 문제를 보여준다.  
						 System.out.println("==============================================================");
						 System.out.println(mvo.getQwestion());//문제
						 System.out.print("정답 입력 : ");	
						 answer = scan.nextLine();
						 
						 System.out.println("==============================================================");
						 if(answer.equals(mvo.getAnswer())) {
							 System.out.println(" /// 정답입니다 !!! ///\n");
							 success[i - 1] += 1;
						 }else {
							 System.out.println(" /// 오답입니다 !!! ///\n ");
							 fail[i - 1] += 1;
						 }
						 System.out.println(mvo.getAnswerInfo() + "\n");
						 System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
						 scan.nextLine();
						 DbConn.clearScreen(); //Enter키 입력시 80칸공백 method 호출

					 } //for문 End
				 	
					 if (i == 2) {
						 if (false == DbConn.rs.next()) {
							 System.out.println("[1. 데이터 모델링의 이해]  총 문제수 : " +examValue);
							 System.out.println(" - 정답 : " + success[0] + " 오답 : " + fail[0] + "\t");
							 System.out.println("[2. SQL 기본 및 활용]    총 문제수 : " +examValue);
							 System.out.println(" - 정답 : " + success[1] + " 오답 : " + fail[1] + "\t");
							 System.out.print(">>다시 모의고사를 진행 하시겠습니까???  [y/n 입력]  :  ");
							 answer = scan.nextLine();
							 if(answer.equals("n")) {
								 mainWhile = false; //MockTestDAO 종료
							 }
										 
						 }
						 
					 }//if문 end
					 
					 DbConn.clearScreen(); //Enter키 입력시 80칸공백 method 호출
					 
				}// SELECT for문 End
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			}//try End
	
		}//main while End
		
	}//mockTestAll End
	
//	public static void main(String[] args) {
//		MockTestDAO dao = new MockTestDAO();
//		dao.mockTestAll();
//	}

}
