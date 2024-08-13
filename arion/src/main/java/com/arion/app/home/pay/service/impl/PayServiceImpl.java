package com.arion.app.home.pay.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.arion.app.group.admin.mapper.GroupAdminMapper;
import com.arion.app.home.pay.mapper.PayMapper;
import com.arion.app.home.pay.service.ContractVO;
import com.arion.app.home.pay.service.PayService;
import com.arion.app.home.pay.service.PayVO;
import com.arion.app.security.service.CompanyVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PayServiceImpl implements PayService {

	private GroupAdminMapper gaMapper;
	private PayMapper payMapper;

	@Autowired
	public PayServiceImpl(GroupAdminMapper groupAdminMapper, PayMapper payMapper) {
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
		System.out.println(fdate+"날짜 형식이 어떻게 나오는지 확인");
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
	@Transactional
	@Override
	 public int payEnd(ContractVO contractVO) {
		System.out.println(contractVO+"최종적으로 데이터가 잘 받아지는지 확인 해보자.");
        return 0;
    }
	
	@Override
	public int payInsert(ContractVO contractVO) {
		return 0;
	}
	@Override
	public int payDetailInsert(ContractVO contractVO) {
		return 0;
	}
	@Override
	public int contractInsert(ContractVO contractVO) {	
		return 0;
	}
	@Override
	public int useModuleInsert(ContractVO contractVO) {
		return 0;
	}
	@Override
	public int comRespUpdate(ContractVO contractVO) {
		return 0;
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