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
import dao.JDBC_Close;

public class Quiz {
	
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	static{
		try {
			Class.forName(DRIVER);
			System.out.println(">> JDBC Driver Loading Success");
		} catch (ClassNotFoundException e) {
			System.out.println("[���ܹ߻�] JDBC Driver Loading Fail");
			
		}
	}
	
	
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input;
		while (true) {
			//1 
			System.out.println("Quiz�濡 �����ϼ̽��ϴ�.");
			System.out.println("���� >> Ű���� - sqld1 : ���� 1");
			System.out.println("����>> Ű���� - sqld2 : ���� 2");
			System.out.println("����>> Ű���� - sqld3 : ���� 3");
			System.out.println("�ش� Ű���带 �Է� �Ͻø� �ش� ������ ������ �����˴ϴ�.");
			
			while(true) {
				
				System.out.print(">>");
				input = scan.nextLine();
				switch (keyword(input)) {
					case 1 :
						System.out.println("1���� ����");
						break;
					case 2 :
						System.out.println("2���� ����");
						break;
					case 3 :
						System.out.println("3���� ����");
						break;
					default :
						break;
				}
				
				System.out.println("������¸޼ҵ�");
				
				System.out.println("������¸޼ҵ�");
				System.out.println("�ؼ���¸޼ҵ�");
				System.out.println("");
				 
			}
		}
	}//main
	
	public static void quesPrint() {
		
		List<ExamVO> list = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT QUESTION, ANSWER, SECTION, EXAM_SEQNUM");
			sql.append("  FROM EXAM_INFO ");//����
			sql.append(" ORDER BY QUESTION");
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			 list.add(new ExamVO(rs.getString("QUESTION"),
							     rs.getString("ANSWER"),
							     rs.getString("SECTION"),
							     rs.getString("EXAM_SEQNUM"), null));
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
}
