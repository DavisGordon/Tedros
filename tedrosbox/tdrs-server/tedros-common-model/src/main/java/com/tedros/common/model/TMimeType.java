/**
 * 
 */
package com.tedros.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.common.domain.DomainSchema;
import com.tedros.common.domain.DomainTables;
import com.tedros.ejb.base.annotation.TCaseSensitive;
import com.tedros.ejb.base.annotation.TEntityImportRule;
import com.tedros.ejb.base.annotation.TFieldImportRule;
import com.tedros.ejb.base.annotation.TFileType;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name=DomainTables.mimeType, schema=DomainSchema.tedros_common)
@TEntityImportRule(description = "#{mimetype.import.rule.desc}", 
fileType = { TFileType.CSV, TFileType.XLS })
public class TMimeType extends TEntity {

	private static final long serialVersionUID = 5379094409048057058L;

	@Column(length=10, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.mimetype.ext}", column = "extension", 
	example=".png", caseSensitive=TCaseSensitive.LOWER, maxLength=10)
	private String extension;

	@Column(length=500)
	@TFieldImportRule(required = true, 
	description = "#{label.mimetype.desc}", column = "description", 
	example="The png image file type",  maxLength=500)
	private String description;

	@Column(length=500)
	@TFieldImportRule(required = true, 
	description = "#{label.mimetype.type}", column = "type", 
	example="image/png",  maxLength=500)
	private String type;

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
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
