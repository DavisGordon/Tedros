package com.covidsemfome.rest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.math.NumberUtils;

import com.covidsemfome.ejb.controller.IAcaoController;
import com.covidsemfome.ejb.controller.IPessoaController;
import com.covidsemfome.ejb.controller.ISiteAboutController;
import com.covidsemfome.ejb.controller.ISiteContatoController;
import com.covidsemfome.ejb.controller.ISiteDoacaoController;
import com.covidsemfome.ejb.controller.ISiteNoticiaController;
import com.covidsemfome.ejb.controller.ISiteVideoController;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.SiteAbout;
import com.covidsemfome.model.SiteContato;
import com.covidsemfome.model.SiteDoacao;
import com.covidsemfome.model.SiteNoticia;
import com.covidsemfome.model.SiteVideo;
import com.covidsemfome.rest.model.AcaoModel;
import com.covidsemfome.rest.model.RestModel;
import com.covidsemfome.rest.model.SiteModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Path("/csf")
@Produces(MediaType.APPLICATION_JSON)
public class SiteApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema técnico e em breve voltaremos.";
	
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
			TResult<Acao> res = aServ.findById(ex);
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
		TResult<List<Pessoa>> pr = pServ.findAll(p);
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
			aServ.save(ex);
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
			return new RestModel<>(null, "500", ERROR);
		}
	}
	

	private RestModel<List<AcaoModel>> processarAcoes(TResult<List<Acao>> res) {
		if(res.getResult().equals(EnumResult.SUCESS)){
			
			List<AcaoModel> models = new ArrayList<>();
			List<Acao> lst = res.getValue();
			if(lst!=null && !lst.isEmpty())
				for (Acao acao : lst) {
					String data;
					Integer qtdVolIns = acao.getVoluntarios()!=null 
							? acao.getVoluntarios().size() 
									: 0;
					
					
					AcaoModel model = new AcaoModel(null, acao.getTitulo(), acao.getDescricao(), 
							formataDataHora(acao.getData()), acao.getStatus(), acao.getObservacao(), 
							acao.getQtdMinVoluntarios(), acao.getQtdMaxVoluntarios(), qtdVolIns, 
							false, null);
					models.add(model);
					
				}
			return new RestModel<>(models, "200", res.getMessage());
		}else{
			//System.out.println(res.getErrorMessage());
			return new RestModel<>(null, "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
		}
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	@GET
	@Path("/about")
	public RestModel<SiteModel> getAbout(){
		try {
			SiteAbout ex = new SiteAbout();
			ex.setStatus("ATIVADO");
			TResult<SiteAbout> res = aboServ.find(ex);
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
			return new RestModel<>(null, "500", ERROR);
		}
		
	}
	
	@GET
	@Path("/contatos")
	public RestModel<List<SiteModel>> getContatos(){
		try {
			SiteContato ex = new SiteContato();
			ex.setStatus("ATIVADO");
			TResult<List<SiteContato>> res = conServ.findAll(ex);
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
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
		
	}
	
	@GET
	@Path("/videos")
	public RestModel<List<SiteModel>> getVideos(){
		try {
			SiteVideo ex = new SiteVideo();
			ex.setStatus("ATIVADO");
			TResult<List<SiteVideo>> res = vidServ.findAll(ex);
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
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
		
	}
	
	@GET
	@Path("/news")
	public RestModel<List<SiteModel>> getNews(){
		try {
			SiteNoticia ex = new SiteNoticia();
			ex.setStatus("ATIVADO");
			TResult<List<SiteNoticia>> res = newServ.findAll(ex);
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
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
		
	}
	
	@GET
	@Path("/doacoes")
	public RestModel<List<SiteModel>> getDoacoes(){
		try {
			SiteDoacao ex = new SiteDoacao();
			ex.setStatus("ATIVADO");
			TResult<List<SiteDoacao>> res = doaServ.findAll(ex);
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
								: ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
		
	}
	
	

}
