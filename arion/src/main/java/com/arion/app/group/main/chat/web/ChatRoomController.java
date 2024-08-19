package com.arion.app.group.main.chat.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.group.main.chat.service.ChatRoomService;
import com.arion.app.group.main.chat.service.ChatRoomVO;

@Controller
public class ChatRoomController {

	private ChatRoomService chatRoomService;

	public ChatRoomController(ChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@PostMapping("/chat/chatrooms")
	@ResponseBody
	public ChatRoomVO createChatRoom(@RequestBody ChatRoomVO chatRoomVO, HttpSession session) {
		System.out.println(chatRoomVO + "컨트롤러에서 찍기");
		String comCode = (String) session.getAttribute("companyCode");
		chatRoomVO.setCompanyCode(comCode);
		return chatRoomService.createChatRoom(chatRoomVO);
	}

}
