package com.covidsemfome.rest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.covidsemfome.ejb.service.IAcaoService;
import com.covidsemfome.ejb.service.IAutUserService;
import com.covidsemfome.ejb.service.IPessoaService;
import com.covidsemfome.ejb.service.IVoluntarioService;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.rest.model.AcaoModel;
import com.covidsemfome.rest.model.RestModel;
import com.covidsemfome.rest.model.UserModel;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

import br.com.covidsemfome.bean.CovidUserBean;
import br.com.covidsemfome.ejb.service.ServiceLocator;

/**
 * @author Davis Gordon
 *
 */
@Singleton

@Path("/painel")
@Produces(MediaType.APPLICATION_JSON)
public class PainelApi {
	
	private static String ERROR = "Desculpe estamos há resolver um problema tecnico em breve voltaremos.";

	
	@Inject @Any
	private CovidUserBean covidUserBean;
	
	@GET
	@Path("/logout")
	public RestModel<String> logout(){
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IAutUserService serv = loc.lookup("IAutUserServiceRemote");
			TResult<Boolean> res = serv.logout(covidUserBean.getUser().getPessoa());
			loc.close();
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println(covidUserBean.getUser().getPessoa().getNome() +" removido da sessao com sucesso");
				
				return new RestModel<String>("LOGOUT", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : ERROR );
			}
			
		} catch (NamingException e) {
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
			@FormParam("sexo") String  sexo,
			@FormParam("voluntario") String  voluntario){
	
		ServiceLocator loc =  ServiceLocator.getInstance();
		try{
			Pessoa p = covidUserBean.getUser().getPessoa();
			p.setNome(name);
			p.setSexo(sexo);
			p.setTipoVoluntario(voluntario);
			p.setLoginName(email);
			
			if(p.getContatos()!=null && tel!=null && !tel.isEmpty())
				for(Contato c : p.getContatos())
					if(c.getTipo().equals("2"))
						c.setDescricao(tel);
			
			IPessoaService serv = loc.lookup("IPessoaServiceRemote");
			TResult<Pessoa> res = serv.save(p);
			loc.close();
			
			
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
	
	
	
	@POST
	@Path("/acao/participar/{id}")
	public RestModel<List<AcaoModel>> participar(@PathParam("id") Long id){
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IVoluntarioService serv = loc.lookup("IVoluntarioServiceRemote");
			TResult<List<Acao>> res = serv.participarEmAcao(covidUserBean.getUser().getPessoa(), id);
			loc.close();
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
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IVoluntarioService serv = loc.lookup("IVoluntarioServiceRemote");
			TResult<List<Acao>> res = serv.sairDaAcao(covidUserBean.getUser().getPessoa(), id);
			loc.close();
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
		
		ServiceLocator loc =  ServiceLocator.getInstance();
		
		try {
			IAcaoService serv = loc.lookup("IAcaoServiceRemote");
			TResult<List<Acao>> res = serv.listAcoesParaExibirNoPainel();
			loc.close();
			
			return processarAcoes(res);
			
		} catch (NamingException e) {
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
							
					if(acao.getVoluntarios()!=null)
						for(Voluntario v : acao.getVoluntarios())
							if(v.getPessoa().getId().equals(pessoa.getId())){
								inscrito = true;
								break;
							}
					
					AcaoModel model = new AcaoModel(acao.getId(), acao.getTitulo(), acao.getDescricao(), 
							formataDataHora(acao.getData()), acao.getStatus(), acao.getObservacao(), 
							acao.getQtdMinVoluntarios(), acao.getQtdMaxVoluntarios(), qtdVolIns, 
							inscrito);
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

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);

		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		return df.format(data);
	}
	
}
