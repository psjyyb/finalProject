package com.arion.app.group.main.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.database.service.CFileVO;
import com.arion.app.group.main.database.service.UnderRankVO;

@Mapper
public interface DatabaseMapper {

	public int ranking(String companycode, String rankname);
	
	public List<CFileVO> start(String companycode);
	
	public List<UnderRankVO> underrank(int ranking);
}
