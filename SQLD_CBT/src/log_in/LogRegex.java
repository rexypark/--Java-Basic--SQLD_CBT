package log_in;

import java.util.regex.Pattern;

public class LogRegex {
	
	
	public static void main(String[] args) {
		String pw = "hh23231i@@";
		isPW(pw);		
	}
	
	//문자열이 정수인지 아닌지
	public static boolean isiInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
		
	//한글 아닌 정규식
	public static int isKor(String txt) {
		if(txt.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]{1,5}$")) {
			return 1;
		} else {
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
	
	
	//아이디 정규식 영문,숫자만 가능 5 ~ 15자이하
	public static int isId(String txt) {
		if(txt.matches("^[a-z]{1}[a-z0-9]{4,16}$")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	//영문(대소문자 구분), 숫자, 특수문자 조합, 9~12자리isPW
	//비밀번호
	//   "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"
	public static int isPW(String txt) {
		
		if(txt.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$")) {
			return 1;
		// 한글이 포함된 문자열
		} else {
			return 0;
		}
}
	
	public static int isPhone(String txt) {
		if(txt.matches("^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")) {
			return 1;
		// 전화번호 양식 아님
		} else {
			return 0;
		}
	}
//	String regex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,16}$";
//	String regex = "^[a-z]{1}[a-z0-9]{4,16}$";

}
