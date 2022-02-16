function buildFooter(l){
	if(l){
		let t = $($('#siganosTemplate').html());
		$('#siganosTemplate').before(t);
		var v = '';
		l.forEach(function (o){
			v += '<li><a href="'+o.link+'" class="icon brands style2 '+o.nome+'"><span class="label"></span></a></li>';
		});
		$('#siganosContent').html(v);
	}
}

function loadFooter(){
	$.ajax
    ({ 
        url: 'api/sm/siganos',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(r)
        {
        	buildFooter(r.data);
    	},
		statusCode: {
		    404: function() {
		    }
		  }
	}); 
}
