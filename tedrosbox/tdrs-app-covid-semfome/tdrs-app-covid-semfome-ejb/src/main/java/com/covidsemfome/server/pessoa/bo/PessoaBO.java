/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.pessoa.bo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.helper.TermoAdesaoHelper;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.PessoaTermoAdesao;
import com.covidsemfome.model.TermoAdesao;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.server.acao.bo.TermoAdesaoBO;
import com.covidsemfome.server.pessoa.eao.PessoaEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class PessoaBO extends TGenericBO<Pessoa> {

	@Inject
	private PessoaEAO eao;
	
	@Inject
	private TermoAdesaoBO termoAdesaoBO;
	
	
	@Override
	public ITGenericEAO<Pessoa> getEao() {
		return eao;
	}
	
	public String gerarTermoAdesao(Pessoa p, Set<TipoAjuda> lst, Date dataHoraAcao)throws Exception{
		String termo = null;
		
		PessoaTermoAdesao pta = p.getTermosAdesaoVigente();
		if(pta==null) {
			termo = saveTermo(p, lst, dataHoraAcao);
		}else {
			if(!pta.isTermoValido(lst)) {
				pta.setStatus("DESATIVADO");
				termo = saveTermo(p, lst, dataHoraAcao);
			}
		}
		
		return termo;
	}

	/**
	 * @param p
	 * @param lst
	 * @param dataHoraAcao
	 * @return
	 * @throws Exception
	 */
	private String saveTermo(Pessoa p, Set<TipoAjuda> lst, Date dataHoraAcao) throws Exception {
		String termo;
		PessoaTermoAdesao pta;
		pta = new PessoaTermoAdesao();
		pta.setPessoa(p);
		pta.setStatus("ATIVADO");
		pta.setTiposAjuda(lst);
		
		TermoAdesao ta = new TermoAdesao();
		ta.setStatus("ATIVADO");
		ta = this.termoAdesaoBO.find(ta);
		termo = ta.getConteudo();
		termo = TermoAdesaoHelper.replaceTokensTermo(p, lst, dataHoraAcao, termo);
		pta.setConteudo(termo);
		super.save(p);
		return termo;
	}
	
	public List<Pessoa> getListaEstrategicoEmail(){
		return eao.getListaEstrategicoEmail();
	}

	public String getEnderecoEstrategicoEmail(){
		String str = "";
		List<Pessoa> lst = this.getListaEstrategicoEmail();
		if(lst!=null){
			for (Pessoa p : lst) {
				String e = getEmail(p);
				if(e!=null)
					str += ((str.isEmpty()) ?"":",") + getEmail(p);
			}
		}
		return str;
	}
	
	public String getEmail(Pessoa p) {
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
	
	public Pessoa recuperar(String loginName, String password){
		return eao.recuperar(loginName, password);
	}
	
	public List<Pessoa> pesquisar(String nome, String tipo, String status, 
			Date dataInicio, Date dataFim, String orderBy, String orderType){
		return eao.pesquisar(nome, tipo, status, dataInicio, dataFim, orderBy, orderType);
	}
	
	public List<Pessoa> pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		return eao.pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
	}
	
	public boolean isLoginExiste(String email){
		return eao.isLoginExiste(email);
	}
	
	
}
