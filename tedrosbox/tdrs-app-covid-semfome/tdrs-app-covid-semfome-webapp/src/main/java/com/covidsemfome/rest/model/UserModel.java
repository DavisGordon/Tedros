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
	
	
	
	public UserModel(Long id, String nome, String email, 
			String telefone, String sexo, String tipoVoluntario) {
		
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.sexo = sexo;
		this.tipoVoluntario = tipoVoluntario;
		
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

}
