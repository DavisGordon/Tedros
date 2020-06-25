package com.covidsemfome.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.documento, schema = DomainSchema.riosemfome)
public class Documento extends TEntity {

	private static final long serialVersionUID = -1610507713559488026L;
	
	@Column(length=1, nullable = false)
	private String tipo;
	
	@Column(length=100, nullable = false)
	private String numero;
	
	@Column(name = "data_emissao", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;
	
	@Column(name = "data_validade", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataValidade;
		
	@Column(name = "orgao_emissor", length=100, nullable = true)
	private String orgaoEmissor;
	
	@Column(name = "observacao", length=300, nullable = true)
	private String observacao;
	
	
	@OneToOne
	@JoinColumn(name = "pess_id", referencedColumnName = "id")
	private Pessoa pessoa;

	public Documento() {
	
	}
	
	public Documento(String numero, String tipo) {
		this.numero = numero;
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "{"+((getId()!=null) ? String.valueOf(getId())+", " :  "") + (tipo!=null?tipo+", ":"") + (numero!=null?numero+", ":"")+"} " ;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof Documento))
			return false;
		
		Documento e = (Documento) obj;
		
		if(getId()!=null &&  e.getId()!=null){
			return getId().equals(e.getId());
		}	
		
		return false;
	}
	
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
