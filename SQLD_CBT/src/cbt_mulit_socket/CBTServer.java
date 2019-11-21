package cbt_mulit_socket;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;

import dao.AdminDAO;
import dao.JDBCConn;
import dao.ServerDAO;
import dao.UserDAO;
import dao.UserLogDAO;
import exam.Quiz;
import log_in.LogRegex;
import vo.UserVO;

public class CBTServer {

	// clientName, clientOutStream;
	static HashMap<String, DataOutputStream> clients; // 접속자 명단

	public static void main(String[] args) {
		new CBTServer().serverStart();
	}

	CBTServer() {
		clients = new HashMap<>();
	}

	// 서버 소켓 생성 대기하다가
	// 클라이언트가 접속하면
	// 메시지를 받기위한 쓰레드 생성(메시지 읽고 전체에게 전달)

	private void serverStart() {
		Socket socket = null;
		ServerSocket server = null;
		try {

			server = new ServerSocket(10000);
			System.out.println("[ 서버가 시작 되었습니다 ]");
			System.out.println(
					"서버 IP주소 : 포트번호 - " + InetAddress.getLocalHost().getHostAddress() + ":" + server.getLocalPort());
			while (true) {
				socket = server.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] 접속");
				// 접속된 클라이언트에 대한 처리 - 쓰레드 생성
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 전달 받은 socket을 이용해 메세지 받고, 전체에게 전달
	// 생생될 때 : 필드 clients 에 추가
	// 종료될 때 : 필드 clients 에서 삭제 처리
	public static class ServerReceiver extends Thread {
		Socket socket = null;
		DataInputStream in; // 메세지를 읽을 때 사용
		DataOutputStream out;;// 필드 clients에 등록 시 사용
		String ip;
		String id;
		String user_pw;
		String user_email;
		String user_name;
		String choice;
		String user_phone;

		ServerReceiver(Socket socket) {
			this.socket = socket;
			try {

				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());

				ip = socket.getInetAddress().getHostAddress();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			UserDAO userDao = new UserDAO();
			// 생성 될 때 clients에 추가, 종료 할 때 clients에서 삭제
			// 메세지를 받고, 접속된 모든 사람에게 메세지 전달
			try {
				// 클라이언트로 부터 받은 첫 메세지가 클라이언트 name 설정
				boolean LogOut = true;
				// 로그인 동작 while문
				// LogOut이 false면 로그아웃
				while (LogOut) {
					out.writeUTF("|=======================================================================|");
					out.writeUTF("|                                                                (v1.0) |");
					out.writeUTF("|                                [환영 합니다]                             |");
					out.writeUTF("|                        SQLD 기출문제 CBT 프로그램입니다.                    |");
					out.writeUTF("|                                                                       |");
					out.writeUTF("|                Kdata(한국데이터 진흥원)에서 주관하는 시험인 SQLD는                              |");
					out.writeUTF("|          SQL(Structured Query Language) + D(Developer)의 줄임말로                |");
					out.writeUTF(
							"|                    데이터베이스 SQL 국가공인 자격증 입니다                                           |");
					out.writeUTF("|-----------------------------------------------------------------------|");
					out.writeUTF(
							"|        1. 로그인                 |      2. 회원가입           |      3. 프로그램 종료            |");
					out.writeUTF("|-----------------------------------------------------------------------|");
					out.writeUTF("   => 번호를 입력해 주세요 !!! : ");
					choice = in.readUTF();
					choice = answerCheck(in, out, choice, 1, 3);

					if (choice.equals("3")) {
						LogOut = false;
//						System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] 종료");
						break;
					}
					if (choice.equals("1")) {
						// 로그인
						// 로그인되면 퀴즈 실행
						if (ServerDAO.login(in, out) == 1) {
							id = ServerDAO.user_id;
							if (ServerDAO.user_id.equals("administrator")) {
								AdminDAO.adminMain(in, out);
								break;
							}
							
							choiceRoom(in, out, id);
						}
					} else if (choice.equals("2")) {
						// 회원가입
						// 회원가입 완료되면 메인
						ServerDAO.signIn(in, out);
					}
				}
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] 종료");
			} catch(SocketException sc) {
				System.out.println("종료");
			}  catch (IOException e) {
				e.printStackTrace();

			} finally {
				//
				try {
					if (socket != null) {
						socket.close();
						System.out.printf("[%s, %s] 종료 \n", id, ip);
						// client에서 해당 name key & value 삭제
						clients.remove(id);
						String outMsg = "<" + id + ">님이 나갔습니다.";
						sendToAll(outMsg);

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public static void sendToAll(String str) {

			// 접속한 전체에게 메세지 전달
			Iterator<String> ite = clients.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				DataOutputStream out = clients.get(key);

				try {
					out.writeUTF(str);
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}

		public static String answerCheck(DataInputStream in, DataOutputStream out, String answer, int startNum,
				int endNum) throws IOException {
			boolean test = true;
			boolean test1 = true;
			boolean test2 = true;
			while (test) { // 문자열 유효성 검사
				while (test1) {
					if (LogRegex.isiInt(answer)) {
						test1 = false;
						test2 = true;
					} else {
						out.writeUTF("[Error] 숫자만 입력 가능합니다  \n  >>> 다시 입력해 주세요 : ");
						answer = in.readUTF();
					}
				}

				while (test2) {
					if (Integer.parseInt(answer) <= endNum && Integer.parseInt(answer) >= startNum) {
						test = false;
						test2 = false;
					} else {
						out.writeUTF("1 - 4 사이의 숫자만 입력 가능합니다. \n  >>> 다시입력하세요.");
						answer = in.readUTF();
						test2 = false;
						test1 = true;
					}
				}
			}
			return answer;
		}

		public static void choiceRoom(DataInputStream in, DataOutputStream out, String id) throws IOException {
			while (true) {
				String choice;
				JDBCConn.clearScreen(in,out);
				out.writeUTF("============================================================================");
				out.writeUTF("  1. 한 문제씩 풀기(여러명)  |   2. 모의고사 보기(개인)   |  3. 로그아웃(초기 메인화면 복귀) ");
				out.writeUTF("============================================================================");
				out.writeUTF(">>");
				choice = in.readUTF();
				choice = answerCheck(in, out, choice, 1, 3);
				if (choice.equals("1")) {
					// 전체에게 접속한 사람 알림
					sendToAll("#" + id + "님이 입장하셨습니다.");
					// ServerQuiz에 quizStart메소드에 dis in, dos out을 입력
					// quiz방에 접속하고 모든 사람과 채팅 가능
					// exit를 눌러야 break; 방을 빠져 나올 수 있다.
					MultiQuizRoom.quizStart(in, out, id);
				} else if (choice.equals("2")) {
					MockTest.mockTestAll(in, out, id);
				} else {
					UserDAO.checkLogOutId(id);
					break;
				}
			}
		}

	}

}
