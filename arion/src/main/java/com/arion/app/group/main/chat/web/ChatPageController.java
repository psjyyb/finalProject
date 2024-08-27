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

/*
 * 작성자 : 박성준
 * 작성일자 : 2024-08-17
 * 채팅화면 : 채팅방목록에 필요한 데이터 보내주기
 * */

@Controller
public class ChatPageController {

	@Autowired
	private ChatMessageRepository chatMessageRepository;
	private ChatRoomService chatRoomService;

	public ChatPageController(GroupAdminService gaService, ChatMessageRepository chatMessageRepository,
			ChatRoomService chatRoomService) {
		this.chatMessageRepository = chatMessageRepository;
		this.chatRoomService = chatRoomService;
	}

	@GetMapping("/group/chat")
	public String getChatPage(Model model, HttpSession session) {
		int empNo = (Integer) session.getAttribute("employeeNo");
		String comCode = (String) session.getAttribute("companyCode");
		String empName = (String) session.getAttribute("empName");
		List<EmployeeVO> list = chatRoomService.chatEmpList(comCode);
		List<ChatRoomVO> chatRoomList = chatRoomService.chatRoomsSelect(comCode, empNo);
		model.addAttribute("empName",empName);
		model.addAttribute("chatRoomList", chatRoomList);
		model.addAttribute("empList", list);
		//model.addAttribute("messages", previousMessages);
		model.addAttribute("empNo", empNo);
		return "group/chat/chat";
	}
//	@PostMapping("/group/chatRoom")
//  public List<Messages>
	
}
