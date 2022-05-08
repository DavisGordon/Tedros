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
		        // Order is created on the server and the order id is returned
		        createOrder: (data, actions) => {
					return actions.order.create({
					            purchase_units: [{
					              amount: {
					                value: pVal // Can also reference a variable or function
					              }
					            }]
					          });
		        },
		        // Finalize the transaction on the server after payer approval
		        onApprove: (data, actions) => {
					return actions.order.capture().then(function(orderData) {
					            // Successful capture! For dev/demo purposes:
					            console.log('Capture result', orderData, JSON.stringify(orderData, null, 2));
					            const transaction = orderData.purchase_units[0].payments.captures[0];
					           // alert(`Transaction ${transaction.status}: ${transaction.id}\n\nSee console for all available details`);
					            // When ready to go live, remove the alert and show a success message within this page. For example:
					            // const element = document.getElementById('paypal-button-container');
					            // element.innerHTML = '<h3>Thank you for your payment!</h3>';
					            // Or go to another URL:  actions.redirect('thank_you.html');
								var detalhe = {
									'tipo':'Pagamento on line',
									'executor':'PayPal',
									'idTransacao': transaction.id,
									'statusTransacao': transaction.status,
									'valorPagamento': transaction.amount.value,
									'dataHoraPagamento': transaction.create_time,
									'detalhe':JSON.stringify(orderData, null, 2).replaceAll("{","<").replaceAll("}",">")
								};
								ajudar(detalhe);
					          });
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


