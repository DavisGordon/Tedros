package org.tedros.core.model;

import org.tedros.server.entity.ITEntity;

import javafx.beans.property.SimpleLongProperty;

public interface ITEntityModelView<E extends ITEntity> extends ITModelView<E> {

	/**
	 * Return the {@link ITEntity}
	 * */
	E getEntity();

	/**
	 * @return the id
	 */
	SimpleLongProperty getId();

	/**
	 * @param id the id to set
	 */
	void setId(SimpleLongProperty id);

}