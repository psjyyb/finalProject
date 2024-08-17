package com.arion.app.group.main.chat.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMemberVO {
	private int chatmembersNo;
	private int employeeNo;
	private int roomNo;
	private Date joinDate;
}
