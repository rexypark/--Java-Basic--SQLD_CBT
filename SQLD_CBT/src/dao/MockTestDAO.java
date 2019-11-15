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
		
		int examValue; //���� ���� �Է� �޴� ����
		String answer; //���� ���� �Է� �޴� ����
		List<ExamVO> list = new ArrayList<>();
		boolean mainWhile = true;
		while(mainWhile) {//main while��
			try {
				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				System.out.println("|========================================================|");
				System.out.println("|               ȯ�� �մϴ� ���ǰ�縦 �����ϰڽ��ϴ�.             |");
				System.out.println("|           SQLD������ �� 2������ ����������� ���� �ֽ��ϴ�.        |");
				System.out.println("|--------------------------------------------------------|");
				System.out.println("|      1. ������ �𵨸��� ����    |     2. SQL �⺻ �� Ȱ��                  |");
				System.out.println("|--------------------------------------------------------|");
				System.out.print("   => �� ����� Ǯ ������ ���� �Է��� �ּ��� !!! : ");
				examValue = scan.nextInt();
				examValue += 1;
				scan.nextLine();
				int[] success = new int[3]; //���� ī��Ʈ�� �迭 ���� ����
				int[] fail = new int[3];    //���� ī��Ʈ�� �迭 ���� ����
				
				for (int i = 1; i <= 2 ; i++) {
					StringBuilder sql = new StringBuilder();
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					
					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					DbConn.rs = DbConn.pstmt.executeQuery();
				
					while (DbConn.rs.next()) {
						for (int j = 0; j <= examValue; j++ ) {
						 list.add(new ExamVO(DbConn.rs.getString("QUESTION"),
								 			 DbConn.rs.getString("ANSWER"),
								 			 DbConn.rs.getString("SECTION"),
								 			 DbConn.rs.getString("EXAM_SEQNUM"),
								 			 DbConn.rs.getString("ANSWER_INFO")));	
						}	
						break; //����ڰ� ���� ���� �� ��ŭ list�� add�ϰ� break;
					}
					
					for (ExamVO mvo : list ) {
						System.out.println("==============================================================");
						System.out.println(mvo.getQwestion());//����
						System.out.print("���� �Է� : ");	
						answer = scan.nextLine();
						
						System.out.println("==============================================================");
						if(answer.equals(mvo.getAnswer())) {
							System.out.println(" /// �����Դϴ� !!! ///\n");
							System.out.println(mvo.getAnswerInfo() + "\n");
							success[i] += 1;
							if (i == 2) {
								if (false == DbConn.rs.next()) {
									System.out.println("[1. ������ �𵨸��� ����]  �� ������ : " +examValue);
									System.out.println(" - ���� : " + success[1] + " ���� : " + fail[1] + "\t");
									System.out.println("[2. SQL �⺻ �� Ȱ��]    �� ������ : " +examValue);
									System.out.println(" - ���� : " + success[2] + " ���� : " + fail[2] + "\t");
									System.out.print(">>�ٽ� ���ǰ�縦 ���� �Ͻðڽ��ϱ�???  [y/n �Է�]  :  ");
									answer = scan.nextLine();
									if(answer.equals("n")) {
										mainWhile = false;
									}
									break;
								}
							}else {
								System.out.print(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
								scan.nextLine();
								break;
							}

						}else {
							System.out.println(" /// �����Դϴ� !!! ///\n ");
							System.out.println(mvo.getAnswerInfo() + "\n");
							fail[i] += 1;
							if (i == 2) {
								if (false == DbConn.rs.next()) {
									System.out.println("1. ������ �𵨸��� ����  ����� : " + success[1]);
									System.out.println("1. ������ �𵨸��� ����  ����� : " + fail[1]);
									System.out.println("2. SQL �⺻ �� Ȱ�� �����      : " + success[2]);
									System.out.println("2. SQL �⺻ �� Ȱ�� �����      : " + fail[2]);
									System.out.print(">>�ٽ� ���ǰ�縦 ���� �Ͻðڽ��ϱ�???  [y/n �Է�]  :  ");
									answer = scan.nextLine();
									if(answer.equals("n")) {
										mainWhile = false;
									}
									break;
								}
							}else {
								System.out.print(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
								scan.nextLine();
								break;
							}
						}
												
					} //for�� End
					for (int k = 0; k < 80; k++) {
						System.out.println("");
					}// clearScreen End
										
				}// for�� End
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			}
	
		}//main while End
		
	}//mockTestAll End
	
	public static void main(String[] args) {
		MockTestDAO dao = new MockTestDAO();
		dao.mockTestAll();
	}

	
}
