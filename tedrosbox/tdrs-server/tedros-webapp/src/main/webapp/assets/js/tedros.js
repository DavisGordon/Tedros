var clang;
const langCke = 'tdrslang';

function getCookie(cookieName) {
	let cookie = {};
	document.cookie.split(';').forEach(function(el) {
		let [key,value] = el.split('=');
		cookie[key.trim()] = value;
	})
	return cookie[cookieName];
}

function showModal(content, params){
	$("#tModal").html(content);
	$("#tModal").modal(params);
}

function showActionModal(msg, callback){
	
	$(document).on($.modal.CLOSE, function() {
		$(document).off($.modal.CLOSE);
		callback();
	});
	showModal("<center class='txt-bold'>"+msg+"</center>", {
		clickClose: false
	});
}

function showMsgModal(msg){
	showModal("<center class='txt-bold'>"+msg+"</center>", {showClose: false});
}

function showWarnModal(msg){
	showModal("<center class='txt-bold'>"+msg+"</center>", {
		  closeClass: 'icon-remove',
		  closeText: '!'
		});
}

function validateEmail(email) {
	  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
}


var getUrlParameter = function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1),
		sURLVariables = sPageURL.split('&'),
		sParameterName,
		i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
		}
	}
};

function search(target, val, callback){
	$.ajax
    ({ 
        url: target+'/'+val,
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
	        if (arr[i].value.substr(0, val.length).toUpperCase() == val.toUpperCase()) {
	          /*create a DIV element for each matching element:*/
	          b = document.createElement("DIV");
	          /*make the matching letters bold:*/
	          b.innerHTML = "<strong>" + arr[i].value.substr(0, val.length) + "</strong>";
	          b.innerHTML += arr[i].value.substr(val.length);
	          /*insert a input field that will hold the current array item's value:*/
	          b.innerHTML += "<input type='hidden' value='" + arr[i].value + "'>";
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