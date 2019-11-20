package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//DAO, Dao : Data Access Object / Database Access Object
//������(�����ͺ��̽�-DB)�� �����ؼ� CRUD�� ������ Ŭ����
public class UserLogDAO {
	
	//ȸ������, ����, ���� Event log method
	public static int userLog(String id, String name, String act) {
		int result = 0;
		
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		try {
			//DB���� - Connection ��ü ����(DB�� �����)
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			//SQL������ �ۼ��ؼ� Statement�� �����ϰ� SQL�� ���� ��û
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_LOG ");
			sql.append("(L_USER_ID, L_USER_NAME, USER_ACT, USER_TIME) ");
			sql.append("VALUES (?, ?, ?, "
					+ "(TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))) ");
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			// ? (���ε�����)�� �� ��Ī ��Ű��
			int idx = 1;
			JDBCConn.pstmt.setString(idx++, id);        //ȸ�� ���̵�                    (L_USER_ID)
			JDBCConn.pstmt.setString(idx++, name);      //ȸ�� �̸�                       (L_USER_NAME)
			JDBCConn.pstmt.setString(idx++, act);       //ȸ������, ����, ����    (USER_ACT)
			
			//SQL ���� ��û
			result = JDBCConn.pstmt.executeUpdate();
			
			//���� �ϸ� 3ȸ retry �Ѵ�.
			for (int i = 0; i < 3; i++) {
				if(result == 0) {
					result += JDBCConn.pstmt.executeUpdate();
				}else {
					break;
				}
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConn.closeConnStmt(JDBCConn.conn, JDBCConn.pstmt);		
		}
		
		return result;
	}// userLog End

}
