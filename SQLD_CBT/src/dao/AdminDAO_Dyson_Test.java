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
		System.out.println("|               ȯ�� �մϴ� Administrator��                                    |");
		System.out.println("|  1. ���� ������ ����Ȯ��  | 2. ȸ������Ȯ��  | 3. ��� �������� Ȯ��     |");
		System.out.println("|--------------------------------------------------------|");
		System.out.print("   => ��ȣ�� �Է��� �ּ��� : ");
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
