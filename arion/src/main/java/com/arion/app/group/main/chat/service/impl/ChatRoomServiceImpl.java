package com.arion.app.group.main.chat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.arion.app.group.main.chat.mapper.ChatRoomMapper;
import com.arion.app.group.main.chat.service.ChatMemberVO;
import com.arion.app.group.main.chat.service.ChatRoomService;
import com.arion.app.group.main.chat.service.ChatRoomVO;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
	
	private ChatRoomMapper chatRoomMapper;
	
	public ChatRoomServiceImpl(ChatRoomMapper chatRoomMapper) {
		this.chatRoomMapper = chatRoomMapper;
	}

	@Transactional
	@Override
	public ChatRoomVO createChatRoom(@RequestBody ChatRoomVO chatRoomVO) {
	    System.out.println(chatRoomVO + " - Service Implementation");

	    // 채팅방 생성
	    chatRoomMapper.createChatRoom(chatRoomVO);

	    // 채팅방 멤버 추가
	    ChatMemberVO chatMemberVO = new ChatMemberVO();
	    chatMemberVO.setRoomNo(chatRoomVO.getRoomNo());

	    // 배열이 null이 아닌지 확인
	    if (chatRoomVO.getEmployeeIds() == null || chatRoomVO.getEmployeeIds().length == 0) {
	        throw new IllegalArgumentException("Employee IDs array is null or empty");
	    }

	    for (int i = 0; i < chatRoomVO.getEmployeeIds().length; i++) {
	        chatMemberVO.setEmployeeNo(chatRoomVO.getEmployeeIds()[i]);
	        System.out.println(chatRoomVO.getEmployeeIds()[i] + " - Employee ID");
	        System.out.println(chatMemberVO + " - Chat Member VO");
	        chatRoomMapper.membersChatRoom(chatMemberVO);
	    }

	    return chatRoomVO;
	}

    @Override
    public List<ChatRoomVO> getAllChatRooms() {
        return new ArrayList<>();
    }
    @Override
    public List<ChatRoomVO> chatRoomsSelect(String companyCode, int employeeNo) {
    	return chatRoomMapper.selectChatRooms(companyCode, employeeNo);
    }
}