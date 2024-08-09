package com.arion.app.group.board.service;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PageDTO {
	private int pageCount; // 화면 하단 출력할 페이지 ex) 1~5, 1~10
	private int startPage; //페이지그룹내에서 시작페이지번호
	private int endPage; //페이지그룹내에서 마지막페이지번호
	private int realEnd; // 실제 마지막 page번호
	private boolean prev, next;
	private int total; // 전체 데이터 개수
	private Criteria criteria; // 
	
	public PageDTO() {;}

	public PageDTO(int pageCount, int total, Criteria criteria) {
		super();
		this.pageCount = pageCount;
		this.total = total;
		this.criteria = criteria;
		
		this.endPage = (int)(Math.ceil(criteria.getPageNum()*1.0/pageCount))*pageCount;
		this.startPage = endPage - (pageCount-1);
		
		realEnd = (int)(Math.ceil(total*1.0 / criteria.getAmount()));
		
		if(endPage > realEnd) {
			endPage = realEnd == 0 ? 1 : realEnd;
		}
		
		prev = startPage > 1;
		next = endPage < realEnd;
		
		
	}
}
