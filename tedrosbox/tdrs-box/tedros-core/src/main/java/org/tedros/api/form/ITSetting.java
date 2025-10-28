package org.tedros.api.form;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.model.ITModelView;

import javafx.beans.Observable;

public interface ITSetting {

	void run();

	/**
	 * @return the descriptor
	 */
	ITComponentDescriptor getDescriptor();

	/**
	 * @return the control
	 */
	<T> T getControl(String field);

	/**
	 * @return the layout
	 */
	<T> T getLayout(String field);

	/**
	 * @return the TFieldBox
	 */
	ITFieldBox getFieldBox(String field);

	/**
	 * @return the modelView registered observable property
	 */
	Observable getProperty(String field);

	/**
	 * @return the modelView 
	 */
	@SuppressWarnings("rawtypes")
	<T extends ITModelView> T getModelView();

	/**
	 * @return the form 
	 */
	ITForm getForm();
	
	/**
	 * Called to clean all items to the garbage collector
	 * */
	void dispose();

}