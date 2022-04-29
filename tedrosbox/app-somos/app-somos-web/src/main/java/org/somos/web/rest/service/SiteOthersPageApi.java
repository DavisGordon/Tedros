package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.somos.ejb.controller.ICampanhaController;
import org.somos.ejb.controller.ISiteParceriaController;
import org.somos.ejb.controller.ISiteSMDoacaoController;
import org.somos.ejb.controller.ISiteVoluntarioController;
import org.somos.model.Campanha;
import org.somos.model.SiteParceria;
import org.somos.model.SiteSMDoacao;
import org.somos.model.SiteVoluntario;
import org.somos.web.rest.model.CampanhaModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.model.SiteModel;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;


public class SiteOthersPageApi extends SiteAcaoApi{
	
	@EJB
	private ISiteVoluntarioController volServ;
	
	@EJB
	private ISiteParceriaController parServ;
	
	@EJB
	private ISiteSMDoacaoController doaServ;
	
	@EJB
	private ICampanhaController caServ;
	
	@GET
	@Path("/campanhas")
	public RestModel<List<CampanhaModel>> getCampanhas(){
		try {
			TResult<List<Campanha>> res = caServ.listarValidos(appBean.getToken());
			List<CampanhaModel> l = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				
				if(res.getValue()!=null) 
					for(Campanha c : res.getValue())
						l.add(new CampanhaModel(c));
				return new RestModel<>(l, "200", "");
			}else{
				return new RestModel<>(l, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	
	@POST
	@Path("/ajudar")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<String> alterUser(@FormParam("emp") String  emp, 
			@FormParam("nome") String  nome,
			@FormParam("contato") String  contato,
			@FormParam("tipoAjuda") String  tpa,
			@FormParam("desc") String  desc,
			@FormParam("endereco") String  end){
		
		try {
			TResult<String> res = parServ.enviarEmail(appBean.getToken(), emp, nome, contato, tpa, desc, end);
			if(res.getResult().equals(EnumResult.SUCESS)){
				return new RestModel<>(null, "200", "Obrigado pelo interesse em ajudar em breve entraremos em contato!");
			}else{
				return new RestModel<>(null, "500", error.getValue() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue() );
		}
		
		
	}
	

	@SuppressWarnings("unchecked")
	@GET
	@Path("/doacoes")
	public RestModel<SiteModel> getDoacoes(){
		try {
			SiteSMDoacao ex = new SiteSMDoacao();
			ex.setStatus("ATIVADO");
			TResult<SiteSMDoacao> res = doaServ.find(appBean.getToken(), ex);
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
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/parceria")
	@SuppressWarnings("unchecked")
	public RestModel<SiteModel> getParceria(){
		try {
			SiteParceria ex = new SiteParceria();
			ex.setStatus("ATIVADO");
			TResult<SiteParceria> res = parServ.find(appBean.getToken(), ex);
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
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	@GET
	@Path("/voluntarios")
	@SuppressWarnings("unchecked")
	public RestModel<SiteModel> getVoluntarios(){
		try {
			SiteVoluntario ex = new SiteVoluntario();
			ex.setStatus("ATIVADO");
			TResult<SiteVoluntario> res = volServ.find(appBean.getToken(), ex);
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
			return new RestModel<>(null, "500", error.getValue());
		}
	}

}
