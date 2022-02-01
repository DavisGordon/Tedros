$(document).ready(function() { 
	loadUserInfo();
	carregarAbout();
	carregarVideos();
	carregarContatos();
});


function buildPage(n){
	if(n){
		var txt = (n && n.sexo=='F') ? 'seja bem vinda!' : 'seja bem vindo!';
		txt += "<br>Aqui você poderá ajudar o próximo com seu tempo, energia e conhecimento.";
		let t = $($('#welcomeLoggedUserTemplate').html());
		$('.sm-txt-bold', t).text(n.nome);
		$('#welcomeTxt', t).html(txt);
		$('#welcomeLoggedUserTemplate').before(t);
		carregarAcoes(n);
	}else{
		let t = $($('#welcomeTemplate').html());
		$('#welcomeTemplate').before(t);
		carregarAcoes(null);
	}
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


function addAcao(obj, userStatus){
	let data = obj.data;
	let status = obj.status;
	let inscritos = obj.qtdVoluntariosInscritos;
	let maxv = obj.qtdMaxVoluntarios;
	let minv = obj.qtdMinVoluntarios;
	let desc = obj.descricao;
	let info = `${data},  ${status}, Inscritos(${inscritos}), Max(${maxv}), Min(${minv})`;
	let content = `${desc}`;
	
	let t = $($('#acaoTemplate').html());
	$('.sm-sub-title', t).text(obj.titulo);
	$('.sm-txt-bold', t).text(info);
	$('.sm-txt', t).text(content);
	if((!userStatus && obj.status!='Agendada') || (userStatus && userStatus==1))
		$('.actions', t).hide();
	$('#acaoTemplate').before(t);
}
		
function carregarAcoes(userInfo){
	var userStatus = (userInfo) ? userInfo.status : null;
	var enable = (userStatus && userStatus>=2 && userStatus<=4);
	var target = enable ? 'api/painel/acoes' : 'api/sm/acoes';
	$.ajax
	({ 
		url: target,
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				 if(result.data){
					if(result.data.length>0){
						$("#acaoAviso").hide();
						if(userStatus && userStatus==1){
							let t = $($('#statusAviso').html());
							$('.sm-txt-bold', t).text(userInfo.nome);
							$('#statusAviso').before(t);
						}
						if(enable){
							ltpf(function (){
								processarAcoes(result);
							});
						}else{
							$.each(result.data, function(index,obj){
								addAcao(obj, userStatus);
							});
						}
					}else{
						$("#acaoAviso").show();
					}
				 }
			}else{
				$("#acaoAviso").show();
			}
		}
	}); 
} 

var tpf;
var tpj;

function ltpf(callBack){
	$.ajax
    ({ 
        url: 'api/painel/tiposAjuda/PF',
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
			ltpj(callBack);
    	}
	}); 
}

function ltpj(callBack){
	$.ajax
    ({ 
        url: 'api/painel/tiposAjuda/PJ',
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
			callBack();
    	}
	}); 
}


function setAbout(txt){
	$("#aboutContent").html(txt);
	$("#aboutContent:contains('SOMOS')").html(function(_, html) {
	   return html.replace(/(SOMOS)/g, '<span class="somos">$1</span>');
	});
}

function carregarAbout(){
	$.ajax
	({ 
		url: 'api/sm/about',
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				 if(result.data){
					setAbout(result.data.descricao);
				 }
			}
		}
	}); 
}

function addContato(obj){
	var content;
	if(obj.telefone){
		if(obj.whatsapp && obj.whatsapp=='true')
			content = '<br />Whatsapp: <a href="https://api.whatsapp.com/send?1=pt_BR&phone=55'+obj.tel+'" >'+obj.telefone+'</a>';
		else
			content = '<br />'+obj.telefone;
	}
	if(obj.email)
		content += '<br />E-mail: <a href="email:'+obj.email+'" >'+obj.email+'</a>';
		
	let t = $($('#contatoTemplate').html());
	
	$('.sm-sub-title', t).text(obj.cargo);
	$('.sm-txt', t).html(obj.nome + content);
	$('#contatoTemplate').before(t);
}
function carregarContatos(){
	$.ajax
	({ 
		url: 'api/sm/contatos',
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				 if(result.data){
					$.each(result.data, function(index,obj){
						addContato(obj);
					});
				 }
			}
		}
	}); 
}

