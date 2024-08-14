package com.arion.app.group.main.approval.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.arion.app.group.main.approval.service.TemplateVO;

public interface TemplateMapper {
	public List<TemplateVO> tempList(@Param("companyCode") String companyCode);
	public int insertTemp(TemplateVO templateVO);
	public TemplateVO tempInfo(TemplateVO templateVO);
	public int tempDelete(@Param("companyCode") String companyCode, @Param("tempNo") String tempNo);
}
