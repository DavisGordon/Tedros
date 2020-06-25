package com.covidsemfome.rest.service;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covidsemfome.ejb.service.IAutUserService;
import com.covidsemfome.model.User;
import com.covidsemfome.rest.model.LoginModel;
import com.covidsemfome.rest.model.RestModel;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

import br.com.covidsemfome.ejb.service.ServiceLocator;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class LoginApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema tecnico em breve voltaremos.";
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<String> login(LoginModel model){
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IAutUserService serv = loc.lookup("IAutUserServiceRemote");
			TResult<User> res = serv.login(model.getEmail(), model.getPass());
			loc.close();
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>(res.getValue().getKey(), "200", res.getMessage());
			}else{
				//System.out.println(res.getErrorMessage());
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}

}
