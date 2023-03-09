/**
 * 
 */
package org.tedros.core.ai.util;

import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TCompletionRequest;
import org.tedros.core.ai.model.TCompletionResult;

/**
 * @author Davis Gordon
 *
 */
public final class TAiModelUtil {

	private TAiModelUtil() {
	}
	
	public static TCompletionRequest convert(TAiCompletion e) {
		TCompletionRequest req = new TCompletionRequest();
		req.setModel(e.getModel());
		req.setMaxTokens(e.getMaxTokens());
		req.setPrompt(e.getPrompt());
		req.setTemperature(e.getTemperature());
		req.setUser(e.getUser());
		
		return req;
	}
	
	public static void parse(TCompletionResult r, TAiCompletion e) {
		
		if(!r.isSuccess()) {
			e.setResponse("Fail see events for more details.");
			e.addEvent("Fail: "+r.getLog());
		}else {
			StringBuilder sb = new StringBuilder();
			r.getChoices().forEach(c->{
				sb.append(c.getText());
			});
			e.setResponse(sb.toString());
			e.addEvent("Success");
		}
	}

}
