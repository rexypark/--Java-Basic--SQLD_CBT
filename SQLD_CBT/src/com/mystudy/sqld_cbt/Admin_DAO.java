package com.mystudy.sqld_cbt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Admin_DAO {

	//DAO, Dao : Data Access Object / Database Access Object
	//데이터(데이터베이스-DB)와 연동해서 CRUD를 구현한 클래스

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.69:1521:xe";
	private static final String USER = "SQLD_CBT";
	private static final String PASSWORD = "sqld";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	Scanner scan = new Scanner(System.in);
	
	//드라이버 로딩 처리
	//static 초기화 구문 (JVM이 실행될 때 한 번만 실행된다.)
	static{
		try {
			Class.forName(DRIVER);
			System.out.println(">> JDBC Driver Loading Success");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] JDBC Driver Loading Fail");
			
		}
	}
	
	
	
	//1.현재 접속자 정보
	
	
	
	//2.전체 회원정보 list
	public void userListAll(List<MemberVO> list) {
		 System.out.println("|=======================================================================|");
		 System.out.println("| Index |    User ID    |   Name   |  Password  |   Phone   |   E-Mail  |");
		for (MemberVO mvo : list) {
			System.out.println("|   " + mvo.getSeqnum() + "\t  " + mvo.getId() + "\t   " + mvo.getName() + "\t  " 
		                            + mvo.getPassword() +
					                 mvo.getPhone() + mvo.getEmail());
		}
				
		
	}
	
	
	//3.장기 미접속자
	
	

	public List<MemberVO> selectAll() {
		List<MemberVO> list = new ArrayList<>();
		
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
				list.add(new MemberVO(						  
									  rs.getString("USER_ID"),
									  rs.getString("USER_NAME"),
									  rs.getString("USER_PW"),
									  rs.getString("USER_PHONE"),
									  rs.getString("USER_EMAIL"),
									  rs.getString("USER_SEQNUM")));
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
	
	
}
