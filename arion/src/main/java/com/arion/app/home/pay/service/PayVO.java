package com.arion.app.home.pay.service;

import lombok.Data;

@Data
public class PayVO {
	private String accountAmount; // 인원합계금액
	private String moduleAmount; // 모듈 합계금액
	private String monthlyAmount; // 월합계 금액
	private String totalAmount; // 합계금액
	private String accountNumber; // 사용인원수
	private String subscriptionPeriod; // 구독기간
}
