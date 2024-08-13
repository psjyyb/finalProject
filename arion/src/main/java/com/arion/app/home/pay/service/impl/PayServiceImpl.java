package com.arion.app.home.pay.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.home.pay.mapper.PayMapper;
import com.arion.app.home.pay.service.ContractVO;
import com.arion.app.home.pay.service.PayService;
import com.arion.app.home.pay.service.PayVO;
import com.arion.app.home.pay.service.UseModuleVO;
import com.arion.app.security.service.CompanyVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PayServiceImpl implements PayService {

	private GroupAdminMapper gaMapper;
	private PayMapper payMapper;
	private final String secretKey = "test_sk_mBZ1gQ4YVXQ1Oj2OJJvjrl2KPoqN";
	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private JavaMailSender mailSender;
	
	@Autowired
	public PayServiceImpl(GroupAdminMapper groupAdminMapper, PayMapper payMapper,JavaMailSender mailSender) {
		this.mailSender = mailSender;
		this.gaMapper = groupAdminMapper;
		this.payMapper = payMapper;
	}

	@Override
	public CompanyVO selectCom(String companyCode, PayVO payVO) {
		CompanyVO cvo = gaMapper.selectCom(companyCode);
		LocalDate date = LocalDate.now();
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String sdate = date.format(fm);
		int month = Integer.parseInt(payVO.getSubscriptionPeriod()) / 30;
		LocalDate fdate = date.plusMonths(month);
		cvo.setStartDate(sdate);
		cvo.setFinalDate(fdate);
		return cvo;
	}

	@Override
	public int findLastNo() {
		return payMapper.findLastNo();
	}

	@Override
	public CompanyVO emailNameSelect(String companyCode) {
		return payMapper.selectComInfo(companyCode);
	}

	@Override
	public String requestBillingKey(String customerKey, String authKey) {
		String secretKey = "test_sk_mBZ1gQ4YVXQ1Oj2OJJvjrl2KPoqN";
		String endpoint = "https://api.tosspayments.com/v1/billing/authorizations/" + authKey;

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(secretKey, "");
		headers.add("Content-Type", "application/json");

		// 필수 파라미터 포함
		Map<String, String> body = new HashMap<>();
		body.put("customerKey", customerKey);

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = "";
		try {
			requestBody = objectMapper.writeValueAsString(body);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				JsonNode root = objectMapper.readTree(response.getBody());
				return root.path("billingKey").asText();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error: " + response.getBody());
		}
		return null;
	}

	@Transactional // 첫결제
	@Override
	public Map<String, Object> payEnd(ContractVO contractVO) {
		System.out.println(contractVO + "첫주문시 결제금액 처리해야돼");
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = 0;
		result += contractInsert(contractVO);
		contractVO.setMonthPayPrice(contractVO.getFirstMonthAmount());
		System.out.println(contractVO + "첫주문 금액 변경");
		result += payInsert(contractVO);

		List<String> list = contractVO.getModuleNames();
		list.forEach(a -> {
			String trimmedValue = a.trim(); // 앞뒤 공백 제거
			payDetailInsert(contractVO.getPayNo(), trimmedValue);
			useModuleInsert(trimmedValue, contractVO.getCompanyCode(), contractVO.getContractNo());
		});
		if (result > 1) {
			isSuccessed = true;
		}

		map.put("result", isSuccessed);
		map.put("target", contractVO);
		return map;
	}

	@Override
	public int payInsert(ContractVO contractVO) {
		return payMapper.insertPay(contractVO);
	}

	@Override
	public int payDetailInsert(int payNo, String moduleName) {
		return payMapper.insertPayDetail(payNo, moduleName);
	}

	@Override
	public int contractInsert(ContractVO contractVO) {
		return payMapper.insertContract(contractVO);
	}

	@Override
	public int useModuleInsert(String moduleName, String companyCode, int contractNo) {
		return payMapper.insertSubModule(moduleName, companyCode, contractNo);
	}

	@Override
	public int comRespUpdate(ContractVO contractVO) {
		return payMapper.updateComResp(contractVO);
	}
	@Transactional
	//@Scheduled(cron = "0 0 12 * * ?") // 매일 낮 12 시에 실행, 초 분 시 월 요일
	//@Scheduled(fixedDelay = 10000, initialDelay = 5000)
	@Override
	public Map<String, Object> schedulePayEnd() {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		LocalDate now = LocalDate.now();
		int day = now.getDayOfMonth();
		// 계약 목록을 가져옵니다.
		List<ContractVO> cList = payMapper.contractList();
		cList.forEach(contract -> {
			if (contract.getRegularDate() == day) {
				contract.setOrderId(UUID.randomUUID().toString());
				
				List<UseModuleVO> mList = payMapper.useModule(contract.getContractNo());
				List<String> moduleNames = contract.getModuleNames();
				if (moduleNames == null) {
					moduleNames = new ArrayList<>();
					contract.setModuleNames(moduleNames);
				}
				for (UseModuleVO module : mList) {
					moduleNames.add(module.getModuleName());
					 String result = String.join(", ", moduleNames);
				}
				String moduleNamesString = String.join(", ", moduleNames);
				contract.setModuleString(moduleNamesString);
				int result = processPayment(contract);
				if(result > 0) {
					payInsert(contract);
					for (UseModuleVO module : mList) {
						payDetailInsert(contract.getPayNo(),module.getModuleName());
					}
				}
			}else if(contract.getRegularDate() == day+5) {
				sendSimpleEmail(contract.getCeoEmail());
				
			}
		});
		return map;
	}
	  public void sendSimpleEmail(String email) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("ARION 정기결제 5일전입니다.");
	        message.setText("안녕하세요 고객님! 저희 ARION 을 이용해 주셔서 감사합니다. 정기결제 5일 전이니 계좌를 확인해 주시고 결제가 원할하게 이루어질수있도록 금액을 채워주시기 바랍니다 감사합니다!");
	        message.setFrom("psjyyb3418@gmail.com");

	        mailSender.send(message);
	    }

	public int processPayment(ContractVO contractVO) {
		System.out.println(contractVO);
		String endpoint = "https://api.tosspayments.com/v1/billing/" + contractVO.getBillingkey();

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(secretKey, "");
		headers.add("Content-Type", "application/json");

		Map<String, Object> body = new HashMap<>();
		body.put("customerKey", contractVO.getCustomerkey());
		body.put("amount", contractVO.getMonthPayPrice()); // 결제 금액
		body.put("orderId", contractVO.getOrderId()); // 주문아이디
		body.put("orderName",contractVO.getModuleString()); // 모듈정보
		body.put("customerEmail", contractVO.getCeoEmail()); // 사장이메일, 메일 받을 이메일
		body.put("customerName", contractVO.getCeoName()); // 사장이름
		body.put("taxFreeAmount", 0);
		body.put("taxExemptionAmount", 0);

		String requestBody = "";
		try {
			requestBody = objectMapper.writeValueAsString(body);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("결제 성공: " + response.getBody());
			return 1;
		} else {
			System.out.println("결제 실패: " + response.getBody());
			return 0;
		}
	}
}
//private final String TOSS_API_URL =
//"https://api.tosspayments.com/v1/billing/keys"; private final String
//SECRET_KEY = "test_sk_mBZ1gQ4YVXQ1Oj2OJJvjrl2KPoqN"; // TossPayments 비밀키 (환경변수에서 가져오는 것이 좋습니다)
//
//private final RestTemplate restTemplate; private final ObjectMapper
//objectMapper;
//
//
//private GroupAdminMapper gaMapper;
//private PayMapper payMapper;
//
//@Autowired
//public PayServiceImpl(GroupAdminMapper groupAdminMapper, PayMapper payMapper, RestTemplate restTemplate,
//		ObjectMapper objectMapper) {
//	this.gaMapper = groupAdminMapper;
//	this.payMapper = payMapper;
//	this.restTemplate = restTemplate;
//	this.objectMapper = objectMapper;
//
//}
//	@Override
//	public String requestBillingKey(String comCode) {
//		CompanyVO comInfo = payMapper.selectComInfo(comCode);
//		try {
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", "Basic " + encodeBase64(":" + SECRET_KEY)); // Base64 인코딩
//			headers.set("Content-Type", "application/json");
//
//			String requestBody = "{" + "\"customerEmail\": \"" + comInfo.getCeoEmail() + "\"," + "\"customerName\": \""
//					+ comInfo.getCeoName() + "\"," + "\"method\": \"CARD\"" + "}";
//
//			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//
//			ResponseEntity<String> response = restTemplate.exchange(TOSS_API_URL, HttpMethod.POST, requestEntity,
//					String.class);
//
//			if (response.getStatusCode().is2xxSuccessful()) {
//				JsonNode jsonResponse = objectMapper.readTree(response.getBody());
//				return jsonResponse.get("billingKey").asText();
//			} else {
//				// API의 실패 응답 로깅
//				System.err.println("API response status code: " + response.getStatusCode());
//				System.err.println("API response body: " + response.getBody());
//				throw new RuntimeException("Failed to request billing key: " + response.getStatusCode());
//			}
//		} catch (HttpServerErrorException e) {
//			// 서버 오류 로깅
//			System.err.println("Server error occurred: " + e.getResponseBodyAsString());
//			throw new RuntimeException("Server error occurred while requesting billing key", e);
//		} catch (HttpClientErrorException e) {
//			// 클라이언트 오류 로깅
//			System.err.println("Client error occurred: " + e.getResponseBodyAsString());
//			throw new RuntimeException("Client error occurred while requesting billing key", e);
//		} catch (Exception e) {
//			// 일반 오류 로깅
//			System.err.println("Exception occurred: " + e.getMessage());
//			throw new RuntimeException("Exception occurred while requesting billing key", e);
//		}
//	}
//
//	// Base64 인코딩 메서드
//	private String encodeBase64(String value) {
//		return Base64.getEncoder().encodeToString(value.getBytes());
//	}
//}
//@Transactional
//@Override
//public int contractInsert(ContractVO contractVO) {
//	// LocalDate localDate = contractVO.getFinalDate();
//	// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	// String dateString = localDate.format(formatter); // LocalDate -> String 변환
//	// contractVO.setFinalDates(dateString);
//	// System.out.println(dateString+"asdasdasdsa"); // 출력 예시: 2024-08-12
//	payMapper.insertContract(contractVO);
//	List<String> moduleNames = contractVO.getModuleNames();
//	if (moduleNames != null && !moduleNames.isEmpty()) {
//		// 첫 번째 요소에서 "[" 제거
//		String firstModule = moduleNames.get(0).replace("[", "").trim();
//		moduleNames.set(0, firstModule);
//		// 마지막 요소에서 "]" 제거
//		String lastModule = moduleNames.get(moduleNames.size() - 1).replace("]", "").trim();
//		moduleNames.set(moduleNames.size() - 1, lastModule);
//	}
//	return 0;
//}