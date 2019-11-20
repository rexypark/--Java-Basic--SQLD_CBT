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
	static HashMap<String, DataOutputStream> clients; // ������ ���

	public static void main(String[] args) {
		new TCPServerMultiChat().serverStart();
	}

	TCPServerMultiChat() {
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
		Socket socket;
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
				//�α��� ���� while��
				//LogOut�� false�� �α׾ƿ�
				while (LogOut) {
					out.writeUTF("|=======================================================================|\n");
					out.writeUTF("|                                                                (v1.0) |\n");
					out.writeUTF("|                                [ȯ�� �մϴ�]                             |\n");
					out.writeUTF("|                        SQLD ���⹮�� CBT ���α׷��Դϴ�.                    |\n");
					out.writeUTF("|                                                                       |\n");
					out.writeUTF("|                Kdata(�ѱ������� �����)���� �ְ��ϴ� ������ SQLD��                              |\n");
					out.writeUTF("|          SQL(Structured Query Language) + D(Developer)�� ���Ӹ���                |\n");
					out.writeUTF(
							"|                    �����ͺ��̽� SQL �������� �ڰ��� �Դϴ�                                           |\n");
					out.writeUTF("|-----------------------------------------------------------------------|\n");
					out.writeUTF(
							"|        1. �α���                 |      2. ȸ������           |      3. ���α׷� ����            |\n");
					out.writeUTF("|-----------------------------------------------------------------------|\n");
					out.writeUTF("   => ��ȣ�� �Է��� �ּ��� !!! : ");
					choice = in.readUTF();
					System.out.println(choice);

					// �α���
					//3 �� �����ϸ� LogOut�� false�� �ǰ� while���� ���� �α׾ƿ��ȴ�.
						if (choice.equals("3")) {
							System.out.println("�α׾ƿ� �Ǽ̽��ϴ�.");
							LogOut = false;
							break;
						}
						//ServerVO Ŭ���� login�޼ҵ忡 dis in�� dos out�� �Է��Ͽ�
						//�α��� ���� �Ǻ�
						if (choice.equals("1")) {
							ServerVO.login(in, out);
						//ȸ������
						//ServerVO Ŭ���� login�޼ҵ忡 dis in�� dos out�� �Է��Ͽ�
						//ȸ���������� �Ǻ�
						} else if (choice.equals("2")) {
							ServerVO.signIn(in, out);
							ServerVO.login(in, out);
						}
						//String id�� �α��ο� �Է���
						//id�� �Է��Ѵ�.
						id = ServerVO.user_id;
						System.out.println("id������~~" + id);
						//clients Map�� �Է¹��� ���̵�� dos out�� �Է��Ͽ�
						//�ش� quiz�濡 ���� �ο����� �����Ѵ�.
						clients.put(id, out);
						
						while(true) {
							out.writeUTF("1. quiz   2. selfTest 3. exit");
							choice = in.readUTF();
							if (choice.equals("1")) {
								// ��ü���� ������ ��� �˸�
								sendToAll("#" + id + "���� �����ϼ̽��ϴ�.");
								System.out.println("id������~~" + id);
								//ServerQuiz�� quizStart�޼ҵ忡 dis in, dos out�� �Է�
								//quiz�濡 �����ϰ� ��� ����� ä�� ���� 
								//exit�� ������ break; ���� ���� ���� �� �ִ�.
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
		
		
		
		
	}

}
