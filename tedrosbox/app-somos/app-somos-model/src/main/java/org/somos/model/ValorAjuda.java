/**
 * 
 */
package org.somos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.math.NumberUtils;
import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.valorAjuda, schema = DomainSchema.riosemfome)
public class ValorAjuda extends TEntity implements Comparable<ValorAjuda>{

	private static final long serialVersionUID = 8496608015893104075L;

	@Column(length=60, nullable=false)
	private String valor;
	
	@Column(length=60)
	private String planoPayPal;

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the planoPayPal
	 */
	public String getPlanoPayPal() {
		return planoPayPal;
	}

	/**
	 * @param planoPayPal the planoPayPal to set
	 */
	public void setPlanoPayPal(String planoPayPal) {
		this.planoPayPal = planoPayPal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  valor != null ? valor : "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((planoPayPal == null) ? 0 : planoPayPal.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ValorAjuda other = (ValorAjuda) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public int compareTo(ValorAjuda o) {
		if(o!=null && NumberUtils.isCreatable(valor) && NumberUtils.isCreatable(o.getValor())){
			BigDecimal v1 = new BigDecimal(valor);
			BigDecimal v2 = new BigDecimal(o.getValor());
			return v1.compareTo(v2);
		}
		
		return valor!=null && o!=null && o.getValor()!=null 
				? valor.compareTo(o.getValor())
						: 0;
	}
	
	
}
