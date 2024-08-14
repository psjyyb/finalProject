package com.arion.app.group.main.mail.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.board.service.Criteria;

public interface MailService {

    // 받은 메일 조회
    List<MailVO> mailList(MailVO mailVO,Criteria criteria);

    // 보낸 메일 조회
    List<MailVO> sendMailList(MailVO mailVO);

    // 중요 메일 조회
    List<MailVO> importMailList(MailVO mailVO);

    // 휴지통 조회
    List<MailVO> deleteMailList(MailVO mailVO);
    
    //메일 페이징
    int selectMailTotalCount(Criteria criteria);
    // 메일 삭제
    Map<String, Object> deleteMail(MailVO mailVO);

    // 메일 보내기
    public void sendMail(MailVO mailVO, MultipartFile[] attachments);
    
    //메일 상세조회
    MailVO mailInfo(MailVO mailVO);
 
    

}
