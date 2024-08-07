$(document).ready(function() {
	let businessValidated = false; 

	$('#businessCheck').click(function(event) {
		event.preventDefault();

		let businessNumber = $('#companyBusinessNumber').val().trim();
		console.log("사업자등록번호 :", businessNumber);

		if (!businessNumber) {
			Swal.fire({
				icon: "error",
				text: "사업자 등록번호를 입력하세요",
				didClose: () => {
					$('#companyBusinessNumber').focus();
				}
			});
			return;
		}

		if (businessNumber.includes('-')) {
			Swal.fire({
				icon: "error",
				text: "사업자등록번호에 하이픈('-')을 포함하지 마세요.",
				didClose: () => {
					$('#companyBusinessNumber').focus();
				}
			});
			return;
		}

		if (businessNumber.length !== 10) {
			Swal.fire({
				icon: "error",
				text: "올바른 사업자등록번호를 입력하세요.",
				didClose: () => {
					$('#companyBusinessNumber').focus();
				}
			});
			return;
		}

		var data = {
			"b_no": [businessNumber]
		};

		console.log(data);

		$.ajax({
			url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=k7RfROiNLFEId95154daEWIESV9T6ldVFAlihqXIr4/gfNxX5FVQA5TsFzftl4qcaFCQdbkEa5vPqSNPBAvjJw==",
			type: "POST",
			data: JSON.stringify(data),
			dataType: "JSON",
			contentType: "application/json",
			accept: "application/json",
			success: function(result) {
				console.log(result);
				if (result.data[0].b_stt_cd === '01') {
					Swal.fire({
						icon: "success",
						title: "유효한 사업자등록번호입니다.",
						showConfirmButton: false,
						timer: 2000
					});
					businessValidated = true; // 유효한 사업자등록번호로 설정
					$('#companyBusinessNumber').data('validated', true); // 데이터 속성 설정
				} else {
					Swal.fire({
						icon: "error",
						title: "유효하지 않은 사업자등록번호입니다."
					});
					businessValidated = false; // 유효하지 않은 사업자등록번호로 설정
				}
			},
			error: function(result) {
				console.log(result.responseText);
				Swal.fire({
					icon: "error",
					title: "사업자등록번호 상태 조회 중 오류가 발생했습니다."
				});
				businessValidated = false; // 오류가 발생했을 때도 유효하지 않음으로 설정
			}
		});
	});

	$('#postcodeSearch').click(function(event) {
		event.preventDefault();

		new daum.Postcode({
			oncomplete: function(data) {
				var addr = '';

				if (data.userSelectedType === 'R') {
					addr = data.roadAddress;
				} else {
					addr = data.jibunAddress;
				}
				$('input[name=companyPost]').val(data.zonecode);
				$('input[name=companyAddress]').val(addr);
				$('input[name=companyAddressDetail]').focus();
			}
		}).open();
	});

	$('#signUpForm').on('submit', function(e) {
		e.preventDefault();
	
		let companyName = $('input[name="companyName"]');
		if(companyName.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "회사명을 입력하세요.",
				didClose: () => {
					companyName.focus();
				}
			});
			return;
		}
		
		let companyTel = $('input[name="companyTel"]');
		if(companyTel.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "회사 전화번호를 입력하세요.",
				didClose: () => {
					companyTel.focus();
				}
			});
			return;
		}
		if (companyTel.val().trim().includes('-')) {
			Swal.fire({
				icon: "error",
				text: "전화번호에 하이픈('-')을 포함하지 마세요.",
				didClose: () => {
					companyTel.focus();
				}
			});
			return;
		}
		if (companyTel.val().trim().length < 9 || companyTel.val().trim().length > 11) {
			Swal.fire({
				icon: "error",
				text: "올바른 전화번호를 입력하세요.",
				didClose: () => {
					companyTel.focus();
				}
			});
			return;
		}
		
		let companyType = $('input[name="companyType"]');
		if(companyType.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "업종을 입력하세요.",
				didClose: () => {
					companyType.focus();
				}
			});
			return;
		}
		
		let companyPost = $('input[name="companyPost"]');
		if(companyPost.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "우편번호 찾기를 이용하세요.",
				didClose: () => {
					companyPost.focus();
				}
			});
			return;
		}
		
		let companyId = $('input[name="companyId"]');
		if(companyId.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "ID를 입력하세요.",
				didClose: () => {
					companyId.focus();
				}
			});
			return;
		}
		
		let password = $('input[name="companyPw"]').val();
		let passwordCheck = $('input[name="companyPwCheck"]').val();
		
		if(password.trim() === '') {
			Swal.fire({
				icon: "error",
				text: "PW를 입력하세요.",
				didClose: () => {
					$('input[name="companyPw"]').focus();
				}
			});
			return;
		}
		
		if (password !== passwordCheck) {
			Swal.fire({
				icon: "error",
				text: "비밀번호가 일치하지 않습니다.",
				didClose: () => {
					$('input[name="companyPw"]').focus();
				}
			});
			return;
		}
		
		let ceoName = $('input[name="ceoName"]');
		if(ceoName.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "대표자 이름을 입력하세요.",
				didClose: () => {
					ceoName.focus();
				}
			});
			return;
		}
		
		let ceoPhone = $('input[name="ceoPhone"]');
		if(ceoPhone.val().trim() === ''){
			Swal.fire({
				icon: "error",
				text: "대표자 전화번호를 입력하세요.",
				didClose: () => {
					ceoPhone.focus();
				}
			});
			return;
		}
		if(ceoPhone.val().trim().includes('-')){
			Swal.fire({
				icon: "error",
				text: "전화번호에 하이픈('-')을 포함하지 마세요.",
				didClose: () => {
					ceoPhone.focus();
				}
			});
			return;
		}
		if (ceoPhone.val().trim().length < 9 || ceoPhone.val().trim().length > 11) {
			Swal.fire({
				icon: "error",
				text: "올바른 전화번호를 입력하세요.",
				didClose: () => {
					ceoPhone.focus();
				}
			});
			return;
		}
		
		let ceoEmail = $('input[name="ceoEmail"]');
		if(!ceoEmail.val().trim().includes('@')){
			Swal.fire({
				icon: "error",
				text: "이메일에 ('@')를 포함하여 작성해주세요.",
				didClose: () => {
					ceoEmail.focus();
				}
			});
			return;
		}
		
		
		if (!businessValidated) {
			Swal.fire({
				icon: "error",
				text: "사업자등록번호를 인증해주세요.",
				didClose: () => {
					$('#companyBusinessNumber').focus();
				}
			});
			return;
		}

		let address = $('input[name="companyAddress"]').val();
		let addressDetail = $('input[name="companyAddressDetail"]').val();
		$('input[name="companyAddress"]').val(address + ' ' + addressDetail);

		this.submit();
	});
});
