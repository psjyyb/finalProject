package com.arion.app.group.main.chat.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arion.app.group.main.chat.service.ChatRoomService;
import com.arion.app.group.main.chat.service.ChatRoomVO;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;
   
    
    @PostMapping
    public ChatRoomVO createChatRoom(@RequestBody ChatRoomVO chatRoomVO,HttpSession session) {
    	System.out.println(chatRoomVO+"컨트롤러에서 찍기");
    	String comCode = (String)session.getAttribute("companyCode");
    	chatRoomVO.setCompanyCode(comCode);
        return chatRoomService.createChatRoom(chatRoomVO);
    }

    @GetMapping
    public List<ChatRoomVO> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }
}