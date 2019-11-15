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
			System.out.println("SQLD CBT 프로그램");
			System.out.println("SQLD_CBT회원이십니까?");
			System.out.println("1. 로그인   2. 회원가입 3. 종료");
			choice = scan.nextInt();
			
			if(choice == 1) {
				//버퍼 제거
				scan.nextLine();
				//logIn
				LogInSignIn.logIn();
				scan.close();
				break;
			} else if(choice == 2) {
				//버퍼 제거
				scan.nextLine();
				
				LogInSignIn.ConformSignIn();
				LogInSignIn.logIn();
				break;
			} else if (choice == 3) {
				break;
			}
			
		}
		
		
//		while (true) {
//			int choice;
//			System.out.println("1. quiz   2. selfTest 3. exit");
//			choice = scan.nextInt();
//			if(choice == 1) {
////				quiz();
//			} else if (choice == 2){
////			selfTest();
//		} else {
//			break;
//		}
//		
//			System.out.println("로그아웃 되셨습니다.");
//		}//while
	}//main
}
