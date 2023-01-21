clang = {
	lang : 'pt',
	msg_enter_valid_email : "Por favor informar um email valido.",
	msg_enter_password : "Por favor informar uma senha",
	msg_password_no_match : "As senhas não conferem!",
	required: function (fields){ return `Os campos ${fields} são obrigatorios!`;},
	check : function (){
		var val = getCookie(langCke);
		if(!val || val!=this.lang){
			$.post('lang', { lng: clang.lang}, 
			    function(returnedData){
			         console.log("lang="+getCookie(langCke));
			}).fail(function(){
			      console.log("error");
			});
		}
	}
};