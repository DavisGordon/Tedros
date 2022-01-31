$(document).ready(function() { 
	loadUserInfo();
});


function logout(){
	$.ajax
    ({ 
        url: 'api/painel/logout',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	if(result.code == "200"){
        		location.href = 'painelv.html';
        	}else{
        		alert(result.message);
        	}
    	}
	}); 
}


function newpass() { 
 
	var email  = document.getElementById("email")
	if (!email.value) { 
		alert('Por favor informar o email.'); 
		return false; 
	} 
	$.ajax
	({ 
		url: 'api/auth/newpass/'+email.value,
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				alert("Um email foi enviado com as instruções para definir uma nova senha.")
			}else{
				alert(result.message);
			}
		
		}
	});
	
	return false;
}

function buildPage(n){
	if(n){
		if($('#loginCol')){
			$('#loginCol').hide();
		}
		let t = $($('#welcomeTemplate').html());
		$('#userLogged', t).text(n.nome);
		$('#welcomeTemplate').before(t);
		
		let t1 = $($('#userFrmTemplate').html());
		$('#userFrmTemplate').before(t1);
		loadUfs(function () {
			loadUser();
		});
		
	}else{
		let t1 = $($('#loginTemplate').html());
		$('#loginTemplate').before(t1);
	}
}

function validate() { 
	 
	var form = $('#frm').get(0); 
	if (!form.email.value || !form.pass.value) { 
		alert('Por favor informar email e senha.'); 
		return; 
	} 
	$.ajax
	({ 
		url: 'api/auth/login',
		data: JSON.stringify({"email": form.email.value, "pass": form.pass.value }),
		type: 'post',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				location.href = 'painelv.html?c='+result.data;
			}else{
				alert(result.message);
			}
		
		}
	});
	
}

function loadUserInfo(){
	$.ajax
    ({ 
        url: 'api/painel/user/info',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(r)
        {
        	buildPage(r.data);
    	},
		statusCode: {
		    401: function() {
		      buildPage(null);
		    }
		  }
	}); 
}

function loadUser(){
	$.ajax
    ({ 
        url: 'api/painel/user',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	processarUser(result);
    	},
		statusCode: {
		    401: function() {
		      alert( "Necessario fazer o login primeiro!" );
		    }
		  }
	}); 
}
		

function processarUser(result){
	if(result.code == "200"){
		 if(result.data){
			 
			 $("#userName").html(result.data.nome);
			 $('#name').val(result.data.nome);
			 $('#profissao').val(result.data.profissao);
			 $('#identidade').val(result.data.identidade);
			 $('#cpf').val(result.data.cpf);
			 $('#nacionalidade').val(result.data.nacionalidade);
			 $('#email').val(result.data.email);
			 $('#telefone').val(result.data.telefone);
			 $('#dtNasc').val(result.data.dataNascimento);
			 
			 $('#estCiv option[value="'+result.data.estadoCivil+'"]').prop('selected', true);
			 $('#sexo option[value="'+result.data.sexo+'"]').prop('selected', true);
			
			 $('#tipoLogradouro').val(result.data.tipoLogr);
			 $('#logradouro').val(result.data.logradouro);
			 $('#complemento').val(result.data.complemento);
			 $('#bairro').val(result.data.bairro);
			 $('#cidade').val(result.data.cidade);
			 $('#cep').val(result.data.cep);
			 
			 $('#uf option[value="'+result.data.ufid+'"]').prop('selected', true);
			 
			 
		 }
	}else{
		alert(result.message);
	}
}

function loadUfs(callBack){
	$.ajax
    ({ 
        url: 'api/painel/ufs',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	$('#uf').append('<option value="">Selecione</option>');
        	if(result.data){
				$.each(result.data, function(index,obj){
				 $('#uf').append('<option value="' + obj.id + '">' + obj.descricao + '</option>');
				});
			}	
        	callBack();
    	}
	}); 
}
