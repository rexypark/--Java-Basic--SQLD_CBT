package com.mystudy.sqld_cbt;

import java.util.Scanner;

import dao.UserDAO;
import dao.UserLogDAO;
import vo.UserVO;

public class CBT_Main {
	private static String user_id = "";
	private static String user_name = "";
	private static String user_pw = "";
	private static String user_phone = "";  
	private static String user_email = "";
	private static Scanner scan = new Scanner(System.in);
	private static UserDAO userDao = new UserDAO();
	
	
	
	public static void main(String[] args) {
		
		while (true) {
			
			int choice = 0;
			
			System.out.println("SQLD CBT ���α׷�");
			System.out.println("SQLD_CBTȸ���̽ʴϱ�?");
			System.out.println("1. �α���   2. ȸ������");
			choice = scan.nextInt();
			if(choice == 1) {
				//���� ����
				scan.nextLine();
				//logIn
				logIn();
				break;
			} else if(choice == 2) {
				ConformSignIn();
			}
		}//while
	}//main
	
	//�α��� �޼���
	public static void logIn() {
		String user_id = "";
		String user_pw = "";
		
		Scanner scan = new Scanner(System.in);
		UserDAO userDao = new UserDAO();
		while (true) {
			System.out.println("�α���");
			System.out.print("ID �Է� >>");
			user_id = scan.nextLine();
			System.out.print("PW �Է� >>");
			user_pw = scan.nextLine();
			
			if(userDao.checkIdPassword(user_id, user_pw)) {
				System.out.println("log In �Ϸ�!");
				UserVO logUser = userDao.userInfo;
				
				//���̵�, �̸�, �α��� ����  USER_LOG���̺� insert
				UserLogDAO.userLog(logUser.getId(), logUser.getName(), "�α���");
				break;
			} else {
				System.out.println("log In ����!");
				System.out.println("�ٽ� �Է��ϼ���.");
			}
		}
	}
	
	//ȸ������ �޼���
	public static void ConformSignIn() {
		while (true) {
			System.out.println("ȸ������");
			while (true) {
				//���ۿ���
				scan.nextLine();
				
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
			
			boolean inUserData = userDao.signUp(user_id, user_name, user_pw, user_phone, user_email);
			
			//�Է��� ȸ�� ���� vo
			UserVO inUser =  userDao.userInfo;
			System.out.println(inUser.getName());
			//scan�� insert
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
