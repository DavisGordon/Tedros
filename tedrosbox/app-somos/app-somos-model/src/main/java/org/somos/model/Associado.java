/**
 * 
 */
package org.somos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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

	@OneToOne()
	@JoinColumn(name="id_pess", nullable=false, updatable=false)
	private Pessoa pessoa;
	
	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelamento;
	
	@Column(length=5)
	private String aceitaEmails;

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
	
	
	
	
	
	
	
}