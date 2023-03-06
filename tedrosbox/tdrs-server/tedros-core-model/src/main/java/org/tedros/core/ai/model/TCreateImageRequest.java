/**
 * 
 */
package org.tedros.core.ai.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TCreateImageRequest implements ITModel {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 8324658434089146507L;

	/**
     * A text description of the desired image(s). The maximum length in 1000 characters.
     */
    private String prompt;

    /**
     * The number of images to generate. Must be between 1 and 10. Defaults to 1.
     */
    private Integer n;

    /**
     * The size of the generated images. Must be one of "256x256", "512x512", or "1024x1024". Defaults to "1024x1024".
     */
    private TImageSize size;

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json. Defaults to url.
     */
    private TResponseFormat responseFormat;

    /**
     * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
     */
    private String user;
	
	/**
	 * 
	 */
	public TCreateImageRequest() {
	}

	/**
	 * @param prompt
	 * @param n
	 * @param size
	 * @param responseFormat
	 * @param user
	 */
	public TCreateImageRequest(String prompt, Integer n, TImageSize size, TResponseFormat responseFormat, String user) {
		this.prompt = prompt;
		this.n = n;
		this.size = size;
		this.responseFormat = responseFormat;
		this.user = user;
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
	 * @return the n
	 */
	public Integer getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(Integer n) {
		this.n = n;
	}

	/**
	 * @return the size
	 */
	public TImageSize getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(TImageSize size) {
		this.size = size;
	}

	/**
	 * @return the responseFormat
	 */
	public TResponseFormat getResponseFormat() {
		return responseFormat;
	}

	/**
	 * @param responseFormat the responseFormat to set
	 */
	public void setResponseFormat(TResponseFormat responseFormat) {
		this.responseFormat = responseFormat;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
}
