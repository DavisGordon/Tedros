package com.tedros.core.setting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.propertie, schema = DomainSchema.tedros_core,
uniqueConstraints=@UniqueConstraint(columnNames = { "key", "type" }))
public class TPropertie extends TEntity {
	
	private static final long serialVersionUID = -3294433359406122382L;

	@Column(length=40, nullable = false)
	private String key;
	
	@Column
	private String value;
	
	@Column(length=500)
	private String description;
	
	@Column(length=20)
	@Enumerated(EnumType.STRING)
	private TType type;
	
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



}
