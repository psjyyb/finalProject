package com.arion.app.group.main.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.arion.app.group.main.database.mapper.DatabaseMapper;
import com.arion.app.group.main.database.service.DatabaseService;

@Service
public class DatabaseServicelmpl implements DatabaseService{

	@Autowired 
	private DatabaseMapper databasemapper;
	
	@Override
	public int ranking(String companycode, String rankname) {
		
		return databasemapper.ranking(companycode, rankname);
	}

}
