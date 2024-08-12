package com.arion.app.group.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.ReplyVO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	public ReplyVO read(int commentNO);
	
	public int delete(int commentNo); 
	
	public int update(ReplyVO vo);
	
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") int boardNo);

}
