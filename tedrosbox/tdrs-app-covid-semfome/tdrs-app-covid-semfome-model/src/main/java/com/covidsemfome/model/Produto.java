/**
 * 
 */
package com.covidsemfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.annotation.TCaseSensitive;
import com.tedros.ejb.base.annotation.TEntityImportRule;
import com.tedros.ejb.base.annotation.TFieldImportRule;
import com.tedros.ejb.base.annotation.TFileType;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.produto, schema = DomainSchema.riosemfome, 
uniqueConstraints= {@UniqueConstraint(name="codigoUnIdx", columnNames = { "codigo" })} )
@TEntityImportRule(description = "Regras para importar um arquivo para a tabela de produtos ", 
fileType = { TFileType.CSV, TFileType.XLS })
public class Produto extends TEntity {

	
	private static final long serialVersionUID = -4590169775657544834L;

	@Column(length=10, nullable = false)
	@TFieldImportRule(required = true, 
		description = "Codigo do produto", column = "Codigo", 
		numberType=Integer.class, example="22")
	private String codigo;
	
	@Column(length=60, nullable = false)
	@TFieldImportRule(required = true, 
	description = "Nome do produto", column = "Nome Produto",
	example="Arroz")
	private String nome;
	
	@Column(length=60, nullable = true)
	private String marca;
	
	@Column(length=120, nullable = true)
	private String descricao;
	
	@Column(length=15, nullable = true)
	@TFieldImportRule(required = true, 
	description = "Unidade medida", column = "Unidade Medida",
	example="KG", caseSensitive=TCaseSensitive.UPPER, 
	possibleValues= {"KG", "LT", "UNID","PCT","ML","GR"})
	private String unidadeMedida;
	
	@Column(length=10, nullable = true)
	private String medida;
	
	public Produto() {
	}
	
	public Produto(String cod) {
		this.codigo = cod;
	}



	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}



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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the unidadeMedida
	 */
	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the medida
	 */
	public String getMedida() {
		return medida;
	}

	/**
	 * @param medida the medida to set
	 */
	public void setMedida(String medida) {
		this.medida = medida;
	}

	
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (codigo != null ? "(COD: " + codigo + ") " : "") + (nome != null ? nome : "");
	}

}
