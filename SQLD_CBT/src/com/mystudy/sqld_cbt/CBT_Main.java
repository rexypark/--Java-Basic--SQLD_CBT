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
			System.out.println("1. 로그인   2. 회원가입");
			choice = scan.nextInt();
			
			if(choice == 1) {
				//버퍼 제거
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
		
			System.out.println("로그아웃 되셨습니다.");
//		while(
//		if(chioce ==1) {
//			quiz();
//		}
//		)
		//while
		//{
			
	//break;}
		//내일 할 일
		//어떤 문제 풀지 선택 창
		//quiz
		//모의고사
		//종료
		//choice 1 2 3
		
		
	}//main
	
}
