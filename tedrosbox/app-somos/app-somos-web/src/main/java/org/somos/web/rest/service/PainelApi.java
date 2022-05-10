package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;

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
import org.somos.model.Associado;
import org.somos.model.Campanha;
import org.somos.model.DetalheAjuda;
import org.somos.model.Pessoa;
import org.somos.web.rest.model.AjudaCampanhaModel;
import org.somos.web.rest.model.CampanhaModel;
import org.somos.web.rest.model.DetalheAjudaModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.util.ApiUtils;

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
	public RestModel<List<AjudaCampanhaModel>> ajudar(AjudaCampanhaModel m){
				
		try {
			Pessoa p = covidUserBean.getUser().getPessoa();
			
			DetalheAjuda da = null;
			
			DetalheAjudaModel dam = m.getDetalhe();
			if(dam!=null) {
				da = new DetalheAjuda();
				da.setTipo(dam.getTipo());
				da.setExecutor(dam.getExecutor());
				da.setDetalhe(dam.getDetalhe());
				da.setIdTransacao(dam.getIdTransacao());
				da.setStatusTransacao(dam.getStatusTransacao());
				da.setIdPagamento(dam.getIdPagamento());
				da.setStatusPagamento(dam.getStatusPagamento());
				da.setValorPagamento(dam.getValorPagamento());
				
				String dhp = dam.getDataHoraPagamento();
				if(dhp!=null)
					da.setDataHoraPagamento(ApiUtils.convertToDateTime(dhp));
			}
			
			TResult<Associado> res = assServ.ajudarCampanha(appBean.getToken(), p, 
					m.getIdCampanha(), m.getValor(), m.getPeriodo(), m.getIdFormaAjuda(), da);
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				
				cmcServ.processarMailing(appBean.getToken());
				
				Associado a = res.getValue();
				return processarAjudaCampanhas(a);
			
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
	public RestModel<List<AjudaCampanhaModel>> cancelar(@PathParam("id") Long id){
				
		try {
			TResult<Associado> res = assServ.cancelarAjuda(appBean.getToken(), covidUserBean.getUser().getPessoa(), id);
			if(res.getResult().equals(EnumResult.SUCESS)){
				Associado a = res.getValue();
				return processarAjudaCampanhas(a);
			
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
	

	@SuppressWarnings("unchecked")
	@GET
	@Path("/campanha/{id}")
	public RestModel<CampanhaModel> getCampanha(@PathParam("id") Long id){
		try {
			Campanha c = new Campanha();
			c.setId(id);
			
			TResult<Campanha> res = caServ.findById(appBean.getToken(), c);
			if(res.getResult().equals(EnumResult.SUCESS)){
				return new RestModel<>(new CampanhaModel(res.getValue()), "200", "");
			}else{
				return new RestModel<>(null, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@GET
	@Path("/campanha/associado")
	public RestModel<List<AjudaCampanhaModel>> getAjudaCampanha(){
		try {
			Pessoa p = covidUserBean.getUser().getPessoa();
			
			TResult<Associado> res = assServ.recuperar(appBean.getToken(), p);
			if(res.getResult().equals(EnumResult.SUCESS)){
				return this.processarAjudaCampanhas(res.getValue());
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

	public RestModel<List<AjudaCampanhaModel>> processarAjudaCampanhas(Associado a) {
		List<AjudaCampanhaModel> l = new ArrayList<>();
		if(a.getAjudaCampanhas()!=null)
			a.getAjudaCampanhas().forEach(e->{
				l.add(new AjudaCampanhaModel(e));
			});
		return new RestModel<>(l,"200","");
	}

}
