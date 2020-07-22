package com.tedros.common.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.common.domain.DomainSchema;
import com.tedros.common.domain.DomainTables;
import com.tedros.ejb.base.entity.ITByteEntity;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.binary, schema = DomainSchema.tedros_common)
public class TByteEntity extends TEntity implements ITByteEntity{

	private static final long serialVersionUID = -4185266706929273739L;
	
	@OneToOne(optional=false, mappedBy="byteEntity")
	private TFileEntity fileEntity;
		
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private byte[] bytes;
	
	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public final TFileEntity getFileEntity() {
		return fileEntity;
	}

	public final void setFileEntity(TFileEntity fileEntity) {
		this.fileEntity = fileEntity;
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
