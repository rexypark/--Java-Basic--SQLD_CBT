package log_in;

import java.util.regex.Pattern;

public class LogRegex {
	
	
	public static void main(String[] args) {
		String pw = "hh23231i@@";
//		String email = "asdfasdf@a.a";
//		isKor(id);
//		if (isEmail(email)) {
//			System.out.println("email Çü½ÄÀÌ´Ù");
//		} else {
//			System.out.println("¾Æ´Ï´Ù");
//		}
		
		//¿µ¹®(´ë¼Ò¹®ÀÚ ±¸ºĞ), ¼ıÀÚ, Æ¯¼ö¹®ÀÚ Á¶ÇÕ, 9~12ÀÚ¸®
		isPW(pw);		
	}
	
	//ÇÑ±Û ¾Æ´Ñ Á¤±Ô½Ä
	public static void isKor(String txt) {
		if(txt.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*")) {
			System.out.println("ÇÑ±Û");
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÈ ¹®ÀÚ¿­
		} else {
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÇÁö ¾ÊÀº ¹®ÀÚ¿­
			System.out.println("ÇÑ±Û ¾Æ´Ô");
		}
	}
	
	public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches(
            "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", 
            email.trim());
        return b;
	}
	
	
	//¾ÆÀÌµğ Á¤±Ô½Ä ¿µ¹®,¼ıÀÚ¸¸ °¡´É 5 ~ 15ÀÚÀÌÇÏ
	public static void isId(String txt) {
		if(txt.matches("^[a-z]{1}[a-z0-9]{4,16}$")) {
			System.out.println("¾ÆÀÌµğ");
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÈ ¹®ÀÚ¿­
		} else {
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÇÁö ¾ÊÀº ¹®ÀÚ¿­
			System.out.println("¾ÆÀÌµğÇü½Ä ¾Æ´Ô");
		}
	}
	
	//¿µ¹®(´ë¼Ò¹®ÀÚ ±¸ºĞ), ¼ıÀÚ, Æ¯¼ö¹®ÀÚ Á¶ÇÕ, 9~12ÀÚ¸®isPW
	//ºñ¹Ğ¹øÈ£
	//   "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"
	public static void isPW(String txt) {
		
		if(txt.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$"));
			System.out.println("ºñ¹Ğ¹øÈ£");
		// ÇÑ±ÛÀÌ Æ÷ÇÔµÈ ¹®ÀÚ¿­
	}
//	String regex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,16}$";
//	String regex = "^[a-z]{1}[a-z0-9]{4,16}$";

}
