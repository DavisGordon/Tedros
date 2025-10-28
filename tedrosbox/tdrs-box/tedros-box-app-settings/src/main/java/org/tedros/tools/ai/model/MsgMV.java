/**
 * 
 */
package org.tedros.tools.ai.model;

import org.tedros.ai.model.Message;
import org.tedros.fx.model.TModelView;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class MsgMV extends TModelView<Message> {

	private SimpleStringProperty content;
	
	/**
	 * @param model
	 */
	public MsgMV(Message model) {
		super(model);
	}

	/**
	 * @return the content
	 */
	public SimpleStringProperty getContent() {
		return content;
	}

}
