package com.arion.app.security.service;

import lombok.Data;

@Data
public class HomeModuleVO {
	private int moduleNo;
	private String moduleName;
	private int modulePrice;
	private String moduleNotice;
	private String moduleIcon;
	
	private int modFileNo;
	private String modFileName;
	private String modFileContent;
	private String modFilePath;
	private int modFileTurn;
	private String modFileType;
	private int modNo;
}
