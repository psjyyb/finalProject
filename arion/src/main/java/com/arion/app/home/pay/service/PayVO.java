package com.arion.app.home.pay.service;

import lombok.Data;

@Data
public class PayVO {
	private String accountAmount; // 인원합계금액
	private String moduleAmount; // 모듈 합계금액
	private String monthlyAmount; // 월합계 금액
	private String totlaAmount; // 합계금액
}
