$(document).ready(function() { 
	loadM();
	loadFooter();
});

function buildM(l){
	if(l){
		l.forEach(function (o){
			let t = $($('#muralTemplate').html());
			if(o.image)
				$('.mural', t).prop('src', 'api/f/i/'+o.image);
			$('#muralTemplate').before(t);
		});
	}
}

function loadM(){
	$.ajax
    ({ 
        url: 'api/sm/galeria/m',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(r)
        {
        	buildM(r.data);
    	},
		statusCode: {
		    404: function() {
				
		    }
		  }
	}); 
}
