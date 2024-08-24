package com.arion.app.group.main.mail.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailVO;

@Mapper
public interface MailMapper {

    // 받은 메일 조회
    List<MailVO> receiveMailAll(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);

    // 보낸 메일 조회
    List<MailVO> sendMailAll(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);

    // 중요 메일 조회
    List<MailVO> importMailAll(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);

    // 휴지통 조회
    List<MailVO> deleteMailAll(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);

    //메일 페이징
    int selectMailTotalCount(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);
   
    int sendMailTotalCount(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);
    
    int importMailTotalCount(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);
    
    int deleteMailTotalCount(@Param("mailVO")MailVO mailVO,@Param("criteria")Criteria criteria);
    // 메일 보내기
    int sendMail(MailVO mailVO);

    // 수신자 정보 조회
    List<MailReceiveVO> selectReceivers(@Param("companyCode") String companyCode);
    
    // 메일 삭제
    int mailDelete(int mailNo);
    
    //메일 상세조회
    MailVO selectMailInfo(MailVO mailVO);
    //메일 파일조회
    MailVO selectfiles(MailVO mailVO);
    //답장하기
    int replyToMail(MailVO mailVO);
    // 메일 수신 기록 삽입 메서드
    int insertMailReceive(MailReceiveVO receiveVO);
    //시퀀스
    int getMailNoSequence();
    
    // 현재 메일 상태 조회
    String selectMailStatus(@Param("mailNo") Integer mailNo, @Param("employeeId") String employeeId);
    void updateMailStatus(List<Integer> mailIds, String employeeId, String status);
  
}
