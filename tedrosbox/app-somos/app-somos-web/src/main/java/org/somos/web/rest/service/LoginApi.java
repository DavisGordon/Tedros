package org.somos.web.rest.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.somos.ejb.controller.IAutUserController;
import org.somos.ejb.controller.IPessoaController;
import org.somos.model.User;
import org.somos.web.bean.AppBean;
import org.somos.web.producer.Item;
import org.somos.web.rest.model.LoginModel;
import org.somos.web.rest.model.RestModel;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Path("/auth")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class LoginApi  extends BaseApi{
	
	@EJB
	private IPessoaController pessServ;
	
	@EJB
	private IAutUserController autServ;
	
	
	@GET
	@Path("/newpass/{email}")
	public RestModel<String> newPass(@PathParam("email") String email){
		
		
		try {
			TResult<Boolean> res = pessServ.newPass(appBean.getToken(), email);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>("", "200", "Email enviado");
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", error.getValue());
		}
		
	}
	
	@POST
	@Path("/defpass")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<String> defpass(@FormParam("key") String  key, 
			@FormParam("pass") String  pass,
			@FormParam("passConf") String  passConf){
		
		
		try {
			TResult<Boolean> res = pessServ.defpass(appBean.getToken(), key, pass);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println("sucesso");
				
				return new RestModel<String>("", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", error.getValue());
		}
		
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public RestModel<String> login(LoginModel model){
		
		try {
			TResult<User> res = autServ.login(model.getEmail(), model.getPass());
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				String s = res.getValue().getPessoa().getStatusVoluntario();
				if(s.equals("5") || s.equals("6")) {
					return new RestModel<String>("", "404", "Acesso negado ou desligado!");
				}else
					return new RestModel<String>(res.getValue().getKey(), "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", error.getValue());
		}
		
	}
	

}
