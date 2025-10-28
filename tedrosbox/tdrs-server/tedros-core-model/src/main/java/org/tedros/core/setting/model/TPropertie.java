package org.tedros.core.setting.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

@Entity
@Table(name = DomainTables.propertie, schema = DomainSchema.tedros_core,
uniqueConstraints=@UniqueConstraint(columnNames = { "key", "type" }))
public class TPropertie extends TVersionEntity {
	
	private static final long serialVersionUID = -3294433359406122382L;

	@Column(length=40, nullable = false)
	private String name;
	
	@Column(length=20, nullable = false)
	private String key;
	
	@Column
	private String value;
	
	@Column(length=500)
	private String description;
	
	@Column(length=20)
	@Enumerated(EnumType.STRING)
	private TType type;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="file_id")
	private TFileEntity file;
	
	public TPropertie() {
		
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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

	/**
	 * @return the type
	 */
	public TType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TType type) {
		this.type = type;
	}

	/**
	 * @return the file
	 */
	public TFileEntity getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(TFileEntity file) {
		this.file = file;
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



}
