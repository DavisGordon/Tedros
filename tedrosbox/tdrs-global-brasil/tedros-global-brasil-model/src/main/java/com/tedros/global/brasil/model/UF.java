package com.tedros.global.brasil.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.global.brasil.domain.DomainSchema;
import com.tedros.global.brasil.domain.DomainTables;

@Entity
@Table(name = DomainTables.uf, schema = DomainSchema.tedros_global_brasil)
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
