package cbt_mulit_socket;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import dao.DbConn;
import dao.MockTestDAO;
import dao.UserDAO;
import dao.UserLogDAO;
import exam.Quiz;
import log_in.LogInSignIn;
import log_in.LogRegex;
import vo.UserVO;

public class TCPServerMultiChat {
	
	// clientName, clientOutStream;
	static HashMap<String, DataOutputStream> clients; // 접속자 명단

	public static void main(String[] args) {
		new TCPServerMultiChat().serverStart();
	}

	TCPServerMultiChat() {
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
		Socket socket;
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
				//로그인 동작 while문
				//LogOut이 false면 로그아웃
				while (LogOut) {
					out.writeUTF("|=======================================================================|\n");
					out.writeUTF("|                                                                (v1.0) |\n");
					out.writeUTF("|                                [환영 합니다]                             |\n");
					out.writeUTF("|                        SQLD 기출문제 CBT 프로그램입니다.                    |\n");
					out.writeUTF("|                                                                       |\n");
					out.writeUTF("|                Kdata(한국데이터 진흥원)에서 주관하는 시험인 SQLD는                              |\n");
					out.writeUTF("|          SQL(Structured Query Language) + D(Developer)의 줄임말로                |\n");
					out.writeUTF(
							"|                    데이터베이스 SQL 국가공인 자격증 입니다                                           |\n");
					out.writeUTF("|-----------------------------------------------------------------------|\n");
					out.writeUTF(
							"|        1. 로그인                 |      2. 회원가입           |      3. 프로그램 종료            |\n");
					out.writeUTF("|-----------------------------------------------------------------------|\n");
					out.writeUTF("   => 번호를 입력해 주세요 !!! : ");
					choice = in.readUTF();
					System.out.println(choice);

					// 로그인
					//3 을 선택하면 LogOut이 false가 되고 while문이 종료 로그아웃된다.
						if (choice.equals("3")) {
							System.out.println("로그아웃 되셨습니다.");
							LogOut = false;
							break;
						}
						//ServerVO 클래스 login메소드에 dis in과 dos out을 입력하여
						//로그인 유무 판별
						if (choice.equals("1")) {
							ServerVO.login(in, out);
						//회원가입
						//ServerVO 클래스 login메소드에 dis in과 dos out을 입력하여
						//회원가입유무 판별
						} else if (choice.equals("2")) {
							ServerVO.signIn(in, out);
							ServerVO.login(in, out);
						}
						//String id에 로그인에 입력한
						//id를 입력한다.
						id = ServerVO.user_id;
						System.out.println("id찍어볼꼐요~~" + id);
						//clients Map에 입력받은 아이디와 dos out을 입력하여
						//해당 quiz방에 들어온 인원들을 저장한다.
						clients.put(id, out);
						
						while(true) {
							out.writeUTF("1. quiz   2. selfTest 3. exit");
							choice = in.readUTF();
							if (choice.equals("1")) {
								// 전체에게 접속한 사람 알림
								sendToAll("#" + id + "님이 입장하셨습니다.");
								System.out.println("id찍어볼꼐요~~" + id);
								//ServerQuiz에 quizStart메소드에 dis in, dos out을 입력
								//quiz방에 접속하고 모든 사람과 채팅 가능 
								//exit를 눌러야 break; 방을 빠져 나올 수 있다.
								ServerQuiz.quizStart(in,out,id);
							} else if (choice.equals("2")) {
								MockTestDAO.mockTestAll();
							} else {
								break;
							}
						}
				}

			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				//
				try {

					if (socket != null) {
						socket.close();

						System.out.printf("[%s Exit] \n", id);
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
		
		
		
		
	}

}
