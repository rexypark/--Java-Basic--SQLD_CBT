package vo;


public class UserVO {
		//DB�� MEMBER�� ���̺�� ������ ���� �ʵ� ����
		private String id;            //(USER_ID)        ### USER_INFO �÷� LIST ###
		private String name;          //(USER_NAME)
		private String pw;            //(USER_PW)
		private String phone;         //(USER_PHONE)
		private String email;         //(USER_EMAIL)
		private String seqnum;        //(USER_SEQNUM)
		
		private String act;           //(USER_ACT)
		private String time;          //(USER_TIME)
		
		private String qwestion;      //(QUESTION)       ### EXAM_INFO ###
		private String answer;        //(ANSWER)
		private String section;       //(SECTION)
		private String answerInfo;    //(ANSWER_INFO)
		
		
		//USER_INFO ��ü SELECT�� ������
		public UserVO(String id, String name, String pw, String phone, String email, String seqnum) {
			super();
			this.id = id;
			this.name = name;
			this.pw = pw;
			this.phone = phone;
			this.email = email;
			this.seqnum = seqnum;
		}

		//USER_LOG�� ������
		public UserVO(String id, String name, String act, String time) {
			super();
			this.id = id;
			this.name = name;
			this.act = act;
			this.time = time;
		}
		
		
		//EXAM_INFO�� ������
		public UserVO(String seqnum, String qwestion, String answer, String section, String answerInfo) {
			super();
			this.seqnum = seqnum;
			this.qwestion = qwestion;
			this.answer = answer;
			this.section = section;
			this.answerInfo = answerInfo;
		}

		
		

		
	
}
