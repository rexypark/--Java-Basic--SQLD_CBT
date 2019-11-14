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

	private static final String IP = "localhost";
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	
	private static final String URL = "jdbc:oracle:thin:@"+ IP + ":1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	public static UserVO userInfo;
	
	static {
		try {
			//1. JDBC 드라이버 로딩
			Class.forName(DRIVER);
			System.out.println(">> JDBC 드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(">> JDBC 드라이버 로딩 실패");
		}
		
		
		}
	
	
	//로그인 ID/PW 체크
	//DB에서 ID와 PW가 있으면 로그인 성공
	//성공 시 true 리턴
	//true이면 userLog id, name, act(접속)
	public boolean checkIdPassword(String id, String password) {
		boolean result = false;
		
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			
			//DB에서 ID와 PW가 함께 있으면 로그인 성공
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ? AND USER_PW = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			// rs에 해당 데이터가 들어가면 result > true
			// userInfo에 위에서 select한 모든 컬럼의 데이터들을 저장
			if(rs.next()) {
				result = true;
				userInfo = new UserVO(rs.getString("USER_ID"), 
								  	 rs.getString("USER_NAME"), 
									 rs.getString("USER_PW"), 
									 rs.getString("USER_PHONE"), 
									 rs.getString("USER_EMAIL"),
									 "0");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//사용한 객체 close
			JDBC_Close.closeConnectionStmtRs(conn, pstmt, rs);
			
		}
		//result true or false반환
		return result;
	}

	
	
	// User에 있는 모든 데이터를 출력
	public List<UserVO> selectUserAll() {
		List<UserVO> list = new ArrayList<>();
		
		//DB연결 - Connection 객체 생성(DB와 연결된)
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//PreparedStatement 객체 생성하고 SQL문 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("ORDER BY USER_SEQNUM");
		
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
				
			//SQL문 실행 결과에 대한 처리
			while (rs.next()) {
				list.add(new UserVO(rs.getString("USER_ID"), 
								    rs.getString("USER_NAME"), 
								    rs.getString("USER_PW"), 
								    rs.getString("USER_PHONE"), 
								    rs.getString("USER_EMAIL"),
								    "0"));
			}
			System.out.println("list data check : " + list.size());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//사용객체 close
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}

		return list;
	}//selectAll End


	
	// 아이디를 입력하면 userInfo에 해당 유저의 모든 정보가 
	// 들어간 UserVO객체를 생성한다.
	public UserVO selectUser(String id) {
		
		//DB연결 - Connection 객체 생성(DB와 연결된)
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//PreparedStatement 객체 생성하고 SQL문 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");

			pstmt.setString(1, id); 
			
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			//SQL문 실행 결과에 대한 처리
			while (rs.next()) {
				userInfo = new UserVO(rs.getString("USER_ID"), 
								      rs.getString("USER_NAME"), 
								      rs.getString("USER_PW"), 
								      rs.getString("USER_PHONE"), 
								      rs.getString("USER_EMAIL"),
								      "0");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//사용객체 close
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return userInfo;
	}//selectAll End
	
	
	// 회원가입 중 아이디의 중복을 검사한다.
	// 모든 아이디 중에 입력 받은 id가 DB에 있는지 확인
	// 있으면 true를 리턴 없으면 false
	public static boolean checkId(String id) {
		boolean result = false;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_INFO ");
			sql.append("WHERE USER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println(">> " + id + "사용중인 아이디입니다.");
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnectionStmtRs(conn, pstmt, rs);
		}
		
		return result;
	}
	
	// **회원가입 정보 입력 메서드
	// UserVO객체를 받으면 DB에 해당 유저 정보 입력
	// LOG > true이면 userLog id, name, act(회원가입)
	// 저장하면 result 1을 반환
	// user정보를 입력 받아 
	public boolean signUp(String user_id, String user_name, String user_pw, String user_phone, String user_email) {
		boolean signUpcmpt = false;
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			
			//SQL문장을 작성해서 Statement에 전달하고 sql문 실행 요청
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USER_INFO");
			sql.append("     (SELECT USER_ID, USER_NAME, USER_PW, USER_PHONE, USER_EMAIL,USER_SEQNUM) ");
			sql.append("VALUES(?,?,?,?,?,SEQ_USER_INFO.NEXTVAL) "); 
			pstmt = conn.prepareStatement(sql.toString());
			System.out.println(user_id + " " + user_name + " " + user_pw + " " + user_phone + " " + user_email);
			//?(Q바인딩변수)에 저장시키기
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_name);
			pstmt.setString(3, user_pw);
			pstmt.setString(4, user_phone);
			pstmt.setString(5, user_email);
			
			System.out.println("sql.toString() : " + sql.toString());
			result = pstmt.executeUpdate();
			
			// 만약 insert 되면 
			// 리턴값 signUpcmpt에 true
			if (result == 1) {
				System.out.println(user_id + " " + user_name + " " + user_pw + " " + user_phone + " " + user_email + "if");
				userInfo = new UserVO(rs.getString("USER_ID"), 
								      rs.getString("USER_NAME"), 
								      rs.getString("USER_PW"), 
								      rs.getString("USER_PHONE"), 
								      rs.getString("USER_EMAIL"),
								      "0");
				signUpcmpt = true;
				}
							 	  
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt);
		}
		
		//inputUserInfo insert실행이 되었으면 true
		
		return signUpcmpt;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
