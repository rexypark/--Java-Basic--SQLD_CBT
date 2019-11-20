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

// 메세지 작성 전송 독립적으로 동작하는 쓰레드 생성
// 메세지 수신 독립적으로 동작하는 쓰레드 생성
public class CBTClient {

	public static void main(String[] args) {

		Socket socket = null;
		// "192.168.0.100"
		try {
			socket = new Socket("192.168.0.58", 10000);

			// 메세지 보내기 쓰래드 생성 실행(쓰레드로 동작)
			ClientSender clientSender = new ClientSender(socket);
			clientSender.start();

			// 메세지 받기 쓰레드 생성 실행(쓰레드로 동작)
			ClientReceiver clientRecevier = new ClientReceiver(socket);
			clientRecevier.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 메세지 보내기
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
				System.out.println("[ 예외발생 ] ClientSender 생성자 out 객체 생성 실패");
			}

		}

		@Override
		public void run() {

			// 메세지 작성하고, 작성된 메세지 서버로 전송
			Scanner sc = new Scanner(System.in);
			if (out == null) {
				System.out.println("[예외발생] out 객체가 null입니다.");
				return;
			}
			try {
				while (true) {
					String msg = sc.nextLine();
					// 서버로 출력
					out.writeUTF(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	// 메세지 받기 쓰레드 작성
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
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("|서버가 종료되었습니다.|");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					break;
				}
				catch (EOFException e2) {
					System.out.println("접속이 종료되었습니다.");
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

}
