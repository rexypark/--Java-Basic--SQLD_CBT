package com.mystudy.sqld_cbt;

public class UserSign {

	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		dao.checkIdPassword(id, password)
	}
	
		
	public static boolean signUp(String user_id, String user_name, String user_pw, String user_phone, String user_email) {
		UserDAO dao = new UserDAO();
		UserVO userVO = new UserVO(user_id, user_name, user_pw, user_phone, user_email);
		
		dao.inputUserInfo(userVO);
		
	}
}
