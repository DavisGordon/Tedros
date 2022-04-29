/**
 * 
 */
package org.somos.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.campanha, schema = DomainSchema.riosemfome)
public class Campanha extends TEntity {
	private static final long serialVersionUID = -8277145664737419416L;

	@Column(length=120, nullable=false)
	private String titulo;
	
	@Column(length=2000, nullable=false)
	private String desc;
	
	@Column(length=120)
	private String valores;
	
	@Column(length=30)
	private String meta;
	
	@Column(length=30)
	private String angariado;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Column(length=15)
	private String status;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_img", nullable=true, updatable=true)
	private TFileEntity image;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.campanha_periodo, schema=DomainSchema.riosemfome,
	uniqueConstraints=@UniqueConstraint(name="campanhaPeriodoUK", columnNames = { "camp_id", "per_id" }),
	joinColumns=@JoinColumn(name="camp_id"), inverseJoinColumns=@JoinColumn(name="per_id"))
	private Set<Periodo> periodos;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.campanha_formaajuda,  schema=DomainSchema.riosemfome,
	uniqueConstraints=@UniqueConstraint(name="campanhaFormaAjudaUK", columnNames = { "camp_id", "fajuda_id" }),
	joinColumns=@JoinColumn(name="camp_id"), inverseJoinColumns=@JoinColumn(name="fajuda_id"))
	private Set<FormaAjuda> formasAjuda;

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
	 * @return the valores
	 */
	public String getValores() {
		return valores;
	}

	/**
	 * @param valores the valores to set
	 */
	public void setValores(String valores) {
		this.valores = valores;
	}

	/**
	 * @return the meta
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(String meta) {
		this.meta = meta;
	}

	/**
	 * @return the angariado
	 */
	public String getAngariado() {
		return angariado;
	}

	/**
	 * @param angariado the angariado to set
	 */
	public void setAngariado(String angariado) {
		this.angariado = angariado;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
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
	 * @return the image
	 */
	public TFileEntity getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(TFileEntity image) {
		this.image = image;
	}

	/**
	 * @return the formasAjuda
	 */
	public Set<FormaAjuda> getFormasAjuda() {
		return formasAjuda;
	}

	/**
	 * @param formasAjuda the formasAjuda to set
	 */
	public void setFormasAjuda(Set<FormaAjuda> formasAjuda) {
		this.formasAjuda = formasAjuda;
	}

	/**
	 * @param periodos the periodos to set
	 */
	public void setPeriodos(Set<Periodo> periodos) {
		this.periodos = periodos;
	}

	/**
	 * @return the periodos
	 */
	public Set<Periodo> getPeriodos() {
		return periodos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Campanha [id=" + getId() + ", titulo=" + titulo + ", dataFim=" + dataFim + ", status=" + status
				+ "]";
	}

}
