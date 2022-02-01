$(document).ready(function() { 
   $('#frm').ajaxForm( { success: validate  } ); 
   termo();
   $(':input[type="submit"]').prop('disabled', true);
   $('#aceito').click(function() {
		$(':input[type="submit"]').prop('disabled', !this.checked);
	});
   
});

function termo(){
	$.ajax
	({ 
		url: 'api/termo/recuperar',
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
			 if(result.data){
				$("#termoContent").html( result.data.descricao);
			 }
		}
		}
	}); 
}


function validate(result, statusText, xhr, $form) { 
	if(result.code == "200"){
		location.href = 'index.html';
	}else{
		alert(result.message);
	} 
}