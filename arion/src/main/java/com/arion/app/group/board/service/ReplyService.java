package com.arion.app.group.board.service;

import java.util.List;
import com.arion.app.group.board.service.ReplyVO;

public interface ReplyService {
	void insert(ReplyVO reply);
	void delete(int commentNo);

}
