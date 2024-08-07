package com.arion.app.common.service;

import lombok.Data;

@Data
public class FileVO {
	private int fileNo;
	private String fileName;
	private String fileOriginalName;
	private String filePath;
	private String fileType;
	private int fileTurn;
	private String companyCode;
	private String tableName;
	private int keyNo;
}
