package com.arion.app.group.main.database.service;

import java.util.List;

public interface DatabaseService {
	public int ranking(String companycode,String rankname);

	public List<CFileVO> startforder(String companycode);
	
	public List<UnderRankVO> underank(int ranking);
	
	public List<CFileVO> filelist(String companycode,int parent);

	public int forderupload(String companycode,int parent,String uploader,String rankname,String filename);

	public List<CFileVO> forder(String companycode);

	public int fileupload(String companycode, int parent, String uploader, String originalFilename, long size,
			String string, String rankname);

	public List<CFileVO> file(String companycode);

}
