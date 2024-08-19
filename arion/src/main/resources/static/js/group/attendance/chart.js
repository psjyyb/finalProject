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
		
		
		
		
		
		

var chart = bb.generate({
    bindto: "#chart",
    data: {
   
        x: "x",
        columns: [
			['x', "2024-01-01", "2024-01-02", "2024-01-03", "2024-01-04", "2024-01-05", "2024-01-06"],
				["근무시간", 320, 580, 450, 590, 570, 500]
        ],
        type: "bar"
            
            
    },
            axis: {
                x: {
                    type: "timeseries",
                    tick: {
                        format: "%Y-%m-%d"  // 날짜 포맷 설정
                    }
                }
            },         
            grid: {
                y: {
                    lines: [
                          {value: 540, text: '표준근무시간'}
                    ]
                }
            }
});
}