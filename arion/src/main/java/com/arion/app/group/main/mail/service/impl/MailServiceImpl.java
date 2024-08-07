package com.arion.app.group.main.mail.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arion.app.group.main.mail.mapper.MailMapper;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;
import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailFileVO;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailMapper mailMapper;

    // 받은 메일함 조회
    @Override
    public List<MailVO> mailList(MailVO mailVO) {
        return mailMapper.recieveMailAll(mailVO.getCompanyCode(), mailVO.getEmployeeId());
    }

    
    // 상세 메일 조회
    @Override
    public MailVO mailInfo(MailVO mailVO) {
        return mailMapper.selectMailInfo(mailVO);
    }

    // 보낸 메일함 조회
    @Override
    public List<MailVO> sendMailList(MailVO mailVO) {
        return mailMapper.sendMailAll(mailVO.getCompanyCode(), mailVO.getEmployeeId());
    }

    // 중요 메일함 조회
    @Override
    public List<MailVO> importMailList(MailVO mailVO) {
        return mailMapper.importMailAll(mailVO.getCompanyCode(), mailVO.getEmployeeId());
    }

    // 휴지통 조회
    @Override
    public List<MailVO> deleteMailList(MailVO mailVO) {
        return mailMapper.deleteMailAll(mailVO.getCompanyCode(), mailVO.getEmployeeId());
    }

    // 메일 보내기
    @Override
    public int mailSend(MailVO mailVO, List<MailReceiveVO> receivers, List<MailFileVO> files) {
        int result = mailMapper.sendMail(mailVO);
        int mailNo = mailVO.getMailNo();

        if (result == 1) {
            // 수신자 데이터베이스 삽입
            for (MailReceiveVO receiver : receivers) {
                receiver.setMailNo(mailNo);
                mailMapper.insertMailReceive(receiver);
            }

            // 첨부파일 데이터베이스 삽입
            for (MailFileVO file : files) {
                file.setMailNo(mailNo);
                mailMapper.insertMailFile(file);
            }

            return mailNo;
        }
        return -1;
    }

    // 메일 삭제
    @Override
    public Map<String, Object> deleteMail(MailVO mailVO) {
        Map<String, Object> map = new HashMap<>();

        int result = mailMapper.mailDelete(mailVO.getMailNo());

        if (result == 1) {
            map.put("status", "success");
            map.put("mailNo", mailVO.getMailNo());
        } else {
            map.put("status", "failure");
        }
        return map;
    }

    // 메일 상태 변경
    @Override
    public int mailStatus(MailVO mailVO) {
        return mailMapper.statusMail(mailVO.getMailNo());
    }
}
