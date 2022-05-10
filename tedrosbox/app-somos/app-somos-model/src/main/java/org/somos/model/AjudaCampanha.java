/**
 * 
 */
package org.somos.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.ajudaCampanha, schema = DomainSchema.riosemfome)
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
	@JoinColumn(name="id_for_ajuda")
	private FormaAjuda formaAjuda;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_detalhe")
	private DetalheAjuda detalheAjuda;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataProcessado;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataProximo;
	
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
		if(this.associado.getAjudaCampanhas()==null)
			this.associado.setAjudaCampanhas(new ArrayList<>());
		if(!this.associado.getAjudaCampanhas().contains(this))
			this.associado.getAjudaCampanhas().add(this);
	}
	
	public boolean cancelarAjuda() {
		if(this.detalheAjuda==null && this.associado.getAjudaCampanhas()!=null 
				&& this.associado.getAjudaCampanhas().contains(this)) {
			this.associado.getAjudaCampanhas().remove(this);
			this.associado = null;
			return true;
		}
		return false;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AjudaCampanha [associado=" + associado + ", campanha=" + campanha + ", valor=" + valor + ", status="
				+ status + ", periodo=" + periodo + ", formaAjuda=" + formaAjuda + ", dataProcessado=" + dataProcessado
				+ "]";
	}

	/**
	 * @return the dataProximo
	 */
	public Date getDataProximo() {
		return dataProximo;
	}

	/**
	 * @param dataProximo the dataProximo to set
	 */
	public void setDataProximo(Date dataProximo) {
		this.dataProximo = dataProximo;
	}

	/**
	 * @return the detalheAjuda
	 */
	public DetalheAjuda getDetalheAjuda() {
		return detalheAjuda;
	}

	/**
	 * @param detalheAjuda the detalheAjuda to set
	 */
	public void setDetalheAjuda(DetalheAjuda detalheAjuda) {
		this.detalheAjuda = detalheAjuda;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((associado == null) ? 0 : associado.hashCode());
		result = prime * result + ((campanha == null) ? 0 : campanha.hashCode());
		result = prime * result + ((dataProcessado == null) ? 0 : dataProcessado.hashCode());
		result = prime * result + ((dataProximo == null) ? 0 : dataProximo.hashCode());
		result = prime * result + ((detalheAjuda == null) ? 0 : detalheAjuda.hashCode());
		result = prime * result + ((formaAjuda == null) ? 0 : formaAjuda.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (super.equals(obj))
			return true;
		if (getClass() != obj.getClass())
			return false;
		AjudaCampanha other = (AjudaCampanha) obj;
		if (associado == null) {
			if (other.associado != null)
				return false;
		} else if (!associado.equals(other.associado))
			return false;
		if (campanha == null) {
			if (other.campanha != null)
				return false;
		} else if (!campanha.equals(other.campanha))
			return false;
		if (dataProcessado == null) {
			if (other.dataProcessado != null)
				return false;
		} else if (!dataProcessado.equals(other.dataProcessado))
			return false;
		if (dataProximo == null) {
			if (other.dataProximo != null)
				return false;
		} else if (!dataProximo.equals(other.dataProximo))
			return false;
		if (detalheAjuda == null) {
			if (other.detalheAjuda != null)
				return false;
		} else if (!detalheAjuda.equals(other.detalheAjuda))
			return false;
		if (formaAjuda == null) {
			if (other.formaAjuda != null)
				return false;
		} else if (!formaAjuda.equals(other.formaAjuda))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
}
