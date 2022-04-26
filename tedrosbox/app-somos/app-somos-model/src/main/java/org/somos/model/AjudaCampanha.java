/**
 * 
 */
package org.somos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.ajudaCampanha, schema = DomainSchema.riosemfome, 
uniqueConstraints=@UniqueConstraint(name="ajudaCampanhaUK", columnNames = { "id_assoc", "id_camp" }))
public class AjudaCampanha extends TEntity {

	private static final long serialVersionUID = 8838010836519979624L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_assoc", nullable=false, updatable=false)
	private Associado associado;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_camp", nullable=false, updatable=false)
	private Campanha campanha;
	
	@Column(length=60)
	private String valor;

	@Column(length=15)
	private String status;

	@Column(length=60)
	private String periodo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_for_ajuda", updatable=false)
	private FormaAjuda formaAjuda;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataProcessado;

	/**
	 * @return the associado
	 */
	public Associado getAssociado() {
		return associado;
	}

	/**
	 * @param associado the associado to set
	 */
	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

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
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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
	 * @return the dataProcessado
	 */
	public Date getDataProcessado() {
		return dataProcessado;
	}

	/**
	 * @param dataProcessado the dataProcessado to set
	 */
	public void setDataProcessado(Date dataProcessado) {
		this.dataProcessado = dataProcessado;
	}
	
}
