$(document).ready(function() {
	autocomplete(document.getElementById("produto"), document.getElementById("prodId"), 'prod');
	loadCoz();
	buildObj();
	addEvents();
	addEventsNewItem();

});

var curObj;

function filter(){
	var c = document.getElementById("cozF").value;
	if(!c) c="0";
	$.ajax
    ({
        url: '../../api/tdrs/filterSC/'+c,
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
			content += ('<td>' + obj.produto.codigo + '</td>');
			content += ('<td>' + obj.produto.nome + '</td>');
			content += ('<td>' + obj.cozinha.nome + '</td>');
			content += ('<td>' + obj.qtdMinima + '</td>');
			content += ('<td>' + obj.qtdInicial + '</td>');
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

	var flag = confirm("Deseja excluir este registro?");
	if (flag) {
		$.ajax
	    ({
	        url: '../../api/tdrs/sc/del/'+id,
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
		if(!curObj.produto || !curObj.produto.id || curObj.produto.id=='')
			s += (s!='' ? ', ' : '') + 'Produto';
		if(!curObj.qtdMinima || curObj.qtdMinima=='')
			s +=  (s!='' ? ', ' : '') + 'Quantidade Minima';
		if(!curObj.qtdInicial || curObj.qtdInicial=='')
			s +=  (s!='' ? ', ' : '') + 'Quantidade Inicial';

		if(s!='')
			s = 'Favor preencher o(s) campo(s): '+s;

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
	        url: '../../api/tdrs/sc/save',
	        type: 'POST',
	        dataType:'json',
	        data: JSON.stringify(curObj),
	        contentType: 'application/json',
	        headers : {'Content-Type' : 'application/json'},
	        success: function(result)
	        {
	        	process(result);
	        	showLoader('', form);
				if(result.code == "200")
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
        url: '../../api/tdrs/sc/'+id,
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
			cozinha: {id:null, nome:null},
			produto: {id:null, codigo:null, nome:null},
			qtdMinima: null,
			qtdInicial: null,
			load: function(m){
				this.id = m.id;
				this.produto = m.produto;
				this.cozinha = m.cozinha;
				this.qtdMinima = m.qtdMinima;
				this.qtdInicial = m.qtdInicial;
			}
	};
}

function readObj(){
	remEvents();
	 $("#id").val(curObj.id);
	 $('#produto').val(curObj.produto.nome);
	 $('#prodId').val(curObj.produto.id);
	 $('#cozinha option[value="'+curObj.cozinha.id+'"]').prop('selected', true);
	 $('#qtdMinima').val(curObj.qtdMinima);
	$('#qtdInicial').val(curObj.qtdInicial);
	addEvents();
}

function addEvents(){
	$("#cozinha").change(function (){
		var id = $("#cozinha option:selected").val();
		var nome = $("#cozinha option:selected").text();
		curObj.cozinha.id=id;
		if(id)
			curObj.cozinha.nome = nome;
	});

	$("#prodId").change(function (){
		var id = $(this).val();
		var nome = $("#produto").val();
		curObj.produto.id=id;
		curObj.produto.nome = nome;
	});
	$("#qtdMinima").change(function (){
		curObj.qtdMinima=$(this).val();
	});
	$("#qtdInicial").change(function (){
		curObj.qtdInicial=$(this).val();
	});
}
function remEvents(){
	$("#cozinha").off("change");
	$("#prodId").off("change");
	$("#qtdMinima").off("change");
	$("#qtdInicial").off("change");
}

function clear(){
	buildObj();
	remEvents();
	$("#id").val('');
	$('#produto').val('');
	$('#prodId').val('');
	$('#cozinha option[value=""]').prop('selected', true);
	$("#qtdInicial").val('');
 	$("#qtdMinima").val('');
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
