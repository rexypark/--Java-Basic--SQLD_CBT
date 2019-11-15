package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExamDAO_Test {

	public static void main(String[] args) {
		ExamDAO dao = new ExamDAO();
		List<UserVO> list = dao.selectAll();
		for (UserVO mvo : list ) {
		System.out.println("===========================");
		System.out.println(mvo.getQwestion());//문제
		
		System.out.println("---------------------------");
		Scanner scan = new Scanner(System.in);
		
		System.out.print("문자열 입력 : ");	
		
		String str1 = scan.nextLine();
		
		if(str1.equals(mvo.getAnswer())) {
			System.out.println("정답이다");
		}else {
			System.out.println("땡!!!!");
		}
		System.out.println("정답은 : " + mvo.getAnswer());
		System.out.println("---------------------------");
		
		}

	}

}
