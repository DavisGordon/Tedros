/**
 * 
 */
package org.tedros.chat.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
@Deprecated
public class FileMsg implements ITModel {

	private static final long serialVersionUID = 3155379886239922784L;
	private Long id;
	private String name;
	private String extension;
	private long size;
	
	private Long idByteEntity;
	private byte[] bytes;
	
	/**
	 * 
	 */
	public FileMsg() {
	}
	
	/**
	 * @param id
	 * @param name
	 * @param extension
	 * @param size
	 * @param idByteEntity
	 * @param bytes
	 */
	public FileMsg(Long id, String name, String extension, long size, Long idByteEntity, byte[] bytes) {
		this.id = id;
		this.name = name;
		this.extension = extension;
		this.size = size;
		this.idByteEntity = idByteEntity;
		this.bytes = bytes;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @return the idByteEntity
	 */
	public Long getIdByteEntity() {
		return idByteEntity;
	}
	/**
	 * @param idByteEntity the idByteEntity to set
	 */
	public void setIdByteEntity(Long idByteEntity) {
		this.idByteEntity = idByteEntity;
	}
	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}
	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idByteEntity == null) ? 0 : idByteEntity.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FileMsg))
			return false;
		FileMsg other = (FileMsg) obj;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idByteEntity == null) {
			if (other.idByteEntity != null)
				return false;
		} else if (!idByteEntity.equals(other.idByteEntity))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (name != null ? name : "");
	}
	
}
