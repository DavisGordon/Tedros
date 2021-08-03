$(document).ready(function() { 
    		    // bind form using ajaxForm 
    		    $('#frm').ajaxForm( { beforeSubmit: validate  } ); 
    		});
    		
    		function validate(formData, jqForm, options) { 
    			 
    		    var form = jqForm[0]; 
    		    if (!form.email.value || !form.pass.value) { 
    		        alert('Por favor informar email e senha.'); 
    		        return false; 
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
    		        		location.href = 'painel/main.html?c='+result.data;
    		        	}else{
    		        		alert(result.message);
    		        	}
    		        
    		    	}
    			});
    		    
    		    return false;
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
    		        		alert("Um email foi enviado com as instrucões para definir uma nova senha.")
    		        	}else{
    		        		alert(result.message);
    		        	}
    		        
    		    	}
    			});
    		    
    		    return false;
    		}