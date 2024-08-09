$(document).ready(function() {
    $('#updateButton').click(function() {
        showPasswordModal(this, 'update');
    });

    $('#deleteButton').click(function() {
        showPasswordModal(this, 'delete');
    });
});

function showPasswordModal(element, action) {
    var qnaNo = $(element).data('qna-no');
    var qnaPw = $(element).data('qna-pw');
    $('#passwordModal').data('action', action).data('qnaNo', qnaNo).data('qnaPw', qnaPw).show();
}

function closePasswordModal() {
    $('#passwordModal').hide();
}

$('#passwordConfirmBtn').click(function() {
    var action = $('#passwordModal').data('action');
    var qnaNo = $('#passwordModal').data('qnaNo');
    var storedPassword = $('#passwordModal').data('qnaPw');
    var enteredPassword = $('#password').val();

    if (enteredPassword === storedPassword) {
        if (action === 'update') {
            location.href = '/home/qnaUpdate?qnaNo=' + qnaNo;
        } else if (action === 'delete') {
            location.href = '/home/qnaDelete?qnaNo=' + qnaNo;
        }
    } else {
        alert('비밀번호가 틀렸습니다.');
    }

   /* $.ajax({
        url: '/qnaPw',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            qnaNo: qnaNo,
            password: password
        }),
        success: function(data) {
            if (data.success) {
                if (action === 'update') {
                    location.href = '/home/qnaUpdate?qnaNo=' + qnaNo;
                } else if (action === 'delete') {
                    location.href = '/home/qnaDelete?qnaNo=' + qnaNo;
                }
            } else {
                Swal.fire({
					icon: "error",
					text: "비밀번호가 틀렸습니다."
				});
            }
        }
    });*/
});