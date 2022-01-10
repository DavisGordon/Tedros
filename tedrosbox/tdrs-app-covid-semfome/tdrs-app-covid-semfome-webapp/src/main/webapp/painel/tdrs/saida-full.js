$(document).ready(function() { 
	autocomplete(document.getElementById("produto"), document.getElementById("prodId"), 'prod');
	loadCoz();
	buildObj();
	addEvents();
	addEventsNewItem();
	
});

var curObj;
var curItemIdx;

function filter(){
	var c = document.getElementById("cozF").value;
	var b = document.getElementById("dtinic").value;
	var e = document.getElementById("dtfim").value;
	if(!c) c="0";
	if(!b) b="x";
	if(!e) e="x";
	$.ajax
    ({ 
        url: '../../api/tdrs/filterOut/'+c+'/'+b+'/'+e,
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
			content += ('<td>' + obj.id + '</td>');
			content += ('<td>' + obj.dataDesc + '</td>');
			content += ('<td>' + obj.acao.nome + '</td>');
			content += ('<td>' + obj.cozinha.nome + '</td>');
			content += ('<td>');
            content += ('<a href="javascript: load('+obj.id+')" >Alterar</a><br>');
            content += ('<a href="javascript: del('+obj.id+', \''+obj.nome+'\')" >Excluir</a>');
            content += ('</td>');
            content += ('</tr>');
		});
		$("#tContent").html(content);
		location.href ="#resultadoPesquisa";
	}else{
		alert(result.message);
	}
}

function newReg(){
	clear();
 	location.href = '#editar';
}

function del(id,nome){
	
	var flag = confirm("Deseja excluir a saida ?");
	if (flag) {
		$.ajax
	    ({ 
	        url: '../../api/tdrs/out/del/'+id,
	        type: 'delete',
	        dataType:'json',
	        headers : {'Content-Type' : 'application/json'},
	        success: function(result)
	        {
	        	processList(result);
	        	clear();
	        	filter();
	    	}
		}); 
	}
}

function validate(){
	if(curObj){
		
		var s = '';
		if(!curObj.cozinha || !curObj.cozinha.id || curObj.cozinha.id=='')
			s += 'Cozinha';
		if(!curObj.data || curObj.data=='')
			s += (s!='' ? ', ' : '') + 'Data';
		if(!curObj.acao || !curObj.acao.id || curObj.acao.id=='')
			s +=  (s!='' ? ', ' : '') + 'Ação';
		
		if(s!='')
			s = 'Favor preencher o(s) campo(s): '+s;
			
			
		if(!curObj.itens || curObj.itens.length==0)
			s +=  (s!='' ? '\n' : '') + 'Favor adicionar os produtos utilizados!';
		else{
			var f = false;
			curObj.itens.forEach(function (i, idx){
				if(!i.produto || !i.produto.id || i.produto.id=='' 
						|| !i.quantidade || i.quantidade==''){
					var tr = $('#tContentEdit').children().get(idx);
					$(tr).addClass('warnColor');
					f = true;
				}else{
					var tr = $('#tContentEdit').children().get(idx);
					$(tr).removeClass('warnColor');
				}
			});
			if(f){
				s +=  (s!='' ? '\n' : '') + 'Favor verificar os itens na lista sem produto ou quantidade!';
				location.href = '#addLista';
			}
		}
		
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
	        url: '../../api/tdrs/out/save',
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
        url: '../../api/tdrs/out/'+id,
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
			acao: {id:null, nome:null},
			observacao: null,
			itens: [],
			containItem: function (prodId){
				var f = false;
				this.itens.forEach(function(o){
					if(o.produto.id && prodId && o.produto.id==prodId) f = true;
				});
				return f;
			},
			addItem: function(id, nome, prodId, qtd){
				if(this.containItem(prodId))
					return;
				this.itens.push({id:id, 
					produto:{id:prodId, nome:nome},
					quantidade:qtd
				})
			},
			removeItem: function(idx){
				if(this.itens){
					this.itens.splice(idx, 1);
				}
			},
			load: function(m){
				this.id = m.id;
				this.data = m.data;
				this.cozinha = m.cozinha;
				this.acao = m.acao;
				this.observacao = m.observacao;
				if(m.itens){
					var _this = this;
					m.itens.forEach(function (i){
						_this.addItem(i.id, i.produto.nome, i.produto.id, i.quantidade);
					});
				}
			}
	};
}

