/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class FormaAjudaModel implements Serializable {

	private static final long serialVersionUID = 7924379493407255213L;
	private Long id;
	private String nome;
	private String desc;
	private String terc;
	
	public FormaAjudaModel() {
	}
	
	public FormaAjudaModel(Long id, String nome, String desc, String terc) {
		super();
		this.id = id;
		this.nome = nome;
		this.desc = desc;
		this.terc = terc;
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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the terc
	 */
	public String getTerc() {
		return terc;
	}

	/**
	 * @param terc the terc to set
	 */
	public void setTerc(String terc) {
		this.terc = terc;
	}
}
