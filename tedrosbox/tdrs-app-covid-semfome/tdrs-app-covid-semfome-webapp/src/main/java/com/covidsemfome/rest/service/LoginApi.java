package com.covidsemfome.rest.service;

import javax.naming.NamingException;
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

import br.com.covidsemfome.ejb.service.ServiceLocator;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LoginApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema tecnico em breve voltaremos.";
	
	@GET
	@Path("/newpass/{email}")
	public RestModel<String> newPass(@PathParam("email") String email){
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IPessoaController serv = loc.lookup("IPessoaControllerRemote");
			TResult<Boolean> res = serv.newPass(email);
			loc.close();
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>("", "200", "Email enviado");
			}else{
				//System.out.println(res.getErrorMessage());
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: ERROR );
			}
			
		} catch (NamingException e) {
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
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IPessoaController serv = loc.lookup("IPessoaControllerRemote");
			TResult<Boolean> res = serv.defpass(key, pass);
			loc.close();
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>("", "200", res.getMessage());
			}else{
				//System.out.println(res.getErrorMessage());
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<String> login(LoginModel model){
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IAutUserController serv = loc.lookup("IAutUserControllerRemote");
			TResult<User> res = serv.login(model.getEmail(), model.getPass());
			loc.close();
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>(res.getValue().getKey(), "200", res.getMessage());
			}else{
				//System.out.println(res.getErrorMessage());
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: ERROR );
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}

}
