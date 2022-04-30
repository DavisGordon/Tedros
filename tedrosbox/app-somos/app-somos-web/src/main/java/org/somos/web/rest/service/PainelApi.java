package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.somos.ejb.controller.IAssociadoController;
import org.somos.ejb.controller.ICampanhaController;
import org.somos.ejb.controller.ICampanhaMailConfigController;
import org.somos.model.AjudaCampanha;
import org.somos.model.Associado;
import org.somos.model.Campanha;
import org.somos.model.Pessoa;
import org.somos.web.rest.model.CampanhaModel;
import org.somos.web.rest.model.RestModel;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
@Path("/painel")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class PainelApi extends PainelAcoesApi{
	

	@EJB
	private ICampanhaController caServ;
	
	@EJB 
	private IAssociadoController assServ;
	
	@EJB
	private ICampanhaMailConfigController cmcServ;
	
	@PUT
	@Path("/campanha/ajudar")
	public RestModel<List<CampanhaModel>> ajudar(CampanhaModel m){
				
		try {
			Pessoa p = covidUserBean.getUser().getPessoa();
			
			TResult<Associado> res = assServ.ajudarCampanha(appBean.getToken(), p, 
					m.getId(), m.getValor(), m.getPeriodo(), m.getAssIdForma());
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				
				cmcServ.processarMailing(appBean.getToken());
				
				Associado a = res.getValue();
				return processarCampanhas(a);
			
			}else if(res.getResult().equals(EnumResult.WARNING)){
				return new RestModel<>(null, "404", res.getMessage());
			}else {
				return new RestModel<>(null, "500", error.getValue() );
			}
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@DELETE
	@Path("/campanha/cancelar/{id}")
	public RestModel<List<CampanhaModel>> cancelar(@PathParam("id") Long id){
				
		try {
			TResult<Associado> res = assServ.cancelarAjuda(appBean.getToken(), covidUserBean.getUser().getPessoa(), id);
			if(res.getResult().equals(EnumResult.SUCESS)){
				Associado a = res.getValue();
				return processarCampanhas(a);
			
			}else if(res.getResult().equals(EnumResult.WARNING)){
				return new RestModel<>(null, "404", res.getMessage());
			}else {
				return new RestModel<>(null, "500", error.getValue() );
			}
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/campanhas")
	public RestModel<List<CampanhaModel>> getCampanhas(){
		try {
			
			Pessoa p = covidUserBean.getUser().getPessoa();
			
			TResult<Associado> res = assServ.recuperar(appBean.getToken(), p);
			if(res.getResult().equals(EnumResult.SUCESS)){
				return this.processarCampanhas(res.getValue());
			}else{
				return new RestModel<>(new ArrayList<>(), "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}


	private RestModel<List<CampanhaModel>> processarCampanhas(Associado a) {
		TResult<List<Campanha>> r = caServ.listarValidos(appBean.getToken());
		List<CampanhaModel> l = new ArrayList<>();
		if(r.getResult().equals(EnumResult.SUCESS)){
			
			if(r.getValue()!=null) 
				for(Campanha c : r.getValue()) {
					CampanhaModel cm = new CampanhaModel(c);
					if(a!=null && a.getAjudaCampanhas()!=null) {
						Optional<AjudaCampanha> op = a.getAjudaCampanhas().stream().filter(ac->{
							return ac.getCampanha().getId().equals(c.getId());
						}).findFirst();
						
						if(op.isPresent()) {
							AjudaCampanha ac = op.get();
							cm.setAssociado("x");
							cm.setValor(ac.getValor());
							cm.setPeriodo(ac.getPeriodo());
							if(ac.getFormaAjuda()!=null)
								cm.setAssIdForma(ac.getFormaAjuda().getId());
						}
					}
					
					l.add(cm);
				}
			return new RestModel<>(l, "200", "");
		}else if(r.getResult().equals(EnumResult.WARNING)){
			return new RestModel<>(null, "404", r.getMessage());
		}else {
			return new RestModel<>(null, "500", error.getValue() );
		}
	}
	
}
