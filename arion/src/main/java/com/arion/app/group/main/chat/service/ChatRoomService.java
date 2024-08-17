package com.arion.app.group.main.chat.service;

import java.util.List;

public interface ChatRoomService {
	
	ChatRoomVO createChatRoom(ChatRoomVO chatRoomVO);

	List<ChatRoomVO> getAllChatRooms();
	
	List<ChatRoomVO> chatRoomsSelect(String companyCode, int employeeNo);
}
