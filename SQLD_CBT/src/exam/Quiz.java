package exam;

import java.util.Scanner;

public class Quiz {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input;
		while (true) {
			//1 
			System.out.println("Quiz�濡 �����ϼ̽��ϴ�.");
			System.out.println("���� >> Ű���� - sqld1 : ���� 1");
			System.out.println("����>> Ű���� - sqld2 : ���� 2");
			System.out.println("����>> Ű���� - sqld3 : ���� 3");
			System.out.println("�ش� Ű���带 �Է� �Ͻø� �ش� ������ ������ �����˴ϴ�.");
			
			while(true) {
				
				System.out.print(">>");
				input = scan.nextLine();
				switch (keyword(input)) {
					case 1 :
						System.out.println("1���� ����");
						break;
					case 2 :
						System.out.println("2���� ����");
						break;
					case 3 :
						System.out.println("3���� ����");
						break;
					default :
						break;
				}
				
				System.out.println("������¸޼ҵ�");
				System.out.println("������¸޼ҵ�");
				System.out.println("�ؼ���¸޼ҵ�");
				System.out.println("");
				 
			}
		}
	}
	
	
}
