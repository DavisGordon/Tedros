package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.math.NumberUtils;
import org.somos.ejb.controller.IAcaoController;
import org.somos.ejb.controller.IPessoaController;
import org.somos.ejb.controller.ISiteAboutController;
import org.somos.ejb.controller.ISiteContatoController;
import org.somos.ejb.controller.ISiteDoacaoController;
import org.somos.ejb.controller.ISiteNoticiaController;
import org.somos.ejb.controller.ISiteVideoController;
import org.somos.model.Acao;
import org.somos.model.Contato;
import org.somos.model.Pessoa;
import org.somos.model.SiteAbout;
import org.somos.model.SiteContato;
import org.somos.model.SiteDoacao;
import org.somos.model.SiteNoticia;
import org.somos.model.SiteVideo;
import org.somos.web.bean.AppBean;
import org.somos.web.producer.Item;
import org.somos.web.rest.model.AcaoModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.model.SiteModel;
import org.somos.web.rest.util.ApiUtils;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Path("/sm")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class SiteApi {
	
	@Inject
	@Named("errorMsg")
	private Item<String> error;
	
	@Inject
	private AppBean appBean;
	
	@EJB
	private ISiteDoacaoController doaServ;
	
	@EJB
	private ISiteNoticiaController newServ;
	
	@EJB
	private ISiteVideoController vidServ;
	
	@EJB
	private ISiteContatoController conServ;
	
	@EJB
	private ISiteAboutController aboServ;
	
	@EJB 
	private IAcaoController aServ;
	
	@EJB 
	private IPessoaController pServ;
	
	@GET
	@Path("/acao/prog/{idx}/{email}")
	public String setDecisaoProgramadas(
			@PathParam("idx")Integer idx,
			@PathParam("email")String email){
		
		
		try {
			if(idx==null || email==null)
				return "Dados incorretos.";
			
			boolean f = false;
			f = validarEmail(email, f);
			
			if(!f)
				return "Você não esta autorizado para esta operacão!";
			
			TResult<List<Acao>> res = aServ.listAcoesProgramadasParaDecisao();
			List<Acao> l = res.getValue();
			if(l!=null) {
				for(Acao e : l) {
					alterarStatus(idx, e);
				}
				return "Status alterado, obrigado!";
			}
			
			return "Esta acão ja foi alterada!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Ocorreu um erro e não foi possivel realizar a operacão!";
		}
	}
				
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/acao/{id}/prog/{idx}/{email}")
	public String setDecisaoProgramadas(@PathParam("id") Long id, 
			@PathParam("idx")Integer idx,
			@PathParam("email")String email){
				
		try {
			if(id==null || idx==null)
				return "Dados incorretos.";
			boolean f = false;
			f = validarEmail(email, f);
			
			if(!f)
				return "Você não esta autorizado para esta operacão!";
			
			Acao ex = new Acao();
			ex.setId(id);
			TResult<Acao> res = aServ.findById(appBean.getToken(), ex);
			ex = res.getValue();
			if(alterarStatus(idx, ex)) {
				return "Status alterado, obrigado!";
			}
			
			return "Esta acão ja foi alterada!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Ocorreu um erro e não foi possivel realizar a operacão!";
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

	/**
	 * @param idx
	 * @param ex
	 */
	private boolean alterarStatus(Integer idx, Acao ex) {
		if(ex.getStatus().equals("Programada") && (idx>=1 && idx<=3)) {
			switch(idx) {
			case 1: ex.setStatus("Agendada"); break;
			case 2: ex.setStatus("Cancelada"); break;
			case 3: ex.setStatus("Executada"); break;
			}
			aServ.save(appBean.getToken(), ex);
			return true;
		}
		return false;
	}
	
	@GET
	@Path("/acoes")
	public RestModel<List<AcaoModel>> getAcoes(){
				
		try {
			TResult<List<Acao>> res = aServ.listAcoesParaExibirNoPainel();
			
			return processarAcoes(res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	

	private RestModel<List<AcaoModel>> processarAcoes(TResult<List<Acao>> res) {
		if(res.getResult().equals(EnumResult.SUCESS)){
			
			List<AcaoModel> models = new ArrayList<>();
			List<Acao> lst = res.getValue();
			if(lst!=null && !lst.isEmpty())
				for (Acao acao : lst) {
					Integer qtdVolIns = acao.getVoluntarios()!=null 
							? acao.getVoluntarios().size() 
									: 0;
					
					
					AcaoModel model = new AcaoModel(null, acao.getTitulo(), acao.getDescricao(), 
							ApiUtils.formatDateHourToView(acao.getData()), acao.getStatus(), acao.getObservacao(), 
							acao.getQtdMinVoluntarios(), acao.getQtdMaxVoluntarios(), qtdVolIns, 
							false, null, null, null);
					models.add(model);
					
				}
			return new RestModel<>(models, "200", res.getMessage());
		}else{
			return new RestModel<>(null, "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : error.getValue() );
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/about")
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
	public RestModel<List<SiteModel>> getDoacoes(){
		try {
			SiteDoacao ex = new SiteDoacao();
			ex.setStatus("ATIVADO");
			TResult<List<SiteDoacao>> res = doaServ.findAll(appBean.getToken(), ex);
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
	
	

}
