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
//데이터(데이터베이스-DB)와 연동해서 CRUD를 구현한 클래스
public class UserLogDAO {
	
	//회원가입, 접속, 종료 Event log method
	public static int userLog(String id, String name, String act) {
		int result = 0;
		
		if (JDBCConn.result == 0) {
			JDBCConn.driverLoad();
		}
		
		try {
			//DB연결 - Connection 객체 생성(DB와 연결된)
			JDBCConn.conn = DriverManager.getConnection(JDBCConn.URL, JDBCConn.USER, JDBCConn.PASSWORD);
			
			//SQL문장을 작성해서 Statement에 전달하고 SQL문 실행 요청
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_LOG ");
			sql.append("(L_USER_ID, L_USER_NAME, USER_ACT, USER_TIME) ");
			sql.append("VALUES (?, ?, ?, "
					+ "(TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))) ");
			JDBCConn.pstmt = JDBCConn.conn.prepareStatement(sql.toString());
			
			// ? (바인딩변수)에 값 매칭 시키기
			int idx = 1;
			JDBCConn.pstmt.setString(idx++, id);        //회원 아이디                    (L_USER_ID)
			JDBCConn.pstmt.setString(idx++, name);      //회원 이름                       (L_USER_NAME)
			JDBCConn.pstmt.setString(idx++, act);       //회원가입, 접속, 종료    (USER_ACT)
			
			//SQL 실행 요청
			result = JDBCConn.pstmt.executeUpdate();
			
			//실패 하면 3회 retry 한다.
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
