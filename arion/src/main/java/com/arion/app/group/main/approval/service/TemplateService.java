package com.arion.app.group.main.approval.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

public interface TemplateService {
	public List<TemplateVO> tempList(@Param("companyCode") String companyCode);
	public Map<String, Object> insertTemp(TemplateVO templateVO);
	public List<TemplateVO> tempInfo(@Param("companyCode") String companyCode, @Param("tempNo") int tempNo);
	
}
