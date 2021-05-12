/**
 * 
 */
package com.covidsemfome.rest.model;

/**
 * @author Davis Gordon
 *
 */
public class UserModel {

	private Long id;
	
	private String nome;
	
	private String email;
	
	private String telefone;

	private String sexo;
	
	private String tipoVoluntario;
	
	private String profissao;
	
	private String nacionalidade;
	
	private String estadoCivil;
	
	private String identidade;
	
	private String cpf;
	
	private String tipoLogr;
	
	private String logradouro;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String cep;
	
	private Long ufid;
	
	
	

	/**
	 * @param id
	 * @param nome
	 * @param email
	 * @param telefone
	 * @param sexo
	 * @param tipoVoluntario
	 * @param profissao
	 * @param nacionalidade
	 * @param estadoCivil
	 * @param identidade
	 * @param cpf
	 * @param tipoLogr
	 * @param logradouro
	 * @param complemento
	 * @param bairro
	 * @param cidade
	 * @param cep
	 * @param ufid
	 */
	public UserModel(Long id, String nome, String email, String telefone, String sexo, String tipoVoluntario,
			String profissao, String nacionalidade, String estadoCivil, String identidade, String cpf, String tipoLogr,
			String logradouro, String complemento, String bairro, String cidade, String cep, Long ufid) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.sexo = sexo;
		this.tipoVoluntario = tipoVoluntario;
		this.profissao = profissao;
		this.nacionalidade = nacionalidade;
		this.estadoCivil = estadoCivil;
		this.identidade = identidade;
		this.cpf = cpf;
		this.tipoLogr = tipoLogr;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.ufid = ufid;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	 * @return the profissao
	 */
	public String getProfissao() {
		return profissao;
	}

	/**
	 * @param profissao the profissao to set
	 */
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	/**
	 * @return the nacionalidade
	 */
	public String getNacionalidade() {
		return nacionalidade;
	}

	/**
	 * @param nacionalidade the nacionalidade to set
	 */
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
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
	 * @return the identidade
	 */
	public String getIdentidade() {
		return identidade;
	}

	/**
	 * @param identidade the identidade to set
	 */
	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the tipoLogr
	 */
	public String getTipoLogr() {
		return tipoLogr;
	}

	/**
	 * @param tipoLogr the tipoLogr to set
	 */
	public void setTipoLogr(String tipoLogr) {
		this.tipoLogr = tipoLogr;
	}

	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the ufid
	 */
	public Long getUfid() {
		return ufid;
	}

	/**
	 * @param ufid the ufid to set
	 */
	public void setUfid(Long ufid) {
		this.ufid = ufid;
	}

}
