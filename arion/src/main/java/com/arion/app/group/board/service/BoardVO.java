package com.arion.app.group.board.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BoardVO {

	private int boardNo;			 //게시글 번호
	private String boardTitle;		 //제목
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date writeDate;			 //작성일	yyyy.MM.dd
	private String boardContent;	 //내용
	private String boardCategory;	 //게시판 카테고리
	private int employeeNo;			 //사원번호 (게시글 작성자, 댓글 작성자)
	private String employeeName;	 //작성자 이름
	private String companyCode;		 //회사번호
	private int viewCnt;		     //조회수
	private String department;		 //부서이름	
	private int rankName;			 //직급이름
	
	private int fileNo;				 //파일번호
	private String fileName;		 //파일이름
	private String fileoriginalName; //파일원본이름
	private String filePath;		 //파일경로
	private int fileTurn;			 //파일순서
	private String fileType;		 //파일타입
	private String tableName;		 //테이블이름
	private int keyNo;				 //키번호
}
