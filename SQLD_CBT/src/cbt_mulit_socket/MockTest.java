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

import cbt_mulit_socket.CBTServer.ServerReceiver;
import dao.JDBCConn;
import dao.ExamDAO;
import dao.UserDAO;
import log_in.LogRegex;
import vo.ExamVO;

public class MockTest {

	static Scanner scan = new Scanner(System.in);

	public static void mockTestAll(DataInputStream in, DataOutputStream out, String id) throws IOException {

		JDBCConn.clearScreen(in,out); // ȭ�� Clear
		String examValue; // ���� ���� �Է� �޴� ����
		int examCount; // ���α׷� ���Ḧ ���� ī��Ʈ ����
		String answer; // ���� ���� �Է� �޴� ����
		List<ExamVO> list = new ArrayList<>();
		List<ExamVO> reList1 = new ArrayList<>();
		List<ExamVO> reList2 = new ArrayList<>();
		List<ExamVO> scoreList = new ArrayList<>();

		boolean mainWhile = true;
		while (mainWhile) {// main while��
			try {
				out.writeUTF("|=======================================================================|");
				out.writeUTF("|                                                                       |");
				out.writeUTF("|                                [ȯ�� �մϴ�]                             |");
				out.writeUTF("|                      [" + id
						+ "]�� SQLD ���ǰ��縦 �����ϰڽ��ϴ�                                 |");
				out.writeUTF("|                                                                       |");
				out.writeUTF("|                ���� �ð��� �� 90���̸� �Ʒ��� ���� ������ �����ȴ�.                 |");
				out.writeUTF("|      (1����) ������ �𵨸��� ���� : 10����(���״� 2��) -> �� 20�� ( 8�� �̸� ����)    |");
				out.writeUTF("|      (2����) SQL �⺻ �� Ȱ��     : 40����(���״� 2��) -> �� 80�� (32�� �̸� ����)    |");
				out.writeUTF("|    �� ���񺰷� �ּ� 40% �̻� ���� ���� �ÿ� ������ �Ǹ� �� ������ '60��' �̻��̸� �հ��̴�     |");
				out.writeUTF("|                                                                       |");
				out.writeUTF("|-----------------------------------------------------------------------|");
				out.writeUTF("|         1. ������ �𵨸��� ����                    |         2. SQL �⺻ �� Ȱ��                  |");
				out.writeUTF("|-----------------------------------------------------------------------|");

				out.writeUTF("   => �� ����� Ǯ ������ ���� �Է��� �ּ��� !!! : ");
				long startTime = System.currentTimeMillis(); // ���ǰ��� ���� �ð� Check
				examValue = in.readUTF();
				while (true) { // ���ڿ� ��ȿ�� �˻�
					if (LogRegex.isiInt(examValue)) {
						break;
					} else {
						out.writeUTF("[Error] ���ڸ� �Է� �����մϴ�  \n  >>> �ٽ� �Է��� �ּ��� : ");
						examValue = in.readUTF();
					}
				}
				examCount = Integer.parseInt(examValue) * 2;
				JDBCConn.clearScreen(in,out); // ȭ�� Clear
				int[] success = new int[2]; // ���� ī��Ʈ�� �迭 ���� ����
				int[] fail = new int[2]; // ���� ī��Ʈ�� �迭 ���� ����
				reList1 = new ArrayList<>();
				for (int i = 1; i <= 2; i++) { // SELECT FOR�� ����
					StringBuilder sql = new StringBuilder();
					if (JDBCConn.result == 0) {
						JDBCConn.driverLoad();
					}
					JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					sql.append(" ORDER BY DBMS_RANDOM.VALUE"); // �������� �����͸� ���� �´�.

					JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
					JDBCConn.rs = JDBCConn.pstmt.executeQuery();

					for (int j = 0; j < Integer.parseInt(examValue); j++) {
						while (JDBCConn.rs.next()) {

							list.add(new ExamVO(JDBCConn.rs.getString("QUESTION"), JDBCConn.rs.getString("ANSWER"),
									JDBCConn.rs.getString("SECTION"), JDBCConn.rs.getString("EXAM_SEQNUM"),
									JDBCConn.rs.getString("ANSWER_INFO")));
							break;
						}
					}
					
					for (ExamVO mvo : list) { // examValue��ŭ ������ �����ش�.
						out.writeUTF("==============================================================");
						out.writeUTF(mvo.getQwestion());// ����
						out.writeUTF("���� �Է� : ");
						answer = in.readUTF();
						
						CBTServer.ServerReceiver.answerCheck(in, out, answer, 1, 4);
						
						out.writeUTF("==============================================================");
						if (answer.equals(mvo.getAnswer())) {
							out.writeUTF(" /// �����Դϴ� !!! ///\n");
							ExamDAO.insertOne(mvo, "o");
							success[i - 1] += 1;
						} else {
							out.writeUTF(" /// �����Դϴ� !!! ///\n ");
							ExamDAO.insertOne(mvo, "x");
							fail[i - 1] += 1;
							reList1.add(mvo); // Ʋ�� ������ �ٽ� Ǯ �� �ֵ��� reList�� ��´�.
						}
						examCount--;
						out.writeUTF(mvo.getAnswerInfo() + "\n");
						out.writeUTF(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
						in.readUTF();
						JDBCConn.clearScreen(in,out); // EnterŰ �Է½� 80ĭ���� method ȣ��

					} // for�� End
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
							out.writeUTF(UserDAO.userInfo.getId() + "���� ���ǰ��� ������  ");
							out.writeUTF(" - �� ����ð� : " + (endTime - startTime) / 1000.0 + "��");
							out.writeUTF(" - [1����. ������ �𵨸��� ����]  ");
							out.writeUTF("    - ������ : " + examValue + "                " + "       - �� ����Ǯ�̼� : "
									+ allTotal[0]);
							out.writeUTF("    - ���� : " + success[0] + "��   |" + " ���� : " + fail[0] + "��" + "           "
									+ "       - �� ����� : " + oTotal[0] + "��  |" + " �� ����� : " + xTotal[0] + "��");
							out.writeUTF("    - ����� : " + chapter1 + "%" + "        " + "          - �� ����� : "
									+ allChapter1 + "%");
							out.writeUTF("\n");
							out.writeUTF(" - [2����. SQL �⺻ �� Ȱ��]  ");
							out.writeUTF("    - ������ : " + examValue + "                " + "       - �� ����Ǯ�̼� : "
									+ allTotal[1]);
							out.writeUTF("    - ���� : " + success[1] + "��   |" + " ���� : " + fail[1] + "��" + "           "
									+ "       - �� ����� : " + oTotal[1] + "��  |" + " �� ����� : " + xTotal[1] + "��");
							out.writeUTF("    - ����� : " + chapter2 + "%" + "        " + "          - �� ����� : "
									+ allChapter2 + "%");
							out.writeUTF(
									"=============================================================================");
							out.writeUTF("\n");

							while (true) {// ��ȿ�� �˻�
								reList2 = new ArrayList<>();
								out.writeUTF(">> Ʋ�������� �ٽ� Ǫ�ðڽ��ϱ�????  [y/n �Է�]  :  ");
								answer = in.readUTF();
								out.writeUTF("\n");
								if (answer.equals("y")) { // y �Է½� ���乮���� �ٽ� Ǯ �� �ֵ��� ���
									reList2 = reExam(in, out, reList1);
									reList1 = reList2;
								} else if (answer.equals("n")) { // n �Է½� ���ǰ��� ���� ���θ� �����.
									out.writeUTF(">> �ٽ� ���ǰ��縦 ���� �Ͻðڽ��ϱ�???  [y/n �Է�]  :  ");
									answer = in.readUTF();
									out.writeUTF("\n");
									if (answer.equals("n")) { // mockTestAll ����
										mainWhile = false;
										break;
									} else if (answer.equals("y")) { // mockTestAll �ٽ� ����
										break;
									} else {
										out.writeUTF("[Error] y�Ǵ� n�� �Է��� �ּ���.");
									}
								} else {
									out.writeUTF("[Error] y�Ǵ� n�� �Է��� �ּ���.");
								}
							}

						}

					} // if�� end

					JDBCConn.clearScreen(in,out); // EnterŰ �Է½� 80ĭ���� method ȣ��

				} // SELECT for�� End
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
			} // try End

		} // main while End

	}// mockTestAll method End

	public static List<ExamVO> reExam(DataInputStream in, DataOutputStream out, List<ExamVO> List) throws IOException {
		String answer;
		int success = 0;
		int fail = 0;
		List<ExamVO> reList = new ArrayList<>();
		for (ExamVO mvo : List) { // examValue��ŭ ������ �����ش�.
			out.writeUTF("==============================================================");
			out.writeUTF(mvo.getQwestion());// ����
			out.writeUTF("���� �Է� : ");
			answer = in.readUTF();
			
			CBTServer.ServerReceiver.answerCheck(in, out, answer, 1, 4);

			out.writeUTF("==============================================================");
			if (answer.equals(mvo.getAnswer())) {
				out.writeUTF(" /// �����Դϴ� !!! ///\n");
				ExamDAO.insertOne(mvo, "o");
				success += 1;
			} else {
				out.writeUTF(" /// �����Դϴ� !!! ///\n ");
				ExamDAO.insertOne(mvo, "x");
				reList.add(mvo); // Ʋ�� ������ �ٽ� Ǯ �� �ֵ��� reList�� ��´�.
				fail += 1;
			}
			out.writeUTF(mvo.getAnswerInfo() + "\n");
			out.writeUTF(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
			in.readUTF();
			JDBCConn.clearScreen(in,out); // EnterŰ �Է½� 80ĭ���� method ȣ��
		}

		out.writeUTF(" - [���乮�� ���]  ");
		out.writeUTF("    - ������ : " + (success + fail));
		out.writeUTF("    - ���� : " + success + "��   |" + " ���� : " + fail + "��");
		out.writeUTF("\n");

		return reList;

	}// reExam method End

	public static List<ExamVO> dbSearch(int index, int exam) {

		List<ExamVO> dbSearch = new ArrayList<>();

		for (int i = 1; i <= 2; i++) { // SELECT FOR�� ����
			StringBuilder sql = new StringBuilder();
			if (JDBCConn.result == 0) {
				JDBCConn.driverLoad();
			}
			try {
				JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
				if (index == 2) { // SCORE_INFO �������� ��

					sql.append("SELECT ");
					sql.append("(Select count(*) From SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId()
							+ "' AND S_SECTION =  " + i + " )as total ");
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId()
							+ "' AND S_SECTION =  " + i + " AND O_X = 'o')as o_total ");
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId()
							+ "' AND S_SECTION =  " + i + " AND O_X = 'x')as x_total ");
					sql.append("FROM SCORE_INFO ");
					sql.append("WHERE ROWNUM = 1 ");

					JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
					JDBCConn.rs = JDBCConn.pstmt.executeQuery();

					while (JDBCConn.rs.next()) {
						dbSearch.add(new ExamVO(JDBCConn.rs.getString("total"), JDBCConn.rs.getString("o_total"),
								JDBCConn.rs.getString("x_total")));

					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
			}

		} // SELECT for�� End

		return dbSearch;

	}// dbSearch method End
	
}