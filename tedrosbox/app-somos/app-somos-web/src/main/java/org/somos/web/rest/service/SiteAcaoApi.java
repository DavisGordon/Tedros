package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.somos.ejb.controller.IAcaoController;
import org.somos.ejb.controller.IPessoaController;
import org.somos.ejb.controller.ITipoAjudaController;
import org.somos.model.Acao;
import org.somos.model.Contato;
import org.somos.model.Pessoa;
import org.somos.web.rest.model.AcaoModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.util.ApiUtils;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;


public class SiteAcaoApi extends BaseApi{
	
	@EJB 
	private IAcaoController aServ;
	
	@EJB 
	private IPessoaController pServ;
	
	@EJB
	private ITipoAjudaController taServ;

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

}
