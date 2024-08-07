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
       /* e.preventDefault();*/

        let qnaTitle = $('input[name="qnaTitle"]');
        if (qnaTitle.val().trim() === '') {
            alert("제목은 필수 값 입니다.");
            qnaTitle.focus();
            return;
        }

        let qnaWriter = $('input[name="qnaWriter"]');
        if (qnaWriter.val().trim() === '') {
            alert("작성자는 필수 값 입니다.");
            qnaWriter.focus();
            return;
        }

        const formData = new FormData(this);
        $.each(fileList, function(index, file) {
            formData.append('files', file);
        });

      /*  $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                window.location.href = '/home/qna';
            },
            error: function(response) {
                alert('파일 업로드 중 오류가 발생했습니다.');
            }
        });*/
        return true;
    });
});
