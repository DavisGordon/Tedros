/**
 * 
 */
package com.covidsemfome.parceiro.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.empresaParceira, schema = DomainSchema.riosemfome)
public class EmpresaParceira extends TEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7789077402629070304L;

	@Column(length=100, nullable=false)
	private String nome;
	
	@Column(length=20)
	private String cnpj;
	
	@Column(length=100, nullable=false)
	private String email;
	
	@Column(length=100)
	private String password;
	
	@Column(length=20, nullable=false)
	private String telefone;
	
	@Column(length=100, nullable=false)
	private String contato;
	
	@Column(length=200)
	private String endereco;
	
	@Column(length=300)
	private String observacao;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="logo_id", unique=true)
	private TFileEntity logo;

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
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the contato
	 */
	public String getContato() {
		return contato;
	}

	/**
	 * @param contato the contato to set
	 */
	public void setContato(String contato) {
		this.contato = contato;
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
	 * @return the logo
	 */
	public TFileEntity getLogo() {
		if(logo==null) {
			logo = new TFileEntity();
		}
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(TFileEntity logo) {
		this.logo = logo;
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
