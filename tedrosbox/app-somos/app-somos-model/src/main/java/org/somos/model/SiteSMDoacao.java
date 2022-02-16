/**
 * 
 */
package org.somos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.domain.DomainSchema;
import org.somos.domain.DomainTables;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name = DomainTables.site_sm_doacao, schema = DomainSchema.riosemfome)
public class SiteSMDoacao extends TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8637421362446521540L;

	@Column(nullable = false)
	private String descricao;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="image_id")
	private TFileEntity image;

	@OneToMany(mappedBy="doacao", fetch = FetchType.EAGER, orphanRemoval=true, cascade={CascadeType.ALL})
	private List<SitePontoColeta> pontosColeta;
	
	@OneToOne( fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name = "transf_id", referencedColumnName = "id")
	private SiteDoacaoTransferencia transferencia;
	
	@Column(length=60, nullable = false)
	private String status;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return descricao;
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
		this.descricao = SiteHelper.cleanHtml(descricao);
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
	 * @return the pontosColeta
	 */
	public List<SitePontoColeta> getPontosColeta() {
		return pontosColeta;
	}

	/**
	 * @param pontosColeta the pontosColeta to set
	 */
	public void setPontosColeta(List<SitePontoColeta> pontosColeta) {
		this.pontosColeta = pontosColeta;
	}

	/**
	 * @return the transferencia
	 */
	public SiteDoacaoTransferencia getTransferencia() {
		return transferencia;
	}

	/**
	 * @param transferencia the transferencia to set
	 */
	public void setTransferencia(SiteDoacaoTransferencia transferencia) {
		this.transferencia = transferencia;
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

}
