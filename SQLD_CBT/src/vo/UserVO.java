package vo;


public class UserVO {
		//DB의 MEMBER의 테이블과 동일한 형태 필드 선언
		private String id;            //(USER_ID)        ### USER_INFO 컬럼 LIST ###
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
		
		
		//USER_INFO 전체 SELECT용 생성자
		public UserVO(String id, String name, String pw, String phone, String email, String seqnum) {
			super();
			this.id = id;
			this.name = name;
			this.pw = pw;
			this.phone = phone;
			this.email = email;
			this.seqnum = seqnum;
		}

		//USER_LOG용 생성자
		public UserVO(String id, String name, String act, String time) {
			super();
			this.id = id;
			this.name = name;
			this.act = act;
			this.time = time;
		}
		
		
		//EXAM_INFO용 생성자
		public UserVO(String seqnum, String qwestion, String answer, String section, String answerInfo) {
			super();
			this.seqnum = seqnum;
			this.qwestion = qwestion;
			this.answer = answer;
			this.section = section;
			this.answerInfo = answerInfo;
		}

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

		public String getPw() {
			return pw;
		}

		public void setPw(String pw) {
			this.pw = pw;
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

		public String getAct() {
			return act;
		}

		public void setAct(String act) {
			this.act = act;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getQwestion() {
			return qwestion;
		}

		public void setQwestion(String qwestion) {
			this.qwestion = qwestion;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

		public String getAnswerInfo() {
			return answerInfo;
		}

		public void setAnswerInfo(String answerInfo) {
			this.answerInfo = answerInfo;
		}

		@Override
		public String toString() {
			return "UserVO [id=" + id + ", name=" + name + ", pw=" + pw + ", phone=" + phone + ", email=" + email
					+ ", seqnum=" + seqnum + ", act=" + act + ", time=" + time + ", qwestion=" + qwestion + ", answer="
					+ answer + ", section=" + section + ", answerInfo=" + answerInfo + "]";
		}

}
