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
	private String companyCode;			//회사코드
	private String rankName;			//직급
	private String departmentName;		//부서명
	private int parentNo;		//답글
	
	
	
	private int employeeNo;				//사원번호
}
