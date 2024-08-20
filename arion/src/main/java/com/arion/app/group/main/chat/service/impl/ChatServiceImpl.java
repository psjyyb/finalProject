package com.arion.app.group.main.chat.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arion.app.group.main.chat.mapper.ChatMapper;
import com.arion.app.group.main.chat.service.ChatService;
import com.arion.app.group.main.chat.service.ChatVO;

@Service
public class ChatServiceImpl implements ChatService{
	
	private ChatMapper chatMapper;
	
	public ChatServiceImpl (ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}
	
	
	@Override
	public List<ChatVO> selectRoomNo(int roomNo) {
		return chatMapper.selectRoomNo(roomNo);
	}
}
