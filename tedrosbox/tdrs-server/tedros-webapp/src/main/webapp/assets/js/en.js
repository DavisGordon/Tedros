clang = {
	lang : 'en',
	msg_data_saved : "Data saved successfully!",
	msg_enter_valid_email : "Please enter a valid email.",
	msg_enter_password : "Please enter a password",
	msg_password_no_match : "Passwords do not match!",
	msg_password_changed : "Password changed successfully!",
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