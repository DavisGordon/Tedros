/**
 * 
 */
package org.tedros.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.server.entity.TEntity;
import org.tedros.chat.domain.DomainSchema;
import org.tedros.chat.domain.DomainTables;

/**
 * @author Davis Dun
 *
 */
@Entity
@Table(name=DomainTables.tchat_setting, schema=DomainSchema.schema)
public class ChatSetting extends TEntity {

	private static final long serialVersionUID = -8008690210025662586L;

	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
	
	
	
}
