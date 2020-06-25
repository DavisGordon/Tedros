package com.covidsemfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.uf, schema =DomainSchema.riosemfome)
public class UF extends TEntity {

	private static final long serialVersionUID = -4713391168986152903L;

	@Column(length=2, nullable = false)
	private String sigla;
	
	@Column(length=80, nullable = false)
	private String descricao;

	public final String getSigla() {
		return sigla;
	}

	public final void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public final String getDescricao() {
		return descricao;
	}

	public final void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

		
}