function readObj(){
	curItemIdx=null;
	remEvents();
	 $("#id").val(curObj.id);
	 $('#data').val(curObj.data);
	 $('#dataAcao').val('');
	 $('#cozinha option[value="'+curObj.cozinha.id+'"]').prop('selected', true);
	 $('#acao').html('');
	 $('#acao').append('<option value="' + curObj.acao.id + '">' + curObj.acao.nome + '</option>');
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
		 s += '<td>'+i.quantidade+'</td>';
		 s += '<td><a href="javascript: altItem('+idx+')">Alterar</a><br>';
		 s += '<a href="javascript: remItem('+idx+')">Remover</a></td>';
		 s += '</tr>';
		 $('#tContentEdit').append(s);
	 });
}


function addEvents(){
	$("#cozinha").change(function (){
		var id = $("#cozinha option:selected").val();
		var nome = $("#cozinha option:selected").text();
		curObj.cozinha.id=id;
		if(id)
			curObj.cozinha.nome = nome;
	});
	$("#dataAcao").change(function (){
		var val = $(this).val();
		if(val)
			filterAcao(val);
	});
	$("#acao").change(function (){
		var id = $("#acao option:selected").val();
		var nome = $("#acao option:selected").text();
		curObj.acao.id=id;
		if(id)
			curObj.acao.nome = nome;
	});
	$("#data").change(function (){
		curObj.data=$(this).val();
	});
	$("#observacao").change(function (){
		curObj.observacao=$(this).val();
	});
}
function remEvents(){
	$("#cozinha").off("change");
	$("#dataAcao").off("change");
	$("#acao").off("change");
	$("#data").off("change");
	$("#observacao").off("change");
}
function add(){
	var prodId = $('#prodId').val();
	var qtd = $('#quantidade').val();
	var f = '';
	if(!prodId)
		f = 'Produto';
	if(!qtd)
		f += (f!='' ? ' e ' : '') + 'Quantidade';
	if(f!=''){
		alert('Favor preencher o(s) campo(s): '+f);
		return;
	}
	
	var nome = $('#produto').val();
	
	curObj.addItem(null, nome, prodId, qtd);
	readObjItens();
	newItem();
}

function newItem(){
	curItemIdx = null;
	document.getElementById('addItemBtn').style.display = 'block';
	document.getElementById('newItemBtn').style.display = 'none';
	remEventsItem();
	clearItem();
	addEventsNewItem();
}

function clearItem(){
	$('#prodId').val('');
	$('#produto').val('');
	$('#quantidade').val('');
}

function addEventsNewItem(){
	$('#prodId').change(function(){
		var id = $(this).val();
		if(id && curObj.containItem(id)){
			alert("Este item já se encontra na lista!");
			$('#produto').val('');
			$('#prodId').val(null);
		}
	});
}

function remEventsNewItem(){
	$('#prodId').off('change');
}

function remItem(idx){
	if(confirm("Deseja remover o produto?")){
		curObj.removeItem(idx);
		readObjItens();
		if(curItemIdx && curItemIdx==idx){
			newItem();
		}else if(curItemIdx && curItemIdx>idx){
			curItemIdx--;
		}
	}
}

function altItem(idx){
	curItemIdx = idx;
	var o = curObj.itens[idx];
	document.getElementById('addItemBtn').style.display = 'none';
	document.getElementById('newItemBtn').style.display = 'block';
	remEventsItem();
	$('#produto').val(o.produto.nome);
	$('#prodId').val(o.produto.id);
	$('#quantidade').val(o.quantidade);
	addEventsItem();
	location.href = "#addLista";
}

