package com.arion.app.common.service;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class PageDTO2 {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private int page;
	private int size;
	
	public PageDTO2(int total, int page, int size) {
		this.total = total;
		this.page = page;
		this.size = size;
		
		this.endPage = (int)(Math.ceil(page / 10) * 10);
		this.startPage = this.endPage = 9;
		
		int realEnd = (int)(Math.ceil((total * 1) / size));
		
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
		
	}
}
