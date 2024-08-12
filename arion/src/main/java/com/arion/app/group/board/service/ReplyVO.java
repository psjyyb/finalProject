package com.arion.app.group.board.service;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	
	private int boardNo;				//게시글 번호
	private int commentNo;				//댓글 번호
	private String commentContent;		//댓글 내용
	private String commentWrite;		//댓글 작성자
	private Date commentDate;			//댓글 작성일
	private int employeeNo;				
	private String employeeName;				
}
