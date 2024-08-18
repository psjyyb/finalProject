package com.arion.app.group.main.approval.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

public interface TemplateService {
	public List<TemplateVO> tempList(@Param("companyCode") String companyCode);
	public Map<String, Object> insertTemp(TemplateVO templateVO);
	public TemplateVO tempInfo(TemplateVO templateVO);
	public int tempDelete(@Param("companyCode") String companyCode, @Param("tempNo") String tempNo);

	public List<TemplateVO> tpyeTempList(@Param("companyCode") String companyCode, @Param("docType") String docType);

}
