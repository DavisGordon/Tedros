package org.somos.web.rest.model;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.somos.model.SiteAbout;
import org.somos.model.SiteContato;
import org.somos.model.SiteDoacao;
import org.somos.model.SiteNoticia;
import org.somos.model.SiteTermo;
import org.somos.model.SiteVideo;

@XmlRootElement(name="site")
public class SiteModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 142693441190203087L;

	@XmlAttribute
	private String descricao;
	
	@XmlAttribute
	private String link;
	
	@XmlAttribute
	private String valor;
	
	@XmlAttribute
	private String nome;
	
	@XmlAttribute
	private String cargo;
	
	@XmlAttribute
	private String telefone;
	
	@XmlAttribute
	private String whatsapp;
	
	@XmlAttribute
	private String email;
	
	public SiteModel(SiteTermo e) {
		this.descricao = e.getDescricao();
	}
	
	public SiteModel(SiteAbout e) {
		this.descricao = e.getDescricao();
	}
	
	public SiteModel(SiteContato e) {
		this.nome = e.getNome();
		this.cargo = e.getCargo();
		this.telefone = e.getTelefone();
		if(e.getWhatsapp()!=null)
			this.whatsapp = e.getWhatsapp().toString();
		this.email = e.getEmail();
	}
	
	public SiteModel(SiteNoticia e) {
		this.descricao = e.getDescricao();
		this.link = e.getLink();
	}
	
	public SiteModel(SiteVideo e) {
		this.descricao = e.getDescricao();
		this.link = e.getLink();
	}

	public SiteModel(SiteDoacao e) {
		this.descricao = e.getDescricao();
		this.link = e.getLink();
		if(e.getValor()!=null) {
			DecimalFormat df = new DecimalFormat("####.00");
			this.valor = "R$ " + df.format(e.getValor());
		}
	}
	

	public SiteModel() {
		
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
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
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
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
	 * @return the whatsapp
	 */
	public String getWhatsapp() {
		return whatsapp;
	}

	/**
	 * @param whatsapp the whatsapp to set
	 */
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
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
}
