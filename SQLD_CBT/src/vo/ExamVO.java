package vo;

public class ExamVO {
		
		private String qwestion;      //(QUESTION)       ### EXAM_INFO ###
		private String answer;        //(ANSWER)
		private String section;       //(SECTION)
		private String examSeq;		  //(EXAM_SEQNUM)	
		private String answerInfo;    //(ANSWER_INFO)
		
		
		//EXAM_INFO용 생성자
		public ExamVO(String qwestion, String answer, String section, String examSeq, String answerInfo) {
			super();
			this.qwestion = qwestion;
			this.answer = answer;
			this.section = section;
			this.examSeq = examSeq;
			this.answerInfo = answerInfo;
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

		public String getExamSeq() {
			return examSeq;
		}

		public void setExamSeq(String examSeq) {
			this.examSeq = examSeq;
		}

		public String getAnswerInfo() {
			return answerInfo;
		}

		public void setAnswerInfo(String answerInfo) {
			this.answerInfo = answerInfo;
		}

		
		
		@Override
		public String toString() {
			return "ExamVO [qwestion=" + qwestion + ", answer=" + answer + ", section=" + section + ", examSeq="
					+ examSeq + ", answerInfo=" + answerInfo + "]";
		}
		
		

		
		
		

}
