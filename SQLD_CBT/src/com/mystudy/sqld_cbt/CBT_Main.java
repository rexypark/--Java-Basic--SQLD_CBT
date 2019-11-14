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
			
			System.out.println("SQLD CBT 프로그램");
			System.out.println("SQLD_CBT회원이십니까?");
			System.out.println("1. 로그인   2. 회원가입");
			choice = scan.nextInt();
			if(choice == 1) {
				//버퍼 제거
				scan.nextLine();
				//logIn
				logIn();
				break;
			} else if(choice == 2) {
				ConformSignIn();
			}
		}//while
	}//main
	
	//로그인 메서드
	public static void logIn() {
		String user_id = "";
		String user_pw = "";
		
		Scanner scan = new Scanner(System.in);
		UserDAO userDao = new UserDAO();
		while (true) {
			System.out.println("로그인");
			System.out.print("ID 입력 >>");
			user_id = scan.nextLine();
			System.out.print("PW 입력 >>");
			user_pw = scan.nextLine();
			
			if(userDao.checkIdPassword(user_id, user_pw)) {
				System.out.println("log In 완료!");
				UserVO logUser = userDao.userInfo;
				
				//아이디, 이름, 로그인 내용  USER_LOG테이블에 insert
				UserLogDAO.userLog(logUser.getId(), logUser.getName(), "로그인");
				break;
			} else {
				System.out.println("log In 실패!");
				System.out.println("다시 입력하세요.");
			}
		}
	}
	
	//회원가입 메서드
	public static void ConformSignIn() {
		while (true) {
			System.out.println("회원가입");
			while (true) {
				//버퍼오류
				scan.nextLine();
				
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
			user_pw = scan.nextLine();
			System.out.print("사용할 전화 입력 >>");
			user_phone = scan.nextLine();
			System.out.print("사용할 이메일 입력 >>");
			user_email = scan.nextLine();
			
			boolean inUserData = userDao.signUp(user_id, user_name, user_pw, user_phone, user_email);
			
			//입력한 회원 정보 vo
			UserVO inUser =  userDao.userInfo;
			System.out.println(inUser.getName());
			//scan값 insert
			if(inUserData = true) {
				System.out.println("회원가입이 완료 되었습니다.");
				//아이디, 이름, 로그인 내용  USER_LOG테이블에 insert
				UserLogDAO.userLog(inUser.getId(), inUser.getName(), "가입");
				break;
			} else {
				System.out.println("회원가입 정상적으로 되지 않았습니다.");
				System.out.println("다시 입력해주세요");
			}
			break;
		} //전체 while문
	 }
	
}
