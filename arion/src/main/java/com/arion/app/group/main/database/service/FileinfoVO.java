package com.arion.app.group.main.database.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FileinfoVO {
	private Integer me;
	private String uploader;
	private String filename;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date uploaddate;
	private Integer csize;
	private String forder;
	private String cname;
}
