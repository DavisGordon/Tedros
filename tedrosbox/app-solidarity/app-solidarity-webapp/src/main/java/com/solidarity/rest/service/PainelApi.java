package com.solidarity.rest.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.solidarity.bean.CovidUserBean;
import com.solidarity.ejb.controller.IAcaoController;
import com.solidarity.ejb.controller.IAutUserController;
import com.solidarity.ejb.controller.IPessoaController;
import com.solidarity.ejb.controller.ITermoAdesaoController;
import com.solidarity.ejb.controller.ITipoAjudaController;
import com.solidarity.ejb.controller.IUFController;
import com.solidarity.ejb.controller.IVoluntarioController;
import com.solidarity.model.Acao;
import com.solidarity.model.Contato;
import com.solidarity.model.Documento;
import com.solidarity.model.Endereco;
import com.solidarity.model.Pessoa;
import com.solidarity.model.PessoaTermoAdesao;
import com.solidarity.model.TermoAdesao;
import com.solidarity.model.TipoAjuda;
import com.solidarity.model.UF;
import com.solidarity.model.Voluntario;
import com.solidarity.rest.model.AcaoModel;
import com.solidarity.rest.model.RestModel;
import com.solidarity.rest.model.TermoAdesaoModel;
import com.solidarity.rest.model.UserModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

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
	
	@EJB 
	private IUFController ufServ;
	
	@EJB 
	private ITermoAdesaoController tAdServ;
	
	/*@GET
	@Path("/")
	public RestModel<String> x(){
		
	}*/
	
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
			@FormParam("profissao") String  prof,
			@FormParam("identidade") String  ident,
			@FormParam("cpf") String  cpf,
			@FormParam("nacionalidade") String  nac,
			@FormParam("telefone") String  tel,
			@FormParam("email") String  email,
			@FormParam("estadoCivil") String  estCiv,
			@FormParam("sexo") String  sexo,
			@FormParam("tipoLogradouro") String  tpLog,
			@FormParam("logradouro") String  logr,
			@FormParam("bairro") String  bairro,
			@FormParam("complemento") String  compl,
			@FormParam("cidade") String  cidade,
			@FormParam("uf") Long  ufid,
			@FormParam("cep") String  cep
			){
	
		try{
			Pessoa p = covidUserBean.getUser().getPessoa();
			p.setNome(name);
			p.setProfissao(prof);
			p.setEstadoCivil(estCiv);
			p.setSexo(sexo);
			p.setLoginName(email);
			
			UF uf = null;
			if(ufid!=null) {
				uf = new UF();
				uf.setId(ufid);
				TResult res = ufServ.findById(uf);
				uf = (UF) res.getValue();
			}
			
			if(StringUtils.isNotBlank(tel)) {
				if(p.getContatos()==null || p.getContatos().isEmpty()) {
					Contato c = new Contato();
					c.setDescricao(tel);
					c.setTipo("2");
					if(p.getContatos()==null)
						p.setContatos(new HashSet<>());
					p.getContatos().add(c);
				}else if(p.getContatos()!=null && !p.getContatos().isEmpty()) {
					boolean f = false;
					for(Contato c : p.getContatos()) {
						if(c.getTipo().equals("2")) {
							c.setDescricao(tel);
							f = true;
						}
					}
					
					if(!f) {
						Contato c = new Contato();
						c.setDescricao(tel);
						c.setTipo("2");
						p.getContatos().add(c);
					}
				} 	
			}
			
			if(StringUtils.isNotBlank(ident) && StringUtils.isNotBlank(cpf) && StringUtils.isNotBlank(nac)) {
				if(p.getDocumentos()==null || p.getDocumentos().isEmpty()) {
					Documento i = new Documento();
					i.setNumero(ident);
					i.setTipo("1");
					i.setNacionalidade(nac);
					
					Documento c = new Documento();
					c.setNumero(cpf);
					c.setTipo("2");
					
					if(p.getDocumentos()==null)
						p.setDocumentos(new HashSet<>());
					
					p.getDocumentos().add(i);
					p.getDocumentos().add(c);
					
				}else if(p.getDocumentos()!=null && !p.getDocumentos().isEmpty()) {
					boolean i = false;
					boolean c = false;
					for(Documento d : p.getDocumentos()) {
						if(d.getTipo().equals("1")) {
							d.setNumero(ident);
							d.setNacionalidade(nac);
							i = true;
						} else if(d.getTipo().equals("2")) {
							d.setNumero(cpf);
							c = true;
						}
					}
					
					if(!i) {
						Documento d = new Documento();
						d.setNumero(ident);
						d.setTipo("1");
						d.setNacionalidade(nac);
						p.getDocumentos().add(d);
					}
					
					if(!c) {
						Documento d = new Documento();
						d.setNumero(cpf);
						d.setTipo("2");
						p.getDocumentos().add(d);
					}
				} 	
			}
			
			if(StringUtils.isNotBlank(tpLog) && StringUtils.isNotBlank(logr) 
					&& StringUtils.isNotBlank(compl) && StringUtils.isNotBlank(bairro) 
					&& StringUtils.isNotBlank(cep) && StringUtils.isNotBlank(cidade) 
					&& uf!=null) {
				if(p.getEnderecos()==null || p.getEnderecos().isEmpty()) {
					Endereco c = new Endereco();
					c.setTipo("1");
					c.setTipoLogradouro(tpLog);
					c.setLogradouro(logr);
					c.setComplemento(compl);
					c.setBairro(bairro);
					c.setCidade(cidade);
					c.setUf(uf);
					c.setCep(cep);
					if(p.getEnderecos()==null)
						p.setContatos(new HashSet<>());
					p.getEnderecos().add(c);
				}else if(p.getEnderecos()!=null && !p.getEnderecos().isEmpty()) {
					boolean f = false;
					for(Endereco c : p.getEnderecos()) {
						if(c.getTipo().equals("1")) {
							c.setTipoLogradouro(tpLog);
							c.setLogradouro(logr);
							c.setComplemento(compl);
							c.setBairro(bairro);
							c.setCidade(cidade);
							c.setUf(uf);
							c.setCep(cep);
							f = true;
						}
					}
					
					if(!f) {
						Endereco c = new Endereco();
						c.setTipo("1");
						c.setTipoLogradouro(tpLog);
						c.setLogradouro(logr);
						c.setComplemento(compl);
						c.setBairro(bairro);
						c.setCidade(cidade);
						c.setUf(uf);
						c.setCep(cep);
						p.getEnderecos().add(c);
					}
				} 	
			}
			
			TResult<Pessoa> res = pessServ.save(p);
			
			UserModel m = new UserModel(p.getId(), p.getNome(), p.getLoginName(), tel, p.getSexo(), p.getTipoVoluntario(), 
					p.getProfissao(), nac, p.getEstadoCivil(), ident, cpf, tpLog, logr, compl, bairro, cidade, cep, ufid);
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
			String ident = "";
			String cpf = "";
			String nac = "";
			String tpLogr = "";
			String logr = "";
			String compl = "";
			String bairro = "";
			String cidade = "";
			String cep = "";
			Long ufid = null;
			
			if(p.getContatos()!=null)
				for(Contato c : p.getContatos())
					if(c.getTipo().equals("2"))//celular
						telefone = c.getDescricao();
			
			if(p.getDocumentos()!=null) {
				for(Documento c : p.getDocumentos()) {
					if(c.getTipo().equals("1")) {//identidade
						ident = c.getNumero();
						nac = c.getNacionalidade();
					} else if(c.getTipo().equals("2")) {//cpf
						cpf = c.getNumero();
					}
				}
			}
			
			if(p.getEnderecos()!=null) {
				for(Endereco c : p.getEnderecos()) {
					if(c.getTipo().equals("1")) {//residencia
						tpLogr = c.getTipoLogradouro();
						logr = c.getLogradouro();
						compl = c.getComplemento();
						bairro = c.getBairro();
						cidade = c.getCidade();
						cep = c.getCep();
						ufid = c.getUf() != null ? c.getUf().getId() : null;
					}
				}
			}
			UserModel m = new UserModel(p.getId(), p.getNome(), p.getLoginName(), telefone, p.getSexo(), p.getTipoVoluntario(), 
					p.getProfissao(), nac, p.getEstadoCivil(), ident, cpf, tpLogr, logr, compl, bairro, cidade, cep, ufid);
			
			if(p.getTermosAdesao()!=null && !p.getTermosAdesao().isEmpty()) {
				for(PessoaTermoAdesao t : p.getTermosAdesao()) {
					if(t.getStatus().equals("ATIVADO")){
						TermoAdesaoModel a = new TermoAdesaoModel(t);
						m.setTermoAdesao(a);
					}	
				}
			}
			
			if(m.getTermoAdesao()==null) {
				TermoAdesao t = new TermoAdesao();
				t.setStatus("ATIVADO");
				
				TResult<TermoAdesao> r = tAdServ.find(t);
				if(r.getValue()!=null) {
					t = r.getValue();
					TermoAdesaoModel a = new TermoAdesaoModel(t);
					m.setTermoAdesao(a);
				}
			}
			
			return new RestModel<>(m, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", ERROR);
		}
	}
	
	@GET
	@Path("/ufs")
	public RestModel<List<UF>> listaEstadosUF(){
				
		try {
			TResult<List<UF>> res = ufServ.listAll(UF.class);
			System.out.println( "UFs recuperado com sucesso!");
			return new RestModel<>(res.getValue(), "200", "OK");
			
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
