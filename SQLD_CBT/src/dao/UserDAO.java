package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.UserVO;

public class UserDAO {
	public static UserVO userInfo;

	//�α��� ID/PW üũ
	//DB���� ID�� PW�� ������ �α��� ����
	//���� �� true ����
	//true�̸� userLog id, name, act(����)
	public boolean checkIdPassword(String id, String password) {
		boolean result = false;
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		try {
			//DB ����̹� ������ �ȵǾ��� �� DB ����̹� ����
			
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			
			//DB���� ID�� PW�� �Բ� ������ �α��� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ? AND USER_PW = ? ");
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.pstmt.setString(1, id);
			DbConn.pstmt.setString(2, password);
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			// rs�� �ش� �����Ͱ� ���� result > true
			// userInfo�� ������ select�� ��� �÷��� �����͵��� ����
			if(DbConn.rs.next()) {
				result = true;
				userInfo = new UserVO(DbConn.rs.getString("USER_ID"), 
						DbConn.rs.getString("USER_NAME"), 
						DbConn.rs.getString("USER_PW"), 
						DbConn.rs.getString("USER_PHONE"), 
						DbConn.rs.getString("USER_EMAIL"),
									 "0");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//����� ��ü close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			
		}
		//result true or false��ȯ
		return result;
	}

	
	
	// User�� �ִ� ��� �����͸� ���
	public List<UserVO> selectUserAll() {
		List<UserVO> list = new ArrayList<>();
	
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		//DB���� - Connection ��ü ����(DB�� �����)
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			//PreparedStatement ��ü �����ϰ� SQL�� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("ORDER BY USER_SEQNUM");
		
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.rs = DbConn.pstmt.executeQuery();
				
			//SQL�� ���� ����� ���� ó��
			while (DbConn.rs.next()) {
				list.add(new UserVO(DbConn.rs.getString("USER_ID"), 
									DbConn.rs.getString("USER_NAME"), 
									DbConn.rs.getString("USER_PW"), 
									DbConn.rs.getString("USER_PHONE"), 
									DbConn.rs.getString("USER_EMAIL"),
											    "0"));
			}
			System.out.println("list data check : " + list.size());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//��밴ü close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}

		return list;
	}//selectAll End


	
	// ���̵� �Է��ϸ� userInfo�� �ش� ������ ��� ������ 
	// �� UserVO��ü�� �����Ѵ�.
	public UserVO selectUser(String id) {
		
		//DB���� - Connection ��ü ����(DB�� �����)
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			//PreparedStatement ��ü �����ϰ� SQL�� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");

			DbConn.pstmt.setString(1, id); 
			
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			//SQL�� ���� ����� ���� ó��
			while (DbConn.rs.next()) {
				userInfo = new UserVO(DbConn.rs.getString("USER_ID"), 
									  DbConn.rs.getString("USER_NAME"), 
									  DbConn.rs.getString("USER_PW"), 
									  DbConn.rs.getString("USER_PHONE"), 
									  DbConn.rs.getString("USER_EMAIL"),
											      "0");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//��밴ü close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}
		return userInfo;
	}//selectAll End
	
	
	// ȸ������ �� ���̵��� �ߺ��� �˻��Ѵ�.
	// ��� ���̵� �߿� �Է� ���� id�� DB�� �ִ��� Ȯ��
	// ������ true�� ���� ������ false
	public static boolean checkId(String id) {
		boolean result = false;
		
		//DB ����̹� ����
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.pstmt.setString(1, id);
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			if(DbConn.rs.next()) {
				System.out.println(">> " + id + "������� ���̵��Դϴ�.");
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}
		
		return result;
	}
	
	// **ȸ������ ���� �Է� �޼���
	// UserVO��ü�� ������ DB�� �ش� ���� ���� �Է�
	// LOG > true�̸� userLog id, name, act(ȸ������)
	// �����ϸ� result 1�� ��ȯ
	// user������ �Է� �޾� 
	public static boolean signUp(String id, String name, String pw, String phone, String email) {
		boolean signUpcmpt = false;
		//DB ����̹� ����
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}

		int result = 0;
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			
			//SQL������ �ۼ��ؼ� Statement�� �����ϰ� sql�� ���� ��û
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_INFO ");
			sql.append("VALUES(?,?,?,?,?,(SELECT NVL(MAX(USER_SEQNUM), 0) + 1 FROM USER_INFO)) "); 

			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			//?(Q���ε�����)�� �����Ű��
			DbConn.pstmt.setString(1, id);
			DbConn.pstmt.setString(2, name);
			DbConn.pstmt.setString(3, pw);
			DbConn.pstmt.setString(4, phone);
			DbConn.pstmt.setString(5, email);
			result = DbConn.pstmt.executeUpdate();
			
			userInfo = new UserVO(id, 
								  name, 
				                  pw, 
				                  phone, 
				                  email,
								  "0");
			
			System.out.println("sql.toString() : " + sql.toString());
			
			
			// ���� insert �Ǹ� 
			// ���ϰ� signUpcmpt�� true
			if (result == 1) {
				signUpcmpt = true;
				}
							 	  
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmt(DbConn.conn, DbConn.pstmt);
		}
		
		//inputUserInfo insert������ �Ǿ����� true
		
		return signUpcmpt;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
