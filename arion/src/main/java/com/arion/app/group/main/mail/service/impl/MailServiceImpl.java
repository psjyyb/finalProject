package com.arion.app.group.main.mail.service.impl;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmailService;
import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;
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
	public int selectMailTotalCount(MailVO mailVO,Criteria criteria) {
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
    public int sendMail(MailVO mailVO, MultipartFile[] files) {
        try {
            // 메일 정보 저장
            mailMapper.sendMail(mailVO);

            MailReceiveVO receiveVO = new MailReceiveVO();
            receiveVO.setMailNo(mailVO.getMailNo());

            if (mailVO.getReceiverIds() != null) {
                for (String email : mailVO.getReceiverIds()) {
                    receiveVO.setEmployeeId(email);

                    if (email.indexOf("@") > 0) {
                        // 외부 메일
                        MimeMessage message = mailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true는 multipart 메시지 여부

                        helper.setSubject(mailVO.getMailTitle());
                        helper.setText(mailVO.getMailContent(), true); // true는 HTML 지원 여부
                        helper.setTo(email);

                        // 첨부파일 추가
                        if (files != null) {
                            for (MultipartFile file : files) {
                                if (!file.isEmpty()) {
                                    try {
                                        byte[] fileBytes = file.getBytes(); // 파일을 바이트 배열로 읽기
                                        ByteArrayResource byteArrayResource = new ByteArrayResource(fileBytes);
                                        helper.addAttachment(file.getOriginalFilename(), byteArrayResource);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        throw new Exception("파일 첨부 실패: " + e.getMessage());
                                    }
                                }
                            }
                        }

                        mailSender.send(message);
                    } else {
                        receiveVO.setCompanyCode(mailVO.getCompanyCode());
                    }

                    mailMapper.insertMailReceive(receiveVO);
                }
            }

            // 파일 저장 처리
            if (files != null) {
                String fileUploadResult = fileService.insertFiles(files, "MAIL", mailVO.getMailNo(), mailVO.getCompanyCode());
                if (fileUploadResult == null) {
                    throw new Exception("파일 저장 실패");
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public List<MailReceiveVO> selectReceivers(String companyCode) {
        return mailMapper.selectReceivers(companyCode);
    }
    
    
    @Override
    @Transactional
    public void updateMailStatus(List<Integer> mailIds, String status) {
        if (status.equals("IMPORT")) {
            mailMapper.updateMailStatusToImportant(mailIds);
        } else if (status.equals("TRASH")) {
            mailMapper.updateMailStatusToTrash(mailIds);
        }
    }
}