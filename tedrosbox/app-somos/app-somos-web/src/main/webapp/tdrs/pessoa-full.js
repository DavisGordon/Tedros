$(document).ready(function() { 
	buildObj();
	addEvents();
});

var curObj;

function filter(){
	var c = document.getElementById("fnome").value;
	var s = document.getElementById("fstatus").value;
	var b = document.getElementById("dtinic").value;
	var e = document.getElementById("dtfim").value;
	if(!b) b="x";
	if(!e) e="x";
	if(!c) c="list_all";
	if(!s) s="list_all";
	$.ajax
    ({ 
        url: '../api/tdrs/filterPess/'+c+'/'+s+'/'+b+'/'+e,
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
			content += ('<td>' + obj.nome + '</td>');
			content += ('<td>' + obj.contato + '</td>');
			content += ('<td>' + obj.tipo + '</td>');
			content += ('<td>' + obj.statusDesc + '</td>');
			content += ('<td>' + obj.dataCadastro + '</td>');
			content += ('<td>');
            content += ('<a href="javascript: load('+obj.id+')" >Alterar</a>');
            content += ('</td>');
            content += ('</tr>');
		});
		$("#tContent").html(content);
		location.href ="#resultadoPesquisa";
	}else{
		alert(result.message);
	}
}

function validate(){
	if(curObj){
		
		var s = '';
		if(!curObj.status || curObj.status=='')
			s += 'Status';
		if(s!='')
			s = 'Favor preencher o campo: '+s;
			
		if(s!='')
			alert(s);
		
		return s=='';
		
	}
}

function save(){
	location.href = "#editar";
	if(validate()){
		var form =  document.getElementById('frm1000');
		showLoader('lds-circle', form);
		$.ajax
	    ({ 
	        url: '../api/tdrs/pess/save',
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
        url: '../api/tdrs/pess/'+id,
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
			nome:null,
			contato: null,
			tipo: null,
			status: null,
			statusDesc: null,
			observacao: null,
			load: function(m){
				this.id = m.id;
				this.nome = m.nome;
				this.contato = m.contato;
				this.tipo = m.tipo;
				this.status = m.status;
				this.statusDesc = m.statusDesc;
				this.observacao = m.observacao;
			}
	};
}

function readObj(){
	remEvents();
	 $("#id").val(curObj.id);
	 $('#nome').val(curObj.nome);
	 $('#status option[value="'+curObj.status+'"]').prop('selected', true);
	 $('#observacao').val(curObj.observacao);
	addEvents();
}

function addEvents(){
	$("#status").change(function (){
		var id = $("#status option:selected").val();
		curObj.status=id;
	});
	$("#observacao").change(function (){
		curObj.observacao=$(this).val();
	});
}
function remEvents(){
	$("#status").off("change");
	$("#observacao").off("change");
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
