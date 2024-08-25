package com.arion.app.group.main.approval.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.common.service.EmployeesVO;
import com.arion.app.common.service.FileService;
import com.arion.app.common.service.FileVO;
import com.arion.app.group.board.service.Criteria;
import com.arion.app.group.board.service.PageDTO;
import com.arion.app.group.main.approval.service.ApprovalService;
import com.arion.app.group.main.approval.service.ApprovalVO;
import com.arion.app.group.main.approval.service.DocAccessService;
import com.arion.app.group.main.approval.service.DocAccessVO;
import com.arion.app.group.main.approval.service.DocumentService;
import com.arion.app.group.main.approval.service.DocumentVO;
import com.arion.app.group.main.approval.service.HolDocService;
import com.arion.app.group.main.approval.service.HolDocVO;
import com.arion.app.group.main.approval.service.SignService;
import com.arion.app.group.main.approval.service.SignVO;
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

	@Autowired
	FileService fsvc;
	
	@Autowired
	ApprovalService asvc;
	
	@Autowired
	DocAccessService dasvc;
	
	@Autowired
	SignService ssavc;
	
	@Autowired
	HolDocService hdsvc;
	
	 // 날짜와 시간을 포맷팅하는 메서드
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return (date != null) ? dateFormat.format(date) : "";
    }

    private String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return (date != null) ? timeFormat.format(date) : "";
    }
	
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
	
	@PostMapping("/group/doc/writeDoc")
	@ResponseBody
	public String insertDoc(
	        	DocumentVO documentVO,
	        	HolDocVO holDocVO,
	        	@RequestParam List<Integer> approverIds,
	        	@RequestParam(value = "referenceIds", required = false) List<Integer> referenceIds,
	        	HttpSession session,
	        	@RequestPart MultipartFile[] files) {
		
		System.out.println("Approver IDs: " + approverIds);
	    System.out.println("Reference IDs: " + referenceIds);

	    
	    String companyCode = (String) session.getAttribute("companyCode");
	    int employeeNo = (int) session.getAttribute("employeeNo");

	    documentVO.setCompanyCode(companyCode);
	    documentVO.setEmployeeNo(employeeNo);
	    holDocVO.setEmployeeNo(employeeNo);
	    holDocVO.setCompanyCode(companyCode);
	    
	    dsvc.insertDocument(documentVO, holDocVO, approverIds, referenceIds, files, companyCode);

	    return "등록되었습니다";
	}
	
	@GetMapping("/group/doc/apprProgress")
	public String apprProgressList(Model model, Criteria criteria, HttpSession session) {
		int employeeNo = (int) session.getAttribute("employeeNo");
		String companyCode = (String) session.getAttribute("companyCode");
		
		List<String> department = dsvc.selectDepartment(companyCode);
		DocAccessVO docAccessVO = new DocAccessVO();
		docAccessVO.setEmployeeNo(employeeNo);
		docAccessVO.setCompanyCode(companyCode);
		
		List<DocumentVO> apprProgressList = dsvc.apprProgressList(docAccessVO, criteria);
		int totalCount = dsvc.countApprProgressList(docAccessVO, criteria);
		
		PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
		model.addAttribute("pageDTO", pageDTO);
		model.addAttribute("proDoc", apprProgressList);
		model.addAttribute("critera", criteria);
		model.addAttribute("department", department);
		
		return "group/document/approval/apprProgressList";
	}
	
	@GetMapping("/group/doc/documentInfo")
	public String documentInfo(Model model, HttpSession session, @RequestParam(value = "docNo", required = false) int docNo) {
		String companyCode = (String) session.getAttribute("companyCode");
		int employeeNo = (int) session.getAttribute("employeeNo");
		String employeeId = (String) session.getAttribute("loginId");
		DocumentVO documentVO = new DocumentVO();
		documentVO.setCompanyCode(companyCode);
		documentVO.setDocNo(docNo);
		DocumentVO docInfo = dsvc.documentInfo(documentVO);
		System.out.println(">>>>>>>>>>>>docNo : " + docNo);
		
		List<FileVO> fileList = fsvc.selectFiles("document", docNo, companyCode);
		
		ApprovalVO approvalVO = new ApprovalVO();
		approvalVO.setCompanyCode(companyCode);
		approvalVO.setDocNo(docNo);
		List<ApprovalVO> apprInfo = asvc.apprInfo(approvalVO);
		
		DocAccessVO docAccessVO = new DocAccessVO(); 
		docAccessVO.setCompanyCode(companyCode);
		docAccessVO.setDocNo(docNo);
		docAccessVO.setEmployeeNo(employeeNo);
		List<DocAccessVO> referenceInfo = dasvc.referenceInfo(docAccessVO);
		int accessAppr = dasvc.accessAppr(docAccessVO);
		
		int approverIndex = -1;
		for (int i = 0; i < apprInfo.size(); i++) {
			if (apprInfo.get(i).getEmployeeNo() == employeeNo) {
				approverIndex = i;
				break;
			}
		}
		SignVO signVO = new SignVO();
		signVO.setCompanyCode(companyCode);
		signVO.setEmployeeId(employeeId);
		String employeeSign = ssavc.empSign(companyCode, employeeId);		
		
		HolDocVO holDocVO = new HolDocVO();
		holDocVO.setCompanyCode(companyCode);
		holDocVO.setDocNo(docNo);
		HolDocVO holDocInfo = hdsvc.holDocInfo(holDocVO);
		
		 // 날짜 및 시간 포맷팅
        if (holDocInfo != null) {
            String formatStartDate = formatDate(holDocInfo.getStartDate());
            String formatStartTime = formatTime(holDocInfo.getStartDate());
            String formatEndDate = formatDate(holDocInfo.getEndDate());
            String formatEndTime = formatTime(holDocInfo.getEndDate());

            model.addAttribute("formatStartDate", formatStartDate);
            model.addAttribute("formatStartTime", formatStartTime);
            model.addAttribute("formatEndDate", formatEndDate);
            model.addAttribute("formatEndTime", formatEndTime);
        }
		
		model.addAttribute("apprInfo", apprInfo);
		model.addAttribute("fileList", fileList);
		model.addAttribute("docInfo", docInfo);
		model.addAttribute("refInfo", referenceInfo);
		model.addAttribute("accessAppr", accessAppr);
		model.addAttribute("empSign", employeeSign);
		model.addAttribute("approverIndex", approverIndex);
		model.addAttribute("holInfo", holDocInfo);	
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + holDocInfo);
		return "group/document/approval/documentInfo";
	}
	
	 @PostMapping("/group/doc/checkApproval")
	 @ResponseBody
	 public boolean checkApproval(HttpSession session, @RequestParam int docNo) {
		String companyCode = (String) session.getAttribute("companyCode");
		int employeeNo = (int) session.getAttribute("employeeNo");
	   // 결재 가능 여부를 확인
		return asvc.checkApproval(employeeNo, docNo, companyCode) > 0;
	 }
	
	 @PostMapping("/group/doc/approveDocument")
	 @ResponseBody
	 public String approveDocument(HttpSession session, @RequestParam int docNo, @RequestParam String signImg) {
		 String companyCode = (String) session.getAttribute("companyCode");
		 int employeeNo = (int) session.getAttribute("employeeNo");
		 dsvc.updateApprStatus(docNo, companyCode, employeeNo, signImg);
		 return "결재가 성공적으로 처리되었습니다.";
		
	 }
	 
	 @PostMapping("/group/doc/checkCanReject")
	 @ResponseBody
	 public int checkCanReject(HttpSession session, @RequestParam int docNo) {
	     String companyCode = (String) session.getAttribute("companyCode");
	     int employeeNo = (int) session.getAttribute("employeeNo");
	     
	     int approvalCount = asvc.hasApproved(employeeNo, docNo, companyCode);
	     System.out.println("Approval Count: " + approvalCount);
	    
	     return approvalCount;
	 }	
	 
	 @PostMapping("/group/doc/rejectDocument")
	 @ResponseBody
	 public String rejectDocument(HttpSession session, @RequestParam int docNo, @RequestParam String apprReason) {
	     String companyCode = (String) session.getAttribute("companyCode");
	     int employeeNo = (int) session.getAttribute("employeeNo");

	     try {
	    	 dsvc.updateRejectStatus(docNo, companyCode, employeeNo, apprReason);	    	 
	    	 return "문서가 성공적으로 반려되었습니다.";
	     }catch (Exception e) {
	    	 return "반려 처리중 오류가 발생했습니다.";
	     }
	 }
	 
	 @GetMapping("/group/doc/apprReject")
	 public String apprRejectList(Model model, Criteria criteria, HttpSession session) {
		 int employeeNo = (int) session.getAttribute("employeeNo");
		 String companyCode = (String) session.getAttribute("companyCode");
		
		 List<String> department = dsvc.selectDepartment(companyCode);
		 DocAccessVO docAccessVO = new DocAccessVO();
		 docAccessVO.setEmployeeNo(employeeNo);
		 docAccessVO.setCompanyCode(companyCode);
			
		 List<DocumentVO> apprRejectList = dsvc.apprRejectList(docAccessVO, criteria);
		 int totalCount = dsvc.countApprRejectList(docAccessVO, criteria);
		 
		 PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
		 model.addAttribute("pageDTO", pageDTO);
		 model.addAttribute("reDoc", apprRejectList);
		 model.addAttribute("critera", criteria);
		 model.addAttribute("department", department);
		 
		 return "group/document/approval/apprRejectList";
	 }
	 
	 @GetMapping("/group/doc/apprFinish")
	 public String apprFinishList(Model model, Criteria criteria, HttpSession session) {
		 int employeeNo = (int) session.getAttribute("employeeNo");
		 String companyCode = (String) session.getAttribute("companyCode");
		 
		 List<String> department = dsvc.selectDepartment(companyCode);
		 DocAccessVO docAccessVO = new DocAccessVO();
		 docAccessVO.setEmployeeNo(employeeNo);
		 docAccessVO.setCompanyCode(companyCode);
			
		 List<DocumentVO> apprFinishList = dsvc.apprFinishList(docAccessVO, criteria);
		 int totalCount = dsvc.countApprFinishList(docAccessVO, criteria);
		 
		 PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
		 model.addAttribute("pageDTO", pageDTO);
		 model.addAttribute("finishDoc", apprFinishList);
		 model.addAttribute("critera", criteria);
		 model.addAttribute("department", department);
		 
		 return "group/document/approval/apprFinishList";
	 }
	 
	 @GetMapping("/group/doc/apprWait")
	 public String apprWaitList(Model model, Criteria criteria, HttpSession session) {
		 int employeeNo = (int) session.getAttribute("employeeNo");
		 String companyCode = (String) session.getAttribute("companyCode");
		 
		 List<String> department = dsvc.selectDepartment(companyCode);
		 DocAccessVO docAccessVO = new DocAccessVO();
		 docAccessVO.setEmployeeNo(employeeNo);
		 docAccessVO.setCompanyCode(companyCode);
			
		 List<DocumentVO> apprWaitList = dsvc.apprWaitList(docAccessVO, criteria);
		 int totalCount = dsvc.countApprWaitList(docAccessVO, criteria);
		 
		 PageDTO pageDTO = new PageDTO(10, totalCount, criteria);
		 model.addAttribute("pageDTO", pageDTO);
		 model.addAttribute("waitDoc", apprWaitList);
		 model.addAttribute("critera", criteria);
		 model.addAttribute("department", department);
		 
		 return "group/document/approval/apprWaitList";
	} 
	 
}