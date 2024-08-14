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
		
		var tbcal = document.getElementById("worktimetable"); 
		
		while (tbcal.rows.length > 1) 
		{
			tbcal.deleteRow(tbcal.rows.length - 1);
		}
		
		//한달 기록 출력
		let endday = new Date(today.getFullYear(), today.getMonth()+1, 0);
		let month=today.getMonth()+1;
		var startdate=today.getFullYear()+'-'+month+'-1';
		console.log('startdate:'+startdate);
		
		var enddate=today.getFullYear()+'-'+month+'-'+endday.getDate();
		console.log('enddate:'+enddate);
		
		 $.ajax({
			url : '/group/attendance/worklist',
			
           type : 'POST',
           data : {
			'employeeno' : $('#employeeno').val(),
              'startdate' : startdate,
              'enddate': enddate
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			//근무시간기록
			var worktimelist = data.worktimelist;
			var sumworktime = data.sumworktime;
			console.log(worktimelist);
			console.log('성공');
			$.each(worktimelist, function(key, value) {
				var row = null;
		row = tbcal.insertRow();
		
		$.each(value, function(key, value) {
		cell = row.insertCell();
		console.log(key+'/'+value);
		if(key=='attdate')
		{
			console.log("날짜");
		    let date =  new Date(value);
			
			cell.innerHTML = date.getFullYear()+'/'+(date.getMonth()+1)+'/'+date.getDate();
		}
		else
		{cell.innerHTML = value;}
		
			
		          })
		     })
		     //근무시간합  
		      var row = null;
		      row = tbcal.insertRow();
		     cell = row.insertCell();
		       cell.innerHTML="총합";
		   cell = row.insertCell();
		       cell.innerHTML=sumworktime.sumworktimehour;
		        cell = row.insertCell();
		       cell.innerHTML=sumworktime.sumworktimeminute;
		   cell = row.insertCell();
		       cell.innerHTML="";
		   cell = row.insertCell();
		       cell.innerHTML="";
		 cell = row.insertCell();
		       cell.innerHTML=sumworktime.sumintervalhour;
		        cell = row.insertCell();
		       cell.innerHTML=sumworktime.sumintervalminute;
		   
		   
		   }
           
       });
           		
		
	}
	
//엑셀
document.getElementById("download").addEventListener('click',download);
	
	function download(){
		let endday = new Date(today.getFullYear(), today.getMonth()+1, 0);
		let month=today.getMonth()+1;
		var startdate=today.getFullYear()+'-'+month+'-1';
	    var enddate=today.getFullYear()+'-'+month+'-'+endday.getDate();
		 
		 var employeeno =  $('#employeeno').val();
		 console.log(employeeno);
		
          //엑셀 다운로드
           $.ajax({
			url : '/files/workdownload',
           type : 'POST',
           data : {
			   'employeeno' :$('#employeeno').val(),
              'startdate' : startdate,
              'enddate': enddate
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

	
	
	
	
	
	
	