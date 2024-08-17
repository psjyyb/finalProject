package com.arion.app.group.main.chat.service.impl;

import org.springframework.stereotype.Service;

import com.arion.app.group.main.chat.service.ChatMessageDTO;
import com.arion.app.group.main.chat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {
	@Override
	public void processMessage(ChatMessageDTO message) {
		// 여기서 비즈니스 로직을 처리합니다.
		System.out.println("Received message from " + message.getSender() + ": " + message.getContent());
	}
}
