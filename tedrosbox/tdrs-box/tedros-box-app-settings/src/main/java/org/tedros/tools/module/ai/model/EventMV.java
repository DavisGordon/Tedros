/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TRequestEvent;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class EventMV extends TEntityModelView<TRequestEvent> {

	private SimpleStringProperty response;
	
	private SimpleStringProperty prompt;
	
	private SimpleStringProperty log;
	
	
	public EventMV(TRequestEvent entity) {
		super(entity);
	}


	/**
	 * @return the response
	 */
	public SimpleStringProperty getResponse() {
		return response;
	}


	/**
	 * @param response the response to set
	 */
	public void setResponse(SimpleStringProperty response) {
		this.response = response;
	}


	/**
	 * @return the prompt
	 */
	public SimpleStringProperty getPrompt() {
		return prompt;
	}


	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(SimpleStringProperty prompt) {
		this.prompt = prompt;
	}


	/**
	 * @return the log
	 */
	public SimpleStringProperty getLog() {
		return log;
	}


	/**
	 * @param log the log to set
	 */
	public void setLog(SimpleStringProperty log) {
		this.log = log;
	}

}
