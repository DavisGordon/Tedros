package org.tedros.web.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.env.entity.WebSession;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.model.CredentialModel;
import org.tedros.web.model.RestModel;

@Path("/auth")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class CredentialApi  extends BaseApi{
	
	@EJB
	private IWebUserController serv;
	
	@GET
	@Path("/newpass/{utype}/{email}")
	public RestModel<String> newPass(@PathParam("email") String email, 
			@PathParam("utype") String utype){
		
		try {
			TResult<Boolean> res = serv.requireChangePass(appBean.getToken(), lang.get(), email, getType(utype));
			
			if(res.getState().equals(TState.SUCCESS)){
				return new RestModel<String>("", OK, res.getMessage());
			}else{
				return new RestModel<String>("", WARN, res.getState().equals(TState.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", ERROR, error.getValue());
		}
	}
	
	@POST
	@Path("/defpass")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<String> defpass(CredentialModel model){
		try {
			TResult<Boolean> res = serv.changePass(appBean.getToken(), lang.get(), model.getKey(), model.getPass());
			
			if(res.getState().equals(TState.SUCCESS)){
				return new RestModel<String>("", OK, res.getMessage());
			}else{
				return new RestModel<String>("", WARN, 
						res.getState().equals(TState.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", ERROR, error.getValue());
		}
	}
	
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<String> signin(CredentialModel model){
		
		try {
			TResult<WebSession> res = serv.signIn(appBean.getToken(), lang.get(), 
					model.getEmail(), model.getPass(), getType(model.getUtype()));
			
			if(res.getState().equals(TState.SUCCESS)){
				return new RestModel<String>(res.getValue().getKey(), OK, res.getMessage());
			}else{
				return new RestModel<String>("", WARN, 
						res.getState().equals(TState.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", ERROR, error.getValue());
		}
		
	}
	

}
