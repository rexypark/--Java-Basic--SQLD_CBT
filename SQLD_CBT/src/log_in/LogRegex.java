package log_in;

import java.util.regex.Pattern;

public class LogRegex {
	
	
	public static void main(String[] args) {
		String pw = "hh23231i@@";
//		String email = "asdfasdf@a.a";
//		isKor(id);
//		if (isEmail(email)) {
//			System.out.println("email �����̴�");
//		} else {
//			System.out.println("�ƴϴ�");
//		}
		
		//����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�
		isPW(pw);		
	}
	
	//�ѱ� �ƴ� ���Խ�
	public static void isKor(String txt) {
		if(txt.matches(".*[��-����-�Ӱ�-�R]+.*")) {
			System.out.println("�ѱ�");
		// �ѱ��� ���Ե� ���ڿ�
		} else {
		// �ѱ��� ���Ե��� ���� ���ڿ�
			System.out.println("�ѱ� �ƴ�");
		}
	}
	
	public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches(
            "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", 
            email.trim());
        return b;
	}
	
	
	//���̵� ���Խ� ����,���ڸ� ���� 5 ~ 15������
	public static void isId(String txt) {
		if(txt.matches("^[a-z]{1}[a-z0-9]{4,16}$")) {
			System.out.println("���̵�");
		// �ѱ��� ���Ե� ���ڿ�
		} else {
		// �ѱ��� ���Ե��� ���� ���ڿ�
			System.out.println("���̵����� �ƴ�");
		}
	}
	
	//����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�isPW
	//��й�ȣ
	//   "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"
	public static void isPW(String txt) {
		
		if(txt.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"));
			System.out.println("��й�ȣ");
		// �ѱ��� ���Ե� ���ڿ�
	}
//	String regex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,16}$";
//	String regex = "^[a-z]{1}[a-z0-9]{4,16}$";

}
