/**
 * 
 */
package org.somos.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private String conteudo;
	
	@Column(length=12, nullable = false)
	private String status;
	
	/**
	 * 
	 */
	public PessoaTermoAdesao() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isTermoValido(Set<TipoAjuda> selected) {
		if(this.tiposAjuda==null)
			return false;
		if(selected!=null && this.tiposAjuda!=null) {
			for(TipoAjuda a : selected) {
				if(!this.tiposAjuda.contains(a)) {
					return false;
				}
			}
		}
		
		return true;
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
		if(this.pessoa.getTermosAdesao()==null)
			this.pessoa.setTermosAdesao(new HashSet<>());
		this.pessoa.getTermosAdesao().add(this);
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


}
