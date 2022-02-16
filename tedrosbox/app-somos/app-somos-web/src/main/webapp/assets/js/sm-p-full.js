$(document).ready(function() { 
	$('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse, error: showResponse} );
	loadInfo();
	loadFooter();
	ltpj();
});


function ltpj(){
	$("#tipoAjuda").append('<option value="">Selecione--</option>');		
	$.ajax
    ({ 
        url: 'api/sm/tiposAjuda',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	if(result.code == "200"){
        		 if(result.data){
					$.each(result.data, function(index,obj){
        				$("#tipoAjuda").append('<option value="'+obj+'">'+obj+'</option>');
        			});
        		 }
        	}
    	}
	}); 
}


function setInfo(obj){
	if(obj.image){
		let t = $($('#parImgTemplate').html());
		$('#parImg', t).prop('src', 'api/f/i/'+obj.image);
		$('#parImgTemplate').before(t);
	}
	$("#parContent").html(obj.descricao);
	$("#parContent:contains('SOMOS')").html(function(_, html) {
	   return html.replace(/(SOMOS)/g, '<span class="somos">$1</span>');
	});
}

function loadInfo(){
	$.ajax
	({ 
		url: 'api/sm/parceria',
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
	if (!form.emp.value || !form.nome.value || !form.contato.value || !form.desc.value ) { 
		alert('Por favor informar a empresa e seu representante uma forma de contato e como gostaria de ajudar, obrigado!'); 
		return false; 
	} 
	
	showLoader('lds-heart', form);
}
 
function showResponse(responseText, statusText, xhr, $form)  { 
	var form = $form[0];
	if(responseText instanceof Object)
		alert(responseText.message); 
	else
		alert(responseText); 
	showLoader('', form);
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
