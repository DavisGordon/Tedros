/**
 * 
 */
package com.covidsemfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.voluntario, schema = DomainSchema.riosemfome,
uniqueConstraints=@UniqueConstraint(name="volun_uk", columnNames = { "pess_id", "acao_id" }))
public class Voluntario extends TEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6400199511751148254L;

	@ManyToOne(targetEntity=Pessoa.class)
	@JoinColumn(name="pess_id")
	private Pessoa pessoa;
	
	@ManyToOne(targetEntity=Acao.class)
	@JoinColumn(name="acao_id")
	private Acao acao;

	@Column(length=60 )
	private String status;

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
	 * @return the acao
	 */
	public Acao getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
