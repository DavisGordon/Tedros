clang = {
	lang : 'en',
	msg_enter_valid_email : "Please enter a valid email.",
	msg_enter_password : "Please enter a password",
	msg_password_no_match : "Passwords do not match!",
	required: function (fields){ return `The field(s) ${fields} is required!`;},
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