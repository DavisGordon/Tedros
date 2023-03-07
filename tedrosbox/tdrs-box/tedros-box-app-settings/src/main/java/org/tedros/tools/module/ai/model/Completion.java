/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TAiModel;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class Completion implements ITModel {

	private static final long serialVersionUID = 6032008792193989534L;

	private String response;
	
	private String prompt;
	
	private Double temperature;
	
	private Integer maxTokens;
	
	private TAiModel model;
	
	
	/**
	 * 
	 */
	public Completion() {
	}


	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}


	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}


	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}


	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}


	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}


	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}


	/**
	 * @return the maxTokens
	 */
	public Integer getMaxTokens() {
		return maxTokens;
	}


	/**
	 * @param maxTokens the maxTokens to set
	 */
	public void setMaxTokens(Integer maxTokens) {
		this.maxTokens = maxTokens;
	}


	/**
	 * @return the model
	 */
	public TAiModel getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(TAiModel model) {
		this.model = model;
	}

}
