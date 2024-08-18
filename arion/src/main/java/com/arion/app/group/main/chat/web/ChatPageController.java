package com.arion.app.group.main.chat.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arion.app.group.main.chat.domain.Messages;
import com.arion.app.group.main.chat.repository.ChatMessageRepository;

@Controller
public class ChatPageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/group/chat")
    public String getChatPage(Model model,HttpSession session) {
    	int empNo = (Integer) session.getAttribute("employeeNo");
        List<Messages> previousMessages = chatMessageRepository.findAll();
        model.addAttribute("messages", previousMessages);
        model.addAttribute("empNo",empNo);
        return "group/chat/chat";
    }
}
