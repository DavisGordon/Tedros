if($('#loginCol')){
	$('#loginCol').hide();
}
let t = $($('#welcomeTemplate').html());
$('#userLogged', t).text(loggedUser.nome);
$('#welcomeTemplate').before(t);

if(loggedUser.estrategico=='true')
	$('#painelActions').prepend('<li><a href="tdrs/main.html" class="button fit primary">Tedros Web (Gestão)</a></li>')

let t1 = $($('#userFrmTemplate').html());
$('#userFrmTemplate').before(t1);
$('#frm1000').ajaxForm( { beforeSubmit: validateUD, success: showResponseUD  } );
	//
loadUfs(function () {
	loadUser();
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

function showLoader(className, form){
	var loader = document.getElementById('loader'+form.idx.value);
	if(className != ''){
		document.getElementById('cssloader'+form.idx.value).className = className;
		loader.style.display = 'block';
		form.style = 'background-color: #cccccc; opacity: .4;';
	}else{
		loader.style.display = 'none';
		document.getElementById('cssloader'+form.idx.value).className = '';
		form.style = '';
	}
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


function validateUD(formData, jqForm, options) { 
	var campos =  "";
    var form = jqForm[0]; 
    
    if (!form.name.value) { 
        campos += "Nome"; 
    } 
    
    if (!form.profissao.value) { 
    	if(campos!="") campos += ", ";
        campos += "Profissão"; 
    } 
    
    if (!form.identidade.value) { 
    	if(campos!="") campos += ", ";
        campos += "Identidade"; 
    } 
    
    if (!form.cpf.value) { 
    	if(campos!="") campos += ", ";
        campos += "CPF"; 
    } 
    
    if (!form.nacionalidade.value) { 
    	if(campos!="") campos += ", ";
        campos += "Nacionalidade"; 
    } 
    
    if (!form.email.value) { 
    	if(campos!="") campos += ", ";
        campos += "Email"; 
    } 
    
    if (!$("#estCiv option:selected").val()){
    	if(campos!="") campos += ", ";
        campos += "Estado Civil"; 
    }
    
    if (!form.tipoLogradouro.value) { 
    	if(campos!="") campos += ", ";
        campos += "Tipo Logradouro"; 
    } 
    
    if (!form.logradouro.value) { 
    	if(campos!="") campos += ", ";
        campos += "Logradouro"; 
    } 
    
    if (!form.bairro.value) { 
    	if(campos!="") campos += ", ";
        campos += "Bairro"; 
    } 
    
    if (!form.complemento.value) { 
    	if(campos!="") campos += ", ";
        campos += "Complemento"; 
    } 
    
    if (!form.cidade.value) { 
    	if(campos!="") campos += ", ";
        campos += "Cidade"; 
    } 
    
    if (!$("#uf option:selected").val()) { 
    	if(campos!="") campos += ", ";
        campos += "Estado (UF)"; 
    } 
    
    if (!form.cep.value) { 
    	if(campos!="") campos += ", ";
        campos += "CEP"; 
    } 
    
    if (campos != "" ) { 
        alert('Por favor informar os campos: '+campos); 
        return false; 
    } else {
    	 if(form.email.value && !validateEmail(form.email.value)){
		    	alert('Por favor informar um email valido.'); 
		        return false; 
		    }
    }
    
    showLoader('lds-circle', form);
}

function showResponseUD(result, statusText, xhr, $form)  { 
	if(result.code == "200"){
    	processarUser(result)
    	alert("Dados alterados!");
	}else{
		alert(result.message);
	}
	 showLoader('', $form[0]);
} 

