$(document).ready(function() { 
    			
    			$('#frm').ajaxForm( { beforeSubmit: validate, success: showResponse  } ); 
    			 
    			carregarEstados();
    			ltpf();
    			
    		});
    		
    		function scrollTo(a){
    			$(document.body).animate({
    			    'scrollTop':   $('#'+a).offset().top
    			}, 2000);
    		}
    		
    		var tpf;
    		var tpj;
    		function ltpf(){
    			$.ajax
    		    ({ 
    		        url: '../api/painel/tiposAjuda/PF',
    		        type: 'get',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	if(result.code == "200"){
    		        		tpf = result.data;
    		        	}else{
    		        		alert(result.message);
    		        	}
    		        	ltpj();
    		    	}
    			}); 
    		}
    		
    		function ltpj(){
    			$.ajax
    		    ({ 
    		        url: '../api/painel/tiposAjuda/PJ',
    		        type: 'get',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	if(result.code == "200"){
    		        		tpj = result.data;
    		        	}else{
    		        		alert(result.message);
    		        	}
    		        	carregarAcoes();
    		    	}
    			}); 
    		}
    		
    		function logout(){
    			$.ajax
    		    ({ 
    		        url: '../api/painel/logout',
    		        type: 'get',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	if(result.code == "200"){
    		        		location.href = '../voluntario.html';
    		        	}else{
    		        		alert(result.message);
    		        	}
    		    	}
    			}); 
    		}
    		
    		function carregarEstados(){
    			$.ajax
    		    ({ 
    		        url: '../api/painel/ufs',
    		        type: 'get',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	$('#uf').append('<option value="">Selecione</option>');
    		        	if(result.data){
   	        				$.each(result.data, function(index,obj){
   	        				 $('#uf').append('<option value="' + obj.id + '">' + obj.descricao + '</option>');
   	        				});
   	        			}	
    		        	carregarDadosPessoais();
    		    	}
    			}); 
    		}
    		
    		function carregarDadosPessoais(){
    			$.ajax
    		    ({ 
    		        url: '../api/painel/user',
    		        type: 'get',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	processarUser(result);
    		    	}
    			}); 
    		}
    		
    		function participar(btn, acao){
    			$(btn).each(function() {
    			    this.data("href", this.attr("href"))
    			        .attr("href", "javascript:void(0)")
    			        .attr("disabled", "disabled");
    			});
    			$.ajax
    		    ({ 
    		        url: '../api/painel/acao/participar/'+acao,
    		        type: 'post',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	processarAcoes(result);
    		    	}
    			}); 
    		}
    		
    		function sair(btn, acao){
    			$(btn).each(function() {
    			    this.data("href", this.attr("href"))
    			        .attr("href", "javascript:void(0)")
    			        .attr("disabled", "disabled");
    			});
    			$.ajax
    		    ({ 
    		        url: '../api/painel/acao/sair/'+acao,
    		        type: 'delete',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	processarAcoes(result);
    		    	}
    			}); 
    		}
    		
    		function carregarAcoes(){
    			$.ajax
    		    ({ 
    		        url: '../api/painel/acoes',
    		        type: 'get',
    		        dataType:'json',
    		        headers : {'Content-Type' : 'application/json'},
    		        success: function(result)
    		        {
    		        	processarAcoes(result);
    		    	}
    			}); 
    		}
    		
    		function displayContent(id){
    			var obj = document.getElementById(id);
    			obj.style.display = obj.style.display == 'none' ? 'block' : 'none';
    		}
    		
    		function processarAcoes(result){
    			if(result.code == "200"){
	        		 if(result.data){
	        			 var content = "";
	        			 var frms = [];
	        			$.each(result.data, function(index,obj){
	        				
	        				frms.push('frm'+index);
	        				
	        				content += ('<header>');
	        				content += ('<h1>' + obj.titulo + '</h1>');
	        				content += ('<p>' + obj.data + ', ' + obj.status + ', Inscritos(' + obj.qtdVoluntariosInscritos + '), Max(' + obj.qtdMaxVoluntarios + '), Min(' + obj.qtdMinVoluntarios + ')</p>');
	        				content += ('</header>');
	        				content += ('<p>' + obj.descricao + '</p>');
	                        content += ('<a href="javascript: displayContent(\'content'+index+'\')" class="button primary fit small">Opções</a>');
	        			
	                        if(obj.status=='Agendada' && (tpf || tpj)){
	        					var display = obj.inscrito ? "block" : "none";
	        					content += ('<div class="content" id="content'+index+'" style="display:'+display+'">');
		        				content += ('<h2>Quero participar</h2>');
		        				content += ('<form name="frm'+index+'" id="frm'+index+'" method="post" action="../api/painel/acao/participar/"><p>');
		        				content += ('<input type="hidden" name="id" id="id" value="'+obj.id+'" /> ');
	        					content += ('<div class="fields">');
		        				if(tpf){
			        				content += ('<div class="field half" style="text-align:left;"><h3>Como voluntário:</h3>');
			        				
			        				$.each(tpf, function(index2,obj2){
			        					var chk = '';
			        					$.each(obj.tiposAjuda, function(index3,obj3){
			        						if(obj2.id==obj3.id){
			        							chk = 'checked';
			        						}
			        					});
			        					content += ('<input type="checkbox" '+chk+' name="tiposAjuda" id="tiposAjudapf'+obj.id+'_'+index2+'" value="'+obj2.id+'" /> ');
			        					content += ('<label for="tiposAjudapf'+obj.id+'_'+index2+'">'+obj2.descricao+'</label><br>');
			        				});
			        				content += ('</p></div>');
		        				}
		        				if(tpj){
			        				content += ('		<div class="field half" style="text-align:left;"><h3>Prestando suporte:</h3><p>');
			        				$.each(tpj, function(index2,obj2){
			        					var chk = '';
			        					$.each(obj.tiposAjuda, function(index3,obj3){
			        						if(obj2.id==obj3.id){
			        							chk = 'checked';
			        						}
			        					});
			        					content += ('<input type="checkbox" '+chk+' name="tiposAjuda" id="tiposAjudapj'+obj.id+'_'+index2+'" value="'+obj2.id+'" />');
			        					content += ('<label for="tiposAjudapj'+obj.id+'_'+index2+'">'+obj2.descricao+'</label><br>');
			        				});
			        				content += ('</p></div>');
		        				}
		        				content += ('	</div>');
		        				content += ('	<ul class="actions">');
		        				content += ('		<li><input type="submit" name="submit" id="submit" value="Participar" /></li>');
		        				
		        				if(obj.inscrito){
	                           		content += ('<li><a id="pa'+obj.id+'" href="javascript: sair(\'pa'+obj.id+'\', '+ obj.id +')" class="button fit small wide smooth-scroll-middle">Sair da ação</a></li>');
	                            }
		        				
		        				content += ('</ul>');
		        				content += ('</form>');
		        				content += ('</div>');
	        				}
                            content += ('<hr />');
	        				});
		        					
	        			 $("#acoesContent").html(content);
	        			 
	        			 frms.forEach(function(value){
	        				 $('#'+value).ajaxForm( { beforeSubmit: validateTpa, success: showResponseTpa  } );
	        			 });
	        		 }
	        	}else{
	        		alert(result.message);
	        	}
    		}
    		
    		function processarUser(result){
    			if(result.code == "200"){
	        		 if(result.data){
	        			 $("#userName").html(result.data.nome);
	        			 $('#name').val(result.data.nome);
	        			 $('#profissao').val(result.data.profissao);
	        			 $('#identidade').val(result.data.identidade);
	        			 $('#cpf').val(result.data.cpf);
	        			 $('#nacionalidade').val(result.data.nacionalidade);
	        			 $('#email').val(result.data.email);
	        			 $('#telefone').val(result.data.telefone);
	        			 if(result.data.estadoCivil && result.data.estadoCivil=='Casado'){
	        				 $("#casado").prop("checked", true);
	        			 }
	        			 if(result.data.estadoCivil && result.data.estadoCivil=='Solteiro'){
	        				 $("#solteiro").prop("checked", true);
	        			 }
	        			 if(result.data.sexo && result.data.sexo=='M'){
	        				 $("#m").prop("checked", true);
	        			 }
	        			 if(result.data.sexo && result.data.sexo=='F'){
	        				 $("#f").prop("checked", true);
	        			 }
	        			 $('#tipoLogradouro').val(result.data.tipoLogr);
	        			 $('#logradouro').val(result.data.logradouro);
	        			 $('#complemento').val(result.data.complemento);
	        			 $('#bairro').val(result.data.bairro);
	        			 $('#cidade').val(result.data.cidade);
	        			 $('#cep').val(result.data.cep);
	        			 
	        			 $('#uf option[value="'+result.data.ufid+'"]').prop('selected', true);
	        			 
	        			 
	        		 }
	        	}else{
	        		alert(result.message);
	        	}
    		}
    		
    		function validateTpa(formData, jqForm, options) { 
    		    
    		    var form = jqForm[0]; 
    		    var f = false;
    		    $.each(form.tiposAjuda, function(index,obj){
    		    	if(obj.checked){
    		    		f = true;
    		    	}
    		    		
    		    });
    		    if (!f) { 
    		        alert('Por favor selecionar uma forma de como deseja ajudar.'); 
    		        return false; 
    		    } 
    		}
    		
			function validate(formData, jqForm, options) { 
				var campos =  "";
    		    var form = jqForm[0]; 
    		    
    		    if (!form.name.value) { 
    		        campos += "Nome"; 
    		    } 
    		    
    		    if (!form.profissao.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Profissão"; 
    		    } 
    		    
    		    if (!form.identidade.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Identidade"; 
    		    } 
    		    
    		    if (!form.cpf.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "CPF"; 
    		    } 
    		    
    		    if (!form.nacionalidade.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Nacionalidade"; 
    		    } 
    		    
    		    if (!form.email.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Email"; 
    		    } 
    		    
    		    if (!document.getElementById("casado").checked && !document.getElementById("solteiro").checked){
    		    	if(campos!="") campos += ", ";
    		        campos += "Casado ou Solteiro"; 
    		    }
    		    
    		    if (!form.tipoLogradouro.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Tipo Logradouro"; 
    		    } 
    		    
    		    if (!form.logradouro.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Logradouro"; 
    		    } 
    		    
    		    if (!form.bairro.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Bairro"; 
    		    } 
    		    
    		    if (!form.complemento.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Complemento"; 
    		    } 
    		    
    		    if (!form.cidade.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Cidade"; 
    		    } 
    		    
    		    if (!$("#uf option:selected").val()) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "Estado (UF)"; 
    		    } 
    		    
    		    if (!form.cep.value) { 
    		    	if(campos!="") campos += ", ";
    		        campos += "CEP"; 
    		    } 
    		    
    		    if (campos != "" ) { 
    		        alert('Por favor informar os campos: '+campos); 
    		        return false; 
    		    } else {
    		    	 if(form.email.value && !validateEmail(form.email.value)){
    				    	alert('Por favor informar um email valido.'); 
    				        return false; 
    				    }
    		    }
    		}
    		
    		function showResponse(result, statusText, xhr, $form)  { 
    			if(result.code == "200"){
    		    	processarUser(result)
    		    	alert("Dados alterados!");
    			}else{
	        		alert(result.message);
	        	}
    		} 
    		
    		function showResponseTpa(result, statusText, xhr, $form)  { 
    			if(result.code == "200"){
    				processarAcoes(result);
    		    	alert("Obrigado sua intenção em ajudar foi registrada e em breve entraremos em contato ou se desejar entre em contato conosco!");
    			}else if(result.code == "404"){
	        		alert(result.message);
	        		location.href = "#volun";
	        	}else{
	        		alert(result.message);
	        	}
    		} 
    		
    		function validateEmail(email) {
  			  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  			  return re.test(email);
  		}