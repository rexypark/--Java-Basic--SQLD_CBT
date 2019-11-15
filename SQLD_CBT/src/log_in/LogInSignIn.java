package log_in;

import java.util.Scanner;

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
		
		// DB에서 해당 아이디 비밀번호가 있는지 확인 true이면 
		// logIn 성공하여 while문 break 아니면 실패하여 로그인 반복
		if(userDao.checkIdPassword(user_id, user_pw)) {
			System.out.println("log In 완료!");
			UserVO logUser = userDao.userInfo;
			
			//아이디, 이름, 로그인 내용  USER_LOG테이블에 insert
			UserLogDAO.userLog(logUser.getId(), logUser.getName(), "로그인");
			break;
		} else {
			//실패시 while문 반복
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
			break;
		} //전체 while문
	 }
}
