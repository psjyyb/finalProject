package com.arion.app.group.main.mail.service;

import java.util.List;
import java.util.Map;

public interface MailService {
    
	//받은 메일 조회
	public List<MailVO> mailList();
	
	//상세 메일 조회
	public MailVO mailInfo(MailVO mailVO);
	
	//보낸메일함
	public List<MailVO> sendMailList(MailVO mailVO);
	
	//중요메일함
	public List<MailVO> importMailList(MailVO mailVO);
	
	//휴지통
	public List<MailVO> deleteMailList(MailVO mailVO);
	
	//메일 보내기
	public int mailSend(MailVO mailVO);
	
	//메일 삭제
	public Map<String,Object> deleteMail(MailVO mailVO);
	
	//메일 상태변경
	public int mailStatus(MailVO mailVO);
	
}