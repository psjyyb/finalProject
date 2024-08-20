function autoReload()
	{     
		
		$.ajax({
			url : '/group/database/start',
			
           type : 'POST',
           data : {
			'companycode' : $('#companycode').val()
             
              },
		 dataType:"json",
		 async: false,
           success : function(data) {
			 										
			 
		   }
		})
		
		
	 }