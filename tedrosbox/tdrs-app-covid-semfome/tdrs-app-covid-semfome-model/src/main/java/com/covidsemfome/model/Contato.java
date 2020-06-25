package com.covidsemfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.contato, schema = DomainSchema.riosemfome)
public class Contato extends TEntity {
	
	private static final long serialVersionUID = -7841296147274009489L;
	
	@Column(nullable = false)
	private String tipo;
	
	@Column(length=100, nullable=false)
	private String descricao; 
	
	@OneToOne
	@JoinColumn(name = "pess_id", referencedColumnName = "id")
	private Pessoa pessoa;
	
	public Contato() {
	
	}
	
	public Contato(String descricao, String tipo) {
		this.descricao = descricao;
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "{"+((getId()!=null) ? String.valueOf(getId())+", " :  "") + (tipo!=null?tipo+", ":"") + (descricao!=null?descricao+", ":"")+"} " ;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof Contato))
			return false;
		
		Contato e = (Contato) obj;
		
		if(getId()!=null &&  e.getId()!=null){
			return getId().equals(e.getId());
		}	
		
		return false;
	}
		
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

}
