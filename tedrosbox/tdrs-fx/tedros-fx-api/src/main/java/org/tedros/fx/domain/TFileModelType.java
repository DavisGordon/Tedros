/**
 * 
 */
package org.tedros.fx.domain;

import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.model.ITFileModel;

/**
 * @author Davis Gordon
 *
 */
public enum TFileModelType {

	NONE(null),
	MODEL (ITFileModel.class),
	ENTITY (ITFileEntity.class);
	
	private Class<? extends ITFileBaseModel> value;
	
	private TFileModelType(Class<? extends ITFileBaseModel> value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Class<? extends ITFileBaseModel> getValue() {
		return value;
	}
	
	
}
