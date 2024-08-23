 var selectid=null;
  var selectname=null;

function autoReload()
	{     
		      /*  var dt = document.getElementById("1"); 
		        dt.val('open');
		  dt.addEventListener('contextmenu', contextmenuClick);*/
		    		    
		    		 $('#1').val('open').addClass('forder');   
		    		    $('#1').on('contextmenu', contextmenuClick);
							$('#1').on('click', folderClick);
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
							let newDiv = $('<div></div>').addClass('forder').css({'white-space':'pre'}).attr('id', value.me).val('close').text(' '+value.filename);
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
				selectid= $(this).attr('id');
			    console.log("선택 아이디"+selectid);
			    
			    selectname= $(this).html().split('<')[0].trim();
			    
			    
			    console.log("선택 이름"+selectname);
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
					
					$('#fsuperforder').val(selectname);
					$('#csuperforderid').val(selectid);
				}
				
                else if($(this).attr('id')==='mupload'){
				console.log('업로드선택');	
				
				$('#usuperforder').val(selectname);
				$('#usuperforderid').val(selectid);
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
			               newDiv = $('<div></div>').addClass('forder').css({'white-space':'pre'}).attr('id', value.me).val('close').text(gap+value.filename);
							newDiv.on('contextmenu', contextmenuClick);
							newDiv.on('click', folderClick);
							}
							else if(type=='file'){
							newDiv = $('<div></div>').addClass('file').css({'white-space':'pre'}).attr('id', value.me).text(gap+value.filename);
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
	 
	 $('#forderup').on('click', function(event) {
             console.log('부모 아이디:'+$('#csuperforderid').val());
             console.log('회사 코드:'+$('#companycode').val()); 
              console.log('업로더:'+$('#uploader').val()); 
               console.log('상위 폴더 이름:'+$('#fsuperforder').val());   
               console.log('직급 이름:'+$('#underrank option:selected').text());  
               console.log('폴더 이름:'+$('#fordername').val());  
                  
               $.ajax({
			url : '/group/database/forderupload',			
           type : 'POST',
           data : {
             'companycode' : $('#companycode').val(),
			'parent' : $('#csuperforderid').val(),
             'uploader' : $('#uploader').val(),
             'rankname' : $('#underrank option:selected').text(),
             'filename' : $('#fordername').val()             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			 			let returnfile = data.returnfile;

						$.each(returnfile, function(key, value) {
							console.log(value.level);
							 let gap=' '.repeat(value.level*3);
						newDiv = $('<div></div>').addClass('file').css({'white-space':'pre'}).attr('id', value.me).val('close').text(gap+value.filename);
							
							newDiv.on('click', folderClick);
							$('#'+$('#csuperforderid').val()).append(newDiv);
					})
			 
		   }
		})	   
                  
      });
      
      //파일배열
      
      $("#files").on("change",function(event) {
      var content_files = new Array();
		var files = event.target.files;
		var filesArr = Array.prototype.slice.call(files);
		
		filesArr.forEach(function (f) {
			content_files.push(f);
			
			
						
		})
				
	  })
      
      //업로드
      var content_files = new Array();
     $('#upload').on('click', function(event) {           
               console.log('부모 아이디:'+$('#usuperforderid').val());
             console.log('회사 코드:'+$('#companycode').val()); 
              console.log('업로더:'+$('#uploader').val()); 
               console.log('상위 폴더 이름:'+$('#usuperforder').val());                               
               
               var formData = new FormData();
               var fileInput = document.getElementById('files');
               var files = fileInput.files;
               for (var x = 0; x < files.length; x++) { 
			       console.log(x);
				 formData.append("files", files[x]);
			     
		}
			    formData.append("parent", $('#usuperforderid').val());			   
			    
                    
         $.ajax({
   	      type: "POST",
   	   	  enctype: "multipart/form-data",
   	      url: "/group/database/fileupload",
       	  data : formData,
       	  processData: false,
   	      contentType: false,
   	      success: function (data) {
   	    	
   	    	
   	    	
   	    	
   	    		console.log("파일업로드 성공");
			
   	      },
   	      error: function (xhr, status, error) {
   	    	alert("서버오류로 지연되고있습니다. 잠시 후 다시 시도해주시기 바랍니다.");
   	     return false;
   	      }
   	    });
   
	                                           
           
                  
      });
	