function addEventsItem(){
	
	$('#produto').change(function(){
		var v = $(this).val();
		if(!v || v==''){
			alert('Preencha o campo produto!');
		}
	});
	
	$('#prodId').change(function(){
		var id = $(this).val();
		if(curObj.itens[curItemIdx].produto.id!=id && curObj.containItem(id)){
			remEventsItem();
			alert("Este produto já se encontra na lista!");
			$('#produto').val(curObj.itens[curItemIdx].produto.nome);
			$('#prodId').val(curObj.itens[curItemIdx].produto.id);
			addEventsItem();
			return;
		}
		curObj.itens[curItemIdx].produto.id = id;
		var v = $('#produto').val();
		var tr = $('#tContentEdit').children().get(curItemIdx);
		var td = $(tr).children().get(0);
		curObj.itens[curItemIdx].produto.nome = v;
		td.innerHTML = v;
		
	});
	$('#quantidade').change(function(){
		var v = $(this).val();
		if(!v || v==''){
			alert('Preencha o campo quantidade!');
		}
		var tr = $('#tContentEdit').children().get(curItemIdx);
		var td = $(tr).children().get(1);
		curObj.itens[curItemIdx].quantidade = v;
		td.innerHTML = v;
	});
	
}
function remEventsItem(){
	//$('#produto').off('change');
	$('#prodId').off('change');
	$('#quantidade').off('change');
}

function clear(){
	buildObj();
	remEvents();
	$("#id").val('');
	$('#data').val('');
	$('#dataAcao').val('');
	$('#cozinha option[value=""]').prop('selected', true);
	$("#acao").html('');
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

function filterAcao(dt){
	$.ajax
    ({ 
        url: '../../api/tdrs/filterAcao/'+dt,
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	$('#acao').html('');
        	$('#acao').append('<option value="">Selecione</option>');
        	if(result.data){
				$.each(result.data, function(index,obj){
				 $('#acao').append('<option value="' + obj.id + '">' + obj.nome + '</option>');
					
				});
			}	
    	}
	}); 
}

