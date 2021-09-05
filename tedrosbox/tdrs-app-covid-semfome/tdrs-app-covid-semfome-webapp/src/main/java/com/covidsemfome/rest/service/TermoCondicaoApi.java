package com.covidsemfome.rest.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.covidsemfome.ejb.controller.IPessoaController;
import com.covidsemfome.ejb.controller.ISiteTermoController;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.SiteTermo;
import com.covidsemfome.rest.model.RestModel;
import com.covidsemfome.rest.model.SiteModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

import br.com.covidsemfome.bean.AppBean;
import br.com.covidsemfome.bean.CovidUserBean;
import br.com.covidsemfome.producer.Item;

@Path("/termo")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class TermoCondicaoApi {
	
	@Inject
	@Named("errorMsg")
	private Item<String> error;
	
	@Inject @Any
	private CovidUserBean covidUserBean;
	
	@Inject
	private AppBean appBean;
	
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
			TResult<SiteTermo> res = terServ.find(appBean.getToken(), ex);
			if(res.getResult().equals(EnumResult.SUCESS)){
				SiteModel m = (res.getValue()!=null) 
						? new SiteModel(res.getValue()) 
								: new SiteModel();
					
				
				return new RestModel<>(m, "200", "");
			}else{
				return new RestModel<>(new SiteModel(), "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(new SiteModel(), "500", error.getValue());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/aceitar")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<String> aceitar(@FormParam("aceito") String  aceitar){
		
		if(StringUtils.isBlank(aceitar))
			return new RestModel<String>("", "404",  "Necessario aceitar os Termos e condições." );
		
		try {
			Pessoa p = this.covidUserBean.getUser().getPessoa();
			p.setConcordaTermo(true);
			
			TResult<Pessoa> res = pessServ.save(appBean.getToken(), p);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				
				return new RestModel<String>("", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", error.getValue());
		}
		
	}

}
