/**
 * 
 */
package org.tedros.core.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.ai.model.TCompletionRequest;
import org.tedros.core.ai.model.TCompletionResult;
import org.tedros.core.cdi.producer.Item;
import org.tedros.core.domain.DomainPropertie;
import org.tedros.core.security.model.TUser;
import org.tedros.server.exception.TBusinessException;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TOpenAiBO {


	@Inject
	@Named(DomainPropertie.OPENAI_KEY)
	private Item<String> key;

	@Inject
	@Named(DomainPropertie.AI_ENABLED)
	private Item<Boolean> enabled;
	
	/**
	 * 
	 */
	public TOpenAiBO() {
	}
	
	public TCompletionResult completion(TUser user, TCompletionRequest req) throws TBusinessException {
		
		if(enabled==null || !enabled.get())
			throw new TBusinessException("Artificial Intelligence is off!");
		
		if(key==null || StringUtils.isBlank(key.get()))
			throw new TBusinessException("The OpenAi key is required, create it at https://openai.com");
		
		OpenAiService service = new OpenAiService(key.get());
		CompletionRequest completionRequest = CompletionRequest.builder()
		        .prompt(req.getPrompt())
		        .model("ada")
		        .echo(true)
		        .build();
		try {
			CompletionResult r = service.createCompletion(completionRequest);
			TCompletionResult tr = new TCompletionResult(r.getId(), r.getCreated(), r.getModel());
			r.getChoices().forEach(c->{
				tr.addChoice(c.getText(), c.getIndex(), c.getFinish_reason());
			});
			return tr;
		}catch(Exception e) {
			throw new TBusinessException(e.getMessage());
		}
		
	}

}
