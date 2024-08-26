package com.arion.app.group.main.mail.web;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.PageDTO;
import com.arion.app.group.main.group.service.GroupService;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;

@Controller
@RequestMapping("/group/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private HttpSession httpSession;

	@Autowired
	FileService fsvc;
	
	@Autowired
	private GroupService groupService;
	
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
        System.out.println("이건 인포>>>>>>>>>>>>>" + mailNo);
        MailVO mailVO = new MailVO();
        mailVO.setMailNo(mailNo);
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        MailVO mailDetails = mailService.mailInfo(mailVO);
        List<FileVO> fileVOList = fsvc.selectFiles("MAIL", mailVO.getMailNo(), companyCode);
        model.addAttribute("mailInfo", mailDetails);
        model.addAttribute("fileInfo", fileVOList);
        
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
        int totalCount = mailService.selectMailTotalCount(mailVO, criteria);
        PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
      
        model.addAttribute("sendMailAll", sendMailAll);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("criteria", criteria);
        return "group/mail/sendmail";
    }
    
    // 메일 보내기 폼 페이지
    @GetMapping("/writemail")
    public String mailSendForm(@RequestParam(required = false) String employeeId, Model model) {
        String senderId = (String) httpSession.getAttribute("loginId");
        model.addAttribute("senderId", senderId);

        if (employeeId != null) {
            model.addAttribute("receiverId", employeeId);
        }

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

        // 수신자 ID 배열로 변환
        mailVO.setReceiverIds(mailVO.getReceiverId().split(","));
        
        // 메일을 전송합니다.
        int result = mailService.sendMail(mailVO, files);
        return result;
    }
    //메일 답장 페이지
    @GetMapping("/reply/{mailNo}")
    public String replyMailForm(@PathVariable("mailNo") int mailNo, Model model) {
        String employeeId = (String) httpSession.getAttribute("loginId");
        String companyCode = (String) httpSession.getAttribute("companyCode");
        System.out.println(">>>>>>>>>>>>>" + mailNo);
        // 메일 상세 조회
        MailVO mailVO = new MailVO();
        mailVO.setMailNo(mailNo);
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);

        MailVO mailDetails = mailService.mailInfo(mailVO);
        List<FileVO> fileVOList = fsvc.selectFiles("MAIL", mailNo, companyCode);

        if (mailDetails != null) {
            model.addAttribute("mailInfo", mailDetails);
        } else {
            // 예외 처리 또는 기본값 설정
            model.addAttribute("mailInfo", new MailVO());
        }
        model.addAttribute("fileInfo", fileVOList);
        
        return "group/mail/replymail";
    }
    
 // 메일 답장 처리
    @PostMapping("/reply")
    public String replyToMail(MailVO mailVO, @RequestPart(required = false) MultipartFile[] files, Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        Integer senderId = (Integer) httpSession.getAttribute("employeeNo");
        String senderName = (String) httpSession.getAttribute("loginName");

        mailVO.setSenderId(senderId.toString());
        mailVO.setSenderName(senderName);
        mailVO.setCompanyCode(companyCode);
        
        // Null 체크 및 처리
        if (mailVO.getReceiverId() != null && !mailVO.getReceiverId().isEmpty()) {
            mailVO.setReceiverIds(mailVO.getReceiverId().split(","));
        } else {
            mailVO.setReceiverIds(new String[0]); // 빈 배열로 초기화
        }

        int result = mailService.replyToMail(mailVO, files);
        
        if (result > 0) {
            model.addAttribute("successMessage", true);
        } else {
            model.addAttribute("errorMessage", true);
        }
        
        return "group/mail/replymail"; 
    }
 // 메일 상태 변경 핸들러 수정
    @PostMapping("/actions")
    public ResponseEntity<String> handleMailActions(@RequestBody MailVO mailVO) {
        System.out.println("Action: " + mailVO.getAction()); // 액션 값 출력
        System.out.println("Mail IDs: " + mailVO.getMailIds()); // 메일 ID 출력

        String employeeId = (String) httpSession.getAttribute("loginId");

        if ("IMPORT".equals(mailVO.getAction())) {
            mailService.updateMailStatus(mailVO.getMailIds(), employeeId, "IMPORT");
        } else if ("TRASH".equals(mailVO.getAction())) {
            mailService.updateMailStatus(mailVO.getMailIds(), employeeId, "TRASH");
        } else if ("RECEIVED".equals(mailVO.getAction())) {
            mailService.updateMailStatus(mailVO.getMailIds(), employeeId, "RECEIVED");
        }
        
        return ResponseEntity.ok("Success");
    }
    
    // 메일 상태 변경 핸들러 수정
    @PostMapping("/removes")
    public ResponseEntity<String> handleMail(@RequestBody MailVO mailVO) {
        System.out.println("Action: " + mailVO.getAction()); // 액션 값 출력
        System.out.println("Mail IDs: " + mailVO.getMailIds()); // 메일 ID 출력

        String employeeId = (String) httpSession.getAttribute("loginId");

        if ("DELETE".equals(mailVO.getAction())) {
            // 메일 상태를 DELETE로 업데이트합니다.
            mailService.updateMailStatus(mailVO.getMailIds(), employeeId, "DELETE");
            System.out.println(mailVO.getAction()+"123123123123");
        } 
        
        return ResponseEntity.ok("Success");
    }
}
