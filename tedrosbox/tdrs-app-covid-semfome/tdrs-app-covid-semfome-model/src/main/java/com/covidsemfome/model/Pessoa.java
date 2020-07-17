package com.covidsemfome.model;

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

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * The person entity
 * 
 * @author Davis Gordon
 * */
@Entity
@Table(name = DomainTables.pessoa, schema = DomainSchema.riosemfome)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=20)
@DiscriminatorValue(DomainTables.pessoa)
public class Pessoa extends TEntity {
	
	private static final long serialVersionUID = 7546438890556957532L;
	
	@Column(length=60, nullable = false)
	private String nome;
	
	@Column(length=80, nullable = true, unique=true)
	private String loginName;
	
	@Column(length=160, nullable = true)
	private String password;
	
	@Column(length=80, nullable = true)
	private String profissao;
	
	@Column(length=1, nullable = true)
	private String tipoVoluntario;
	
	
	@Column(length=1, nullable = true)
	private String statusVoluntario;
	
	@Column(name = "data_nascimento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(length=1, nullable = true)
	private String sexo;
	
	@Column(length=1000)
	private String observacao;
	
	@Column(length=12, nullable = true)
	private String status;
	
	@Column(length=100)
	private String newPassKey;
	
	@OneToMany(mappedBy="pessoa", fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Documento> documentos;
	
	@OneToMany(mappedBy="pessoa",  fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Contato> contatos;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinTable(	name=DomainTables.pessoa_endereco,
    			schema=DomainSchema.riosemfome,
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

	/**
	 * @return the tipoVoluntario
	 */
	public String getTipoVoluntario() {
		return tipoVoluntario;
	}

	/**
	 * @param tipoVoluntario the tipoVoluntario to set
	 */
	public void setTipoVoluntario(String tipoVoluntario) {
		this.tipoVoluntario = tipoVoluntario;
	}

	/**
	 * @return the statusVoluntario
	 */
	public String getStatusVoluntario() {
		return statusVoluntario;
	}

	/**
	 * @param statusVoluntario the statusVoluntario to set
	 */
	public void setStatusVoluntario(String statusVoluntario) {
		this.statusVoluntario = statusVoluntario;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the newPassKey
	 */
	public String getNewPassKey() {
		return newPassKey;
	}

	/**
	 * @param newPassKey the newPassKey to set
	 */
	public void setNewPassKey(String newPassKey) {
		this.newPassKey = newPassKey;
	}

	
}
