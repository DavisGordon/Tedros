package com.tedros.common.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.common.domain.DomainSchema;
import com.tedros.common.domain.DomainTables;
import com.tedros.ejb.base.entity.ITByteEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name=DomainTables.file, schema=DomainSchema.tedros_common)
public class TFileEntity extends TEntity implements ITFileEntity {

	private static final long serialVersionUID = 6429566289857357149L;

	@Column(name="file_name", nullable=false, length=1000)
	private String fileName;
	
	@Column(name="file_extension")
	private String fileExtension;
	
	@Column(name="file_size")
	private long fileSize;
		
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
	public long getFileSize() {
		return fileSize;
	}

	@Override
	public void setFileSize(long size) {
		this.fileSize = size;
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

	
}
