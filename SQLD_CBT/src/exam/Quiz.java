package exam;

import java.util.Scanner;

public class Quiz {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input;
		while (true) {
			//1 
			System.out.println("Quiz방에 입장하셨습니다.");
			System.out.println("방장 >> 키워드 - sqld1 : 과목 1");
			System.out.println("방장>> 키워드 - sqld2 : 과목 2");
			System.out.println("방장>> 키워드 - sqld3 : 과목 3");
			System.out.println("해당 키워드를 입력 하시면 해당 과목의 문제가 출제됩니다.");
			
			while(true) {
				
				System.out.print(">>");
				input = scan.nextLine();
				switch (keyword(input)) {
					case 1 :
						System.out.println("1과목 문제");
						break;
					case 2 :
						System.out.println("2과목 문제");
						break;
					case 3 :
						System.out.println("3과목 문제");
						break;
					default :
						break;
				}
				
				System.out.println("문제출력메소드");
				System.out.println("정답출력메소드");
				System.out.println("해설출력메소드");
				System.out.println("");
				 
			}
		}
	}
	
	
}
