package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//DAO, Dao : Data Access Object / Database Access Object
//데이터(데이터베이스-DB)와 연동해서 CRUD를 구현한 클래스
public class ExamDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//드라이버 로딩 처리
	//static 초기화 구문
	static {
		try {
			Class.forName(DRIVER);
			System.out.println(">> 퀴즈 시작.");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] 드라이버 로딩 실패!!!");
		}
	}
		//SELECT : 테이블 전체 데이터 조회 - selectAll : List<MemberVO>
	public List<UserVO> selectAll() {
		List<UserVO> list = new ArrayList<>();
		
		try {
			//DB연결 - Connection 객체 생성(DB와 연결된)
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//PreparedStatement 객체 생성하고 SQL문 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT QUESTION, ANSWER, SECTION, EXAM_SEQNUM");
			sql.append("  FROM EXAM_INFO ");//조인
			sql.append(" ORDER BY QUESTION");
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			//SQL문 실행 결과에 대한 처리
			while (rs.next()) {
			 list.add(new UserVO(rs.getString("QUESTION"),
						rs.getString("ANSWER"),
						rs.getString("SECTION"),
						rs.getString("EXAM_SEQNUM")));
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//사용객체 close
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
	}
	

}
























