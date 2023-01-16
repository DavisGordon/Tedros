var clang;

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