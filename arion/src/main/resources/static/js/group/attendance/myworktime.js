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
			
			var worktimelist = data.worktimelist;
			
			console.log(worktimelist);
			console.log('성공');
			$.each(worktimelist, function(key, value) {
				var row = null;
		row = tbcal.insertRow();
		
		$.each(value, function(key, value) {
		cell = row.insertCell();
		console.log(value);
		cell.innerHTML = value;
			
		})
						
			})
		   }
           });
           		
		
	}