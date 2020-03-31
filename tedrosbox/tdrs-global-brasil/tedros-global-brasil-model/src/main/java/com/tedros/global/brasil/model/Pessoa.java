package com.tedros.global.brasil.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.global.brasil.domain.DomainSchema;
import com.tedros.global.brasil.domain.DomainTables;

/**
 * The person entity
 * 
 * @author Davis Gordon
 * */
@Entity
@Table(name = DomainTables.pessoa, schema = DomainSchema.tedros_global_brasil)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=20)
@DiscriminatorValue(DomainTables.pessoa)
public class Pessoa extends TEntity {
	
	private static final long serialVersionUID = 7546438890556957532L;
	
	@Column(length=60, nullable = false)
	private String nome;
	
	@Column(name="sobre_nome", length=60, nullable = true)
	private String sobreNome;
	
	@Column(length=60, nullable = true)
	private String apelido;
	
	@Column(length=80, nullable = true)
	private String profissao;
	
	@Column(name = "data_nascimento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(length=1, nullable = false)
	private String sexo;
	
	@Column(length=1000)
	private String observacao;
	
	@OneToMany(mappedBy="pessoa", fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Documento> documentos;
	
	@OneToMany(mappedBy="pessoa",  fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Contato> contatos;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinTable(	name=DomainTables.pessoa_endereco,
    			schema=DomainSchema.tedros_global_brasil,
                joinColumns= @JoinColumn(name="pess_id"),
                inverseJoinColumns= @JoinColumn(name="ende_id"))	
	private Set<Endereco> enderecos;
	
		
	public Pessoa() {
		
	}
	
	public Pessoa(Long id) {
		setId(id);
	}
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<Documento> getDocumentos() {
		if(documentos==null)
			documentos = new HashSet<Documento>();
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	public Set<Contato> getContatos() {
		if(contatos==null)
			contatos = new HashSet<Contato>();
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}

	public Set<Endereco> getEnderecos() {
		if(enderecos==null)
			enderecos = new HashSet<Endereco>();
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public final String getSexo() {
		return sexo;
	}

	public final void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public final String getSobreNome() {
		return sobreNome;
	}

	public final void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public final String getApelido() {
		return apelido;
	}

	public final void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public final String getProfissao() {
		return profissao;
	}

	public final void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
