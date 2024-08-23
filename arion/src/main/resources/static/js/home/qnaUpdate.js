$(document).ready(function() {
    let fileList = [];

    $('#files').on('change', function(e) {
        const fileListTable = $('#fileListTable tbody');

        $.each(e.target.files, function(index, file) {
            fileList.push(file);
            const newRow = $('<tr>');
            newRow.append($('<td>').text(file.name));
            const removeButton = $('<td>').append($('<span class="remove-file">x</span>').on('click', function() {
                const rowIndex = $(this).closest('tr').index();
                fileList.splice(rowIndex, 1);
                $(this).closest('tr').remove();
            }));
            newRow.append(removeButton);
            fileListTable.append(newRow);
        });

        $('#files').val('');
    });

    $('#qnaUpdate').on('submit', function(e) {
       e.preventDefault();

        const formData = new FormData(this);
        $.each(fileList, function(index, file) {
            formData.append('files', file);
        });
       $.ajax({
            url: '/home/qnaUpdate',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
        })
        .done(result => {
            Swal.fire({
                icon: "success",
                text: "수정되었습니다."
            }).then(() => {
                window.location.href = '/home/qna';
            });
        })
        .fail(err => {
            Swal.fire({
                icon: "error",
                text: "수정 중 오류가 발생했습니다."
            });
        });
    });
});
