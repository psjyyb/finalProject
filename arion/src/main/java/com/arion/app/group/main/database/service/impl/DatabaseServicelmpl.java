package com.arion.app.group.main.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.arion.app.group.main.database.mapper.DatabaseMapper;
import com.arion.app.group.main.database.service.CFileVO;
import com.arion.app.group.main.database.service.DatabaseService;
import com.arion.app.group.main.database.service.UnderRankVO;

@Service
public class DatabaseServicelmpl implements DatabaseService{

	@Autowired 
	private DatabaseMapper databasemapper;
	
	@Override
	public int ranking(String companycode, String rankname) {
		
		return databasemapper.ranking(companycode, rankname);
	}

	@Override
	public List<CFileVO> startforder(String companycode) {
		
		return databasemapper.start(companycode);
	}

	@Override
	public List<UnderRankVO> underank(int ranking) {
		// TODO Auto-generated method stub
		return databasemapper.underrank(ranking);
	}

	@Override
	public List<CFileVO> filelist(String companycode, int parent) {
		// TODO Auto-generated method stub
		return databasemapper.filelist(companycode,parent);
	}

}
