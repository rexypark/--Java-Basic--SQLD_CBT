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
		
		

		DbConn.clearScreen(); // ȭ�� Clear
		int examValue; //���� ���� �Է� �޴� ����
		int examCount; //���α׷� ���Ḧ ���� ī��Ʈ ����
		String answer; //���� ���� �Է� �޴� ����
		List<ExamVO> list = new ArrayList<>();
		
		boolean mainWhile = true;
		while(mainWhile) {//main while��
			try {

//				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				System.out.println("|=======================================================================|");
				System.out.println("|                                                                       |");
				System.out.println("|                                [ȯ�� �մϴ�]                             |");
				System.out.println("|                      [" + UserDAO.userInfo.getId() + "]�� SQLD ���ǰ�縦 �����ϰڽ��ϴ�                                 |");
				System.out.println("|                                                                       |");
				System.out.println("|                ���� �ð��� �� 90���̸� �Ʒ��� ���� ������ �����ȴ�.                 |");
				System.out.println("|      (1����) ������ �𵨸��� ���� : 10����(���״� 2��) -> �� 20�� ( 8�� �̸� ����)    |");
				System.out.println("|      (2����) SQL �⺻ �� Ȱ��     : 40����(���״� 2��) -> �� 80�� (32�� �̸� ����)    |");
				System.out.println("|    �� ���񺰷� �ּ� 40% �̻� ���� ���� �ÿ� ������ �Ǹ� �� ������ '60��' �̻��̸� �հ��̴�     |");
				System.out.println("|                                                                       |");
				System.out.println("|-----------------------------------------------------------------------|");
				System.out.println("|         1. ������ �𵨸��� ����                    |         2. SQL �⺻ �� Ȱ��                  |");
				System.out.println("|-----------------------------------------------------------------------|");
				
				System.out.print("   => �� ����� Ǯ ������ ���� �Է��� �ּ��� !!! : ");
				long startTime = System.currentTimeMillis(); //���ǰ�� ���� �ð� Check
				
				while (!scan.hasNextInt()) { //���ڿ� ��ȿ�� �˻�
	                scan.next();
	                System.err.print("[Error] ���ڸ� �Է� �����մϴ�  \n  >>> �ٽ� �Է��� �ּ��� : ");
				}

				examValue = scan.nextInt();
				scan.nextLine();
				examCount = examValue * 2;
				DbConn.clearScreen(); // ȭ�� Clear
				int[] success = new int[2]; //���� ī��Ʈ�� �迭 ���� ����
				int[] fail = new int[2];    //���� ī��Ʈ�� �迭 ���� ����
				
				for (int i = 1; i <= 2 ; i++) { //SELECT FOR�� ����
					StringBuilder sql = new StringBuilder();
					if (DbConn.result == 0) {
						DbConn.driverLoad();
					}
					DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
					list = new ArrayList<>();
					sql.append("SELECT * ");
					sql.append("FROM EXAM_INFO ");
					sql.append("WHERE SECTION = " + i);
					sql.append(" ORDER BY DBMS_RANDOM.VALUE"); //�������� �����͸� ���� �´�.
					
					System.out.println("i�� �� : " + i);
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
					
				 	for (ExamVO mvo : list ) { // examValue��ŭ ������ �����ش�.  
						 System.out.println("==============================================================");
						 System.out.println(mvo.getQwestion());//����
						 System.out.print("���� �Է� : ");	
						while (!scan.hasNextInt()) { //���ڿ� ��ȿ�� �˻�
			                scan.next();
			                System.err.print("[Error] ���ڸ� �Է� �����մϴ�  \n  >>> �ٽ� �Է��� �ּ��� : ");
						}
						 answer = scan.nextLine();
						 
						 System.out.println("==============================================================");
						 if(answer.equals(mvo.getAnswer())) {
							 System.out.println(" /// �����Դϴ� !!! ///\n");
						      ExamDAO.insertOne(mvo ,"o");
							 success[i - 1] += 1;
						 }else {
							 System.out.println(" /// �����Դϴ� !!! ///\n ");
						      ExamDAO.insertOne(mvo ,"x");
							 fail[i - 1] += 1;
						 }
						 examCount--;
						 System.out.println(mvo.getAnswerInfo() + "\n");
						 System.out.print(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
						 scan.nextLine();
						 DbConn.clearScreen(); //EnterŰ �Է½� 80ĭ���� method ȣ��

					 } //for�� End
					 if (i == 2) {
						 if (examCount == 0) {
							 System.out.println(UserDAO.userInfo.getId());
							 long endTime = System.currentTimeMillis();
							 System.out.println( "���ǰ�� ����ð� : " + ( endTime - startTime )/1000.0 +"��");

							 System.out.println("[1. ������ �𵨸��� ����]  �� ������ : " +examValue);
							 System.out.println(" - ���� : " + success[0] + " ���� : " + fail[0] + "\t");
							 System.out.println("[2. SQL �⺻ �� Ȱ��]    �� ������ : " +examValue);
							 System.out.println(" - ���� : " + success[1] + " ���� : " + fail[1] + "\t");
							 
							 while(true) {//��ȿ�� �˻�
								 System.out.print(">>�ٽ� ���ǰ�縦 ���� �Ͻðڽ��ϱ�???  [y/n �Է�]  :  ");
								 answer = scan.nextLine();
								 if(answer.equals("n")) {
									 mainWhile = false; //MockTestDAO ����
									 break;
								 } else if(answer.equals("y")) {
									 break;
								 } else {
									 System.out.println("[Error] y�Ǵ� n�� �Է��� �ּ���."); 
								 }
							 }
			 
						 }
						 
					 }//if�� end
					 
					 DbConn.clearScreen(); //EnterŰ �Է½� 80ĭ���� method ȣ��
					 
				}// SELECT for�� End
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			}//try End
	
		}//main while End
		
	}//mockTestAll End

}
