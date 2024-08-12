package com.arion.app.group.main.mail.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailVO;

@Mapper
public interface MailMapper {

    // 받은 메일 조회
    List<MailVO> receiveMailAll(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);

    // 보낸 메일 조회
    List<MailVO> sendMailAll(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);

    // 중요 메일 조회
    List<MailVO> importMailAll(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);

    // 휴지통 조회
    List<MailVO> deleteMailAll(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);

    // 메일 보내기
    int sendMail(MailVO mailVO);

    // 메일 삭제
    int mailDelete(int mailNo);
    
    // 메일 수신 기록 삽입 메서드
    int insertMailReceive(MailReceiveVO receiveVO);
    //시퀀스
    int getMailNoSequence();
}
