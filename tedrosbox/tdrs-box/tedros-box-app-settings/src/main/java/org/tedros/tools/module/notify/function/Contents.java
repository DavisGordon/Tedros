/**
 * 
 */
package org.tedros.tools.module.notify.function;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
public class Contents {

	@JsonPropertyDescription("An list of email content")
	private List<Content> list;
	/**
	 * 
	 */
	public Contents() {
	}
	/**
	 * @return the list
	 */
	public List<Content> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<Content> list) {
		this.list = list;
	}

}
