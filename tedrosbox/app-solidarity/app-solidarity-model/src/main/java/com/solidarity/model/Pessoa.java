package com.solidarity.model;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.solidarity.domain.DomainSchema;
import com.solidarity.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * The person entity
 * 
 * @author Davis Gordon
 * */
@Entity
@Table(name = DomainTables.pessoa, schema = DomainSchema.solidarity)
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
	
	@Column(length=12, nullable = true)
	private String estadoCivil;
	
	@Column(length=1000)
	private String observacao;
	
	@Column(length=12, nullable = true)
	private String status;
	
	@Column
	private Boolean concordaTermo;
	
	@Column(length=100)
	private String newPassKey;
	
	@OneToMany(mappedBy="pessoa", fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Documento> documentos;
	
	@OneToMany(mappedBy="pessoa",  fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<Contato> contatos;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinTable(	name=DomainTables.pessoa_endereco,
    			schema=DomainSchema.solidarity,
                joinColumns= @JoinColumn(name="pess_id"),
                inverseJoinColumns= @JoinColumn(name="ende_id"))	
	private Set<Endereco> enderecos;
	
	@OneToMany(mappedBy="pessoa",  fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private Set<PessoaTermoAdesao> termosAdesao;
	
	
	public Pessoa() {
		
	}
	
	public Pessoa(Long id) {
		setId(id);
	}
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	
	public boolean isTermoAdesaoNecessario(Set<TipoAjuda> lst) {
		PessoaTermoAdesao pta = this.getTermosAdesaoVigente();
		return pta==null || (pta!=null && !pta.isTermoValido(lst));
	}
	
	public boolean isTermoAdesaoElegivel() {
		boolean a = StringUtils.isNotBlank(nome) && StringUtils.isNotBlank(profissao) 
				&& StringUtils.isNotBlank(estadoCivil);
		
		boolean b = false;
		if (this.getDocumentos()!=null) {
			int t = 2;
			for(Documento d : this.getDocumentos())
				if(d.getTipo().equals("1") || d.getTipo().equals("2"))
					t--;
			b = t==0;
		}
		
		boolean c = false;
		if (this.getEnderecos()!=null) {
			for(Endereco e : this.getEnderecos())
				if(e.getTipo().equals("1")) {
					c = StringUtils.isNotBlank(e.getTipoLogradouro()) && StringUtils.isNotBlank(e.getLogradouro()) 
							&& StringUtils.isNotBlank(e.getComplemento()) && StringUtils.isNotBlank(e.getBairro()) 
							&& StringUtils.isNotBlank(e.getCidade()) && StringUtils.isNotBlank(e.getCep()) 
							&& e.getUf()!=null;
				}
					
		}
		
		return (a && b && c);
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

	/**
	 * @return the estadoCivil
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return the termosAdesao
	 */
	public PessoaTermoAdesao getTermosAdesaoVigente() {
		if(termosAdesao!=null)
			for(PessoaTermoAdesao e : termosAdesao)
				if(e.getStatus().equals("ATIVADO"))
					return e;
		return null;
	}


	/**
	 * @return the termosAdesao
	 */
	public Set<PessoaTermoAdesao> getTermosAdesao() {
		return termosAdesao;
	}
	
	/**
	 * @param termosAdesao the termosAdesao to set
	 */
	public void setTermosAdesao(Set<PessoaTermoAdesao> termosAdesao) {
		this.termosAdesao = termosAdesao;
	}

	/**
	 * @return the concordaTermo
	 */
	public Boolean getConcordaTermo() {
		return concordaTermo;
	}

	/**
	 * @param concordaTermo the concordaTermo to set
	 */
	public void setConcordaTermo(Boolean concordaTermo) {
		this.concordaTermo = concordaTermo;
	}

	
}
