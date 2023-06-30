package org.tedros.api.presenter.behavior;

import java.util.List;

import org.tedros.core.message.TMessage;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

public interface ITDynaViewSimpleBaseBehavior<M extends ITModelView<E>, E extends ITModel> {

	/**
	 * Show the view screen saver
	 */
	void showScreenSaver();

	/**
	 * If false none message from method addMessage will be displayed.
	 * */
	void setShoWMessages(boolean show);

	/**
	 * Show a list of message in a modal view
	 * */
	void addMessage(List<TMessage> msgList);

	/**
	 * Show messages in a modal view
	 * */
	void addMessage(TMessage... msg);

	/**
	 * Return the {@link ITModelView} class
	 * */
	Class<M> getModelViewClass();

	/**
	 * Define whether the process should skip model change validation.  
	 * @param skipChangeValidation
	 */
	void setSkipChangeValidation(boolean skipChangeValidation);

	/**
	 * Define if the process should ignore the 
	 * validation of mandatory fields.  
	 * @param skipChangeValidation
	 */
	void setSkipRequiredValidation(boolean skipRequiredValidation);

	/**
	 * @return the modelClass
	 */
	Class<E> getModelClass();

}