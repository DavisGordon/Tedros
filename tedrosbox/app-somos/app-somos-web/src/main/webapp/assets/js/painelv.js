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
function loadJS(){
	var l = window.location.href;
	l = l.split('painelv.html')[0];
	let myScript = document.createElement("script");
	myScript.setAttribute("src", l + "/assets/js/painelu.js");
	document.body.appendChild(myScript);
}

function buildPage(n){
	if(n){
		if($('#loginCol')){
			$('#loginCol').hide();
		}
		let t = $($('#welcomeTemplate').html());
		$('#userLogged', t).text(n.nome);
		$('#welcomeTemplate').before(t);
		loadJS();
	}else{
		let t1 = $($('#loginTemplate').html());
		$('#loginTemplate').before(t1);
	}
}

function validateEmail(email) {
  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
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
		    },
			409: function() {
			  location.href = 'termo.html';
			}
		  }
	}); 
}

