package org.somos.web.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.somos.ejb.controller.IAcaoController;
import org.somos.ejb.controller.IVoluntarioController;
import org.somos.model.Acao;
import org.somos.model.Pessoa;
import org.somos.model.TipoAjuda;
import org.somos.model.Voluntario;
import org.somos.web.rest.model.AcaoModel;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.util.ApiUtils;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
public class PainelAcoesApi extends PainelPessoaApi{
	
	@EJB 
	private IVoluntarioController volServ;

	@EJB 
	private IAcaoController aServ;
	
	@POST
	@Path("/acao/participar/")
	public RestModel<List<AcaoModel>> participar(@FormParam("id") Long id, @FormParam("tiposAjuda") List<Long> tiposAjuda){
				
		try {
			TResult<List<Acao>> res = volServ.participarEmAcao(covidUserBean.getUser().getPessoa(), id, tiposAjuda);
			System.out.println(covidUserBean.getUser().getPessoa().getNome()+ " participando com sucesso a acao "+id.toString());
			return processarAcoes(res);
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@DELETE
	@Path("/acao/sair/{id}")
	public RestModel<List<AcaoModel>> sairAcao(@PathParam("id") Long id){
				
		try {
			TResult<List<Acao>> res = volServ.sairDaAcao(covidUserBean.getUser().getPessoa(), id);
			System.out.println(covidUserBean.getUser().getPessoa().getNome()+ " saiu com sucesso da acao "+id.toString());
			return processarAcoes(res);
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
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
			Pessoa pessoa = covidUserBean.getUser().getPessoa();
			
			String s = pessoa.getStatusVoluntario();
			if((s.equals("2") || s.equals("3") || s.equals("4")) 
					&& lst!=null && !lst.isEmpty())
				for (Acao acao : lst) {
					boolean inscrito = false;
					Integer qtdVolIns = acao.getVoluntarios()!=null 
							? acao.getVoluntarios().size() 
									: 0;
					List<TipoAjuda> lst2 = new ArrayList<>();
					if(acao.getVoluntarios()!=null)
						for(Voluntario v : acao.getVoluntarios())
							if(v.getPessoa().getId().equals(pessoa.getId())){
								inscrito = true;
								for(TipoAjuda t : v.getTiposAjuda())
									if(t.getStatus().equals("ATIVADO"))
										lst2.add(t);
								break;
							}
					
					List<TipoAjuda> lst3 = new ArrayList<>();
					List<TipoAjuda> lst4 = new ArrayList<>();
					if(acao.getTiposAjuda()!=null) {
						for(TipoAjuda t : acao.getTiposAjuda()) {
							if(t.getStatus().equals("ATIVADO") && t.getTipoPessoa().equals("PF"))
								lst3.add(t);
							if(t.getStatus().equals("ATIVADO") && t.getTipoPessoa().equals("PJ"))
								lst4.add(t);
						}
						if(lst3.isEmpty())
							lst3 = null;
						if(lst4.isEmpty())
							lst4 = null;
					}
					AcaoModel model = new AcaoModel(acao.getId(), acao.getTitulo(), acao.getDescricao(), 
							ApiUtils.formatDateHourToView(acao.getData()), acao.getStatus(), acao.getObservacao(), 
							acao.getQtdMinVoluntarios(), acao.getQtdMaxVoluntarios(), qtdVolIns, 
							inscrito, lst2, lst3, lst4);
					models.add(model);
					
				}
			System.out.println("Acoes processadas");
			return new RestModel<>(models, "200", res.getMessage());
		}else if(res.getResult().equals(EnumResult.WARNING)){
			return new RestModel<>(null, "404", res.getMessage());
		}else {
			return new RestModel<>(null, "500", error.getValue() );

		}
	}
	
	
}
