$(document).ready(function() { 
	clang.check();
	var key = getUrlParameter('k');
	$("#key").val(key);
});
function validate() { 
	
	var form = $('#frm').get(0); 
	
	if (!form.pass.value || !form.passConf.value) { 
		showWarnModal(clang.msg_enter_password); 
		return; 
	} 
	if (form.pass.value && form.passConf.value && form.pass.value != form.passConf.value) { 
		showWarnModal(clang.msg_password_no_match); 
		return; 
	} 
	showLoader('lds-circle', form);
	$.ajax
	({ 
		url: '../api/auth/defpass',
		data: JSON.stringify({"key": form.key.value, 
			"pass": form.pass.value
			}),
		type: 'post',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			showLoader('', form);
			if(result.code==500){
				location.href = '500.html';
			}else{
				var msg = result.code==200
				? clang.msg_password_changed
					: result.message;
				showActionModal(msg, function(){
					if(result.code==200){
						location.href = 'sginindex.html';
					}
				});
			}
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
