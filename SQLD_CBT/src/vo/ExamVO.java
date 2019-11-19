package vo;

public class ExamVO {
		
		private String qwestion;      //(QUESTION)       ### EXAM_INFO ###
		private String answer;        //(ANSWER)
		private String section;       //(SECTION)
		private String examSeq;		  //(EXAM_SEQNUM)	
		private String answerInfo;    //(ANSWER_INFO)
		
		private String allTotal;
		private String oTotal;
		private String xTotal;
		
		
		//EXAM_INFO遂 持失切
		public ExamVO(String qwestion, String answer, String section, String examSeq, String answerInfo) {
			super();
			this.qwestion = qwestion;
			this.answer = answer;
			this.section = section;
			this.examSeq = examSeq;
			this.answerInfo = answerInfo;
		}
		
		//SCORE_INFO遂 持失切
		public ExamVO(String allTotal, String oTotal, String xTotal) {
			super();
			this.allTotal = allTotal;
			this.oTotal = oTotal;
			this.xTotal = xTotal;
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


		public String getAllTotal() {
			return allTotal;
		}

		public void setAllTotal(String allTotal) {
			this.allTotal = allTotal;
		}

		public String getoTotal() {
			return oTotal;
		}

		public void setoTotal(String oTotal) {
			this.oTotal = oTotal;
		}

		public String getxTotal() {
			return xTotal;
		}

		public void setxTotal(String xTotal) {
			this.xTotal = xTotal;
		}

		@Override
		public String toString() {
			return "ExamVO [qwestion=" + qwestion + ", answer=" + answer + ", section=" + section + ", examSeq="
					+ examSeq + ", answerInfo=" + answerInfo + "]";
		}
		
		

		
		
		

}
