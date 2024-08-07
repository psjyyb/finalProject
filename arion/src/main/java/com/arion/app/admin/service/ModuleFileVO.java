package com.arion.app.admin.service;

import lombok.Data;

@Data
public class ModuleFileVO {
	private int modFileNo;
	private String modFileName;
	private String modFileContent;
	private String modFilePath;
	private int modFileTurn;
	private String modFileType;
	private int qnaNo;
}
