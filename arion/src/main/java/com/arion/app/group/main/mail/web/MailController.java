package com.arion.app.group.main.mail.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;
import com.arion.app.group.main.mail.service.MailReceiveVO;
import com.arion.app.group.main.mail.service.MailFileVO;

@Controller
@RequestMapping("/group")  // 수정: /mail에서 /group으로 변경
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private HttpSession httpSession;

    // 받은 메일 조회
    @GetMapping("/Mymail")
    public String mailList(Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("loginId");

        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setEmployeeId(employeeId); // 여기서 문자열로 설정

        List<MailVO> receivedMails = mailService.mailList(mailVO);
        model.addAttribute("receivedMails", receivedMails);

        return "group/mail/Mymail";
    }

    // 보낸 메일 조회
    @GetMapping("/group/sendmail")
    public String sendMailList(Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("employeeId");

        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setEmployeeId(employeeId);

        List<MailVO> sentMails = mailService.sendMailList(mailVO);
        model.addAttribute("sentMails", sentMails);
        return "group/mail/sendmail";
    }

    // 중요 메일 조회
    @GetMapping("/group/importmail")
    public String importMailList(Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("employeeId");

        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setEmployeeId(employeeId);

        List<MailVO> importantMails = mailService.importMailList(mailVO);
        model.addAttribute("importantMails", importantMails);
        return "group/mail/importmail";
    }

    // 휴지통 메일 조회
    @GetMapping("/group/trashmail")
    public String deleteMailList(Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("employeeId");

        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setEmployeeId(employeeId);

        List<MailVO> trashMails = mailService.deleteMailList(mailVO);
        model.addAttribute("trashMails", trashMails);
        return "group/mail/trashmail";
    }

    // 메일 보내기
    @PostMapping("/group/writemail")
    public String mailSend(MailVO mailVO, @RequestParam("receivers") List<MailReceiveVO> receivers,
                           @RequestParam("files") List<MailFileVO> files) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("employeeId");

        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);

        int mailNo = mailService.mailSend(mailVO, receivers, files);
        if (mailNo > 0) {
            return "redirect:/mail/group/Mymail";  // 수정된 부분
        } else {
            // 실패 처리 로직
            return "redirect:/mail/group/Mymail";  // 수정된 부분
        }
    }

    // 메일 상세 조회
    @GetMapping("/detailmail")
    public String mailInfo(@RequestParam("mailNo") int mailNo, Model model) {
        MailVO mailVO = new MailVO();
        mailVO.setMailNo(mailNo);

        MailVO mailInfo = mailService.mailInfo(mailVO);
        model.addAttribute("mail", mailInfo);

        return "group/mail/detailmail";  // 상세 보기 HTML 페이지 이름
    }

    // 메일 삭제
    @PostMapping("/delete")
    public String deleteMail(@RequestParam("mailNo") int mailNo) {
        MailVO mailVO = new MailVO();
        mailVO.setMailNo(mailNo);
        mailVO.setCompanyCode((String) httpSession.getAttribute("companyCode"));
        mailVO.setEmployeeId((String) httpSession.getAttribute("employeeId"));

        Map<String, Object> result = mailService.deleteMail(mailVO);
        if ("success".equals(result.get("status"))) {
            return "redirect:/mail/group/Mymail";  // 수정된 부분
        } else {
            // 실패 처리 로직
            return "redirect:/mail/group/Mymail";  // 수정된 부분
        }
    }
}
