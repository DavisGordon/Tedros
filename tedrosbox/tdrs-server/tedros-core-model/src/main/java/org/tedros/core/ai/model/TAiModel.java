/**
 * 
 */
package org.tedros.core.ai.model;

/**
 * @author Davis Gordon
 *
 */
public enum TAiModel {
	GPT3_TURBO ("gpt-3.5-turbo"),
	TEXT_DAVINCI_003 ("text-davinci-003"),
	TEXT_DAVINCI_002 ("text-davinci-002"),
	CODE_DAVINCI_002 ("code-davinci-002"),
	CURIE ("CURIE"),
	ADA("ADA"),
	BABBAGE ("BABBAGE"),
	DAVINCI ("DAVINCI"),
	TURBO ("TURBO");
	
	private String value;

	/**
	 * @param value
	 */
	private TAiModel(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}
