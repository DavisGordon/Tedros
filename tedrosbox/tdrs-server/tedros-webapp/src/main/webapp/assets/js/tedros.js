var clang;

function showModal(content, params){
	$("#tModal").html(content);
	$("#tModal").modal(params);
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