package com.arion.app.group.main.chat.web;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.arion.app.group.main.chat.domain.Messages;
import com.arion.app.group.main.chat.repository.ChatMessageRepository;


@Controller
public class ChatController {
	   @Autowired
	    private ChatMessageRepository chatMessageRepository;

	    @MessageMapping("/chat")
	    @SendTo("/topic/messages")
	    public Messages sendMessage(Messages message) {
	    	System.out.println(message);
	        message.setSendAt(LocalDateTime.now()); // 현재 시간 설정
	        chatMessageRepository.save(message); // 메시지 DB 저장
	        return message;
	    }

}