function search(target, val, callback){
	$.ajax
    ({ 
        url: '../../api/tdrs/'+target+'/search/'+val,
        type: 'get',
        dataType:'json',
        headers : {'Content-Type' : 'application/json'},
        success: function(result)
        {
        	if(result.data){
        		callback(result.data);
			}	
    	}
	}); 
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
        	$('#cozinha').append('<option value="">Selecione</option>');
        	$('#cozF').append('<option value="">Selecione</option>');
        	if(result.data){
				$.each(result.data, function(index,obj){
				 $('#cozinha').append('<option value="' + obj.id + '">' + obj.nome + '</option>');
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

  
   function autocomplete(inp, inpSel, target) {
    	//add an observer in the input hidden to trigger a change event
    	//var inpSel = document.getElementById("prodId");
    	var observer = new MutationObserver(function(mutations, observer) {
    	    if(mutations[0].attributeName == "value") {		
    	        $(inpSel).change();
    	    }
    	});
    	observer.observe(inpSel, {
    	    attributes: true
    	});
    	  /*the autocomplete function takes two arguments,
    	  the text field element and an array of possible autocompleted values:*/
    	  var currentFocus;
    	  /*execute a function when someone writes in the text field:*/
    	  inp.addEventListener("input", function(e) {
    	      var a, b, i, val = this.value;
    	      /*close any already open lists of autocompleted values*/
    	      closeAllLists();
    	      if (!val) { 
    	      	inpSel.value = '';
    	      	return false;
    	      }
    	      
    	      var thisObj = this;
    	      search(target, val, function(arr){
    	      
    	      currentFocus = -1;
    	      
    	      /*create a DIV element that will contain the items (values):*/
    	      a = document.createElement("DIV");
    	      a.setAttribute("id", this.id + "autocomplete-list");
    	      a.setAttribute("class", "autocomplete-items");
    	      /*append the DIV element as a child of the autocomplete container:*/
    	      thisObj.parentNode.appendChild(a);
    	      /*for each item in the array...*/
    	      for (i = 0; i < arr.length; i++) {
    	        /*check if the item starts with the same letters as the text field value:*/
    	        if (arr[i].nome.substr(0, val.length).toUpperCase() == val.toUpperCase()) {
    	          /*create a DIV element for each matching element:*/
    	          b = document.createElement("DIV");
    	          /*make the matching letters bold:*/
    	          b.innerHTML = "<strong>" + arr[i].nome.substr(0, val.length) + "</strong>";
    	          b.innerHTML += arr[i].nome.substr(val.length);
    	          /*insert a input field that will hold the current array item's value:*/
    	          b.innerHTML += "<input type='hidden' value='" + arr[i].nome + "'>";
    	          b.innerHTML += "<input type='hidden' value='" + arr[i].id + "'>";
    	          /*execute a function when someone clicks on the item value (DIV element):*/
    	              b.addEventListener("click", function(e) {
    	              /*insert the value for the autocomplete text field:*/
    	              inp.value = this.getElementsByTagName("input")[0].value;
    	              inpSel.value = this.getElementsByTagName("input")[1].value;
    	              /*close the list of autocompleted values,
    	              (or any other open lists of autocompleted values:*/
    	              closeAllLists();
    	          });
    	          a.appendChild(b);
    	        }
    	      }
    	  });
    	  });
    	  /*execute a function presses a key on the keyboard:*/
    	  inp.addEventListener("keydown", function(e) {
    	      var x = document.getElementById(this.id + "autocomplete-list");
    	      if (x) x = x.getElementsByTagName("div");
    	      if (e.keyCode == 40) {
    	        /*If the arrow DOWN key is pressed,
    	        increase the currentFocus variable:*/
    	        currentFocus++;
    	        /*and and make the current item more visible:*/
    	        addActive(x);
    	      } else if (e.keyCode == 38) { //up
    	        /*If the arrow UP key is pressed,
    	        decrease the currentFocus variable:*/
    	        currentFocus--;
    	        /*and and make the current item more visible:*/
    	        addActive(x);
    	      } else if (e.keyCode == 13) {
    	        /*If the ENTER key is pressed, prevent the form from being submitted,*/
    	        e.preventDefault();
    	        if (currentFocus > -1) {
    	          /*and simulate a click on the "active" item:*/
    	          if (x) x[currentFocus].click();
    	        }
    	      }
    	  });
    	  function addActive(x) {
    	    /*a function to classify an item as "active":*/
    	    if (!x) return false;
    	    /*start by removing the "active" class on all items:*/
    	    removeActive(x);
    	    if (currentFocus >= x.length) currentFocus = 0;
    	    if (currentFocus < 0) currentFocus = (x.length - 1);
    	    /*add class "autocomplete-active":*/
    	    x[currentFocus].classList.add("autocomplete-active");
    	  }
    	  function removeActive(x) {
    	    /*a function to remove the "active" class from all autocomplete items:*/
    	    for (var i = 0; i < x.length; i++) {
    	      x[i].classList.remove("autocomplete-active");
    	    }
    	  }
    	  function closeAllLists(elmnt) {
    	    /*close all autocomplete lists in the document,
    	    except the one passed as an argument:*/
    	    var x = document.getElementsByClassName("autocomplete-items");
    	    for (var i = 0; i < x.length; i++) {
    	      if (elmnt != x[i] && elmnt != inp) {
    	      x[i].parentNode.removeChild(x[i]);
    	    }
    	  }
    	}
    	/*execute a function when someone clicks in the document:*/
    	document.addEventListener("click", function (e) {
    	    closeAllLists(e.target);
    	});
    	}