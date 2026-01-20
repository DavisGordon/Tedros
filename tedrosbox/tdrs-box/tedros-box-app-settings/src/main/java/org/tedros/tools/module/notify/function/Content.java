/**
 * 
 */
package org.tedros.tools.module.notify.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Davis Gordon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
}
