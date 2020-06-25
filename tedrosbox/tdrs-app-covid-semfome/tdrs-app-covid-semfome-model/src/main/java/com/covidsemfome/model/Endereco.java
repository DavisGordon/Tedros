package com.covidsemfome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.endereco, schema = DomainSchema.riosemfome)
public class Endereco extends TEntity {

	private static final long serialVersionUID = -8036028647622773159L;
	
	@Column(nullable = false)
	private String tipo;
	
	@Column(name = "caixa_postal", length=60, nullable = true)
	private String caixaPostal;
	
	@Column(length=10, nullable = false)
	private String cep;
	
	@Column(length=12, nullable = true)
	private String numero;
	
	@Column(length=300, nullable = true)
	private String complemento;
	
	@Column(length=300, nullable = true)
	private String logradouro;
	
	@Column(length=300, nullable = true)
	private String bairro;
	
	@Column(length=300, nullable = false)
	private String cidade; 
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="uf_id")
	private UF uf; 
	
	public Endereco() {
		
	}
	
	public Endereco(UF uf, String cidade, String cep, String tipo) {
		this.uf = uf;
		this.cidade = cidade;
		this.cep = cep;
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "{"+((getId()!=null) ? String.valueOf(getId())+", " :  "") + (tipo!=null?tipo+", ":"") + (cidade!=null?cidade+", ":"")+"} " ;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof Endereco))
			return false;
		
		Endereco e = (Endereco) obj;
		
		if(getId()!=null &&  e.getId()!=null){
			return getId().equals(e.getId());
		}	
		
		return false;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCaixaPostal() {
		return caixaPostal;
	}

	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public final String getNumero() {
		return numero;
	}

	public final void setNumero(String numero) {
		this.numero = numero;
	}

	public final UF getUf() {
		return uf;
	}

	public final void setUf(UF uf) {
		this.uf = uf;
	}
	

	 
	
}
