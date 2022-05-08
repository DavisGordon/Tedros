$(document).ready(function() { 
	
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	pCamp = urlParams.get('c');
	pVal = urlParams.get('v');
	pPer = urlParams.get('p');
	pFor = urlParams.get('fid');
	pForDesc = urlParams.get('fd');
	if(pCamp){
		$('#campanhaLoading').show();
		$('#campanhaAviso').hide();
		$('#processandoAjuda').hide();
		loadUserInfo();
	}
	loadFooter();
});
var pVal, pPer, pFor, pForDesc, pCamp;
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
			
			if(pVal){
				$('#valDiv', t).show();
				var r = '<span class="sm-txt-bold" >'+pVal+'</span>';
				$('#valDiv', t).append(r);
			}
			if(pPer){
				$('#perDiv', t).show();
				var r = '<span class="sm-txt-bold" >'+pPer+'</span>';
				$('#perDiv', t).append(r);
				
			}
			$('#campanhasContainer').append(t);
		
			paypal.Buttons({
		        style: {
		                 shape: 'rect',
		                 color: 'gold',
		                 layout: 'vertical',
		                 label: 'subscribe'
		             },
		             createSubscription: function(data, actions) {
		               return actions.subscription.create({
		                 /* Creates the subscription */
		                 plan_id: 'P-9NW33321M3871482CMJ3SFVI',
		                 quantity: 1 // The quantity of the product for a subscription
		               });
		             },
		        // Finalize the transaction on the server after payer approval
		        onApprove: (data, actions) => {
					var detalhe = {
									'tipo':'Pagamento on line',
									'executor':'PayPal',
									'idTransacao': data.subscriptionID,
									//'statusTransacao': transaction.status,
									//'valorPagamento': transaction.amount.value,
									//'dataHoraPagamento': transaction.create_time,
									'detalhe':JSON.stringify(data, null, 2).replaceAll("{","<").replaceAll("}",">")
								};
								ajudar(detalhe);
					          
		        }
		      }).render('#paypal-button-container');
		});
	}else{
		$('#campanhaLoading').hide();
		$('#campanhaAviso').show();
	}
}

function ajudar(detalhe){
	if(!loggedUser){
		alert('Necessario fazer o login primeiro!');
		location='painelv.html';
	}else{
		if(detalhe && pVal && pPer && pFor && pForDesc && pCamp){
		
			$('#paypal-button-container')
			.html("<p  class='sm-txt fnt-large'>Atualizando seus dados sobre sua ajuda... </p>");
			
			var curObj = {'id':pCamp, 'valor':pVal, 'periodo':pPer, 'assIdForma':pFor, 'detalheAjuda': detalhe };
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
				$('#paypal-button-container')
				.html("<p class='sm-txt-bold fnt-large'>Obrigado pela sua ajuda!<br>Deus te abençõe abundantemente!</p><br><ul class='actions stacked'><li><a href='campanha.html' class='button primary fit'>Voltar</a></li></ul>");
				
				}
			});
			
		}else{
			alert('Esta operação não pode ser realizada por falta de alguns dados!');
			location = 'campanha.html';
		}
	}
}


function loadCampanha(t){
	$.ajax
    ({ 
        url: 'api/'+t+'/campanha/'+pCamp,
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(r)
        {
        	buildCampanha([r.data]);
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


