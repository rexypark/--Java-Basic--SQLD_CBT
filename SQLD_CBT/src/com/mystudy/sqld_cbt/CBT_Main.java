package com.mystudy.sqld_cbt;

import java.util.Scanner;

import dao.MockTestDAO;
import dao.UserDAO;
import dao.UserLogDAO;
import exam.Quiz;
import log_in.LogInSignIn;
import vo.UserVO;

public class CBT_Main {

	public static void main(String[] args) {
		

		while (true) {
			
			while (true) {
				Scanner scan = new Scanner(System.in);
				int choice = 0;
				System.out.println("|=======================================================================|");
				System.out.println("|                                                                (v1.0) |");
				System.out.println("|                                [ȯ�� �մϴ�]                             |");
				System.out.println("|                        SQLD ���⹮�� CBT ���α׷��Դϴ�.                    |");
				System.out.println("|                                                                       |");
				System.out.println("|                Kdata(�ѱ������� �����)���� �ְ��ϴ� ������ SQLD��                              |");
				System.out.println("|          SQL(Structured Query Language) + D(Developer)�� ���Ӹ���                |");
				System.out.println(
						"|                    �����ͺ��̽� SQL �������� �ڰ��� �Դϴ�                                           |");
				System.out.println("|-----------------------------------------------------------------------|");
				System.out.println(
						"|        1. �α���                 |      2. ȸ������           |      3. ���α׷� ����            |");
				System.out.println("|-----------------------------------------------------------------------|");
				System.out.print("   => ��ȣ�� �Է��� �ּ��� !!! : ");
				
				choice = scan.nextInt();

				if (choice == 1) {

					// ���� ����
					scan.nextLine();
					// logIn
					LogInSignIn.logIn();
					while (true) {
						System.out.println("1. quiz   2. selfTest 3. exit");
						choice = scan.nextInt();
						if (choice == 1) {
							Quiz.quizStart();
						} else if (choice == 2) {
							MockTestDAO.mockTestAll();
						} else {
							break;
						}
					} // while
				} else if (choice == 2) {
					// ���� ����
					scan.nextLine();

					LogInSignIn.ConformSignIn();
					//exit�Է� �� ù��° â���� �̵� 
					if(LogInSignIn.exit.equals("exit")) {
						break;
					}
					LogInSignIn.logIn();
					while (true) {
						int choice2;
						System.out.println("1. quiz   2. selfTest 3. exit");
						choice2 = scan.nextInt();
						if (choice2 == 1) {
							Quiz.quizStart();
						} else if (choice2 == 2) {
							MockTestDAO.mockTestAll();
						} else {
							break;
						}
					} // while
				}
				if (choice == 3) {
					System.out.println("�α׾ƿ� �Ǽ̽��ϴ�.");
					break;
				}
				scan.close();
			}
			
		}
	}// main
}
