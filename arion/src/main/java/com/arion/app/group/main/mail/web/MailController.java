package com.arion.app.group.main.mail.web;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.PageDTO;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;

@Controller
@RequestMapping("/group/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private HttpSession httpSession;


    // 받은 메일 조회 (페이지)
    @GetMapping("/Mymail")
    public String mailList(Model model, Criteria criteria) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");
        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        mailVO.setMailStatus("RECEIVED");
        List<MailVO> receivedMails = mailService.mailList(mailVO, criteria);
        int totalCount = mailService.selectMailTotalCount(mailVO,criteria);

        PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
        model.addAttribute("receivedMails", receivedMails);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("criteria", criteria);

        return "group/mail/Mymail";
    }
    //중요메일
    @GetMapping("/importmail")
    public String importMailList(Model model, Criteria criteria) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");
        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        mailVO.setMailStatus("IMPORT");
        List<MailVO> importMailAll = mailService.importMailList(mailVO, criteria);
        int totalCount = mailService.selectMailTotalCount(mailVO,criteria);
        PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
       
        model.addAttribute("importMailAll", importMailAll);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("criteria", criteria);
        return "group/mail/importmail";
    }
    //휴지통
    @GetMapping("/trashmail")
    public String deleteMailList(Model model, Criteria criteria) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");
        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        mailVO.setMailStatus("TRASH");
        List<MailVO> deleteMailAll = mailService.deleteMailList(mailVO,criteria);
        int totalCount = mailService.selectMailTotalCount(mailVO,criteria);
        PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
        
        model.addAttribute("deleteMailAll", deleteMailAll);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("criteria", criteria);
        return "group/mail/trashmail";
    }
    //메일 상세보기
    @GetMapping("/mailInfo/{mailNo}")
    public String mailInfo(@PathVariable("mailNo") int mailNo, Model model) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");
        MailVO mailVO = new MailVO();
        mailVO.setMailNo(mailNo);
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        MailVO mailDetails = mailService.mailInfo(mailVO);
        model.addAttribute("mailInfo", mailDetails);

        return "group/mail/mailInfo";
    }
    
    //보낸메일
    @GetMapping("/sendmail")
    public String sendMailList(Model model, Criteria criteria) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");
        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        mailVO.setMailStatus("SEND");
        List<MailVO> sendMailAll = mailService.sendMailList(mailVO, criteria);
        int totalCount = mailService.selectMailTotalCount(mailVO,criteria);
        PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
       
        model.addAttribute("sendMailAll",sendMailAll);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("criteria", criteria);
        return "group/mail/sendmail";
    }
    
    // 메일 보내기 폼 페이지
    @GetMapping("/writemail")
    public String mailSendForm(Model model) {
    	String employeeId=(String) httpSession.getAttribute("loginId");
    	
    	//조직도    	
    
        model.addAttribute("employeeId", employeeId);
        
        return "group/mail/writemail";
    }
    
    
    //보내기
    @PostMapping("/writemail")
    @ResponseBody
    public int sendMail(MailVO mailVO, @RequestPart(required = false) MultipartFile[] files, HttpSession httpSession) {
        // 세션에서 회사 코드와 로그인 사용자 ID를 가져옵니다.
        String companyCode = (String) httpSession.getAttribute("companyCode");
        Integer senderId = (Integer) httpSession.getAttribute("employeeNo");
        String senderName = (String) httpSession.getAttribute("loginName"); // 예를 들어, 로그인 사용자 이름

        // MailVO에 발신자 정보와 회사 코드를 설정합니다.
        mailVO.setSenderId(senderId.toString());
        mailVO.setSenderName(senderName);
        mailVO.setCompanyCode(companyCode);

        mailVO.setReceiverIds(mailVO.getReceiverId().split(","));
        // 메일을 전송합니다.
        int result = mailService.sendMail(mailVO, files);
        return result;
        
    }
    // 메일 삭제 (처리)
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteMail(MailVO mailVO) {
    	String employeeId=(String) httpSession.getAttribute("loginId");
    	mailVO.setCompanyCode((String) httpSession.getAttribute("companyCode"));
        mailVO.setSenderId(employeeId);

        return mailService.deleteMail(mailVO);
    }
}
