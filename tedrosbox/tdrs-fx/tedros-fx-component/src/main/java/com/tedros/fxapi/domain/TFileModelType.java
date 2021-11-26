/**
 * 
 */
package com.tedros.fxapi.domain;

import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.ejb.base.model.ITFileModel;

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
