package com.arion.app.group.main.chat.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.group.admin.service.EmployeeVO;
import com.arion.app.group.admin.service.GroupAdminService;
import com.arion.app.group.main.chat.domain.Messages;
import com.arion.app.group.main.chat.repository.ChatMessageRepository;
import com.arion.app.group.main.chat.service.ChatRoomService;
import com.arion.app.group.main.chat.service.ChatRoomVO;

@Controller
public class ChatPageController {

	@Autowired
	private GroupAdminService gaService;
	private ChatMessageRepository chatMessageRepository;
	private ChatRoomService chatRoomService;

	public ChatPageController(GroupAdminService gaService, ChatMessageRepository chatMessageRepository,
			ChatRoomService chatRoomService) {
		this.gaService = gaService;
		this.chatMessageRepository = chatMessageRepository;
		this.chatRoomService = chatRoomService;
	}

	@GetMapping("/group/chat")
	public String getChatPage(Model model, HttpSession session) {
		int empNo = (Integer) session.getAttribute("employeeNo");
		String comCode = (String) session.getAttribute("companyCode");
		List<Messages> previousMessages = chatMessageRepository.findAll(); // 이걸 해당하는 방넘버를 가지고  대화내용 불러오는걸ㄹ ㅗ바꿔야돼
		List<EmployeeVO> list = gaService.empListSelect(comCode);
		List<ChatRoomVO> chatRoomList = chatRoomService.chatRoomsSelect(comCode, empNo);
		model.addAttribute("chatRoomList", chatRoomList);
		model.addAttribute("empList", list);
		model.addAttribute("messages", previousMessages);
		model.addAttribute("empNo", empNo);
		return "group/chat/chat";

	}
}
