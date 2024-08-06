package com.arion.app.group.main.mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.arion.app.group.main.mail.service.MailVO;

@Mapper
public interface MailMapper {
	
	//받은메일조회
	public List<MailVO> recieveMailAll();
	
	//중요메일조회
	public List<MailVO> importMailAll();
	
	//휴지통조회
	public List<MailVO> deleteMailAll();
	
	//보낸메일조회
	public List<MailVO> sendMailAll();
	
	//상세메일조회
	public MailVO selectMailInfo(MailVO mailVO);
	
	//메일보내기
	public int sendMail(MailVO mailVO);
	
	//메일삭제
	public int mailDelete(int mailNo);
	
	//메일 상태 변경 (중요메일, 휴지통, 보낸메일)
	public int statusMail(int mailNo);



}
