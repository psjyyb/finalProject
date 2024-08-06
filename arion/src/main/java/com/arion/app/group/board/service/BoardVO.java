package com.arion.app.group.board.service;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {

	private long boardNo;			//게시글 번호
	private String boardTitle;		//제목
	private String boardContent;	//내용
	private String employeeName;	//작성자 이름
	private Date regdate;			//작성일	yyyy. MM. dd
	private int viewcnt;		    //조회수
	private String image;			//첨부이미지
	private long employeeNo;		//작성자 번호
	
}
