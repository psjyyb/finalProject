package com.arion.app.admin.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AdminVO {
	// 계약 회사에 관한.
	private String companyCode;  // 회사코드
	private String companyName;  // 회사명 
	private String ceoName;      // 대표자 이름
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 파라미터 자동변환
	private Date startDate;     // 계약시작일
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 파라미터 자동변환
	private Date finalDate;     // 계약종료일
	private int usersCnt;       // 사용인원수
	private int contractNo;     // 계약서번호
	private int monthPayPrice;  // 매달 결제금액
	private int totalPayPrice;  // 총결제금액
	private String contractState; // 계약상태
	private String companyTel;  // 회사전화번호
	private Long companyBusinessNumber; // 사업자 번호
	private String companyType; // 업종
	private String companyPost; // 회사우편번호 
	private String ceoEmail;    // 사장 이메일
	private String companyAddress; // 회사주소
	private String moduleNames;
	private String contractSign; // 계약서싸인
	
	
}
