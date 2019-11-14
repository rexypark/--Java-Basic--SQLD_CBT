package com.mystudy.sqld_cbt;

import java.util.Scanner;

public class CBT_Main {
	
	public static void main(String[] args) {
		String user_id = "";
		String user_name = "";
		String user_pw = "";
		String user_phone = "";  
		String user_email = "";
		
		
		Scanner scan = new Scanner(System.in);
		UserDAO userDao = new UserDAO();
		
		
		while (true) {
			
			int choice = 0;
			
			System.out.println("SQLD CBT 프로그램");
			System.out.println("SQLD_CBT회원이십니까?");
			System.out.println("1. 로그인   2. 회원가입");
			choice = scan.nextInt();
			if(choice == 1) {
				//버퍼 제거
				scan.nextLine();
				while (true) {
					System.out.println("로그인");
					System.out.print("ID 입력 >>");
					user_id = scan.nextLine();
					System.out.print("PW 입력 >>");
					user_pw = scan.nextLine();
					
					if(userDao.checkIdPassword(user_id, user_pw)) {
						System.out.println("log In 완료!");
						UserVO logUser = userDao.userInfo;
						
						UserLogDAO.userLog(logUser.getUser_id(), logUser.getUser_name(), "로그인");
						break;
					} else {
						System.out.println("log In 실패!");
						System.out.println("다시 입력하세요.");
					}
				}
			} else if(choice == 2) {
				while (true) {
					System.out.println("회원가입");
					while (true) {
						System.out.print("사용할 ID 입력 >>");
						user_id = scan.nextLine();
						//UserDAO에 있는 checkId메서드를 통해 ID 중복 검사
						if(userDao.checkId(user_id)) {
							System.out.println("다시 입력해 주세요");
						}else {
							break;
						}
					}
					System.out.print("사용할 이름 입력 >>");
					user_name = scan.nextLine();
					System.out.print("사용할 비밀번호 입력 >>");
					user_id = scan.nextLine();
					System.out.print("사용할 전화 입력 >>");
					user_phone = scan.nextLine();
					System.out.print("사용할 이메일 입력 >>");
					user_email = scan.nextLine();
					
					boolean inUserData = UserSign.signUp(user_id, user_name, user_pw, user_phone, user_email);
					
					//scan값 insert
					if(inUserData = true) {
						System.out.println("회원가입이 완료 되었습니다.");
						break;
					} else {
						System.out.println("회원가입 정상적으로 되지 않았습니다.");
						System.out.println("다시 입력해주세요");
					}
				
				} 
			}//choice == 2
			break;
		 }//전체 while문
		
		}//main
		
		
//		public static void logIn() {
//			
//			while (true) {
//				System.out.println("로그인");
//				System.out.print("ID 입력 >>");
//				user_id = scan.nextLine();
//				System.out.print("PW 입력 >>");
//				user_pw = scan.nextLine();
//				
//				if(userDao.checkIdPassword(user_id, user_pw)) {
//					System.out.println("log In 완료!");
//					break;
//				} else {
//					System.out.println("log In 실패!");
//					System.out.println("다시 입력하세요.");
//				}
//		}
//		
//		
//	}
	
}
