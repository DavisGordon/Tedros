$(document).ready(function() {
	loadCoz();
	buildObj();
});

var curObj;
var curItemIdx;
var enableSave = false;

function filter(){
	var c = document.getElementById("cozF").value;
	var b = document.getElementById("dtinic").value;
	var e = document.getElementById("dtfim").value;
	if(!c) c="0";
	if(!b) b="x";
	if(!e) e="x";
	$.ajax
    ({
        url: '../../api/tdrs/filterST/'+c+'/'+b+'/'+e,
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	processList(result);
    	}
	});
}

function processList(result){
	if(result.code == "200"){
		var content = '';
		$.each(result.data, function(index,obj){

			content += ('<tr>');
			content += ('<td>' + obj.dataDesc + '</td>');
			content += ('<td>' + obj.origem + '</td>');
			content += ('<td>' + obj.cozinha.nome + '</td>');
			content += ('<td>');
            content += ('<a href="javascript: load('+obj.id+')" >Ver/Ajustar</a><br>');
            content += ('</td>');
            content += ('</tr>');
		});
		$("#tContent").html(content);
		location.href ="#resultadoPesquisa";
	}else{
		alert(result.message);
	}
}

function save(){
	location.href = "#editar";
	if(enableSave){
		var form =  document.getElementById('frm1000');
		showLoader('lds-circle', form);
		$.ajax
	    ({
	        url: '../../api/tdrs/st/save',
	        type: 'POST',
	        dataType:'json',
	        data: JSON.stringify(curObj),
	        contentType: 'application/json',
	        headers : {'Content-Type' : 'application/json'},
	        success: function(result)
	        {
	        	process(result);
	        	showLoader('', form);
	        	alert('Dados salvo com sucesso!');
	        	if($("#tContent").children().length>0){
	        		filter();
	        	}
	    	}
		});
	}
}

function load(id){
	var form =  document.getElementById('frm1000');
	showLoader('lds-circle', form);
	location.href = "#editar";
	$.ajax
    ({
        url: '../../api/tdrs/st/'+id,
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	process(result);
        	showLoader('', form);
    	}
	});
}

function buildObj(){

	curObj = {
			id:null,
			data:null,
			cozinha: {id:null, nome:null},
			origem: null,
			observacao: null,
			itens: [],
			addItem: function(id, cod, nome, prodId, qtdMin, qtdIni, qtdCalc, qtdAjus){
				var total = (qtdAjus) ? parseInt(qtdCalc) + parseInt(qtdAjus) : parseInt(qtdCalc);
				this.itens.push({id:id,
					produto:{id:prodId, codigo:cod, nome:nome},
					qtdMinima:qtdMin,
					qtdInicial:qtdIni,
					qtdCalculado:qtdCalc,
					qtdAjuste:qtdAjus,
					total:total
				})
			},
			load: function(m){
				this.id = m.id;
				this.data = m.data;
				this.cozinha = m.cozinha;
				this.origem = m.origem;
				this.observacao = m.observacao;
				if(m.itens){
					var _this = this;
					m.itens.forEach(function (i){
						_this.addItem(i.id, i.produto.codigo, i.produto.nome, i.produto.id,
							i.qtdMinima, i.qtdInicial, i.qtdCalculado, i.qtdAjuste);
					});
				}
			}
	};
}

function readObj(){
	curItemIdx=null;
	enableSave = false;
	remEvents();
	 $("#id").val(curObj.id);
	 $('#data').val(curObj.data);
	 $('#cozinha').append('<option value="'+curObj.cozinha.id+'">'+curObj.cozinha.nome+'</option>');
	 $('#origem').val(curObj.origem);
	 $('#observacao').val(curObj.observacao);
	newItem();
	readObjItens();
	addEvents();
}

function readObjItens(){
	 $('#tContentEdit').html('');
	 curObj.itens.forEach(function(i, idx, arr){
		 var s = '<tr>';
		 s += '<td>'+i.produto.nome+'</td>';
		 s += '<td>'+i.qtdMinima+'</td>';
		 s += '<td>'+i.qtdInicial+'</td>';
		 s += '<td>'+i.qtdCalculado+'</td>';
		 s += '<td>'+i.qtdAjuste+'</td>';
		 s += '<td>'+i.total+'</td>';
		 s += '<td><a href="javascript: altItem('+idx+')">Ajustar</a></td>';
		 s += '</tr>';
		 $('#tContentEdit').append(s);
	 });
}
function addEvents(){
	$("#observacao").change(function (){
		curObj.observacao=$(this).val();
		enableSave = true;
	});
}
function remEvents(){
	$("#observacao").off("change");
}
function newItem(){
	curItemIdx = null;
	clearItem();
}
function clearItem(){
	$('#produto').val('');
	$('#qtdMinima').val('');
	$('#qtdInicial').val('');
	$('#qtdCalculado').val('');
	$('#qtdAjuste').val('');
	$('#total').val('');
}
function addEventsItem(){
	$('#qtdAjuste').change(function(){
		var v = $(this).val();
		var c = curObj.itens[curItemIdx].qtdCalculado;
		var t = (v) ? parseInt(c)+parseInt(v) : parseInt(c);
		curObj.itens[curItemIdx].qtdAjuste = v;
		curObj.itens[curItemIdx].total = t;
		$('#total').val(t);
		var tr = $('#tContentEdit').children().get(curItemIdx);
		$(tr).children().get(4).innerHTML = v;
		$(tr).children().get(5).innerHTML = t;
		enableSave = true;
	});

}
function remEventsItem(){
	$('#qtdAjuste').off('change');
}

function altItem(idx){
	curItemIdx = idx;
	var o = curObj.itens[idx];
	remEventsItem();
	$('#produto').val(o.produto.nome);
	$('#qtdMinima').val(o.qtdMinima);
	$('#qtdInicial').val(o.qtdInicial);
	$('#qtdCalculado').val(o.qtdCalculado);
	$('#qtdAjuste').val(o.qtdAjuste);
	$('#total').val(o.total);
	addEventsItem();
	location.href = "#addLista";
}

function clear(){
	buildObj();
	remEvents();
	$("#id").val('');
	$('#data').val('');
	$('#cozinha').html('');
	$("#origem").val('');
 	$("#observacao").val('');
 	$('#tContentEdit').html('');
 	newItem();
 	addEvents();
}

function process(result){
	if(result.code == "200"){
  		 if(result.data){
  			 buildObj();
  			 var e = result.data;
  			 var o = curObj;
  			 o.load(e);
  			 readObj();
  		 }
   	}else{
   		alert(result.message);
   	}
}
function loadCoz(){
	$.ajax
    ({
        url: '../../api/tdrs/coz',
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	$('#cozF').append('<option value="">Selecione</option>');
        	if(result.data){
				$.each(result.data, function(index,obj){
				 $('#cozF').append('<option value="' + obj.id + '">' + obj.nome + '</option>');
				});
			}
    	}
	});
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
