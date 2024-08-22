 var context=null;
 

function autoReload()
	{     
		        var dt = document.getElementById("1"); 
		  dt.addEventListener('contextmenu', contextmenuClick);
		    		    
		$.ajax({
			url : '/group/database/start',
			
           type : 'POST',
           data : {
			'companycode' : $('#companycode').val()
             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			 			let startforder = data.startforder;
			 			console.log(startforder);
			 			
			 			$.each(startforder, function(key, value) {
							let newDiv = $('<div></div>').addClass('forder').css({'white-space':'pre'}).attr('id', value.me).val('close').text('ㄴ'+value.filename);
							newDiv.on('contextmenu', contextmenuClick);
							newDiv.on('click', folderClick);
							$('#1').append(newDiv);
								                		          
		     })							
			 
		   }
		})		
	 }
	 	 	 	  
	  function contextmenuClick(event) {
		event.stopPropagation();
		console.log($(this).attr('class'));
		if($(this).attr('class')=='forder'){
				context= $(this).attr('id');
			console.log(context);
                event.preventDefault(); // 기본 컨텍스트 메뉴 차단
               
               let menu = $('#popMenu');
                let x = event.pageX;
                let y = event.pageY;

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
                  
            }
	 }
            $('#popMenu p').on('click', function(event) {
              event.stopPropagation();
                
                if($(this).attr('id')==='mforder'){
					console.log( '폴더선택');
					
					$('#fsuperforder').val(context);
				}
				
                else if($(this).attr('id')==='mupload'){
				console.log('업로드선택');	
				
				$('#usuperforder').val(context);
				
				}
				
                $('#popMenu').hide(); // 클릭 후 메뉴 숨기기
               });
	 
	 function folderClick(event) {
		event.stopPropagation();
				console.log('폴더클릭');
				console.log("누른번호:"+$(this).attr('id'));
				console.log("상태:"+$(this).val());
				
				let clickforder=$(this).attr('id');
				//폴더열람시
				if($(this).val()=='close'){
				console.log('열음');
				$.ajax({
			url : '/group/database/callfile',			
           type : 'POST',
           data : {
			'companycode' : $('#companycode').val()
			,'parent' : $(this).attr('id')
             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			 			let filelist = data.filelist;
			 			console.log(filelist);
			 			
			 			$.each(filelist, function(key, value) {
							console.log("레벨"+value.level);
			 			   let gap=' '.repeat(value.level*3);
			 			   
						   let type='';
						   
			 			   if(value.forder=='Y'){
							console.log('폴더추가');
							type='forder';
						   }
						   
			 			   else if(value.forder=='N'){
							console.log('파일추가');
							type='file';
						   }
			 			   let newDiv;
							if(type=='forder'){
			               newDiv = $('<div></div>').addClass('forder').css({'white-space':'pre'}).attr('id', value.me).val('close').text(gap+"ㄴ"+value.filename);
							newDiv.on('contextmenu', contextmenuClick);
							newDiv.on('click', folderClick);
							}
							else if(type=='file'){
							newDiv = $('<div></div>').addClass('file').css({'white-space':'pre'}).attr('id', value.me).text(gap+"ㄴ"+value.filename);
							newDiv.on('click', fileClick);
							}
							$('#'+clickforder).append(newDiv);
			 			 
			 })
		   }
		})	
		$(this).val('open');
		
		}
		//폴더 닫을시
		else if($(this).val()=='open'){
			console.log('닫음');
			console.log(clickforder+'비움');
		$('#'+clickforder).find('div').remove();
		$(this).val('close');
		
       }             
	 }
	 
	  function fileClick(event) {
		event.stopPropagation();
		console.log('파일클릭');
		}
	 
	