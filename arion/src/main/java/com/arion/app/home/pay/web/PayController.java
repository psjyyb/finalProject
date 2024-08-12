package com.arion.app.home.pay.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arion.app.home.pay.service.ContractVO;
import com.arion.app.home.pay.service.PayService;
import com.arion.app.home.pay.service.PayVO;
import com.arion.app.security.service.CompanyVO;

/*
 * 작성자 : 박성준
 * 작성일자 : 2024-08-10
 * 결제및계약서 : 계약서작성, 결제카드 등록, 결제
 * */

@Controller
public class PayController {

	private PayService payService;

	@Autowired
	public PayController(PayService payService) {
		this.payService = payService;
	}

	@GetMapping("/home/payView")
	public String payView(PayVO payVO, Model model, HttpSession session) {
		String comCode = (String) session.getAttribute("companyCode");
		CompanyVO cvo = payService.selectCom(comCode, payVO);
		int conNo = payService.findLastNo();
		model.addAttribute("conNo", conNo);
		model.addAttribute("comInfo", cvo);
		model.addAttribute("payInfo", payVO);
		return "pay/payView";
	}

	@PostMapping("/home/payView")
	public String payProcess(@RequestParam("signatureData") String signatureData, ContractVO contractVO, Model model) {
		String fileName = null;
		try {
			// 이미지 데이터에서 Base64 접두사 제거
			String base64Image = signatureData.split(",")[1];
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);

			// 저장할 디렉토리 경로
			String directoryPath = "D:/upload/signatures/";
			File directory = new File(directoryPath);

			// 디렉토리가 존재하지 않으면 생성
			if (!directory.exists()) {
				directory.mkdirs();
			}

			UUID uuid = UUID.randomUUID();
			// 파일 경로 설정
			fileName = uuid + "_" + contractVO.getCompanyCode() + ".png";
			String filePath = directoryPath + fileName;

			// 파일 저장
			try (OutputStream stream = new FileOutputStream(filePath)) {
				stream.write(imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contractVO.setContractSign(fileName);
		String customerKey = UUID.randomUUID().toString();
		contractVO.setCustomerkey(customerKey);
		int result = payService.contractInsert(contractVO);
		model.addAttribute("payInfo", contractVO);
		String url = null;
		if (result > 0) {
			url = "redirect:/pay/firstPay";
		} else {
			url = "redirect:/groupAdmin/GASubInfo";
		}
		
		return url;
	}
	
	@GetMapping("/pay/firstPay")
	public String firstPay() {
		return "/pay/firstPay";
	}
//	@PostMapping("/home/success")
//	public String paySuccess();

}
