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
		List<ExamVO> reList1 = new ArrayList<>();
		List<ExamVO> reList2 = new ArrayList<>();
		List<ExamVO> scoreList = new ArrayList<>();
		
		boolean mainWhile = true;
		while(mainWhile) {//main while문
			try {
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
				reList1 = new ArrayList<>();
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
							 reList1.add(mvo); //틀린 문제를 다시 풀 수 있도록 reList에 담는다.
						 }
						 examCount--;
						 System.out.println(mvo.getAnswerInfo() + "\n");
						 System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
						 scan.nextLine();
						 DbConn.clearScreen(); //Enter키 입력시 80칸공백 method 호출

					 } //for문 End
					 if (i == 2) {
						 if (examCount == 0) {
							 long endTime = System.currentTimeMillis();
							 int[] allTotal = new int[2]; 
							 int[] xTotal = new int[2];    
							 int[] oTotal = new int[2];    
							 scoreList = new ArrayList<>();
							 scoreList = dbSearch(2, 1);
							 for (ExamVO mvo : scoreList) {
								 int index = 0;
								 allTotal[index] = Integer.parseInt(mvo.getAllTotal());
								 xTotal[index] = Integer.parseInt(mvo.getxTotal());
								 oTotal[index] = Integer.parseInt(mvo.getoTotal());
								 index++;
							 }
							 double chapter1 = Double.parseDouble(String.format("%.2f",((double) success[0] / (double) examValue) * 100));
							 double chapter2 = Double.parseDouble(String.format("%.2f",((double) success[1] / (double) examValue) * 100));
							 double allChapter1 = Double.parseDouble(String.format("%.2f",((double) oTotal[0] / (double) allTotal[0]) * 100));
							 double allChapter2 = Double.parseDouble(String.format("%.2f",((double) oTotal[1] / (double) allTotal[1]) * 100));
							 System.out.println("=============================================================");
							 System.out.println(UserDAO.userInfo.getId() + "님의 모의고사 진행결과  ");
							 System.out.println(" - 총 진행시간 : " + ( endTime - startTime )/1000.0 +"초");
							 System.out.println(" - [1과목. 데이터 모델링의 이해]  ");
							 System.out.println("    - 문제수 : " + examValue +         "                " + "   - 총 문제풀이수 : " + allTotal[0] );
							 System.out.println("    - 정답 : " + success[0] + "개   |" + " 오답 : " + fail[0] + "개"+ 
							                    "           " + "   - 총 정답수 : " + oTotal[0] + "개  |" + " 총 오답수 : " + xTotal[0] + "개" );
							 System.out.println("    - 정답률 : " + chapter1 + "%"+         "        " + "      - 총 정답률 : " + allChapter1 + "%" );
							 System.out.println();
							 System.out.println(" - [2과목. SQL 기본 및 활용]  ");
							 System.out.println("    - 문제수 : " + examValue +         "                " + "   - 총 문제풀이수 : " + allTotal[1] );
							 System.out.println("    - 정답 : " + success[1] + "개   |" + " 오답 : " + fail[1] + "개"+ 
							                    "           " + "   - 총 정답수 : " + oTotal[1] + "개  |" + " 총 오답수 : " + xTotal[1] + "개" );
							 System.out.println("    - 정답률 : " + chapter2 + "%"+         "        " + "      - 총 정답률 : " + allChapter2 + "%" );
							 System.out.println("=============================================================");
							 System.out.println();
					 
							 
							 while(true) {//유효성 검사
								 reList2 = new ArrayList<>();
								 System.out.print(">> 틀린문제를 다시 푸시겠습니까????  [y/n 입력]  :  ");
								 answer = scan.nextLine();
								 System.out.println();
								 if(answer.equals("y")) { //y 입력시 오답문제만 다시 풀 수 있도록 출력
									reList2 = reExam(reList1);
									reList1 = reList2;
								 } else if(answer.equals("n")) { //n 입력시 모의고사 진행 여부를 물어본다.
									 System.out.print(">> 다시 모의고사를 진행 하시겠습니까???  [y/n 입력]  :  ");
									 answer = scan.nextLine();
									 System.out.println();
									 if(answer.equals("n")) { //mockTestAll 종료
										 mainWhile = false; 
										 break;
									 } else if(answer.equals("y")) { //mockTestAll 다시 시작
										 break;
									 } else {
										 System.out.println("[Error] y또는 n을 입력해 주세요."); 
									 }
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
		
	}//mockTestAll method End
	
	public static List<ExamVO> reExam(List<ExamVO> List) {
		String answer;
		int success = 0;
		int fail = 0;
		List<ExamVO> reList = new ArrayList<>();
		for (ExamVO mvo : List ) { // examValue만큼 문제를 보여준다.  
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
			      success += 1;
			 }else {
				 System.out.println(" /// 오답입니다 !!! ///\n ");
			      ExamDAO.insertOne(mvo ,"x");
			      reList.add(mvo); //틀린 문제를 다시 풀 수 있도록 reList에 담는다.
			      fail += 1;
			 }
			 System.out.println(mvo.getAnswerInfo() + "\n");
			 System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
			 scan.nextLine();
			 DbConn.clearScreen(); //Enter키 입력시 80칸공백 method 호출 
		}
		
		 System.out.println(" - [오답문제 결과]  ");
		 System.out.println("    - 문제수 : " + (success + fail));
		 System.out.println("    - 정답 : " + success + "개   |" + " 오답 : " + fail + "개");
		 System.out.println();
		 
		 return reList;
		
	}// reExam method End
	
	
	public static List<ExamVO> dbSearch(int index, int exam) {
		
		List<ExamVO> dbSearch = new ArrayList<>();
		
		for (int i = 1; i <= 2 ; i++) { //SELECT FOR문 시작
			StringBuilder sql = new StringBuilder();
			if (DbConn.result == 0) {
				DbConn.driverLoad();
			}
			try {
				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				if(index ==2) { // SCORE_INFO 가져오는 거
					
					sql.append("SELECT ");
					sql.append("(Select count(*) From SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId() +"' AND S_SECTION =  " + i + " )as total ");
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '"+ UserDAO.userInfo.getId() +"' AND S_SECTION =  " + i + " AND O_X = 'o')as o_total " );
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '"+ UserDAO.userInfo.getId() +"' AND S_SECTION =  " + i + " AND O_X = 'x')as x_total ");
					sql.append("FROM SCORE_INFO ");
					sql.append("WHERE ROWNUM = 1 ");
					
					
					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					DbConn.rs = DbConn.pstmt.executeQuery();
					System.out.println(i + " rs 성공");
					while (DbConn.rs.next()) {
						dbSearch.add(new ExamVO(DbConn.rs.getString("total"),
												DbConn.rs.getString("o_total"),
												DbConn.rs.getString("x_total")));
					}
					System.out.println(i + " dbSearch.add 성공");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			}
						
			
		}//SELECT for문 End
		
		return dbSearch;
	
	}//dbSearch method End
			
}
