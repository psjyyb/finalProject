package com.arion.app.group.main.mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.mail.service.MailVO;

@Mapper
public interface MailMapper {
	public List<MailVO> selectMailAll();

}
