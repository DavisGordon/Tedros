/**
 * 
 */
package com.covidsemfome.rest.model;

import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement 
public class IdNomeModel implements Serializable{

	private static final long serialVersionUID = 2195785092601867812L;
	
	//@FormParam("id")
	private Long id;
	
	//@FormParam("nome")
	private String nome;
	
	public IdNomeModel() {
		
	}

	/**
	 * @param id
	 * @param nome
	 */
	public IdNomeModel(Long id, String nome) {
		this.id = id;
		this.nome = nome;
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

}
