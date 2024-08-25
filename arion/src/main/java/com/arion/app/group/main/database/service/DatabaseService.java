package com.arion.app.group.main.database.service;

import java.util.List;

public interface DatabaseService {
	public int ranking(String companycode,String rankname);

	public List<CFileVO> startforder(String companycode);
	
	public List<UnderRankVO> underank(int ranking);
	
	public List<CFileVO> filelist(String companycode,int parent);

	public int forderupload(String companycode,int parent,String uploader,String rankname,String filename);

	public List<CFileVO> forder(String companycode);

	public int fileupload(String companycode, int parent, String uploader, String originalFilename, long filesize,
			String filename, String rankname);

	public CFileVO file(String companycode);

	public List<FileinfoVO> fileinfo(String companycode, int me);

	public int filedelete(String companycode, int deleteid);
	
	public int forderdelete(String companycode, int deleteid);
}
