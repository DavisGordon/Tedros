package org.tedros.common.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.common.domain.DomainSchema;
import org.tedros.common.domain.DomainTables;
import org.tedros.server.entity.ITByteEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.entity.TEntity;
import org.tedros.server.model.ITByteBaseModel;

@Entity
@Table(name=DomainTables.file, schema=DomainSchema.tedros_common)
public class TFileEntity extends TEntity implements ITFileEntity {

	private static final long serialVersionUID = 6429566289857357149L;

	@Column(length=60)
	private String owner;
	
	@Column(length=60)
	private String description;
	
	@Column(name="file_name", nullable=false, length=1000)
	private String fileName;
	
	@Column(name="file_extension")
	private String fileExtension;
	
	@Column(name="file_size")
	private Long fileSize;
		
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="BYTE_ID", unique=true, nullable=false)
	private TByteEntity byteEntity;
	
	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void setFileName(String name) {
		this.fileName = name;
	}

	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public void setFileExtension(String extension) {
		this.fileExtension = extension;
		
	}

	@Override
	public TByteEntity getByteEntity() {
		if(this.byteEntity==null)
			this.byteEntity = new TByteEntity();
		return this.byteEntity;
	}

	@Override
	public void setByteEntity(ITByteEntity byteEntity) {
		this.byteEntity = (TByteEntity) byteEntity;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public ITByteBaseModel getByte() {
		return this.getByteEntity();
	}

	@Override
	public <T extends ITByteBaseModel> void setByte(T byteModel) {
		this.setByteEntity((ITByteEntity) byteModel);
	}

	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
}
