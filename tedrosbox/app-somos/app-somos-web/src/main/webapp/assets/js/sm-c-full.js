$(document).ready(function() { 
	$('#campanhaLoading').show();
	$('#campanhaAviso').hide();
	$('#processandoAjuda').hide();
	loadUserInfo();
	loadFooter();
});

function buildAjudaCampanha(l){
	if(l && l.length>0){
		$('#meuhistorico').show();
		$('#historicoContainer').empty();
		l.forEach(function (o, idx){
			let t = $($('#historicoTemplate').html());
			var color = idx%2==0 ? '#D6EAF8' : '#ffffff';
			$('.col-4', t).css('background-color: '+color+';');
			$('#histTitulo', t).text(o.titulo);
			$('#histDesc', t).text(o.desc);
			if(o.detalhe){
				$('#histAction', t).text(o.detalhe.desc);
			}else
				$('#histAction', t)
				.html("<ul class='actions stacked'><li><a href='javascript:cancelar(\""+o.id+"\", \""+o.titulo+"\")' class='button small fit'>Cancelar</a></li></ul>");
			
			$('#historicoContainer').append(t);
		});
	}else{
		$('#meuhistorico').hide();
	}
}


function buildCampanha(l){
	if(l && l.length>0){
		$('#campanhaLoading').hide();
		$('#campanhaAviso').hide();
		l.forEach(function (o, idx){
			let t = $($('#campTemplate').html());
			if(o.image)
				$('#campImg', t).prop('src', 'api/f/i/'+o.image);
			$('#titulo', t).html(o.titulo);
			$('#desc', t).html(o.desc);
			if(o.dataFim){
				$('#dtFimDiv', t).show();
				$('#dtFim', t).html(o.dataFim);
			}else
				$('#dtFimDiv', t).hide();
			if(o.meta){
				$('#metaDiv', t).show();
				$('#meta', t).html(o.meta);
			}else
				$('#metaDiv', t).hide();
			if(o.angariado){
				$('#angaDiv',t).show();
				$('#angariado', t).html(o.angariado);
			}else
				$('#angaDiv', t).hide();
			if(o.valores){
				$('#valDiv', t).show();
				o.valores.forEach(function (v, i){
					var r = '<input type="radio" id="val'+idx+'-'+i+'" name="valRadio'+idx+'" value="'+v.valor+'">'+
							'<label class="sm-txt-bold" for="val'+idx+'-'+i+'">'+v.valor+'</label>' +
							'<input type="hidden" id="plval'+idx+'-'+i+'" value="'+v.plano+'" >';
					$('#valDiv', t).append(r);
				});
			}else{
				$('#valDiv', t).append("<input type='hidden' id='val"+idx+"n' >");
				$('#valDiv', t).hide();
			}
			if(o.periodos){
				$('#perDiv', t).show();
				o.periodos.forEach(function (v, i){
					var r = '<input type="radio" id="per'+idx+'-'+i+'" name="perRadio'+idx+'" value="'+v+'">'+
							'<label class="sm-txt-bold"  for="per'+idx+'-'+i+'">'+v+'</label>';
					$('#perDiv', t).append(r);
				});
			}else{
				$('#perDiv', t).append("<input type='hidden' id='per"+idx+"n' >");
				$('#perDiv', t).hide();
			}
			if(o.formas){
				$('#forDiv', t).show();
				o.formas.forEach(function (v, i){
					var tr = '<tr><td><input type="radio" id="for'+idx+'-'+i+'" name="forRadio'+idx+'" value="'+v.id+'">'+
					'<label class="sm-txt-bold"  for="for'+idx+'-'+i+'">'+v.nome+'</label></td><td>'+v.desc+'</td></tr>';
					$('#forTb', t).append(tr);
				});
			}else{
				$('#forDiv', t).append("<input type='hidden' id='for"+idx+"n' >");
				$('#forDiv', t).hide();
			}
			if(loggedUser){
				var btn =  "Quero ajudar";
				$('#ajudarBtn', t).append("<li><a href='javascript:ajudar(\""+idx+"\")' class='button primary fit'>"+btn+"</a></li>");
				//if(o.associado && o.associado=='x' && !hideCancelar)
				//	$('#ajudarBtn', t).append("<li><a href='javascript:cancelar(\""+o.id+"\", \""+o.titulo+"\")' class='button small fit'>Cancelar ajuda</a></li>");
				
				$('#ajudarBtn', t).append('<input type="hidden" id="camp'+idx+'" value="'+o.id+'" >');
			}else
				$('#ajudarBtn', t).append('<li><a href="painelv.html" class="button fit">Para ajudar faça login primeiro</a></li>');
			$('#campanhasContainer').append(t);
		});
	}else{
		$('#campanhaLoading').hide();
		$('#campanhaAviso').show();
	}
}

