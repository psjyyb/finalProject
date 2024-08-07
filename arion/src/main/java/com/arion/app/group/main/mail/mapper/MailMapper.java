package com.arion.app.group.main.mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.arion.app.group.main.mail.service.MailFileVO;
import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailVO;

@Mapper
public interface MailMapper {

	 public   List<MailVO> recieveMailAll(@Param("companyCode") String companyCode, @Param("employeeId") String employeeId);
    
    // 중요 메일 조회
    public List<MailVO> importMailAll(String companyCode, String employeeId);
    
    // 휴지통 조회
    public List<MailVO> deleteMailAll(String companyCode, String employeeId);
    
    // 보낸 메일 조회
    public List<MailVO> sendMailAll(String companyCode, String employeeId);
    
    // 상세 메일 조회
    public MailVO selectMailInfo(MailVO mailVO);
    
    // 메일 보내기
    public int sendMail(MailVO mailVO);
    
    // 메일 삭제
    public int mailDelete(int mailNo);
    
    // 메일 상태 변경 (중요 메일, 휴지통, 보낸 메일)
    public int statusMail(int mailNo);
    
    // 수신자 삽입
    public int insertMailReceive(MailReceiveVO mailReceiveVO);
    
    // 첨부파일 삽입
    public int insertMailFile(MailFileVO mailFileVO);
}
