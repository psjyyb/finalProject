$(document).ready(function() {
	let businessValidated = false; // 사업자등록번호가 유효한지 여부

	$('#businessCheck').click(function(event) {
		event.preventDefault();

		let businessNumber = $('#companyBusinessNumber').val().trim();
		console.log("사업자등록번호 :", businessNumber);

		if (!businessNumber) {
			alert('사업자등록번호를 입력하세요.');
			return;
		}

		if (businessNumber.includes('-')) {
			alert("사업자등록번호에 하이픈('-')을 포함하지 마세요.");
			return;
		}

		if (businessNumber.length !== 10) {
			alert('올바른 사업자등록번호를 입력하세요.');
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
					alert('유효한 사업자등록번호입니다.');
					businessValidated = true; // 유효한 사업자등록번호로 설정
					$('#companyBusinessNumber').data('validated', true); // 데이터 속성 설정
				} else {
					alert('유효하지 않은 사업자등록번호입니다.');
					businessValidated = false; // 유효하지 않은 사업자등록번호로 설정
				}
			},
			error: function(result) {
				console.log(result.responseText);
				alert('사업자등록번호 상태 조회 중 오류가 발생했습니다.');
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

		let isValid = true;
		let errorMessage = '';

		$('input[type="text"], input[type="password"]').each(function() {
			if ($(this).val().trim() === '') {
				isValid = false;
				errorMessage = $(this).attr('name') + '은(는) 필수 값입니다.';
				$(this).focus();
				alert(errorMessage);
				return false;
			}
		});

		if (!isValid) return;

		let password = $('input[name="companyPw"]').val();
		let passwordCheck = $('input[name="companyPwCheck"]').val();
		if (password !== passwordCheck) {
			alert("비밀번호가 일치하지 않습니다.");
			$('input[name="companyPw"]').focus();
			return;
		}
		
		if (!businessValidated) {
			alert('사업자등록번호를 인증해 주세요.');
			$('#companyBusinessNumber').focus();
			return;
		}

		let address = $('input[name="companyAddress"]').val();
		let addressDetail = $('input[name="companyAddressDetail"]').val();
		$('input[name="companyAddress"]').val(address + ' ' + addressDetail);

		this.submit();
	});
});