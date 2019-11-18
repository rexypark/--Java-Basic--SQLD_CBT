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
	public static String exit = "exit";
	
	//로그인 메서드
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
			System.out.print("                  ID 입력 : ");
			user_id = scan.nextLine();
			DbConn.clearScreen();
			System.out.println("         |================================|");
			System.out.println("         |    [SQLD CBT Program Login]    |         ");
			System.out.println("         |                                |         ");
			System.out.println("                  ID : " + user_id + "              ");
			System.out.println("                  PW : _________                  ");
			System.out.print("                 PW 입력 : ");
			user_pw = scan.nextLine();
			DbConn.clearScreen();
			System.out.println("         |================================|");
			System.out.println("         |    [SQLD CBT Program Login]    |         ");
			System.out.println("         |                                |         ");
			System.out.println("                  ID : " + user_id + "              ");
			System.out.println("                  PW : " + user_pw + "              ");
			
			// DB에서 해당 아이디 비밀번호가 있는지 확인 true이면 
			// logIn 성공하여 while문 break 아니면 실패하여 로그인 반복
			if(userDao.checkIdPassword(user_id, user_pw)) {
				System.out.println("         |                                |         ");
				System.out.println("         |        [log In Success]        |          ");
				System.out.println("         |================================|");
				UserVO logUser = userDao.userInfo;
				try {
					Thread.sleep(2000); // 로그인 Success창 2초 딜레이 준 뒤 다음 스텝으로 넘어간다.)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//아이디, 이름, 로그인 내용  USER_LOG테이블에 insert
				UserLogDAO.userLog(logUser.getId(), logUser.getName(), "로그인");
				break;
			} else {
				//실패시 while문 반복
				System.out.println("         |                                |         ");
				System.out.println("         |         [log In Fail]          |          ");
				System.out.println("         |================================|");
				try {
					Thread.sleep(2000); // 로그인 Success창 2초 딜레이 준 뒤 다시 ID를 물어본다..)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					DbConn.clearScreen(); //창 클리어																		
				}
			}				
	}
		
	//회원가입 메서드
	public static void ConformSignIn() {
		while (true) {
			System.out.println("회원가입");
			System.out.println("exit 입력 시 log in 창으로 갑니다.");
			System.out.print("사용할 ID 입력 >>");
			user_id = scan.nextLine();
			if (exit.equals(user_id)) {
				break;
			}
			while (true) {
				if(LogRegex.isId(user_id) == 0 || (user_id.length() == 0)) {
					System.out.println("아이디 형식이 맞지 않습니다.");
					System.out.println("다시 입력해 주세요.");
					System.out.print("사용할 ID 입력 >>");
					user_id = scan.nextLine();
					
				} else if (userDao.checkId(user_id)){
					System.out.println("중복된 아이디가 있습니다.");
					System.out.println("다시 입력해 주세요");
					System.out.print("사용할 ID 입력 >>");
					user_id = scan.nextLine();
				} else {
					break;
			    }
			}
			System.out.print("한글만 입력 가능합니다.");
			System.out.print("사용할 이름 입력 >>");
			user_name = scan.nextLine();
			while (true) {
				if(LogRegex.isKor(user_name) == 0 || (user_name.length() == 0)) {
					System.out.println("이름 형식이 맞지 않습니다. [한글 입력]");
					System.out.println("다시 입력해 주세요.");
					System.out.print("사용할 이름 입력 >>");
					user_name = scan.nextLine();
				} else {
					break;
			    }
			}
			
			
			System.out.println("영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리");
			System.out.print("사용할 비밀번호 입력 >>");
			user_pw = scan.nextLine();
			while (true) {
				if(LogRegex.isPW(user_pw) == 0 || (user_pw.length() == 0)) {
					System.out.println("비밀번호 형식이 맞지 않습니다.");
					System.out.println("영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리");
					System.out.println("다시 입력해 주세요.");
					System.out.print("사용할 비밀번호 입력 >>");
					user_pw = scan.nextLine();
				} else {
					break;
			    }
			}
			
			System.out.print("사용할 전화 입력 >>");
			user_phone = scan.nextLine();
			while (true) {
				if(LogRegex.isPhone(user_phone) == 0 || (user_phone.length() == 0)) {
					System.out.println("전화번호 형식이 맞지 않습니다.");
					System.out.println("010-1234-1234 / 01012341234 / 010.1234.1234");
					System.out.println("다시 입력해 주세요.");
					System.out.print("사용할 전화 입력 >>");
					user_phone = scan.nextLine();
				} else {
					break;
			    }
			}
			
			System.out.print("사용할 이메일 입력 >>");
			user_email = scan.nextLine();
			while (true) {
				if(LogRegex.isEmail(user_email) == 0 || (user_email.length() == 0)) {
					System.out.println("이메일 형식이 맞지 않습니다.");
					System.out.println("asdf12@asd.com");
					System.out.println("다시 입력해 주세요.");
					System.out.print("사용할 이메일 입력 >>");
					user_email = scan.nextLine();
				} else {
					break;
			    }
			}
			
			//signUp에서 insert완료 시 true반환
			boolean inUserData = userDao.signUp(user_id, user_name, user_pw, user_phone, user_email);
			
			//입력한 회원 정보 vo
			UserVO inUser =  userDao.userInfo;
			//scan값 insert를 완료하면 회원가입 완료
			if(inUserData = true) {
				System.out.println("회원가입이 완료 되었습니다.");
				//아이디, 이름, 로그인 내용  USER_LOG테이블에 insert
				UserLogDAO.userLog(inUser.getId(), inUser.getName(), "가입");
				break;
			} else {
				System.out.println("회원가입 정상적으로 되지 않았습니다.");
				System.out.println("다시 입력해주세요");
			}
		} //전체 while문
	 }
}