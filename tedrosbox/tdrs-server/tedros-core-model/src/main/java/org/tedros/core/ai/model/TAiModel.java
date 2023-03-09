/**
 * 
 */
package org.tedros.core.ai.model;

/**
 * @author Davis Gordon
 *
 */
public enum TAiModel {
	TEXT_DAVINCI_003 ("text-davinci-003"),
	//TEXT_DAVINCI_002 ("text-davinci-002"),
	//CODE_DAVINCI_002 ("code-davinci-002"),
	CURIE ("curie"),
	ADA("ada"),
	BABBAGE ("babbage");
	//DAVINCI ("davinci");
	//TURBO ("TURBO");
	
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
