package com.arion.app.home.board.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;
import com.arion.app.home.board.service.HomeQnaVO;
import com.arion.app.home.board.service.QnaService;
import com.arion.app.security.service.CompanyVO;

@Controller
public class HomeBoardController {

	@Autowired
	QnaService qsvc;
	
	@Autowired
	FileService fsvc;

	@GetMapping("/home/faq")
	public String faq() {
		return "home/board/faq";
	}

	@GetMapping("/home/qna")
	public String qna(Model model) {
		List<HomeQnaVO> qvo = qsvc.selectQnAList();
		model.addAttribute("qnaList", qvo);
		return "home/board/qna";
	}

	@GetMapping("/home/qnaInsert")
	public String qnaInsert(Model model, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		if (companyCode != null) {
			List<CompanyVO> companyList = qsvc.selectCompany(companyCode);
			if (!companyList.isEmpty()) {
				model.addAttribute("company", companyList.get(0));
			} else {
				model.addAttribute("company", new CompanyVO());
			}
		} else {
			model.addAttribute("company", new CompanyVO()); 
		}
		return "home/board/qnaInsert";
	}
	
	@PostMapping("/qnaInsert")
    public String insertQna(HomeQnaVO homeQnaVO, @RequestPart MultipartFile[] files,  HttpSession session) {
        String companyCode = (String) session.getAttribute("companyCode");
        homeQnaVO.setQnaCompany(companyCode);
        System.out.println(">>>>>>>>>>>"+files[0].getOriginalFilename());
       // System.out.println(">>>>>>>>>>>"+files2[0].getOriginalFilename());
        qsvc.insertQna(homeQnaVO, files, companyCode);
        return "redirect:/home/qna";
    }
	
	@GetMapping("/home/qnaInfo")
	public String qnaInfo(HomeQnaVO homeQnaVO, Model model, HttpSession session) {
        HomeQnaVO findVO = qsvc.QnaInfo(homeQnaVO);
        List<FileVO> fileVOList = fsvc.selectFiles("qna", homeQnaVO.getQnaNo());
        model.addAttribute("qnaInfo", findVO);
        model.addAttribute("fileInfo", fileVOList);
        return "home/board/qnaInfo";
    }
	
	@GetMapping("/home/qnaDelete")
	public String boardDelete(@RequestParam Integer qnaNo) {
		qsvc.deleteQna(qnaNo);
		fsvc.deleteFiles("qna", qnaNo);
		return "redirect:/home/qna";
	}
	
}
