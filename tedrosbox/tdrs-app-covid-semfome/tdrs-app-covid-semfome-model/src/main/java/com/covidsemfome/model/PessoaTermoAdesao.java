/**
 * 
 */
package com.covidsemfome.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.pessoaTermoAdesao, schema = DomainSchema.riosemfome)
public class PessoaTermoAdesao extends TEntity {

	
	private static final long serialVersionUID = 6247703319893159525L;

	@OneToOne
	@JoinColumn(name = "pess_id", referencedColumnName = "id")
	private Pessoa pessoa;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name=DomainTables.termo_tipoajuda,
				schema=DomainSchema.riosemfome,
				joinColumns= @JoinColumn(name="pt_id"),
				inverseJoinColumns= @JoinColumn(name="tip_id"))
	private Set<TipoAjuda> tiposAjuda;
	
	@Column(nullable=false)
	private String termoAdesao;
	/**
	 * 
	 */
	public PessoaTermoAdesao() {
		// TODO Auto-generated constructor stub
	}
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
	/**
	 * @return the termoAdesao
	 */
	public String getTermoAdesao() {
		return termoAdesao;
	}
	/**
	 * @param termoAdesao the termoAdesao to set
	 */
	public void setTermoAdesao(String termoAdesao) {
		this.termoAdesao = termoAdesao;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((termoAdesao == null) ? 0 : termoAdesao.hashCode());
		result = prime * result + ((tiposAjuda == null) ? 0 : tiposAjuda.hashCode());
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
		if (getClass() != obj.getClass())
			return false;
		PessoaTermoAdesao other = (PessoaTermoAdesao) obj;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (termoAdesao == null) {
			if (other.termoAdesao != null)
				return false;
		} else if (!termoAdesao.equals(other.termoAdesao))
			return false;
		if (tiposAjuda == null) {
			if (other.tiposAjuda != null)
				return false;
		} else if (!tiposAjuda.equals(other.tiposAjuda))
			return false;
		return true;
	}


}
