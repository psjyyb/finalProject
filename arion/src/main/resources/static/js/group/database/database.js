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
			 			var startforder = data.startforder;
			 			console.log(startforder);
			 			
			 			$.each(startforder, function(key, value) {
				
		     })							
			 
		   }
		})
		
		
	 }