function cancelar(id, titulo){
	if(confirm('Deseja realmente cancelar sua ajuda na campanha '+titulo+'?')){
		$('#historicoContainer').empty();
		$.ajax
		({ 
		    url: 'api/painel/campanha/cancelar/'+id,
		    type: 'delete',
		    dataType:'json',
		    headers : {'Content-Type' : 'application/json'},
		    success: function(result)
		    {
		    	buildAjudaCampanha(result.data);
				alert("Sua participação foi cancelada!");
			}
		}); 
	}
}

function ajudar(idx){
	if(!loggedUser){
		alert('Necessario fazer o login primeiro!');
		location='painelv.html';
	}else{
		if(idx){
			var s = '';
			var pVal, pPer, pFor, pForDesc, pCamp, pPlano;
			pCamp = $('#camp'+idx).val();
			if(!document.getElementById('val'+idx+'n')){
				var id = $("input[id|='val"+idx+"']:checked").attr('id');
				if(id)
					pPlano = $("#pl"+id).val();
				pVal = $("input[id|='val"+idx+"']:checked").val();
				if(!pVal) s += 'Valor';
			} 
			if(!document.getElementById('per'+idx+'n')){
				pPer = $("input[id|='per"+idx+"']:checked").val();
				if(!pPer) s += s!='' ?  ', Periodo' : 'Periodo';
			} 
			if(!document.getElementById('for'+idx+'n')){
				pFor = $("input[id|='for"+idx+"']:checked").val();
				$("input[id|='for"+idx+"']:checked").each(function() {
				    var idVal = $(this).attr("id");
				    pForDesc = $("label[for='"+idVal+"']").text();
				});
				if(!pFor) s += s!='' ?  ', Forma de ajuda' : 'Forma de ajuda';
			} 
			if(s!='')
				alert('Favor preencher o(s) campo(s) '+s);
			else{
				if(pForDesc && pForDesc=="PayPal"){
					if(pPer=="Unica"){
						location = "paypal_capture.html?c="+pCamp+"&v="+pVal+"&p="+pPer+"&fid="+pFor+"&fd="+pForDesc;
					}else if(pPer=="Mensal"){
						if(pPlano && pPlano!="null")
							location = "paypal_subscription.html?c="+pCamp+"&v="+pVal+"&p="+pPer+"&fid="+pFor+"&fd="+pForDesc+"&pl="+pPlano;
						else
							alert('Desculpe no momento este valor não esta disponivel para doações mensais para esta forma de ajuda, '+
							'tente outro valor, obrigado.');
					}else{
						alert("Para esta forma de ajuda somente os periodos Unica e Mensal são aceitos!")
					}
					
				}else{
				$('#campanhasContainer').empty();
				$('#processandoAjuda').show();
				var curObj = {'idCampanha':pCamp, 'valor':pVal, 'periodo':pPer, 'idFormaAjuda':pFor };
				$.ajax
				({ 
				    url: 'api/painel/campanha/ajudar',
				    type: 'PUT',
				    dataType:'json',
				    data: JSON.stringify(curObj),
				    contentType: 'application/json',
				    headers : {'Content-Type' : 'application/json'},
				    success: function(r)
				    {
						alert('Obrigado '+loggedUser.nome+'!\n'+
						'Em breve enviaremos para seu email os dados para concluir esta ajuda!');
				    	buildAjudaCampanha(r.data);
						$('#processandoAjuda').hide();
				    	loadCampanha();
					}
				});
			}
			}
		}
	}
}

function loadCampanha(){
	$.ajax
    ({ 
        url: 'api/sm/campanhas',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(r)
        {
        	buildCampanha(r.data);
    	},
		statusCode: {
		    404: function() {
		    }
		  }
	}); 
}


function loadAjudaCampanha(){
	$.ajax
    ({ 
        url: 'api/painel/campanha/associado',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(r)
        {
        	buildAjudaCampanha(r.data);
    	},
		statusCode: {
		    404: function() {
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
			loggedUser = r.data;
        	loadCampanha();
			loadAjudaCampanha()
    	},
		statusCode: {
		    401: function() {
		      loadCampanha();
		    },
			409: function() {
			  location.href = 'termo.html';
			}
		  }
	}); 
}
var loggedUser;


