package com.arion.app.group.main.chat.service;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChatVO {
	private int messageNo;
	private String senderId;
	private int employeeNo;
	private String content;
	@DateTimeFormat(pattern = "MM-dd hh:mm")
	private LocalDate sendAt;
	private int roomNo;
	private String sendTime;
}
