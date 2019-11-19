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
import dao.DbConn;
import dao.JDBC_Close;

public class ServerQuiz {

	private static Scanner scan = new Scanner(System.in);

	public static void quizStart(DataInputStream in, DataOutputStream out ) throws IOException {
		Scanner scan = new Scanner(System.in);
		String input;
		String key;
		out.writeUTF("Quiz방에 입장하셨습니다.");
		out.writeUTF("방장 >> 키워드 - sqld1 : 과목 1");
		out.writeUTF("방장>> 키워드 - sqld2 : 과목 2");
		out.writeUTF("해당 키워드를 입력 하시면 해당 과목의 문제가 출제됩니다.");
		while (true) {
			
			out.writeUTF(dao.UserDAO.userInfo.getId() + " >>");
			// 1
			try {
				key = in.readUTF();
				if (key.equals("exit"))
					break;
				
				if (key.equals("1") || key.equals("2")) {
					TCPServerMultiChat.ServerReceiver.sendToAll(TCPServerMultiChat.ServerReceiver.id+ " >> " + key);
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
					TCPServerMultiChat.ServerReceiver.sendToAll(TCPServerMultiChat.ServerReceiver.id+ " >> " + key);
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
		TCPServerMultiChat.ServerReceiver.sendToAll("===========================");
		TCPServerMultiChat.ServerReceiver.sendToAll(vo.getQwestion());// 문제
		try {
		Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TCPServerMultiChat.ServerReceiver.sendToAll(vo.getAnswerInfo() + "\n");

		TCPServerMultiChat.ServerReceiver.sendToAll("==============================================================");
	}

	public static ExamVO selectQues(int section) {

		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}

		ExamVO examVO = null;
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT *");
			sql.append("  FROM (SELECT* FROM EXAM_INFO ");// 조인
			sql.append("  WHERE SECTION = ?  ");// 조인
			sql.append("  ORDER BY DBMS_RANDOM.VALUE)" + "WHERE ROWNUM = 1");// 조인

			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());

			DbConn.pstmt.setInt(1, section);

			DbConn.rs = DbConn.pstmt.executeQuery();

			if (DbConn.rs.next()) {
				examVO = new ExamVO(DbConn.rs.getString("QUESTION"), DbConn.rs.getString("ANSWER"),
						DbConn.rs.getString("SECTION"), DbConn.rs.getString("EXAM_SEQNUM"),
						DbConn.rs.getString("ANSWER_INFO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
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
