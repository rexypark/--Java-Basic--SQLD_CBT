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
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			
			int choice = 0;
			System.out.println("|=======================================================================|");
			System.out.println("|                                                                (v1.0) |");
			System.out.println("|                                [환영 합니다]                             |");
			System.out.println("|                        SQLD 기출문제 CBT 프로그램입니다.                    |");
			System.out.println("|                                                                       |");
			System.out.println("|                Kdata(한국데이터 진흥원)에서 주관하는 시험인 SQLD는                              |");
			System.out.println("|          SQL(Structured Query Language) + D(Developer)의 줄임말로                |");
			System.out.println("|                    데이터베이스 SQL 국가공인 자격증 입니다                                           |");
			System.out.println("|-----------------------------------------------------------------------|");
			System.out.println("|        1. 로그인                 |      2. 회원가입           |      3. 프로그램 종료            |");
			System.out.println("|-----------------------------------------------------------------------|");
			System.out.print("   => 번호를 입력해 주세요 !!! : ");
			choice = scan.nextInt();
			
			if(choice == 1) {
				
				//버퍼 제거
				scan.nextLine();
				//logIn
				LogInSignIn.logIn();
				
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
		
		while (true) {
			int choice;
			System.out.println("1. quiz   2. selfTest 3. exit");
			choice = scan.nextInt();
			if(choice == 1) {
				Quiz.quizStart();
			} else if (choice == 2){
			MockTestDAO.mockTestAll();
			} else {
			break;
			}
		}//while
		System.out.println("로그아웃 되셨습니다.");
		
		scan.close();
	}//main
}
