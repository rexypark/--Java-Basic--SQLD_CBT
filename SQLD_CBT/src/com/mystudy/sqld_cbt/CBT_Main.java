package com.mystudy.sqld_cbt;

import java.util.Scanner;

import dao.UserDAO;
import dao.UserLogDAO;
import log_in.LogInSignIn;
import vo.UserVO;

public class CBT_Main {
	
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
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
				LogInSignIn.logIn();
				break;
			} else if(choice == 2) {
				LogInSignIn.ConformSignIn();
			}
			scan.close();
		}
		while (true) {
			int choice;
			System.out.println("1. quiz   2. selfTest 3. exit");
			choice = scan.nextInt();
			if(choice == 1) {
//				quiz();
			} else if (choice == 2){
//			selfTest();
		} else {
			break;
		}
		
			System.out.println("�α׾ƿ� �Ǽ̽��ϴ�.");
//		while(
//		if(chioce ==1) {
//			quiz();
//		}
//		)
		//while
		//{
			
	//break;}
		//���� �� ��
		//� ���� Ǯ�� ���� â
		//quiz
		//���ǰ��
		//����
		//choice 1 2 3
		
		
	}//main
	
}
