package com.arion.app.admin.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class QnAVO {
	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private String qnaWriter;
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 파라미터 자동변환
	private Date qnaDate;
	private String qnaCompany;
	private String qnaReply;
	private String qnaState;
}
