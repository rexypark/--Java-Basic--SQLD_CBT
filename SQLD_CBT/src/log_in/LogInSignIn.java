package log_in;

import java.util.Scanner;

import dao.DbConn;
import dao.UserDAO;
import dao.UserLogDAO;
import vo.UserVO;

public class LogInSignIn {

	private static String user_id = "";
	private static String user_name = "";
	private static String user_pw = "";
	private static String user_phone = "";  
	private static String user_email = "";
	private static Scanner scan = new Scanner(System.in);
	private static UserDAO userDao = new UserDAO();
	public static String exit = "exit";
	
	//�α��� �޼���
	public static void logIn() {
		String user_id = "";
		String user_pw = "";
		
		Scanner scan = new Scanner(System.in);
		UserDAO userDao = new UserDAO();
		DbConn.clearScreen();
		while (true) {
			System.out.println("         |================================|");
			System.out.println("         |    [SQLD CBT Program Login]    |         ");
			System.out.println("         |                                |         ");
			System.out.println("         |         ID : _________         |         ");
			System.out.print("                  ID �Է� : ");
			user_id = scan.nextLine();
			DbConn.clearScreen();
			System.out.println("         |================================|");
			System.out.println("         |    [SQLD CBT Program Login]    |         ");
			System.out.println("         |                                |         ");
			System.out.println("                  ID : " + user_id + "              ");
			System.out.println("                  PW : _________                  ");
			System.out.print("                 PW �Է� : ");
			user_pw = scan.nextLine();
			DbConn.clearScreen();
			System.out.println("         |================================|");
			System.out.println("         |    [SQLD CBT Program Login]    |         ");
			System.out.println("         |                                |         ");
			System.out.println("                  ID : " + user_id + "              ");
			System.out.println("                  PW : " + user_pw + "              ");
			
			// DB���� �ش� ���̵� ��й�ȣ�� �ִ��� Ȯ�� true�̸� 
			// logIn �����Ͽ� while�� break �ƴϸ� �����Ͽ� �α��� �ݺ�
			if(userDao.checkIdPassword(user_id, user_pw)) {
				System.out.println("         |                                |         ");
				System.out.println("         |        [log In Success]        |          ");
				System.out.println("         |================================|");
				UserVO logUser = userDao.userInfo;
				try {
					Thread.sleep(2000); // �α��� Successâ 2�� ������ �� �� ���� �������� �Ѿ��.)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//���̵�, �̸�, �α��� ����  USER_LOG���̺� insert
				UserLogDAO.userLog(logUser.getId(), logUser.getName(), "�α���");
				break;
			} else {
				//���н� while�� �ݺ�
				System.out.println("         |                                |         ");
				System.out.println("         |         [log In Fail]          |          ");
				System.out.println("         |================================|");
				try {
					Thread.sleep(2000); // �α��� Successâ 2�� ������ �� �� �ٽ� ID�� �����..)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					DbConn.clearScreen(); //â Ŭ����																		
				}
			}				
	}
		
	//ȸ������ �޼���
	public static void ConformSignIn() {
		while (true) {
			System.out.println("ȸ������");
			System.out.println("exit �Է� �� log in â���� ���ϴ�.");
			System.out.print("����� ID �Է� >>");
			user_id = scan.nextLine();
			if (exit.equals(user_id)) {
				break;
			}
			while (true) {
				if(LogRegex.isId(user_id) == 0 || (user_id.length() == 0)) {
					System.out.println("���̵� ������ ���� �ʽ��ϴ�.");
					System.out.println("�ٽ� �Է��� �ּ���.");
					System.out.print("����� ID �Է� >>");
					user_id = scan.nextLine();
					
				} else if (userDao.checkId(user_id)){
					System.out.println("�ߺ��� ���̵� �ֽ��ϴ�.");
					System.out.println("�ٽ� �Է��� �ּ���");
					System.out.print("����� ID �Է� >>");
					user_id = scan.nextLine();
				} else {
					break;
			    }
			}
			System.out.print("�ѱ۸� �Է� �����մϴ�.");
			System.out.print("����� �̸� �Է� >>");
			user_name = scan.nextLine();
			while (true) {
				if(LogRegex.isKor(user_name) == 0 || (user_name.length() == 0)) {
					System.out.println("�̸� ������ ���� �ʽ��ϴ�. [�ѱ� �Է�]");
					System.out.println("�ٽ� �Է��� �ּ���.");
					System.out.print("����� �̸� �Է� >>");
					user_name = scan.nextLine();
				} else {
					break;
			    }
			}
			
			
			System.out.println("����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�");
			System.out.print("����� ��й�ȣ �Է� >>");
			user_pw = scan.nextLine();
			while (true) {
				if(LogRegex.isPW(user_pw) == 0 || (user_pw.length() == 0)) {
					System.out.println("��й�ȣ ������ ���� �ʽ��ϴ�.");
					System.out.println("����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�");
					System.out.println("�ٽ� �Է��� �ּ���.");
					System.out.print("����� ��й�ȣ �Է� >>");
					user_pw = scan.nextLine();
				} else {
					break;
			    }
			}
			
			System.out.print("����� ��ȭ �Է� >>");
			user_phone = scan.nextLine();
			while (true) {
				if(LogRegex.isPhone(user_phone) == 0 || (user_phone.length() == 0)) {
					System.out.println("��ȭ��ȣ ������ ���� �ʽ��ϴ�.");
					System.out.println("010-1234-1234 / 01012341234 / 010.1234.1234");
					System.out.println("�ٽ� �Է��� �ּ���.");
					System.out.print("����� ��ȭ �Է� >>");
					user_phone = scan.nextLine();
				} else {
					break;
			    }
			}
			
			System.out.print("����� �̸��� �Է� >>");
			user_email = scan.nextLine();
			while (true) {
				if(LogRegex.isEmail(user_email) == 0 || (user_email.length() == 0)) {
					System.out.println("�̸��� ������ ���� �ʽ��ϴ�.");
					System.out.println("asdf12@asd.com");
					System.out.println("�ٽ� �Է��� �ּ���.");
					System.out.print("����� �̸��� �Է� >>");
					user_email = scan.nextLine();
				} else {
					break;
			    }
			}
			
			//signUp���� insert�Ϸ� �� true��ȯ
			boolean inUserData = userDao.signUp(user_id, user_name, user_pw, user_phone, user_email);
			
			//�Է��� ȸ�� ���� vo
			UserVO inUser =  userDao.userInfo;
			//scan�� insert�� �Ϸ��ϸ� ȸ������ �Ϸ�
			if(inUserData = true) {
				System.out.println("ȸ�������� �Ϸ� �Ǿ����ϴ�.");
				//���̵�, �̸�, �α��� ����  USER_LOG���̺� insert
				UserLogDAO.userLog(inUser.getId(), inUser.getName(), "����");
				break;
			} else {
				System.out.println("ȸ������ ���������� ���� �ʾҽ��ϴ�.");
				System.out.println("�ٽ� �Է����ּ���");
			}
		} //��ü while��
	 }
}