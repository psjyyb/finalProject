package com.arion.app.group.main.database.service;

import java.util.List;

public interface DatabaseService {
	public int ranking(String companycode,String rankname);

	public List<CFileVO> startforder(String companycode);
	
	public List<UnderRankVO> underank(int ranking);
}
