package org.somos.web.rest.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.somos.ejb.controller.IPessoaController;
import org.somos.ejb.controller.ITermoAdesaoController;
import org.somos.ejb.controller.ITipoAjudaController;
import org.somos.ejb.controller.IUFController;
import org.somos.model.Contato;
import org.somos.model.Documento;
import org.somos.model.Endereco;
import org.somos.model.Pessoa;
import org.somos.model.PessoaTermoAdesao;
import org.somos.model.TermoAdesao;
import org.somos.model.TipoAjuda;
import org.somos.model.UF;
import org.somos.web.rest.model.RestModel;
import org.somos.web.rest.model.TermoAdesaoModel;
import org.somos.web.rest.model.UserModel;
import org.somos.web.rest.util.ApiUtils;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
public class PainelPessoaApi extends LoggedUserBaseApi{
	

	@EJB 
	private IUFController ufServ;
	
	@EJB
	private IPessoaController pessServ;

	@EJB
	private ITipoAjudaController taServ;
	
	@EJB 
	private ITermoAdesaoController tAdServ;
	
	
	@POST
	@Path("/user/alter")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<UserModel> alterUser(@FormParam("name") String  name, 
			@FormParam("profissao") String  prof,
			@FormParam("identidade") String  ident,
			@FormParam("cpf") String  cpf,
			@FormParam("dtNasc") String  dtNasc,
			@FormParam("nacionalidade") String  nac,
			@FormParam("telefone") String  tel,
			@FormParam("email") String  email,
			@FormParam("estCiv") String  estCiv,
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
			
			if(!email.equals(p.getLoginName())) {
				TResult<Boolean> r = pessServ.isLoginInUse(appBean.getToken(), email);
				if(!r.getResult().equals(EnumResult.SUCESS))
					return new RestModel<>(null, "500", error.getValue());
				
				if(r.getValue())
					return new RestModel<>(null, "404", "O email informado já encontra-se em uso!" );

				p.setLoginName(email);
			}
			
			Date dt = ApiUtils.convertToDate(dtNasc);
			p.setDataNascimento(dt);
				
			UF uf = null;
			if(ufid!=null) {
				uf = new UF();
				uf.setId(ufid);
				TResult res = ufServ.findById(appBean.getToken(), uf);
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
			
			TResult<Pessoa> res = pessServ.save(appBean.getToken(), p);
			
			UserModel m = new UserModel(p.getId(), p.getNome(), p.getLoginName(), tel, p.getSexo(), p.getTipoVoluntario(), 
					p.getDataNascimento(), p.getProfissao(), nac, p.getEstadoCivil(), ident, cpf, tpLog, logr, compl, bairro, cidade, cep, ufid, p.getStatusVoluntario());
			return new RestModel<>(m, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
		
	}
	
	@SuppressWarnings("unchecked")
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
					p.getDataNascimento(), p.getProfissao(), nac, p.getEstadoCivil(), ident, cpf, tpLogr, logr, compl, bairro, cidade, cep, ufid, p.getStatusVoluntario());
			
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
				
				TResult<TermoAdesao> r = tAdServ.find(appBean.getToken(), t);
				if(r.getValue()!=null) {
					t = r.getValue();
					TermoAdesaoModel a = new TermoAdesaoModel(t);
					m.setTermoAdesao(a);
				}
			}
			
			return new RestModel<>(m, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/ufs")
	public RestModel<List<UF>> listaEstadosUF(){
				
		try {
			TResult<List<UF>> res = ufServ.listAll(appBean.getToken(), UF.class);
			System.out.println( "UFs recuperado com sucesso!");
			return new RestModel<>(res.getValue(), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	@GET
	@Path("/tiposAjuda/{tipo}")
	public RestModel<List<TipoAjuda>> listaTiposAjuda(@PathParam("tipo") String tipo){
				
		try {
			TResult<List<TipoAjuda>> res = taServ.listar(tipo);
			return new RestModel<>(res.getValue(), "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	
}
