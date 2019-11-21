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
	static HashMap<String, DataOutputStream> clients; // ������ ���

	public static void main(String[] args) {
		new CBTServer().serverStart();
	}

	CBTServer() {
		clients = new HashMap<>();
	}

	// ���� ���� ���� ����ϴٰ�
	// Ŭ���̾�Ʈ�� �����ϸ�
	// �޽����� �ޱ����� ������ ����(�޽��� �а� ��ü���� ����)

	private void serverStart() {
		Socket socket = null;
		ServerSocket server = null;
		try {

			server = new ServerSocket(10000);
			System.out.println("[ ������ ���� �Ǿ����ϴ� ]");
			System.out.println(
					"���� IP�ּ� : ��Ʈ��ȣ - " + InetAddress.getLocalHost().getHostAddress() + ":" + server.getLocalPort());
			while (true) {
				socket = server.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] ����");
				// ���ӵ� Ŭ���̾�Ʈ�� ���� ó�� - ������ ����
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ���� ���� socket�� �̿��� �޼��� �ް�, ��ü���� ����
	// ������ �� : �ʵ� clients �� �߰�
	// ����� �� : �ʵ� clients ���� ���� ó��
	public static class ServerReceiver extends Thread {
		Socket socket = null;
		DataInputStream in; // �޼����� ���� �� ���
		DataOutputStream out;;// �ʵ� clients�� ��� �� ���
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
			// ���� �� �� clients�� �߰�, ���� �� �� clients���� ����
			// �޼����� �ް�, ���ӵ� ��� ������� �޼��� ����
			try {
				// Ŭ���̾�Ʈ�� ���� ���� ù �޼����� Ŭ���̾�Ʈ name ����
				boolean LogOut = true;
				// �α��� ���� while��
				// LogOut�� false�� �α׾ƿ�
				while (LogOut) {
					out.writeUTF("|=======================================================================|");
					out.writeUTF("|                                                                (v1.0) |");
					out.writeUTF("|                                [ȯ�� �մϴ�]                             |");
					out.writeUTF("|                        SQLD ���⹮�� CBT ���α׷��Դϴ�.                    |");
					out.writeUTF("|                                                                       |");
					out.writeUTF("|                Kdata(�ѱ������� �����)���� �ְ��ϴ� ������ SQLD��                              |");
					out.writeUTF("|          SQL(Structured Query Language) + D(Developer)�� ���Ӹ���                |");
					out.writeUTF(
							"|                    �����ͺ��̽� SQL �������� �ڰ��� �Դϴ�                                           |");
					out.writeUTF("|-----------------------------------------------------------------------|");
					out.writeUTF(
							"|        1. �α���                 |      2. ȸ������           |      3. ���α׷� ����            |");
					out.writeUTF("|-----------------------------------------------------------------------|");
					out.writeUTF("   => ��ȣ�� �Է��� �ּ��� !!! : ");
					choice = in.readUTF();
					choice = answerCheck(in, out, choice, 1, 3);

					if (choice.equals("3")) {
						LogOut = false;
//						System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] ����");
						break;
					}
					if (choice.equals("1")) {
						// �α���
						// �α��εǸ� ���� ����
						if (ServerDAO.login(in, out) == 1) {
							id = ServerDAO.user_id;
							if (ServerDAO.user_id.equals("administrator")) {
								AdminDAO.adminMain(in, out);
								break;
							}
							
							choiceRoom(in, out, id);
						}
					} else if (choice.equals("2")) {
						// ȸ������
						// ȸ������ �Ϸ�Ǹ� ����
						ServerDAO.signIn(in, out);
					}
				}
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "] ����");
			} catch(SocketException sc) {
				System.out.println("����");
			}  catch (IOException e) {
				e.printStackTrace();

			} finally {
				//
				try {
					if (socket != null) {
						socket.close();
						System.out.printf("[%s, %s] ���� \n", id, ip);
						// client���� �ش� name key & value ����
						clients.remove(id);
						String outMsg = "<" + id + ">���� �������ϴ�.";
						sendToAll(outMsg);

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public static void sendToAll(String str) {

			// ������ ��ü���� �޼��� ����
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
			while (test) { // ���ڿ� ��ȿ�� �˻�
				while (test1) {
					if (LogRegex.isiInt(answer)) {
						test1 = false;
						test2 = true;
					} else {
						out.writeUTF("[Error] ���ڸ� �Է� �����մϴ�  \n  >>> �ٽ� �Է��� �ּ��� : ");
						answer = in.readUTF();
					}
				}

				while (test2) {
					if (Integer.parseInt(answer) <= endNum && Integer.parseInt(answer) >= startNum) {
						test = false;
						test2 = false;
					} else {
						out.writeUTF("1 - 4 ������ ���ڸ� �Է� �����մϴ�. \n  >>> �ٽ��Է��ϼ���.");
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
				out.writeUTF("  1. �� ������ Ǯ��(������)  |   2. ���ǰ�� ����(����)   |  3. �α׾ƿ�(�ʱ� ����ȭ�� ����) ");
				out.writeUTF("============================================================================");
				out.writeUTF(">>");
				choice = in.readUTF();
				choice = answerCheck(in, out, choice, 1, 3);
				if (choice.equals("1")) {
					// ��ü���� ������ ��� �˸�
					sendToAll("#" + id + "���� �����ϼ̽��ϴ�.");
					// ServerQuiz�� quizStart�޼ҵ忡 dis in, dos out�� �Է�
					// quiz�濡 �����ϰ� ��� ����� ä�� ����
					// exit�� ������ break; ���� ���� ���� �� �ִ�.
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
