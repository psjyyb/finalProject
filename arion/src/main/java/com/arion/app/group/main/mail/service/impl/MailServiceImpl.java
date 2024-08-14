package com.arion.app.group.main.mail.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmailService;
import com.arion.app.common.service.FileService;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.main.mail.mapper.MailMapper;
import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailMapper mailMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private JavaMailSender mailSender; 
    
    //받은메일
    @Override
    public List<MailVO> mailList(MailVO mailVO, Criteria criteria) {
        List<MailVO> mails = mailMapper.receiveMailAll(mailVO, criteria);
        return mails;
    }
    //보낸메일
    @Override
    public List<MailVO> sendMailList(MailVO mailVO, Criteria criteria) {
    	  List<MailVO> mails = mailMapper.sendMailAll(mailVO, criteria);
    	  return mails;
    }
    //중요메일
    @Override
    public List<MailVO> importMailList(MailVO mailVO, Criteria criteria) {
        List<MailVO> mails = mailMapper.importMailAll(mailVO, criteria);
        return mails;
    }
    
    //메일 페이징
	@Override
	public int selectMailTotalCount(MailVO mailVO,Criteria criteria) {
//		Map<String, Object> params = new HashMap<>();
//		params.put("searchType", criteria.getSearchType());
//		params.put("keyword", criteria.getKeyword());

		return mailMapper.selectMailTotalCount(mailVO,criteria);
	}

    //휴지통
    @Override
    public List<MailVO> deleteMailList(MailVO mailVO, Criteria criteria) {
		 List<MailVO> mails = mailMapper.deleteMailAll(mailVO, criteria);
	        return mails;
    }
    
    //메일 상세조회
    @Override
    public MailVO mailInfo(MailVO mailVO) {
        return mailMapper.selectMailInfo(mailVO);
    }
    
    //메일검색

    //삭제
    @Override
    public Map<String, Object> deleteMail(MailVO mailVO) {
        Map<String, Object> map = new HashMap<>();
        boolean isSuccess = mailMapper.mailDelete(mailVO.getMailNo()) == 1;
        map.put("result", isSuccess);
        map.put("target", mailVO);
        return map;
    }
    
    //메일보내기
    @Transactional
    @Override
    public int sendMail(MailVO mailVO, MultipartFile[] attachments) {
        try {
            // 내부 수신자 정보
            List<MailReceiveVO> internalReceivers = mailMapper.selectReceivers(mailVO.getCompanyCode());

            // 메일 제목 내용변경
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(mailVO.getMailTitle());
            message.setText(mailVO.getMailContent());

            // 외부 수신자에게 메일
            if (mailVO.getReceiverEmails() != null) {
                for (String email : mailVO.getReceiverEmails()) {
                    message.setTo(email);
                    mailSender.send(message);
                  
                }
            }

            // 내부 수신자에게 메일을 보냅니다.
            if (internalReceivers != null && !internalReceivers.isEmpty()) {
                for (MailReceiveVO receiver : internalReceivers) {
                    message.setTo(receiver.getReceiveEmail());
                    mailSender.send(message);
                  
                }
            }
            return 1; 
        } catch (Exception e) {
            return 0; 
        }
    }
    
    @Override
    public List<MailReceiveVO> selectReceivers(String companyCode) {
        return mailMapper.selectReceivers(companyCode);
    }
}