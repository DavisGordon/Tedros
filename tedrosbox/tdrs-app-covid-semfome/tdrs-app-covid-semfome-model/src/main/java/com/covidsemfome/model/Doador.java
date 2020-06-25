package com.covidsemfome.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = DomainTables.doador, schema = DomainSchema.riosemfome)
public class Doador extends TEntity {
	
	private static final long serialVersionUID = 7546438890556957532L;
	
	@Column(length=60, nullable = false)
	private String nome;
	
	@Column(length=80, nullable = true)
	private String email;
	
	@Column(length=20, nullable = true)
	private String celular;
	
	@Column(length=150, nullable = true)
	private String endereco;
	
	@Column(length=450, nullable = true)
	private String observacao;
	
	@Column(length=1, nullable = false)
	private String sexo;
	
	@Column(name = "data_nascimento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@OneToMany(mappedBy="doador", fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Doacao> doacoes;
	
	
		
	public Doador() {
		
	}
	
	public Doador(Long id) {
		setId(id);
	}
	
	public Doador(String nome) {
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


	public Set<Doacao> getDoacoes() {
		if(doacoes==null)
			doacoes = new HashSet<Doacao>();
		return doacoes;
	}

	public void setDoacoes(Set<Doacao> doacoes) {
		this.doacoes = doacoes;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	
	
}
