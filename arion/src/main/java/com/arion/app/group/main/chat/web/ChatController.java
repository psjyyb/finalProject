package com.arion.app.group.main.chat.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.main.chat.domain.Messages;
import com.arion.app.group.main.chat.repository.ChatMessageRepository;
import com.arion.app.group.main.chat.service.ChatService;
import com.arion.app.group.main.chat.service.ChatVO;

@Controller
public class ChatController {

	@Autowired
	private ChatMessageRepository chatMessageRepository;
	private ChatService chatService;

	public ChatController(ChatMessageRepository chatMesaageRepository,ChatService chatService) {
	    this.chatMessageRepository = chatMesaageRepository;
	    this.chatService = chatService;
}

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public Messages sendMessage(Messages message) {
		System.out.println(message);
		message.setSendAt(LocalDateTime.now()); // 현재 시간 설정
		chatMessageRepository.save(message); // 메시지 DB 저장
		return message;
	}

	@PostMapping("/group/chatRoom")
	@ResponseBody
	public List<ChatVO> selectRoom(@RequestParam int roomNo) {
		System.out.println(roomNo);
		List<ChatVO> list =  chatService.selectRoomNo(roomNo);
		System.out.println(list);
		return list;
	}

}
