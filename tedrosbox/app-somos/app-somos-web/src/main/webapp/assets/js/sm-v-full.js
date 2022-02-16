$(document).ready(function() { 
	$('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse, error: showResponse  } );
	loadInfo();
	loadFooter();
});


function setInfo(obj){
	if(obj.image){
		let t = $($('#volImgTemplate').html());
		$('#volImg', t).prop('src', 'api/f/i/'+obj.image);
		$('#volImgTemplate').before(t);
	}
	$("#volContent").html(obj.descricao);
	$("#volContent:contains('SOMOS')").html(function(_, html) {
	   return html.replace(/(SOMOS)/g, '<span class="somos">$1</span>');
	});
}

function loadInfo(){
	$.ajax
	({ 
		url: 'api/sm/voluntarios',
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


function validate(formData, jqForm, options) { 
	
	var form = jqForm[0]; 
	if (!form.name.value || !form.email.value) { 
		alert('Por favor informar o seu nome e email para acesso ao painel.'); 
		return false; 
	} 
	
	if(form.email.value && !validateEmail(form.email.value)){
		alert('Por favor informar um email valido.'); 
		return false; 
	}
	
	if (!form.pass.value || !form.passConf.value) { 
		alert('Por favor informar uma senha para acesso ao painel.'); 
		return false; 
	} 
	if (form.pass.value && form.passConf.value && form.pass.value != form.passConf.value) { 
		alert('As senhas não conferem.'); 
		return false; 
	} 
	showLoader('lds-heart', form);
}

function validateEmail(email) {
	  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
}

// post-submit callback 
function showResponse(responseText, statusText, xhr, $form)  { 
	var form = $form[0];
	if(responseText instanceof Object)
		alert(responseText.responseText); 
	else
		alert(responseText); 
	showLoader('', form);
	if(xhr.status==200 || xhr.status==202){
		location.href = 'painelv.html';
	}else {
		location.href = '500.html';
	}
		
}

function showLoader(className, form){
	var loader = document.getElementById('loader');
	if(className != ''){
		document.getElementById('cssloader').className = className;
		loader.style.display = 'block';
		form.style = 'background-color: #cccccc; opacity: .4;';
	}else{
		loader.style.display = 'none';
		document.getElementById('cssloader').className = '';
		form.style = '';
	}
}
