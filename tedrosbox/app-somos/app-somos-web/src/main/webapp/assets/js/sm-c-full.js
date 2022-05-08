$(document).ready(function() { 
	$('#campanhaLoading').show();
	$('#campanhaAviso').hide();
	$('#processandoAjuda').hide();
	loadUserInfo();
	loadFooter();
});

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
					var chk = o.valor && o.valor==v ? 'checked' : '';
					var r = '<input type="radio" '+chk+' id="val'+idx+'-'+i+'" name="valRadio'+idx+'" value="'+v+'">'+
							'<label class="sm-txt-bold" for="val'+idx+'-'+i+'">'+v+'</label>';
					$('#valDiv', t).append(r);
				});
			}else{
				$('#valDiv', t).append("<input type='hidden' id='val"+idx+"n' >");
				$('#valDiv', t).hide();
			}
			if(o.periodos){
				$('#perDiv', t).show();
				o.periodos.forEach(function (v, i){
					var chk = o.periodo && o.periodo==v ? 'checked' : '';
					var r = '<input type="radio" '+chk+' id="per'+idx+'-'+i+'" name="perRadio'+idx+'" value="'+v+'">'+
							'<label class="sm-txt-bold"  for="per'+idx+'-'+i+'">'+v+'</label>';
					$('#perDiv', t).append(r);
				});
			}else{
				$('#perDiv', t).append("<input type='hidden' id='per"+idx+"n' >");
				$('#perDiv', t).hide();
			}
			var hideCancelar = false;
			if(o.formas){
				$('#forDiv', t).show();
				o.formas.forEach(function (v, i){
					if(o.assIdForma==v.id && v.terc && v.terc=='Sim')
						hideCancelar = true;
					var chk = o.assIdForma && o.assIdForma==v.id ? 'checked' : '';
					var tr = '<tr><td><input type="radio" '+chk+' id="for'+idx+'-'+i+'" name="forRadio'+idx+'" value="'+v.id+'">'+
					'<label class="sm-txt-bold"  for="for'+idx+'-'+i+'">'+v.nome+'</label></td><td>'+v.desc+'</td></tr>';
					$('#forTb', t).append(tr);
				});
			}else{
				$('#forDiv', t).append("<input type='hidden' id='for"+idx+"n' >");
				$('#forDiv', t).hide();
			}
			if(loggedUser){
				var btn = o.associado && o.associado=='x' ? "Quero ajudar mais" : "Quero ajudar";
				$('#ajudarBtn', t).append("<li><a href='javascript:ajudar(\""+idx+"\")' class='button primary fit'>"+btn+"</a></li>");
				if(o.associado && o.associado=='x' && !hideCancelar)
					$('#ajudarBtn', t).append("<li><a href='javascript:cancelar(\""+o.id+"\", \""+o.titulo+"\")' class='button small fit'>Cancelar ajuda</a></li>");
				
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
		$('#campanhasContainer').empty();
		$('#processandoAjuda').show();
		$.ajax
		({ 
		    url: 'api/painel/campanha/cancelar/'+id,
		    type: 'delete',
		    dataType:'json',
		    headers : {'Content-Type' : 'application/json'},
		    success: function(result)
		    {
		    	loadCampanha('painel');
				$('#processandoAjuda').hide();
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
			var pVal, pPer, pFor, pForDesc, pCamp;
			pCamp = $('#camp'+idx).val();
			if(!document.getElementById('val'+idx+'n')){
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
						location = "paypal_subscription.html?c="+pCamp+"&v="+pVal+"&p="+pPer+"&fid="+pFor+"&fd="+pForDesc;
					}else{
						alert("Para esta forma de ajuda somente os periodos Unica e Mensal são aceitos!")
					}
					
				}else{
				$('#campanhasContainer').empty();
				$('#processandoAjuda').show();
				var curObj = {'id':pCamp, 'valor':pVal, 'periodo':pPer, 'assIdForma':pFor };
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
				    	buildCampanha(r.data);
						$('#processandoAjuda').hide();
				    	alert('Obrigado '+loggedUser.nome+'!\n'+
						'Em breve enviaremos para seu email os dados para concluir esta ajuda!');
					}
				});
			}
			}
		}
	}
}

function loadCampanha(t){
	$.ajax
    ({ 
        url: 'api/'+t+'/campanhas',
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
        	loadCampanha('painel');
    	},
		statusCode: {
		    401: function() {
		      loadCampanha('sm');
		    },
			409: function() {
			  location.href = 'termo.html';
			}
		  }
	}); 
}
var loggedUser;


