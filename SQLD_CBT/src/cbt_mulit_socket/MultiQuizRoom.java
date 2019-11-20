package cbt_mulit_socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vo.ExamVO;
import dao.JDBCConn;

public class MultiQuizRoom {

	private static Scanner scan = new Scanner(System.in);

	public static void quizStart(DataInputStream in, DataOutputStream out, String id) throws IOException {
		Scanner scan = new Scanner(System.in);
		String input;
		String key;
		out.writeUTF("=============================================================");
		out.writeUTF("                    한 문제씩 풀기방에 입장하셨습니다                                  ");
		out.writeUTF("                                                             ");
		out.writeUTF(" 채팅창에 [sqld1] 커맨드 입력시  [1. 데이터 모델링의 이해] 문제가 출력 됩니다. ");
		out.writeUTF(" 채팅창에 [sqld2] 커맨드 입력시  [2.   SQL 기본 및 활용] 문제가 출력 됩니다. ");
		out.writeUTF(" 채팅창에 [ ㅋㅈ  ] 커맨드 입력시  [     주관식 문제         ] 문제가 출력 됩니다. ");
		out.writeUTF("=============================================================");
		while (true) {
			
//			out.writeUTF(dao.UserDAO.userInfo.getId() + " >>");
			// 1
			try {
				key = in.readUTF();
				if (key.equals("exit")) {
					CBTServer.clients.remove(id);
					String outMsg = "<" + id + ">님이 나갔습니다.";
					CBTServer.ServerReceiver.sendToAll(outMsg);
					CBTServer.clients.remove(id);
					break;
				}
				if (key.equals("1") || key.equals("2")) {
//					TCPServerMultiChat.ServerReceiver.sendToAll(TCPServerMultiChat.ServerReceiver.id+ " >> " + key);
					CBTServer.ServerReceiver.sendToAll(id+ " >> " + key);
				} else {
				switch (keyword(key)) {
				case "1":
					quesPrint(1, in, out);
					break;
				case "2":
					quesPrint(2, in, out);
					break;
				default:
					break;
				}
//					TCPServerMultiChat.ServerReceiver.sendToAll(TCPServerMultiChat.ServerReceiver.id+ " >> " + key);
					CBTServer.ServerReceiver.sendToAll(id+ " >> " + key);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}// main

	public static void quesPrint(int section, DataInputStream in, DataOutputStream out) throws IOException {

		ExamVO vo = selectQues(section);
		String userAnswer;
		CBTServer.ServerReceiver.sendToAll("===========================");
		CBTServer.ServerReceiver.sendToAll(vo.getQwestion());// 문제
		try {
		Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CBTServer.ServerReceiver.sendToAll("정답 : " + vo.getAnswer() + "\n");
		CBTServer.ServerReceiver.sendToAll(vo.getAnswerInfo() + "\n");

		CBTServer.ServerReceiver.sendToAll("==============================================================");
	}

	public static ExamVO selectQues(int section) {

		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}

		ExamVO examVO = null;
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT *");
			sql.append("  FROM (SELECT* FROM EXAM_INFO ");// 조인
			sql.append("  WHERE SECTION = ?  ");// 조인
			sql.append("  ORDER BY DBMS_RANDOM.VALUE)" + "WHERE ROWNUM = 1");// 조인

			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());

			JDBCConn.pstmt.setInt(1, section);

			JDBCConn.rs = JDBCConn.pstmt.executeQuery();

			if (JDBCConn.rs.next()) {
				examVO = new ExamVO(JDBCConn.rs.getString("QUESTION"), JDBCConn.rs.getString("ANSWER"),
						JDBCConn.rs.getString("SECTION"), JDBCConn.rs.getString("EXAM_SEQNUM"),
						JDBCConn.rs.getString("ANSWER_INFO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}
		return examVO;
	}

	public static String keyword(String usrInput) {
		String result = "0";

		if (usrInput.equals("sqld1")) {
			result = "1";
			return result;
		} else if (usrInput.equals("sqld2")) {
			result = "2";
			return result;
		}
		return usrInput;
	}
}
