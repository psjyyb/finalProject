$(document).ready(function() {
    let fileList = [];

    $('#files').on('change', function(e) {
        const fileListTable = $('#fileListTable tbody');

        $.each(e.target.files, function(index, file) {
            fileList.push(file);
            const newRow = $('<tr>');
            newRow.append($('<td>').text(file.name));
            const removeButton = $('<td>').append($('<span class="remove-file">X</span>').on('click', function() {
                const rowIndex = $(this).closest('tr').index();
                fileList.splice(rowIndex, 1);
                $(this).closest('tr').remove();
            }));
            newRow.append(removeButton);
            fileListTable.append(newRow);
        });

        $('#files').val('');
    });

    $('#qnaInsert').on('submit', function(e) {
       e.preventDefault();

        let qnaTitle = $('input[name="qnaTitle"]');
        if (qnaTitle.val().trim() === '') {
				Swal.fire({
					icon: "error",
					text: "제목을 입력해 주세요.",
					didClose: () => {
						$('.qnaTitle').focus();	
					}
				});
            return;
        }

        let qnaWriter = $('input[name="qnaWriter"]');
        if (qnaWriter.val().trim() === '') {
            	Swal.fire({
					icon: "error",
					text: "작성자를 입력해 주세요.",
					didClose: () => {
						$('.qnaWriter').focus();	
					}
				});
            return;
        }

		let isLoggedIn = $('input[name="isLoggedIn"]').val() === 'true';

        if (!isLoggedIn) {
            let password = $('input[name="qnaPw"]');
            if (password.val().trim() === '') {
                	Swal.fire({
						icon: "error",
						text: "제목을 입력해 주세요.",
						didClose: () => {
							$('.qnaPw').focus();	
						}
					});
                return;
            }
        }
        
        let qnaContent = $('textarea[name="qnaContent"]');
        if (qnaContent.val().trim() === '') {
            	Swal.fire({
					icon: "error",
					text: "내용을 입력해 주세요.",
					didClose: () => {
						$('.qnaContent').focus();	
					}
				});
            return;
        }

        const formData = new FormData(document.qnaInsert);
        $.each(fileList, function(index, file) {
            formData.append('files', file);
        });
       $.ajax({
            url: '/home/qnaInsert',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
        })
        .done(result => {
			Swal.fire({
				icon: "success",
				text: "등록되었습니다.",
				timer: 3000,
				willClose: () => {
                    window.location.href = '/home/qna';
                }
			});
		})
		.fail(err => {
			Swal.fire({
				icon: "error",
				text: "등록 중 오류가 발생했습니다."
			});
		})
		
      /* return true;*/
	});
  
});
