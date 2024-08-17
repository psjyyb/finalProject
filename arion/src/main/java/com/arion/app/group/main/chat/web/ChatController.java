package com.arion.app.group.main.chat.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.arion.app.group.main.chat.service.ChatMessageDTO;
import com.arion.app.group.main.chat.service.ChatService;


@RestController
public class ChatController {

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public ChatMessageDTO sendMessage(ChatMessageDTO message) {
		chatService.processMessage(message);
		return message;
	}
}
