package com.solidarity.rest.service;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.solidarity.bean.CovidUserBean;
import com.solidarity.ejb.controller.IAutUserController;
import com.solidarity.ejb.controller.IPessoaController;
import com.solidarity.ejb.controller.ISiteTermoController;
import com.solidarity.model.Pessoa;
import com.solidarity.model.SiteTermo;
import com.solidarity.model.User;
import com.solidarity.rest.model.LoginModel;
import com.solidarity.rest.model.RestModel;
import com.solidarity.rest.model.SiteModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Path("/termo")
@Produces(MediaType.APPLICATION_JSON)
public class TermoCondicaoApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema técnico e em breve voltaremos.";
	
	@Inject @Any
	private CovidUserBean covidUserBean;
	
	@EJB
	private IPessoaController pessServ;
	
	@EJB
	private ISiteTermoController terServ;
	
	@GET
	@Path("/recuperar")
	public RestModel<SiteModel> getTermo(){
		
		
		try {
			SiteTermo ex = new SiteTermo();
			ex.setStatus("ATIVADO");
			TResult<SiteTermo> res = terServ.find(ex);
			if(res.getResult().equals(EnumResult.SUCESS)){
				SiteModel m = (res.getValue()!=null) 
						? new SiteModel(res.getValue()) 
								: new SiteModel();
					
				
				return new RestModel<>(m, "200", "");
			}else{
				return new RestModel<>(new SiteModel(), "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(new SiteModel(), "500", ERROR);
		}
		
	}
	
	@POST
	@Path("/aceitar")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<String> aceitar(@FormParam("aceito") String  aceitar){
		
		if(StringUtils.isBlank(aceitar))
			return new RestModel<String>("", "404",  "Necessario aceitar os Termos e condições." );
		
		try {
			Pessoa p = this.covidUserBean.getUser().getPessoa();
			p.setConcordaTermo(true);
			
			TResult<Pessoa> res = pessServ.save(p);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				
				return new RestModel<String>("", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
		
	}

}
