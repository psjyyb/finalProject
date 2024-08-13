package com.arion.app.group.main.approval.web;

import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@GetMapping("/group/doc/tempInfo")
	public String tempInfo(TemplateVO tempVO, Model model, HttpSession session) {
		String companyCode = (String) session.getAttribute("companyCode");

		return "group/documnet/template/templateInfo";
	}

	@GetMapping("/group/doc/insertTemp")
	public String insertTempForm() {
		return "group/document/template/templateInsert";
	}

	@PostMapping("/insertTemp")
	public String insertTemp(TemplateVO tempVO, HttpSession session) {
		String fileName = null;
		String companyCode = (String) session.getAttribute("companyCode");

		if (!tempVO.getDocFileName().isEmpty()) {
			fileName = tempVO.getDocFileName().getOriginalFilename();
			String directoryPath = "D:/upload/templates/";

			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String saveName = Paths.get(directoryPath + fileName).toString();
			try {
				tempVO.getDocFileName().transferTo(Paths.get(saveName));
				tempVO.setDocFile(fileName);
				tempVO.setCompanyCode(companyCode);
				tsvc.insertTemp(tempVO);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/group/doc/template";
	}

	@PostMapping("/saveImage")
	@ResponseBody
	public String saveImage(TemplateVO tempVO, @RequestParam("image") String image) {
		String base64Image = image.split(",")[1]; // Data URL의 "data:image/png;base64," 부분을 제거
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);

		String directoryPath = "D:/upload/templates/";
		File directory = new File(directoryPath);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		String fileName = tempVO.getDocType() + ".png";
		String filePath = directoryPath + fileName;

		try (OutputStream stream = new FileOutputStream(filePath)) {
			stream.write(imageBytes);
			
			tempVO.setDocImg(fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
			return "이미지 저장에 실패했습니다.";
		}
		return "이미지가 성공적으로 저장되었습니다.";
		
	}

	private String convertToHTML(String text) {
		text = text.replace("\n", "<br/>");
		return text;
	}
}
