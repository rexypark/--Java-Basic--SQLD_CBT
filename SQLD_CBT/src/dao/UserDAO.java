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

	//로그인 ID/PW 체크
	//DB에서 ID와 PW가 있으면 로그인 성공
	//성공 시 true 리턴
	//true이면 userLog id, name, act(접속)
	public boolean checkIdPassword(String id, String password) {
		boolean result = false;
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		try {
			//DB 드라이버 연결이 안되었을 시 DB 드라이버 연결
			
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			
			//DB에서 ID와 PW가 함께 있으면 로그인 성공
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ? AND USER_PW = ? ");
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.pstmt.setString(1, id);
			DbConn.pstmt.setString(2, password);
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			// rs에 해당 데이터가 들어가면 result > true
			// userInfo에 위에서 select한 모든 컬럼의 데이터들을 저장
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
			//사용한 객체 close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
			
		}
		//result true or false반환
		return result;
	}

	
	
	// User에 있는 모든 데이터를 출력
	public List<UserVO> selectUserAll() {
		List<UserVO> list = new ArrayList<>();
	
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		//DB연결 - Connection 객체 생성(DB와 연결된)
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			//PreparedStatement 객체 생성하고 SQL문 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("ORDER BY USER_SEQNUM");
		
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.rs = DbConn.pstmt.executeQuery();
				
			//SQL문 실행 결과에 대한 처리
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
			//사용객체 close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}

		return list;
	}//selectAll End


	
	// 아이디를 입력하면 userInfo에 해당 유저의 모든 정보가 
	// 들어간 UserVO객체를 생성한다.
	public UserVO selectUser(String id) {
		
		//DB연결 - Connection 객체 생성(DB와 연결된)
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			//PreparedStatement 객체 생성하고 SQL문 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");

			DbConn.pstmt.setString(1, id); 
			
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.rs = DbConn.pstmt.executeQuery();
			
			//SQL문 실행 결과에 대한 처리
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
			//사용객체 close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}
		return userInfo;
	}//selectAll End
	
	
	// 회원가입 중 아이디의 중복을 검사한다.
	// 모든 아이디 중에 입력 받은 id가 DB에 있는지 확인
	// 있으면 true를 리턴 없으면 false
	public static boolean checkId(String id) {
		boolean result = false;
		
		//DB 드라이버 연결
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
				System.out.println(">> " + id + "사용중인 아이디입니다.");
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}
		
		return result;
	}
	
	// **회원가입 정보 입력 메서드
	// UserVO객체를 받으면 DB에 해당 유저 정보 입력
	// LOG > true이면 userLog id, name, act(회원가입)
	// 저장하면 result 1을 반환
	// user정보를 입력 받아 
	public static boolean signUp(String id, String name, String pw, String phone, String email) {
		boolean signUpcmpt = false;
		//DB 드라이버 연결
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}

		int result = 0;
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			
			//SQL문장을 작성해서 Statement에 전달하고 sql문 실행 요청
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_INFO ");
			sql.append("VALUES(?,?,?,?,?,(SELECT NVL(MAX(USER_SEQNUM), 0) + 1 FROM USER_INFO)) "); 

			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			//?(Q바인딩변수)에 저장시키기
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
			
			
			// 만약 insert 되면 
			// 리턴값 signUpcmpt에 true
			if (result == 1) {
				signUpcmpt = true;
				}
							 	  
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmt(DbConn.conn, DbConn.pstmt);
		}
		
		//inputUserInfo insert실행이 되었으면 true
		
		return signUpcmpt;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
