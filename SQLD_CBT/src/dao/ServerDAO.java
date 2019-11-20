package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import log_in.LogRegex;
import vo.UserVO;

public class ServerDAO {
	public static String user_id;
	public static String user_pw;
	public static String user_email;
	public static String user_name;
	public static String choice;
	public static String user_phone;
	

	
	//해당 소켓의 dis와 dos를 받아 뷰를 출력하고
	//아이디와 비밀번호를 받아 로그인을 처리하는 메소드
	public static int login(DataInputStream in, DataOutputStream out) {
		int sw = 0;
		int exit = 0;
		UserDAO userDao = new UserDAO();
		while (true) {
			try {
				out.writeUTF("         |================================|");
				out.writeUTF("         |    [SQLD CBT Program Login]    |         ");
				out.writeUTF("         |                                |         ");
				out.writeUTF("         |         ID : _________         |         ");
				out.writeUTF("                  ID 입력 : ");
				user_id = in.readUTF();
				while (true) {
					if (user_id.equals("exit")) {
						exit = 1;
						break;
					}				
					if (LogRegex.isId(user_id) == 0 || (user_id.length() == 0)) {
				
					out.writeUTF("아이디 형식이 맞지 않습니다.");
					out.writeUTF("다시 입력해 주세요.");
					out.writeUTF(" >> ");
					user_id = in.readUTF();
					} else {
						break;
					}
				}
				if (user_id.equals("exit")) {
					exit = 1;
					break;
				}

				JDBCConn.clearScreen(in,out);
				out.writeUTF("         |================================|");
				out.writeUTF("         |    [SQLD CBT Program Login]    |         ");
				out.writeUTF("         |                                |         ");
				out.writeUTF("                  ID : " + user_id + "              ");
				out.writeUTF("                  PW : _________                  ");
				out.writeUTF("                 PW 입력 : ");
				user_pw = in.readUTF();
				if (user_pw.equals("exit")) {
					exit = 1;
					break;
				}
				JDBCConn.clearScreen(in,out);
				out.writeUTF("         |================================|");
				out.writeUTF("         |    [SQLD CBT Program Login]    |         ");
				out.writeUTF("         |                                |         ");
				out.writeUTF("                  ID : " + user_id + "              ");
				out.writeUTF("                  PW : " + user_pw + "              ");

				if (userDao.checkIdPassword(user_id, user_pw)) {
					out.writeUTF("         |                                |         ");
					out.writeUTF("         |        [log In Success]        |         ");
					out.writeUTF("         |================================|");
					UserVO logUser = userDao.userInfo;
					try {
						Thread.sleep(1500); // 로그인 Success창 2초 딜레이 준 뒤 다음 스텝으로 넘어간다.)
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// 아이디, 이름, 로그인 내용 USER_LOG테이블에 insert
					UserLogDAO.userLog(logUser.getId(), logUser.getName(), "접속");
					sw = 1;
					break;
				} else {
					// 실패시 while문 반복
					out.writeUTF("         |                                |         \n");
					out.writeUTF("         |         [log In Fail]          |         \n");
					out.writeUTF("         |================================|\n");
					try {
						Thread.sleep(2000); // 로그인 Success창 2초 딜레이 준 뒤 다시 ID를 물어본다..)
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// DbConn.clearScreen(); // 창 클리어
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return sw;
	}

	//해당 소켓의 dis와 dos를 받아 뷰를 출력하고
	//아이디와 비밀번호를 받아 회원가입을 처리하는 메소드
	public static void signIn(DataInputStream in, DataOutputStream out) {
		int exit = 0;
		while (true) {
			try {
				out.writeUTF("===================================");
				out.writeUTF("               회원가입                            ");
				out.writeUTF("-----------------------------------");
				out.writeUTF("   exit 입력시 초기 화면 창으로 돌아갑니다     ");
				out.writeUTF("                                   ");
				out.writeUTF(" - 사용하실 ID 입력 >>");
				user_id = in.readUTF();
				while (true) {
					if (user_id.equals("exit")) {
						exit = 1;
						break;
					}
					if (LogRegex.isId(user_id) == 0 || (user_id.length() == 0)) {
						out.writeUTF("아이디 형식이 맞지 않습니다.");
						out.writeUTF("다시 입력해 주세요.");
						out.writeUTF("사용할 ID 입력 >>");
						user_id = in.readUTF();
					} else if (UserDAO.checkId(user_id)) {
						out.writeUTF("중복된 아이디가 있습니다.");
						out.writeUTF("다시 입력해 주세요");
						out.writeUTF("사용할 ID 입력 >>");
						user_id = in.readUTF();
						if (user_id.equals("exit")) {
							exit = 1;
							break;
						}
					} else {
						break;
					}
				}
				if (user_id.equals("exit")) {
					exit = 1;
					break;
				}
				out.writeUTF("한글만 입력 가능합니다.");
				out.writeUTF("사용할 이름 입력 >>");
				user_name = in.readUTF();
				while (exit != 1) {
					if (user_name.equals("exit")) {
						exit = 1;
						break;
					}
					if (LogRegex.isKor(user_name) == 0 || (user_name.length() == 0)) {
						out.writeUTF("이름 형식이 맞지 않습니다. [한글 입력]");
						out.writeUTF("다시 입력해 주세요.");
						out.writeUTF("사용할 이름 입력 >>");
						user_name = in.readUTF();
					} else {
						break;
					}
				}
				if (user_name.equals("exit")) {
					exit = 1;
					break;
				}
				out.writeUTF("영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리");
				out.writeUTF("사용할 비밀번호 입력 >>");
				user_pw = in.readUTF();
				
				while (exit != 1) {
					if (user_pw.equals("exit")) {
						exit = 1;
						break;
					}
					if (LogRegex.isPW(user_pw) == 0 || (user_pw.length() == 0)) {
						out.writeUTF("비밀번호 형식이 맞지 않습니다.");
						out.writeUTF("영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리");
						out.writeUTF("다시 입력해 주세요.");
						out.writeUTF("사용할 비밀번호 입력 >>");
						user_pw = in.readUTF();
					} else {
						break;
					}
				}
				if (user_pw.equals("exit")) {
					exit = 1;
					break;
				}
				
				out.writeUTF("사용할 전화 입력 >>");
				user_phone = in.readUTF();
				
				while (exit != 1) {
					if (user_phone.equals("exit")) {
						exit = 1;
						break;
					}
					if (LogRegex.isPhone(user_phone) == 0 || (user_phone.length() == 0)) {
						out.writeUTF("전화번호 형식이 맞지 않습니다.");
						out.writeUTF("010-1234-1234 / 01012341234 / 010.1234.1234");
						out.writeUTF("다시 입력해 주세요.");
						out.writeUTF("사용할 전화 입력 >>");
						user_phone = in.readUTF();
					} else {
						break;
					}
				}

				if (user_phone.equals("exit")) {
					exit = 1;
					break;
				}
				
				out.writeUTF("사용할 이메일 입력 >>");
				user_email = in.readUTF();
				while (exit != 1) {
					if (user_email.equals("exit")) {
						exit = 1;
						break;
					}
					if (LogRegex.isEmail(user_email) == 0 || (user_email.length() == 0)) {
						out.writeUTF("이메일 형식이 맞지 않습니다.");
						out.writeUTF("asdf12@asd.com");
						out.writeUTF("다시 입력해 주세요.");
						out.writeUTF("사용할 이메일 입력 >>");
						user_email = in.readUTF();
					} else {
						break;
					}
				}
				if (user_email.equals("exit")) {
					exit = 1;
					break;
				}
				// signUp에서 insert완료 시 true반환
				boolean inUserData = UserDAO.signUp(user_id, user_name, user_pw, user_phone, user_email);

				// 입력한 회원 정보 vo
				UserVO inUser = UserDAO.userInfo;
				// scan값 insert를 완료하면 회원가입 완료
				if (inUserData = true) {
					out.writeUTF("회원가입이 완료 되었습니다.");
					// 아이디, 이름, 로그인 내용 USER_LOG테이블에 insert
					UserLogDAO.userLog(inUser.getId(), inUser.getName(), "가입");
					break;
				} else {
					out.writeUTF("회원가입 정상적으로 되지 않았습니다.");
					out.writeUTF("다시 입력해주세요");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}