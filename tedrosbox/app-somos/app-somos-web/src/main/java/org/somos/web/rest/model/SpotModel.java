/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.somos.parceiro.model.SiteConteudo;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement(name="spot")
public final class SpotModel implements Serializable{

	private static final long serialVersionUID = 449968749733619887L;

	@XmlAttribute
	private String titulo;
	@XmlAttribute
	private String conteudo;
	@XmlAttribute
	private Long image;
	@XmlAttribute
	private String base64;
	@XmlAttribute
	private String ext;
	@XmlAttribute
	private String orientacao;
	@XmlAttribute
	private String estilo;

	public SpotModel(SiteConteudo e) {
		this.titulo = e.getTitulo();
		this.conteudo = e.getCode();
		if(!e.getImage().isNew()) {
			this.image =  e.getImage().getId();
			this.ext = e.getImage().getFileExtension();
			//this.base64 = Base64.getEncoder().encodeToString(e.getImage().getByteEntity().getBytes());
		}
		
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the conteudo
	 */
	public String getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	/**
	 * @return the image
	 */
	public Long getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Long image) {
		this.image = image;
	}

	/**
	 * @return the orientacao
	 */
	public String getOrientacao() {
		return orientacao;
	}

	/**
	 * @param orientacao the orientacao to set
	 */
	public void setOrientacao(String orientacao) {
		this.orientacao = orientacao;
	}

	/**
	 * @return the estilo
	 */
	public String getEstilo() {
		return estilo;
	}

	/**
	 * @param estilo the estilo to set
	 */
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	/**
	 * @return the base64
	 */
	public String getBase64() {
		return base64;
	}

	/**
	 * @param base64 the base64 to set
	 */
	public void setBase64(String base64) {
		this.base64 = base64;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}
	
}
