/**
 * 
 */
package org.somos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.campanha_mail_config, schema = DomainSchema.riosemfome)
public class CampanhaMailConfig extends TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8838010836519979624L;

	@Column(length=120, nullable=false)
	private String titulo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_camp")
	private Campanha campanha;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_for_ajuda", nullable=false)
	private FormaAjuda formaAjuda;
	
	@Column(nullable=false)
	private String conteudo;
	

	@Column(length=15)
	private String status;

	/**
	 * @return the campanha
	 */
	public Campanha getCampanha() {
		return campanha;
	}

	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	/**
	 * @return the formaAjuda
	 */
	public FormaAjuda getFormaAjuda() {
		return formaAjuda;
	}

	/**
	 * @param formaAjuda the formaAjuda to set
	 */
	public void setFormaAjuda(FormaAjuda formaAjuda) {
		this.formaAjuda = formaAjuda;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CampanhaMailConfig [titulo=" + titulo + ", status=" + status + ", formaAjuda=" + formaAjuda
				+ ", campanha=" + campanha + "]";
	}
	

	
}
