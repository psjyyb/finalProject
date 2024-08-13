package com.arion.app.home.pay.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arion.app.home.pay.service.ContractVO;
import com.arion.app.home.pay.service.PayService;
import com.arion.app.home.pay.service.PayVO;
import com.arion.app.security.service.CompanyVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		if (payVO.getRegularPaymentDate() == null) {
			payVO.setRegularPaymentDate("0");
		}
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
		Date fd = contractVO.getFinalDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fds = format.format(fd);
		contractVO.setFinalDates(fds);

		model.addAttribute("payInfo", contractVO);
		return "pay/firstPay";
	}

	@GetMapping("/pay/success")
	public String successPage(@RequestParam("data") String data, Model model, @RequestParam String customerKey,
			@RequestParam String authKey) {
		// JSON 데이터를 객체로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		ContractVO contractData = null;
		try {
			contractData = objectMapper.readValue(data, ContractVO.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String billingKey = payService.requestBillingKey(customerKey, authKey);
		contractData.setBillingkey(billingKey);
		contractData.setCustomerkey(customerKey);
		String companyCode = contractData.getCompanyCode();
		CompanyVO comVo = payService.emailNameSelect(companyCode);
		model.addAttribute("comInfo", comVo);
		model.addAttribute("payInfo", contractData);
		return "/pay/success"; // 성공 페이지로 리다이렉트
	}

	@GetMapping("/pay/fail")
	public String cardRegistrationFailed(
	    @RequestParam(value = "code", required = false) String code,
	    @RequestParam(value = "message", required = false) String message,
	    Model model) {
		System.out.println("=====================들어옴옴옴ㅇ몽모옴옴옴ㅇ몽모====================");
	    // 파라미터 디코딩
	    try {
	        if (message != null) {
	            message = URLDecoder.decode(message, StandardCharsets.UTF_8.toString());
	        }
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }

	    model.addAttribute("errorCode", code);
	    model.addAttribute("errorMessage", message);
	    return "pay/fail";
	}
	@PostMapping("/pay/result")
	@ResponseBody
	public Map<String, Object> payResult(@RequestBody ContractVO contractVO) {
		return payService.payEnd(contractVO);
	}

}
