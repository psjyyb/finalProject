package com.arion.app.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ModuleVO {
		private int moduleNo;
		private String moduleName;
		private int modulePrice;
		private String[] moduleNotice;
		private MultipartFile[] uploadFiles;
		//private List<ModuleFileVO> list;
}
