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
		List<ExamVO> reList1 = new ArrayList<>();
		List<ExamVO> reList2 = new ArrayList<>();
		List<ExamVO> scoreList = new ArrayList<>();
		
		boolean mainWhile = true;
		while(mainWhile) {//main while��
			try {
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
				reList1 = new ArrayList<>();
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
							 reList1.add(mvo); //Ʋ�� ������ �ٽ� Ǯ �� �ֵ��� reList�� ��´�.
						 }
						 examCount--;
						 System.out.println(mvo.getAnswerInfo() + "\n");
						 System.out.print(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
						 scan.nextLine();
						 DbConn.clearScreen(); //EnterŰ �Է½� 80ĭ���� method ȣ��

					 } //for�� End
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
							 System.out.println(UserDAO.userInfo.getId() + "���� ���ǰ�� ������  ");
							 System.out.println(" - �� ����ð� : " + ( endTime - startTime )/1000.0 +"��");
							 System.out.println(" - [1����. ������ �𵨸��� ����]  ");
							 System.out.println("    - ������ : " + examValue +         "                " + "   - �� ����Ǯ�̼� : " + allTotal[0] );
							 System.out.println("    - ���� : " + success[0] + "��   |" + " ���� : " + fail[0] + "��"+ 
							                    "           " + "   - �� ����� : " + oTotal[0] + "��  |" + " �� ����� : " + xTotal[0] + "��" );
							 System.out.println("    - ����� : " + chapter1 + "%"+         "        " + "      - �� ����� : " + allChapter1 + "%" );
							 System.out.println();
							 System.out.println(" - [2����. SQL �⺻ �� Ȱ��]  ");
							 System.out.println("    - ������ : " + examValue +         "                " + "   - �� ����Ǯ�̼� : " + allTotal[1] );
							 System.out.println("    - ���� : " + success[1] + "��   |" + " ���� : " + fail[1] + "��"+ 
							                    "           " + "   - �� ����� : " + oTotal[1] + "��  |" + " �� ����� : " + xTotal[1] + "��" );
							 System.out.println("    - ����� : " + chapter2 + "%"+         "        " + "      - �� ����� : " + allChapter2 + "%" );
							 System.out.println("=============================================================");
							 System.out.println();
					 
							 
							 while(true) {//��ȿ�� �˻�
								 reList2 = new ArrayList<>();
								 System.out.print(">> Ʋ�������� �ٽ� Ǫ�ðڽ��ϱ�????  [y/n �Է�]  :  ");
								 answer = scan.nextLine();
								 System.out.println();
								 if(answer.equals("y")) { //y �Է½� ���乮���� �ٽ� Ǯ �� �ֵ��� ���
									reList2 = reExam(reList1);
									reList1 = reList2;
								 } else if(answer.equals("n")) { //n �Է½� ���ǰ�� ���� ���θ� �����.
									 System.out.print(">> �ٽ� ���ǰ�縦 ���� �Ͻðڽ��ϱ�???  [y/n �Է�]  :  ");
									 answer = scan.nextLine();
									 System.out.println();
									 if(answer.equals("n")) { //mockTestAll ����
										 mainWhile = false; 
										 break;
									 } else if(answer.equals("y")) { //mockTestAll �ٽ� ����
										 break;
									 } else {
										 System.out.println("[Error] y�Ǵ� n�� �Է��� �ּ���."); 
									 }
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
		
	}//mockTestAll method End
	
	public static List<ExamVO> reExam(List<ExamVO> List) {
		String answer;
		int success = 0;
		int fail = 0;
		List<ExamVO> reList = new ArrayList<>();
		for (ExamVO mvo : List ) { // examValue��ŭ ������ �����ش�.  
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
			      success += 1;
			 }else {
				 System.out.println(" /// �����Դϴ� !!! ///\n ");
			      ExamDAO.insertOne(mvo ,"x");
			      reList.add(mvo); //Ʋ�� ������ �ٽ� Ǯ �� �ֵ��� reList�� ��´�.
			      fail += 1;
			 }
			 System.out.println(mvo.getAnswerInfo() + "\n");
			 System.out.print(">>EnterŰ�� �����ֽø� ���� ������ �Ѿ�ϴ�.");
			 scan.nextLine();
			 DbConn.clearScreen(); //EnterŰ �Է½� 80ĭ���� method ȣ�� 
		}
		
		 System.out.println(" - [���乮�� ���]  ");
		 System.out.println("    - ������ : " + (success + fail));
		 System.out.println("    - ���� : " + success + "��   |" + " ���� : " + fail + "��");
		 System.out.println();
		 
		 return reList;
		
	}// reExam method End
	
	
	public static List<ExamVO> dbSearch(int index, int exam) {
		
		List<ExamVO> dbSearch = new ArrayList<>();
		
		for (int i = 1; i <= 2 ; i++) { //SELECT FOR�� ����
			StringBuilder sql = new StringBuilder();
			if (DbConn.result == 0) {
				DbConn.driverLoad();
			}
			try {
				DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
				if(index ==2) { // SCORE_INFO �������� ��
					
					sql.append("SELECT ");
					sql.append("(Select count(*) From SCORE_INFO WHERE S_USER_ID = '" + UserDAO.userInfo.getId() +"' AND S_SECTION =  " + i + " )as total ");
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '"+ UserDAO.userInfo.getId() +"' AND S_SECTION =  " + i + " AND O_X = 'o')as o_total " );
					sql.append(",(select count(*) FROM SCORE_INFO WHERE S_USER_ID = '"+ UserDAO.userInfo.getId() +"' AND S_SECTION =  " + i + " AND O_X = 'x')as x_total ");
					sql.append("FROM SCORE_INFO ");
					sql.append("WHERE ROWNUM = 1 ");
					
					
					DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
					DbConn.rs = DbConn.pstmt.executeQuery();
					System.out.println(i + " rs ����");
					while (DbConn.rs.next()) {
						dbSearch.add(new ExamVO(DbConn.rs.getString("total"),
												DbConn.rs.getString("o_total"),
												DbConn.rs.getString("x_total")));
					}
					System.out.println(i + " dbSearch.add ����");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			}
						
			
		}//SELECT for�� End
		
		return dbSearch;
	
	}//dbSearch method End
			
}
