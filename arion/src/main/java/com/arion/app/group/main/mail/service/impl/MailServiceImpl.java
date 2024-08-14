package com.arion.app.group.main.mail.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    //받은메일
    @Override
    public List<MailVO> mailList(MailVO mailVO,Criteria criteria) {
        return mailMapper.receiveMailAll(mailVO,criteria);
    }
    //보낸메일
    @Override
    public List<MailVO> sendMailList(MailVO mailVO) {
        return mailMapper.sendMailAll(mailVO.getCompanyCode(), mailVO.getSenderId());
    }
    //중요메일
    @Override
    public List<MailVO> importMailList(MailVO mailVO) {
        List<MailVO> mails = mailMapper.importMailAll(mailVO.getCompanyCode(), mailVO.getSenderId());
        System.out.println("중요 메일 조회 결과: " + mails);
        return mails;
    }
    
    //메일 페이징
	@Override
	public int selectMailTotalCount(Criteria criteria) {
//		Map<String, Object> params = new HashMap<>();
//		params.put("searchType", criteria.getSearchType());
//		params.put("keyword", criteria.getKeyword());

		return mailMapper.selectMailTotalCount(criteria);
	}
    
    //휴지통
    @Override
    public List<MailVO> deleteMailList(MailVO mailVO) {
        return mailMapper.deleteMailAll(mailVO.getCompanyCode(), mailVO.getSenderId());
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
    public void sendMail(MailVO mailVO, MultipartFile[] attachments) {
        try {
            int mailNo = mailMapper.getMailNoSequence();
            mailVO.setMailNo(mailNo);
            mailVO.setSendDate(new Date());
            mailVO.setMailStatus("SEND");

            mailMapper.sendMail(mailVO);
            System.out.println("메일이 데이터베이스에 저장되었습니다: " + mailVO);

            MailReceiveVO receiveVO = new MailReceiveVO();
            receiveVO.setMailNo(mailNo);
            receiveVO.setReceiveEmail(mailVO.getReceiverEmail());
            receiveVO.setCompanyCode(mailVO.getCompanyCode());
            mailMapper.insertMailReceive(receiveVO);

            if (attachments != null && attachments.length > 0) {
                fileService.insertFiles(attachments, "MAIL", mailNo, mailVO.getCompanyCode());
            }

            emailService.sendEmail(mailVO.getReceiverEmail(), mailVO.getMailTitle(), mailVO.getMailContent());
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그를 출력합니다.
            throw new RuntimeException("메일 전송 중 오류 발생", e);
        }
    }
}
