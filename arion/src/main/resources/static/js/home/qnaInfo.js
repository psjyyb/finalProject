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
    $('#passwordModal').data('action', action).data('qnaNo', qnaNo).show();
}

function closePasswordModal() {
    $('#passwordModal').hide();
}

$('#passwordConfirmBtn').click(function() {
    var action = $('#passwordModal').data('action');
    var qnaNo = $('#passwordModal').data('qnaNo');
    var enteredPassword = $('#password').val();

    $.ajax({
        url: '/qnaPw',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            qnaNo: qnaNo,
            password: enteredPassword
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
    });
});