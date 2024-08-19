
document.getElementById('years').addEventListener('change', function(event) {
            var selectedValue = event.target.value;
            console.log('선택년수:', selectedValue);
            autoReload()
        });

function autoReload()
	{      
		var tbcal = document.getElementById("vacationtable"); 
				
		while (tbcal.rows.length > 1) 
		{
			tbcal.deleteRow(tbcal.rows.length - 1);
		}		
				
		$.ajax({
			url : '/group/attendance/vacationlist',
			
           type : 'POST',
           data : {
			'employeeno' : $('#employeeno').val(),
              'years' : $("select[name=years] option:selected").val()
             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			 var vacationlist = data.vacationlist;
			 var empvacation = data.empvacation;
			 
			 console.log(vacationlist);
			 console.log(empvacation);
			 $.each(vacationlist, function(key, value) {
				
				var row = null;
		         row = tbcal.insertRow();
				
				$.each(value, function(key, value) {
					cell = row.insertCell();
					cell.innerHTML = value;
					
					})
				
				
			})
			
			console.log(empvacation.used);				
				
			$('#used').text(empvacation.used);
			$('#remaining').text (empvacation.remaining);
			$('#vacation').text(empvacation.vacation);
			$('#expirationdate').text(empvacation.expirationdate);											
			 
		   }
		})
				
}

//엑셀
document.getElementById("download").addEventListener('click',download);
	
	function download(){
		
		
          //엑셀 다운로드
           $.ajax({
			url : '/files/vacationdownload',
           type : 'POST',
           data : {
			  'employeeno' : $('#employeeno').val(),
              'years' : $("select[name=years] option:selected").val()
              },                     
            
            xhrFields: { 

                responseType: 'blob'
            },

            success: function(blob) { 

                var link = document.createElement('a'); 
    
                link.href = window.URL.createObjectURL(blob); 
           
                link.download = "사번:"+ $('#employeeno').val()+".xlsx"; 

                link.click(); 
       
                alert('엑셀 다운로드에 성공했습니다'); // 성공 메시지를 출력합니다.
            },
            error: function(xhr, status, error) { // 요청이 실패했을 때 실행할 함수입니다.
                console.error('서버 응답:', xhr); // 서버의 응답을 콘솔에 출력합니다.
                console.error('오류 상태:', status); // 오류 상태를 콘솔에 출력합니다.
                console.error('오류 메시지:', error); // 오류 메시지를 콘솔에 출력합니다.
                alert('엑셀 다운로드에 실패했습니다.'); // 실패 메시지를 출력합니다.
            }
    
           });
           
	}



