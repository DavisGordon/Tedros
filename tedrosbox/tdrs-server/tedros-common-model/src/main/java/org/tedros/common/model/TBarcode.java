/**
 * 
 */
package org.tedros.common.model;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.tedros.common.domain.DomainSchema;
import org.tedros.common.domain.DomainTables;
import org.tedros.server.entity.TEntity;
import org.tedros.server.model.ITBarcode;
import org.tedros.server.model.ITFileBaseModel;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Cacheable(false)
@Table(name=DomainTables.barcode, schema=DomainSchema.tedros_common)
public class TBarcode extends TEntity implements ITBarcode {

	private static final long serialVersionUID = -5862336001870552877L;

	@Column(nullable=false)
	private String content;

	@Column(length=25, nullable=false)
	private String type;
	
	@Column
	private Integer orientation;
	
	@Column
	private Integer resolution;

	@Column
	private Integer columns;

	@Column
	private Integer size;
	
	@OneToOne(cascade=CascadeType.ALL, 
			fetch=FetchType.EAGER, 
			optional=false)
	@JoinColumn(name="image_id")
	private TFileEntity image;

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getContent()
	 */
	@Override
	public String getContent() {
		return content;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setContent(java.lang.String)
	 */
	@Override
	public void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getOrientation()
	 */
	@Override
	public Integer getOrientation() {
		return orientation;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setOrientation(java.lang.Integer)
	 */
	@Override
	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getResolution()
	 */
	@Override
	public Integer getResolution() {
		return resolution;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setResolution(java.lang.Integer)
	 */
	@Override
	public void setResolution(Integer resolution) {
		this.resolution = resolution;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getColumns()
	 */
	@Override
	public Integer getColumns() {
		return columns;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setColumns(java.lang.Integer)
	 */
	@Override
	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getSize()
	 */
	@Override
	public Integer getSize() {
		return size;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setSize(java.lang.Integer)
	 */
	@Override
	public void setSize(Integer size) {
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#getImage()
	 */
	@Override
	public ITFileBaseModel getImage() {
		return image;
	}

	/* (non-Javadoc)
	 * @see org.tedros.common.model.ITBarcode#setImage(org.tedros.common.model.TFileEntity)
	 */
	@Override
	public void setImage(ITFileBaseModel image) {
		this.image = (TFileEntity) image;
	}

	@Override
	public ITFileBaseModel createFileModel() {
		TFileEntity m = new TFileEntity();
		TByteEntity b = new TByteEntity();
		m.setByteEntity(b);
		return m;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((resolution == null) ? 0 : resolution.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof TBarcode))
			return false;
		TBarcode other = (TBarcode) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.equals(other.orientation))
			return false;
		if (resolution == null) {
			if (other.resolution != null)
				return false;
		} else if (!resolution.equals(other.resolution))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
