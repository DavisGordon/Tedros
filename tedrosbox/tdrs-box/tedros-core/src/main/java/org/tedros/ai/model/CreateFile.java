/**
 * 
 */
package org.tedros.ai.model;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
public class CreateFile {
	
	@TRequiredProperty
	@JsonPropertyDescription("the file name")	
	private String name;
	
	@TRequiredProperty
	@JsonPropertyDescription("the file extension")
	private String extension;
	
	@TRequiredProperty
	@JsonPropertyDescription("the file content")
	private String content;

	/**
	 * 
	 */
	public CreateFile() {
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
