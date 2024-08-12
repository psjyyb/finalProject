package com.arion.app.home.pay.service;

import java.util.List;

import lombok.Data;

@Data
public class PayVO {
	private String accountAmount; // 인원합계금액
	private String moduleAmount; // 모듈 합계금액
	private String monthlyAmount; // 월합계 금액
	private String totalAmount; // 합계금액
	private String accountNumber; // 사용인원수
	private String subscriptionPeriod; // 구독기간
	private String regularPaymentDate; // 정기결제일
	private String firstMonthAmount; // 첫달 결제일
	
	// 모듈정보담기.
	private List<String> moduleNames;  
	private List<String> moduleNos;
}
