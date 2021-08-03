$(document).ready(function() { 
		    // bind form using ajaxForm 
		    $('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse, error: showResponse  } ); 
		    
		    carregarContatos();
		    carregarAbout();
		    carregarAcoes();
		    carregarVideos();
		    carregarDoacoes();
		    carregarNoticias();
		});
		
		function carregarAcoes(){
			$.ajax
		    ({ 
		        url: 'api/csf/acoes',
		        type: 'get',
		        dataType:'json',
		        headers : {'Content-Type' : 'application/json'},
		        success: function(result)
		        {
		        	var content = "";
		        	if(result.code == "200"){
		        		 if(result.data){
		        			if(result.data.length>0){
			        			$.each(result.data, function(index,obj){
			        				content += ('<header>');
			        				content += ('<h1>' + obj.titulo + '</h1>');
			        				content += ('<p>' + obj.data + ', ' + obj.status + ', Inscritos(' + obj.qtdVoluntariosInscritos + '), Max(' + obj.qtdMaxVoluntarios + '), Min(' + obj.qtdMinVoluntarios + ')</p>');
			        				content += ('</header>');
			        				content += ('<p>' + obj.descricao + '</p>');
			        				content += ('<a href="voluntario.html" class="button primary fit small">Participar</a>');
			        				if((result.data.length-1) == index+1)
			        					content += ('<span  id="contatos" ></span>');
			        				
			                        
			        				});
		        			}else{
		        				content += ('<span  id="contatos" ></span>');
		        				content += ('<p>Não existem ações agendadas no sistema no momento, entre em contato com nossa equipe para saber onde e quando será a proxima ação.</p>');
		        			}
		        			$("#acoesContent").html(content);
		        		 }
		        	}else{
		        		content += ('<span  id="contatos" ></span>');
		        		content += ('<p>Não existem ações agendadas no sistema no momento, entre em contato com nossa equipe para saber onde e quando será a proxima ação.</p>');
        				$("#acoesContent").html(content);
		        	}
		    	}
			}); 
		}
		
		
		function carregarAbout(){
			$.ajax
		    ({ 
		        url: 'api/csf/about',
		        type: 'get',
		        dataType:'json',
		        headers : {'Content-Type' : 'application/json'},
		        success: function(result)
		        {
		        	if(result.code == "200"){
		        		 if(result.data){
		        			$("#aboutContent").html(result.data.descricao);
		        		 }
		        	}
		    	}
			}); 
		}
		
		function carregarContatos(){
			$.ajax
		    ({ 
		        url: 'api/csf/contatos',
		        type: 'get',
		        dataType:'json',
		        headers : {'Content-Type' : 'application/json'},
		        success: function(result)
		        {
		        	if(result.code == "200"){
		        		 if(result.data){
		        			 var content = "";
		        			$.each(result.data, function(index,obj){
		        				if(index>0)
		        					content += ('<br><br>');
		        				
		        				if((result.data.length-1)==index+1)
		        					content += ('<span id="doar"></span>');
		        				
		        				content += (obj.cargo+'<br />'+obj.nome);
		        				if(obj.telefone){
		        					if(obj.whatsapp && obj.whatsapp=='true')
		        						content += ('<br />Whatsapp: <a href="https://api.whatsapp.com/send?1=pt_BR&phone=55'+obj.tel+'" >'+obj.telefone+'</a>');
		        					else
		        						content += ('<br />'+obj.telefone);
		        				}
		        				if(obj.email)
		        					content += ('<br />E-mail: <a href="email:'+obj.email+'" >'+obj.email+'</a>');
		        					
		        				
		        			});
		        			$("#contatosContent").html(content);
		        		 }
		        	}
		    	}
			}); 
		}
		
		function carregarVideos(){
			$.ajax
		    ({ 
		        url: 'api/csf/videos',
		        type: 'get',
		        dataType:'json',
		        headers : {'Content-Type' : 'application/json'},
		        success: function(result)
		        {
		        	if(result.code == "200"){
		        		 if(result.data){
		        			 var content = "";
		        			$.each(result.data, function(index,obj){
		        				content += ('<div class="container">');
		        				content += ('<iframe  class="responsive-iframe"  src="'+obj.link+'">');
		        				content += ('</iframe>');
		        				content += ('</div>');
		        				content += ('<h3>' + obj.descricao + '</h3>');
		        			});
		        			$("#videosContent").html(content);
		        		 }
		        	}
		    	}
			}); 
		}
		
		function carregarNoticias(){
			$.ajax
		    ({ 
		        url: 'api/csf/news',
		        type: 'get',
		        dataType:'json',
		        headers : {'Content-Type' : 'application/json'},
		        success: function(result)
		        {
		        	if(result.code == "200"){
		        		 if(result.data){
		        			 var content = "";
		        			$.each(result.data, function(index,obj){
		        				content += ('<li><a href="'+obj.link+'">');
		        				content += ('<b>' + obj.descricao + '</b>');
		        				content += ('</a></li>');
		        			});
		        			$("#noticiasContent").html(content);
		        		 }
		        	}
		    	}
			}); 
		}
		
		
		function carregarDoacoes(){
			$.ajax
		    ({ 
		        url: 'api/csf/doacoes',
		        type: 'get',
		        dataType:'json',
		        headers : {'Content-Type' : 'application/json'},
		        success: function(result)
		        {
		        	if(result.code == "200"){
		        		 if(result.data){
		        			 var content = "";
		        			$.each(result.data, function(index,obj){
		        				content += ('<tr>');
		        				if(obj.link)
		        					 content += ('<td><a href="'+obj.link+'">' + obj.descricao + '</a></td>');
		        				else
		        					content += ('<td>' + obj.descricao + '</td>');
		        				content += ('<td>' + obj.valor +'</td>');
		        				content += ('</tr>');
		        			});
		        			$("#doacoesContent").html(content);
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
		    
		}
		
		function validateEmail(email) {
			  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			  return re.test(email);
		}
		
		// post-submit callback 
		function showResponse(responseText, statusText, xhr, $form)  { 
		    if(responseText instanceof Object)
		    	alert(responseText.responseText); 
		 	else
		 		alert(responseText); 
		    
		    if(xhr.status==200 || xhr.status==202){
		    	location.href = 'voluntario.html';
		    }else {
		    	location.href = '500.html';
		    }
		    	
		} 