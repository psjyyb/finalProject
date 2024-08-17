package com.arion.app.group.main.chat.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChatRoomVO {
	private int roomNo;
	private String chatroomName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	private String companyCode;
	private int[]  employeeIds;
}
