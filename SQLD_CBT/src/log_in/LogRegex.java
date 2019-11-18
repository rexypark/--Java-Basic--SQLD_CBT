package log_in;

import java.util.regex.Pattern;

public class LogRegex {
	
	
	public static void main(String[] args) {
		String pw = "hh23231i@@";
		isPW(pw);		
	}
	
	//ÇÑ±Û ¾Æ´Ñ Á¤±Ô½Ä
	public static int isKor(String txt) {
		if(txt.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]{1,5}$")) {
			return 1;
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÈ ¹®ÀÚ¿­
		} else {
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÇÁö ¾ÊÀº ¹®ÀÚ¿­
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
	
	
	//¾ÆÀÌµğ Á¤±Ô½Ä ¿µ¹®,¼ıÀÚ¸¸ °¡´É 5 ~ 15ÀÚÀÌÇÏ
	public static int isId(String txt) {
		if(txt.matches("^[a-z]{1}[a-z0-9]{4,16}$")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	//¿µ¹®(´ë¼Ò¹®ÀÚ ±¸ºĞ), ¼ıÀÚ, Æ¯¼ö¹®ÀÚ Á¶ÇÕ, 9~12ÀÚ¸®isPW
	//ºñ¹Ğ¹øÈ£
	//   "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"
	public static int isPW(String txt) {
		
		if(txt.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$")) {
			return 1;
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÈ ¹®ÀÚ¿­
		} else {
			return 0;
		}
}
	
	public static int isPhone(String txt) {
		if(txt.matches("^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")) {
			return 1;
		// ÀüÈ­¹øÈ£ ¾ç½Ä ¾Æ´Ô
		} else {
			return 0;
		}
	}
//	String regex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,16}$";
//	String regex = "^[a-z]{1}[a-z0-9]{4,16}$";

}
