/**
 * 
 */
package com.covidsemfome.rest.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covidsemfome.ejb.controller.ISiteConteudoController;
import com.covidsemfome.parceiro.model.SiteConteudo;
import com.covidsemfome.rest.model.RestModel;
import com.covidsemfome.rest.model.SpotModel;
import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.controller.TFileEntityController;
import com.tedros.ejb.base.result.TResult;

import br.com.covidsemfome.bean.AppBean;
import br.com.covidsemfome.producer.Item;

/**
 * @author Davis Gordon
 *
 */
@Path("/emp")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class ParceiroApi {

	@Inject
	@Named("errorMsg")
	private Item<String> error;
	
	@Inject
	private AppBean appBean;
	
	@EJB
	private ISiteConteudoController scServ;
	
	@EJB
	private TFileEntityController fServ;
	
	@GET
	@Path("/spot/{id}")
	public RestModel<SpotModel> getSpot(@PathParam("id") Long id){
		try {
			SiteConteudo e = new SiteConteudo();
			e.setId(id);
			TResult<SiteConteudo> res = scServ.findById(appBean.getToken(), e);
			e = res.getValue();
			/*if(!e.getImage().isNew()) {
				TResult<TFileEntity> r = fServ.loadBytes(appBean.getToken(), e.getImage());
				e.setImage(r.getValue());
			}*/
			return new RestModel<>(new SpotModel(e), "200", "");
			
		}catch(Exception e) {
			return new RestModel<>(null, "500", this.error.getValue());
		}
	}
	
	
}
