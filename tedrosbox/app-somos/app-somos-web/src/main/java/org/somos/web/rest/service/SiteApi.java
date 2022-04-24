package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;

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

import org.apache.commons.lang3.math.NumberUtils;
import org.somos.ejb.controller.IAcaoController;
import org.somos.ejb.controller.ICampanhaController;
import org.somos.ejb.controller.IPessoaController;
import org.somos.ejb.controller.ISiteAboutController;
import org.somos.ejb.controller.ISiteContatoController;
import org.somos.ejb.controller.ISiteEquipeController;
import org.somos.ejb.controller.ISiteGaleriaController;
import org.somos.ejb.controller.ISiteMidiaSocialController;
import org.somos.ejb.controller.ISiteNoticiaController;
import org.somos.ejb.controller.ISiteParceriaController;
import org.somos.ejb.controller.ISiteSMDoacaoController;
import org.somos.ejb.controller.ISiteVideoController;
import org.somos.ejb.controller.ISiteVoluntarioController;
import org.somos.ejb.controller.ITipoAjudaController;
import org.somos.model.Acao;
import org.somos.model.Campanha;
import org.somos.model.Contato;
import org.somos.model.Pessoa;
import org.somos.model.SiteAbout;
import org.somos.model.SiteContato;
import org.somos.model.SiteEquipe;
import org.somos.model.SiteGaleria;
import org.somos.model.SiteMidiaSocial;
import org.somos.model.SiteNoticia;
import org.somos.model.SiteParceria;
import org.somos.model.SiteSMDoacao;
import org.somos.model.SiteVideo;
import org.somos.model.SiteVoluntario;
import org.somos.model.TipoAjuda;
import org.somos.web.bean.AppBean;
import org.somos.web.producer.Item;
import org.somos.web.rest.model.AcaoModel;
import org.somos.web.rest.model.CampanhaModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.model.SiteModel;
import org.somos.web.rest.util.ApiUtils;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;


public class SiteApi extends BaseApi{
	
	@EJB
	private ISiteVoluntarioController volServ;
	
	@EJB
	private ISiteParceriaController parServ;
	
	@EJB
	private ISiteSMDoacaoController doaServ;
	
	@EJB
	private ISiteNoticiaController newServ;
	
	@EJB
	private ISiteVideoController vidServ;
	
	@EJB
	private ISiteContatoController conServ;
	
	@EJB
	private ISiteAboutController aboServ;
	
	@EJB 
	private IPessoaController pServ;
	
	@EJB
	private ITipoAjudaController taServ;

	@EJB
	private ISiteEquipeController eqServ;

	@EJB
	private ISiteMidiaSocialController msServ;

	@EJB
	private ISiteGaleriaController gaServ;
	
	@EJB
	private ICampanhaController caServ;
	
