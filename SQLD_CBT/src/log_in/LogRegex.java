package log_in;

import java.util.regex.Pattern;

public class LogRegex {
	
	
	public static void main(String[] args) {
		String pw = "hh23231i@@";
		isPW(pw);		
	}
	
	//�ѱ� �ƴ� ���Խ�
	public static int isKor(String txt) {
		if(txt.matches(".*[��-����-�Ӱ�-�R]{1,5}$")) {
			return 1;
		// �ѱ��� ���Ե� ���ڿ�
		} else {
		// �ѱ��� ���Ե��� ���� ���ڿ�
			return 0;
		}
	}
	
	public static int isEmail(String txt) {
		if(txt.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	//���̵� ���Խ� ����,���ڸ� ���� 5 ~ 15������
	public static int isId(String txt) {
		if(txt.matches("^[a-z]{1}[a-z0-9]{4,16}$")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	//����(��ҹ��� ����), ����, Ư������ ����, 9~12�ڸ�isPW
	//��й�ȣ
	//   "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"
	public static int isPW(String txt) {
		
		if(txt.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$")) {
			return 1;
		// �ѱ��� ���Ե� ���ڿ�
		} else {
			return 0;
		}
}
	
	public static int isPhone(String txt) {
		if(txt.matches("^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")) {
			return 1;
		// ��ȭ��ȣ ��� �ƴ�
		} else {
			return 0;
		}
	}
//	String regex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,16}$";
//	String regex = "^[a-z]{1}[a-z0-9]{4,16}$";

}
