class Geo {
	constructor(country, adminArea, city, 
			countryCallback, adminAreaCallback, cityCallback){
		this.country = country;
		this.adminArea = adminArea;
		this.city = city;
		var instance = this;
		$.get( "../../api/geo/countries")
		  .done(function( res ) {
			  if(res.code == "200"){
	     		 if(res.data){
						$.each(res.data, function(index,obj){
	     				$("#"+country).append('<option value="'+obj.id+'">'+obj.value+'</option>');
	     			});
	     		 }
			  }
			  if(countryCallback)
				  countryCallback(res);
		  });
	
		$('#'+country).change(function(){
			var v = $("#"+country+" option:selected").val();
			if(v)
				instance.loadAdminArea(v, adminAreaCallback);
		});
		
		$('#'+adminArea).change(function(){
			var c = $("#"+country+" option:selected").val();
			var v = $("#"+adminArea+" option:selected").val();
			if(v && c)
				instance.loadCities(c, v, cityCallback);
		});
	}
	
	loadAdminArea(countryId, callback){
		var aa = this.adminArea;
		var ct = this.city;
		$("#"+aa).empty();
		$("#"+ct).empty();
		$.get( "../../api/geo/adminAreas/"+countryId)
		  .done(function( res ) {
			  if(res.code == "200"){
	     		 if(res.data){
	     			$("#"+aa).append('<option value="">--</option>');
					$.each(res.data, function(index,obj){
	     				$("#"+aa).append('<option value="'+obj.id+'">'+obj.value+'</option>');
	     			});
	     		 }
			  }
			  if(callback)
				  callback(res);
		  });
	}
	
	loadCities(countryId, adminAreaId, callback){
		var ct = this.city;
		$("#"+ct).empty();
		$.get( "../../api/geo/cities/"+countryId+"/"+adminAreaId)
		  .done(function( res ) {
			  if(res.code == "200"){
	     		 if(res.data){
	     			$("#"+ct).append('<option value="">--</option>');
					$.each(res.data, function(index,obj){
	     				$("#"+ct).append('<option value="'+obj.id+'">'+obj.value+'</option>');
	     			});
	     		 }
			  }
			  if(callback)
				  callback(res);
		  });
	}
	
	
	
	
	
	
	
	
	
}