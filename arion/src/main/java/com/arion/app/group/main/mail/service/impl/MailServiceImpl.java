package com.arion.app.group.main.mail.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.mail.mapper.MailMapper;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;


@Service
public class MailServiceImpl implements MailService{
	
	@Autowired 
	private MailMapper mailMapper;
	
	//받은메일함 조회
	@Override
	public List<MailVO> mailList() {
	    return mailMapper.recieveMailAll();
	}

	//메일 상세조회
	@Override
	public MailVO mailInfo(MailVO mailVO) {
		// TODO Auto-generated method stub
		return mailMapper.selectMailInfo(mailVO);
	}
	
	//보낸 메일함 조회
	@Override
	public List<MailVO> sendMailList(MailVO mailVO) {
		// TODO Auto-generated method stub
		return mailMapper.sendMailAll();
	}
	
	//중요 메일함 조회
	@Override
	public List<MailVO> importMailList(MailVO mailVO) {
		// TODO Auto-generated method stub
		return mailMapper.importMailAll();
	}
	
	//휴지통조회
	@Override
	public List<MailVO> deleteMailList(MailVO mailVO) {
		// TODO Auto-generated method stub
		return mailMapper.deleteMailAll();
	}

	//메일 보내기
	@Override
	public int mailSend(MailVO mailVO) {
		int result = mailMapper.sendMail(mailVO);
		return result ==1 ? mailVO.getMailNo() : -1;
	}
	
	//메일 삭제
	@Override
	public Map<String, Object> deleteMail(MailVO mailVO) {
		Map<String, Object> map = new HashMap<>();
		
		int result = mailMapper.mailDelete(mailVO.getMailNo());
		
		if(result ==1 ) {
			map.put("mailNo", mailVO.getMailNo());
		}
		return map;
	}
	
	//메일 상태변경 (중요메일, 받은메일, 휴지통)
	@Override
	public int mailStatus(MailVO mailVO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
