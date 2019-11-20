package dao;

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
	
	//1.현재 접속자 정보
	public void nowUserList() {

		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		List<UserVO> list = new ArrayList<>();
		//DB연결 - Connection 객체 생성(DB와 연결된)
		try {
			DbConn.conn = DriverManager.getConnection(DbConn.URL, DbConn.USER, DbConn.PASSWORD);
			
			//PreparedStatement 객체 생성하고 SQL문 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM USER_LOG ");
			sql.append("WHERE USER_ACT = '로그인' ");
			sql.append("ORDER BY L_USER_ID");
		
			DbConn.pstmt = DbConn.conn.prepareStatement(sql.toString());
			
			DbConn.rs = DbConn.pstmt.executeQuery();
				
			//SQL문 실행 결과에 대한 처리
			while (DbConn.rs.next()) {
				list.add(new UserVO(
									DbConn.rs.getString("L_USER_ID"), 
									DbConn.rs.getString("L_USER_NAME"),
									DbConn.rs.getString("USER_ACT"),
									DbConn.rs.getString("USER_TIME")));
			}
			
			System.out.println("list data check : " + list.size());
			
			System.out.println("|====================================================================|");
			System.out.println("|    User ID    |       Name      |   접속 여부    |        접속 시간              |");
			for (UserVO mvo : list) { 
				System.out.printf("%10s %16s %23s %29s \n", mvo.getId(), mvo.getName(), mvo.getAct()
						          , mvo.getTime());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//사용객체 close
			JDBC_Close.closeConnStmtRs(DbConn.conn, DbConn.pstmt, DbConn.rs);
		}
		
	}// nowUserList End
	
	
	//2.전체 회원정보 list
	public void userListAll(List<UserVO> list) {
		 System.out.println("|=====================================================================================================================|");
		 System.out.println("| Index |       User ID        |       Name      |     Password    |       Phone       |             E-Mail           |");
		for (UserVO mvo : list) { 
			System.out.printf("%5s %20s %15s %30s %20s %28s \n", mvo.getSeqnum(), mvo.getId(), mvo.getName()
					          , mvo.getPw(), mvo.getPhone(), mvo.getEmail());
		}
	}// userListAll End
	
	
	//3.장기 미접속자
	public List<UserVO> selectAll() {
		
		if (DbConn.result == 0) {
			DbConn.driverLoad();
		}
		
		List<UserVO> list = new ArrayList<>();
		
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
				list.add(new UserVO(						  
									DbConn.rs.getString("USER_ID"),
									DbConn.rs.getString("USER_NAME"),
									DbConn.rs.getString("USER_PW"),
									DbConn.rs.getString("USER_PHONE"),
									DbConn.rs.getString("USER_EMAIL"),
									DbConn.rs.getString("USER_SEQNUM")));
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
	
	
}