	@GET
	@Path("/campanhas")
	public RestModel<List<CampanhaModel>> getCampanhas(){
		try {
			Campanha ex = new Campanha();
			ex.setStatus("ATIVADO");
			TResult<List<Campanha>> res = caServ.findAll(appBean.getToken(), ex);
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
	
	
	@GET
	@Path("/tiposAjuda")
	public RestModel<List<String>> listaTiposAjuda(){
				
		try {
			TResult<List<TipoAjuda>> res = taServ.listar("PJ");
			List<String> l = new ArrayList<>();
			if(res.getValue()!=null) {
				for(TipoAjuda t : res.getValue())
					l.add(t.getDescricao());
				l.sort((a,b)->{
					return a.compareToIgnoreCase(b);
				});
			}
			return new RestModel<>(l, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	/**
	 * @param email
	 * @param f
	 * @return
	 */
	private boolean validarEmail(String email, boolean f) {
		Pessoa p = new Pessoa();
		p.setTipoVoluntario("3");
		TResult<List<Pessoa>> pr = pServ.findAll(appBean.getToken(), p);
		List<Pessoa> l = pr.getValue();
		for(Pessoa e : l) {
			String em = this.getEmail(e);
			if(em!=null && email.equals(em)) {
				f = true;
				break;
			}
		}
		return f;
	}
	
	private String getEmail(Pessoa p) {
		if(p.getLoginName()!=null){
			 return p.getLoginName();
		}else{
			if(p.getContatos()!= null){
				for(Contato c : p.getContatos()){
					if(c.getTipo().equals("1")){
						return c.getDescricao();
					}
				}
			}
		}
		return null;
	}

	@GET
	@Path("/about")
	@SuppressWarnings("unchecked")
	public RestModel<SiteModel> getAbout(){
		try {
			SiteAbout ex = new SiteAbout();
			ex.setStatus("ATIVADO");
			TResult<SiteAbout> res = aboServ.find(appBean.getToken(), ex);
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
	@Path("/galeria/{tipo}")
	public RestModel<List<SiteModel>> getGaleria(@PathParam("tipo") String tipo){
		try {
			String t = null;
			if(tipo!=null && tipo.equals("p"))
				t = "Parceiro";
			if(tipo!=null && tipo.equals("m"))
				t = "Mural";
			
			if(t==null)
				return new RestModel<>(new ArrayList<>(), "404","" );
			
			SiteGaleria ex = new SiteGaleria();
			ex.setStatus("ATIVADO");
			ex.setTipo(t);
			TResult<List<SiteGaleria>> res = gaServ.findAll(appBean.getToken(), ex);
			List<SiteModel> lst = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				if(res.getValue()!=null) {
					res.getValue().sort((a, b) -> {
						int x = (a.getOrdem()!=null) ? a.getOrdem() : 0;
						int y = (b.getOrdem()!=null) ? b.getOrdem() : 0;
						return NumberUtils.compare(x, y);
					});
					res.getValue().forEach(e->{
						lst.add(new SiteModel(e));
					});
				}
				return new RestModel<>(lst, "200", "");
			}else{
				return new RestModel<>(lst, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/equipe")
	public RestModel<List<SiteModel>> getEquipe(){
		try {
			SiteEquipe ex = new SiteEquipe();
			ex.setStatus("ATIVADO");
			TResult<List<SiteEquipe>> res = eqServ.findAll(appBean.getToken(), ex);
			List<SiteModel> lst = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				if(res.getValue()!=null) {
					res.getValue().sort((a, b) -> {
						int x = (a.getOrdem()!=null) ? a.getOrdem() : 0;
						int y = (b.getOrdem()!=null) ? b.getOrdem() : 0;
						return NumberUtils.compare(x, y);
					});
					res.getValue().forEach(e->{
						lst.add(new SiteModel(e));
					});
				}
				return new RestModel<>(lst, "200", "");
			}else{
				return new RestModel<>(lst, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/siganos")
	public RestModel<List<SiteModel>> getSiganos(){
		try {
			SiteMidiaSocial ex = new SiteMidiaSocial();
			ex.setStatus("ATIVADO");
			TResult<List<SiteMidiaSocial>> res = msServ.findAll(appBean.getToken(), ex);
			List<SiteModel> lst = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				if(res.getValue()!=null) {
					res.getValue().sort((a, b) -> {
						int x = (a.getOrdem()!=null) ? a.getOrdem() : 0;
						int y = (b.getOrdem()!=null) ? b.getOrdem() : 0;
						return NumberUtils.compare(x, y);
					});
					res.getValue().forEach(e->{
						lst.add(new SiteModel(e));
					});
				}
				return new RestModel<>(lst, "200", "");
			}else{
				return new RestModel<>(lst, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	
	@GET
	@Path("/contatos")
	public RestModel<List<SiteModel>> getContatos(){
		try {
			SiteContato ex = new SiteContato();
			ex.setStatus("ATIVADO");
			TResult<List<SiteContato>> res = conServ.findAll(appBean.getToken(), ex);
			List<SiteModel> lst = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				if(res.getValue()!=null) {
					res.getValue().sort((a, b) -> {
						int x = (a.getOrdem()!=null) ? a.getOrdem() : 0;
						int y = (b.getOrdem()!=null) ? b.getOrdem() : 0;
						return NumberUtils.compare(x, y);
					});
					res.getValue().forEach(e->{
						lst.add(new SiteModel(e));
					});
				}
				return new RestModel<>(lst, "200", "");
			}else{
				return new RestModel<>(lst, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/videos")
	public RestModel<List<SiteModel>> getVideos(){
		try {
			SiteVideo ex = new SiteVideo();
			ex.setStatus("ATIVADO");
			TResult<List<SiteVideo>> res = vidServ.findAll(appBean.getToken(), ex);
			List<SiteModel> lst = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				if(res.getValue()!=null) {
					res.getValue().sort((a, b) -> {
						int x = (a.getOrdem()!=null) ? a.getOrdem() : 0;
						int y = (b.getOrdem()!=null) ? b.getOrdem() : 0;
						return NumberUtils.compare(x, y);
					});
					res.getValue().forEach(e->{
						lst.add(new SiteModel(e));
					});
				}
				return new RestModel<>(lst, "200", "");
			}else{
				return new RestModel<>(lst, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@GET
	@Path("/news")
	public RestModel<List<SiteModel>> getNews(){
		try {
			SiteNoticia ex = new SiteNoticia();
			ex.setStatus("ATIVADO");
			TResult<List<SiteNoticia>> res = newServ.findAll(appBean.getToken(), ex);
			List<SiteModel> lst = new ArrayList<>();
			if(res.getResult().equals(EnumResult.SUCESS)){
				if(res.getValue()!=null) {
					res.getValue().sort((a, b) -> {
						int x = (a.getOrdem()!=null) ? a.getOrdem() : 0;
						int y = (b.getOrdem()!=null) ? b.getOrdem() : 0;
						return NumberUtils.compare(x, y);
					});
					res.getValue().forEach(e->{
						lst.add(new SiteModel(e));
					});
				}
				return new RestModel<>(lst, "200", "");
			}else{
				return new RestModel<>(lst, "404", res.getResult().equals(EnumResult.WARNING) 
						? res.getMessage()  
								: error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
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
