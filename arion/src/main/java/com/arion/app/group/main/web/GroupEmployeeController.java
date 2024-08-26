package com.arion.app.group.main.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.common.service.FileService;
import com.arion.app.group.board.service.BoardService;
import com.arion.app.group.board.service.BoardVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.ReplyService;
import com.arion.app.group.main.mail.service.MailService;
import com.arion.app.group.main.mail.service.MailVO;
import com.arion.app.group.main.service.AttService;
import com.arion.app.group.main.service.AttVO;
import com.arion.app.group.main.service.MainService;

@Controller
public class GroupEmployeeController {

    @Autowired
    private MainService mainService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private MailService mailService;

    @Autowired
    private BoardService boardService; 
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private ReplyService replyService;
    
    @Autowired
    private AttService attService;

    @GetMapping("/group")
    public String getmoduleList(Model model) {
        String companyCode = (String) httpSession.getAttribute("companyCode");
        String employeeId = (String) httpSession.getAttribute("loginId");

        // 받은 메일 가져오기
        MailVO mailVO = new MailVO();
        mailVO.setCompanyCode(companyCode);
        mailVO.setSenderId(employeeId);
        mailVO.setMailStatus("RECEIVED");
        Criteria mailCriteria = new Criteria(); // 페이지네이션을 위한 기준 객체
        List<MailVO> receivedMails = mailService.mailList(mailVO, mailCriteria);
        model.addAttribute("receivedMails", receivedMails);

        // 공지사항 가져오기
        Criteria boardCriteria = new Criteria(); // 페이지네이션을 위한 기준 객체
        boardCriteria.setCompanyCode(companyCode);
        boardCriteria.setBoardCategory("Y01"); // 공지사항 카테고리 설정

        List<BoardVO> noticeboardList = boardService.noticeboardList(boardCriteria);
        model.addAttribute("noticeboardList", noticeboardList);

        return "group/main/groupMain";
    }
    @RequestMapping("/group/redirectNoticeboardInfo")
    public String redirectToNoticeboardInfo(@RequestParam("boardNo") Integer boardNo, HttpServletResponse response) throws IOException {
        response.sendRedirect("/group/noticeboardInfo?boardNo=" + boardNo);
        return null; // 리다이렉트로 인해 이 메서드는 반환하지 않음
    }
    
    

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }
    
    //출근 기록
    @PostMapping("/group/startAtt")
    public ResponseEntity<String> startAtt() {
        Integer empNo = (Integer) httpSession.getAttribute("employeeNo");
        String companyCode = (String) httpSession.getAttribute("companyCode");

        AttVO attVO = new AttVO();
        attVO.setEmpNo(empNo);
        attVO.setCompanyCode(companyCode);
     
        int result = attService.startAtt(attVO);

        if (result == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already_checked_in"); // 출근 기록이 이미 있음

        }

        return ResponseEntity.ok("success"); // 출근 성공
    }
    

    @PostMapping("/group/endAtt")
    public ResponseEntity<String> endAtt() {
        Integer empNo = (Integer) httpSession.getAttribute("employeeNo");

        // empNo가 null인지 확인
        if (empNo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("empNo내놔 ㅋ");
        }

        // 출근 기록 조회
        AttVO attVO = attService.getAttendanceByDate(empNo, new Date());

        if (attVO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("님 출근아직안함 ㅋ");
        }

        // endTime이 이미 설정된 경우 (퇴근 처리된 경우)
        if (attVO.getEndTime() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already_checked_out");
        }

        // 퇴근 시간 설정 및 empNo 명시적으로 설정
        attVO.setEmpNo(empNo);
        attVO.setEndTime(new Date());

        // 퇴근 기록 업데이트
        int result = attService.endAtt(attVO);

        if (result == 1) {
            return ResponseEntity.ok("success"); // 퇴근 성공
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error"); // 퇴근 실패
        }
    }
}
