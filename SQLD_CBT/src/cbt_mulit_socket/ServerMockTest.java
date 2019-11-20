package cbt_mulit_socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.LogRecord;

import dao.DbConn;
import dao.ExamDAO;
import dao.JDBC_Close;
import dao.UserDAO;
import log_in.LogRegex;
import vo.ExamVO;

public class ServerMockTest {

	static Scanner scan = new Scanner(System.in);

	public static void mockTestAll(DataInputStream in, DataOutputStream out, String id) throws IOException {

		DbConn.clearScreen(); // 화면 Clear
		String examValue; // 문제 개수 입력 받는 변수
		int examCount; // 프로그램 종료를 위한 카운트 변수
		String answer; // 문제 정답 입력 받는 변수
		List<ExamVO> list = new ArrayList<>();
		List<ExamVO> reList1 = new ArrayList<>();
		List<ExamVO> reList2 = new ArrayList<>();
		List<ExamVO> scoreList = new ArrayList<>();

		boolean mainWhile = true;
		while (mainWhile) {// main while문
			try {
				out.writeUTF("|=======================================================================|");
				out.writeUTF("|                                                                       |");
				out.writeUTF("|                                [환영 합니다]                             |");
				out.writeUTF("|                      [" + id
						+ "]님 SQLD 모의고사를 시작하겠습니다                                 |");
				out.writeUTF("|                                                                       |");
				out.writeUTF("|                시험 시간은 총 90분이며 아래와 같이 문제가 출제된다.                 |");
				out.writeUTF("|      (1과목) 데이터 모델링의 이해 : 10문제(문항당 2점) -> 총 20점 ( 8점 미만 과락)    |");
				out.writeUTF("|      (2과목) SQL 기본 및 활용     : 40문제(문항당 2점) -> 총 80점 (32점 미만 과락)    |");
				out.writeUTF("|    각 과목별로 최소 40% 이상 득점 못할 시에 과락이 되며 총 점수가 '60점' 이상이면 합격이다     |");
				out.writeUTF("|                                                                       |");
				out.writeUTF("|-----------------------------------------------------------------------|");
				out.writeUTF("|         1. 데이터 모델링의 이해                    |         2. SQL 기본 및 활용                  |");
				out.writeUTF("|-----------------------------------------------------------------------|");

				out.writeUTF("   => 각 과목당 풀 문제의 수를 입력해 주세요 !!! : ");
				long startTime = System.currentTimeMillis(); // 모의고사 시작 시간 Check
				examValue = in.readUTF();
				while (true) { // 문자열 유효성 검사
					if (LogRegex.isiInt(examValue)) {
						break;
					} else {
						out.writeUTF("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
						examValue = in.readUTF();
					}
				}
				examCount = Integer.parseInt(examValue) * 2;
				DbConn.clearScreen(); // 화면 Clear
				int[] success = new int[2]; // 정답 카운트용 배열 변수 선언
				int[] fail = new int[2]; // 오답 카운트용 배열 변수 선언
				reList1 = new ArrayList<>();
				for (int i = 1; i <= 2; i++) { // SELECT FOR문 시작
					StringBuilder sql = new StringBuilder();
					if (DbConn.result == 0) {
						DbConn.driverLoad();
					}
					DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					sql.append(" ORDER BY DBMS_RANDOM.VALUE"); // 랜덤으로 데이터를 가져 온다.

					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					DbConn.rs = DbConn.pstmt.executeQuery();

					for (int j = 0; j < Integer.parseInt(examValue); j++) {
						while (DbConn.rs.next()) {

							list.add(new ExamVO(DbConn.rs.getString("QUESTION"), DbConn.rs.getString("ANSWER"),
									DbConn.rs.getString("SECTION"), DbConn.rs.getString("EXAM_SEQNUM"),
									DbConn.rs.getString("ANSWER_INFO")));
							break;
						}
					}
					boolean test = true;
					for (ExamVO mvo : list) { // examValue만큼 문제를 보여준다.
						out.writeUTF("==============================================================");
						out.writeUTF(mvo.getQwestion());// 문제
						out.writeUTF("정답 입력 : ");
						out.writeUTF("여기다: ");
						answer = in.readUTF();
						
						while (true) { // 문자열 유효성 검사
		                     if (answer.length() == 0) {
		                        out.writeUTF("1 - 4 사이의 숫자만 입력 가능합니다. \n  >>> 다시입력하세요.");
		                        answer = in.readUTF();
		                     }
		                     if (!LogRegex.isiInt(answer)) {
		                        out.writeUTF("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
		                        answer = in.readUTF();
		                     }
		                     if (Integer.parseInt(answer) <= 4 && Integer.parseInt(answer)>=1) {
		                        break;
		                     } else {
		                           out.writeUTF("1 - 4 사이의 숫자만 입력 가능합니다. \n  >>> 다시입력하세요.");
		                           answer = in.readUTF();
		                     }
		                  }
//						while (test) { // 문자열 유효성 검사
//							while (true) {
//								if (LogRegex.isiInt(answer)) {
//									break;
//								} else {
//									out.writeUTF("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
//									answer = in.readUTF();
//									break;
//								}
//							}
//							
//							while (true) {
//								if (Integer.parseInt(answer) <= 4 && Integer.parseInt(answer)>=1) {
//									test = false;
//									break;
//								} else {
//									out.writeUTF("1 - 4 사이의 숫자만 입력 가능합니다. \n  >>> 다시입력하세요.");
//									answer = in.readUTF();
//									test = true;
//									break;
//								}
//							}
//						}
						
						out.writeUTF("==============================================================");
						if (answer.equals(mvo.getAnswer())) {
							out.writeUTF(" /// 정답입니다 !!! ///\n");
							ExamDAO.insertOne(mvo, "o");
							success[i - 1] += 1;
						} else {
							out.writeUTF(" /// 오답입니다 !!! ///\n ");
							ExamDAO.insertOne(mvo, "x");
							fail[i - 1] += 1;
							reList1.add(mvo); // 틀린 문제를 다시 풀 수 있도록 reList에 담는다.
						}
						examCount--;
						out.writeUTF(mvo.getAnswerInfo() + "\n");
						out.writeUTF(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
						in.readUTF();
						DbConn.clearScreen(); // Enter키 입력시 80칸공백 method 호출

					} // for문 End
					if (i == 2) {
						if (examCount == 0) {
							long endTime = System.currentTimeMillis();
							int[] allTotal = new int[2];
							int[] xTotal = new int[2];
							int[] oTotal = new int[2];
							scoreList = new ArrayList<>();
							scoreList = dbSearch(2, 1);
							int index = 0;
							for (ExamVO mvo : scoreList) {
								allTotal[index] = Integer.parseInt(mvo.getAllTotal());
								xTotal[index] = Integer.parseInt(mvo.getxTotal());
								oTotal[index] = Integer.parseInt(mvo.getoTotal());
								index++;
							}

							double chapter1 = Double.parseDouble(String.format("%.2f",
									((double) success[0] / (double) Integer.parseInt(examValue)) * 100));
							double chapter2 = Double.parseDouble(String.format("%.2f",
									((double) success[1] / (double) Integer.parseInt(examValue)) * 100));
							double allChapter1 = Double.parseDouble(
									String.format("%.2f", ((double) oTotal[0] / (double) allTotal[0]) * 100));
							double allChapter2 = Double.parseDouble(
									String.format("%.2f", ((double) oTotal[1] / (double) allTotal[1]) * 100));
							out.writeUTF(
									"=============================================================================");
							out.writeUTF(UserDAO.userInfo.getId() + "님의 모의고사 진행결과  ");
							out.writeUTF(" - 총 진행시간 : " + (endTime - startTime) / 1000.0 + "초");
							out.writeUTF(" - [1과목. 데이터 모델링의 이해]  ");
							out.writeUTF("    - 문제수 : " + examValue + "                " + "       - 총 문제풀이수 : "
									+ allTotal[0]);
							out.writeUTF("    - 정답 : " + success[0] + "개   |" + " 오답 : " + fail[0] + "개" + "           "
									+ "       - 총 정답수 : " + oTotal[0] + "개  |" + " 총 오답수 : " + xTotal[0] + "개");
							out.writeUTF("    - 정답률 : " + chapter1 + "%" + "        " + "          - 총 정답률 : "
									+ allChapter1 + "%");
							out.writeUTF("\n");
							out.writeUTF(" - [2과목. SQL 기본 및 활용]  ");
							out.writeUTF("    - 문제수 : " + examValue + "                " + "       - 총 문제풀이수 : "
									+ allTotal[1]);
							out.writeUTF("    - 정답 : " + success[1] + "개   |" + " 오답 : " + fail[1] + "개" + "           "
									+ "       - 총 정답수 : " + oTotal[1] + "개  |" + " 총 오답수 : " + xTotal[1] + "개");
							out.writeUTF("    - 정답률 : " + chapter2 + "%" + "        " + "          - 총 정답률 : "
									+ allChapter2 + "%");
							out.writeUTF(
									"=============================================================================");
							out.writeUTF("\n");

							while (true) {// 유효성 검사
								reList2 = new ArrayList<>();
								out.writeUTF(">> 틀린문제를 다시 푸시겠습니까????  [y/n 입력]  :  ");
								answer = in.readUTF();
								out.writeUTF("\n");
								if (answer.equals("y")) { // y 입력시 오답문제만 다시 풀 수 있도록 출력
									reList2 = reExam(in, out, reList1);
									reList1 = reList2;
								} else if (answer.equals("n")) { // n 입력시 모의고사 진행 여부를 물어본다.
									out.writeUTF(">> 다시 모의고사를 진행 하시겠습니까???  [y/n 입력]  :  ");
									answer = in.readUTF();
									out.writeUTF("\n");
									if (answer.equals("n")) { // mockTestAll 종료
										mainWhile = false;
										break;
									} else if (answer.equals("y")) { // mockTestAll 다시 시작
										break;
									} else {
										out.writeUTF("[Error] y또는 n을 입력해 주세요.");
									}
								} else {
									out.writeUTF("[Error] y또는 n을 입력해 주세요.");
								}
							}

						}

					} // if문 end

					DbConn.clearScreen(); // Enter키 입력시 80칸공백 method 호출

				} // SELECT for문 End
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			} // try End

		} // main while End

	}// mockTestAll method End

	public static List<ExamVO> reExam(DataInputStream in, DataOutputStream out, List<ExamVO> List) throws IOException {
		String answer;
		int success = 0;
		int fail = 0;
		List<ExamVO> reList = new ArrayList<>();
		for (ExamVO mvo : List) { // examValue만큼 문제를 보여준다.
			out.writeUTF("==============================================================");
			out.writeUTF(mvo.getQwestion());// 문제
			out.writeUTF("정답 입력 : ");
			answer = in.readUTF();
			while (true) { // 문자열 유효성 검사
				if (LogRegex.isiInt(answer)) {
					break;
				} else {
					out.writeUTF("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
					answer = in.readUTF();
				}
			}
			while (true) {
				if (Integer.parseInt(answer) <= 4 && Integer.parseInt(answer)>=1 && LogRegex.isiInt(answer) && answer != null) {
					break;
				} else {
						out.writeUTF("1 - 4 사이의 숫자만 입력 가능합니다. \n  >>> 다시입력하세요.");
						answer = in.readUTF();
				}
			}
			out.writeUTF("==============================================================");
			if (answer.equals(mvo.getAnswer())) {
				out.writeUTF(" /// 정답입니다 !!! ///\n");
				ExamDAO.insertOne(mvo, "o");
				success += 1;
			} else {
				out.writeUTF(" /// 오답입니다 !!! ///\n ");
				ExamDAO.insertOne(mvo, "x");
				reList.add(mvo); // 틀린 문제를 다시 풀 수 있도록 reList에 담는다.
				fail += 1;
			}
			out.writeUTF(mvo.getAnswerInfo() + "\n");
			out.writeUTF(">>Enter키를 눌러주시면 다음 문제로 넘어갑니다.");
			in.readUTF();
			DbConn.clearScreen(); // Enter키 입력시 80칸공백 method 호출
		}

		out.writeUTF(" - [오답문제 결과]  ");
		out.writeUTF("    - 문제수 : " + (success + fail));
		out.writeUTF("    - 정답 : " + success + "개   |" + " 오답 : " + fail + "개");
		out.writeUTF("\n");

		return reList;

	}// reExam method End

	public static List<ExamVO> dbSearch(int index, int exam) {

		List<ExamVO> dbSearch = new ArrayList<>();

		for (int i = 1; i <= 2; i++) { // SELECT FOR문 시작
			StringBuilder sql = new StringBuilder();
			if (DbConn.result == 0) {
				DbConn.driverLoad();
			}
			try {
				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				if (index == 2) { // SCORE_INFO 가져오는 거

					sql.append("SELECT ");
					sql.append("(Select count(*) From SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId()
							+ "' AND S_SECTION =  " + i + " )as total ");
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId()
							+ "' AND S_SECTION =  " + i + " AND O_X = 'o')as o_total ");
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId()
							+ "' AND S_SECTION =  " + i + " AND O_X = 'x')as x_total ");
					sql.append("FROM SCORE_INFO ");
					sql.append("WHERE ROWNUM = 1 ");

					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					DbConn.rs = DbConn.pstmt.executeQuery();

					while (DbConn.rs.next()) {
						dbSearch.add(new ExamVO(DbConn.rs.getString("total"), DbConn.rs.getString("o_total"),
								DbConn.rs.getString("x_total")));

					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			}

		} // SELECT for문 End

		return dbSearch;

	}// dbSearch method End
}
