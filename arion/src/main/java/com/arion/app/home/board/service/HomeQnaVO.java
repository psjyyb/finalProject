package com.arion.app.home.board.service;

import java.util.Date;

import lombok.Data;

@Data
public class HomeQnaVO {
	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private String qnaWriter;
	private Date qnaDate;
	private String qnaCompany;
	private String qnaReply;
	private String qnaState;
	private String qnaPw;
}
