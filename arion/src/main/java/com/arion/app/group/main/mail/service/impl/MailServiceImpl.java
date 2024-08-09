package com.arion.app.group.main.mail.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.main.mail.mapper.MailMapper;
import com.arion.app.group.main.mail.service.MailFileVO;
import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;

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
//    @Transactional
//    @Override
//    public int mailSend(MailVO mailVO, List<String> receiverIds, List<MultipartFile> files) {
//        int mailNo = mailMapper.getMailNoSequence();
//        mailVO.setMailNo(mailNo);
//
//        // 메일 저장
//        int result = mailMapper.sendMail(mailVO);
//
//        if (result == 1) {
//            // 수신자 정보 저장
//            List<MailReceiveVO> receivers = receiverIds.stream()
//                .map(receiverId -> {
//                    MailReceiveVO receiver = new MailReceiveVO();
//                    receiver.setMailNo(mailNo);
//                    receiver.setEmployeeId(receiverId);
//                    receiver.setReceiveNo(mailMapper.getMailNoSequence()); // 시퀀스 값
//                    return receiver;
//                })
//                .collect(Collectors.toList());
//            
//            for (MailReceiveVO receiver : receivers) {
//                mailMapper.insertMailReceive(receiver);
//            }
//
//            // 파일 정보 저장
//            List<MailFileVO> fileVOs = files.stream()
//                .map(file -> {
//                    MailFileVO fileVO = new MailFileVO();
//                    fileVO.setMailNo(mailNo);
//                    fileVO.setFileName(file.getOriginalFilename());
//                    fileVO.setFiletype(file.getContentType());
//                    fileVO.setFileNo(mailMapper.getMailNoSequence()); // 시퀀스 값
//                    fileVO.setCompanyCode(mailVO.getCompanyCode()); // 회사 코드 설정
//                    return fileVO;
//                })
//                .collect(Collectors.toList());
//
//            for (MailFileVO fileVO : fileVOs) {
//                mailMapper.insertMailFile(fileVO);
//            }
//
//            return mailNo;
//        }
//
//        return -1;
//    }
    @Override
    public Map<String, Object> deleteMail(MailVO mailVO) {
        Map<String, Object> map = new HashMap<>();
        boolean isSuccessed = mailMapper.mailDelete(mailVO.getMailNo()) == 1;

        map.put("result", isSuccessed);
        map.put("target", mailVO);

        return map;
    }

    @Override
    public int mailStatus(MailVO mailVO) {
        return mailMapper.statusMail(mailVO.getMailNo());
    }
}