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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public String mailList(Model model) {
    	String employeeId=(String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");

        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        List<MailVO> receivedMails = mailService.mailList(mailVO);
        model.addAttribute("receivedMails", receivedMails);

        return "group/mail/Mymail";	
    }
    //중요메일
    @GetMapping("/importmail")
    public String importMailList(Model model) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");

        if (employeeId == null || companyCode == null) {
            model.addAttribute("error", "Session information is missing.");
            return "group/mail/importmail";
        }

        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        List<MailVO> importMailAll = mailService.importMailList(mailVO);
        model.addAttribute("importMailAll", importMailAll);

        return "group/mail/importmail";
    }
    
    // 메일 보내기 폼 페이지
    @GetMapping("/writemail")
    public String mailSendForm(Model model) {
    	String employeeId=(String) httpSession.getAttribute("loginId");
        model.addAttribute("employeeId", employeeId);
        
        return "group/mail/writemail";
    }

    @PostMapping("/writemail")
    public String sendMail(@RequestParam("receiverId") String receiverId,
                           @RequestParam("subject") String subject,
                           @RequestParam("content") String content,
                           @RequestParam("attachments") MultipartFile[] attachments,
                           Model model) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        try {
            if (employeeId == null) {
                model.addAttribute("error", "Session information is missing.");
                return "group/mail/writemail";
            }

            String companyCode = (String) httpSession.getAttribute("companyCode");

            MailVO mailVO = new MailVO();
            mailVO.setSenderId(employeeId);
            mailVO.setReceiverEmail(receiverId);
            mailVO.setMailTitle(subject);
            mailVO.setMailContent(content);
            mailVO.setCompanyCode(companyCode);

            mailService.sendMail(mailVO, attachments);

            return "redirect:/group/mail/Mymail";
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "group/mail/writemail";
        }
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
