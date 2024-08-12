package com.arion.app.group.main.approval.web;

import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.arion.app.group.main.approval.service.TemplateService;
import com.arion.app.group.main.approval.service.TemplateVO;

@Controller
public class TemplateController {

	@Autowired
	TemplateService tsvc;

	@GetMapping("/group/doc/template")
	public String templateList(Model model, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");
		List<TemplateVO> tempList = tsvc.tempList(companyCode);
		List<String> fileContents = new ArrayList<>();

		for (TemplateVO temp : tempList) {
			String fileContent = "";
			String filePath = "D:/upload/templates/" + temp.getDocFile();
			File file = new File(filePath);

			if (file.exists()) {
				if (file.getName().endsWith(".hwp")) {
					try {
						HWPFile hwpFile = HWPReader.fromFile(file.getPath());
						if (hwpFile != null) {
							fileContent = TextExtractor.extract(hwpFile,
									TextExtractMethod.InsertControlTextBetweenParagraphText);
							fileContent = convertToHTML(fileContent);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try (BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
						StringBuilder sb = new StringBuilder();
						String line;
						while ((line = br.readLine()) != null) {
							sb.append(line).append("<br/>");
						}
						fileContent = sb.toString();
					} catch (IOException e) {
						e.printStackTrace();
						fileContent = "텍스트 파일을 읽는 중 오류가 발생했습니다.";
					}
				}
			} else {
				fileContent = "파일을 찾을 수 없습니다.";
			}

			fileContents.add(fileContent);
		}

		model.addAttribute("tempList", tempList);
		model.addAttribute("fileContents", fileContents);

		return "group/document/template/templateList";
	}

	@GetMapping("/group/doc/insertTemp")
	public String insertTempForm() {
		return "group/document/template/templateInsert";
	}

	@PostMapping("/insertTemp")
	public String insertTemp(TemplateVO tempVO, @RequestParam("docFile") MultipartFile file, HttpSession session) {
		String fileName = null;
		String companyCode = (String) session.getAttribute("companyCode");

		// 파일이 비어있지 않은 경우 처리
		if (!file.isEmpty()) {
			fileName = file.getOriginalFilename();
			String directoryPath = "D:/upload/templates/";

			// 디렉토리 생성
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// 파일 저장
			File saveFile = new File(directoryPath + fileName);
			try {
				file.transferTo(saveFile);
			} catch (IOException e) {
				e.printStackTrace();
				return "error"; // 오류 발생 시 에러 페이지로 이동
			}
			tempVO.setDocFile(fileName);
		}

		tempVO.setCompanyCode(companyCode);
		tsvc.insertTemp(tempVO);

		return "redirect:/group/doc/template";
	}

	private String convertToHTML(String text) {
		text = text.replace("\n", "<br/>");
		return text;
	}
}
