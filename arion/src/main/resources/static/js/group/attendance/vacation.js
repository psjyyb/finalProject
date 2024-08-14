//백 입사일 기준으로 년차별 계산  : 년차 시작일,끝일 년차, 총사용일, 남은일수, 총 휴가수(년차로 계산)
//,시작일-끝일(남은 사용기간)
//옵션 클릭시 value(년차) 전송 입사일+년차 에서 입사일+년차+1로 휴가 기안서테이블에서 조회후 휴가일 기록 작성후 전송

function autoReload()
	{
		$.ajax({
			url : '/group/attendance/vacationlist',
			
           type : 'POST',
           data : {
			'employeeno' : $('#employeeno').val(),
              'years' : 0
             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			
		   }
		})
		
		
		}