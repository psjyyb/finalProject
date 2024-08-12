package com.arion.app.group.main.mail.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface MailService {

    // 받은 메일 조회
    List<MailVO> mailList(MailVO mailVO);

    // 보낸 메일 조회
    List<MailVO> sendMailList(MailVO mailVO);

    // 중요 메일 조회
    List<MailVO> importMailList(MailVO mailVO);

    // 휴지통 조회
    List<MailVO> deleteMailList(MailVO mailVO);

    // 메일 삭제
    Map<String, Object> deleteMail(MailVO mailVO);

    // 메일 보내기
    void sendMail(MailVO mailVO, MultipartFile[] attachments);
}
