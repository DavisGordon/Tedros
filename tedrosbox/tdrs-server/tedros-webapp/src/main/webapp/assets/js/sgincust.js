
function validate() { 
	
	var form = $('#frm').get(0); 
	var fields = "";
	if (!form.email.value) { 
		fields = form.email.placeholder;
	} 
	if (!form.pass.value) { 
		fields += fields && fields.length>0 
		? ", "+form.pass.placeholder
				: form.pass.placeholder;
	} 
	if(fields.length>0){
		showWarnModal(clang.required(fields));
		return;
	}
	if(form.email.value && !validateEmail(form.email.value)){
		showWarnModal(clang.msg_enter_valid_email);
		return; 
	}
	
	$.ajax
	({ 
		url: 'api/auth/signin',
		data: JSON.stringify({"email": form.email.value, 
			"pass": form.pass.value,
			"utype": "c"
			}),
		type: 'post',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				location.href = '/cstmr/index.html?c='+result.data;
			}else{
				showWarnModal(result.message);
			}
		
		}
	});
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
