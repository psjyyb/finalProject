package com.arion.app.group.main.chat.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.main.chat.service.ChatRoomService;
import com.arion.app.group.main.chat.service.ChatRoomVO;
import com.arion.app.group.main.chat.service.ChatVO;

@Controller
public class ChatRoomController {

	private ChatRoomService chatRoomService;

	public ChatRoomController(ChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@PostMapping("/chat/chatrooms")
	@ResponseBody
	public ChatRoomVO createChatRoom(@RequestBody ChatRoomVO chatRoomVO, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		chatRoomVO.setCompanyCode(comCode);
		return chatRoomService.createChatRoom(chatRoomVO);
	}
	@PostMapping("/chat/exitRoom")
	@ResponseBody
	public Map<String,Object> exitChatRoom(@RequestBody ChatVO chatVO){
	return chatRoomService.chatRoomExit(chatVO);
	}
}
