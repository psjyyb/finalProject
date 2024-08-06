package com.arion.app.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class QnAVO {
	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private String qnaWriter;
	private Date qnaDate;
	private String qnaCompany;
	private String qnaReply;
	private String qnaState;
}
