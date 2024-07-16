/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name = DomainTables.ai_image, schema = DomainSchema.tedros_core)
public class TAiImage extends TEntity {
	
	private static final long serialVersionUID = 8172525565114443839L;

	@Column(length=6, nullable=false)
	@Enumerated(EnumType.STRING)
	private TResponseFormat format;

	@Column
	private String url;
	
	@OneToOne(fetch=FetchType.EAGER, 
			cascade=CascadeType.ALL)
	@JoinColumn(name="file_id")
	private TFileEntity image;

	/**
	 * 
	 */
	public TAiImage() {
	}

	/**
	 * @param id
	 * @param lastUpdate
	 * @param insertDate
	 */
	public TAiImage(Long id, Date lastUpdate, Date insertDate) {
		super(id, lastUpdate, insertDate);
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param format
	 * @param url
	 */
	public TAiImage(TResponseFormat format, String url) {
		this.format = format;
		this.url = url;
	}

	/**
	 * @param format
	 * @param image
	 */
	public TAiImage(TResponseFormat format, TFileEntity image) {
		this.format = format;
		this.image = image;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the format
	 */
	public TResponseFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(TResponseFormat format) {
		this.format = format;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TAiImage))
			return false;
		TAiImage other = (TAiImage) obj;
		if (format != other.format)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
