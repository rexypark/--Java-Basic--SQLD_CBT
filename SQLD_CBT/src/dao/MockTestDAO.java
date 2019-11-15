package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MockTestDAO {
	
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	Scanner scan = new Scanner(System.in);

	static {
		try {
			Class.forName(DRIVER);
			System.out.println(">> JDBC Driver Loading Success");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] JDBC Driver Loading Fail");
		}
	}
	
	public void mockTestAll() {
		
		
		int examValue; //문제 개수 입력 받는 변수
		String answer; //문제 정답 입력 받는 변수
		List<ExamVO> list = new ArrayList<>();
		boolean mainWhile = true;
		while(mainWhile) {//main while문
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("|========================================================|");
				System.out.println("|               환영 합니다 모의고사를 시작하겠습니다.             |");
				System.out.println("|           SQLD시험은 총 2과목을 시험과목으로 보고 있습니다.        |");
				System.out.println("|--------------------------------------------------------|");
				System.out.println("|      1. 데이터 모델링의 이해    |     2. SQL 기본 및 활용                  |");
				System.out.println("|--------------------------------------------------------|");
				System.out.print("   => 각 과목당 풀 문제의 수를 입력해 주세요 !!! : ");
				examValue = scan.nextInt();
				examValue += 1;
				scan.nextLine();
				int[] success = new int[3]; //정답 카운트용 배열 변수 선언
				int[] fail = new int[3];    //오답 카운트용 배열 변수 선언
				
				for (int i = 1; i <= 2 ; i++) {
					StringBuilder sql = new StringBuilder();
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					
					pstmt = conn.prepareStatement(sql.toString());
					rs = pstmt.executeQuery();
				
					while (rs.next()) {
						for (int j = 0; j <= examValue; j++ ) {
						 list.add(new ExamVO(rs.getString("QUESTION"),
									rs.getString("ANSWER"),
									rs.getString("SECTION"),
									rs.getString("EXAM_SEQNUM"),
									rs.getString("ANSWER_INFO")));
						}	
						break; //사용자가 원한 문제 수 만큼 list에 add하고 break;
					}
					
					for (ExamVO mvo : list ) {
						System.out.println("==============================================================");
						System.out.println(mvo.getQwestion());//문제
						System.out.print("정답 입력 : ");	
						answer = scan.nextLine();
						
						System.out.println("==============================================================");
						if(answer.equals(mvo.getAnswer())) {
							System.out.println(" /// 정답입니다 !!! ///\n");
							System.out.println(mvo.getAnswerInfo() + "\n");
							success[i] += 1;
							if (i == 2) {
								if (false == rs.next()) {
									System.out.println("[1. 데이터 모델링의 이해]  총 문제수 : " +examValue);
									System.out.println(" - 정답 : " + success[1] + " 오답 : " + fail[1] + "\t");
									System.out.println("[2. SQL 기본 및 활용]    총 문제수 : " +examValue);
									System.out.println(" - 정답 : " + success[2] + " 오답 : " + fail[2] + "\t");
									System.out.print(">>다시 모의고사를 진행 하시겠습니까???  [y/n 입력]  :  ");
									answer = scan.nextLine();
									if(answer.equals("n")) {
										mainWhile = false;
									}
									break;
								}
							}else {
								System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
								scan.nextLine();
								break;
							}

						}else {
							System.out.println(" /// 오답입니다 !!! ///\n ");
							System.out.println(mvo.getAnswerInfo() + "\n");
							fail[i] += 1;
							if (i == 2) {
								if (false == rs.next()) {
									System.out.println("1. 데이터 모델링의 이해  정답수 : " + success[1]);
									System.out.println("1. 데이터 모델링의 이해  오답수 : " + fail[1]);
									System.out.println("2. SQL 기본 및 활용 정답수      : " + success[2]);
									System.out.println("2. SQL 기본 및 활용 오답수      : " + fail[2]);
									System.out.print(">>다시 모의고사를 진행 하시겠습니까???  [y/n 입력]  :  ");
									answer = scan.nextLine();
									if(answer.equals("n")) {
										mainWhile = false;
									}
									break;
								}
							}else {
								System.out.print(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
								scan.nextLine();
								break;
							}
						}
												
					} //for문 End
					for (int k = 0; k < 80; k++) {
						System.out.println("");
					}// clearScreen End
										
				}// for문 End
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			}
	
		}//main while End
		
	}//mockTestAll End
	
	public static void main(String[] args) {
		MockTestDAO dao = new MockTestDAO();
		dao.mockTestAll();
	}

	
}
