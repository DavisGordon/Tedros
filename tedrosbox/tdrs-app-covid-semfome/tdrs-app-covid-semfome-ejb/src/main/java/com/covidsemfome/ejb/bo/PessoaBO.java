/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.PessoaEAO;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
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
	
	
	@Override
	public ITGenericEAO<Pessoa> getEao() {
		return eao;
	}
	
	public String estrategicoEmail(){
		String str = "";
		List<Pessoa> lst = eao.estrategicoEmail();
		if(lst!=null){
			for (Pessoa p : lst) {
				if(p.getLoginName()!=null){
					str += ((str.isEmpty()) ?"":",") + p.getLoginName();
				}else{
					if(p.getContatos()!= null){
						for(Contato c : p.getContatos()){
							if(c.getTipo().equals("1")){
								str += ((str.isEmpty()) ?"":",") + c.getDescricao();
								break;
							}
						}
					}
				}
			}
		}
		return str;
	}
	
	public Pessoa recuperar(String loginName, String password){
		return eao.recuperar(loginName, password);
	}
	
	public List<Pessoa> pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		return eao.pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
	}
	
	public boolean isLoginExiste(String email){
		return eao.isLoginExiste(email);
	}
	
	
}
