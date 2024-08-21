  var context=null;

function autoReload()
	{     
				var tb = document.getElementById("datatable"); 
		         row = tb.insertRow();
		         cell = row.insertCell();
		         cell.innerHTML = "최상단";
		         cell.id= 'top';
		         cell.className='forder';
		         cell.addEventListener('contextmenu', contextmenuClick)
		    
		$.ajax({
			url : '/group/database/start',
			
           type : 'POST',
           data : {
			'companycode' : $('#companycode').val()
             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			 			var startforder = data.startforder;
			 			console.log(startforder);
			 			
			 			$.each(startforder, function(key, value) {
						row = tb.insertRow();
		                cell = row.insertCell();
		                cell.innerHTML ="&nbsp;&nbsp;ㄴ"+value.filename;
		                cell.id= value.me;
		                cell.className='forder';	
						cell.addEventListener('contextmenu', contextmenuClick);
		                cell.addEventListener('click', folderClick)
		     })							
			 
		   }
		})		
	 }
	 	 
	 	
	 	  
	  function contextmenuClick(event) {
				context= $(this).attr('id');
			console.log(context);
                event.preventDefault(); // 기본 컨텍스트 메뉴 차단
               
               var menu = $('#popMenu');
                var x = event.pageX;
                var y = event.pageY;

                 menu.css({
					position: 'absolute',
                    display: 'block',
                    left: x + 'px',
                    top: y+ 'px'
                });
            

            // 다른 곳을 클릭 시 컨텍스트 메뉴 숨기기
            $(document).on('click', function() {
                $('#popMenu').hide();
            });
                  
            // 컨텍스트 메뉴 항목 클릭 시 동작 정의
	 }
            $('#popMenu p').on('click', function(event) {
              
                
                if($(this).attr('id')==='mforder'){
					console.log( '폴더선택');
					
					$('#fsuperforder').val(""+context);
				}
				
                else if($(this).attr('id')==='mupload'){
				console.log('업로드선택');	
				
				$('#usuperforder').val(""+context);
				
				}
				
                $('#popMenu').hide(); // 클릭 후 메뉴 숨기기
               });
	 
	 function folderClick(event) {
				console.log('폴더클릭');
				console.log($(this).attr('id'));
                
           	
          
	 }