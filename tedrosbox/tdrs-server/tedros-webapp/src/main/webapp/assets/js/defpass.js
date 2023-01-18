$(document).ready(function() { 
	$('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse  } ); 
	var key = getUrlParameter('k');
	document.getElementById("key").value = key;
});



function validate(formData, jqForm, options) { 
	
	var form = jqForm[0]; 
	
	if (!form.pass.value || !form.passConf.value) { 
		showWarnModal(clang.msg_enter_password); 
		return false; 
	} 
	if (form.pass.value && form.passConf.value && form.pass.value != form.passConf.value) { 
		showWarnModal(clang.msg_password_no_match); 
		return false; 
	} 
	showLoader('lds-circle', form);
}


// post-submit callback 
function showResponse(responseText, statusText, xhr, $form)  { 
	var form = $form[0];
	var msg = (responseText instanceof Object)
	? responseText.responseText
			: responseText;

	showLoader('', form);
	showActionModal(msg, function(){
		if(xhr.status==200){
			location.href = 'csignin.html';
		}else if(xhr.status==500){
			location.href = '500.html';
		}
	}); 
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
