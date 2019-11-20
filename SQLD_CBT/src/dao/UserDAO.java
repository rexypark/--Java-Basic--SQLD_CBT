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
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		try {
			//DB ����̹� ������ �ȵǾ��� �� DB ����̹� ����
			
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			
			//DB���� ID�� PW�� �Բ� ������ �α��� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ? AND USER_PW = ? ");
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.pstmt.setString(1, id);
			JDBCConn.pstmt.setString(2, password);
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
			
			// rs�� �ش� �����Ͱ� ���� result > true
			// userInfo�� ������ select�� ��� �÷��� �����͵��� ����
			if(JDBCConn.rs.next()) {
				result = true;
				userInfo = new UserVO(JDBCConn.rs.getString("USER_ID"), 
						JDBCConn.rs.getString("USER_NAME"), 
						JDBCConn.rs.getString("USER_PW"), 
						JDBCConn.rs.getString("USER_PHONE"), 
						JDBCConn.rs.getString("USER_EMAIL"),
									 "0");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//����� ��ü close
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
			
		}
		//result true or false��ȯ
		return result;
	}

	
	
	// User�� �ִ� ��� �����͸� ���
	public List<UserVO> selectUserAll() {
		List<UserVO> list = new ArrayList<>();
	
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
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
				list.add(new UserVO(JDBCConn.rs.getString("USER_ID"), 
									JDBCConn.rs.getString("USER_NAME"), 
									JDBCConn.rs.getString("USER_PW"), 
									JDBCConn.rs.getString("USER_PHONE"), 
									JDBCConn.rs.getString("USER_EMAIL"),
											    "0"));
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


	
	// ���̵� �Է��ϸ� userInfo�� �ش� ������ ��� ������ 
	// �� UserVO��ü�� �����Ѵ�.
	public UserVO selectUser(String id) {
		
		//DB���� - Connection ��ü ����(DB�� �����)
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			//PreparedStatement ��ü �����ϰ� SQL�� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");

			JDBCConn.pstmt.setString(1, id); 
			
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
			
			//SQL�� ���� ����� ���� ó��
			while (JDBCConn.rs.next()) {
				userInfo = new UserVO(JDBCConn.rs.getString("USER_ID"), 
									  JDBCConn.rs.getString("USER_NAME"), 
									  JDBCConn.rs.getString("USER_PW"), 
									  JDBCConn.rs.getString("USER_PHONE"), 
									  JDBCConn.rs.getString("USER_EMAIL"),
											      "0");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//��밴ü close
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}
		return userInfo;
	}//selectAll End
	
	
	// ȸ������ �� ���̵��� �ߺ��� �˻��Ѵ�.
	// ��� ���̵� �߿� �Է� ���� id�� DB�� �ִ��� Ȯ��
	// ������ true�� ���� ������ false
	public static boolean checkId(String id) {
		boolean result = false;
		
		//DB ����̹� ����
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.pstmt.setString(1, id);
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
			
			if(JDBCConn.rs.next()) {
				System.out.println(">> " + id + "������� ���̵��Դϴ�.");
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
		}
		
		return result;
	}
	
	
	public static void checkLogOutId(String id) {
		UserVO logOutUser = null;
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		try {
			//DB ����̹� ������ �ȵǾ��� �� DB ����̹� ����
			
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			
			//DB���� ID�� PW�� �Բ� ������ �α��� ����
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			JDBCConn.pstmt.setString(1, id);
			
			JDBCConn.rs = JDBCConn.pstmt.executeQuery();
			
			// rs�� �ش� �����Ͱ� ���� result > true
			// userInfo�� ������ select�� ��� �÷��� �����͵��� ����
			if(JDBCConn.rs.next()) {
				logOutUser = new UserVO(JDBCConn.rs.getString("USER_ID"), 
						JDBCConn.rs.getString("USER_NAME"), 
						JDBCConn.rs.getString("USER_PW"), 
						JDBCConn.rs.getString("USER_PHONE"), 
						JDBCConn.rs.getString("USER_EMAIL"),
									 "0");
			}
			UserLogDAO.userLog(logOutUser.getId(), logOutUser.getName(), "����");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//����� ��ü close
			JDBCConn.closeConnStmtRs(JDBCConn.conn, JDBCConn.pstmt, JDBCConn.rs);
			
		}
	}
	
	
	
	
	// **ȸ������ ���� �Է� �޼���
	// UserVO��ü�� ������ DB�� �ش� ���� ���� �Է�
	// LOG > true�̸� userLog id, name, act(ȸ������)
	// �����ϸ� result 1�� ��ȯ
	// user������ �Է� �޾� 
	public static boolean signUp(String id, String name, String pw, String phone, String email) {
		boolean signUpcmpt = false;
		//DB ����̹� ����
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}

		int result = 0;
		try {
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			
			//SQL������ �ۼ��ؼ� Statement�� �����ϰ� sql�� ���� ��û
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_INFO ");
			sql.append("VALUES(?,?,?,?,?,(SELECT NVL(MAX(USER_SEQNUM), 0) + 1 FROM USER_INFO)) "); 

			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			//?(Q���ε�����)�� �����Ű��
			JDBCConn.pstmt.setString(1, id);
			JDBCConn.pstmt.setString(2, name);
			JDBCConn.pstmt.setString(3, pw);
			JDBCConn.pstmt.setString(4, phone);
			JDBCConn.pstmt.setString(5, email);
			result = JDBCConn.pstmt.executeUpdate();
			
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
			JDBCConn.closeConnStmt(JDBCConn.conn, JDBCConn.pstmt);
		}
		
		//inputUserInfo insert������ �Ǿ����� true
		
		return signUpcmpt;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
