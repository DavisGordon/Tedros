function configGeo(country, adminArea, city){
	$.get( "../../api/geo/countries")
	  .done(function( res ) {
		  if(res.code == "200"){
     		 if(res.data){
					$.each(res.data, function(index,obj){
     				$("#"+country).append('<option value="'+obj.id+'">'+obj.value+'</option>');
     			});
     		 }
     	}
	  });
	
	$('#'+country).change(function(){
		var v = $("#"+country+" option:selected").val();
		$("#"+adminArea).empty();
		$("#"+city).empty();
		if(v)
			$.get( "../../api/geo/adminAreas/"+v)
			  .done(function( res ) {
				  if(res.code == "200"){
		     		 if(res.data){
		     			$("#"+adminArea).append('<option value="">--</option>');
						$.each(res.data, function(index,obj){
		     				$("#"+adminArea).append('<option value="'+obj.id+'">'+obj.value+'</option>');
		     			});
		     		 }
				  }
			  });
		
	});
	
	$('#'+adminArea).change(function(){
		var c = $("#"+country+" option:selected").val();
		var v = $("#"+adminArea+" option:selected").val();
		$("#"+city).empty();
		if(v)
			$.get( "../../api/geo/cities/"+c+"/"+v)
			  .done(function( res ) {
				  if(res.code == "200"){
		     		 if(res.data){
		     			$("#"+city).append('<option value="">--</option>');
						$.each(res.data, function(index,obj){
		     				$("#"+city).append('<option value="'+obj.id+'">'+obj.value+'</option>');
		     			});
		     		 }
				  }
			  });
		
	});
}