/**
 * 
 */
package org.somos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.formaAjuda, schema = DomainSchema.riosemfome)
public class FormaAjuda extends TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9043608584135050089L;

	@Column(length=60, nullable=false)
	private String tipo;
	
	@Column(length=2000)
	private String detalhe;
	
	@Column(length=3)
	private String tercerizado;

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the detalhe
	 */
	public String getDetalhe() {
		return detalhe;
	}

	/**
	 * @param detalhe the detalhe to set
	 */
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormaAjuda [id=" + getId() + ", tipo=" + tipo + "]";
	}

	/**
	 * @return the tercerizado
	 */
	public String getTercerizado() {
		return tercerizado;
	}

	/**
	 * @param tercerizado the tercerizado to set
	 */
	public void setTercerizado(String tercerizado) {
		this.tercerizado = tercerizado;
	}
	
}
