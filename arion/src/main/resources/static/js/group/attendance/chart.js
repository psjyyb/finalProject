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
		
		
		//한달 기록 출력
		let endday = new Date(today.getFullYear(), today.getMonth()+1, 0);
		let month=today.getMonth()+1;
		var startdate=today.getFullYear()+'-'+month+'-1';
		console.log('startdate:'+startdate);
		
		var enddate=today.getFullYear()+'-'+month+'-'+endday.getDate();
		console.log('enddate:'+enddate);
		
		
		var datax= ['x'];
		
		var datay= ['근무시간(분)'];
		
		
		 $.ajax({
			url : '/group/attendance/chartlist',
			
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
		   $.each(worktimelist, function(key, value) {
			
			
			$.each(value, function(key, value) {
		
		if(key=='attdate')
		{
			
		    let date =  new Date(value);						
			datax.push(''+ date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+'');
		}
		if(key=='worktimeminute')
		{
			console.log(value);
			
			
			
			datay.push(value);
			
		}
					
		          })			
			
		   })
		   
		   }
           
       });
		
		
var chart = bb.generate({
    bindto: "#chart",
    data: {
   
        x: "x",
        columns: [
			datax,
			datay
        ],
        type: "bar"
            
            
    },
            axis: {
                x: {
                    type: "timeseries",
                    tick: {
                        format: "%Y-%m-%d"  // 날짜 포맷 설정
                    }
                    
                   },y: {
            
            max: 800  // Y축의 최대값 설정
        }
                   
                   
                   
            },         
            grid: {
                y: {
                    lines: [
                          {value: 540, text: '표준근무시간(540)'}
                    ]
                }
            },
    bar: {
        width: {
            ratio: 0.1 // 바 너비의 비율 (0~1 범위)
        }
    }
           
});
}



