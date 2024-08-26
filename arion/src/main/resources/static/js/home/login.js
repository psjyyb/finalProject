$(document).ready(function() {
    if (localStorage.getItem("companyCode")) {
        $('input[name="companyCode"]').val(localStorage.getItem("companyCode"));
    }
    if (localStorage.getItem("id")) {
        $('input[name="id"]').val(localStorage.getItem("id"));
    }
    if (localStorage.getItem("remember") === "true") {
        $('input[name="remember"]').prop('checked', true);
    }

    $('#loginForm').on('submit', function() {
        let loginType = $('input[name="loginType"]:checked').val();
        let companyCode = $('input[name="companyCode"]').val();
        let userId = $('input[name="id"]').val();
        let password = $('input[name="password"]').val();
        let idField = $('input[name="username"]');
        idField.val(loginType + ":" + companyCode + ":" + userId + ":" + password);

        if ($('input[name="remember"]').is(':checked')) {
			localStorage.setItem("loginType", loginType);
            localStorage.setItem("companyCode", companyCode);
            localStorage.setItem("id", userId);
            localStorage.setItem("remember", true);
        } else {
			localStorage.removeItem("loginType", loginType);
            localStorage.removeItem("companyCode");
            localStorage.removeItem("id");
            localStorage.setItem("remember", false);
        }
    });

    // 모달창 처리
    $("#findIdModalBtn").on("click", function() {
		$("#ceoName").val('');
		$("#businessNumber").val('');
        $("#findIdModal").show();
    });

    $("#findPwModalBtn").on("click", function() {
		$("#companyCode").val('');
		$("#companyId").val('');
        $("#findPwModal").show();
    });

    $(".close").on("click", function() {
        $(this).closest(".modal").hide();
    });

    $(window).on("click", function(event) {
        if ($(event.target).hasClass("modal")) {
            $(event.target).hide();
        }
    });

    // 회사코드/아이디 찾기 처리
    $("#findIdBtn").on("click", function() {
        let ceoName = $("#ceoName").val();
        let businessNumber = $("#businessNumber").val();

        $.ajax({
            type: "POST",
            url: "/home/findId",
            data: {
                ceoName: ceoName,
                companyBusinessNumber: businessNumber
            }
        }).done(function(response) {
            Swal.fire({
                title: 'Success',
                text: response.message,
                icon: 'success',
                timer: 3000,
                confirmButtonText: 'OK'
            });
            $("#findIdModal").hide();
        }).fail(function() {
            Swal.fire({
                title: 'Error',
                text: '회사코드/아이디 찾기에 실패했습니다.',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        });
    });

    // 비밀번호 찾기 처리
    $("#findPwBtn").on("click", function() {
        let companyCode = $("#companyCode").val();
        let companyId = $("#companyId").val();

        $.ajax({
            type: "POST",
            url: "/home/resetPassword",
            data: {
                companyCode: companyCode,
                companyId: companyId
            }
        }).done(function(response) {
            Swal.fire({
                title: 'Success',
                text: response.message,
                icon: 'success',
                confirmButtonText: 'OK'
            });
            $("#findPwModal").hide();
        }).fail(function() {
            Swal.fire({
                title: 'Error',
                text: '비밀번호 찾기에 실패했습니다.',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        });
    });
});
