package com.arion.app.admin.service;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ModuleVO {
		private int moduleNo;
		private String moduleName;
		private int modulePrice;
		private String moduleIcon;
		private String moduleNotice;
		private String[] moduleNotices;
		private MultipartFile[] uploadFiles;
		private MultipartFile moduleIcons;
}
