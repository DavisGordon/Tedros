/**
 * 
 */
package org.tedros.web.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.lang3.StringUtils;
import org.tedros.env.ejb.controller.IWebSessionController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.bean.WebSessionBean;
import org.tedros.web.model.RestModel;

/**
 * @author Davis Gordon
 *
 */
public class WebSessionBaseApi extends BaseApi {

	@Inject @Any
	protected WebSessionBean session;
	
	@EJB
	protected IWebSessionController wsServ;

	@GET
	@Path("/signout")
	public RestModel<String> signOut(){
		
		try {
			TResult<Boolean> res = wsServ.signOut(super.appBean.getToken(), 
					lang.get(), session.get().getKey());
			
			if(res.getState().equals(TState.SUCCESS)){
				return new RestModel<String>("SIGNOUT", OK, res.getMessage());
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

	@GET
	@Path("/user/info")
	public RestModel<Map<String, String>> getUserInfo(){
		
		try{
			NaturalPerson p = session.get().getUser().getPerson();
			
			Map<String, String> info = new HashMap<>();
			info.put("id", Long.toString(p.getId()));
			info.put("name", StringUtils.capitalize(p.getName()));
			info.put("sex", p.getSex().name());
			return new RestModel<>(info, OK, "OK");
		}catch (Exception e) {
			return new RestModel<>(null, ERROR, error.getValue());
		}
	}
}
