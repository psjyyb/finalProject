package com.arion.app.group.main.chat.service;

import java.util.List;
import java.util.Map;

public interface ChatRoomService {

	ChatRoomVO createChatRoom(ChatRoomVO chatRoomVO);

	List<ChatRoomVO> chatRoomsSelect(String companyCode, int employeeNo);
	
	Map<String,Object> chatRoomExit(ChatVO chatVO);
}