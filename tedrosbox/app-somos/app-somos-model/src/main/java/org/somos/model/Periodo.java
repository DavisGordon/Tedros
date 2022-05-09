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
@Table(name = DomainTables.periodo, schema = DomainSchema.riosemfome)
public class Periodo extends TEntity {

	private static final long serialVersionUID = -5505163709895306039L;

	@Column(length=20, nullable=false)
	private String nome;
	
	@Column(length=15, nullable=false)
	private String code;
	
	@Column
	private Integer totalDias;
	

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the totalDias
	 */
	public Integer getTotalDias() {
		return totalDias;
	}

	/**
	 * @param totalDias the totalDias to set
	 */
	public void setTotalDias(Integer totalDias) {
		this.totalDias = totalDias;
	}

}
