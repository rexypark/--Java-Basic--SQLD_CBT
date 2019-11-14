package com.mystudy.sqld_cbt;

public class UserSign {
	
	public static void main(String[] args) {
		//scan값
		
		
	}
	
	
	public static boolean signUp(String user_id, String user_name, String user_pw, String user_phone, String user_email) {
		boolean signUpcmpt = false;
		
		UserDAO dao = new UserDAO();
		UserVO userVO = new UserVO(user_id, user_name, user_pw, user_phone, user_email);
		//inputUserInfo insert실행이 되었으면 true
		if (dao.inputUserInfo(userVO) == 1) {
			signUpcmpt = true;
			}
		return signUpcmpt;
	}
	
	
	
}
