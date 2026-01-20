/**
 * 
 */
package org.tedros.tools.module.notify.function;

import java.util.List;

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
public class Contents {

	@JsonPropertyDescription("A list of email drafts to be generated.")
	private List<Content> list;
}
