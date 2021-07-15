/**
 * 
 */
package com.solidarity.rest.model;

import java.util.ArrayList;
import java.util.List;

import com.solidarity.model.PessoaTermoAdesao;
import com.solidarity.model.TermoAdesao;
import com.solidarity.model.TipoAjuda;

/**
 * @author Davis Gordon
 *
 */
public class TermoAdesaoModel {

	private String conteudo;
	
	private List<Long> tiposAjuda;
	
	
	/**
	 * 
	 */
	public TermoAdesaoModel(PessoaTermoAdesao t) {
		this.conteudo = t.getConteudo();
		this.tiposAjuda = new ArrayList<>();
		for(TipoAjuda e : t.getTiposAjuda())
			if(e.getTipoPessoa().equals("PF"))
				this.tiposAjuda.add(e.getId());
	}


	public TermoAdesaoModel(TermoAdesao t) {
		this.conteudo = t.getConteudo();
	}
	
	/**
	 * @return the conteudo
	 */
	public String getConteudo() {
		return conteudo;
	}


	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}


	/**
	 * @return the tiposAjuda
	 */
	public List<Long> getTiposAjuda() {
		return tiposAjuda;
	}


	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(List<Long> tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
	}

}
