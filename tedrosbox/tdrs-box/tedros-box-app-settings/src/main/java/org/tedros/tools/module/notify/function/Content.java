/**
 * 
 */
package org.tedros.tools.module.notify.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("The structure of a single email draft.")
public class Content {
	
	@JsonPropertyDescription("The email subject line. Keep it concise and professional.")
	private String subject;

	@JsonPropertyDescription("The destination email address (e.g., user@example.com).")
	private String to;
	
    // AQUI ESTÁ O SEGREDO PARA O HTML:
	@JsonPropertyDescription("The email body formatted strictly as HTML. " +
            "Use tags like <p>, <br>, <b>, <ul>, <li>, <table> to structure the text legibly. " +
            "Do NOT use Markdown (like **bold** or # Header). Ensure it provides a good user experience.")
	private String content;
	/**
	 * 
	 */
	public Content() {
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

}
