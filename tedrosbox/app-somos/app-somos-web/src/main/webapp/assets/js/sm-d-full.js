$(document).ready(function() { 
	
});

function carregarDoacoes(){
$.ajax
({ 
	url: 'api/csf/doacoes',
	type: 'get',
	dataType:'json',
	headers : {'Content-Type' : 'application/json'},
	success: function(result)
	{
		if(result.code == "200"){
			 if(result.data){
				 var content = "";
				$.each(result.data, function(index,obj){
					content += ('<tr>');
					if(obj.link)
						 content += ('<td><a href="'+obj.link+'">' + obj.descricao + '</a></td>');
					else
						content += ('<td>' + obj.descricao + '</td>');
					content += ('<td>' + obj.valor +'</td>');
					content += ('</tr>');
				});
				$("#doacoesContent").html(content);
			 }
		}
	}
}); 
}
		
		