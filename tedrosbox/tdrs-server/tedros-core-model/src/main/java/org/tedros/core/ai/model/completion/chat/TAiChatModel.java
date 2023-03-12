/**
 * 
 */
package org.tedros.core.ai.model.completion.chat;

/**
 * @author Davis Gordon
 *
 */
public enum TAiChatModel {
	
	GPT35_TURBO ("gpt-3.5-turbo");
	
	private String value;

	/**
	 * @param value
	 */
	private TAiChatModel(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String value() {
		return value;
	}
	
	public static TAiChatModel value(String s) {
		for(TAiChatModel m : values())
			if(m.value().equals(s))
				return m;
		return null;
	}
	
	
}
