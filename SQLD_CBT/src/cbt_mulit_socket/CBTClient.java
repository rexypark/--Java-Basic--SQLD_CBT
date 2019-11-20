package cbt_mulit_socket;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.InputMap;

// �޼��� �ۼ� ���� ���������� �����ϴ� ������ ����
// �޼��� ���� ���������� �����ϴ� ������ ����
public class CBTClient {

	public static void main(String[] args) {

		Socket socket = null;
		// "192.168.0.100"
		try {
			socket = new Socket("192.168.0.58", 10000);

			// �޼��� ������ ������ ���� ����(������� ����)
			ClientSender clientSender = new ClientSender(socket);
			clientSender.start();

			// �޼��� �ޱ� ������ ���� ����(������� ����)
			ClientReceiver clientRecevier = new ClientReceiver(socket);
			clientRecevier.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// �޼��� ������
	static class ClientSender extends Thread {
		Socket socket;
		String id;
		String pw;
		DataOutputStream out;
		String choice;

		ClientSender(Socket socket) {
			this.socket = socket;

			try {
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("[ ���ܹ߻� ] ClientSender ������ out ��ü ���� ����");
			}

		}

		@Override
		public void run() {

			// �޼��� �ۼ��ϰ�, �ۼ��� �޼��� ������ ����
			Scanner sc = new Scanner(System.in);
			if (out == null) {
				System.out.println("[���ܹ߻�] out ��ü�� null�Դϴ�.");
				return;
			}
			try {
				while (true) {
					String msg = sc.nextLine();
					// ������ ���
					out.writeUTF(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	// �޼��� �ޱ� ������ �ۼ�
	static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;

		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					String msg = in.readUTF();
					System.out.println(msg);
				} catch(SocketException sc) {
					System.out.println("�ѤѤѤѤѤѤѤѤѤѤѤѤѤ�");
					System.out.println("|������ ����Ǿ����ϴ�.|");
					System.out.println("�ѤѤѤѤѤѤѤѤѤѤѤѤѤ�");
					break;
				}
				catch (EOFException e2) {
					System.out.println("������ ����Ǿ����ϴ�.");
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

}
