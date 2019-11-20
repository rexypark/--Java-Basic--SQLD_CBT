package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vo.UserVO;


public class AdminDAO_Dyson_Test {
	public static void main(String[] args) {
		AdminDAO dao = new AdminDAO();
		Scanner scan = new Scanner(System.in);
		UserLogDAO udao = new UserLogDAO();
		
		String select = "";
		
		System.out.println("|========================================================|");
		System.out.println("|               환영 합니다 Administrator님                                    |");
		System.out.println("|  1. 현재 접속자 정보확인  | 2. 회원정보확인  | 3. 장기 미접속자 확인     |");
		System.out.println("|--------------------------------------------------------|");
		System.out.print("   => 번호를 입력해 주세요 : ");
		select = scan.nextLine();
		System.out.println("|========================================================|");
		clearScreen();
		switch (select) {
		 case "1" :
			 dao.nowUserList();
			 break;
		 case "2" :
			 List<UserVO> list = dao.selectAll();
			 dao.userListAll(list);
			 break;
		 case "3" :
			 
		}
		
	}
	
	  public static void clearScreen() {
		    for (int i = 0; i < 80; i++)
		      System.out.println("");
		  }

}
