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
	public String insertTemp(@RequestParam("files") MultipartFile[] files, @ModelAttribute TemplateVO tempVO, HttpSession session) {
	    String companyCode = (String) session.getAttribute("companyCode");
	    String directoryPath = "D:/upload/templates/";
	    System.out.println("docType: " + tempVO.getDocType());
	    
	    if (files.length > 0) {
	        MultipartFile docFile = files[0];
	        if (!docFile.isEmpty()) {
	            String fileName = docFile.getOriginalFilename();
	            File directory = new File(directoryPath);

	            if (!directory.exists()) {
	                directory.mkdirs();
	            }

	            String saveName = Paths.get(directoryPath + fileName).toString();
	            try {
	                docFile.transferTo(Paths.get(saveName));
	                tempVO.setDocFile(fileName);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    if (files.length > 1) {
	        MultipartFile docImg = files[1];
	        if (!docImg.isEmpty()) {
	            String imageFileName = docImg.getOriginalFilename() + ".png";
	            String imagePath = directoryPath + imageFileName;
	            System.out.println(">>>>>>>>>>>>>>>>>>>>" + "docType: " + tempVO.getDocType());
	            System.out.println(">>>>>>>>>>>>>>>>>>>>" + "imageFileName: " + imageFileName);
	            try (OutputStream stream = new FileOutputStream(imagePath)) {
	                stream.write(docImg.getBytes());
	                tempVO.setDocImg(imagePath);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    tempVO.setCompanyCode(companyCode);
	    tsvc.insertTemp(tempVO);

	    return "redirect:/group/doc/template";
	}

	//파일 업로드전 미리보기
	@PostMapping("/previewHwp")
	@ResponseBody
	public String previewHwp(@RequestParam("file") MultipartFile file) {
	    String fileContent = "";
	    
	    try {
	        // 임시 파일로 저장
	        File tempFile = File.createTempFile("upload-", ".hwp");
	        file.transferTo(tempFile);

	        // HWP 파일 읽기
	        HWPFile hwpFile = HWPReader.fromFile(tempFile.getPath());
	        if (hwpFile != null) {
	            fileContent = TextExtractor.extract(hwpFile, TextExtractMethod.InsertControlTextBetweenParagraphText);
	            fileContent = convertToHTML(fileContent);
	        }
	        // 임시 파일 삭제
	        tempFile.delete();
	    } catch (Exception e) {
	        e.printStackTrace();
	        fileContent = "HWP 파일을 읽는 중 오류가 발생했습니다.";
	    }

	    return fileContent;
	}
	
	private String convertToHTML(String text) {
		text = text.replace("\n", "<br/>");
		return text;
	}
}
