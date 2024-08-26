package com.arion.app.group.main.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.database.service.CFileVO;
import com.arion.app.group.main.database.service.FileinfoVO;
import com.arion.app.group.main.database.service.UnderRankVO;

@Mapper
public interface DatabaseMapper {

	public int ranking(String companycode, String rankname);
	
	public List<CFileVO> start(String companycode);
	
	public Integer startfordercheck(String companycode);
	
	public int startfordercreate(String companycode,String uploader);
	
	public List<UnderRankVO> underrank(int ranking);
	
	public List<CFileVO> filelist(String companycode, int parent);
	
	public int forderupload(String companycode, int parent, String uploader, String rankname, String filename);

	public List<CFileVO> forder(String companycode);

	public int fileupload(String companycode, int parent, String uploader, String originalFilename, long filesize, String filename, String rankname);

	public CFileVO file(String companycode);
	
	public List<FileinfoVO> datainfo(String companycode,int me);
	
	public int filedelete(String companycode,int deleteid);
	
	public int forderdelete(String companycode,int deleteid);
}
