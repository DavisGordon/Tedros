/**
 * 
 */
package org.tedros.core.message;

import java.util.function.Consumer;

import javafx.event.ActionEvent;

/**
 * @author Davis Gordon
 *
 */
public class TMessage {
	
	private TMessageType type;
	
	private String value;
	
	private String buttonText;
	
	private Consumer<ActionEvent> buttonAction;
	
	private boolean loaded = false;

	/**
	 * @param type
	 * @param value
	 */
	public TMessage(TMessageType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * @param value
	 * @param buttonText
	 * @param buttonAction
	 */
	public TMessage(String value, String buttonText, Consumer<ActionEvent> buttonAction) {
		this.value = value;
		this.buttonText = buttonText;
		this.buttonAction = buttonAction;
	}

	/**
	 * @param type
	 * @param value
	 * @param buttonText
	 * @param buttonAction
	 */
	public TMessage(TMessageType type, String value, String buttonText, Consumer<ActionEvent> buttonAction) {
		this.type = type;
		this.value = value;
		this.buttonText = buttonText;
		this.buttonAction = buttonAction;
	}

	/**
	 * @return the type
	 */
	public TMessageType getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the buttonText
	 */
	public String getButtonText() {
		return buttonText;
	}

	/**
	 * @return the buttonAction
	 */
	public Consumer<ActionEvent> getButtonAction() {
		return buttonAction;
	}

	/**
	 * @return the loaded
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * @param loaded the loaded to set
	 */
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

}
