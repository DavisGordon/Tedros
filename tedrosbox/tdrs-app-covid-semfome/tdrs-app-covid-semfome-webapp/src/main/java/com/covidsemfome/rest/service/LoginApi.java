package com.covidsemfome.rest.service;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covidsemfome.ejb.controller.IAutUserController;
import com.covidsemfome.ejb.controller.IPessoaController;
import com.covidsemfome.model.User;
import com.covidsemfome.rest.model.LoginModel;
import com.covidsemfome.rest.model.RestModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LoginApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema técnico e em breve voltaremos.";
	
	@EJB
	private IPessoaController pessServ;
	
	@EJB
	private IAutUserController autServ;
	
	@GET
	@Path("/newpass/{email}")
	public RestModel<String> newPass(@PathParam("email") String email){
		
		
		try {
			TResult<Boolean> res = pessServ.newPass(email);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>("", "200", "Email enviado");
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}
	
	@POST
	@Path("/defpass")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<String> defpass(@FormParam("key") String  key, 
			@FormParam("pass") String  pass,
			@FormParam("passConf") String  passConf){
		
		
		try {
			TResult<Boolean> res = pessServ.defpass(key, pass);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>("", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<String> login(LoginModel model){
		
		try {
			TResult<User> res = autServ.login(model.getEmail(), model.getPass());
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>(res.getValue().getKey(), "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}

}
