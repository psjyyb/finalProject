package com.arion.app.group.main.mail.service;

import java.util.List;
import java.util.Map;

public interface MailService {

    // 받은 메일 조회
    public List<MailVO> mailList(MailVO mailVO);

    // 상세 메일 조회
    MailVO mailInfo(MailVO mailVO);

    // 보낸 메일 조회
    List<MailVO> sendMailList(MailVO mailVO);

    // 중요 메일 조회
    List<MailVO> importMailList(MailVO mailVO);

    // 휴지통 조회
    List<MailVO> deleteMailList(MailVO mailVO);

    // 메일 보내기
//    int mailSend(MailVO mailVO, List<MailReceiveVO> receivers, List<MailFileVO> files);

    // 메일 삭제
    Map<String, Object> deleteMail(MailVO mailVO);

    // 메일 상태 변경
    int mailStatus(MailVO mailVO);
}