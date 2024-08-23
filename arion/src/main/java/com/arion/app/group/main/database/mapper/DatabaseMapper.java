package com.arion.app.group.main.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.database.service.CFileVO;
import com.arion.app.group.main.database.service.UnderRankVO;

@Mapper
public interface DatabaseMapper {

	public int ranking(String companycode, String rankname);
	
	public List<CFileVO> start(String companycode);
	
	public List<UnderRankVO> underrank(int ranking);
	
	public List<CFileVO> filelist(String companycode, int parent);
	
	public int forderupload(String companycode, int parent, String uploader, String rankname, String filename);

	public List<CFileVO> forder(String companycode);

	public int fileupload(String companycode, int parent, String uploader, String rankname, String filename, int filesize);

	public List<CFileVO> file(String companycode);
}
