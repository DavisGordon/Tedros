$(document).ready(function() { 
	loadInfo();
	loadFooter();
});


function setInfo(obj){
	if(obj.image){
		let t = $($('#doaImgTemplate').html());
		$('#doaImg', t).prop('src', 'api/f/i/'+obj.image);
		$('#doaImgTemplate').before(t);
	}
	$("#doaContent").html(obj.descricao);
	$("#doaContent:contains('SOMOS')").html(function(_, html) {
	   return html.replace(/(SOMOS)/g, '<span class="somos">$1</span>');
	});
	
	if(obj.itens1){
		let t = $($('#ponColTemplate').html());
		$('#ponColTemplate').before(t);
		var v = '';
		obj.itens1.forEach(function (o){
			v += '<li class="sm-txt">'+o.descricao+'</li>';
		});
		$('#ponColContent').html(v);
	}
	
	if(obj.itens2){
		var d = obj.itens2[0];
		if(d.image){
			let t = $($('#traImgTemplate').html());
			$('#traImg', t).prop('src', 'api/f/i/'+d.image);
			$('#traImgTemplate').before(t);
		}
		$("#traContent").html(d.descricao);
	}
}

function loadInfo(){
	$.ajax
	({ 
		url: 'api/sm/doacoes',
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				 if(result.data){
					setInfo(result.data);
				 }
			}
		}
	}); 
}

