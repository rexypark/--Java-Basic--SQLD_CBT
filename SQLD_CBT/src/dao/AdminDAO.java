package dao;

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

import vo.UserVO;

public class AdminDAO {
	
	Scanner scan = new Scanner(System.in);
	
	//Admin main
	public static void adminMain(DataInputStream in, DataOutputStream out) throws IOException {
		while (true) {
			out.writeUTF("|========================================================|");
			out.writeUTF("|               ȯ�� �մϴ� Administrator��                                    |");
			out.writeUTF("|   1. ���� ������ ����Ȯ��   |  2. ȸ������Ȯ��   |    3. exit     |");
			out.writeUTF("|--------------------------------------------------------|");
			out.writeUTF("   => ��ȣ�� �Է��� �ּ��� : ");
			String select = in.readUTF();
			out.writeUTF("|========================================================|");
			JDBCConn.clearScreen(in,out);
			if (select == "1") nowUserList(in, out); 
			if (select == "2") {
				 List<UserVO> list = selectAll();
				 userListAll(list, in, out); 
			}
			if (select == "3") break;
		}
	
	}
	
	//1.���� ������ ����
	public static void nowUserList(DataInputStream in, DataOutputStream out) throws IOException {

		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		List<UserVO> list = new ArrayList<>();
		//DB���� - Connection ��ü ����(DB�� �����)
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			//PreparedStatement ��ü �����ϰ� SQL�� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_LOG ");
			sql.append("WHERE USER_ACT = '�α���' ");
			sql.append("ORDER BY L_USER_ID");
		
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
				
			//SQL�� ���� ����� ���� ó��
			while (JDBCConn.rs.next()) {
				list.add(new UserVO(
									JDBCConn.rs.getString("L_USER_ID"), 
									JDBCConn.rs.getString("L_USER_NAME"),
									JDBCConn.rs.getString("USER_ACT"),
									JDBCConn.rs.getString("USER_TIME")));
			}
			
			System.out.println("list data check : " + list.size());
			
			out.writeUTF("|====================================================================|");
			out.writeUTF("|    User ID    |       Name      |   ���� ����    |        ���� �ð�              |");
			for (UserVO mvo : list) { 
				System.out.printf("%10s %16s %23s %29s \n", mvo.getId(), mvo.getName(), mvo.getAct()
						          , mvo.getTime());
			}
			out.writeUTF(">>EnterŰ�� �����ֽø� �ʱ�ȭ������ ���ư��ϴ�.");
			in.readUTF();
			JDBCConn.clearScreen(in,out); // EnterŰ �Է½� 80ĭ���� method ȣ��
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//��밴ü close
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}
		
	}// nowUserList End
	
	
	//2.��ü ȸ������ list
	public static void userListAll(List<UserVO> list, DataInputStream in, DataOutputStream out) throws IOException {
		 System.out.println("|=====================================================================================================================|");
		 System.out.println("| Index |       User ID        |       Name      |     Password    |       Phone       |             E-Mail           |");
		for (UserVO mvo : list) { 
			System.out.printf("%5s %20s %15s %30s %20s %28s \n", mvo.getSeqnum(), mvo.getId(), mvo.getName()
					          , mvo.getPw(), mvo.getPhone(), mvo.getEmail());
		}
		out.writeUTF(">>EnterŰ�� �����ֽø� �ʱ�ȭ������ ���ư��ϴ�.");
		in.readUTF();
		JDBCConn.clearScreen(in,out); // EnterŰ �Է½� 80ĭ���� method ȣ��
	}// userListAll End
	

	public static List<UserVO> selectAll() {
		
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		List<UserVO> list = new ArrayList<>();
		
		//DB���� - Connection ��ü ����(DB�� �����)
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			//PreparedStatement ��ü �����ϰ� SQL�� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("ORDER BY USER_SEQNUM");
		
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
				
			//SQL�� ���� ����� ���� ó��
			while (JDBCConn.rs.next()) {
				list.add(new UserVO(						  
									JDBCConn.rs.getString("USER_ID"),
									JDBCConn.rs.getString("USER_NAME"),
									JDBCConn.rs.getString("USER_PW"),
									JDBCConn.rs.getString("USER_PHONE"),
									JDBCConn.rs.getString("USER_EMAIL"),
									JDBCConn.rs.getString("USER_SEQNUM")));
			}
			System.out.println("list data check : " + list.size());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//��밴ü close
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}

		return list;
	}//selectAll End
	
	
}
