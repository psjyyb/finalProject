package com.arion.app.group.main.mail.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		String companyCode = (String) httpSession.getAttribute("companyCode");
		String employeeId = (String) httpSession.getAttribute("loginId");
		MailVO mailVO = new MailVO();
		mailVO.setCompanyCode(companyCode);
		mailVO.setEmployeeId(employeeId);
		List<MailVO> receivedMails = mailService.mailList(mailVO);
		model.addAttribute("receivedMails", receivedMails);

		return "group/mail/Mymail";
	}

	// 메일 보내기 (페이지)
	@GetMapping("/writemail/{employeeId}")
	public String mailSendForm(@PathVariable("employeeId") String employeeId, Model model) {
		model.addAttribute("employeeId", employeeId);
		return "group/mail/writemail";
	}

	// 메일 보내기 (처리)
//	@PostMapping("/writemail/{employeeId}")
//	public String mailSendProcess(@PathVariable("employeeId") String employeeId,
//	                              @ModelAttribute MailVO mailVO,
//	                              @RequestParam("receivers") List<String> receivers,
//	                              @RequestParam("files") List<MultipartFile> files) {
//	    String companyCode = (String) httpSession.getAttribute("companyCode");
//	    mailVO.setCompanyCode(companyCode);
//	    mailVO.setEmployeeId(employeeId);
//
//	    // 메일 보내기 서비스 호출
//	    int mailNo = mailService.mailSend(mailVO, receivers, files);
//	    if (mailNo > 0) {
//	        return "redirect:/group/mail/Mymail/" + employeeId;
//	    } else {
//	        return "redirect:/group/mail/writemail/" + employeeId;
//	    }
//	}
	//메일상세페이지
	@GetMapping("/detailmail/{mailNo}/{employeeId}")
	public String mailInfo(@PathVariable("mailNo") int mailNo, @PathVariable("employeeId") String employeeId,
	                       Model model) {
	    System.out.println("Received mailNo: " + mailNo); // 로그 확인
	    System.out.println("Received employeeId: " + employeeId); // 로그 확인

	    MailVO mailVO = new MailVO();
	    mailVO.setMailNo(mailNo);
	    mailVO.setEmployeeId(employeeId);

	    MailVO mailInfo = mailService.mailInfo(mailVO);
	    model.addAttribute("mail", mailInfo);

	    return "group/mail/detailmail";
	}
	// 메일 삭제 (처리)
	@PostMapping("/delete/{employeeId}")
	@ResponseBody
	public Map<String, Object> deleteMail(@PathVariable("employeeId") String employeeId, @RequestBody MailVO mailVO) {
		mailVO.setCompanyCode((String) httpSession.getAttribute("companyCode"));
		mailVO.setEmployeeId(employeeId);

		return mailService.deleteMail(mailVO);
	}

	// 중요 메일 조회 (페이지)
	@GetMapping("/importmail/{employeeId}")
	public String importMailList(@PathVariable("employeeId") String employeeId, Model model) {
		String companyCode = (String) httpSession.getAttribute("companyCode");

		MailVO mailVO = new MailVO();
		mailVO.setCompanyCode(companyCode);
		mailVO.setEmployeeId(employeeId);

		List<MailVO> importantMails = mailService.importMailList(mailVO);
		model.addAttribute("importantMails", importantMails);

		return "group/mail/importmail";
	}

	// 휴지통 메일 조회 (페이지)
	@GetMapping("/trashmail/{employeeId}")
	public String trashMailList(@PathVariable("employeeId") String employeeId, Model model) {
		String companyCode = (String) httpSession.getAttribute("companyCode");

		MailVO mailVO = new MailVO();
		mailVO.setCompanyCode(companyCode);
		mailVO.setEmployeeId(employeeId);

		List<MailVO> trashMails = mailService.deleteMailList(mailVO);
		model.addAttribute("trashMails", trashMails);
		return "group/mail/trashmail";
	}

	// 보낸 메일 조회 (페이지)
	@GetMapping("/sendmail/{employeeId}")
	public String sendMailList(@PathVariable("employeeId") String employeeId, Model model) {
		String companyCode = (String) httpSession.getAttribute("companyCode");

		MailVO mailVO = new MailVO();
		mailVO.setCompanyCode(companyCode);
		mailVO.setEmployeeId(employeeId);

		List<MailVO> sentMails = mailService.sendMailList(mailVO);
		model.addAttribute("sentMails", sentMails);
		return "group/mail/sendmail";
	}
}
