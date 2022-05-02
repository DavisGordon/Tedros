/**
 * 
 */
package org.somos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.associado, schema = DomainSchema.riosemfome)
public class Associado extends TEntity {

	private static final long serialVersionUID = 4090673678878783748L;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_pess", nullable=false, updatable=false)
	private Pessoa pessoa;
	
	@OneToMany(mappedBy="associado", 
			fetch=FetchType.EAGER, 
			orphanRemoval=true,
			cascade=CascadeType.ALL)
	private List<AjudaCampanha> ajudaCampanhas;

	/**
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	/**
	 * @return the ajudaCampanhas
	 */
	public List<AjudaCampanha> getAjudaCampanhas() {
		return ajudaCampanhas;
	}

	/**
	 * @param ajudaCampanhas the ajudaCampanhas to set
	 */
	public void setAjudaCampanhas(List<AjudaCampanha> ajudaCampanhas) {
		this.ajudaCampanhas = ajudaCampanhas;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  pessoa.toString();
	}
	
	
	
	
	
	
	
}