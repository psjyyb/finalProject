	package com.arion.app.home.board.service;
	
	import java.util.Date;
	
	import org.springframework.format.annotation.DateTimeFormat;
	
	import lombok.Data;
	
	@Data
	public class HomeQnaVO {
		private int qnaNo;
		private String qnaTitle;
		private String qnaContent;
		private String qnaWriter;
		@DateTimeFormat(pattern="yyyy/MM/dd")
		private Date qnaDate;
		private String qnaCompany;
		private String qnaReply;
		private String qnaState;
		private String qnaPw;
		@DateTimeFormat(pattern="yyyy/MM/dd")
		private Date replyDate;
	}
