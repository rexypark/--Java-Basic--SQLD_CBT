package cbt_mulit_socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import dao.DbConn;
import dao.UserDAO;
import dao.UserLogDAO;
import log_in.LogRegex;
import vo.UserVO;

public class ServerVO {
	public static String user_id;
	public static String user_pw;
	public static String user_email;
	public static String user_name;
	public static String choice;
	public static String user_phone;
	public static int exit = 0;

	
	//�ش� ������ dis�� dos�� �޾� �並 ����ϰ�
	//���̵�� ��й�ȣ�� �޾� �α����� ó���ϴ� �޼ҵ�
	public static void login(DataInputStream in, DataOutputStream out) {
		UserDAO userDao = new UserDAO();
		while (true) {
			try {
				out.writeUTF("         |================================|");
				out.writeUTF("         |    [SQLD CBT Program Login]    |         ");
				out.writeUTF("         |                                |         ");
				out.writeUTF("         |         ID : _________         |         ");
				out.writeUTF("                  ID �Է� : ");
				user_id = in.readUTF();

				if (user_id.equals("exit")) {
					exit = 1;
					break;
				}

				DbConn.clearScreen();
				out.writeUTF("         |================================|");
				out.writeUTF("         |    [SQLD CBT Program Login]    |         ");
				out.writeUTF("         |                                |         ");
				out.writeUTF("                  ID : " + user_id + "              ");
				out.writeUTF("                  PW : _________                  ");
				out.writeUTF("                 PW �Է� : ");
				user_pw = in.readUTF();
				if (user_pw.equals("exit")) {
					exit = 1;
					break;
				}
				DbConn.clearScreen();
				out.writeUTF("         |================================|");
				out.writeUTF("         |    [SQLD CBT Program Login]    |         ");
				out.writeUTF("         |                                |         ");
				out.writeUTF("                  ID : " + user_id + "              ");
				out.writeUTF("                  PW : " + user_pw + "              ");

				if (userDao.checkIdPassword(user_id, user_pw)) {
					out.writeUTF("         |                                |         ");
					out.writeUTF("         |        [log In Success]        |         ");
					out.writeUTF("         |================================|");
					UserVO logUser = userDao.userInfo;
					try {
						Thread.sleep(2000); // �α��� Successâ 2�� ������ �� �� ���� �������� �Ѿ��.)
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// ���̵�, �̸�, �α��� ���� USER_LOG���̺� insert
					UserLogDAO.userLog(logUser.getId(), logUser.getName(), "�α���");
					break;
				} else {
					// ���н� while�� �ݺ�
					out.writeUTF("         |                                |         \n");
					out.writeUTF("         |         [log In Fail]          |         \n");
					out.writeUTF("         |================================|\n");
					try {
						Thread.sleep(2000); // �α��� Successâ 2�� ������ �� �� �ٽ� ID�� �����..)
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// DbConn.clearScreen(); // â Ŭ����
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	//�ش� ������ dis�� dos�� �޾� �並 ����ϰ�
	//���̵�� ��й�ȣ�� �޾� ȸ�������� ó���ϴ� �޼ҵ�
	public static void signIn(DataInputStream in, DataOutputStream out) {
		while (true) {
			try {
				out.writeUTF("===================================");
				out.writeUTF("               ȸ������                            ");
				out.writeUTF("-----------------------------------");
				out.writeUTF("   exit �Է½� �ʱ� ȭ�� â���� ���ư��ϴ�     ");
				out.writeUTF("                                   ");
				out.writeUTF(" - ����Ͻ� ID �Է� >>");
				user_id = in.readUTF();
				if (user_id.equals("exit")) {
					exit = 1;
					break;
				}
				while (true) {
					if (LogRegex.isId(user_id) == 0 || (user_id.length() == 0)) {
						out.writeUTF("���̵� ������ ���� �ʽ��ϴ�.");
						out.writeUTF("�ٽ� �Է��� �ּ���.");
						out.writeUTF("����� ID �Է� >>");
						user_id = in.readUTF();
					} else if (UserDAO.checkId(user_id)) {
						out.writeUTF("�ߺ��� ���̵� �ֽ��ϴ�.");
						out.writeUTF("�ٽ� �Է��� �ּ���");
						out.writeUTF("����� ID �Է� >>");
						user_id = in.readUTF();
					} else {
						break;
					}
				}
				out.writeUTF("�ѱ۸� �Է� �����մϴ�.");
				out.writeUTF("����� �̸� �Է� >>");
				user_name = in.readUTF();
				if (user_name.equals("exit")) {
					exit = 1;
					break;
				}
				while (true) {
					if (LogRegex.isKor(user_name) == 0 || (user_name.length() == 0)) {
						out.writeUTF("�̸� ������ ���� �ʽ��ϴ�. [�ѱ� �Է�]");
						out.writeUTF("�ٽ� �Է��� �ּ���.");
						out.writeUTF("����� �̸� �Է� >>");
						user_name = in.readUTF();
					} else {
						break;
					}
				}
				out.writeUTF("����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�");
				out.writeUTF("����� ��й�ȣ �Է� >>");
				user_pw = in.readUTF();
				if (user_pw.equals("exit")) {
					exit = 1;
					break;
				}
				while (true) {
					if (LogRegex.isPW(user_pw) == 0 || (user_pw.length() == 0)) {
						out.writeUTF("��й�ȣ ������ ���� �ʽ��ϴ�.");
						out.writeUTF("����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�");
						out.writeUTF("�ٽ� �Է��� �ּ���.");
						out.writeUTF("����� ��й�ȣ �Է� >>");
						user_pw = in.readUTF();
					} else {
						break;
					}
				}

				out.writeUTF("����� ��ȭ �Է� >>");
				user_phone = in.readUTF();
				if (user_phone.equals("exit")) {
					exit = 1;
					break;
				}
				while (true) {
					if (LogRegex.isPhone(user_phone) == 0 || (user_phone.length() == 0)) {
						out.writeUTF("��ȭ��ȣ ������ ���� �ʽ��ϴ�.");
						out.writeUTF("010-1234-1234 / 01012341234 / 010.1234.1234");
						out.writeUTF("�ٽ� �Է��� �ּ���.");
						out.writeUTF("����� ��ȭ �Է� >>");
						user_phone = in.readUTF();
					} else {
						break;
					}
				}

				out.writeUTF("����� �̸��� �Է� >>");
				user_email = in.readUTF();
				if (user_email.equals("exit")) {
					exit = 1;
					break;
				}
				while (true) {
					if (LogRegex.isEmail(user_email) == 0 || (user_email.length() == 0)) {
						out.writeUTF("�̸��� ������ ���� �ʽ��ϴ�.");
						out.writeUTF("asdf12@asd.com");
						out.writeUTF("�ٽ� �Է��� �ּ���.");
						out.writeUTF("����� �̸��� �Է� >>");
						user_email = in.readUTF();
					} else {
						break;
					}
				}

				// signUp���� insert�Ϸ� �� true��ȯ
				boolean inUserData = UserDAO.signUp(user_id, user_name, user_pw, user_phone, user_email);

				// �Է��� ȸ�� ���� vo
				UserVO inUser = UserDAO.userInfo;
				// scan�� insert�� �Ϸ��ϸ� ȸ������ �Ϸ�
				if (inUserData = true) {
					out.writeUTF("ȸ�������� �Ϸ� �Ǿ����ϴ�.");
					// ���̵�, �̸�, �α��� ���� USER_LOG���̺� insert
					UserLogDAO.userLog(inUser.getId(), inUser.getName(), "����");
					break;
				} else {
					out.writeUTF("ȸ������ ���������� ���� �ʾҽ��ϴ�.");
					out.writeUTF("�ٽ� �Է����ּ���");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}