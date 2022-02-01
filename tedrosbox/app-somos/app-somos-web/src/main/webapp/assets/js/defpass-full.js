$(document).ready(function() { 
	$('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse  } ); 
	var key = getUrlParameter('k');
	document.getElementById("key").value = key;
});

function validate(formData, jqForm, options) { 
	 
	var form = jqForm[0]; 
	if (!form.pass.value || !form.passConf.value) { 
		alert('Por favor informar as senhas.'); 
		return false; 
	} 
	if (form.pass.value && form.passConf.value && form.pass.value != form.passConf.value) { 
		alert('As senhas não conferem.'); 
		return false; 
	} 
	
}

function showResponse(result, statusText, xhr, $form)  { 
	if(result.code == "200"){
		location.href = 'painelv.html';
	}else{
		alert(result.message);
	}
} 

var getUrlParameter = function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1),
		sURLVariables = sPageURL.split('&'),
		sParameterName,
		i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
		}
	}
};