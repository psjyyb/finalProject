    var today = new Date(); //오늘 날짜        
	var date = new Date();
	
	document.getElementById("before").addEventListener('click',beforem);
	//이전달
	function beforem() //이전 달을 today에 값을 저장
	{ 
		today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
		autoReload(); //만들기
	}
	
	document.getElementById("next").addEventListener('click',nextm);
	//다음달
	function nextm()  //다음 달을 today에 저장
	{
		today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
		autoReload();
	}
	
	document.getElementById("today").addEventListener('click',thisMonth);
	//오늘선택
	
	function thisMonth(){
		today = new Date();
		autoReload();
	}

	function autoReload()
	{
		var nMonth = new Date(today.getFullYear(), today.getMonth(), 1); //현재달의 첫째 날
		var lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0); //현재 달의 마지막 날
		var tbcal = document.getElementById("calendar"); // 테이블 달력을 만들 테이블
		var yearmonth = document.getElementById("yearmonth"); //  년도와 월 출력할곳
		yearmonth.innerHTML = today.getFullYear() + "년 "+ (today.getMonth() + 1) + "월"; //년도와 월 출력

		if(today.getMonth()+1==12) //  눌렀을 때 월이 넘어가는 곳
		{
			before.innerHTML=("<"+today.getMonth())+"월";
			next.innerHTML="1월"+">";
			
		}
		else if(today.getMonth()+1==1) //  1월 일 때
		{
			before.innerHTML="<"+"12월";
			next.innerHTML=(today.getMonth()+2)+"월" +">";
		}
		else //   12월 일 때
		{
			before.innerHTML="<"+(today.getMonth())+"월";
			next.innerHTML=(today.getMonth()+2)+"월"+">";
		}


		// 남은 테이블 줄 삭제
		while (tbcal.rows.length > 2) 
		{
			tbcal.deleteRow(tbcal.rows.length - 1);
		}
		var row = null;
		row = tbcal.insertRow();
		var cnt = 0;
		var dayCheck = (nMonth.getDay()==0) ? 7 : nMonth.getDay(); //일요일을 마지막으로 넣기 위해서.

		 // 1일 시작칸 찾기
		for (i = 0; i < (dayCheck-1); i++) 
		{
			cnt = cnt + 1;	//요일값
			cell = row.insertCell();
			
			if (i>4) { //주말
				cell.style.backgroundColor = "#f7f7f7";
			}
		}


		// 달력 출력
		for (i = 1; i <= lastDate.getDate(); i++) // 1일부터 마지막 일까지
		{ 
			cell = row.insertCell();
			
			var str="";
			
			str += "<div>"+i+"</div>";
			var day = (i<10) ? "0"+i : i;            	
			str += "<div id='"+day+"'></div>"; //나중에 원하는 날에 일정을 넣기위해 id값을 날자로 설정
			cell.innerHTML = str;
			
			//
			cnt = cnt + 1;
			if (cnt % 7 == 6) {//토요일
				var str="";
				str += "<div>"+i+"</div>";
				var day = (i<10) ? "0"+i : i;            	
				str += "<div id='"+day+"'>";
				str += "</div>";
				cell.innerHTML = str;
				cell.style.color = "#009de0";
				cell.style.backgroundColor = "#f7f7f7";                    
			}
			if (cnt % 7 == 0) { //일요일
				var str="";
				str += "<div>"+i+"</div>";
				var day = (i<10) ? "0"+i : i;            	
				str += "<div id='"+day+"'>";
				str += "</div>";
				cell.innerHTML = str;
				row = calendar.insertRow();// 줄 추가
				cell.style.color = "#ed5353";
				cell.style.backgroundColor = "#f7f7f7";
			}
			
			//마지막 날짜가 지나면 일요일까지 칸 그리기
			if(lastDate.getDate() == i && ((cnt % 7) != 0)){
				var add = 7 - (cnt % 7);
				for(var k = 1; k <= add; k++){
					cell = row.insertCell();
					cnt = cnt + 1;
					if (cnt % 7 == 6) {//토요일
						cell.style.backgroundColor = "#f7f7f7";
					}
					if (cnt % 7 == 0) { //일요일
						cell.style.backgroundColor = "#f7f7f7";
					}
				}
			}
			
			//오늘날짜배경색
			if( today.getFullYear() == date.getFullYear() && today.getMonth() == date.getMonth() && i==date.getDate() )
			{
				cell.style.backgroundColor = "#e2f3da"; //오늘날짜배경색
			}
			
			//마지막 날짜가 지나면 일요일까지 칸 그리기
			if(lastDate.getDate() == i && ((cnt % 7) != 0)){
				var add = 7 - (cnt % 7);
				for(var k = 1; k <= add; k++){
					cell = row.insertCell();
					cnt = cnt + 1;
					if (cnt % 7 == 6) {//토요일
						cell.style.backgroundColor = "#f7f7f7";
					}
					if (cnt % 7 == 0) { //일요일
						cell.style.backgroundColor = "#f7f7f7";
					}
				}
			}
			  
		}
		
        
        let endday = new Date(today.getFullYear(), today.getMonth()+1, 0);
        
        let month=today.getMonth()+1;
		var startdate=today.getFullYear()+'-'+month+'-1';
		console.log('startdate:'+startdate);
		
		var enddate=today.getFullYear()+'-'+month+'-'+endday.getDate();
		console.log('enddate:'+enddate);
		
		 $.ajax({
			url : '/attendancelist',
			
           type : 'POST',
           data : {
              'startdate' : startdate,
              'enddate': enddate
              },
		 dataType:"json",
           success : function(data) {
			
			var attendancelist = data.attendancelist;
			
			console.log(attendancelist);
			$.each(attendancelist, function(key, value) {
				$('#'+ value.attdate).html("출근"+value.starttime+"<br>퇴근"+value.endtime+"<br>"+value.state);
				
			})
		   }
           });
			
}



document.getElementById("download").addEventListener('click',download);
	
	
	
	
	function download(){
		let endday = new Date(today.getFullYear(), today.getMonth()+1, 0);
		let month=today.getMonth()+1;
		var startdate=today.getFullYear()+'-'+month+'-1';
	    var enddate=today.getFullYear()+'-'+month+'-'+endday.getDate();
		 
		 var employeeno =  $('#employeeno').val();
		 console.log(employeeno);
		
          
           $.ajax({
			url : '/files/attendancedownload',
           type : 'POST',
           data : {
			   'employeeno' :employeeno,
              'startdate' : startdate,
              'enddate': enddate
              },                     
            
            xhrFields: { 

                responseType: 'blob'
            },

            success: function(blob) { 

                var link = document.createElement('a'); 
    
                link.href = window.URL.createObjectURL(blob); 
           
                link.download = "boardList_data.xlsx"; 

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


