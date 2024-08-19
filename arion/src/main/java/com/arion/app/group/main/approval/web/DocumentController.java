package com.arion.app.group.main.approval.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.PageDTO;
import com.arion.app.group.main.approval.service.ApprovalVO;
import com.arion.app.group.main.approval.service.DocAccessVO;
import com.arion.app.group.main.approval.service.DocumentService;
import com.arion.app.group.main.approval.service.DocumentVO;
import com.arion.app.group.main.approval.service.TemplateService;
import com.arion.app.group.main.approval.service.TemplateVO;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;

@Controller
public class DocumentController {

	@Autowired
	TemplateService tsvc;

	@Autowired
	DocumentService dsvc;

	@GetMapping("/group/doc/document")
	public String document(Model model, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		List<TemplateVO> tempList = tsvc.tempList(companyCode);
		model.addAttribute("tempList", tempList);

		return "group/document/approval/document";
	}

	@GetMapping("/group/doc/templateContent")
	@ResponseBody
	public String getTemplateContent(@RequestParam String tempNo, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		TemplateVO templateVO = new TemplateVO();
		templateVO.setCompanyCode(companyCode);
		templateVO.setTempNo(tempNo);

		TemplateVO template = tsvc.tempInfo(templateVO);
		String filePath = "D:/upload/templates/" + template.getDocFile();
		String docType = template.getDocType();

		if (docType.equals(".hwp")) {
			return convertHWPToHtml(filePath);
		} else if (docType.equals(".html")) {
			return convertHtmlToText(filePath);
		} else {
			return "지원하지 않는 파일 형식입니다.";
		}
	}

	private String convertHWPToHtml(String filePath) {
		try {
			HWPFile hwpFile = HWPReader.fromFile(filePath);
			if (hwpFile != null) {
				String extractedText = TextExtractor.extract(hwpFile,
						TextExtractMethod.InsertControlTextBetweenParagraphText);
				return extractedText.replace("\n", "<br/>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "HWP 파일을 처리하는 중 오류가 발생했습니다.";
	}

	private String convertHtmlToText(String filePath) {
		StringBuilder fileContent = new StringBuilder();
		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			 String line;
             while ((line = br.readLine()) != null) {
                 fileContent.append(line).append("\n");
             }
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "HTML 파일을 처리하는 중 오류가 발생했습니다.";
		}
		return fileContent.toString();
	}

	//조직도 모달창 부서 목록 가져오기
	@GetMapping("/group/doc/department")
	@ResponseBody
	public List<String> departmentList(HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		return dsvc.selectDepartment(companyCode);
	}
	
	//조직도 모달창 해당 부서의 사원목록 가져오기
	@GetMapping("/group/doc/employee")
	@ResponseBody
	public List<EmployeesVO> employeeList(@RequestParam String departmentName, HttpSession session){
		String companyCode = (String) session.getAttribute("companyCode");
		
		return dsvc.selectEmployeeList(companyCode, departmentName);
	}
	
	@PostMapping("/writeDoc")
	public String insertDoc(
	        	DocumentVO documentVO,
	        	@RequestParam List<Integer> approverIds,
	        	@RequestParam List<Integer> referenceIds,
	        	HttpSession session,
	        	@RequestPart MultipartFile[] files) {
		
		
		System.out.println("Approver IDs: " + approverIds);
	    System.out.println("Reference IDs: " + referenceIds);
		
	    String companyCode = (String) session.getAttribute("companyCode");
	    int employeeNo = (int) session.getAttribute("employeeNo");

	    documentVO.setCompanyCode(companyCode);
	    documentVO.setEmployeeNo(employeeNo);

	    dsvc.insertDocument(documentVO, approverIds, referenceIds, files, companyCode);

	    return "redirect:/group";
	}
	
	@GetMapping("/group/doc/apprProgress")
	public String waitApprList(Model model, Criteria criteria, HttpSession session) {
		int employeeNo = (int) session.getAttribute("employeeNo");
		String companyCode = (String) session.getAttribute("companyCode");
		
		DocAccessVO docAccessVO = new DocAccessVO();
		docAccessVO.setEmployeeNo(employeeNo);
		docAccessVO.setCompanyCode(companyCode);
		
		List<DocumentVO> apprProgressList = dsvc.apprProgressList(docAccessVO, criteria);
		int totalCount = dsvc.countApprProgressList(docAccessVO, criteria);
		
		PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
		model.addAttribute("pageDTO", pageDTO);
		model.addAttribute("proDoc", apprProgressList);
		model.addAttribute("critera", criteria);
		
		return "group/document/approval/apprProgressList";
	}
	
	
}