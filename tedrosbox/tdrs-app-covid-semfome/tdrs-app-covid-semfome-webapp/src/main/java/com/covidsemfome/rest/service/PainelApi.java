package com.covidsemfome.rest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.covidsemfome.ejb.controller.IAcaoController;
import com.covidsemfome.ejb.controller.IAutUserController;
import com.covidsemfome.ejb.controller.IPessoaController;
import com.covidsemfome.ejb.controller.ITipoAjudaController;
import com.covidsemfome.ejb.controller.IVoluntarioController;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.rest.model.AcaoModel;
import com.covidsemfome.rest.model.RestModel;
import com.covidsemfome.rest.model.UserModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

import br.com.covidsemfome.bean.CovidUserBean;
import br.com.covidsemfome.ejb.service.ServiceLocator;

/**
 * @author Davis Gordon
 *
 */
@Singleton

@Path("/painel")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class PainelApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema tecnico em breve voltaremos.";

	
	@Inject @Any
	private CovidUserBean covidUserBean;

	@EJB
	private IPessoaController pessServ;
	
	@EJB
	private IAutUserController autServ;
	
	@EJB
	private ITipoAjudaController taServ;
	
	@EJB 
	private IVoluntarioController volServ;

	@EJB 
	private IAcaoController aServ;
	
	@GET
	@Path("/logout")
	public RestModel<String> logout(){
		
		try {
			TResult<Boolean> res = autServ.logout(covidUserBean.getUser().getPessoa());
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println(covidUserBean.getUser().getPessoa().getNome() +" removido da sessao com sucesso");
				
				return new RestModel<String>("LOGOUT", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", ERROR);
		}
	}
	
	@POST
	@Path("/user/alter")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<UserModel> alterUser(@FormParam("name") String  name, 
			@FormParam("telefone") String  tel,
			@FormParam("email") String  email,
			@FormParam("sexo") String  sexo){
	
		try{
			Pessoa p = covidUserBean.getUser().getPessoa();
			p.setNome(name);
			p.setSexo(sexo);
			p.setLoginName(email);
			
			if(p.getContatos()!=null && tel!=null && !tel.isEmpty())
				for(Contato c : p.getContatos())
					if(c.getTipo().equals("2"))
						c.setDescricao(tel);
			
			TResult<Pessoa> res = pessServ.save(p);
			
			UserModel m = new UserModel(p.getId(), p.getNome(), p.getLoginName(), tel, p.getSexo(), p.getTipoVoluntario());
			return new RestModel<>(m, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
		
	}
	
	@GET
	@Path("/user")
	public RestModel<UserModel> getUser(){
		
		try{
			Pessoa p = covidUserBean.getUser().getPessoa();
			
			String telefone = "";
			
			if(p.getContatos()!=null)
				for(Contato c : p.getContatos())
					if(c.getTipo().equals("2"))//celular
						telefone = c.getDescricao();
			
			UserModel m = new UserModel(p.getId(), p.getNome(), p.getLoginName(), telefone, p.getSexo(), p.getTipoVoluntario());
			return new RestModel<>(m, "200", "OK");
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
	}
	
	@GET
	@Path("/tiposAjuda/{tipo}")
	public RestModel<List<TipoAjuda>> listaTiposAjuda(@PathParam("tipo") String tipo){
				
		try {
			TResult<List<TipoAjuda>> res = taServ.listar(tipo);
			System.out.println( "Tipo ajuda para "+tipo+" recuperado com sucesso!");
			return new RestModel<>(res.getValue(), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
	}
	
	@POST
	@Path("/acao/participar/")
	public RestModel<List<AcaoModel>> participar(@FormParam("id") Long id, @FormParam("tiposAjuda") List<Long> tiposAjuda){
				
		try {
			TResult<List<Acao>> res = volServ.participarEmAcao(covidUserBean.getUser().getPessoa(), id, tiposAjuda);
			System.out.println(covidUserBean.getUser().getPessoa().getNome()+ " participando com sucesso a acao "+id.toString());
			return processarAcoes(res);
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
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
			return new RestModel<>(null, "500", ERROR);
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
			return new RestModel<>(null, "500", ERROR);
		}
	}

	private RestModel<List<AcaoModel>> processarAcoes(TResult<List<Acao>> res) {
		if(res.getResult().equals(EnumResult.SUCESS)){
			
			List<AcaoModel> models = new ArrayList<>();
			List<Acao> lst = res.getValue();
			Pessoa pessoa = covidUserBean.getUser().getPessoa();
			if(lst!=null && !lst.isEmpty())
				for (Acao acao : lst) {
					boolean inscrito = false;
					String data;
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
					
					AcaoModel model = new AcaoModel(acao.getId(), acao.getTitulo(), acao.getDescricao(), 
							formataDataHora(acao.getData()), acao.getStatus(), acao.getObservacao(), 
							acao.getQtdMinVoluntarios(), acao.getQtdMaxVoluntarios(), qtdVolIns, 
							inscrito, lst2);
					models.add(model);
					
				}
			System.out.println("Acoes processadas");
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
	
}
