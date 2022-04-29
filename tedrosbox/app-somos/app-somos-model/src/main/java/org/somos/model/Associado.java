/**
 * 
 */
package org.somos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelamento;
	
	@Column(length=5)
	private String aceitaEmails;
	
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
	 * @return the dataCancelamento
	 */
	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	/**
	 * @param dataCancelamento the dataCancelamento to set
	 */
	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	/**
	 * @return the aceitaEmails
	 */
	public String getAceitaEmails() {
		return aceitaEmails;
	}

	/**
	 * @param aceitaEmails the aceitaEmails to set
	 */
	public void setAceitaEmails(String aceitaEmails) {
		this.aceitaEmails = aceitaEmails;
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