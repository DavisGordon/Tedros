$(document).ready(function() { 
	clang.check();
	autocomplete(document.getElementById("streetTypeName"), 
			document.getElementById("streetType"), 
			'../../api/geo/streettype');
	configGeo("country", "adminArea", "city");
	$('#frm1000').ajaxForm( { beforeSubmit: validate, success: showResponse, error: showResponse  } );
});



function validate(formData, jqForm, options) { 
	
	var form = jqForm[0]; 
	var fields;
	if(!form.name.value)
		fields = append("name", fields);
	if(!form.lastName.value)
		fields = append("lastName", fields);
	if(!$("#sex option:selected").val())
		fields = append("sex", fields);
	if(!form.identity.value)
		fields = append("identity", fields);
	if(!form.taxId.value)
		fields = append("taxId", fields);
	if(!form.phone.value)
		fields = append("phone", fields);
	if(!form.streetType.value)
		fields = append("streetTypeName", fields)
	if(!form.publicPlace.value)
		fields = append("publicPlace", fields);
	if(!$("#country option:selected").val())
		fields = append("country", fields);
	if(!$("#adminArea option:selected").val())
		fields = append("adminArea", fields);
	if(!$("#city option:selected").val())
		fields = append("city", fields);
	
	if(fields){
		showWarnModal(clang.required(fields));
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
	
	if(xhr.status==200){
		showMsgModal(clang.msg_data_saved);
	}else if(xhr.status==500)
		location.href = '500.html';
		
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
