/**
 * 
 */
package org.somos.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name=DomainTables.vol_tipoajuda,
				schema=DomainSchema.riosemfome,
				joinColumns= @JoinColumn(name="vol_id"),
				inverseJoinColumns= @JoinColumn(name="tip_id"))
	private Set<TipoAjuda> tiposAjuda;

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

	/**
	 * @return the tiposAjuda
	 */
	public Set<TipoAjuda> getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(Set<TipoAjuda> tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((acao == null) ? 0 : acao.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Voluntario))
			return false;
		Voluntario other = (Voluntario) obj;
		if (acao == null) {
			if (other.acao != null)
				return false;
		} else if (!acao.equals(other.acao))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		return true;
	}
	
	
}
