$(document).ready(function() { 
    			
    			$('#frm1000').ajaxForm( { beforeSubmit: validate, success: showResponse  } ); 
    			 
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
    		
    		function sair(btn, acao, index){
    			var form = document.getElementById("frm"+index);
    			showLoader('lds-circle', form);
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
    		        	showLoader('', form);
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
	        			
	                        if(obj.status=='Agendada' && (tpf || tpj) 
	                        		&& ((obj.qtdVoluntariosInscritos < obj.qtdMaxVoluntarios) || (obj.qtdVoluntariosInscritos >= obj.qtdMaxVoluntarios && obj.inscrito)  ) ){
	        					var display = obj.inscrito ? "block" : "none";
		                        content += ('<a href="javascript: displayContent(\'content'+index+'\')" class="button primary fit small">Opções</a>');

	        					content += ('<div class="content contentLoad" id="content'+index+'" style="display:'+display+';">');
	        					content += ('<div id="loader'+index+'" class="loader">');
	        					content += ('<div id="cssloader'+index+'"><div></div></div>');
	        					content += ('</div>');
	        					content += ('<h2>Quero participar</h2>');
		        				content += ('<form name="frm'+index+'" id="frm'+index+'"  method="post" action="../api/painel/acao/participar/"><p>');
		        				content += ('<input type="hidden" name="id" id="id" value="'+obj.id+'" /> ');
		        				content += ('<input type="hidden" name="idx" id="idx" value="'+index+'" /> ');
		        				content += ('<div class="fields">');
		        				
		        				if(obj.tiposAjudaPF || (!obj.tiposAjudaPF && !obj.tiposAjudaPJ)){
		        					var vTpf = obj.tiposAjudaPF ? obj.tiposAjudaPF : tpf;
			        				content += ('<div class="field half" style="text-align:left;"><h3>Como voluntário:</h3>');
			        				
			        				$.each(vTpf, function(index2,obj2){
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
		        				if(obj.tiposAjudaPJ || (!obj.tiposAjudaPF && !obj.tiposAjudaPJ)){
		        					var vTpj = obj.tiposAjudaPJ ? obj.tiposAjudaPJ : tpj;
			        				content += ('		<div class="field half" style="text-align:left;"><h3>Prestando suporte:</h3><p>');
			        				$.each(vTpj, function(index2,obj2){
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
		        				if(obj.qtdVoluntariosInscritos < obj.qtdMaxVoluntarios){
		        					content += ('		<li><input type="submit" name="submit" id="submit" value="Participar" /></li>');
		        				}
		        				if(obj.inscrito){
	                           		content += ('<li><a id="pa'+obj.id+'" href="javascript: sair(\'pa'+obj.id+'\', '+ obj.id +', '+ index +')" class="button fit small wide smooth-scroll-middle">Sair da ação</a></li>');
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
	        			 $('#dtNasc').val(result.data.dataNascimento);
	        			 
	        			 $('#estCiv option[value="'+result.data.estadoCivil+'"]').prop('selected', true);
	        			 $('#sexo option[value="'+result.data.sexo+'"]').prop('selected', true);
	        			
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
    		    if(form.tiposAjuda.length){
	    		    $.each(form.tiposAjuda, function(index,obj){
	    		    	if(obj.checked){
	    		    		f = true;
	    		    	}
	    		    		
	    		    });
    		    }else{
    		    	f = form.tiposAjuda.checked;
    		    }
    		    if (!f) { 
    		        alert('Por favor selecionar uma forma de como deseja ajudar.'); 
    		        return false; 
    		    } 
    		    
    		    showLoader('lds-heart', form);
    		}
    		
    		function showLoader(className, form){
    			var loader = document.getElementById('loader'+form.idx.value);
    			if(className != ''){
    				document.getElementById('cssloader'+form.idx.value).className = className;
    	    		loader.style.display = 'block';
    	    		form.style = 'background-color: #cccccc; opacity: .4;';
    			}else{
    				loader.style.display = 'none';
    				document.getElementById('cssloader'+form.idx.value).className = '';
    	    		form.style = '';
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
    		    
    		    if (!$("#estCiv option:selected").val()){
    		    	if(campos!="") campos += ", ";
    		        campos += "Estado Civil"; 
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
    		    
    		    showLoader('lds-circle', form);
    		}
    		
    		function showResponse(result, statusText, xhr, $form)  { 
    			if(result.code == "200"){
    		    	processarUser(result)
    		    	alert("Dados alterados!");
    			}else{
	        		alert(result.message);
	        	}
    			 showLoader('', $form[0]);
    		} 
    		
    		function showResponseTpa(result, statusText, xhr, $form)  { 
    			var form = $form[0];
    			if(result.code == "200"){
    				processarAcoes(result);
    		    	alert("Obrigado sua intenção em ajudar foi registrada e em breve entraremos em contato ou se desejar entre em contato conosco!");
    			}else if(result.code == "404"){
	        		alert(result.message);
	        		location.href = "#volun";
	        	}else{
	        		alert(result.message);
	        	}
    			showLoader('', form);
    		} 
    		
    		function validateEmail(email) {
  			  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  			  return re.test(email);
  		}