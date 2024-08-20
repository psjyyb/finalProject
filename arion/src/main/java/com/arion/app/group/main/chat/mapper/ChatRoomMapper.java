package com.arion.app.group.main.chat.mapper;

import java.util.List;

import com.arion.app.group.main.chat.service.ChatMemberVO;
import com.arion.app.group.main.chat.service.ChatRoomVO;
import com.arion.app.group.main.chat.service.ChatVO;

public interface ChatRoomMapper {
	// 채팅방 테이블 인서트
	public int createChatRoom(ChatRoomVO chatRoomVO);
	// 채팅방에 속한 멤버테이블 인서트
	public int membersChatRoom(ChatMemberVO chatMemberVO);
	// 유저가 속한 채팅방 목록 
	List<ChatRoomVO> selectChatRooms(String companyCode, int employeeNo);
	// 채팅방 나가기
	int exitChatRoom(ChatVO chatVO);
}
