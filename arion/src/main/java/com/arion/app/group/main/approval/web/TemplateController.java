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
	public String insertTemp(TemplateVO tempVO, HttpSession session, @RequestParam("image") String image) {
	    String companyCode = (String) session.getAttribute("companyCode");

	    // 파일 저장 처리
	    String fileName = null;
	    String directoryPath = "D:/upload/templates/";
	    
	    if (!tempVO.getDocFileName().isEmpty()) {
	        fileName = tempVO.getDocFileName().getOriginalFilename();
	        File directory = new File(directoryPath);
	        
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        String saveName = Paths.get(directoryPath + fileName).toString();
	        try {
	            tempVO.getDocFileName().transferTo(Paths.get(saveName));
	            tempVO.setDocFile(fileName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // 이미지 저장 처리
	    String base64Image = image.split(",")[1]; // Data URL의 "data:image/png;base64," 부분 제거
	    byte[] imageBytes = Base64.getDecoder().decode(base64Image);

	    String imageFileName = tempVO.getDocType() + "-" + UUID.randomUUID() + ".png";
	    String imagePath = directoryPath + imageFileName;

	    try (OutputStream stream = new FileOutputStream(imagePath)) {
	        stream.write(imageBytes);
	        tempVO.setDocImg(imagePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // 회사 코드 설정
	    tempVO.setCompanyCode(companyCode);

	    // 데이터베이스에 정보 저장 (파일 경로와 이미지 경로를 포함하여 한 번에 저장)
	    tsvc.insertTemp(tempVO);

	    return "redirect:/group/doc/template";
	}


	private String convertToHTML(String text) {
		text = text.replace("\n", "<br/>");
		return text;
	}
}
