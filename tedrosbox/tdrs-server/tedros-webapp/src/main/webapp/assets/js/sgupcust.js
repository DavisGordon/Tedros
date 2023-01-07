$(document).ready(function() { 
	$('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse, error: showResponse  } );
});



function validate(formData, jqForm, options) { 
	
	var form = jqForm[0]; 
	
	if(form.email.value && !validateEmail(form.email.value)){
		alert('Por favor informar um email valido.'); 
		return false; 
	}
	
	if (!form.pass.value || !form.passConf.value) { 
		alert('Por favor informar uma senha.'); 
		return false; 
	} 
	if (form.pass.value && form.passConf.value && form.pass.value != form.passConf.value) { 
		alert('As senhas não conferem.'); 
		return false; 
	} 
	showLoader('lds-circle', form);
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
		location.href = 'customer.html';
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
