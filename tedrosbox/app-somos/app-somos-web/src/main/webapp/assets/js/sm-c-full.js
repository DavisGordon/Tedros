$(document).ready(function() { 
	$('#campanhaLoading').show();
	$('#campanhaAviso').hide();
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
					var r = '<input type="radio" id="val'+idx+'-'+i+'" name="valRadio'+idx+'" value="'+v+'">'+
							'<label for="val'+idx+'-'+i+'">'+v+'</label>';
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
							'<label for="per'+idx+'-'+i+'">'+v+'</label>';
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
					'<label for="for'+idx+'-'+i+'">'+v.nome+'</label></td><td>'+v.desc+'</td></tr>';
					$('#forTb', t).append(tr);
				});
			}else{
				$('#forDiv', t).append("<input type='hidden' id='for"+idx+"n' >");
				$('#forDiv', t).hide();
			}
			if(loggedUser){
				$('#ajudarBtn', t).append("<a href='javascript:ajudar(\""+idx+"\")' class='button primary fit'>Quero ajudar</a>");
				$('#ajudarBtn', t).append('<input type="hidden" id="camp'+idx+'" value="'+o.id+'" >');
			}else
				$('#ajudarBtn', t).append('<a href="painelv.html" class="button fit">Faça o login e volte aqui para ajudar!</a>');
			$('#campTemplate').before(t);
		});
	}else{
		$('#campanhaLoading').hide();
		$('#campanhaAviso').show();
	}
}

function ajudar(idx){
	if(!loggedUser){
		alert('Necessario fazer o login primeiro!');
		location='painelv.html';
	}else{
		if(idx){
			var s = '';
			var pVal, pPer, pFor, pCamp;
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
				if(!pFor) s += s!='' ?  ', Forma de ajuda' : 'Forma de ajuda';
			} 
			if(s!='')
			alert('Favor preencher o(s) campo(s) '+s);
			else
			alert('sel= '+pVal+' '+pPer+' '+pFor+' '+pCamp);
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


