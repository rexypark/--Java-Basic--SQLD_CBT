package com.mystudy.sqld_cbt;

//VO : Value Object
public class MemberVO {
	//DB의 USER_INFO 테이블과 동일한 형태 필드 선언
	private String id;       //(USER_ID)
	private String name;     //(USER_NAME)
	private String password; //(USER_PW)
	private String phone;    //(USER_PHONE)
	private String email;    //(USER_EMAIL)
	private String seqnum;   //(USER_SEQNUM)
	
	public MemberVO(String id, String name, String password, String phone, String email, String seqnum) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.seqnum = seqnum;
	}

	
	//getter, setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}

	//tostring
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", name=" + name + ", password=" + password + ", phone=" + phone + ", email="
				+ email + ", seqnum=" + seqnum + "]";
	}

}
