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
			while (true) {
				System.out.print("����� ID �Է� >>");
				user_id = scan.nextLine();
				//UserDAO�� �ִ� checkId�޼��带 ���� ID �ߺ� �˻�
				if(userDao.checkId(user_id)) {
					System.out.println("�ٽ� �Է��� �ּ���");
				}else {
					break;
				}
			}
			System.out.print("����� �̸� �Է� >>");
			user_name = scan.nextLine();
			System.out.print("����� ��й�ȣ �Է� >>");
			user_pw = scan.nextLine();
			System.out.print("����� ��ȭ �Է� >>");
			user_phone = scan.nextLine();
			System.out.print("����� �̸��� �Է� >>");
			user_email = scan.nextLine();
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
			break;
		} //��ü while��
	 }
}
