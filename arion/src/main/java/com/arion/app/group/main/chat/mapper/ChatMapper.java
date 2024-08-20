package com.arion.app.group.main.chat.mapper;

import java.util.List;

import com.arion.app.group.main.chat.service.ChatVO;

public interface ChatMapper {
	
	List<ChatVO>  selectRoomNo(int roomNo);
}
