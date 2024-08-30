package com.arion.app.group.main.mail.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.board.service.Criteria;

public interface MailService {

    // 받은 메일 조회

	List<MailVO> mailList(MailVO mailVO, Criteria criteria);

    // 보낸 메일 조회
    List<MailVO> sendMailList(MailVO mailVO, Criteria criteria);

    // 중요 메일 조회
    List<MailVO> importMailList(MailVO mailVO, Criteria criteria);

    // 휴지통 조회
    List<MailVO> deleteMailList(MailVO mailVO, Criteria criteria);
    
    //메일 페이징
    int selectMailTotalCount(MailVO mailVO,Criteria criteria);
    
    int importMailTotalCount(MailVO mailVO,Criteria criteria);
    int deleteMailTotalCount(MailVO mailVO,Criteria criteria);
    //메일 상태변경
    
    // 메일 삭제
    Map<String, Object> deleteMail(MailVO mailVO);

    Map<String, Object> removeMail(int mailNo);

    // 메일 보내기
    int sendMail(MailVO mailVO, MultipartFile[] files);
  
    
    // 답장하기
    int replyToMail(MailVO mailVO, MultipartFile[] files);
    // 수신자 정보 조회
   
    //메일 상세조회
    MailVO mailInfo(MailVO mailVO);
    //메일 상태변경
    void updateMailStatus(List<Integer> mailIds, String employeeId,String status);
    void deleteMailStatus(List<Integer> mailIds, String employeeId,String status);
}
