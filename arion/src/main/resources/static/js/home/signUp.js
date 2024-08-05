$(document).ready(function() {
    $('#businessCheck').click(function(event) {
        event.preventDefault(); 

        let businessNumber = $('#companyBusinessNumber').val();
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
            "b_no": [businessNumber] // 사업자번호 "xxxxxxx" 로 조회 시,
        };

        console.log(data); 

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=k7RfROiNLFEId95154daEWIESV9T6ldVFAlihqXIr4/gfNxX5FVQA5TsFzftl4qcaFCQdbkEa5vPqSNPBAvjJw==",  // serviceKey 값을 xxxxxx에 입력
            type: "POST",
            data: JSON.stringify(data), // json 을 string으로 변환하여 전송
            dataType: "JSON",
            contentType: "application/json",
            accept: "application/json",
            success: function(result) {
                console.log(result);
                if (result.data[0].b_stt_cd === '01') {
                    alert('유효한 사업자등록번호입니다.');
                } else {
                    alert('유효하지 않은 사업자등록번호입니다.');
                }
            },
            error: function(result) {
                console.log(result.responseText); //responseText의 에러메세지 확인
                alert('사업자등록번호 상태 조회 중 오류가 발생했습니다.');
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
                document.querySelector('input[name=companyPost]').value = data.zonecode;
                document.querySelector('input[name=companyAddress]').value = addr;
                document.querySelector('input[name=companyAddressDetail]').focus();
            }
        }).open();
    });
});