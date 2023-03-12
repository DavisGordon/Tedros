/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class AiChatMessageMV extends TEntityModelView<TAiChatMessage> {

	private SimpleStringProperty content;
	
	public AiChatMessageMV(TAiChatMessage entity) {
		super(entity);
	}

	/**
	 * @return the content
	 */
	public SimpleStringProperty getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}

}
