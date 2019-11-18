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
		
		

		DbConn.clearScreen(); // 화면 Clear
		int examValue; //문제 개수 입력 받는 변수
		int examCount; //프로그램 종료를 위한 카운트 변수
		String answer; //문제 정답 입력 받는 변수
		List<ExamVO> list = new ArrayList<>();
		
		boolean mainWhile = true;
		while(mainWhile) {//main while문
			try {

//				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				System.out.println("|=======================================================================|");
				System.out.println("|                                                                       |");
				System.out.println("|                                [환영 합니다]                             |");
				System.out.println("|                      [" + UserDAO.userInfo.getId() + "]님 SQLD 모의고사를 시작하겠습니다                                 |");
				System.out.println("|                                                                       |");
				System.out.println("|                시험 시간은 총 90분이며 아래와 같이 문제가 출제된다.                 |");
				System.out.println("|      (1과목) 데이터 모델링의 이해 : 10문제(문항당 2점) -> 총 20점 ( 8점 미만 과락)    |");
				System.out.println("|      (2과목) SQL 기본 및 활용     : 40문제(문항당 2점) -> 총 80점 (32점 미만 과락)    |");
				System.out.println("|    각 과목별로 최소 40% 이상 득점 못할 시에 과락이 되며 총 점수가 '60점' 이상이면 합격이다     |");
				System.out.println("|                                                                       |");
				System.out.println("|-----------------------------------------------------------------------|");
				System.out.println("|         1. 데이터 모델링의 이해                    |         2. SQL 기본 및 활용                  |");
				System.out.println("|-----------------------------------------------------------------------|");
				
				System.out.print("   => 각 과목당 풀 문제의 수를 입력해 주세요 !!! : ");
				long startTime = System.currentTimeMillis(); //모의고사 시작 시간 Check
				
				while (!scan.hasNextInt()) { //문자열 유효성 검사
	                scan.next();
	                System.err.print("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
				}

				examValue = scan.nextInt();
				scan.nextLine();
				examCount = examValue * 2;
				DbConn.clearScreen(); // 화면 Clear
				int[] success = new int[2]; //정답 카운트용 배열 변수 선언
				int[] fail = new int[2];    //오답 카운트용 배열 변수 선언
				
				for (int i = 1; i <= 2 ; i++) { //SELECT FOR문 시작
					StringBuilder sql = new StringBuilder();
					if (DbConn.result == 0) {
						DbConn.driverLoad();
					}
					DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					sql.append(" ORDER BY DBMS_RANDOM.VALUE"); //랜덤으로 데이터를 가져 온다.
					
					System.out.println("i의 값 : " + i);
					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					System.out.println("???????");
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
						while (!scan.hasNextInt()) { //문자열 유효성 검사
			                scan.next();
			                System.err.print("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
						}
						 answer = scan.nextLine();
						 
						 System.out.println("==============================================================");
						 if(answer.equals(mvo.getAnswer())) {
							 System.out.println(" /// 정답입니다 !!! ///\n");
						      ExamDAO.insertOne(mvo ,"o");
							 success[i - 1] += 1;
						 }else {
							 System.out.println(" /// 오답입니다 !!! ///\n ");
						      ExamDAO.insertOne(mvo ,"x");
							 fail[i - 1] += 1;
						 }
						 examCount--;
						 System.out.println(mvo.getAnswerInfo() + "\n");
						 System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
						 scan.nextLine();
						 DbConn.clearScreen(); //Enter키 입력시 80칸공백 method 호출

					 } //for문 End
					 if (i == 2) {
						 if (examCount == 0) {
							 System.out.println(UserDAO.userInfo.getId());
							 long endTime = System.currentTimeMillis();
							 System.out.println( "모의고사 경과시간 : " + ( endTime - startTime )/1000.0 +"초");

							 System.out.println("[1. 데이터 모델링의 이해]  총 문제수 : " +examValue);
							 System.out.println(" - 정답 : " + success[0] + " 오답 : " + fail[0] + "\t");
							 System.out.println("[2. SQL 기본 및 활용]    총 문제수 : " +examValue);
							 System.out.println(" - 정답 : " + success[1] + " 오답 : " + fail[1] + "\t");
							 
							 while(true) {//유효성 검사
								 System.out.print(">>다시 모의고사를 진행 하시겠습니까???  [y/n 입력]  :  ");
								 answer = scan.nextLine();
								 if(answer.equals("n")) {
									 mainWhile = false; //MockTestDAO 종료
									 break;
								 } else if(answer.equals("y")) {
									 break;
								 } else {
									 System.out.println("[Error] y또는 n을 입력해 주세요."); 
								 }
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

}