function addVideo(obj){
	let t = $($('#videoTemplate').html());
	$('.responsive-iframe', t).prop('src', obj.link);
	$('.sm-txt', t).text(obj.descricao);
	$('#videoTemplate').before(t);
}
function carregarVideos(){
	$.ajax
	({ 
		url: 'api/sm/videos',
		type: 'get',
		dataType:'json',
		headers : {'Content-Type' : 'application/json'},
		success: function(result)
		{
			if(result.code == "200"){
				 if(result.data){
					$.each(result.data, function(index,obj){
						addVideo(obj);
					});
				 }
			}
		}
	}); 
}

function carregarNoticias(){
	$.ajax
	({ 
		url: 'api/sm/news',
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
				content += ('<div class="col-12">');
				content += ('<span class="sm-sub-title">' + obj.titulo + '</span>');
				content += ('<p class="sm-txt-bold">' + obj.data + ', ' + obj.status + ', Inscritos(' + obj.qtdVoluntariosInscritos + '), Max(' + obj.qtdMaxVoluntarios + '), Min(' + obj.qtdMinVoluntarios + ')</p>');
				content += ('<p class="sm-txt">' + obj.descricao + '</p>');
			
                if(obj.status=='Agendada' && (tpf || tpj) 
                		&& ((obj.qtdVoluntariosInscritos < obj.qtdMaxVoluntarios) || (obj.qtdVoluntariosInscritos >= obj.qtdMaxVoluntarios && obj.inscrito)  ) ){
					var display = obj.inscrito ? "block" : "none";
                    content += ('<a href="javascript: displayContent(\'content'+index+'\')" class="button primary fit">Opções</a>');

					content += ('<div class="content contentLoad" id="content'+index+'" style="display:'+display+';">');
					content += ('<div id="loader'+index+'" class="loader">');
					content += ('<div id="cssloader'+index+'"><div></div></div>');
					content += ('</div>');
					content += ('<br><p class="sm-txt-bold fnt-large">Quero participar</p>');
    				content += ('<form name="frm'+index+'" id="frm'+index+'"  method="post" action="api/painel/acao/participar/">');
    				content += ('<input type="hidden" name="id" id="id" value="'+obj.id+'" /> ');
    				content += ('<input type="hidden" name="idx" id="idx" value="'+index+'" /> ');
    				content += ('<div class="fields">');
    				
    				if(obj.tiposAjudaPF || (!obj.tiposAjudaPF && !obj.tiposAjudaPJ)){
    					var vTpf = obj.tiposAjudaPF ? obj.tiposAjudaPF : tpf;
        				content += ('<div class="field half" style="text-align:left;"><p class="sm-txt-ebold">Como voluntário:</p>');
        				
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
        				content += ('</div>');
    				}
    				if(obj.tiposAjudaPJ || (!obj.tiposAjudaPF && !obj.tiposAjudaPJ)){
    					var vTpj = obj.tiposAjudaPJ ? obj.tiposAjudaPJ : tpj;
        				content += ('		<div class="field half" style="text-align:left;"><p class="sm-txt-ebold">Prestando suporte:</p>');
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
        				content += ('</div>');
    				}
    				content += ('	</div>');
    				content += ('	<ul class="actions">');
    				if(obj.qtdVoluntariosInscritos < obj.qtdMaxVoluntarios){
    					content += ('<li><input type="submit" name="submit" id="submit" class="button primary" value="Participar" /></li>');
    				}
    				if(obj.inscrito){
                   		content += ('<li><a id="pa'+obj.id+'" href="javascript: sair(\'pa'+obj.id+'\', '+ obj.id +', '+ index +')" class="button fit">Sair da ação</a></li>');
                    }
    				
    				content += ('</ul>');
    				content += ('</form>');
    				content += ('</div>');
				}
                content += ('<hr />');
				content += ('</div>');
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

function participar(btn, acao){
	$(btn).each(function() {
	    this.data("href", this.attr("href"))
	        .attr("href", "javascript:void(0)")
	        .attr("disabled", "disabled");
	});
	$.ajax
    ({ 
        url: 'api/painel/acao/participar/'+acao,
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
        url: 'api/painel/acao/sair/'+acao,
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
function showResponseTpa(result, statusText, xhr, $form)  { 
	var form = $form[0];
	if(result.code == "200"){
		processarAcoes(result);
    	alert("Obrigado sua intenção em ajudar foi registrada e em breve entraremos em contato ou se desejar entre em contato conosco!");
	}else if(result.code == "404"){
		alert(result.message);
		location.href = "painelv.html";
	}else{
		alert(result.message);
	}
	showLoader('', form);
} 