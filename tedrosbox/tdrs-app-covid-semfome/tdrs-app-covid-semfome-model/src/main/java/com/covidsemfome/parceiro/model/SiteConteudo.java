/**
 * 
 */
package com.covidsemfome.parceiro.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = DomainTables.site_conteudo, schema = DomainSchema.riosemfome)
public class SiteConteudo extends TEntity {

	private static final long serialVersionUID = -1708755572418627462L;

	@Column(length=60)
	private String titulo;
	
	@Column
	private Boolean showMenu;
	
	@Column(nullable=false)
	private String code;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="image_id")
	private TFileEntity image;
	
	@Column
	private Integer ordem;
	
	@Column(length=10)
	private String status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="template_id", nullable=false)
	private ComponentTemplate template;

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
	 * @return the showMenu
	 */
	public Boolean getShowMenu() {
		return showMenu;
	}

	/**
	 * @param showMenu the showMenu to set
	 */
	public void setShowMenu(Boolean showMenu) {
		this.showMenu = showMenu;
	}

	/**
	 * @return the image
	 */
	public TFileEntity getImage() {
		if(image==null)
			image = new TFileEntity();
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(TFileEntity image) {
		this.image = image;
	}

	/**
	 * @return the ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the template
	 */
	public ComponentTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(ComponentTemplate template) {
		this.template = template;
	}
	
	
}
