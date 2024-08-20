package com.arion.app.group.main.database.mapper;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface DatabaseMapper {

	public int ranking(String companycode, String rankname);
}
